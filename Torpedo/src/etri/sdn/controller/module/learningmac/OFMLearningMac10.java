package etri.sdn.controller.module.learningmac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.ver1_0.messages.OFActionOutput;
import org.openflow.protocol.ver1_0.messages.OFFlowMod;
import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.protocol.ver1_0.messages.OFPacketIn;
import org.openflow.protocol.ver1_0.messages.OFPacketOut;
import org.openflow.protocol.ver1_0.types.OFFlowWildcards;
import org.openflow.protocol.ver1_0.types.OFFlowModCommand;
import org.openflow.protocol.ver1_0.types.OFPortNo;
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
 * @author bjlee
 *
 */
public final class OFMLearningMac10 extends OFModule {

	/**
	 * Table to save learning result.
	 */
	private Map<IOFSwitch, Map<MacVlanPair, Short>> macVlanToSwitchPortMap =
		new ConcurrentHashMap<IOFSwitch, Map<MacVlanPair, Short>>();

	private OFProtocol version_adaptor_10;

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
	public OFMLearningMac10() {
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
	private void writeFlowMod(IOFSwitch sw, short command, int bufferId,
			OFMatch match, short outPort, List<OFMessage> out) {
		// from openflow 1.0 spec - need to set these on a struct ofp_flow_mod:
		// struct ofp_flow_mod {
		//    struct ofp_header header;
		//    struct ofp_match match; /* Fields to match */
		//    uint64_t cookie; /* Opaque controller-issued identifier. */
		//
		//    /* Flow actions. */
		//    uint16_t command; /* One of OFPFC_*. */
		//    uint16_t idle_timeout; /* Idle time before discarding (seconds). */
		//    uint16_t hard_timeout; /* Max time before discarding (seconds). */
		//    uint16_t priority; /* Priority level of flow entry. */
		//    uint32_t buffer_id; /* Buffered packet to apply to (or -1).
		//                           Not meaningful for OFPFC_DELETE*. */
		//    uint16_t out_port; /* For OFPFC_DELETE* commands, require
		//                          matching entries to include this as an
		//                          output port. A value of OFPP_NONE
		//                          indicates no restriction. */
		//    uint16_t flags; /* One of OFPFF_*. */
		//    struct ofp_action_header actions[0]; /* The action length is inferred
		//                                            from the length field in the
		//                                            header. */
		//    };

		OFFlowMod flowMod = new OFFlowMod();
		flowMod.setMatch(match);
		flowMod.setCookie(LEARNING_SWITCH_COOKIE);
		flowMod.setCommand(OFFlowModCommand.valueOf(command));
		flowMod.setIdleTimeout(IDLE_TIMEOUT_DEFAULT);
		flowMod.setHardTimeout(HARD_TIMEOUT_DEFAULT);
		flowMod.setPriority(PRIORITY_DEFAULT);
		flowMod.setBufferId(bufferId);
		flowMod.setOutPort(OFPort.of((command == OFFlowModCommand.DELETE.getValue()) ? outPort : OFPortNo.NONE.getValue()));
		flowMod.setFlagsWire((command == OFFlowModCommand.DELETE.getValue()) ? 0 : (short) (1 << 0)); // OFPFF_SEND_FLOW_REM

		// set the ofp_action_header/out actions:
		// from the openflow 1.0 spec: need to set these on a struct ofp_action_output:
		// uint16_t type; /* OFPAT_OUTPUT. */
		// uint16_t len; /* Length is 8. */
		// uint16_t port; /* Output port. */
		// uint16_t max_len; /* Max length to send to controller. */
		// type/len are set because it is OFActionOutput,
		// and port, max_len are arguments to this constructor
		OFActionOutput action_output = new OFActionOutput();
		action_output.setPort(OFPort.of(outPort)).setMaxLength((short)0xffff);
		flowMod.setActions(Arrays.asList( (org.openflow.protocol.interfaces.OFAction)action_output) );
		
		flowMod.setLength((short) (OFFlowMod.MINIMUM_LENGTH + OFActionOutput.MINIMUM_LENGTH));

		// TODO: support for counter store.
		//		counterStore.updatePktOutFMCounterStore(sw, flowMod);

		// and write it out
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
			short egressPort,
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
		packetOutMessage.setInputPort(packetInMessage.getInputPort());
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
		OFMatch match = (OFMatch) version_adaptor_10.loadOFMatchFromPacket(conn.getSwitch(), pi.getData(), (short) pi.getInputPort().get());
		Long sourceMac = Ethernet.toLong(match.getDataLayerSource());
		Long destMac = Ethernet.toLong(match.getDataLayerDestination());
		Short vlan = match.getDataLayerVirtualLan();
		
		if ((destMac & 0xfffffffffff0L) == 0x0180c2000000L) {
			return true;
		}
		if ((sourceMac & 0x010000000000L) == 0) {
			// If source MAC is a unicast address, learn the port for this MAC/VLAN
			this.addToPortMap(conn.getSwitch(), sourceMac, vlan, (short) pi.getInputPort().get());
		}

		// Now output flow-mod and/or packet
		Short outPort = getFromPortMap(conn.getSwitch(), destMac, vlan);
		if (outPort == null) {
			// If we haven't learned the port for the dest MAC/VLAN, flood it
			// Don't flood broadcast packets if the broadcast is disabled.
			// XXX For LearningSwitch this doesn't do much. The sourceMac is removed
			//     from port map whenever a flow expires, so you would still see
			//     a lot of floods.
			this.writePacketOutForPacketIn(conn.getSwitch(), pi, OFPortNo.FLOOD.getValue(), out);
		} else if (outPort == match.getInputPort().get()) {
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
			match.setWildcardsWire(
					((Integer)conn.getSwitch().getAttribute(IOFSwitch.PROP_FASTWILDCARDS)).intValue()
					& ~OFFlowWildcards.IN_PORT
					& ~OFFlowWildcards.DL_VLAN & ~OFFlowWildcards.DL_SRC & ~OFFlowWildcards.DL_DST
					& ~OFFlowWildcards.NW_SRC_MASK & ~OFFlowWildcards.NW_DST_MASK
			);
			this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD.getValue(), 
					pi.getBufferId(), match, outPort, out);
			if (LEARNING_SWITCH_REVERSE_FLOW) {
				this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD.getValue(), -1, 
						new OFMatch(match)
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
		
		version_adaptor_10 = getController().getProtocol();
		
		registerFilter(
				OFMessageType.PACKET_IN,
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						return m.getVersion() == (byte)0x01;
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
