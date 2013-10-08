/**
 *    Copyright 2012, Big Switch Networks, Inc. 
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

package etri.sdn.controller.protocol.io;

import java.io.IOException;
import java.net.SocketAddress;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

//import org.openflow.protocol.OFFeaturesReply;
//import org.openflow.protocol.OFFeaturesRequest;
//import org.openflow.protocol.OFFlowMod;
//import org.openflow.protocol.OFMatch;
//import org.openflow.protocol.OFMessage;
//import org.openflow.protocol.OFPhysicalPort;
//import org.openflow.protocol.OFPhysicalPort.OFPortConfig;
//import org.openflow.protocol.OFPhysicalPort.OFPortState;
//import org.openflow.protocol.OFPort;
//import org.openflow.protocol.OFStatisticsRequest;
//import org.openflow.protocol.OFType;
//import org.openflow.protocol.statistics.OFDescriptionStatistics;
//import org.openflow.protocol.statistics.OFStatistics;
import org.openflow.util.HexString;
//import org.openflow.util.U16;

import etri.sdn.controller.protocol.io.IOFHandler.Role;
//import etri.sdn.controller.util.Logger;
import etri.sdn.controller.util.TimedCache;

/**
 * This is the internal representation of an openflow switch.
 * This is a slight modification of OFSwitchImpl of Floodlight. 
 * 
 * @author bjlee
 */
public final class OFSwitchImpl implements IOFSwitch {

	@SuppressWarnings("unused")
	private static final String HA_CHECK_SWITCH = 
		"Check the health of the indicated switch.  If the problem " +
		"persists or occurs repeatedly, it likely indicates a defect " +
		"in the switch HA implementation.";

	private ConcurrentMap<Object, Object> attributes;
	private Date connectedSince;
	private Connection conn;
	private String stringId;
//	private OFDescriptionStatistics description;
	private AtomicInteger transactionIdSource;
	// Lock to protect modification of the port maps. We only need to 
	// synchronize on modifications. For read operations we are fine since
	// we rely on ConcurrentMaps which works for our use case.
//	private Object portLock;
	// Map port numbers to the appropriate OFPhysicalPort
//	private ConcurrentHashMap<Short, OFPhysicalPort> portsByNumber;
	// Map port names to the appropriate OFPhyiscalPort
	// XXX: The OF spec doesn't specify if port names need to be unique but
	//      according it's always the case in practice. 
//	private ConcurrentHashMap<String, OFPhysicalPort> portsByName;
	
	/**
	 * This field is used to exchange information with switch.
	 */
//	private ConcurrentHashMap<Integer, Object> responsesCache;

	private Role role;

    private TimedCache<Long> timedCache;
	private ConcurrentMap<Short, Long> portBroadcastCacheHitMap;

	/* Switch features from initial featuresReply */
//	private int capabilities;
//	private int buffers;
//	private int actions;
//	private byte tables;
	private long datapathId;

	public OFSwitchImpl() {
		this.stringId = null;
		this.attributes = new ConcurrentHashMap<Object, Object>();
		this.connectedSince = new Date();
		this.transactionIdSource = new AtomicInteger();
//		this.portLock = new Object();
//		this.portsByNumber = new ConcurrentHashMap<Short, OFPhysicalPort>();
//		this.portsByName = new ConcurrentHashMap<String, OFPhysicalPort>();
//		this.responsesCache = new ConcurrentHashMap<Integer, Object>();
		this.role = Role.MASTER;		// FIXME: bjlee

		this.timedCache = new TimedCache<Long>(100, 5*1000 );  // 5 seconds interval
		this.portBroadcastCacheHitMap = new ConcurrentHashMap<Short, Long>();

		// Defaults properties for an ideal switch
//		this.setAttribute(PROP_FASTWILDCARDS, OFMatch.OFPFW_ALL);
		this.setAttribute(PROP_FASTWILDCARDS, new Integer(0x3fffff)	/* OFPFW_ALL */);
		this.setAttribute(PROP_SUPPORTS_OFPP_FLOOD, new Boolean(true));
		this.setAttribute(PROP_SUPPORTS_OFPP_TABLE, new Boolean(true));
	}
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Connection getConnection() {
		return this.conn;
	}

	@Override
	public Object getAttribute(String name) {
		if (this.attributes.containsKey(name)) {
			return this.attributes.get(name);
		}
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
		return;
	}

	@Override
	public Object removeAttribute(String name) {
		return this.attributes.remove(name);
	}

	@Override
	public boolean hasAttribute(String name) {
		return this.attributes.containsKey(name);
	}

	
//	@Override
//	public void setFeaturesReply(OFFeaturesReply featuresReply) {
//		synchronized(portLock) {
//			if (stringId == null) {
//				
//				/* ports are updated via port status message, so we
//				 * only fill in ports on initial connection.
//				 */
//				
//				for (OFPhysicalPort port : featuresReply.getPorts()) {
//					setPort(port);
//				}
//			}
//			this.datapathId = featuresReply.getDatapathId();
//			this.capabilities = featuresReply.getCapabilities();
//			this.buffers = featuresReply.getBuffers();
//			this.actions = featuresReply.getActions();
//			this.tables = featuresReply.getTables();
//			this.stringId = HexString.toHexString(this.datapathId);
//		}
//	}

//	@Override
//	public Collection<OFPhysicalPort> getEnabledPorts() {
//		List<OFPhysicalPort> result = new ArrayList<OFPhysicalPort>();
//		for (OFPhysicalPort port : portsByNumber.values()) {
//			if (portEnabled(port)) {
//				result.add(port);
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public Collection<Short> getEnabledPortNumbers() {
//		List<Short> result = new ArrayList<Short>();
//		for (OFPhysicalPort port : portsByNumber.values()) {
//			if (portEnabled(port)) {
//				result.add(port.getPortNumber());
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public OFPhysicalPort getPort(short portNumber) {
//		return portsByNumber.get(portNumber);
//	}
//
//	@Override
//	public OFPhysicalPort getPort(String portName) {
//		return portsByName.get(portName);
//	}
//
//	@Override
//	public void setPort(OFPhysicalPort port) {
//		synchronized(portLock) {
//			portsByNumber.put(port.getPortNumber(), port);
//			portsByName.put(port.getName(), port);
//		}
//	}
//
//	@Override
//	public Collection<OFPhysicalPort> getPorts() {
//		return Collections.unmodifiableCollection(portsByNumber.values());
//	}
//
//	@Override
//	public void deletePort(short portNumber) {
//		synchronized(portLock) {
//			portsByName.remove(portsByNumber.get(portNumber).getName());
//			portsByNumber.remove(portNumber);
//		}
//	}
//
//	@Override
//	public void deletePort(String portName) {
//		synchronized(portLock) {
//			portsByNumber.remove(portsByName.get(portName).getPortNumber());
//			portsByName.remove(portName);
//		}
//	}
//
//	@Override
//	public boolean portEnabled(short portNumber) {
//		if (portsByNumber.get(portNumber) == null) return false;
//		return portEnabled(portsByNumber.get(portNumber));
//	}
//
//	@Override
//	public boolean portEnabled(String portName) {
//		if (portsByName.get(portName) == null) return false;
//		return portEnabled(portsByName.get(portName));
//	}
//
//	@Override
//	public boolean portEnabled(OFPhysicalPort port) {
//		if (port == null)
//			return false;
//		if ((port.getConfig() & OFPortConfig.OFPPC_PORT_DOWN.getValue()) > 0)
//			return false;
//		if ((port.getState() & OFPortState.OFPPS_LINK_DOWN.getValue()) > 0)
//			return false;
//		// Port STP state doesn't work with multiple VLANs, so ignore it for now
//		//if ((port.getState() & OFPortState.OFPPS_STP_MASK.getValue()) == OFPortState.OFPPS_STP_BLOCK.getValue())
//		//    return false;
//		return true;
//	}

	@Override
	public long getId() {
		if (this.stringId == null)
			throw new RuntimeException("Features reply has not yet been set");
		return this.datapathId;
	}
	
	@Override
	public void setId(long id) {
		this.datapathId = id;
		this.stringId = HexString.toHexString(this.datapathId);
	}

	@Override
	public String getStringId() {
		return this.stringId;
	}
	
//	@Override
//	public OFDescriptionStatistics getDescription() {
//		return description;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return "OFSwitchImpl [" + conn.getClient().getRemoteAddress() + " DPID[" + ((stringId != null) ? stringId : "?") + "]]";
		} catch (IOException e) {
			return "OFSwitchImpl [" + "null" + " DPID[" + ((stringId != null) ? stringId : "?") + "]]";
		}
	}

	@Override
	public ConcurrentMap<Object, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Date getConnectedSince() {
		return connectedSince;
	}

	@Override
	public int getNextTransactionId() {
		return this.transactionIdSource.incrementAndGet();
	}

	@Override
	public synchronized boolean isConnected() {
		if ( this.conn != null ) {
			return this.conn.isConnected();
		}
		return false;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public boolean isActive() {
		return (role != Role.SLAVE);
	}

//	@Override
//	public void setSwitchProperties(OFDescriptionStatistics description) {
//		this.description = description;
//	}

//	@Override
//	public boolean clearAllFlowMods() {
//		// Delete all pre-existing flows
//		OFMatch match = new OFMatch().setWildcards(OFMatch.OFPFW_ALL);
//		OFMessage fm = ((OFFlowMod) this.conn.getFactory()
//				.getMessage(OFType.FLOW_MOD))
//				.setMatch(match)
//				.setCommand(OFFlowMod.OFPFC_DELETE)
//				.setOutPort(OFPort.OFPP_NONE)
//				.setLength(U16.t(OFFlowMod.MINIMUM_LENGTH));
//		try {
//			List<OFMessage> msglist = new ArrayList<OFMessage>(1);
//			msglist.add(fm);
//			return this.conn.write(msglist);
//		} catch (Exception e) {
//			Logger.stderr("Failed to clear all flows on switch " + this);
//			e.printStackTrace();
//			return false;
//		}
//	}

	@Override
	public Map<Short, Long> getPortBroadcastHits() {
		return this.portBroadcastCacheHitMap;
	}

	public SocketAddress getInetAddress() {
		try {
			return this.conn.getClient().getRemoteAddress();
		} catch (IOException e) {
			// cannot get the remote address.
			return null;
		}
	}

//	@Override
//	public int getBuffers() {
//		return buffers;
//	}
//	
//	@Override
//	public void setBuffers(int buffers) {
//		this.buffers = buffers;
//	}
//
//	@Override
//	public int getActions() {
//		return actions;
//	}
//	
//	@Override
//	public void setActions(int actions) {
//		this.actions = actions;
//	}
//
//	@Override
//	public int getCapabilities() {
//		return capabilities;
//	}
//	
//	@Override
//	public void setCapabilities(int capabilities) {
//		this.capabilities = capabilities;
//	}
//
//	@Override
//	public byte getTables() {
//		return tables;
//	}
//	
//	@Override
//	public void setTables(byte tables) {
//		this.tables = tables;
//	}

	@Override
	public boolean updateBroadcastCache(Long entry, Short port) {
		if (timedCache.update(entry)) {
            Long count = portBroadcastCacheHitMap.putIfAbsent(port, new Long(1));
            if (count != null) {
                count++;
            }
            return true;
        } else {
            return false;
        }
	}

//	/**
//	 * Modules that use IOFSwitch objects use this method to request statistics 
//	 * to the switch.
//	 * @param req OFStatisticsRequest object.
//	 */
//	@Override
//	public List<OFStatistics> getSwitchStatistics(OFStatisticsRequest req) {
//		req.setXid( this.getNextTransactionId() );
//		
//		List<OFStatistics> response = new LinkedList<OFStatistics>();
//		Integer xid = req.getXid();
//		this.responsesCache.put( xid, response );
//		getConnection().write(req);
//		synchronized ( response ) {
//			try {
//				response.wait(1000);		// wait for 3 second
//			} catch (InterruptedException e) {
//				// does nothing.
//			} finally {
//				this.responsesCache.remove( xid );
//			}
//		}
//		return response;
//	}
//	
//	@Override
//	public void deliverSwitchStatistics(int xid, List<OFStatistics> m) {
////		System.out.println("delivering...");
//		Object response = this.responsesCache.get( xid );
//		if ( response != null && response instanceof List<?> ) {
//			@SuppressWarnings("unchecked")
//			List<OFStatistics> rl = (List<OFStatistics>) response;
//			synchronized ( response ) {
//				rl.addAll( m );
//				response.notifyAll();
//			}
//		}
//	}
//	
//	@Override
//	public OFFeaturesReply getFeaturesReply() {
//		OFFeaturesRequest req = new OFFeaturesRequest();
//		req.setXid( this.getNextTransactionId() );
//		List<OFFeaturesReply> response = new LinkedList<OFFeaturesReply>();
//		this.responsesCache.put( req.getXid(), response );
//		getConnection().write( req );
//		synchronized( response ) {
//			try { 
//				response.wait(1000);				// wait for 3 seconds
//			} catch ( InterruptedException e ) {
//				// does nothing
//			} finally {
//				this.responsesCache.remove( req.getXid() );
//			}
//		}
//		if ( response.isEmpty() ) {
//			return null;
//		}
//		else {
//			return response.remove(0);
//		}
//	}
//	
//	@Override
//	public void deliverFeaturesReply(int xid, OFFeaturesReply reply) {
////		System.out.println("delivering...");
//		Object response = this.responsesCache.get( xid );
//		if ( response != null && response instanceof List<?> ) {
//			@SuppressWarnings("unchecked")
//			List<OFFeaturesReply> rl = (List<OFFeaturesReply>) response;
//			synchronized ( response ) {
//				rl.add( reply );
//				response.notifyAll();
//			}
//		}
//	}
}
