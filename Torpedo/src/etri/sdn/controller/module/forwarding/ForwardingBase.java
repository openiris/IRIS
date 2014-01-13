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
import java.util.Comparator;
//import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.ver1_0.messages.OFAction;
import org.openflow.protocol.ver1_0.messages.OFActionOutput;
import org.openflow.protocol.ver1_0.messages.OFFlowMod;
import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.protocol.ver1_0.messages.OFPacketIn;
import org.openflow.protocol.ver1_0.messages.OFPacketOut;
import org.openflow.protocol.ver1_0.types.OFFlowModCommand;
import org.openflow.protocol.ver1_0.types.OFFlowModFlags;
import org.openflow.protocol.ver1_0.types.OFFlowWildcards;
import org.openflow.protocol.ver1_0.types.OFMessageType;
import org.openflow.util.OFPort;

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
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPacket;
import etri.sdn.controller.protocol.version.VersionAdaptor10;
import etri.sdn.controller.util.AppCookie;
import etri.sdn.controller.util.Logger;
import etri.sdn.controller.util.OFMessageDamper;
import etri.sdn.controller.util.TimedCache;

/**
 * Abstract base class for implementing a forwarding module. Forwarding is responsible
 * for programming flows to a switch in response to a policy decision.
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
	
	VersionAdaptor10 version_adaptor_10;

	// for broadcast loop suppression
	protected boolean broadcastCacheFeature = true;
	public final int prime1 = 2633;  // for hash calculation
	public final static int prime2 = 4357;  // for hash calculation
	public TimedCache<Long> broadcastCache =
			new TimedCache<Long>(100, 5*1000);  // 5 seconds interval;

	// flow-mod - for use in the cookie
	public static final int FORWARDING_APP_ID = 2; // TODO: This must be managed
	// by a global APP_ID class
	public long appCookie = AppCookie.makeCookie(FORWARDING_APP_ID, 0);

	// Comparator for sorting by SwitchCluster
	public Comparator<SwitchPort> clusterIdComparator =
			new Comparator<SwitchPort>() {
		@Override
		public int compare(SwitchPort d1, SwitchPort d2) {
			Long d1ClusterId = 
					topology.getL2DomainId(d1.getSwitchDPID());
			Long d2ClusterId = 
					topology.getL2DomainId(d2.getSwitchDPID());
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
		
		version_adaptor_10 = (VersionAdaptor10) getController().getVersionAdaptor((byte)0x01);
		
		HashSet<Byte> set = new HashSet<Byte>();
		set.add(OFMessageType.FLOW_MOD.getTypeValue());
		messageDamper = new OFMessageDamper(OFMESSAGE_DAMPER_CAPACITY, 
				set, OFMESSAGE_DAMPER_TIMEOUT);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection sw, MessageContext context) {
		// does nothing
		return true;
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
		
		switch ( OFMessageType.valueOf(msg.getTypeByte()) ) {
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
	processPacketInMessage(Connection conn, OFPacketIn pi,
			IRoutingDecision decision, MessageContext cntx);
	
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
			Route route, OFMatch match, 
			Integer wildcard_hints,
			OFPacketIn pi,
			long pinSwitch,
			long cookie, 
			MessageContext cntx,
			boolean reqeustFlowRemovedNotifn,
			boolean doFlush,
			short   flowModCommand) {

		boolean srcSwitchIncluded = false;
//		OFFlowMod fm = (OFFlowMod) conn.getFactory().getMessage(OFType.FLOW_MOD);
		OFFlowMod fm = (OFFlowMod) OFMessageType.FLOW_MOD.newInstance();
		
		OFActionOutput action = new OFActionOutput();
		action.setMaxLength((short)0xffff);
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>();
		actions.add(action);

		fm.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
		.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
		.setBufferId(0xffffffff /* OFPacketOut.BUFFER_ID_NONE */)
		.setCookie(cookie)
		.setCommand(OFFlowModCommand.valueOf(flowModCommand))
		.setMatch(match)
		.setActions(actions)
		.setLengthU(OFFlowMod.MINIMUM_LENGTH+OFActionOutput.MINIMUM_LENGTH);

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
			fm.setMatch(wildcard(match, sw, wildcard_hints));

			// set buffer id if it is the source switch
			if (1 == indx) {
				// Set the flag to request flow-mod removal notifications only for the
				// source switch. The removal message is used to maintain the flow cache.
				// Don't set the flag for ARP messages - TODO generalize check
				if ((reqeustFlowRemovedNotifn)
						&& (match.getDataLayerType() != Ethernet.TYPE_ARP)) {
//					fm.setFlags(OFFlowMod.OFPFF_SEND_FLOW_REM);
					fm.setFlagsWire(OFFlowModFlags.SEND_FLOW_REM);
					match.setWildcards(fm.getMatch().getWildcards());
				}
			}

			short outPort = switchPortList.get(indx).getPortId();
			short inPort = switchPortList.get(indx-1).getPortId();
			// set input and output ports on the switch
//			fm.getMatch().setInputPort(inPort);
			fm.getMatch().setInputPort(OFPort.of(inPort));
			((OFActionOutput)fm.getActions().get(0)).setPort(OFPort.of(outPort));

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

//			fm = fm.clone();
			fm = new OFFlowMod(fm);
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
		if (wildcard_hints != null) {
//			return ((OFMatch) match.clone()).setWildcards(wildcard_hints.intValue());
			return new OFMatch().setWildcardsWire(wildcard_hints);
		}
//		return match.clone();
		return new OFMatch(match);
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
			short outport, MessageContext cntx) {

		if (pi == null) {
			return;
		} else if (pi.getInputPort().get() == outport){
			Logger.stdout("Packet out not sent as the outport matches inport. " + pi.toString());
			return;
		}

		// The assumption here is (sw) is the switch that generated the packet-in. 
		// If the input port is the same as output port, then the packet-out should be ignored.
		if (pi.getInputPort().get() == outport) {
//			if (log.isDebugEnabled()) {
//				log.debug("Attempting to do packet-out to the same " + 
//						"interface as packet-in. Dropping packet. " + 
//						" SrcSwitch={}, match = {}, pi={}", 
//						new Object[]{sw, match, pi});
//			}
			return;
		}

//			if (log.isTraceEnabled()) {
//				log.trace("PacketOut srcSwitch={} match={} pi={}", 
//						new Object[] {sw, match, pi});
//			}

//		OFPacketOut po = (OFPacketOut) conn.getFactory().getMessage(OFType.PACKET_OUT);
		OFPacketOut po = (OFPacketOut) OFMessageType.PACKET_OUT.newInstance();

		// set actions
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>();
		OFActionOutput action_output = new OFActionOutput();
		actions.add( action_output.setPort(OFPort.of(outport)).setMaxLength((short)0xffff) );
//		actions.add(new OFActionOutput(outport, (short) 0xffff));
		
		po
		.setActions(actions)
		.setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);
		short poLength = (short) (po.getActionsLength() + OFPacketOut.MINIMUM_LENGTH);

		// If the switch doens't support buffering set the buffer id to be none
		// otherwise it'll be the the buffer id of the PacketIn
		if ( version_adaptor_10.getSwitchInformation(conn.getSwitch()).getBuffers() == 0 ) {
//		if (conn.getSwitch().getBuffers() == 0) {
			// We set the PI buffer id here so we don't have to check again below
			pi.setBufferId( 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */ );
			po.setBufferId( 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */ );
		} else {
			po.setBufferId(pi.getBufferId());
		}

		po.setInputPort(pi.getInputPort());

		// If the buffer id is none or the switch doesn's support buffering
		// we send the data with the packet out
		if (pi.getBufferId() == 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */) {
//			byte[] packetData = pi.getPacketData();
			byte[] packetData = pi.getData();
			poLength += packetData.length;
//			po.setPacketData(packetData);
			po.setData(packetData);
		}
		
		po.setLength(poLength);
		
//		System.err.println ("FB PacketOut XXXXXXXX " + po.toString());

		try {
//			counterStore.updatePktOutFMCounterStore(sw, po);
			messageDamper.write(conn, po);
		} catch (IOException e) {
			Logger.stderr("Failure writing packet out" + e.toString());
		}
	}

	/**
	 * Pushes a packet-out to a switch. If bufferId != BUFFER_ID_NONE we 
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

//		if (log.isTraceEnabled()) {
//			log.trace("PacketOut srcSwitch={} inPort={} outPort={}", 
//					new Object[] {sw, inPort, outPort});
//		}

//		OFPacketOut po = (OFPacketOut) conn.getFactory().getMessage(OFType.PACKET_OUT);
		OFPacketOut po = (OFPacketOut) OFMessageType.PACKET_OUT.newInstance();

		// set actions
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>();
		OFActionOutput action_output = new OFActionOutput();
//		actions.add(new OFActionOutput(outPort, (short) 0xffff));
		actions.add( action_output.setPort(OFPort.of(outPort)).setMaxLength((short)0xffff) );

		po.setActions(actions)
		.setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);
		short poLength = (short) (po.getActionsLength() + OFPacketOut.MINIMUM_LENGTH);

		// set buffer_id, in_port
		po.setBufferId(bufferId);
		po.setInputPort(OFPort.of(inPort));

		// set data - only if buffer_id == -1
		if (po.getBufferId() == 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */) {
			if (packet == null) {
//				log.error("BufferId is not set and packet data is null. " +
//						"Cannot send packetOut. " +
//						"srcSwitch={} inPort={} outPort={}",
//						new Object[] {sw, inPort, outPort});
				return;
			}
			byte[] packetData = packet.serialize();
			poLength += packetData.length;
//			po.setPacketData(packetData);
			po.setData(packetData);
		}

		po.setLength(poLength);
		
//		System.out.println("FB PacketOut XXXXXXXXXXXXXXX " + po.toString());

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
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>();

		Iterator<Integer> j = outPorts.iterator();

		while (j.hasNext())
		{
			OFActionOutput action_output = new OFActionOutput();
			action_output.setPort(OFPort.of(j.next().shortValue())).setMaxLength((short)0);
//			actions.add(new OFActionOutput(j.next().shortValue(), 
//					(short) 0));
			actions.add(action_output);
		}

//		OFPacketOut po = (OFPacketOut)conn.getFactory().getMessage(OFType.PACKET_OUT);
		OFPacketOut po = (OFPacketOut) OFMessageType.PACKET_OUT.newInstance();
		
		po.setActions(actions);
		po.setActionsLength((short) (OFActionOutput.MINIMUM_LENGTH * 
				outPorts.size()));

		// set buffer-id to BUFFER_ID_NONE, and set in-port to OFPP_NONE
		po.setBufferId( 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */);
		po.setInputPort(OFPort.of(inPort));

		// data (note buffer_id is always BUFFER_ID_NONE) and length
		short poLength = (short)(po.getActionsLength() + 
				OFPacketOut.MINIMUM_LENGTH);
		poLength += packetData.length;
//		po.setPacketData(packetData);
		po.setData(packetData);
		po.setLength(poLength);
		
//		System.out.println("FB PacketOut 2 YYYYYYYYYYYYYY " + po.toString());

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
//		packetOutMultiPort(pi.getPacketData(), conn, inPort, outPorts, cntx);
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
		broadcastHash = topology.getL2DomainId(conn.getSwitch().getId()) * prime1 +
				pi.getInputPort().get() * prime2 + eth.hashCode();
		if (broadcastCache.update(broadcastHash)) {
			conn.getSwitch().updateBroadcastCache(broadcastHash, (short) pi.getInputPort().get());
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

		long hash =  pi.getInputPort().get() * prime2 + eth.hashCode();

		// some FORWARD_OR_FLOOD packets are unicast with unknown destination mac
		return conn.getSwitch().updateBroadcastCache(hash, (short) pi.getInputPort().get());
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
//		OFFlowMod fm = (OFFlowMod) sw.getConnection().getFactory().getMessage(OFType.FLOW_MOD);
		OFFlowMod fm = (OFFlowMod) OFMessageType.FLOW_MOD.newInstance();
		
		OFMatch match = new OFMatch();
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>(); // Set no action to
		// drop
		match.setDataLayerSource(Ethernet.toByteArray(host_mac))
		.setInputPort(OFPort.of(inputPort))
//		.setWildcards(OFMatch.OFPFW_ALL & ~OFMatch.OFPFW_DL_SRC
//				& ~OFMatch.OFPFW_IN_PORT);
		.setWildcardsWire(OFFlowWildcards.ALL & ~OFFlowWildcards.DL_SRC & ~OFFlowWildcards.IN_PORT);
		
		fm.setCookie(cookie)
		.setHardTimeout((short) hardTimeout)
		.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
		.setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
		.setBufferId( 0xffffffff /* OFPacketOut.BUFFER_ID_NONE */)
		.setMatch(match)
		.setActions(actions)
		.setLengthU(OFFlowMod.MINIMUM_LENGTH); // +OFActionOutput.MINIMUM_LENGTH);

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
