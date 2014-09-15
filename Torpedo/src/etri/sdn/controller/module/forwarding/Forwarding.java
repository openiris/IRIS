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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFFlowModCommand;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.IService;
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
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.util.AppCookie;

/**
 * This class implements the forwarding module.
 * This module determines how to handle all PACKET_IN messages
 * according to the routing decision.
 * 
 * @author jshin
 */
public class Forwarding extends ForwardingBase {
	
	private static final Logger logger = LoggerFactory.getLogger(Forwarding.class);
	
	OFProtocol protocol;
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		// no service implemented.
		return Collections.emptyList();
	}
	
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
		
		this.protocol = getController().getProtocol();
		
		registerFilter(
			OFType.PACKET_IN, 
			new OFMFilter() {
				@Override
				public boolean filter(OFMessage m) {
					OFPacketIn pi = (OFPacketIn) m;
					if ( pi.getData() == null || pi.getData().length <= 0 ) {
						return false;
					}
					return true;
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
			eth.deserialize(pi.getData(), 0, pi.getData().length);
			cntx.put(MessageContext.ETHER_PAYLOAD, eth);
		}

		// If a decision has been made we obey it
		// otherwise we just forward
		if (decision != null) {
//			logger.debug("Forwaring decision={} was made for PacketIn={}", 
//					decision.getRoutingAction().toString(), pi);

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
				logger.debug("Unexpected decision made for this packet-in={}, routingAction={}",
						pi, decision.getRoutingAction());
				return true;
			}
		} else {
//			logger.debug("No forwarding decision was made for PacketIn={}", pi);

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
		OFPort inPort = getInputPort(pi);
		
		// initialize match structure and populate it using the packet
		Match match = protocol.loadOFMatchFromPacket(sw, pi, inPort, false);

		// Create flow-mod based on packet-in and src-switch
		OFFlowAdd.Builder fm = OFFactories.getFactory(pi.getVersion()).buildFlowAdd();
		
		// Drop
		try {
			fm.setInstructions(Collections.<OFInstruction>emptyList());
		} catch ( UnsupportedOperationException u ) {
			fm.setActions(Collections.<OFAction>emptyList());
		}

		long cookie = AppCookie.makeCookie(FORWARDING_APP_ID, 0);
		
		try { 
			fm
			.setCookie(U64.of(cookie))
			.setHardTimeout(0)
			.setIdleTimeout(5)
			.setPriority(ForwardingBase.FLOWMOD_DEFAULT_PRIORITY)
			.setBufferId(OFBufferId.NO_BUFFER)
			.setMatch(match)
			.setCookieMask(U64.of(0xffffffffffffffffL))
			.setTableId(TableId.of(0));
		} catch ( UnsupportedOperationException u ) {
			// do nothing. possibly from setTableId() call
		}
		
		try {
			logger.debug("write drop flow-mod sw={} match={} flow-mod={}", sw, match, fm);
			messageDamper.write(sw.getConnection(), fm.build());
		} catch (IOException e) {
			logger.error("Failure writing drop flow mod: {}", e);
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
		
		OFPort inPort = getInputPort(pi);
		
		Match match = protocol.loadOFMatchFromPacket(sw, pi, inPort, false);

		// Check if we have the location of the destination
		IDevice dstDevice = (IDevice) cntx.get(MessageContext.DST_DEVICE);

		if (dstDevice != null) {
			IDevice srcDevice = (IDevice) cntx.get(MessageContext.SRC_DEVICE);
			Long srcIsland = topology.getL2DomainId(sw.getId());

			if (srcDevice == null) {
				logger.debug("No device entry found for source device");
				return;
			}
			if (srcIsland == null) {
				logger.debug("No openflow island found for source {}/{}",sw.getStringId(),inPort);
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
					if ((sw.getId() == dstSwDpid) && (inPort == dstDap.getPort())) {
						on_same_if = true;
					}
					break;
				}
			}

			if (!on_same_island) {
				logger.debug("No first hop island found for destination device={}, action=flooding", dstDevice);
				// Flood since we don't know the dst device
				doFlood(sw, pi, cntx);
				return;
			}            

			if (on_same_if) {
				logger.debug("Both source and destination are on the same switch/port={}/{} action=NOP",sw,inPort);
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
										srcDap.getPort(),
										dstDap.getSwitchDPID(),
										dstDap.getPort());
						if (route != null) {
//							if (log.isTraceEnabled()) {
//								log.trace("pushRoute match={} route={} " + 
//										"destination={}:{}",
//										new Object[] {match, route, 
//										dstDap.getSwitchDPID(),
//										dstDap.getPort()});
//							}
							
							long cookie = AppCookie.makeCookie(FORWARDING_APP_ID, 0);

							pushRoute(sw.getConnection(), route, match, pi, sw.getId(), cookie, 
									cntx, requestFlowRemovedNotifn, false,
									OFFlowModCommand.ADD);
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
		OFPort inPort = getInputPort(pi);
		
		if (! topology.isIncomingBroadcastAllowed(sw.getId(), inPort) ) {
//			if (log.isTraceEnabled()) {
//				log.trace("doFlood, drop broadcast packet, pi={}, " + 
//						"from a blocked port, srcSwitch=[{},{}], linkInfo={}",
//						new Object[] {pi, sw.getId(),pi.getInPort()});
//			}
			return;
		}

		OFFactory fac = OFFactories.getFactory(pi.getVersion());
		
		// Set Action to flood
		OFPacketOut.Builder po = fac.buildPacketOut();
		
		po
		.setBufferId(pi.getBufferId())
		.setInPort(inPort);
		
		List<OFAction> actions = new ArrayList<OFAction>();
		OFActionOutput.Builder action_output = fac.actions().buildOutput();
		
		if (sw.hasAttribute(IOFSwitch.PROP_SUPPORTS_OFPP_FLOOD)) {
			action_output.setPort(OFPort.FLOOD);
		} else {
			action_output.setPort(OFPort.ALL);
		}
		action_output.setMaxLen(0);
		
		actions.add(action_output.build());
		po.setActions(actions);

		// set buffer-id, in-port and packet-data based on packet-in
		if (pi.getBufferId() == OFBufferId.NO_BUFFER ) {
			po.setData( pi.getData() );
		}
		
		try {
//			if (log.isTraceEnabled()) {
//				log.trace("Writing flood PacketOut switch={} packet-in={} packet-out={}",
//						new Object[] {sw, pi, po});
//			}
			messageDamper.write(sw.getConnection(), po.build());
		} catch (IOException e) {
//			log.error("Failure writing PacketOut switch={} packet-in={} packet-out={}",
//					new Object[] {sw, pi, po}, e);
			logger.error("Failure writing PacketOut switch={} packet-in={} packet-out={}, err={}",
					sw, pi, po, e);
		}            

		return;
	}

	@Override
	public OFModel[] getModels() {
		return null;
	}
}
