/**
 *    Copyright 2011, Big Switch Networks, Inc. 
 *    Originally created by David Erickson, Stanford University
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 **/

package etri.sdn.controller.module.forwarding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
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
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U64;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.devicemanager.IDevice;
import etri.sdn.controller.module.devicemanager.IDeviceListener;
import etri.sdn.controller.module.devicemanager.IDeviceService;
import etri.sdn.controller.module.devicemanager.SwitchPort;
import etri.sdn.controller.module.linkdiscovery.NodePortTuple;
import etri.sdn.controller.module.routing.IRoutingDecision;
import etri.sdn.controller.module.routing.IRoutingService;
import etri.sdn.controller.module.routing.Route;
import etri.sdn.controller.module.topologymanager.ITopologyService;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPacket;
import etri.sdn.controller.util.AppCookie;
import etri.sdn.controller.util.Logger;
import etri.sdn.controller.util.OFMessageDamper;
import etri.sdn.controller.util.TimedCache;
//import java.util.EnumSet;

/**
 * Abstract base class for implementing a forwarding module. Forwarding is responsible
 * for programming flows to a switch in response to a policy decision.
 *
 *  @author jshin
 */
public abstract class ForwardingBase extends OFModule implements IDeviceListener {

	protected static int OFMESSAGE_DAMPER_CAPACITY = 50000; // TODO: find sweet spot
	protected static int OFMESSAGE_DAMPER_TIMEOUT = 250; 	// ms 

	public static short FLOWMOD_DEFAULT_IDLE_TIMEOUT = 5; 	// in seconds
	public static short FLOWMOD_DEFAULT_HARD_TIMEOUT = 0; 	// infinite
	public static short FLOWMOD_DEFAULT_PRIORITY = 10;

	protected IDeviceService deviceManager;
	protected IRoutingService routingEngine;
	protected ITopologyService topology;

	protected OFMessageDamper messageDamper;
	
	OFProtocol protocol;

	// for broadcast loop suppression
	protected boolean broadcastCacheFeature = true;
	public final int prime1 = 2633;  												// for hash calculation
	public final static int prime2 = 4357;  										// for hash calculation
	public TimedCache<Long> broadcastCache = new TimedCache<Long>(100, 5*1000);  	// 5 seconds interval;

	// flow-mod - for use in the cookie
	public static final int FORWARDING_APP_ID = 2; // TODO: This must be managed
	// by a global APP_ID class
	public long appCookie = AppCookie.makeCookie(FORWARDING_APP_ID, 0);

	// Comparator for sorting by SwitchCluster
	public Comparator<SwitchPort> clusterIdComparator = new Comparator<SwitchPort>() {
		@Override
		public int compare(SwitchPort d1, SwitchPort d2) {
			Long d1ClusterId = topology.getL2DomainId(d1.getSwitchDPID());
			Long d2ClusterId = topology.getL2DomainId(d2.getSwitchDPID());
			return d1ClusterId.compareTo(d2ClusterId);
		}
	};

	/**
	 * Adds a listener for device manager and registers for PacketIns
	 * and initializes data structure.
	 */
	@Override
	public void initialize() {
		if ( deviceManager != null ) {
			deviceManager.addListener(this);
		}
		
		this.protocol = getController().getProtocol();
		
		HashSet<OFType> set = new HashSet<OFType>();
		set.add(OFType.FLOW_MOD);
		messageDamper = new OFMessageDamper(OFMESSAGE_DAMPER_CAPACITY, 
				set, OFMESSAGE_DAMPER_TIMEOUT);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection sw, MessageContext context) {
		// does nothing
		return true;
	}
	
	protected OFPort getInputPort(OFPacketIn pi) {
		if ( pi == null ) {
			throw new AssertionError("pi cannot refer null");
		}
		try {
			return pi.getInPort();
		} catch ( UnsupportedOperationException e ) {
			try {
				return pi.getMatch().get(MatchField.IN_PORT);
			} catch ( NullPointerException nx ) {
				Logger.debug("Packet-in does not have oxm object for OFB_IN_PORT");
				throw new AssertionError("pi cannot refer null");
			}
		}
	}

	/**
	 * Initializes this module.
	 * 
	 * @param conn the connection to switch
	 * @param cntx the {@link MessageContext}
	 * @param msg the {@link OFMessage} to handle
	 * @param outgoing
	 * 
	 * @return true
	 */
	@Override
	protected boolean handleMessage(Connection conn, MessageContext cntx, OFMessage msg, List<OFMessage> outgoing) {
		
		switch ( msg.getType() ) {
		case PACKET_IN:
			IRoutingDecision decision = null;
			if (cntx != null) {
				decision = (IRoutingDecision) cntx.get(MessageContext.ROUTING_DECISION);
			}

			return this.processPacketInMessage(conn,
					(OFPacketIn) msg,
					decision,
					cntx);
		default:
			break;
		}
		
		return true;
	}

	/**
	 * All subclasses must define this function if they want any specific
	 * forwarding action.
	 * 
	 * @param conn the connection that the packet came in from
	 * @param pi the packet that came in
	 * @param decision any decision made by a policy engine
	 * @param cntx the {@link MessageContext}
	 */
	public abstract boolean
	processPacketInMessage(Connection conn, OFPacketIn pi, IRoutingDecision decision, MessageContext cntx);
	
	@Override
	protected boolean handleDisconnect(Connection conn) {
		// does nothing currently
		return true;
	}
	
	/**
	 * Pushes routes from back to front.
	 * 
	 * @param conn the connection to switch
	 * @param route a route to push
	 * @param match openFlow fields to match on
	 * @param pi packetin
	 * @param pinSwitch the switch of packetin
	 * @param cookie the cookie to set in each flow_mod
	 * @param cntx the {@link MessageContext}
	 * @param reqeustFlowRemovedNotifn true when the switch would send a flow mod 
	 *        removal notification when the flow mod expires
	 * @param doFlush true when the flow mod would be immediately written to the switch
	 * @param flowModCommand flow mod. command to use, e.g. OFFlowMod.OFPFC_ADD,
	 *        OFFlowMod.OFPFC_MODIFY etc.
	 * 
	 * @return true if the source switch is included in this route
	 */
	public boolean pushRoute(
			Connection conn,
			Route route, 
			Match match, 
			OFPacketIn pi,
			long pinSwitch,
			long cookie, 
			MessageContext cntx,
			boolean reqeustFlowRemovedNotifn,
			boolean doFlush,
			OFFlowModCommand   flowModCommand) {

		boolean srcSwitchIncluded = false;
		
		OFFactory fac = OFFactories.getFactory(pi.getVersion());
		
		OFFlowMod.Builder fm = null;
		switch ( flowModCommand ){
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

		fm.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
		.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
//		.setBufferId(OFBufferId.NO_BUFFER)
		.setBufferId(pi.getBufferId())
		.setCookie(U64.of(cookie))
		.setMatch(match)
		.setPriority(FLOWMOD_DEFAULT_PRIORITY)
		.setFlags( EnumSet.noneOf(OFFlowModFlags.class) );
		
		try { 
			fm.setTableId(TableId.ZERO);
		} catch ( UnsupportedOperationException u ) {
			// does nothing
		}
		
		List<NodePortTuple> switchPortList = route.getPath();

		for (int indx = switchPortList.size()-1; fm != null && indx > 0; indx -= 2) {
		
			// indx and indx-1 will always have the same switch dpid.
			long switchDPID = switchPortList.get(indx).getNodeId();
			IOFSwitch sw = controller.getSwitch(switchDPID);
			if (sw == null) {
//				if (log.isWarnEnabled()) {
//					log.warn("Unable to push route, switch at dpid {} " +
//							"not available", switchDPID);
//				}
				return srcSwitchIncluded;
			}

			// fm.setMatch(match);
			
			// set buffer id if it is the source switch
			if (1 == indx) {
				// Set the flag to request flow-mod removal notifications only for the
				// source switch. The removal message is used to maintain the flow cache.
				// Don't set the flag for ARP messages - TODO generalize check
				if ( match.get(MatchField.ETH_TYPE) != EthType.ARP ) {
//					fm.setFlags(EnumSet.of(OFFlowModFlags.SEND_FLOW_REM, OFFlowModFlags.CHECK_OVERLAP));
					fm.setFlags(EnumSet.of(OFFlowModFlags.SEND_FLOW_REM));
				}
			}

			OFPort outPort = switchPortList.get(indx).getPortId();
			OFPort inPort = switchPortList.get(indx-1).getPortId();
			
			Match.Builder fm_match = match.createBuilder();
			
			// copy construct the original match object.
			for ( MatchField mf : match.getMatchFields() ) {
				if ( match.isExact(mf) ) {
					fm_match.setExact(mf, match.get(mf));
				} else {
					fm_match.setMasked(mf, match.get(mf), match.getMasked(mf));
				}
			}
			
			fm_match.setExact(MatchField.IN_PORT, inPort);
			
			List<OFAction> actions = new ArrayList<OFAction>();
			OFActionOutput.Builder action_output = fac.actions().buildOutput(); 
			action_output.setMaxLen((short)0xffff).setPort(outPort);
			actions.add(action_output.build());
			
			try {	
				fm.setActions( actions );
			} catch ( UnsupportedOperationException u ) {
				List<OFInstruction> instructions = new ArrayList<OFInstruction>();
				OFInstructionApplyActions.Builder instruction = fac.instructions().buildApplyActions();
				instruction.setActions(actions);
				instructions.add(instruction.build());

				fm.setInstructions( instructions );
			}
			
			fm.setMatch(fm_match.build());
			
			fm.setBufferId(OFBufferId.NO_BUFFER); //?

			try {
//				counterStore.updatePktOutFMCounterStore(sw, fm);
//				if (log.isTraceEnabled()) {
//					log.trace("Pushing Route flowmod routeIndx={} " + 
//							"sw={} inPort={} outPort={}",
//							new Object[] {indx, sw,
//							fm.getMatch().getInputPort(), outPort });
//				}
				
				// write flow-mod object to switch.
				messageDamper.write(sw.getConnection(), fm.build());

				// Push the packet out the source switch
				if (sw.getId() == pinSwitch) {
					// TODO: Instead of doing a packetOut here we could also 
					// send a flowMod with bufferId set.... 
					pushPacket(conn, match, pi, outPort, cntx);
					srcSwitchIncluded = true;
				}
				
			} catch (IOException e) {
				Logger.stderr("Failure writing flow mod" + e.toString());
			}
		}

		return srcSwitchIncluded;
	}


	/**
	 * Pushes a packet-out to a switch. The assumption here is that the packet-in 
	 * was also generated from the same switch. Thus, if the input port of the
	 * packet-in and the outport are the same, the function will not push the packet-out.
	 * 
	 * @param conn the connection to the switch that generated the packet-in, and from which packet-out is sent
	 * @param match the {@link OFMatch}
	 * @param pi packetin
	 * @param outPort the output port
	 * @param cntx the {@link MessageContext}
	 */
	protected void pushPacket(Connection conn, Match match, OFPacketIn pi, 
			OFPort outPort, MessageContext cntx) {

		if (pi == null) {
			return;
		}
		OFPort inPort = getInputPort(pi);
		
		if ( outPort.equals(inPort) ){
			Logger.stdout("Packet out not sent as the outport matches inport. " + pi.toString());
			return;
		}

		// The assumption here is (sw) is the switch that generated the packet-in. 
		// If the input port is the same as output port, then the packet-out should be ignored.
		if ( inPort.equals(outPort) ) {
			return;
		}
		
		OFFactory fac = OFFactories.getFactory(pi.getVersion());

		// set actions
		OFActionOutput.Builder action_output = fac.actions().buildOutput();
		List<OFAction> actions = new ArrayList<OFAction>();

		action_output.setMaxLen((short)0xffff);
		action_output.setPort(outPort);
		actions.add(action_output.build());
		
		OFPacketOut.Builder po = fac.buildPacketOut().setActions(actions);

		// If the switch doens't support buffering set the buffer id to be none
		// otherwise it'll be the the buffer id of the PacketIn
		if ( protocol.getSwitchInformation(conn.getSwitch()).getBuffers() == 0 ) {
			po
			.setBufferId( OFBufferId.NO_BUFFER )
			.setData( pi.getData() );
		} else {
			po.setBufferId(pi.getBufferId());
		}

		po.setInPort(inPort);
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
			messageDamper.write(conn, po.build());
		} catch (IOException e) {
			Logger.stderr("Failure writing packet out" + e.toString());
		}
	}

	/**
	 * Pushes a packet-out to a switch. If bufferId != OFP_NO_BUFFER we 
	 * assume that the packetOut switch is the same as the packetIn switch
	 * and we will use the bufferId. 
	 * Caller needs to make sure that inPort and outPort differs.
	 * 
	 * @param conn the connection to switch
	 * @param packet a packet data to send
	 * @param sw the switch from which packet-out is sent
	 * @param bufferId the bufferId
	 * @param inPort the input port
	 * @param outPort the output port
	 * @param cntx the {@link MessageContext}
	 */
	public void pushPacket(
			Connection conn,
			IPacket packet, 
			IOFSwitch sw,
			int bufferId,
			short inPort,
			short outPort, 
			MessageContext cntx) {
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		OFPacketOut.Builder po = fac.buildPacketOut();

		// set actions
		OFActionOutput.Builder action_output = fac.actions().buildOutput();
		List<OFAction> actions = new ArrayList<OFAction>();

		action_output
		.setMaxLen((short)0xffff)
		.setPort(OFPort.of(outPort));
		actions.add(action_output.build());
		
		po
		.setActions(actions)
		.setBufferId(OFBufferId.of(bufferId))
		.setInPort(OFPort.of(inPort));

		// set data - only if buffer_id == -1
		if ( po.getBufferId() == OFBufferId.NO_BUFFER ) {
			if (packet == null) {
//				log.error("BufferId is not set and packet data is null. " +
//						"Cannot send packetOut. " +
//						"srcSwitch={} inPort={} outPort={}",
//						new Object[] {sw, inPort, outPort});
				return;
			}
			byte[] packetData = packet.serialize();
			po.setData(packetData);
		}
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
			messageDamper.write(conn, po.build());
		} catch (IOException e) {
			Logger.stderr("Failure writing packet out" + e.toString());
		}
	}


	/**
	 * Writes packet-out message to switch with output actions to one or more
	 * output ports with inPort/outPorts passed in.
	 * 
	 * @param packetData the packet to write
	 * @param conn the connection to switch
	 * @param inPort input port
	 * @param outPorts output port
	 * @param cntx the {@link MessageContext}
	 */
	public void packetOutMultiPort(
			byte[] packetData,
			Connection conn,
			short inPort,
			Set<Integer> outPorts,
			MessageContext cntx) {
		
		OFFactory fac = OFFactories.getFactory(conn.getSwitch().getVersion());
		
		//setting actions
		List<OFAction> actions = new ArrayList<OFAction>();

		Iterator<Integer> j = outPorts.iterator();

		while (j.hasNext())
		{
			OFActionOutput.Builder action_output = fac.actions().buildOutput();
			action_output
			.setPort(OFPort.of(j.next()))
			.setMaxLen(0);
			actions.add(action_output.build());
			
			action_output.setPort(OFPort.of(j.next().shortValue()));
			action_output.setMaxLen((short)0);
			actions.add(action_output.build());
		}

		OFPacketOut.Builder po = fac.buildPacketOut();
		po
		.setActions(actions)
		.setBufferId(OFBufferId.NO_BUFFER)
		.setInPort(OFPort.of(inPort))
		.setData( packetData );
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
//			if (log.isTraceEnabled()) {
//				log.trace("write broadcast packet on switch-id={} " + 
//						"interfaces={} packet-out={}",
//						new Object[] {sw.getId(), outPorts, po});
//			}
			messageDamper.write(conn, po.build());

		} catch (IOException e) {
			Logger.stderr("Failure writing packet out" + e.toString());
		}
	}

	/** 
	 * Accepts a PacketIn instead of raw packet data. Note that the inPort
	 * and switch can be different than the packet in switch/port.
	 * 
	 * @param pi packetin
	 * @param conn the connection to switch
	 * @param inPort the input port
	 * @param outPorts a set of output port
	 * @param cntx the {@link MessageContext}
	 * 
	 * @see etri.sdn.controller.module.forwarding.ForwardingBase#packetOutMultiPort
	 * 
	 */
	public void packetOutMultiPort(
			OFPacketIn pi,
			Connection conn,
			short inPort,
			Set<Integer> outPorts,
			MessageContext cntx) {

		packetOutMultiPort(pi.getData(), conn, inPort, outPorts, cntx);
	}

	/** 
	 * Accepts an IPacket instead of raw packet data. Note that the inPort
	 * and switch can be different than the packet in switch/port.
	 * 
	 * @param packet a packet data to send 
	 * @param conn the connection to switch
	 * @param inPort the input port
	 * @param outPorts a set of output port
	 * @param cntx the {@link MessageContext}
	 * 
	 * @see etri.sdn.controller.module.forwarding.ForwardingBase#packetOutMultiPort
	 */
	public void packetOutMultiPort(
			IPacket packet,
			Connection conn,
			short inPort,
			Set<Integer> outPorts,
			MessageContext cntx) {
		packetOutMultiPort(packet.serialize(), conn, inPort, outPorts, cntx);
	}

	/**
	 * Updates the broadcast cache of the switch.
	 * 
	 * @param conn the connection to switch
	 * @param pi packetin
	 * @param cntx the {@link MessageContext}
	 * 
	 * @return true if successfully updated, false otherwise
	 */
	protected boolean isInBroadcastCache(Connection conn, OFPacketIn pi,
			MessageContext cntx) {
		// Get the cluster id of the switch.
		// Get the hash of the Ethernet packet.
		//		if (sw == null) return true;  

		// If the feature is disabled, always return false;
		if (!broadcastCacheFeature) return false;

		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);

		Long broadcastHash;
		OFPort inPort = getInputPort(pi);
		broadcastHash = topology.getL2DomainId(conn.getSwitch().getId()) * prime1 +
				inPort.getPortNumber() * prime2 + eth.hashCode();
		if (broadcastCache.update(broadcastHash)) {
			conn.getSwitch().updateBroadcastCache(broadcastHash, inPort);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates the broadcast cache of the switch.
	 * 
	 * @param conn the connection to switch
	 * @param pi packetin
	 * @param cntx the {@link MessageContext}
	 * 
	 * @return true if successfully updated, false otherwise
	 */
	protected boolean isInSwitchBroadcastCache(Connection conn, OFPacketIn pi, MessageContext cntx) {

		// If the feature is disabled, always return false;
		if (!broadcastCacheFeature) return false;

		// Get the hash of the Ethernet packet.
		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);

		OFPort inPort = getInputPort(pi);
		
		long hash =  inPort.getPortNumber() * prime2 + eth.hashCode();

		// some FORWARD_OR_FLOOD packets are unicast with unknown destination mac
		return conn.getSwitch().updateBroadcastCache(hash, inPort);
	}

	/**
	 * Creates a flow mod to block the packet based on the given MAC 
	 * address and the source switch. 
	 * 
	 * @param sw_tup the input port number
	 * @param host_mac the MAC address to block
	 * @param hardTimeout a timeout value
	 * @param cookie the cookie to set in each flow_mod
	 * 
	 * @return true if successfully created, false otherwise 
	 */
	public boolean blockHost(SwitchPort sw_tup, long host_mac,
			short hardTimeout, long cookie) {

		if (sw_tup == null) {
			return false;
		}

		IOFSwitch sw = this.controller.getSwitch(sw_tup.getSwitchDPID());
		if (sw == null) return false;
		OFPort inputPort = sw_tup.getPort();
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		
		OFFlowAdd.Builder fm = fac.buildFlowAdd();
		Match.Builder match = fac.buildMatch();
		
		match.setExact(MatchField.ETH_SRC, MacAddress.of(host_mac));
		match.setExact(MatchField.IN_PORT, inputPort);
		
		try {
			fm.setInstructions(Collections.<OFInstruction>emptyList());
		} catch ( UnsupportedOperationException u ) {
			fm.setActions(Collections.<OFAction>emptyList());
		}
		
		try {
			fm
			.setCookie(U64.of(cookie))
			.setHardTimeout(hardTimeout)
			.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
			.setBufferId(OFBufferId.NO_BUFFER)
			.setMatch( match.build() )
			.setTableId(TableId.of(0));
		} catch ( UnsupportedOperationException u ) {
			// does nothing. possibly from setTableId() call
		}
		
//		log.debug("write drop flow-mod sw={} match={} flow-mod={}",
//				new Object[] { sw, match, fm });
		try {
			messageDamper.write(sw.getConnection(), fm.build());

		} catch (IOException e) {
			Logger.stderr("Failure writing drop flow mod " + e.toString());
		}
		return true;

	}

	@Override
	public void deviceAdded(IDevice device) {
		// NOOP
	}

	@Override
	public void deviceRemoved(IDevice device) {
		// NOOP
	}

	@Override
	public void deviceMoved(IDevice device) {
		
	}

	@Override
	public void deviceIPV4AddrChanged(IDevice device) {

	}

	@Override
	public void deviceVlanChanged(IDevice device) {

	}
}
