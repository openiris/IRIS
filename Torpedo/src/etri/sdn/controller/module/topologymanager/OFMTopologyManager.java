package etri.sdn.controller.module.topologymanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowDelete;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;

import etri.sdn.controller.IOFTask;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscovery.LinkType;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryService;
import etri.sdn.controller.module.linkdiscovery.Link;
import etri.sdn.controller.module.linkdiscovery.NodePortTuple;
import etri.sdn.controller.module.routing.IRoutingService;
import etri.sdn.controller.module.routing.Route;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.BSN;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.LLDP;
import etri.sdn.controller.util.Logger;

/**
 * Topology Manager Module.
 * This module implements ITopologyService.
 * 
 * Slightly modified by
 * @author SaeHyong Park (labry@etri.re.kr)
 *
 */
public class OFMTopologyManager extends OFModule implements ITopologyService, ILinkDiscoveryListener, IRoutingService {
	/** 
	 * Set of ports for each switch
	 */
	protected Map<Long, Set<OFPort>> switchPorts;

	/**
	 * Set of links organized by node port tuple
	 */
	protected Map<NodePortTuple, Set<Link>> switchPortLinks;

	/**
	 * Set of direct links
	 */
	protected Map<NodePortTuple, Set<Link>> directLinks;    

	/**
	 * set of links that are broadcast domain links.
	 */
	protected Map<NodePortTuple, Set<Link>> portBroadcastDomainLinks;    

	/**
	 * set of tunnel links
	 */
	protected Map<NodePortTuple, Set<Link>> tunnelLinks;  

	// These must be accessed using getCurrentInstance(), not directly
	protected TopologyInstance currentInstance;
	protected TopologyInstance currentInstanceWithoutTunnels;

	/**
	 * Flag that indicates if links (direct/tunnel/multihop links) were
	 * updated as part of LDUpdate.
	 */
	protected boolean linksUpdated;
	/**
	 * Flag that indicates if direct or tunnel links were updated as
	 * part of LDUpdate.
	 */
	protected boolean dtLinksUpdated;

	protected ILinkDiscoveryService linkDiscovery;


	// Modules that listen to our updates
	protected ArrayList<ITopologyListener> topologyAware;

	protected BlockingQueue<LDUpdate> ldUpdates;
	protected List<LDUpdate> appliedUpdates;

	private Date lastUpdateTime;
	
	private Topology topologyModel;
	
	private OFProtocol protocol;

	@Override
	public void initialize() {
		
		topologyModel = new Topology(this);
		
		linkDiscovery = (ILinkDiscoveryService) getModule(ILinkDiscoveryService.class);
		linkDiscovery.addListener(this);
		
		protocol = (OFProtocol) getController().getProtocol();

		registerModule(ITopologyService.class, this);
		registerModule(IRoutingService.class, this);

		// I will receive all PACKET_IN messages.
		registerFilter(
				OFType.PACKET_IN, 
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						return true;
					}
				}
		);

		init();
//		initiatePeriodicTopologyUpdate();
	}
	
	protected void initiatePeriodicTopologyUpdate() {

		// registered task will be re-executed every DISCOVERY_TASK_INTERVAL seconds.
		this.controller.scheduleTask(
				new IOFTask() {

					@Override
					public boolean execute() {
						updateTopology();
						return true;
					}

				},
				1 * 100 /* milliseconds */);
	}

	public void init() {
		switchPorts = new HashMap<Long,Set<OFPort>>();
		switchPortLinks = new HashMap<NodePortTuple, Set<Link>>();
		directLinks = new HashMap<NodePortTuple, Set<Link>>();
		portBroadcastDomainLinks = new HashMap<NodePortTuple, Set<Link>>();
		tunnelLinks = new HashMap<NodePortTuple, Set<Link>>();
		topologyAware = new ArrayList<ITopologyListener>();
		ldUpdates = new LinkedBlockingQueue<LDUpdate>();
		appliedUpdates = new ArrayList<LDUpdate>();
		clearCurrentTopology();
	}

	public boolean updateTopology() {
		boolean newInstanceFlag;
		linksUpdated = false;
		dtLinksUpdated = false;
		applyUpdates();
		newInstanceFlag = createNewInstance();
		lastUpdateTime = new Date();
		informListeners();
		return newInstanceFlag;
	}

	/**
	 * This is starting point of OFMTopologyManager
	 * 
	 * @param conn			
	 * @param context		MessageContext
	 * @param outgoing		list of OFMessage objects to be delivered to switches after this method ends the execution
	 * @return				true to further process the message, or false.
	 */
	@Override
	public boolean handleMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing) {

		switch (msg.getType()) {
		case PACKET_IN:
			return this.processPacketInMessage(conn.getSwitch(), (OFPacketIn) msg, context, outgoing);

		default:
			break;
		}

		return true;
	}
	
	@Override
	protected boolean handleDisconnect(Connection conn) {
		// does nothing currently
		return true;
	}

	public TopologyInstance getCurrentInstance(boolean tunnelEnabled) {
		if (tunnelEnabled)
			return currentInstance;
		else return this.currentInstanceWithoutTunnels;
	}

	public TopologyInstance getCurrentInstance() {
		return this.getCurrentInstance(true);
	}

	public boolean isAllowed(long sw, OFPort portId) {
		return isAllowed(sw, portId, true);
	}

	public boolean isAllowed(long sw, OFPort portId, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.isAllowed(sw, portId);
	}
	
	protected OFPort getInputPort(OFPacketIn pi) {
		if ( pi == null ) {
			throw new AssertionError("pi cannot refer null");
		}
		try {
			return pi.getInPort();
		} catch ( UnsupportedOperationException e ) {
			return pi.getMatch().get(MatchField.IN_PORT);
		}
	}


	// ****************
	// Internal methods
	// ****************
	/**
	 * If the packet-in switch port is disabled for all data traffic, then
	 * the packet will be dropped.  Otherwise, the packet will follow the
	 * normal processing chain.
	 * @param sw	switch 
	 * @param pi 	Packet-IN message 
	 * @return		true if correctly enforced, false otherwise
	 */
	protected boolean dropFilter(long sw, OFPacketIn pi) {
		boolean result = true;
		OFPort port = getInputPort(pi);

		// If the input port is not allowed for data traffic, drop everything.
		// BDDP packets will not reach this stage.
		if (isAllowed(sw, port) == false) {
			result = false;
		}

		// if sufficient information is available, then drop broadcast
		// packets here as well.
		return result;
	}

	/** 
	 * TODO This method must be moved to a layer below forwarding
	 * so that anyone can use it.
	 * @param packetData
	 * @param sw
	 * @param ports
	 */
	public void doMultiActionPacketOut(byte[] packetData, IOFSwitch sw, Set<OFPort> ports) {

		if (ports == null) return;
		if (packetData == null || packetData.length <= 0) return;
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		
		OFPacketOut.Builder po = fac.buildPacketOut();

		List<OFAction> actions = new ArrayList<OFAction>();
		for( OFPort p: ports ) {
			OFActionOutput.Builder action_out = fac.actions().buildOutput();
			actions.add( action_out.setPort(p).setMaxLen(0).build());
		}

		// set actions
		po
		.setActions(actions)
		.setBufferId(OFBufferId.NO_BUFFER)
		.setInPort(OFPort.ANY /* for 1.0, ANY is NONE */)
		.setData(packetData);
		
		sw.getConnection().write(po.build());
	}


	/**
	 * The BDDP packets are forwarded out of all the ports out of an
	 * openflowdomain.  Get all the switches in the same openflow
	 * domain as the sw (disabling tunnels).  Then get all the 
	 * external switch ports and send these packets out.
	 * @param pinSwitch		switch datapath id
	 * @param pi			packet-in message
	 */
	protected void doFloodBDDP(long pinSwitch, OFPacketIn pi) {
		
		TopologyInstance ti = getCurrentInstance(false);

		Set<Long> switches = ti.getSwitchesInOpenflowDomain(pinSwitch);

		if (switches == null)
		{
			// indicates no links are connected to the switches
			switches = new HashSet<Long>();
			switches.add(pinSwitch);
		}

		for(long sid: switches) {
			IOFSwitch sw = controller.getSwitch(sid); //floodlightProvider.getSwitches().get(sid);
			if (sw == null) continue;
			Collection<OFPort> enabledPorts = protocol.getEnabledPortNumbers(sw);
			if (enabledPorts == null)
				continue;
			Set<OFPort> ports = new HashSet<OFPort>();
			ports.addAll(enabledPorts);

			// all the ports known to topology // without tunnels.
			// out of these, we need to choose only those that are 
			// broadcast port, otherwise, we should eliminate.
			Set<OFPort> portsKnownToTopo = ti.getPortsWithLinks(sid);

			if (portsKnownToTopo != null) {
				for(OFPort p: portsKnownToTopo) {
					NodePortTuple npt = new NodePortTuple(sid, p);
					if (ti.isBroadcastDomainPort(npt) == false) {
						ports.remove(p);
					}
				}
			}

			// remove the incoming switch port
			if (pinSwitch == sid) {
				ports.remove(getInputPort(pi));
			}

			// we have all the switch ports to which we need to broadcast.
//			doMultiActionPacketOut(pi.getPacketData(), sw, ports);
			doMultiActionPacketOut(pi.getData(), sw, ports);
		}

	}

	/**
	 * From the OFPacketIn message received, this method performs following procedures.
	 * 
	 * <ol>
	 * <li> parse Ethernet header
	 * <li> check is the OFPacketIn is a BSN (BigSwitch Network) LLDP message
	 * <li> in case BSN LLDP, call doFloodBDDP
	 * </ol>
	 * 
	 * @param sw			the switch that the pi message is received
	 * @param pi			OFPacketIn message itself	
	 * @param context		MessageContext.
	 * @param outgoing		list of OFMessage objects to be delivered to switches after this method ends the execution.
	 * @return				true to further process the message, or false.
	 */
	private boolean processPacketInMessage(IOFSwitch sw, OFPacketIn pi, MessageContext context, List<OFMessage> outgoing) {

		// get the packet-in switch.
		Ethernet eth = (Ethernet) context.get(MessageContext.ETHER_PAYLOAD);

		if ( eth == null ) {
			// parse Ethernet header and put into the context
			eth = new Ethernet();
			eth.deserialize(pi.getData(), 0, pi.getData().length);
			context.put(MessageContext.ETHER_PAYLOAD, eth);
		}

		if (eth.getEtherType() == Ethernet.TYPE_BSN) {
			BSN bsn = (BSN) eth.getPayload();
			if (bsn == null) return false;
			if (bsn.getPayload() == null) return false;

			// It could be a packet other than BSN LLDP, therefore
			// continue with the regular processing.
			if (bsn.getPayload() instanceof LLDP == false)
				return true;

			doFloodBDDP(sw.getId(), pi);
		} else {
			return dropFilter(sw.getId(), pi);
		}
		return false;
	}

	public void addSwitch(long sid) {
		if (switchPorts.containsKey(sid) == false) {
			switchPorts.put(sid, new HashSet<OFPort>());
		}
	}

	private void addPortToSwitch(long s, OFPort p) {
		addSwitch(s);
		switchPorts.get(s).add(p);
	}

	/**
	 * Updates concerning switch disconnect and port down are not processed.
	 * LinkDiscoveryManager is expected to process those messages and send
	 * multiple link removed messages.  However, all the updates from
	 * LinkDiscoveryManager would be propagated to the listeners of topology.
	 */
	public void applyUpdates() {
		appliedUpdates.clear();
		LDUpdate update = null;
		while (ldUpdates.peek() != null) {
			try {
				update = ldUpdates.take();
			} catch (Exception e) {
				Logger.stderr("Error reading link discovery update.");
			}

			if (update.getOperation() == UpdateOperation.LINK_UPDATED) {
				addOrUpdateLink(update.getSrc(), update.getSrcPort(),
						update.getDst(), update.getDstPort(),
						update.getType());
			} else if (update.getOperation() == UpdateOperation.LINK_REMOVED){
				removeLink(update.getSrc(), update.getSrcPort(), 
						update.getDst(), update.getDstPort());
			}
			// Add to the list of applied updates.
			appliedUpdates.add(update);
		}
	}

	public void addOrUpdateLink(long srcId, OFPort srcPort, long dstId, 
								OFPort dstPort, LinkType type) {
		
		boolean flag1 = false, flag2 = false;

		Link link = new Link(srcId, srcPort, dstId, dstPort);
		addPortToSwitch(srcId, srcPort);
		addPortToSwitch(dstId, dstPort);

		addLinkToStructure(switchPortLinks, link);

		if (type.equals(LinkType.MULTIHOP_LINK)) {
			addLinkToStructure(portBroadcastDomainLinks, link);
			flag1 = removeLinkFromStructure(tunnelLinks, link);
			flag2 = removeLinkFromStructure(directLinks, link);
			dtLinksUpdated = flag1 || flag2;
		} else if (type.equals(LinkType.TUNNEL)) {
			addLinkToStructure(tunnelLinks, link);
			removeLinkFromStructure(portBroadcastDomainLinks, link);
			removeLinkFromStructure(directLinks, link);
			dtLinksUpdated = true;
		} else if (type.equals(LinkType.DIRECT_LINK)) {
			addLinkToStructure(directLinks, link);
			removeLinkFromStructure(tunnelLinks, link);
			removeLinkFromStructure(portBroadcastDomainLinks, link);
			dtLinksUpdated = true;
		}
		linksUpdated = true;
		
		// purge all useless flow records.
		purgeAllFlowRecords(srcId, srcPort, dstId, dstPort);		
	}

	/**
	 * This method is to fastly convery to newer topology my eliminating 
	 * useless and outdated flow records from the network.
	 * TODO: the scalability problem of this method should be fixed.
	 * TODO: this method does not achieve global optimization.
	 * 
	 * @param srcId
	 * @param dstId
	 */
	private void purgeAllFlowRecords(long srcId, OFPort srcPort, long dstId, OFPort dstPort) {
		IOFSwitch src = this.controller.getSwitch(srcId);
		IOFSwitch dst = this.controller.getSwitch(dstId);
		List<IOFSwitch> switches = new LinkedList<IOFSwitch>();
		if ( src != null ) switches.add(src);
		if ( dst != null ) switches.add(dst);
		for ( IOFSwitch s : switches ) {
			Collection<OFPort> ports = this.protocol.getEnabledPortNumbers(s);
			for ( OFPort p: ports ) {
				if ( !p.equals(srcPort) ) {
					this.removeOutgoingFlowRecord(s.getId(), p);
				}
			}
		}		
	}

	public void removeLink(Link link)  {
		boolean flag1 = false, flag2 = false;

		flag1 = removeLinkFromStructure(directLinks, link);
		flag2 = removeLinkFromStructure(tunnelLinks, link);

		linksUpdated = true;
		dtLinksUpdated = flag1 || flag2;

		removeLinkFromStructure(portBroadcastDomainLinks, link);
		removeLinkFromStructure(switchPortLinks, link);

		NodePortTuple srcNpt = 
			new NodePortTuple(link.getSrc(), link.getSrcPort());
		NodePortTuple dstNpt = 
			new NodePortTuple(link.getDst(), link.getDstPort());

		// Remove switch ports if there are no links through those switch ports
		if (switchPortLinks.get(srcNpt) == null) {
			if (switchPorts.get(srcNpt.getNodeId()) != null)
				switchPorts.get(srcNpt.getNodeId()).remove(srcNpt.getPortId());
		}
		if (switchPortLinks.get(dstNpt) == null) {
			if (switchPorts.get(dstNpt.getNodeId()) != null)
				switchPorts.get(dstNpt.getNodeId()).remove(dstNpt.getPortId());
		}

		// Remove the node if no ports are present
		if (switchPorts.get(srcNpt.getNodeId())!=null && 
				switchPorts.get(srcNpt.getNodeId()).isEmpty()) {
			switchPorts.remove(srcNpt.getNodeId());
		}
		if (switchPorts.get(dstNpt.getNodeId())!=null && 
				switchPorts.get(dstNpt.getNodeId()).isEmpty()) {
			switchPorts.remove(dstNpt.getNodeId());
		}
	}

	public void removeLink(long srcId, OFPort srcPort,
						   long dstId, OFPort dstPort) {
		Link link = new Link(srcId, srcPort, dstId, dstPort);
		removeLink(link);
		removeUselessFlowRecord(link);
	}

	private void removeOutgoingFlowRecord(long srcId, OFPort outPort) {
		IOFSwitch sw = this.getController().getSwitch(srcId);
		if ( sw == null ) {
			return;
		}
		
		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		
		OFFlowDelete.Builder del = fac.buildFlowDelete();
		Match.Builder dm = fac.buildMatch();
		try {
			del
			.setOutPort(outPort)
			.setMatch(dm.build())
			.setTableId(TableId.ZERO);
		} catch ( UnsupportedOperationException u ) {
			// does nothing.
		}
		
		sw.getConnection().write( del.build() );
	}

	private void removeUselessFlowRecord(Link link) {
		removeOutgoingFlowRecord(link.getSrc(), link.getSrcPort());
		removeOutgoingFlowRecord(link.getDst(), link.getDstPort());
	}

	public void clear() {
		switchPorts.clear();
		switchPortLinks.clear();
		portBroadcastDomainLinks.clear();
		tunnelLinks.clear();
		directLinks.clear();
		appliedUpdates.clear();
	}

	/**
	 * Clears the current topology. Note that this does NOT
	 * send out updates.
	 */
	public void clearCurrentTopology() {
		this.clear();
		linksUpdated = true;
		dtLinksUpdated = true;
		createNewInstance();
		lastUpdateTime = new Date();
	}

	/**
	 * Add the given link to the data structure.  Returns true if a link was
	 * added.
	 * @param s		Map<NodePortTuple, Set<Link>>
	 * @param l		Link
	 * @return	true if link is successfully added, false otherwise
	 */
	private boolean addLinkToStructure(Map<NodePortTuple, Set<Link>> s, Link l) {
		boolean result1 = false, result2 = false; 

		NodePortTuple n1 = new NodePortTuple(l.getSrc(), l.getSrcPort());
		NodePortTuple n2 = new NodePortTuple(l.getDst(), l.getDstPort());

		if (s.get(n1) == null) {
			s.put(n1, new HashSet<Link>());
		}
		if (s.get(n2) == null) {
			s.put(n2, new HashSet<Link>());
		}
		result1 = s.get(n1).add(l);
		result2 = s.get(n2).add(l);

		return (result1 || result2);
	}


	/**
	 * Delete the given link from the data strucure.  Returns true if the
	 * link was deleted.
	 * @param s		Map<NodePortTuple, Set<Link>>
	 * @param l		Link
	 * @return		true if successfully removed, false otherwise
	 */
	private boolean removeLinkFromStructure(Map<NodePortTuple, Set<Link>> s, Link l) {

		boolean result1 = false, result2 = false;
		NodePortTuple n1 = new NodePortTuple(l.getSrc(), l.getSrcPort());
		NodePortTuple n2 = new NodePortTuple(l.getDst(), l.getDstPort());

		if (s.get(n1) != null) {
			result1 = s.get(n1).remove(l);
			if (s.get(n1).isEmpty()) s.remove(n1);
		}
		if (s.get(n2) != null) {
			result2 = s.get(n2).remove(l);
			if (s.get(n2).isEmpty()) s.remove(n2);
		}
		return result1 || result2;
	}

	/**
	 * This function computes a new topology.
	 */
	/**
	 * This function computes a new topology instance.
	 * It ignores links connected to all broadcast domain ports
	 * and tunnel ports. The method returns if a new instance of
	 * topology was created or not.
	 */
	protected boolean createNewInstance() {
		Set<NodePortTuple> blockedPorts = new HashSet<NodePortTuple>();

		if (!linksUpdated) return false;

		Map<NodePortTuple, Set<Link>> openflowLinks;
		openflowLinks = 
			new HashMap<NodePortTuple, Set<Link>>(switchPortLinks);

		// Remove all tunnel links.
		for(NodePortTuple npt: tunnelLinks.keySet()) {
			if (openflowLinks.get(npt) != null)
				openflowLinks.remove(npt);
		}

		// Remove all broadcast domain links.
		for(NodePortTuple npt: portBroadcastDomainLinks.keySet()) {
			if (openflowLinks.get(npt) != null)
				openflowLinks.remove(npt);
		}

		TopologyInstance nt = new TopologyInstance(switchPorts, 
				blockedPorts,
				openflowLinks, 
				portBroadcastDomainLinks.keySet(), 
				tunnelLinks.keySet());
		nt.compute();
		// We set the instances with and without tunnels to be identical.
		// If needed, we may compute them differently.
		currentInstance = nt;
		currentInstanceWithoutTunnels = nt;
		return true;
	}

	public void informListeners() {
		for(int i=0; i<topologyAware.size(); ++i) {
			ITopologyListener listener = topologyAware.get(i);
			listener.topologyChanged();
//			currentInstance.printTopology();
		}
	}


	@Override
	public boolean handleHandshakedEvent(Connection sw, MessageContext context) {
		return true;
	}

	@Override
	public void linkDiscoveryUpdate(LDUpdate update) {		
		boolean scheduleFlag = false;
		// if there's no udpates in the queue, then
		// we need to schedule an update.
		if (ldUpdates.peek() == null)
			scheduleFlag = true;

		ldUpdates.add(update);

		if (scheduleFlag) {
			this.controller.scheduleTask( new IOFTask() {

				@Override
				public boolean execute() {
					updateTopology();
					return false;
				}

			}, 1 /* millisecond */);
		}
	}

	@Override
	public void addListener(ITopologyListener listener) {
		topologyAware.add(listener);
	}

	@Override
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	@Override
	public boolean isAttachmentPointPort(long switchid, OFPort port) {
		return isAttachmentPointPort(switchid, port, true);
	}

	@Override
	public boolean isAttachmentPointPort(long switchid, OFPort port, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);

		// if the port is not attachment point port according to
		// topology instance, then return false
		if (ti.isAttachmentPointPort(switchid, port) == false)
			return false;

		// Check whether the port is a physical port. We should not learn
		// attachment points on "special" ports.
		if ((port.getShortPortNumber() & 0xff00) == 0xff00 && 
			 port != OFPort.LOCAL) return false;

		// Make sure that the port is enabled.
		IOFSwitch sw =  controller.getSwitch(switchid);
		if (sw == null) return false;
		
		return protocol.portEnabled(sw, port);
	}

	@Override
	public long getOpenflowDomainId(long switchId) {
		return getOpenflowDomainId(switchId, true);
	}

	@Override
	public long getOpenflowDomainId(long switchId, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getOpenflowDomainId(switchId);
	}

	@Override
	public long getL2DomainId(long switchId) {
		return getL2DomainId(switchId, true);
	}

	@Override
	public long getL2DomainId(long switchId, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getL2DomainId(switchId);
	}

	@Override
	public boolean inSameOpenflowDomain(long switch1, long switch2) {
		return inSameOpenflowDomain(switch1, switch2, true);
	}

	@Override
	public boolean inSameOpenflowDomain(long switch1, long switch2,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.inSameOpenflowDomain(switch1, switch2);
	}

	@Override
	public boolean inSameL2Domain(long switch1, long switch2) {
		return inSameL2Domain(switch1, switch2, true);
	}

	@Override
	public boolean inSameL2Domain(long switch1, long switch2,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.inSameL2Domain(switch1, switch2);
	}

	@Override
	public boolean isBroadcastDomainPort(long sw, OFPort port) {
		return isBroadcastDomainPort(sw, port, true);
	}

	@Override
	public boolean isBroadcastDomainPort(long sw, OFPort port,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.isBroadcastDomainPort(new NodePortTuple(sw, port));
	}

	@Override
	public boolean isConsistent(long oldSw, OFPort oldPort, long newSw,
			OFPort newPort) {
		return isConsistent(oldSw, oldPort,
				newSw, newPort, true);
	}

	@Override
	public boolean isConsistent(long oldSw, OFPort oldPort, long newSw,
			OFPort newPort, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.isConsistent(oldSw, oldPort, newSw, newPort);
	}

	@Override
	public boolean isInSameBroadcastDomain(long s1, OFPort p1, long s2, OFPort p2) {
		return isInSameBroadcastDomain(s1, p1, s2, p2, true);
	}

	@Override
	public boolean isInSameBroadcastDomain(long s1, OFPort p1, long s2,
			OFPort p2, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.inSameBroadcastDomain(s1, p1, s2, p2);
	}

	@Override
	public Set<OFPort> getPortsWithLinks(long sw) {
		return getPortsWithLinks(sw, true);
	}

	@Override
	public Set<OFPort> getPortsWithLinks(long sw, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getPortsWithLinks(sw);
	}

	@Override
	public Set<OFPort> getBroadcastPorts(long targetSw, long src, OFPort srcPort) {
		return getBroadcastPorts(targetSw, src, srcPort, true);
	}

	@Override
	public Set<OFPort> getBroadcastPorts(long targetSw, long src, OFPort srcPort,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getBroadcastPorts(targetSw, src, srcPort);
	}

	@Override
	public boolean isIncomingBroadcastAllowed(long sw, OFPort portId) {
		return isIncomingBroadcastAllowed(sw, portId, true);
	}

	@Override
	public boolean isIncomingBroadcastAllowed(long sw, OFPort portId,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.isIncomingBroadcastAllowedOnSwitchPort(sw, portId);
	}

	@Override
	public NodePortTuple getOutgoingSwitchPort(long src, OFPort srcPort,
			long dst, OFPort dstPort) {
		return getOutgoingSwitchPort(src, srcPort, dst, dstPort, true);
	}

	@Override
	public NodePortTuple getOutgoingSwitchPort(long src, OFPort srcPort,
			long dst, OFPort dstPort, boolean tunnelEnabled) {
		// Use this function to redirect traffic if needed.
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getOutgoingSwitchPort(src, srcPort,
				dst, dstPort);
	}

	@Override
	public NodePortTuple getIncomingSwitchPort(long src, OFPort srcPort,
			long dst, OFPort dstPort) {
		return getIncomingSwitchPort(src, srcPort, dst, dstPort, true);
	}

	@Override
	public NodePortTuple getIncomingSwitchPort(long src, OFPort srcPort,
			long dst, OFPort dstPort, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getIncomingSwitchPort(src, srcPort,
				dst, dstPort);
	}

	@Override
	public NodePortTuple getAllowedOutgoingBroadcastPort(long src,
			OFPort srcPort, long dst, OFPort dstPort) {
		return getAllowedOutgoingBroadcastPort(src, srcPort,
				dst, dstPort, true);
	}

	@Override
	public NodePortTuple getAllowedOutgoingBroadcastPort(long src,
			OFPort srcPort, long dst, OFPort dstPort, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getAllowedOutgoingBroadcastPort(src, srcPort,
				dst, dstPort);
	}

	@Override
	public NodePortTuple getAllowedIncomingBroadcastPort(long src, OFPort srcPort) {
		return getAllowedIncomingBroadcastPort(src,srcPort, true);
	}

	@Override
	public NodePortTuple getAllowedIncomingBroadcastPort(long src,
			OFPort srcPort, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getAllowedIncomingBroadcastPort(src,srcPort);
	}

	@Override
	public Set<NodePortTuple> getBroadcastDomainPorts() {
		return portBroadcastDomainLinks.keySet();
	}

	@Override
	public Set<NodePortTuple> getTunnelPorts() {
		return tunnelLinks.keySet();
	}

	@Override
	public Set<NodePortTuple> getBlockedPorts() {
		Set<NodePortTuple> bp;
		Set<NodePortTuple> blockedPorts =
			new HashSet<NodePortTuple>();

		// As we might have two topologies, simply get the union of
		// both of them and send it.
		bp = getCurrentInstance(true).getBlockedPorts();
		if (bp != null)
			blockedPorts.addAll(bp);

		bp = getCurrentInstance(false).getBlockedPorts();
		if (bp != null)
			blockedPorts.addAll(bp);

		return blockedPorts;
	}

	@Override
	public List<LDUpdate> getLastLinkUpdates() {
		return appliedUpdates;
	}

	@Override
	public Set<OFPort> getPorts(long sw) {
		Set<OFPort> ports = new HashSet<OFPort>();
		IOFSwitch iofSwitch = controller.getSwitch(sw);
		if (iofSwitch == null) return null;

		Collection<OFPort> ofpList = protocol.getEnabledPortNumbers(iofSwitch);
		
		if (ofpList == null) return null;

		Set<Integer> qPorts = linkDiscovery.getQuarantinedPorts(sw);
		if (qPorts != null)
			ofpList.removeAll(qPorts);

		ports.addAll(ofpList);
		return ports;
	}
	
	/*
	 * IRoutingService methods
	 */

	@Override
	public Route getRoute(long src, long dst) {
		return getRoute(src, dst, true);
	}

	@Override
	public Route getRoute(long src, long dst, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getRoute(src, dst);
	}

	@Override
	public Route getRoute(long src, OFPort srcPort, long dst, OFPort dstPort) {
		return getRoute(src, srcPort, dst, dstPort, true);
	}

	@Override
	public Route getRoute(long src, OFPort srcPort, long dst, OFPort dstPort,
			boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
		return ti.getRoute(src, srcPort, dst, dstPort);
	}

	@Override
	public boolean routeExists(long src, long dst) {
		return routeExists(src, dst, true);
	}

	@Override
	public boolean routeExists(long src, long dst, boolean tunnelEnabled) {
		TopologyInstance ti = getCurrentInstance(tunnelEnabled);
        return ti.routeExists(src, dst);
	}
	
	/*
	 * OFModule methods
	 */
	
	@Override
	public OFModel[] getModels() {
		// TODO Auto-generated method stub
		return  new OFModel[] { this.topologyModel };
	}
}
