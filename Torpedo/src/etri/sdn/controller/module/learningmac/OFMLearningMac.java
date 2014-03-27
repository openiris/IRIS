package etri.sdn.controller.module.learningmac;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFFlowModCommand;
import org.projectfloodlight.openflow.protocol.OFFlowModFlags;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionApplyActions;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.OFVlanVidMatch;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U64;
import org.projectfloodlight.openflow.types.VlanVid;
import org.projectfloodlight.openflow.util.LRULinkedHashMap;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.Logger;

/**
 * MAC Learning Module. 
 * Modified the original LearningMac class of Floodlight.
 * 
 * @author labry
 *
 */
public final class OFMLearningMac extends OFModule {

	/**
	 * Table to save learning result.
	 */
	private Map<IOFSwitch, Map<MacVlanPair, OFPort>> macVlanToSwitchPortMap =
			new ConcurrentHashMap<IOFSwitch, Map<MacVlanPair, OFPort>>();

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
	public OFMLearningMac() {
		// does nothing
	}

	/**
	 * Adds a host to the MAC/VLAN->SwitchPort mapping
	 * @param sw The switch to add the mapping to
	 * @param mac The MAC address of the host to add
	 * @param vlan The VLAN that the host is on
	 * @param portVal The switchport that the host is on
	 */
	protected void addToPortMap(IOFSwitch sw, MacAddress mac, VlanVid vlan, OFPort portVal) {
		Map<MacVlanPair,OFPort> swMap = macVlanToSwitchPortMap.get(sw);

		if ( vlan == null ) {
			// OFMatch.loadFromPacket sets VLAN ID to 0xffff if the packet contains no VLAN tag;
			// for our purposes that is equivalent to the default VLAN ID 0
			vlan = VlanVid.ZERO;
		}

		if (swMap == null) {
			// May be accessed by REST API so we need to make it thread safe
			//			swMap = new ConcurrentHashMap<MacVlanPair,Short>();
			swMap = Collections.synchronizedMap(new LRULinkedHashMap<MacVlanPair,OFPort>(MAX_MACS_PER_SWITCH));
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
	protected void removeFromPortMap(IOFSwitch sw, MacAddress mac, VlanVid vlan) {
		if ( vlan == null ) {
			vlan = VlanVid.ZERO;
		}

		Map<MacVlanPair,OFPort> swMap = macVlanToSwitchPortMap.get(sw);
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
	public OFPort getFromPortMap(IOFSwitch sw, MacAddress mac, VlanVid vlan) {
		if ( vlan == null ) {
			vlan = VlanVid.ZERO;
		}

		Map<MacVlanPair,OFPort> swMap = macVlanToSwitchPortMap.get(sw);
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
		Map<MacVlanPair, OFPort> swMap = macVlanToSwitchPortMap.get(sw);
		if (swMap != null)
			swMap.clear();
	}

	/**
	 * Get all the mappings between MAC/VLAN to switch port.
	 * 
	 * @return	Map<IOFSwitch, Map<MacVlanPair,Short>> object
	 */
	public synchronized Map<IOFSwitch, Map<MacVlanPair,OFPort>> getTable() {
		return macVlanToSwitchPortMap;
	}

	/**
	 * Writes a OFFlowMod to a switch.
	 * @param sw The switch tow rite the flowmod to.
	 * @param command The FlowMod actions (add, delete, etc).
	 * @param ofBufferId The buffer ID if the switch has buffered the packet.
	 * @param match The OFMatch structure to write.
	 * @param outPort The switch port to output it to.
	 */
	private void writeFlowMod(IOFSwitch sw, OFFlowModCommand command, OFBufferId ofBufferId,
			Match match, OFPort outPort, List<OFMessage> out) {
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		OFFlowMod.Builder fm = null;
		switch ( command ){
		case ADD:
			fm = fac.buildFlowAdd();
			break;
		case DELETE:
			fm = fac.buildFlowDelete();
			break;
		case MODIFY:
			fm = fac.buildFlowModify();
			break;
		case DELETE_STRICT:
			fm = fac.buildFlowDeleteStrict();
			break;
		case MODIFY_STRICT:
			fm = fac.buildFlowModifyStrict();
			break;
		}
		
		List<OFAction> actions = new LinkedList<OFAction>();
		OFActionOutput.Builder action = fac.actions().buildOutput();
		action
		.setPort(outPort)
		.setMaxLen(0xffff);
		actions.add(action.build());
		
		try {
			fm
			.setCookie(U64.of(LEARNING_SWITCH_COOKIE))
			.setIdleTimeout(IDLE_TIMEOUT_DEFAULT)
			.setHardTimeout(HARD_TIMEOUT_DEFAULT)
			.setPriority(PRIORITY_DEFAULT)
			.setBufferId(ofBufferId)
			.setOutPort((command != OFFlowModCommand.DELETE)?outPort:OFPort.ANY /*for 1.0, this is NONE */)
			.setMatch(match)
			.setFlags((command != OFFlowModCommand.DELETE)?EnumSet.of(OFFlowModFlags.SEND_FLOW_REM):EnumSet.noneOf(OFFlowModFlags.class))
			.setActions(actions);
		} catch ( UnsupportedOperationException u ) {
			// probably from setActions() method
			List<OFInstruction> instructions = new LinkedList<OFInstruction>();
			OFInstructionApplyActions.Builder instruction = fac.instructions().buildApplyActions();
			instructions.add( instruction.setActions(actions).build() );
			
			fm
			.setInstructions( instructions )
			.setTableId(TableId.ZERO);
		}

		out.add(fm.build());
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
			OFPort getInputPort,
			List<OFMessage> out) {
		
		OFFactory fac = OFFactories.getFactory(packetInMessage.getVersion());
		OFPacketOut.Builder po = fac.buildPacketOut();
		
		List<OFAction> actions = new LinkedList<OFAction>();
		OFActionOutput.Builder action_output = fac.actions().buildOutput();
		actions.add( action_output.setPort(egressPort).setMaxLen(0xffff).build());
		
		po
		.setBufferId(packetInMessage.getBufferId())
		.setInPort(getInputPort)
		.setActions(actions);
		
		if ( po.getBufferId() == OFBufferId.NO_BUFFER ) {
			po.setData( packetInMessage.getData() );
		}

		// TODO: counter store support
		//		counterStore.updatePktOutFMCounterStore(sw, packetOutMessage);
		out.add(po.build());
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
	 * @param conn		Connection object
	 * @param context 	MessageContext object
	 * @param msg		OFMessage object (packet-in)
	 * @param out		List of outgoing messages to switch
	 * @return			true of correctly processed, false otherwise
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
		
		Match match = null;
		try { 
			match = pi.getMatch();
		} catch ( UnsupportedOperationException u ) {
			match = this.protocol.loadOFMatchFromPacket(conn.getSwitch(), pi.getData(), pi.getInPort(), true);
		}
		
		OFPort inputPort = match.get(MatchField.IN_PORT);
		MacAddress sourceMac = match.get(MatchField.ETH_SRC);
		MacAddress destMac = match.get(MatchField.ETH_DST);
		EthType etherType = match.get(MatchField.ETH_TYPE);
		OFVlanVidMatch vm = match.get(MatchField.VLAN_VID);
		VlanVid vlan = (vm != null)?vm.getVlanVid():null;
		
		Match.Builder target = match.createBuilder();
		if ( inputPort != null ) {
			target.setExact(MatchField.IN_PORT, inputPort);
		}
		if ( etherType != null ) {
			target.setExact(MatchField.ETH_TYPE, etherType);
		}
		if ( vlan != null ) {
			target.setExact(MatchField.VLAN_VID, vm);
		}
		if ( sourceMac != null ) {
			target.setExact(MatchField.ETH_SRC, sourceMac);
		}
		if ( destMac != null ) {
			target.setExact(MatchField.ETH_DST, destMac);
		}
		
		match = target.build();

		if ( destMac != null && (destMac.getLong() & 0xfffffffffff0L) == 0x0180c2000000L ) {
			// what is this?
			return true;
		}

		if ( sourceMac != null && ((sourceMac.getLong() & 0x010000000000L) == 0) ) {
			// If source MAC is a unicast address, learn the port for this MAC/VLAN
			this.addToPortMap(conn.getSwitch(), sourceMac, vlan, inputPort);
		}

		// Now output flow-mod and/or packet
		OFPort outPort = getFromPortMap(conn.getSwitch(), destMac, vlan);

		if (outPort == null) {
			// If we haven't learned the port for the dest MAC/VLAN, flood it
			// Don't flood broadcast packets if the broadcast is disabled.
			// XXX For LearningSwitch this doesn't do much. The sourceMac is removed
			//     from port map whenever a flow expires, so you would still see
			//     a lot of floods.
			this.writePacketOutForPacketIn(conn.getSwitch(), pi, OFPort.FLOOD, inputPort, out);
		} else if ( outPort.equals(inputPort) ) {
			// ignore this packet.
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
			
			this.writePacketOutForPacketIn(conn.getSwitch(), pi, outPort, inputPort, out);

			// setting buffer id and do not write packet out cause some 
			// initial ping messages dropped for OF1.3 switches.
			this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD, 
					OFBufferId.NO_BUFFER/*pi.getBufferId()*/, match, outPort, out);

			if (LEARNING_SWITCH_REVERSE_FLOW) {
				
				Match.Builder reverse = match.createBuilder();
				
				if ( inputPort != null ) {
					reverse.setExact(MatchField.IN_PORT, inputPort);
				}
				if ( etherType != null ) {
					reverse.setExact(MatchField.ETH_TYPE, etherType);
				}
				if ( vlan != null ) {
					reverse.setExact(MatchField.VLAN_VID, vm);
				}
				if ( sourceMac != null ) {
					reverse.setExact(MatchField.ETH_DST, sourceMac);
				}
				if ( reverse != null ) {
					target.setExact(MatchField.ETH_SRC, destMac);
				}
				
				this.writeFlowMod(conn.getSwitch(), OFFlowModCommand.ADD, OFBufferId.NO_BUFFER,
						reverse.build(), inputPort, out );
			}
		}
		return true;
	}

	public final byte[] longToBytes(long eth) {
	    byte[] ethAddress = new byte[ 6 ];

	    ethAddress[0] = (byte)(eth >>> 40);
	    ethAddress[1] = (byte)(eth >>> 32);
	    ethAddress[2] = (byte)(eth >>> 24);
	    ethAddress[3] = (byte)(eth >>> 16);
	    ethAddress[4] = (byte)(eth >>>  8);
	    ethAddress[5] = (byte)(eth >>>  0);

	    return ethAddress;
	}
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		// no service implemented.
		return Collections.emptyList();
	}
	
	/**
	 * Initialize this module. As this module processes all PACKET_IN messages,
	 * it registers filter to receive those messages.
	 */
	@Override
	protected void initialize() {

		this.protocol = getController().getProtocol();

		registerFilter(
				OFType.PACKET_IN,
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
