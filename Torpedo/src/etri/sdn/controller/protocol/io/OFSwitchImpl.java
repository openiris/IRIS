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
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.util.HexString;

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

	private OFVersion version;
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
	private ConcurrentMap<OFPort, Long> portBroadcastCacheHitMap;

	private long datapathId;

	public OFSwitchImpl() {
		this.version = OFVersion.OF_10;		// IS THIS PROPER INITIALIZATION?
		this.stringId = null;
		this.attributes = new ConcurrentHashMap<Object, Object>();
		this.connectedSince = new Date();
		this.transactionIdSource = new AtomicInteger();
		this.role = Role.MASTER;		// FIXME: bjlee

		this.timedCache = new TimedCache<Long>(100, 5*1000 );  // 5 seconds interval
		this.portBroadcastCacheHitMap = new ConcurrentHashMap<OFPort, Long>();

		// Defaults properties for an ideal switch
		this.setAttribute(PROP_FASTWILDCARDS, new Integer(0x3fffff)	/* OFPFW_ALL */);
		this.setAttribute(PROP_SUPPORTS_OFPP_FLOOD, new Boolean(true));
		this.setAttribute(PROP_SUPPORTS_OFPP_TABLE, new Boolean(true));
	}
	
	@Override
	public void setVersion(OFVersion v) {
		this.version = v;
	}
	
	@Override
	public OFVersion getVersion() {
		return this.version;
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
	}

	@Override
	public Object removeAttribute(String name) {
		return this.attributes.remove(name);
	}

	@Override
	public boolean hasAttribute(String name) {
		return this.attributes.containsKey(name);
	}

	
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

	@Override
	public Map<OFPort, Long> getPortBroadcastHits() {
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

	@Override
	public boolean updateBroadcastCache(Long entry, OFPort port) {
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

}
