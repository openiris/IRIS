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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.ver1_0.messages.OFAction;
import org.openflow.protocol.ver1_0.messages.OFActionOutput;
import org.openflow.protocol.ver1_0.messages.OFFlowMod;
import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.protocol.ver1_0.messages.OFPacketIn;
import org.openflow.protocol.ver1_0.messages.OFPacketOut;
import org.openflow.protocol.ver1_0.types.OFFlowModCommand;
import org.openflow.protocol.ver1_0.types.OFFlowWildcards;
import org.openflow.protocol.ver1_0.types.OFPortNo;
import org.openflow.util.OFPort;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.devicemanager.IDevice;
import etri.sdn.controller.module.devicemanager.IDeviceService;
import etri.sdn.controller.module.devicemanager.SwitchPort;
import etri.sdn.controller.module.routing.IRoutingDecision;
import etri.sdn.controller.module.routing.IRoutingService;
import etri.sdn.controller.module.routing.Route;
import etri.sdn.controller.module.topologymanager.ITopologyService;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.version.VersionAdaptor10;
import etri.sdn.controller.util.AppCookie;
import etri.sdn.controller.util.Logger;

/**
 * This class implements the forwarding module.
 * This module determines how to handle all PACKET_IN messages
 * according to the routing decision.
 * 
 * <p>Modified the original LearningMac class of Floodlight.
 * 
 * @author jshin
 */
public class Forwarding extends ForwardingBase {
	
	VersionAdaptor10 version_adaptor_10;
	
	/**
	 * Initializes this module. As this module processes all PACKET_IN messages,
	 * it registers filter to receive those messages.
	 */
	@Override
	public void initialize() {
		super.initialize();
		
		this.deviceManager = (IDeviceService) getModule(IDeviceService.class);
		this.routingEngine = (IRoutingService) getModule(IRoutingService.class);
		this.topology = (ITopologyService) getModule(ITopologyService.class);
		
		version_adaptor_10 = (VersionAdaptor10) getController().getVersionAdaptor((byte)0x01);
		
		registerFilter(
			OFMessageType.PACKET_IN, 
			new OFMFilter() {
				@Override
				public boolean filter(OFMessage m) {
					if ( m.getVersion() == (byte)0x01 )
						return true;
					return false;
				}
			}
		);
	}

	/**
	 * Calls an appropriate method to process packetin according to the routing decision.
	 */
	@Override
	public boolean processPacketInMessage(Connection conn, OFPacketIn pi,
			IRoutingDecision decision, MessageContext cntx) {

		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);
		
		if ( eth == null ) {
			// parse Ethernet header and put into the context
			eth = new Ethernet();
//			eth.deserialize(pi.getPacketData(), 0, pi.getPacketData().length);
			eth.deserialize(pi.getData(), 0, pi.getData().length);
			cntx.put(MessageContext.ETHER_PAYLOAD, eth);
		}

		// If a decision has been made we obey it
		// otherwise we just forward
		if (decision != null) {
			Logger.stdout("Forwaring decision=" + decision.getRoutingAction().toString() + " was made for PacketIn=" + pi);

			switch(decision.getRoutingAction()) {
			case NONE:
				// don't do anything
				return true;
			case FORWARD_OR_FLOOD:
			case FORWARD:
				doForwardFlow(conn.getSwitch(), pi, cntx, false);
				return true;
			case MULTICAST:
				// treat as broadcast
				doFlood(conn.getSwitch(), pi, cntx);
				return true;
			case DROP:
				doDropFlow(conn.getSwitch(), pi, decision, cntx);
				return true;
			default:
				Logger.error("Unexpected decision made for this packet-in={}",
						pi, decision.getRoutingAction());
				return true;
			}
		} else {
			Logger.stdout("No decision was made for PacketIn=" + pi + " forwarding");

			if (eth.isBroadcast() || eth.isMulticast()) {
				// For now we treat multicast as broadcast
				doFlood(conn.getSwitch(), pi, cntx);
			} else {
				doForwardFlow(conn.getSwitch(), pi, cntx, false);
			}
		}

		return true;
	}
	
	

	/**
	 * Creates a {@link OFPacketOut} with packetin that is dropped.
	 * 
	 * @param sw the switch that receives packetin
	 * @param pi packetin
	 * @param decision the routing decision
	 * @param cntx the {@link MessageContext}
	 */
	protected void doDropFlow(IOFSwitch sw, OFPacketIn pi, IRoutingDecision decision, MessageContext cntx) {
		// initialize match structure and populate it using the packet
//		OFMatch match = new OFMatch();
//		match.loadFromPacket(pi.getPacketData(), pi.getInPort());
		OFMatch match = version_adaptor_10.loadOFMatchFromPacket(pi.getData(), (short)pi.getInputPort().get());
		if (decision.getWildcards() != null) {
			match.setWildcardsWire(decision.getWildcards());
		}

		// Create flow-mod based on packet-in and src-switch
		OFFlowMod fm = new OFFlowMod();
		
//		List<OFAction> actions = new ArrayList<OFAction>(); // Set no action to
		// drop
		long cookie = AppCookie.makeCookie(FORWARDING_APP_ID, 0);

		fm.setCookie(cookie)
		.setHardTimeout((short) 0)
		.setIdleTimeout((short) 5)
		.setBufferId(0xffffffff /* OFPacketOut.BUFFER_ID_NONE */)
		.setMatch(match)
		.setActions(Collections.<org.openflow.protocol.interfaces.OFAction>emptyList())
		.setLengthU(OFFlowMod.MINIMUM_LENGTH); // +OFActionOutput.MINIMUM_LENGTH);

		try {
			Logger.debug("write drop flow-mod sw={} match={} flow-mod={}", 
					new Object[] { sw, match, fm });
			messageDamper.write(sw.getConnection(), fm);
		} catch (IOException e) {
			Logger.stderr("Failure writing drop flow mod" + e.toString());
		}
	}

	/**
	 * Creates a OFPacketOut with packetin that is forwarded.
	 * Forwards packet if we know where the destination device is
	 * or floods packet if we don't know. 
	 * 
	 * @param sw the switch that receives packetin
	 * @param pi packetin
	 * @param cntx the {@link MessageContext}
	 * @param requestFlowRemovedNotifn true when the switch would send a flow mod removal notification when the flow mod expires
	 * 
	 */
	protected void doForwardFlow(IOFSwitch sw, OFPacketIn pi, 
			MessageContext cntx,
			boolean requestFlowRemovedNotifn) {    
//		OFMatch match = new OFMatch();
//		match.loadFromPacket(pi.getPacketData(), pi.getInPort());
		OFMatch match = version_adaptor_10.loadOFMatchFromPacket(pi.getData(), (short)pi.getInputPort().get());

		// Check if we have the location of the destination
		IDevice dstDevice = (IDevice) cntx.get(MessageContext.DST_DEVICE);

		if (dstDevice != null) {
			IDevice srcDevice = (IDevice) cntx.get(MessageContext.SRC_DEVICE);
			Long srcIsland = topology.getL2DomainId(sw.getId());

			if (srcDevice == null) {
				Logger.stderr("No device entry found for source device");
				return;
			}
			if (srcIsland == null) {
				Logger.stderr("No openflow island found for source {" + sw.getStringId() + "}/{" + pi.getInputPort() + "}");
				return;
			}

			// Validate that we have a destination known on the same island
			// Validate that the source and destination are not on the same switchport
			boolean on_same_island = false;
			boolean on_same_if = false;
			for (SwitchPort dstDap : dstDevice.getAttachmentPoints()) {
				long dstSwDpid = dstDap.getSwitchDPID();
				Long dstIsland = topology.getL2DomainId(dstSwDpid);
				if ((dstIsland != null) && dstIsland.equals(srcIsland)) {
					on_same_island = true;
					if ((sw.getId() == dstSwDpid) &&
							(pi.getInputPort().get() == dstDap.getPort())) {
						on_same_if = true;
					}
					break;
				}
			}

			if (!on_same_island) {
				Logger.stdout("No first hop island found for destination device " + dstDevice + ", Action = flooding");
				// Flood since we don't know the dst device
				doFlood(sw, pi, cntx);
				return;
			}            

			if (on_same_if) {
				Logger.stdout("Both source and destination are on the same switch/port " + 
						sw.toString() + "/" + pi.getInputPort() + ", Action = NOP");
				return;
			}

			// Install all the routes where both src and dst have attachment points.
			// Since the lists are stored in sorted order we can traverse the attachment
			// points in O(m+n) time.
			SwitchPort[] srcDaps = srcDevice.getAttachmentPoints();
			Arrays.sort(srcDaps, clusterIdComparator);
			SwitchPort[] dstDaps = dstDevice.getAttachmentPoints();
			Arrays.sort(dstDaps, clusterIdComparator);

			int iSrcDaps = 0, iDstDaps = 0;

			while ((iSrcDaps < srcDaps.length) && (iDstDaps < dstDaps.length)) {
				SwitchPort srcDap = srcDaps[iSrcDaps];
				SwitchPort dstDap = dstDaps[iDstDaps];
				Long srcCluster = 
						topology.getL2DomainId(srcDap.getSwitchDPID());
				Long dstCluster = 
						topology.getL2DomainId(dstDap.getSwitchDPID());

				int srcVsDest = srcCluster.compareTo(dstCluster);
				if (srcVsDest == 0) {
					if (!srcDap.equals(dstDap) && 
							/* (srcCluster != null) && */		// --redundant null check.
							(dstCluster != null)) {
						Route route = 
								routingEngine.getRoute(srcDap.getSwitchDPID(),
										(short)srcDap.getPort(),
										dstDap.getSwitchDPID(),
										(short)dstDap.getPort());
						if (route != null) {
//							if (log.isTraceEnabled()) {
//								log.trace("pushRoute match={} route={} " + 
//										"destination={}:{}",
//										new Object[] {match, route, 
//										dstDap.getSwitchDPID(),
//										dstDap.getPort()});
//							}
							
							long cookie = 
									AppCookie.makeCookie(FORWARDING_APP_ID, 0);

							// if there is prior routing decision use wildcard                                                     
							Integer wildcard_hints = null;
							IRoutingDecision decision = (IRoutingDecision) cntx.get(MessageContext.ROUTING_DECISION);
							
							if (decision != null) {
								wildcard_hints = decision.getWildcards();
							} else {
								// L2 only wildcard if there is no prior route decision
								wildcard_hints = ((Integer) sw
										.getAttribute(IOFSwitch.PROP_FASTWILDCARDS))
										.intValue()
										& ~OFFlowWildcards.IN_PORT
										& ~OFFlowWildcards.DL_VLAN
										& ~OFFlowWildcards.DL_SRC
										& ~OFFlowWildcards.DL_DST
										& ~OFFlowWildcards.NW_SRC_MASK
										& ~OFFlowWildcards.NW_DST_MASK;
							}

							pushRoute(sw.getConnection(), route, match, wildcard_hints, pi, sw.getId(), cookie, 
									cntx, requestFlowRemovedNotifn, false,
									OFFlowModCommand.ADD.getValue());
						}
					}
					iSrcDaps++;
					iDstDaps++;
				} else if (srcVsDest < 0) {
					iSrcDaps++;
				} else {
					iDstDaps++;
				}
			}
		} else {
			// Flood since we don't know the dst device
			doFlood(sw, pi, cntx);
		}
	}

	/**
	 * Creates a OFPacketOut with packetin that is flooded on all ports
	 * unless the port is blocked, in which case the packet will be dropped.
	 * 
	 * @param sw the switch that receives packetin
	 * @param pi packetin
	 * @param cntx the {@link MessageContext}
	 */
	protected void doFlood(IOFSwitch sw, OFPacketIn pi, MessageContext cntx) {
		if (! topology.isIncomingBroadcastAllowed(sw.getId(), (short) pi.getInputPort().get()) ) {
//			if (log.isTraceEnabled()) {
//				log.trace("doFlood, drop broadcast packet, pi={}, " + 
//						"from a blocked port, srcSwitch=[{},{}], linkInfo={}",
//						new Object[] {pi, sw.getId(),pi.getInPort()});
//			}
			return;
		}

		// Set Action to flood
		OFPacketOut po = new OFPacketOut();
		
		List<org.openflow.protocol.interfaces.OFAction> actions = new ArrayList<org.openflow.protocol.interfaces.OFAction>();
		if (sw.hasAttribute(IOFSwitch.PROP_SUPPORTS_OFPP_FLOOD)) {
			OFActionOutput action_output = new OFActionOutput();
			action_output.setPort(OFPort.of(OFPortNo.FLOOD.getValue())).setMaxLength((short)0xffff);
			actions.add(action_output);
		} else {
			OFActionOutput action_output = new OFActionOutput();
			action_output.setPort(OFPort.of(OFPortNo.ALL.getValue())).setMaxLength((short)0xffff);
			actions.add(action_output);
		}
		po.setActions(actions);
		po.setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);

		// set buffer-id, in-port and packet-data based on packet-in
		short poLength = (short)(po.getActionsLength() + OFPacketOut.MINIMUM_LENGTH);
		po.setBufferId(pi.getBufferId());
		po.setInputPort(pi.getInputPort());
		if (pi.getBufferId() == 0xffffffff /*OFPacketOut.BUFFER_ID_NONE */ ) {
			byte[] packetData = pi.getData();
			poLength += packetData.length;
			po.setData(packetData);
		}
		po.setLength(poLength);

		try {
//			if (log.isTraceEnabled()) {
//				log.trace("Writing flood PacketOut switch={} packet-in={} packet-out={}",
//						new Object[] {sw, pi, po});
//			}
			messageDamper.write(sw.getConnection(), po);
		} catch (IOException e) {
//			log.error("Failure writing PacketOut switch={} packet-in={} packet-out={}",
//					new Object[] {sw, pi, po}, e);
			Logger.stderr("Failure writing PacketOut");
		}            

		return;
	}

	@Override
	public OFModel[] getModels() {
		return null;
	}
}
