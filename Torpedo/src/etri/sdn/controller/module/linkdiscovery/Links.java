package etri.sdn.controller.module.linkdiscovery;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.openflow.protocol.ver1_0.messages.OFPortDesc;
import org.openflow.protocol.ver1_0.messages.OFPortStatus;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener.UpdateOperation;

/**
 * Model of the module {@link OFMLinkDiscovery}
 * 
 * @author bjlee
 *
 */
public class Links extends OFModel {
	
	/**
	 * Timeout as part of LLDP process
	 */
	protected final int LINK_TIMEOUT = 35; 
	
	/**
	 * Lock for guaranteeing synchronized access to {@link #links};
	 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Association between a link and the link information
	 */
	private Map<Link, LinkInfo> links = new HashMap<Link, LinkInfo>();

	/**
	 * Map from switch id to a set of all links with it as an endpoint
	 */
	private Map<Long, Set<Link>> switchLinks = new HashMap<Long, Set<Link>>();

	/**
	 * Map from a id:port to the set of links containing it as an endpoint
	 */
	private Map<NodePortTuple, Set<Link>> portLinks = new HashMap<NodePortTuple, Set<Link>>();

	/**
	 * Set of link tuples over which multicast LLDPs are received
	 * and unicast LLDPs are not received.
	 */
	private Map<NodePortTuple, Set<Link>> portBroadcastDomainLinks = new HashMap<NodePortTuple, Set<Link>>();
	
	/**
	 * reference to the module of this model.
	 */
	private OFMLinkDiscovery manager;
	
	/**
	 * Constructor
	 * 
	 * @param manager	reference to the module of this model
	 */
	public Links(OFMLinkDiscovery manager) {
		this.manager = manager;
	}
	
	/** 
	 * Iterates through the list of links and deletes if the
	 * last discovery message reception time exceeds timeout values.
	 */
	public void timeoutLinks() {
		List<Link> eraseList = new ArrayList<Link>();
		Long curTime = System.currentTimeMillis();
		boolean linkChanged = false;

		// reentrant required here because deleteLink also write locks
		lock.writeLock().lock();
		try {
			Iterator<Entry<Link, LinkInfo>> it =
				this.links.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Link, LinkInfo> entry = it.next();
				Link lt = entry.getKey();
				LinkInfo info = entry.getValue();

				// Timeout the unicast and multicast LLDP valid times
				// independently.
				if ((info.getUnicastValidTime() != null) && 
						(info.getUnicastValidTime() + (this.LINK_TIMEOUT * 1000) < curTime)){
					info.setUnicastValidTime(null);

					if (info.getMulticastValidTime() != null)
						addLinkToBroadcastDomain(lt);
					// Note that even if mTime becomes null later on,
					// the link would be deleted, which would trigger updateClusters().
					linkChanged = true;
				}
				if ((info.getMulticastValidTime()!= null) && 
						(info.getMulticastValidTime()+ (this.LINK_TIMEOUT * 1000 ) < curTime)) {
					info.setMulticastValidTime(null);
					// if uTime is not null, then link will remain as openflow
					// link. If uTime is null, it will be deleted.  So, we
					// don't care about linkChanged flag here.
					removeLinkFromBroadcastDomain(lt);
					linkChanged = true;
				}
				// Add to the erase list only if the unicast
				// time is null.
				if (info.getUnicastValidTime() == null && 
						info.getMulticastValidTime() == null){
					eraseList.add(entry.getKey());
				} else if (linkChanged) {
					this.manager.addLinkUpdate(lt, info);
				}
			}

			// if any link was deleted or any link was changed.
			if ((eraseList.size() > 0) || linkChanged) {
				// Reason: LLDP Timeout
				deleteLinks(eraseList);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Put a link into the map of {@link #portBroadcastDomainLinks}. 
	 * Broadcast domain links are the links found via BDDP packets. 
	 * 
	 * @param lt	link to put into the list.
	 */
	public void addLinkToBroadcastDomain(Link lt) {
		NodePortTuple srcNpt, dstNpt;
		srcNpt = new NodePortTuple(lt.getSrc(), lt.getSrcPort());
		dstNpt = new NodePortTuple(lt.getDst(), lt.getDstPort());

		if ( !portBroadcastDomainLinks.containsKey(srcNpt) ) 
			portBroadcastDomainLinks.put(srcNpt, new HashSet<Link>());
		portBroadcastDomainLinks.get(srcNpt).add(lt);

		if ( !portBroadcastDomainLinks.containsKey(dstNpt) )
			portBroadcastDomainLinks.put(dstNpt, new HashSet<Link>());
		portBroadcastDomainLinks.get(dstNpt).add(lt);
	}
	
	/**
	 * Remove a link from the map of {@link #portBroadcastDomainLinks}.
	 * Broadcast domain links are the links found via BDDP packets. 
	 * 
	 * @param lt	link to remove
	 */
	public void removeLinkFromBroadcastDomain(Link lt) {
		NodePortTuple srcNpt, dstNpt;
		srcNpt = new NodePortTuple(lt.getSrc(), lt.getSrcPort());
		dstNpt = new NodePortTuple(lt.getDst(), lt.getDstPort());

		if (portBroadcastDomainLinks.containsKey(srcNpt)) {
			portBroadcastDomainLinks.get(srcNpt).remove(lt);
			if (portBroadcastDomainLinks.get(srcNpt).isEmpty())
				portBroadcastDomainLinks.remove(srcNpt);
		}

		if (portBroadcastDomainLinks.containsKey(dstNpt)) {
			portBroadcastDomainLinks.get(dstNpt).remove(lt);
			if (portBroadcastDomainLinks.get(dstNpt).isEmpty())
				portBroadcastDomainLinks.remove(dstNpt);
		}
	}
	
	/**
	 * Delete all links associated with a {@link NodePortTuple} object.
	 * 
	 * @param npt	a node-port tuple
	 */
	public void deleteLinksOnPort(NodePortTuple npt) {
		List<Link> eraseList = new ArrayList<Link>();
		if (this.portLinks.containsKey(npt)) {
			eraseList.addAll(this.portLinks.get(npt));
			deleteLinks(eraseList);
		}

		// send port-down notification to all listeners
		this.manager.addLinkUpdate(npt.getNodeId(), npt.getPortId(), UpdateOperation.PORT_DOWN);
	}
	
//	public void deleteLinksOnPortCompletely(NodePortTuple npt) {
//		List<Link> eraseList = new ArrayList<Link>();
//		if (this.portLinks.containsKey(npt)) {
//			eraseList.addAll(this.portLinks.get(npt));
//			deleteLinks(eraseList);
//		}
//		
//		this.manager.addLinkUpdate(npt.getNodeId(), npt.getPortId(), UpdateOperation.LINK_REMOVED);
//	}
	
	
	/**
	 * Add or update link information. used by OFMLinkDiscovery.handleLldp method. 
	 * After creating the {@link Link} and {@link LinkInfo} object, 
	 * (remote is set to source, and local is set to destination)
	 * this method first calls {@link #addOrUpdateLink(Link, LinkInfo)} method.
	 * Then, if the {@link LinkInfo} is correctly set, 
	 * search the {@link LinkInfo} object for the reverse link.
	 * If none, this method sends out a LLDP discovery for the reverse link 
	 * by calling {@link OFMLinkDiscovery#sendDiscoveryMessage(long, short, boolean, boolean)}. 
	 * 
	 * For a BDDP packet, this method just tries to enrolls the reverse link information
	 * by calling {@link #addOrUpdateLink(Link, LinkInfo)}.
	 * 
	 */
	public Link addOrUpdateLink(
			long remoteSwitchId, 
			short remotePort,
//			OFPhysicalPort remotePhyPort, 
			OFPortDesc remotePhyPort,
			long localSwitchId, 
			short localInPort,
//			OFPhysicalPort localInPhyPort,
			OFPortDesc localInPhyPort,
			boolean isStandard,
			boolean isReverse) 
	{
		int srcPortState = (remotePhyPort != null) ? remotePhyPort.getState() : 0;
		int dstPortState = (localInPhyPort != null) ? localInPhyPort.getState() : 0;
		
		// Store the time of update to this link, and push it out to routingEngine
		Link lt = new Link(remoteSwitchId, remotePort, localSwitchId, localInPort);

		Long lastLldpTime = null;
		Long lastBddpTime = null;

		Long firstSeenTime = System.currentTimeMillis();

		if (isStandard)
			lastLldpTime = System.currentTimeMillis();
		else
			lastBddpTime = System.currentTimeMillis();

		LinkInfo newLinkInfo =
			new LinkInfo(firstSeenTime, lastLldpTime, lastBddpTime,
					srcPortState, dstPortState);

		addOrUpdateLink(lt, newLinkInfo);

		// Check if reverse link exists. 
		// If it doesn't exist and if the forward link was seen 
		// first seen within a small interval, send probe on the 
		// reverse link.

		lock.readLock().lock();
		try {
			newLinkInfo = links.get(lt);
			if (newLinkInfo != null && isStandard && isReverse == false) {
				Link reverseLink = new Link(lt.getDst(), lt.getDstPort(),
						lt.getSrc(), lt.getSrcPort());
				LinkInfo reverseInfo = links.get(reverseLink);
				if (reverseInfo == null) {
					// the reverse link does not exist.
					if (newLinkInfo.getFirstSeenTime() > System.currentTimeMillis() - LINK_TIMEOUT) {
						this.manager.sendDiscoveryMessage(
								lt.getDst(),
								lt.getDstPort(), 
								isStandard, 
								true);
					}
				}
			}
		} finally {
			lock.readLock().unlock();
		}

		// If the received packet is a BDDP packet, then create a reverse BDDP
		// link as well.
		if (!isStandard) {
			Link reverseLink = new Link(lt.getDst(), lt.getDstPort(),
					lt.getSrc(), lt.getSrcPort());

			// srcPortState and dstPort state are reversed.
			LinkInfo reverseInfo =
				new LinkInfo(firstSeenTime, lastLldpTime, lastBddpTime,
						dstPortState, srcPortState);

			addOrUpdateLink(reverseLink, reverseInfo);
		}
		
		return lt;
	}
	
	/**
	 * Update port status. In result, listeners are notified on the link update.
	 * Used by OFMLinkDiscovery.handlePortStatus method.
	 * 
	 * @param switchId	switch identifier
	 * @param portnum	port number
	 * @param ps		OFPortStatus message
	 * @return
	 */
	public boolean updatePortStatus(Long switchId, short portnum, OFPortStatus ps) {
		
		NodePortTuple npt = new NodePortTuple(switchId, portnum);
		
		boolean linkInfoChanged = false;
		
		lock.writeLock().lock();
		try {
			if (this.portLinks.containsKey(npt)) {
				for (Link lt: this.portLinks.get(npt)) {
					LinkInfo linkInfo = links.get(lt);
					assert(linkInfo != null);
					Integer updatedSrcPortState = null;
					Integer updatedDstPortState = null;
					// update if source port state has been changed
					if (lt.getSrc() == npt.getNodeId() && 
						lt.getSrcPort() == npt.getPortId() &&
						(linkInfo.getSrcPortState() != ps.getDesc().getState())) {
						updatedSrcPortState = ps.getDesc().getState();
						linkInfo.setSrcPortState(updatedSrcPortState);
					}
					// update if destination port state has been changed
					if (lt.getDst() == npt.getNodeId() &&
						lt.getDstPort() == npt.getPortId() &&
						(linkInfo.getDstPortState() != ps.getDesc().getState())) {
						updatedDstPortState = ps.getDesc().getState();
						linkInfo.setDstPortState(updatedDstPortState);
					}
					if ((updatedSrcPortState != null) || 
						(updatedDstPortState != null)) {
						// The link is already known to link discovery
						// manager and the status has changed, therefore
						// send an LDUpdate.
						this.manager.addLinkUpdate(lt, linkInfo);
						linkInfoChanged = true;
					}
				}
			}
		} finally { 
			lock.writeLock().unlock();
		}

		this.manager.addLinkUpdate(switchId, portnum, ps.getDesc().getState());
		
		return linkInfoChanged;
	}
	
	/**
	 * return the name of this Model.
	 */
	public String toString() {
		return "Links";
	}
	
	/**
	 * Get the string representation of {@link #portLinks}.
	 * 
	 * @return
	 */
	public String getStringRepresentation() {
		StringBuffer ret = new StringBuffer();
		for ( NodePortTuple npt : portLinks.keySet() ) {
			ret.append("\n Port and Links \n");
			ret.append("[").append(npt.toString()).append("\n");
			for( Link l : portLinks.get(npt) ) {
				ret.append("\t ").append(l.toString());
				ret.append("\n");
			}
		}
		ret.append("\n");
		return ret.toString();
	}
	
	/**
	 * Delete links from the {@link #switchLinks} and {@link #portLinks} structures.
	 * Called by {@link #deleteLinksOnPort(NodePortTuple)}.
	 * 
	 * @param links
	 */
	private void deleteLinks(List<Link> links) {
		NodePortTuple srcNpt, dstNpt;

		lock.writeLock().lock();
		try {
			for (Link lt : links) {
				srcNpt = new NodePortTuple(lt.getSrc(), lt.getSrcPort());
				dstNpt  =new NodePortTuple(lt.getDst(), lt.getDstPort());

				switchLinks.get(lt.getSrc()).remove(lt);
				switchLinks.get(lt.getDst()).remove(lt);
				if (switchLinks.containsKey(lt.getSrc()) &&
						switchLinks.get(lt.getSrc()).isEmpty())
					this.switchLinks.remove(lt.getSrc());
				if (this.switchLinks.containsKey(lt.getDst()) &&
						this.switchLinks.get(lt.getDst()).isEmpty())
					this.switchLinks.remove(lt.getDst());

				if (this.portLinks.get(srcNpt) != null) {
					this.portLinks.get(srcNpt).remove(lt);
					if (this.portLinks.get(srcNpt).isEmpty())
						this.portLinks.remove(srcNpt);
				}
				if (this.portLinks.get(dstNpt) != null) {
					this.portLinks.get(dstNpt).remove(lt);
					if (this.portLinks.get(dstNpt).isEmpty())
						this.portLinks.remove(dstNpt);
				}

				LinkInfo info = this.links.remove(lt);
				
				this.manager.addLinkUpdate(lt, info, UpdateOperation.LINK_REMOVED);

				//                // Update Event History
				//                evHistTopoLink(lt.getSrc(),
				//                               lt.getDst(),
				//                               lt.getSrcPort(),
				//                               lt.getDstPort(),
				//                               0, 0, // Port states
				//                               LinkType.INVALID_LINK,
				//                               EvAction.LINK_DELETED, reason);

				// remove link from storage.
				//                removeLinkFromStorage(lt);

				// TODO  Whenever link is removed, it has to checked if
				// the switchports must be added to quarantine.
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Add or Update a link information.
	 * 
	 * <ol>
	 * <li>If link was first found, the link goes into {@link #links} structure. 
	 * If the link was discovered via BDDP packets, the links goes to the 
	 * {@link #portBroadcastDomainLinks} by calling {@link #addLinkToBroadcastDomain(Link)}. 
	 * 
	 * <li>If the found link exists, the information is updated.
	 * If the discovery method changes between LLDP and BDDP, the link is added to 
	 * or removed from the {@link #portBroadcastDomainLinks} structure.
	 * </ol>
	 * 
	 * @param lt		target link to update or add
	 * @param newInfo	information of the link
	 * @return			true if a link info is successfully updated or added. false otherwise.
	 */
	private boolean addOrUpdateLink(Link lt, LinkInfo newInfo) {
		NodePortTuple srcNpt, dstNpt;
		boolean linkChanged = false;

		lock.writeLock().lock();
		try {
			// put the new info.  if an old info exists, it will be returned.
			LinkInfo oldInfo = links.put(lt, newInfo);
			
			// we preserve the original LinkInfo object's first seen time. 
			if (oldInfo != null &&
					oldInfo.getFirstSeenTime() < newInfo.getFirstSeenTime())
				newInfo.setFirstSeenTime(oldInfo.getFirstSeenTime());

			linkChanged = false;

			srcNpt = new NodePortTuple(lt.getSrc(), lt.getSrcPort());
			dstNpt = new NodePortTuple(lt.getDst(), lt.getDstPort());

			if (oldInfo == null) {
				// if there was no old LinkInfo object, 
				// linkChanged is changed to true without exception. 
				// we manually add all Link object to all data structures.
				
				// index it by switch source
				if (!switchLinks.containsKey(lt.getSrc()))
					switchLinks.put(lt.getSrc(), new HashSet<Link>());
				switchLinks.get(lt.getSrc()).add(lt);

				// index it by switch dest
				if (!switchLinks.containsKey(lt.getDst()))
					switchLinks.put(lt.getDst(), new HashSet<Link>());
				switchLinks.get(lt.getDst()).add(lt);

				// index both ends by switch:port
				if (!portLinks.containsKey(srcNpt))
					portLinks.put(srcNpt, new HashSet<Link>());
				portLinks.get(srcNpt).add(lt);

				if (!portLinks.containsKey(dstNpt))
					portLinks.put(dstNpt, new HashSet<Link>());
				portLinks.get(dstNpt).add(lt);

				// Add to portNOFLinks if the unicast valid time is null
				// getUnicastValidTime() returns null if the link was found
				// via BDDP packet
				if (newInfo.getUnicastValidTime() == null)
					addLinkToBroadcastDomain(lt);

				//              writeLinkToStorage(lt, newInfo);
				linkChanged = true;
				
				// Now as the new Link information has been added,
				// we should notify all the listeners about that. 
				this.manager.addLinkUpdate(lt, newInfo, UpdateOperation.LINK_UPDATED);

			} else {
				// Since the link info is already there, we need to
				// update the right fields.
				
				// getUnicastValidTime() returns the time that the last time 
				// that a LLDP packet is received. 
				if (newInfo.getUnicastValidTime() == null) {
					// This is due to a multicast LLDP, so copy the old unicast
					// value.
					if (oldInfo.getUnicastValidTime() != null) {
						newInfo.setUnicastValidTime(oldInfo.getUnicastValidTime());
					}
				} 
				// getMulticastValidTime() returns the time that the last time
				// that a BDDP packet is received.
				else if (newInfo.getMulticastValidTime() == null) {
					// This is due to a unicast LLDP, so copy the old multicast
					// value.
					if (oldInfo.getMulticastValidTime() != null) {
						newInfo.setMulticastValidTime(oldInfo.getMulticastValidTime());
					}
				}

				Long oldTime = oldInfo.getUnicastValidTime();
				Long newTime = newInfo.getUnicastValidTime();
				// the link has changed its state between openflow and non-openflow
				// if the unicastValidTimes are null or not null
				if (oldTime != null & newTime == null) {
					// openflow -> non-openflow transition
					// we need to add the link tuple to the portNOFLinks
					addLinkToBroadcastDomain(lt);
					linkChanged = true;
				} else if (oldTime == null & newTime != null) {
					// non-openflow -> openflow transition
					// we need to remove the link from the portNOFLinks
					removeLinkFromBroadcastDomain(lt);
					linkChanged = true;
				}

				// Only update the port states if they've changed
				if (newInfo.getSrcPortState().intValue() !=
					oldInfo.getSrcPortState().intValue() ||
					newInfo.getDstPortState().intValue() !=
						oldInfo.getDstPortState().intValue())
					linkChanged = true;

				// Write changes to storage. This will always write the updated
				// valid time, plus the port states if they've changed (i.e. if
				// they weren't set to null in the previous block of code.
				//              writeLinkToStorage(lt, newInfo);

				if (linkChanged) {
					this.manager.addLinkUpdate(lt, newInfo);
				}
			}

		} finally {
			lock.writeLock().unlock();
		}

		return linkChanged;
	}
	
	/*
	 * OFModel methods
	 */
	
	/** 
	 * class to convert Link information into JSON format.
	 *
	 */
	class RESTLink {
		@JsonProperty("src-switch")
		public String srcdpid;
		
		@JsonProperty("src-port")
		public short srcport;
		
		@JsonProperty("src-port-state")
		public int srcstatus;
		
		@JsonProperty("dst-switch")
		public String dstdpid;
		
		@JsonProperty("dst-port")
		public short dstport;
		
		@JsonProperty("dst-port-state")
		public int dststatus;
		
		public String type;
		
		public RESTLink (Link l) {
			byte[] bDPID = ByteBuffer.allocate(8).putLong(l.getSrc()).array();
			this.srcdpid = String.format("%02x:%02x:%02x:%02x:%02x:%02x:%02x:%02x",
					bDPID[0], bDPID[1], bDPID[2], bDPID[3], bDPID[4], bDPID[5], bDPID[6], bDPID[7]);
			
			bDPID = ByteBuffer.allocate(8).putLong(l.getDst()).array();
			this.dstdpid = String.format("%02x:%02x:%02x:%02x:%02x:%02x:%02x:%02x",
					bDPID[0], bDPID[1], bDPID[2], bDPID[3], bDPID[4], bDPID[5], bDPID[6], bDPID[7]);
			
			this.srcport = l.getSrcPort();
			this.dstport = l.getDstPort();
			
			LinkInfo linkInfo = links.get( l );
			this.srcstatus = linkInfo.getSrcPortState();
			this.dststatus = linkInfo.getDstPortState();
			this.type = linkInfo.getLinkType().toString();
		}
	}
	
	/**
	 * All RESTApi objects associated with this model.
	 */
	private RESTApi[] apis = {
			
		/**
		 * This API returns the list of all links.
		 */
		new RESTApi(
			"/wm/topology/links/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					// create an object mapper.
					ObjectMapper om = new ObjectMapper();

					// retrieve all link information as JSON.
					List<RESTLink> list = new LinkedList<RESTLink>();
					for ( Link l : links.keySet() ){
						list.add( new RESTLink (l) );
					}

					try {
						String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(list);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		),
		
		/**
		 * TBD
		 */
		new RESTApi(
			"/wm/topology/linkinfos/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					// TBD:
				}
			}
		)
	};

	/**
	 * return the list of all RESTApi objects.
	 * 
	 * @return		an array of RESTApi objects
	 */
	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}

}