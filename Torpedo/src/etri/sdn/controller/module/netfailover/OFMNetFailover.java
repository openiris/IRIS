/**
 *    Copyright 2014, ETRI.
 *     
 *    Originally created by Byungjoon Lee, ETRI
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

package etri.sdn.controller.module.netfailover;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowDelete;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U64;

import com.google.common.hash.BloomFilter;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.forwarding.Forwarding;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener.LDUpdate;
import etri.sdn.controller.module.linkdiscovery.NodePortTuple;
import etri.sdn.controller.module.routing.IRoutingService;
import etri.sdn.controller.module.routing.Route;
import etri.sdn.controller.module.topologymanager.ITopologyListener;
import etri.sdn.controller.module.topologymanager.ITopologyService;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.AppCookie;


/**
 * Thread that relays LDUpdate notification to a real worker method.
 * @author Byungjoon Lee
 *
 */
class LDUpdateProcessor extends Thread {
	private OFMNetFailover parent;
	private LinkedBlockingQueue<LDUpdate> queue = new LinkedBlockingQueue<LDUpdate>(128);
	
	LDUpdateProcessor(OFMNetFailover parent) {
		this.parent = parent;
	}
	
	void put(LDUpdate t) {
		queue.add(t);
	}
	
	public void run() {
		do {
			try {
				LDUpdate t = queue.take();
				
				switch ( t.getOperation() ) {
				case LINK_UPDATED:
					parent.removeRoutesOnAnyLink(t.getSrc(),t.getSrcPort(), t.getDst(),t.getDstPort());
					break;
				case LINK_REMOVED:
					parent.removeOldRoutesOnLink(t.getSrc(),t.getSrcPort(), t.getDst(),t.getDstPort());
					break;
				default:
					break;
				}
				
			} catch (InterruptedException e) {
				// quit further processing
				return;
			}
			
		} while ( true );
	}
}

/**
 * Network Failover module.
 * 
 * @author Byungjoon Lee
 *
 */
public class OFMNetFailover 
extends OFModule
implements ITopologyListener {

	private ITopologyService topologyService;
	private IRoutingService routingService;
	
	private LDUpdateProcessor processor;
	
	private long cookie = AppCookie.makeCookie(Forwarding.FORWARDING_APP_ID, 0);
		
	@Override
	public void topologyChanged() {
		List<LDUpdate> updates = this.topologyService.getLastLinkUpdates();
		for ( LDUpdate u : updates ) {
			switch ( u.getOperation() ) {
			case LINK_UPDATED:
				// a link is added or updated.
				this.processor.put(u);
				break;
			case LINK_REMOVED:
				// a link is removed. We need to find all routes on the link
				// and remove them from the flow tables of the switches. 
				this.processor.put(u);
				break;
			default:
				// does nothing
			}
		}
	}

	void removeOldRoutesOnLink(long src, OFPort srcPort, long dst,	OFPort dstPort) {
		NodePortTuple srcNpt = new NodePortTuple(src, srcPort);
		NodePortTuple dstNpt = new NodePortTuple(dst, dstPort);

		Set<Long> asws = getAccessSwitches();
		
		// now, for every pair of access switches,
		for ( long s : asws ) {
			for ( long d : asws ) {
				// we don't do the removal for the reverse direction.
				// can this be some source of evil?
				if ( s <= d ) continue;
				
				 Route r = this.routingService.getOldRoute(s, d);
				 if  (r == null) 
					 // topology is not ready right now.
					 continue;
				 
				 BloomFilter<NodePortTuple> bf = r.getBloomFilter();
				 if ( bf.mightContain(srcNpt) || bf.mightContain(dstNpt) ) {
					 removeRouteFromNetwork(r);
				 }
			}
		}		
	}
	
	void removeRoutesOnAnyLink(long src, OFPort srcPort, long dst,	OFPort dstPort) {
		NodePortTuple srcNpt = new NodePortTuple(src, srcPort);
		NodePortTuple dstNpt = new NodePortTuple(dst, dstPort);

		Set<Long> asws = getAccessSwitches();
		
		// now, for every pair of access switches,
		for ( long s : asws ) {
			for ( long d : asws ) {
				// we don't do the removal for the reverse direction.
				// can this be some source of evil?
				if ( s <= d ) continue;
				
				 Route r = this.routingService.getRoute(s, d);
				 if  (r == null) 
					 // topology is not ready right now.
					 continue;
				 
				 BloomFilter<NodePortTuple> bf = r.getBloomFilter();
				 if ( bf.mightContain(srcNpt) || bf.mightContain(dstNpt) ) {
					 removeAnyRouteFromNetwork(r);
				 }
			}
		}		
	}

	private Set<Long> getAccessSwitches() {
		// get all switch identifiers
		Set<Long> sws = this.controller.getSwitchIdentifiers();
		
		// this is a set of access switch identifiers
		Set<Long> asws = new HashSet<Long>();
		
		for ( long s : sws ) {
			Set<OFPort> ports = this.topologyService.getPorts(s);
			for ( OFPort p: ports ) {
				// if the switch has an attachment point port, 
				// then it's used as an access switch.
				if ( this.topologyService.isAttachmentPointPort(s, p) ) {
					// if this is an access switch, we include it in the calculation.
					asws.add(s);
					break;
				}
			}
		}
		return asws;
	}

	/**
	 * Remove a route from network by eliminating flow-mod records
	 * @param r			Route object
	 */
	private void removeRouteFromNetwork(Route r) {
		for ( NodePortTuple npt: r.getPath()) {
			removeOutgoingFlowRecord( npt.getNodeId(), npt.getPortId() );
		}
	}
	
	private void removeAnyRouteFromNetwork(Route r) {
		for ( NodePortTuple npt: r.getPath()) {
			removeOutgoingFlowRecord( npt.getNodeId(), OFPort.ANY );
		}
	}
	
	
	/**
	 * Remove Flow-mod record from the switch
	 * 
	 * @param srcId		long identifier of the switch
	 * @param outPort	outgoing port where the flow-del would be applied
	 */
	private void removeOutgoingFlowRecord(long srcId, OFPort outPort) {
		IOFSwitch sw = this.getController().getSwitch(srcId);
		if ( sw == null ) {
			return;
		}
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		
		OFFlowDelete.Builder del = fac.buildFlowDelete();
		try {
			del
			.setCookie(U64.of(this.cookie))
			.setOutPort(outPort)
			.setMatch(fac.matchWildcardAll())
			.setCookieMask(U64.of(0xffffffffffffffffL))				// >1.3
			.setTableId(TableId.ALL);								// >1.3
		} catch ( UnsupportedOperationException u ) {
			// does nothing.
		}
		
		sw.getConnection().write( del.build() );
	}
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		// no service to implement
		return Collections.emptyList();
	}

	@Override
	protected void initialize() {
		this.topologyService = 
				(ITopologyService) OFModule.getModule(ITopologyService.class);
		this.routingService = 
				(IRoutingService) OFModule.getModule(IRoutingService.class);

		this.topologyService.addListener(this);
		
		this.processor = new LDUpdateProcessor(this);
		
		// start the token processor
		this.processor.start();
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return true;
	}
	
	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		return null;
	}

}
