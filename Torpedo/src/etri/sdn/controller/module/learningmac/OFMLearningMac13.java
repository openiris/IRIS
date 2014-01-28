package etri.sdn.controller.module.learningmac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.ver1_3.messages.OFActionOutput;
import org.openflow.protocol.ver1_3.messages.OFFlowMod;
import org.openflow.protocol.ver1_3.messages.OFInstructionApplyActions;
import org.openflow.protocol.ver1_3.messages.OFMatchOxm;
import org.openflow.protocol.ver1_3.messages.OFOxm;
import org.openflow.protocol.ver1_3.messages.OFPacketIn;
import org.openflow.protocol.ver1_3.messages.OFPacketOut;
import org.openflow.protocol.ver1_3.types.OFActionType;
import org.openflow.protocol.ver1_3.types.OFFlowModCommand;
import org.openflow.protocol.ver1_3.types.OFInstructionType;
import org.openflow.protocol.ver1_3.types.OFMatchType;
import org.openflow.protocol.ver1_3.types.OFOxmClass;
import org.openflow.protocol.ver1_3.types.OFOxmMatchFields;
import org.openflow.protocol.ver1_3.types.OFPortNo;
import org.openflow.util.LRULinkedHashMap;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.util.Logger;

/**
 * MAC Learning Module. 
 * Modified the original LearningMac class of Floodlight.
 * 
 * @author labry
 *
 */
public final class OFMLearningMac13 extends OFModule {

	/**
	 * Table to save learning result.
	 */
	private Map<IOFSwitch, Map<MacVlanPair, Short>> macVlanToSwitchPortMap =
			new ConcurrentHashMap<IOFSwitch, Map<MacVlanPair, Short>>();


	private  OFProtocol version_adaptor_13;
	
	// flow-mod - for use in the cookie
	private static final int LEARNING_SWITCH_APP_ID = 1;
	private static final int APP_ID_BITS = 12;
	private static final int APP_ID_SHIFT = (64 - APP_ID_BITS);
	private static final long LEARNING_SWITCH_COOKIE = (long) (LEARNING_SWITCH_APP_ID & ((1 << APP_ID_BITS) - 1)) << APP_ID_SHIFT;

	private static final short IDLE_TIMEOUT_DEFAULT = 0;
	private static final short HARD_TIMEOUT_DEFAULT = 0;
	private static final short PRIORITY_DEFAULT = 100;
	// normally, setup reverse flow as well. 
	private static final boolean LEARNING_SWITCH_REVERSE_FLOW = true;
	private static final int MAX_MACS_PER_SWITCH  = 1000; 

	/**
	 * Constructor to create learning mac module instance. 
	 * It does nothing internally.
	 */
	public OFMLearningMac13() {

		//send table-miss flow_mod to switch upon connection.
		
	}

	/**
	 * Adds a host to the MAC/VLAN->SwitchPort mapping
	 * @param sw The switch to add the mapping to
	 * @param mac The MAC address of the host to add
	 * @param vlan The VLAN that the host is on
	 * @param portVal The switchport that the host is on
	 */
	protected void addToPortMap(IOFSwitch sw, long mac, short vlan, short portVal) {
		Map<MacVlanPair,Short> swMap = macVlanToSwitchPortMap.get(sw);

		if (vlan == (short) 0xffff) {
			// OFMatch.loadFromPacket sets VLAN ID to 0xffff if the packet contains no VLAN tag;
			// for our purposes that is equivalent to the default VLAN ID 0
			vlan = 0;
		}

		if (swMap == null) {
			// May be accessed by REST API so we need to make it thread safe
			//			swMap = new ConcurrentHashMap<MacVlanPair,Short>();
			swMap = Collections.synchronizedMap(new LRULinkedHashMap<MacVlanPair,Short>(MAX_MACS_PER_SWITCH));
			macVlanToSwitchPortMap.put(sw, swMap);
		}
		swMap.put(new MacVlanPair(mac, vlan, sw), portVal);
	}

	/**
	 * Removes a host from the MAC/VLAN->SwitchPort mapping
	 * @param sw The switch to remove the mapping from
	 * @param mac The MAC address of the host to remove
	 * @param vlan The VLAN that the host is on
	 */
	protected void removeFromPortMap(IOFSwitch sw, long mac, short vlan) {
		if (vlan == (short) 0xffff) {
			vlan = 0;
		}

		Map<MacVlanPair,Short> swMap = macVlanToSwitchPortMap.get(sw);
		if (swMap != null)
			swMap.remove(new MacVlanPair(mac, vlan, sw));
	}

	/**
	 * Get the port that a MAC/VLAN pair is associated with
	 * @param sw The switch to get the mapping from
	 * @param mac The MAC address to get
	 * @param vlan The VLAN number to get
	 * @return The port the host is on
	 */
	public Short getFromPortMap(IOFSwitch sw, long mac, short vlan) {
		if (vlan == (short) 0xffff) {
			vlan = 0;
		}

		Map<MacVlanPair,Short> swMap = macVlanToSwitchPortMap.get(sw);
		if (swMap != null)
			return swMap.get(new MacVlanPair(mac, vlan, sw));

		// if none found
		return null;
	}

	/**
	 * Clears the MAC/VLAN -> SwitchPort map for all switches
	 */
	public void clearLearnedTable() {
		macVlanToSwitchPortMap.clear();
	}

	/**
	 * Clears the MAC/VLAN -> SwitchPort map for a single switch
	 * @param sw The switch to clear the mapping for
	 */
	public void clearLearnedTable(IOFSwitch sw) {
		Map<MacVlanPair, Short> swMap = macVlanToSwitchPortMap.get(sw);
		if (swMap != null)
			swMap.clear();
	}

	/**
	 * Get all the mappings between MAC/VLAN to switch port.
	 * 
	 * @return	Map<IOFSwitch, Map<MacVlanPair,Short>> object
	 */
	public synchronized Map<IOFSwitch, Map<MacVlanPair,Short>> getTable() {
		return macVlanToSwitchPortMap;
	}




	/**
	 * Writes a OFFlowMod to a switch.
	 * @param sw The switch tow rite the flowmod to.
	 * @param command The FlowMod actions (add, delete, etc).
	 * @param bufferId The buffer ID if the switch has buffered the packet.
	 * @param match The OFMatch structure to write.
	 * @param outPort The switch port to output it to.
	 */
	private void writeFlowMod(IOFSwitch sw, short command, int bufferId,
			OFMatchOxm matchOxm, short outPort, List<OFMessage> out) {
		// from openflow 1.3.2 spec - need to set these on a struct ofp_flow_mod:
		// generate match with eth_src eth_dst and such..
		// here 

		// send flow_mod to process [labry]
		OFInstructionApplyActions instruction = new OFInstructionApplyActions();
		List<org.openflow.protocol.interfaces.OFInstruction> instructions = new LinkedList<org.openflow.protocol.interfaces.OFInstruction>();
		OFActionOutput action = new OFActionOutput();
		List<org.openflow.protocol.interfaces.OFAction> actions = new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		
		OFFlowMod fm = new OFFlowMod();
		action.setType(OFActionType.OUTPUT);
		action.setPort(OFPort.of(outPort));
		action.setMaxLength((short) 0x0);
		action.setLength(action.computeLength());
		actions.add(action);
		instruction.setActions(actions);
		instruction.setType(OFInstructionType.APPLY_ACTIONS); //labry added
		instruction.setLength(instruction.computeLength());//labry added
		instructions.clear();
		instructions.add(instruction);

		fm.setTableId((byte) 0x0)						//the table which the flow entry should be inserted
		.setCommand(OFFlowModCommand.ADD)
		.setIdleTimeout((short) 0)
		.setHardTimeout((short) 0)					//permanent if idle and hard timeout are zero
		.setPriority((short) 100)
		.setBufferId(bufferId)					//refers to a packet buffered at the switch and sent to the controller
		.setOutGroup(bufferId)					//OFPP_ANY
		.setOutPort(OFPort.of((command == OFFlowModCommand.DELETE.getValue()) ? outPort : OFPortNo.ANY.getValue()))
		.setMatch(matchOxm)
		.setInstructions(instructions)
		.setFlagsWire((short) 0x0001);					//send flow removed message when flow expires or is deleted
		fm.setLength(fm.computeLength());
		
		out.add(fm);
	}

	/**
	 * Writes an OFPacketOut message to a switch.
	 * 
	 * @param sw 				The switch to write the PacketOut to.
	 * @param packetInMessage 	The corresponding PacketIn.
	 * @param inPort 		
	 * @param egressPort 		The switchport to output the PacketOut.
	 */
	private void writePacketOutForPacketIn(IOFSwitch sw, 
			OFPacketIn packetInMessage, 
			int egressPort,
			short getInputPort,
			List<OFMessage> out) {
		// from openflow 1.0 spec - need to set these on a struct ofp_packet_out:
		// uint32_t buffer_id; /* ID assigned by datapath (-1 if none). */
		// uint16_t in_port; /* Packet's input port (OFPP_NONE if none). */
		// uint16_t actions_len; /* Size of action array in bytes. */
		// struct ofp_action_header actions[0]; /* Actions. */
		/* uint8_t data[0]; */ /* Packet data. The length is inferred
                                  from the length field in the header.
                                  (Only meaningful if buffer_id == -1.) */

		OFPacketOut packetOutMessage = new OFPacketOut();

		short packetOutLength = (short)OFPacketOut.MINIMUM_LENGTH; // starting length

		// Set buffer_id, in_port, actions_len
		packetOutMessage.setBufferId(packetInMessage.getBufferId());
//		packetOutMessage.setInPort(OFPort.of(getInputPort));
		packetOutMessage.setInputPort(OFPort.of(getInputPort));
		packetOutMessage.setActionsLength((short)OFActionOutput.MINIMUM_LENGTH);
		packetOutLength += OFActionOutput.MINIMUM_LENGTH;

		// set actions
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>(1);      
		OFActionOutput action_output = new OFActionOutput();
		action_output.setPort(OFPort.of(egressPort)).setMaxLength((short)0);
		actions.add(action_output);
		//		actions.add(new OFActionOutput(egressPort, (short) 0));
		packetOutMessage.setActions(actions);

		// set data - only if buffer_id == -1
		if (packetInMessage.getBufferId() == 0xffffffff /*OFPacketOut.BUFFER_ID_NONE*/) {
			//			byte[] packetData = packetInMessage.getPacketData();
			//			packetOutMessage.setPacketData(packetData); 
			byte[] packetData = packetInMessage.getData();
			packetOutMessage.setData(packetData);
			packetOutLength += (short)packetData.length;
		} 

		// finally, set the total length
		packetOutMessage.setLength(packetOutLength);     

		//		System.err.println("FB PacketOut 3 =======" + packetOutMessage.toString());

		// TODO: counter store support
		//		counterStore.updatePktOutFMCounterStore(sw, packetOutMessage);
		out.add(packetOutMessage);
	}

	/**
	 * this method is a callback called by controller to request this module to 
	 * handle an incoming message. 
	 * 
	 * @param conn		connection that the message was received
	 * @param context	MessageContext object for the message.
	 * @param msg		the received message
	 * @param outgoing	list to put some outgoing messages to switch. 
	 */
	@Override
	public boolean handleMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing) {
		return processPacketInMessage(conn, context, msg, outgoing);
	}

	/**
	 * Handle disconnection to a switch. 
	 * As this module does not handle the event, this method just return true.
	 * 
	 * @param conn		connection to a switch (just disconnected)
	 */
	@Override
	public boolean handleDisconnect(Connection conn) {
		// does nothing currently.
		return true;
	}

	/**
	 * Processes a OFPacketIn message. If the switch has learned the MAC/VLAN to port mapping
	 * for the pair it will write a FlowMod for. If the mapping has not been learned the 
	 * we will flood the packet.
	 * 
	 * @param context 
	 * @param sw
	 * @param pi
	 * @return
	 */
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] toBytes(short s, short d) {
        return new byte[]{(byte)(s & 0x00FF),(byte)((s & 0xFF00)>>8),(byte)((d & 0xFF00)>>16),(byte)((d & 0xFF00)>>32)};
    }
	
	public boolean processPacketInMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> out) {

		if ( conn.getSwitch() == null ) {
			
			Logger.stderr("Connection is not fully handshaked");
			return true;
		}

		if ( msg == null ) {
			// this is critical. 
			// no further processing of this msg is possible.
			return false;
		}

		OFPacketIn pi = (OFPacketIn) msg;
		
		Long sourceMac = null;
		Long destMac = null;
		Integer sourceIP = null;
		Integer destIP = null;
		Short vlan = new Short((short) 0xffff);
		Short outPort = null;

		OFMatchOxm recievedMatchOxm = (OFMatchOxm) pi.getMatch();
		
		List<org.openflow.protocol.interfaces.OFOxm> recievedlistOxm = recievedMatchOxm.getOxmFields();

		Integer getInputPortTmp = null;

		for(org.openflow.protocol.interfaces.OFOxm oxm : recievedlistOxm) {
			
			switch(oxm.getField()) {
			case 11:
				sourceIP = IPv4.toIPv4Address(oxm.getData());
				break;
			case 12:
				destIP = IPv4.toIPv4Address(oxm.getData());
				break;
			case 4:
				sourceMac = Ethernet.toLong(oxm.getData());
				break;
			case 3:
				destMac = Ethernet.toLong(oxm.getData());
				break;
			case 6: 
				vlan = readShort(oxm.getData());
				break;
			case 0:
				getInputPortTmp = IPv4.toIPv4Address(oxm.getData());
			}
		}

		if(sourceMac == null || destMac == null ) {
			return true;
		}
		
		Short getInputPort = getInputPortTmp.shortValue();
		
		
		OFMatchOxm forwardMatchOxm = new OFMatchOxm();
		forwardMatchOxm.setType(OFMatchType.OXM);
		List<org.openflow.protocol.interfaces.OFOxm> forwardListOxm = new ArrayList<org.openflow.protocol.interfaces.OFOxm>();

		OFOxm ofOxmInPort = new OFOxm();
		ofOxmInPort.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
		ofOxmInPort.setField(OFOxmMatchFields.OFB_IN_PORT.getValue()); //OFOxmMatchFields.
//		ofOxmInPort.setField(OFOxmMatchFields.OFB_IN_PORT.getValue()); //OFOxmMatchFields.
		ofOxmInPort.setBitmask((byte) 0);
		ofOxmInPort.setData(IPv4.toIPv4AddressBytes(getInputPortTmp));
		ofOxmInPort.setPayloadLength((byte) 0x04);


		OFOxm ofOxmEthDst = new OFOxm();
		ofOxmEthDst.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
		ofOxmEthDst.setField(OFOxmMatchFields.OFB_ETH_DST.getValue()); //OFOxmMatchFields.
		ofOxmEthDst.setBitmask((byte) 0);
		ofOxmEthDst.setData(Ethernet.toByteArray(destMac));
		ofOxmEthDst.setPayloadLength((byte) 0x06);

		OFOxm ofOxmEthSrc = new OFOxm();
		ofOxmEthSrc.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
		ofOxmEthSrc.setField(OFOxmMatchFields.OFB_ETH_SRC.getValue()); //OFOxmMatchFields.
		ofOxmEthSrc.setBitmask((byte) 0);
		ofOxmEthSrc.setData(Ethernet.toByteArray(sourceMac));
		ofOxmEthSrc.setPayloadLength((byte) 0x06);

		forwardListOxm.add(ofOxmInPort);
		forwardListOxm.add(ofOxmEthDst);
		forwardListOxm.add(ofOxmEthSrc);

		forwardMatchOxm.setOxmFields(forwardListOxm);
		forwardMatchOxm.setLength((short) forwardMatchOxm.computeLength());//working on...
		
		if ((destMac & 0xfffffffffff0L) == 0x0180c2000000L) {
			return true;
		}
		
		if ((sourceMac & 0x010000000000L) == 0) {
			// If source MAC is a unicast address, learn the port for this MAC/VLAN
			this.addToPortMap(conn.getSwitch(), sourceMac, vlan, getInputPort);
		}
		
		// Now output flow-mod and/or packet
		outPort = getFromPortMap(conn.getSwitch(), destMac, vlan);
		if (outPort == null) {
			// If we haven't learned the port for the dest MAC/VLAN, flood it
			// Don't flood broadcast packets if the broadcast is disabled.
			// XXX For LearningSwitch this doesn't do much. The sourceMac is removed
			//     from port map whenever a flow expires, so you would still see
			//     a lot of floods.
			this.writePacketOutForPacketIn(conn.getSwitch(), pi, OFPortNo.FLOOD.getValue(), getInputPort, out);
			
		} else if (outPort == getInputPort) {
			
			System.out.println("ignored.");

		} else {
			// Add flow table entry matching source MAC, dest MAC, VLAN and input port
			// that sends to the port we previously learned for the dest MAC/VLAN.  Also
			// add a flow table entry with source and destination MACs reversed, and
			// input and output ports reversed.  When either entry expires due to idle
			// timeout, remove the other one.  This ensures that if a device moves to
			// a different port, a constant stream of packets headed to the device at
			// its former location does not keep the stale entry alive forever.
			// FIXME: current HP switches ignore DL_SRC and DL_DST fields, so we have to match on
			// NW_SRC and NW_DST as well


			this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD.getValue(), 
					pi.getBufferId(), forwardMatchOxm, outPort, out);

			
			if (LEARNING_SWITCH_REVERSE_FLOW) {		
				
//				matchOxm2.setType(OFMatchType.OXM);
//				List<OFOxm> listOxm2 = new ArrayList<OFOxm>();
//	
//				OFOxm ofOxmInPort = new OFOxm();
//				ofOxmInPort.setOxmClass(OFOxmClass.OFPXMC_OPENFLOW_BASIC);
//				ofOxmInPort.setField(OFOxmMatchFields.OFPXMT_OFB_IN_PORT.getValue()); //OFOxmMatchFields.
//				ofOxmInPort.setBitmask((byte) 0);
//				ofOxmInPort.setData(IPv4.toIPv4AddressBytes(outPort));
//				ofOxmInPort.setPayloadLength((byte) 0x04);
//	
//	
//				OFOxm ofOxmEthDst = new OFOxm();
//				ofOxmEthDst.setOxmClass(OFOxmClass.OFPXMC_OPENFLOW_BASIC);
//				ofOxmEthDst.setField(OFOxmMatchFields.OFPXMT_OFB_ETH_DST.getValue()); //OFOxmMatchFields.
//				ofOxmEthDst.setBitmask((byte) 0);
//				ofOxmEthDst.setData(Ethernet.toByteArray(sourceMac));
//				ofOxmEthDst.setPayloadLength((byte) ofOxmEthDst.computeLength());
//	
//				OFOxm ofOxmEthSrc = new OFOxm();
//				ofOxmEthSrc.setOxmClass(OFOxmClass.OFPXMC_OPENFLOW_BASIC);
//				ofOxmEthSrc.setField(OFOxmMatchFields.OFPXMT_OFB_ETH_SRC.getValue()); //OFOxmMatchFields.
//				ofOxmEthSrc.setBitmask((byte) 0);
//				ofOxmEthSrc.setData(Ethernet.toByteArray(destMac));
//				ofOxmEthSrc.setPayloadLength((byte) ofOxmEthSrc.computeLength());
//				
//				OFOxm ofOxmIPSrc = new OFOxm();
//				ofOxmEthSrc.setOxmClass(OFOxmClass.OFPXMC_OPENFLOW_BASIC);
//				ofOxmEthSrc.setField(OFOxmMatchFields.OFPXMT_OFB_IPV4_SRC.getValue()); //OFOxmMatchFields.
//				ofOxmEthSrc.setBitmask((byte) 0);
//				ofOxmEthSrc.setData(Ethernet.toByteArray(destMac));
//				ofOxmEthSrc.setPayloadLength((byte) ofOxmEthSrc.computeLength());
//				
//				OFOxm ofOxmIPDst = new OFOxm();
//				ofOxmEthSrc.setOxmClass(OFOxmClass.OFPXMC_OPENFLOW_BASIC);
//				ofOxmEthSrc.setField(OFOxmMatchFields.OFPXMT_OFB_IPV4_DST.getValue()); //OFOxmMatchFields.
//				ofOxmEthSrc.setBitmask((byte) 0);
//				ofOxmEthSrc.setData(Ethernet.toByteArray(destMac));
//				ofOxmEthSrc.setPayloadLength((byte) ofOxmEthSrc.computeLength());
//	
//				listOxm2.add(ofOxmInPort);
//				listOxm2.add(ofOxmEthDst);
//				listOxm2.add(ofOxmEthSrc);
//	
//				matchOxm.setOxmFields(listOxm2);
//				matchOxm.setLength((short) matchOxm.computeLength());//working on...
			}
			
		}
		return true;			
}

private short readShort(byte[] data) {
	
	return (short) (((data[0] << 8 | data[1] & 0xff)));
}


/**
 * Initialize this module. As this module processes all PACKET_IN messages,
 * it registers filter to receive those messages.
 */
@Override
protected void initialize() {

	//version_adaptor_13 = (VersionAdaptor13) getController().getVersionAdaptor((byte)0x04);
	this.version_adaptor_13 = getController().getProtocol();
	
	registerFilter(
			OFMessageType.PACKET_IN,
			new OFMFilter() {
				@Override
				public boolean filter(OFMessage m) {
					return m.getVersion() == (byte)0x04;
				}
			}
			);
}

/**
 * As this module does not process handshake event, 
 * it just returns true.
 * 
 * @return		true
 */
@Override
protected boolean handleHandshakedEvent(Connection sw, MessageContext context) {
	return true;
}

/**
 * As this module have no model, it just returns null
 * 
 * @return		null
 */
@Override
public OFModel[] getModels() {
	// TODO Auto-generated method stub
	return null;
}
}
