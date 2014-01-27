package etri.sdn.controller.module.learningmac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFBFlowWildcard;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.OFMatchUtil;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.interfaces.OFActionOutput;
import org.openflow.protocol.interfaces.OFFlowMod;
import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFOxmMatchFields;
import org.openflow.protocol.interfaces.OFPacketIn;
import org.openflow.protocol.interfaces.OFPacketOut;
import org.openflow.protocol.interfaces.OFFlowModCommand;
import org.openflow.protocol.interfaces.OFInstructionApplyActions;
import org.openflow.protocol.interfaces.OFActionType;
import org.openflow.protocol.interfaces.OFInstructionType;
import org.openflow.util.LRULinkedHashMap;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.util.Logger;

/**
 * MAC Learning Module. 
 * Modified the original LearningMac class of Floodlight.
 * 
 * @author labry
 *
 */
public final class OFMLearningMacMultipleVersion extends OFModule {

	/**
	 * Table to save learning result.
	 */
	private Map<IOFSwitch, Map<MacVlanPair, Short>> macVlanToSwitchPortMap =
			new ConcurrentHashMap<IOFSwitch, Map<MacVlanPair, Short>>();

	private OFProtocol protocol;

	// flow-mod - for use in the cookie
	private static final int LEARNING_SWITCH_APP_ID = 1;
	private static final int APP_ID_BITS = 12;
	private static final int APP_ID_SHIFT = (64 - APP_ID_BITS);
	private static final long LEARNING_SWITCH_COOKIE = (long) (LEARNING_SWITCH_APP_ID & ((1 << APP_ID_BITS) - 1)) << APP_ID_SHIFT;

	private static final short IDLE_TIMEOUT_DEFAULT = 5;
	private static final short HARD_TIMEOUT_DEFAULT = 0;
	private static final short PRIORITY_DEFAULT = 100;
	// normally, setup reverse flow as well. 
	private static final boolean LEARNING_SWITCH_REVERSE_FLOW = true;
	private static final int MAX_MACS_PER_SWITCH  = 1000; 

	/**
	 * Constructor to create learning mac module instance. 
	 * It does nothing internally.
	 */
	public OFMLearningMacMultipleVersion() {
		// does nothing
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
	private void writeFlowMod(IOFSwitch sw, OFFlowModCommand command, int bufferId,
			OFMatch match, short outPort, List<OFMessage> out) {


		OFFlowMod flowMod = OFMessageFactory.createFlowMod(sw.getVersion());

		if(flowMod.isInstructionsSupported()) {
			OFInstructionApplyActions instruction = OFMessageFactory.createInstructionApplyActions(sw.getVersion());
			List<org.openflow.protocol.interfaces.OFInstruction> instructions = new LinkedList<org.openflow.protocol.interfaces.OFInstruction>();
			OFActionOutput action = OFMessageFactory.createActionOutput(sw.getVersion());
			List<org.openflow.protocol.interfaces.OFAction> actions = new LinkedList<org.openflow.protocol.interfaces.OFAction>();

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

			flowMod.setTableId((byte) 0x0)						//the table which the flow entry should be inserted
			.setCommand(OFFlowModCommand.ADD)
			.setIdleTimeout((short) 0)
			.setHardTimeout((short) 0)					//permanent if idle and hard timeout are zero
			.setPriority((short) 100)
			.setBufferId(bufferId)					//refers to a packet buffered at the switch and sent to the controller
			.setOutGroup(bufferId)					//OFPP_ANY
			.setOutPort((command == OFFlowModCommand.DELETE) ? OFPort.of(outPort) : OFPort.ANY)
			.setMatch(match)
			.setInstructions(instructions)
			.setFlagsWire((short) 0x0001);					//send flow removed message when flow expires or is deleted
			flowMod.setLength(flowMod.computeLength());

		} else {
			flowMod.setMatch(match);
			flowMod.setCookie(LEARNING_SWITCH_COOKIE);
			flowMod.setCommand(command);
			flowMod.setIdleTimeout(IDLE_TIMEOUT_DEFAULT);
			flowMod.setHardTimeout(HARD_TIMEOUT_DEFAULT);
			flowMod.setPriority(PRIORITY_DEFAULT);
			flowMod.setBufferId(bufferId);
			//		flowMod.setOutPort(OFPort.o.of(command == OFFlowModCommand.DELETE ? outPort : OFPortNo.NONE);
			flowMod.setOutPort(OFPort.of(command == OFFlowModCommand.DELETE ? outPort : OFPort.NONE.get()));
			flowMod.setFlagsWire((command == OFFlowModCommand.DELETE ? 0 : (short) (1 << 0))); // OFPFF_SEND_FLOW_REM	

			OFActionOutput action_output = OFMessageFactory.createActionOutput(sw.getVersion());
			action_output.setPort(OFPort.of(outPort)).setMaxLength((short)0xffff);
			flowMod.setActions(Arrays.asList( (org.openflow.protocol.interfaces.OFAction)action_output) );
			flowMod.setLength((short) (org.openflow.protocol.ver1_0.messages.OFFlowMod.MINIMUM_LENGTH + org.openflow.protocol.ver1_0.messages.OFActionOutput.MINIMUM_LENGTH));
		}

		out.add(flowMod);
	}

	/**
	 * Writes an OFPacketOut message to a switch.
	 * 
	 * @param sw 				The switch to write the PacketOut to.
	 * @param packetInMessage 	The corresponding PacketIn.
	 * @param egressPort 		The switchport to output the PacketOut.
	 */
	private void writePacketOutForPacketIn(IOFSwitch sw, 
			OFPacketIn packetInMessage, 
			OFPort egressPort,
			short getInputPort,
			List<OFMessage> out) {

		OFPacketOut packetOutMessage = OFMessageFactory.createPacketOut(sw.getVersion());

		short packetOutLength = ((sw.getVersion() == (byte)0x01)? (short)org.openflow.protocol.ver1_0.messages.OFPacketOut.MINIMUM_LENGTH:
			(short)org.openflow.protocol.ver1_3.messages.OFPacketOut.MINIMUM_LENGTH);

		// Set buffer_id, in_port, actions_len
		packetOutMessage.setBufferId(packetInMessage.getBufferId());

		if ( packetInMessage.isInputPortSupported() ) {
			packetOutMessage.setInputPort(packetInMessage.getInputPort());
		} else {
			packetOutMessage.setInputPort(OFPort.of(getInputPort));
		}

		packetOutMessage.setActionsLength((sw.getVersion() == (byte)0x01)?(short)org.openflow.protocol.ver1_0.messages.OFActionOutput.MINIMUM_LENGTH:
			(short)org.openflow.protocol.ver1_3.messages.OFActionOutput.MINIMUM_LENGTH);

		packetOutLength += (sw.getVersion() == (byte)0x01)?(short)org.openflow.protocol.ver1_0.messages.OFActionOutput.MINIMUM_LENGTH:
			(short)org.openflow.protocol.ver1_3.messages.OFActionOutput.MINIMUM_LENGTH;

		// set actions
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>(1);      
		OFActionOutput action_output = OFMessageFactory.createActionOutput(sw.getVersion());
		//			OFActionOutput action_output = new OFActionOutput();

		action_output.setPort(egressPort).setMaxLength((short)0);
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
	private boolean processPacketInMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> out) {

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

		// Read in packet data headers by using OFMatch
		//		OFMatch match = new OFMatch();
		//		match.loadFromPacket(pi.getPacketData(), pi.getInPort());

		Long sourceMac = null;
		Long destMac = null;
		Integer vlan = null;
		Integer inputPort = null;
		OFMatch match = null;

		if ( pi.isInputPortSupported() ) { // is it OF 0x01 ?? 
			match = (OFMatch) protocol.loadOFMatchFromPacket(conn.getSwitch(), pi.getData(), (short) pi.getInputPort().get());
			inputPort = pi.getInputPort().get();
			sourceMac = Ethernet.toLong(match.getDataLayerSource());
			destMac = Ethernet.toLong(match.getDataLayerDestination());
			vlan = (int) match.getDataLayerVirtualLan();

		} else {
			match = pi.getMatch();
			inputPort = OFMatchUtil.getInt(pi.getMatch(), OFOxmMatchFields.OFB_IN_PORT);
			sourceMac = OFMatchUtil.getEthAsLong(pi.getMatch(), OFOxmMatchFields.OFB_ETH_SRC);
			destMac = OFMatchUtil.getEthAsLong(pi.getMatch(), OFOxmMatchFields.OFB_ETH_DST);
			vlan = OFMatchUtil.getInt(pi.getMatch(), OFOxmMatchFields.OFB_VLAN_VID);
			if(vlan == null) {
				vlan = -1;
			}

		}


		if ((destMac & 0xfffffffffff0L) == 0x0180c2000000L) {
			return true;
		}
		if ((sourceMac & 0x010000000000L) == 0) {
			// If source MAC is a unicast address, learn the port for this MAC/VLAN
			this.addToPortMap(conn.getSwitch(), sourceMac, vlan.shortValue(), inputPort.shortValue());
		}

		// Now output flow-mod and/or packet
		Short outPort = getFromPortMap(conn.getSwitch(), destMac, vlan.shortValue());
		System.out.println("outPort is :" + outPort);

		if (outPort == null) {
			// If we haven't learned the port for the dest MAC/VLAN, flood it
			// Don't flood broadcast packets if the broadcast is disabled.
			// XXX For LearningSwitch this doesn't do much. The sourceMac is removed
			//     from port map whenever a flow expires, so you would still see
			//     a lot of floods.
			this.writePacketOutForPacketIn(conn.getSwitch(), pi, OFPort.FLOOD, inputPort.shortValue(), out);
		} else if (outPort == inputPort.shortValue()) {
			// ignore this packet.
			//            log.trace("ignoring packet that arrived on same port as learned destination:"
			//                    + " switch {} vlan {} dest MAC {} port {}",
			//                    new Object[]{ sw, vlan, HexString.toHexString(destMac), outPort });
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

			if(match.isWildcardsSupported()) {
				match.setWildcardsWire(
						((Integer)conn.getSwitch().getAttribute(IOFSwitch.PROP_FASTWILDCARDS)).intValue()
						& ~OFBFlowWildcard.IN_PORT
						& ~OFBFlowWildcard.DL_VLAN & ~OFBFlowWildcard.DL_SRC & ~OFBFlowWildcard.DL_DST
						& ~OFBFlowWildcard.NW_SRC_MASK & ~OFBFlowWildcard.NW_DST_MASK
						);
			} else {
				// How to set wildcards in OF1.3?
				// does nothing
			}

			this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD, 
					pi.getBufferId(), match, outPort, out);

			if (LEARNING_SWITCH_REVERSE_FLOW) {
				OFMatch.Builder builder = OFMessageFactory.createMatchBuilder(conn.getSwitch().getVersion());

				OFMatch ofMatch = builder.build();

				ofMatch = match;

				if(ofMatch.isDataLayerSourceSupported()) {
				this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD, -1,
						ofMatch
						.setDataLayerSource(match.getDataLayerDestination())
						.setDataLayerDestination(match.getDataLayerSource())
						.setNetworkSource(match.getNetworkDestination())
						.setNetworkDestination(match.getNetworkSource())
						.setTransportSource(match.getTransportDestination())
						.setTransportDestination(match.getTransportSource())
						.setInputPort(OFPort.of(outPort)),
						(short) match.getInputPort().get(),
						out
						);
				} else {
					// how set to reverse flow in OF1.3
					
				}
			}
		}
		return true;
	}

	/**
	 * Initialize this module. As this module processes all PACKET_IN messages,
	 * it registers filter to receive those messages.
	 */
	@Override
	protected void initialize() {

		protocol = getController().getProtocol();

		registerFilter(
				OFMessageType.PACKET_IN,
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						return true;
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
