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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openflow.protocol.OFBFlowWildcard;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.protocol.interfaces.OFAction;
import org.openflow.protocol.interfaces.OFActionOutput;
import org.openflow.protocol.interfaces.OFFlowMod;
import org.openflow.protocol.interfaces.OFFlowModCommand;
import org.openflow.protocol.interfaces.OFFlowModFlags;
import org.openflow.protocol.interfaces.OFInstruction;
import org.openflow.protocol.interfaces.OFInstructionApplyActions;
import org.openflow.protocol.interfaces.OFInstructionType;
import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.interfaces.OFOxm;
import org.openflow.protocol.interfaces.OFOxmClass;
import org.openflow.protocol.interfaces.OFOxmMatchFields;
import org.openflow.protocol.interfaces.OFPacketIn;
import org.openflow.protocol.interfaces.OFPacketOut;

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
import etri.sdn.controller.protocol.packet.IPv4;
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
	protected static int OFMESSAGE_DAMPER_TIMEOUT = 250; // ms 

	public static short FLOWMOD_DEFAULT_IDLE_TIMEOUT = 5; // in seconds
	public static short FLOWMOD_DEFAULT_HARD_TIMEOUT = 0; // infinite

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
		
		HashSet<OFMessageType> set = new HashSet<OFMessageType>();
		set.add(OFMessageType.FLOW_MOD);
		messageDamper = new OFMessageDamper(OFMESSAGE_DAMPER_CAPACITY, 
				set, OFMESSAGE_DAMPER_TIMEOUT);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection sw, MessageContext context) {
		// does nothing
		return true;
	}
	
	protected int getInputPort(OFPacketIn pi) {
		if ( pi == null ) {
			throw new AssertionError("pi cannot refer null");
		}
		if ( pi.isInputPortSupported() ) {
			return pi.getInputPort().get();
		}
		else {
			OFOxm oxm = pi.getMatch().getOxmFromIndex(OFOxmMatchFields.OFB_IN_PORT);
			if ( oxm == null ) {
				Logger.debug("Packet-in does not have oxm object for OFB_IN_PORT");
				throw new AssertionError("pi cannot refer null");
			}
			return ByteBuffer.wrap(oxm.getData()).getInt();
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
	 * @param wildcard_hints wildcard information as integer
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
			OFMatch match, 
			Integer wildcard_hints,
			OFPacketIn pi,
			long pinSwitch,
			long cookie, 
			MessageContext cntx,
			boolean reqeustFlowRemovedNotifn,
			boolean doFlush,
			OFFlowModCommand   flowModCommand) {

		boolean srcSwitchIncluded = false;
		
		OFFlowMod fm = OFMessageFactory.createFlowMod(pi.getVersion());

		List<OFAction> actions = new ArrayList<OFAction>();
		OFActionOutput action_output = OFMessageFactory.createActionOutput(pi.getVersion());
		action_output.setMaxLength((short)0xffff);
		action_output.setLength(action_output.computeLength());
		actions.add(action_output);

		fm.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
		.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
		.setBufferId(0xffffffff /* OFP_NO_BUFFER */)
		.setCookie(cookie)
		.setCommand(flowModCommand)
		.setMatch(match)
		.setFlags(OFFlowModFlags.SEND_FLOW_REM);

		if ( fm.isTableIdSupported() ) {
			fm.setTableId( (byte) 0x0 );
		}

		if ( fm.isInstructionsSupported() ) {
			List<OFInstruction> instructions = new ArrayList<OFInstruction>();
			OFInstructionApplyActions instruction = OFMessageFactory.createInstructionApplyActions(pi.getVersion());
			instruction.setActions(actions);
			instruction.setLength(instruction.computeLength());
			instructions.add(instruction);

			fm.setInstructions( instructions );
		} else {
			fm.setActions( actions );
		}

		fm.setLength( fm.computeLength() );
		
		List<NodePortTuple> switchPortList = route.getPath();

//		for (int indx = switchPortList.size()-1; indx > 0; indx -= 2) {
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

			// set the match.
			if (match.isWildcardsSupported()){
				fm.setMatch(wildcard(match, sw, wildcard_hints));
			} else {
				fm.setMatch(match);
			}

			// set buffer id if it is the source switch
			if (1 == indx) {
				// Set the flag to request flow-mod removal notifications only for the
				// source switch. The removal message is used to maintain the flow cache.
				// Don't set the flag for ARP messages - TODO generalize check
				if ((reqeustFlowRemovedNotifn)
						&& (match.getDataLayerType() != Ethernet.TYPE_ARP)) {
					fm.setFlags(OFFlowModFlags.SEND_FLOW_REM);
					if ( match.isWildcardsSupported() ) 
						match.setWildcards(fm.getMatch().getWildcards());
				}
			}

			short outPort = switchPortList.get(indx).getPortId();
			short inPort = switchPortList.get(indx-1).getPortId();
			// set input and output ports on the switch
			OFMatch fm_match = fm.getMatch();
			if ( fm_match.isInputPortSupported() ) {
				fm_match.setInputPort(OFPort.of(inPort));
			} else {
				OFOxm oxm = fm_match.getOxmFromIndex(OFOxmMatchFields.OFB_IN_PORT);
				if ( oxm == null ) {
					List<OFOxm> oxms = fm_match.getOxmFields();
					oxm = OFMessageFactory.createOxm(pi.getVersion());
					oxm.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
					oxm.setBitmask((byte)0);
					oxm.setField((byte)0);		// OFB_IN_PORT;
					oxm.setData(ByteBuffer.allocate(4).putInt(inPort).array());
					oxms.add(oxm);
					fm_match.setOxmFields( oxms );
					fm.setLength( fm.computeLength() );
				}
				else {
					oxm.setData(ByteBuffer.allocate(4).putInt(inPort).array());
				}
			}
			
			if ( fm.isActionsSupported() ) {
				List<OFAction> fm_actions = fm.getActions();
				OFAction action = fm_actions.get(0);
				((OFActionOutput)action).setPort(OFPort.of(outPort));
			} else {
				List<OFInstruction> fm_instructions = fm.getInstructions();
				OFInstruction one = fm_instructions.get(0);
				if ( one.getType() == OFInstructionType.APPLY_ACTIONS ) {
					OFInstructionApplyActions apply = (OFInstructionApplyActions) one;
					List<OFAction> inst_actions = apply.getActions();
					if ( inst_actions != null && !inst_actions.isEmpty() ) {
						OFAction action = inst_actions.get(0);
						((OFActionOutput)action).setPort(OFPort.of(outPort));
					}
				}
			}

			try {
//				counterStore.updatePktOutFMCounterStore(sw, fm);
//				if (log.isTraceEnabled()) {
//					log.trace("Pushing Route flowmod routeIndx={} " + 
//							"sw={} inPort={} outPort={}",
//							new Object[] {indx, sw,
//							fm.getMatch().getInputPort(), outPort });
//				}
				messageDamper.write(sw.getConnection(), fm);
//				if (doFlush) {
//					sw.flush();
//				}

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

			// clone the OFFlowMod object
			fm = fm.dup();
		}

		return srcSwitchIncluded;
	}

	/**
	 * Sets wildcards if wildcard_hints is null.
	 * 
	 * @param match the {@link OFMatch}
	 * @param sw the switch instance
	 * @param wildcard_hints wildcard information as integer
	 * 
	 * @return a clone of {@link OFMatch} instance
	 */
	protected OFMatch wildcard(OFMatch match, IOFSwitch sw, Integer wildcard_hints) {
		if (wildcard_hints != null && match.isWildcardsSupported() ) {
			return match.dup().setWildcardsWire(wildcard_hints);
		}
		return match.dup();
	}

	/**
	 * Pushes a packet-out to a switch. The assumption here is that the packet-in 
	 * was also generated from the same switch. Thus, if the input port of the
	 * packet-in and the outport are the same, the function will not push the packet-out.
	 * 
	 * @param conn the connection to the switch that generated the packet-in, and from which packet-out is sent
	 * @param match the {@link OFMatch}
	 * @param pi packetin
	 * @param outport the output port
	 * @param cntx the {@link MessageContext}
	 */
	protected void pushPacket(Connection conn, OFMatch match, OFPacketIn pi, 
			int outport, MessageContext cntx) {

		if (pi == null) {
			return;
		}
		int input_port = getInputPort(pi);
		
		if ( input_port == outport){
			Logger.stdout("Packet out not sent as the outport matches inport. " + pi.toString());
			return;
		}

		// The assumption here is (sw) is the switch that generated the packet-in. 
		// If the input port is the same as output port, then the packet-out should be ignored.
		if ( input_port == outport) {
			return;
		}

		// set actions
		OFActionOutput action_output = OFMessageFactory.createActionOutput(pi.getVersion());
		List<OFAction> actions = new ArrayList<OFAction>();

		action_output.setMaxLength((short)0xffff);
		action_output.setPort(OFPort.of(outport));
		actions.add(action_output);
		
		OFPacketOut po = OFMessageFactory.createPacketOut(pi.getVersion());
		po
		.setActions(actions)
		.setActionsLength( action_output.computeLength() );

		// If the switch doens't support buffering set the buffer id to be none
		// otherwise it'll be the the buffer id of the PacketIn
		if ( protocol.getSwitchInformation(conn.getSwitch()).getBuffers() == 0 ) {
//		if (conn.getSwitch().getBuffers() == 0) {
			// We set the PI buffer id here so we don't have to check again below
			pi.setBufferId( 0xffffffff /* OFP_NO_BUFFER */ );
			po.setBufferId( 0xffffffff /* OFP_NO_BUFFER */ );
		} else {
			po.setBufferId(pi.getBufferId());
		}

		po.setInputPort(OFPort.of(input_port));

		// If the buffer id is none or the switch doesn's support buffering
		// we send the data with the packet out
		if (pi.getBufferId() == 0xffffffff /* OFP_NO_BUFFER */) {
			byte[] packetData = pi.getData();
			po.setData(packetData);
		}
		
		po.setLength(po.computeLength());
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
			messageDamper.write(conn, po);
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

		OFPacketOut po = OFMessageFactory.createPacketOut(sw.getVersion());

		// set actions
		OFActionOutput action_output = OFMessageFactory.createActionOutput(sw.getVersion());
		List<OFAction> actions = new ArrayList<OFAction>();

		action_output.setMaxLength((short)0xffff);
		action_output.setPort(OFPort.of(outPort));
		actions.add(action_output);
		
		po.setActions(actions)
		.setActionsLength( action_output.computeLength() );

		// set buffer_id, in_port
		po.setBufferId(bufferId);
		po.setInputPort(OFPort.of(inPort));

		// set data - only if buffer_id == -1
		if (po.getBufferId() == 0xffffffff /* OFP_NO_BUFFER */) {
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

		po.setLength(po.computeLength());
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
			messageDamper.write(conn, po);
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
		//setting actions
		List<OFAction> actions = new ArrayList<OFAction>();

		Iterator<Integer> j = outPorts.iterator();

		short actions_length = 0;
		while (j.hasNext())
		{
			OFActionOutput action_output = 
					OFMessageFactory.createActionOutput(conn.getSwitch().getVersion());
			action_output.setPort(OFPort.of(j.next().shortValue()));
			action_output.setMaxLength((short)0);
			actions.add(action_output);
			actions_length += action_output.computeLength();
		}

		OFPacketOut po = OFMessageFactory.createPacketOut(conn.getSwitch().getVersion());
		
		po.setActions(actions);
		po.setActionsLength( actions_length );

		// set buffer-id to OFP_NO_BUFFER, and set in-port to OFPP_NONE
		po.setBufferId( 0xffffffff /* OFP_NO_BUFFER */);
		po.setInputPort(OFPort.of(inPort));
		
		// data (note buffer_id is always OFP_NO_BUFFER) and length
		po.setData(packetData);
		po.setLength(po.computeLength());
		
		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
//			if (log.isTraceEnabled()) {
//				log.trace("write broadcast packet on switch-id={} " + 
//						"interfaces={} packet-out={}",
//						new Object[] {sw.getId(), outPorts, po});
//			}
			messageDamper.write(conn, po);

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
		int input_port = getInputPort(pi);
		broadcastHash = topology.getL2DomainId(conn.getSwitch().getId()) * prime1 +
				input_port * prime2 + eth.hashCode();
		if (broadcastCache.update(broadcastHash)) {
			conn.getSwitch().updateBroadcastCache(broadcastHash, (short) input_port);
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
//		if (sw == null) return true;

		// If the feature is disabled, always return false;
		if (!broadcastCacheFeature) return false;

		// Get the hash of the Ethernet packet.
		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);

		int input_port = getInputPort(pi);
		
		long hash =  input_port * prime2 + eth.hashCode();

		// some FORWARD_OR_FLOOD packets are unicast with unknown destination mac
		return conn.getSwitch().updateBroadcastCache(hash, (short) input_port);
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
		int inputPort = sw_tup.getPort();
//		log.debug("blockHost sw={} port={} mac={}",
//				new Object[] { sw, sw_tup.getPort(), new Long(host_mac) });

		// Create flow-mod based on packet-in and src-switch
		OFFlowMod fm = OFMessageFactory.createFlowMod(sw.getVersion());
		OFMatch.Builder match = OFMessageFactory.createMatchBuilder(sw.getVersion());
		
		if (fm.isInstructionsSupported()) {
			match.setValue(OFOxmMatchFields.OFB_ETH_SRC, (byte) 0, Ethernet.toByteArray(host_mac));
			match.setValue(OFOxmMatchFields.OFB_IN_PORT, (byte) 0, IPv4.toIPv4AddressBytes(inputPort));
			
//			OFInstructionApplyActions instruction = OFMessageFactory.createInstructionApplyActions(sw.getVersion());
//			instruction.setActions(Collections.<OFAction>emptyList());	// drop
//			List<OFInstruction> instructions = new ArrayList<OFInstruction>();
//			instruction.setLength(instruction.computeLength());
//			instructions.add(instruction);
			
			fm.setCommand(OFFlowModCommand.ADD)
			.setInstructions(Collections.<OFInstruction>emptyList());	// Drop
		} else {
//			List<OFAction> actions = new ArrayList<OFAction>();
			
			match                                                             
			.setDataLayerSource(Ethernet.toByteArray(host_mac))
			.setInputPort(OFPort.of(inputPort));
			if (match.isWildcardsSupported())
				match.setWildcardsWire(OFBFlowWildcard.ALL & ~OFBFlowWildcard.DL_SRC & ~OFBFlowWildcard.IN_PORT);
			
			fm.setActions(Collections.<OFAction>emptyList());			// Drop
		}
		
		fm.setCookie(cookie)
		.setHardTimeout((short) hardTimeout)
		.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
		.setBufferId( 0xffffffff /* OFP_NO_BUFFER */)
		.setMatch(match.build());
		if ( fm.isTableIdSupported() ) {
			fm.setTableId((byte) 0x0);
		}
		fm.setLength(fm.computeLength());
		
//		if (fm.isInstructionsSupported()) {
//			OFMatch.Builder blockMatchOxm = OFMessageFactory.createMatchBuilder(sw.getVersion());
//			
//			blockMatchOxm.setValue(OFOxmMatchFields.OFB_ETH_SRC, (byte) 0, Ethernet.toByteArray(host_mac));
//			blockMatchOxm.setValue(OFOxmMatchFields.OFB_IN_PORT, (byte) 0, IPv4.toIPv4AddressBytes(inputPort));
//			
//			List<OFInstruction> instructions = new ArrayList<OFInstruction>();
//			OFInstructionApplyActions instruction = OFMessageFactory.createInstructionApplyActions(sw.getVersion());
//			instruction.setActions(Collections.<OFAction>emptyList());	// drop
//			instruction.setLength(instruction.computeLength());
//			instructions.add(instruction);
//			
//			fm.setCookie(cookie)
//			.setTableId((byte) 0x0)
//			.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
////			.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
//			.setHardTimeout((short) hardTimeout)
//			.setCommand(OFFlowModCommand.ADD)
//			.setBufferId( 0xffffffff /* OFP_NO_BUFFER */)
////			.setFlagsWire((short) 0x0001)			//send flow removed message when flow expires or is deleted.
//			.setMatch(blockMatchOxm.build())
//			.setInstructions(instructions);
////			setCookie
////			setCookieMask
////			setFlags
////			setOutGroup
////			setOutPort
////			setPriority
//			fm.setLength(fm.computeLength());
//		} else {
//			OFMatch.Builder match = OFMessageFactory.createMatchBuilder(sw.getVersion());
//			List<OFAction> actions = new ArrayList<OFAction>(); // Set no action to
//
//			// drop
//			match                                                             
//			.setDataLayerSource(Ethernet.toByteArray(host_mac))
//			.setInputPort(OFPort.of(inputPort));
//			if (match.isWildcardsSupported())
//				match.setWildcardsWire(OFBFlowWildcard.ALL & ~OFBFlowWildcard.DL_SRC & ~OFBFlowWildcard.IN_PORT);
//
//			fm.setCookie(cookie)
//			.setHardTimeout((short) hardTimeout)
//			.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
////			.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
//			.setBufferId( 0xffffffff /* OFP_NO_BUFFER */)
//			.setMatch(match.build())
//			.setActions(actions)
//			.setLength(fm.computeLength());
//		}

//		log.debug("write drop flow-mod sw={} match={} flow-mod={}",
//				new Object[] { sw, match, fm });
		try {
			messageDamper.write(sw.getConnection(), fm);

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
