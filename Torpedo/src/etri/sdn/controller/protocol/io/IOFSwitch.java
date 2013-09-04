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

package etri.sdn.controller.protocol.io;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openflow.protocol.OFFeaturesReply;
import org.openflow.protocol.OFPhysicalPort;
import org.openflow.protocol.OFStatisticsRequest;
import org.openflow.protocol.statistics.OFDescriptionStatistics;
import org.openflow.protocol.statistics.OFStatistics;

/**
 * This was originally written for Floodlight, but we slightly modified it to 
 * fit our implementation needs. 
 *
 * @author David Erickson (daviderickson@cs.stanford.edu)
 * @author Byungjoon Lee (bjlee@etri.re.kr) 
 */
public interface IOFSwitch {
	
	public enum SwitchType {
        BASIC_SWITCH, CORE_SWITCH
    };
	
    // Attribute keys
    public static final String SWITCH_DESCRIPTION_FUTURE = "DescriptionFuture";
    public static final String SWITCH_DESCRIPTION_DATA = "DescriptionData";
    public static final String SWITCH_SUPPORTS_NX_ROLE = "supportsNxRole";
    public static final String SWITCH_IS_CORE_SWITCH = "isCoreSwitch";
    public static final String PROP_FASTWILDCARDS = "FastWildcards";
    public static final String PROP_REQUIRES_L3_MATCH = "requiresL3Match";
    public static final String PROP_SUPPORTS_OFPP_TABLE = "supportsOfppTable";
    public static final String PROP_SUPPORTS_OFPP_FLOOD = "supportsOfppFlood";
    public static final String PROP_SUPPORTS_NETMASK_TBL = "supportsNetmaskTbl";
    
    public void setConnection(Connection conn);
	public Connection getConnection();
	
    /**
     * Returns switch features from features Reply
     * @return
     */
    public int getBuffers();
    
    /**
     * Returns switch features from features Reply
     * @return
     */
    public int getActions();
    
    /**
     * Returns switch features from features Reply
     * @return
     */
    public int getCapabilities();
    
    /**
     * Returns switch features from features Reply
     * @return
     */
    public byte getTables();

    /**
     * Set the OFFeaturesReply message returned by the switch during initial
     * handshake.
     * @param featuresReply
     */
    public void setFeaturesReply(OFFeaturesReply featuresReply);
    
    /**
     * Set the SwitchProperties based on it's description
     * @param description
     */
    public void setSwitchProperties(OFDescriptionStatistics description);    

    /**
     * Get list of all enabled ports. This will typically be different from
     * the list of ports in the OFFeaturesReply, since that one is a static
     * snapshot of the ports at the time the switch connected to the controller
     * whereas this port list also reflects the port status messages that have
     * been received.
     * @return Unmodifiable list of ports not backed by the underlying collection
     */
    public Collection<OFPhysicalPort> getEnabledPorts();
    
    /**
     * Get list of the port numbers of all enabled ports. This will typically
     * be different from the list of ports in the OFFeaturesReply, since that
     * one is a static snapshot of the ports at the time the switch connected 
     * to the controller whereas this port list also reflects the port status
     * messages that have been received.
     * @return Unmodifiable list of ports not backed by the underlying collection
     */
    public Collection<Short> getEnabledPortNumbers();

    /**
     * Retrieve the port object by the port number. The port object
     * is the one that reflects the port status updates that have been
     * received, not the one from the features reply.
     * @param portNumber
     * @return port object
     */
    public OFPhysicalPort getPort(short portNumber);
    
    /**
     * Retrieve the port object by the port name. The port object
     * is the one that reflects the port status updates that have been
     * received, not the one from the features reply.
     * @param portName
     * @return port object
     */
    public OFPhysicalPort getPort(String portName);
    
    /**
     * Add or modify a switch port. This is called by the core controller
     * code in response to a OFPortStatus message. It should not typically be
     * called by other floodlight applications.
     * @param port
     */
    public void setPort(OFPhysicalPort port);

    /**
     * Delete a port for the switch. This is called by the core controller
     * code in response to a OFPortStatus message. It should not typically be
     * called by other floodlight applications.
     * @param portNumber
     */
    public void deletePort(short portNumber);
    
    /**
     * Delete a port for the switch. This is called by the core controller
     * code in response to a OFPortStatus message. It should not typically be
     * called by other floodlight applications.
     * @param portName
     */
    public void deletePort(String portName);
    
    /**
     * Get list of all ports. This will typically be different from
     * the list of ports in the OFFeaturesReply, since that one is a static
     * snapshot of the ports at the time the switch connected to the controller
     * whereas this port list also reflects the port status messages that have
     * been received.
     * @return Unmodifiable list of ports 
     */
    public Collection<OFPhysicalPort> getPorts();

    /**
     * @param portName
     * @return Whether a port is enabled per latest port status message
     * (not configured down nor link down nor in spanning tree blocking state)
     */
    public boolean portEnabled(short portName);
    
    /**
     * @param portNumber
     * @return Whether a port is enabled per latest port status message
     * (not configured down nor link down nor in spanning tree blocking state)
     */
    public boolean portEnabled(String portName);

    /**
     * @param port
     * @return Whether a port is enabled per latest port status message
     * (not configured down nor link down nor in spanning tree blocking state)
     */
    public boolean portEnabled(OFPhysicalPort port);

    /**
     * Get the datapathId of the switch
     * @return
     */
    public long getId();
    
    /**
     * Get the OFDescriptionStatistics object.
     * @return
     */
    public OFDescriptionStatistics getDescription();


    /**
     * Get a string version of the ID for this switch
     * @return
     */
    public String getStringId();
    
    /**
     * Retrieves attributes of this switch
     * @return
     */
    public Map<Object, Object> getAttributes();

    /**
     * Retrieves the date the switch connected to this controller
     * @return the date
     */
    public Date getConnectedSince();

    /**
     * Returns the next available transaction id
     * @return
     */
    public int getNextTransactionId();

     /**
     * Check if the switch is still connected;
     * Only call while holding processMessageLock
     * @return whether the switch is still disconnected
     */
    public boolean isConnected();
    
    /**
     * Get the current role of the controller for the switch
     * @return the role of the controller
     */
    public IOFHandler.Role getRole();
    
    /**
     * Check if the controller is an active controller for the switch.
     * The controller is active if its role is MASTER or EQUAL.
     * @return whether the controller is active
     */
    public boolean isActive();
    
    /**
     * Checks if a specific switch property exists for this switch
     * @param name name of property
     * @return value for name
     */
    boolean hasAttribute(String name);

    /**
     * Set properties for switch specific behavior
     * @param name name of property
     * @return value for name
     */
    Object getAttribute(String name);

    /**
     * Set properties for switch specific behavior
     * @param name name of property
     * @param value value for name
     */
    void setAttribute(String name, Object value);

    /**
     * Set properties for switch specific behavior
     * @param name name of property
     * @return current value for name or null (if not present)
     */
    Object removeAttribute(String name);

    /**
     * Clear all flowmods on this switch
     * @return 
     */
    public boolean clearAllFlowMods();

    /**
     * Update broadcast cache
     * @param data
     * @return true if there is a cache hit
     *         false if there is no cache hit.
     */
    public boolean updateBroadcastCache(Long entry, Short port);
    
    /**
     * Get the portBroadcastCacheHits
     * @return
     */
    public Map<Short, Long> getPortBroadcastHits();
    
    /**
     * query statistics to the switch.
     * @param req
     * @return
     */
	public List<OFStatistics> getSwitchStatistics(OFStatisticsRequest req);
	
	/**
	 * deliver an switch statistics from switch to IOFSwitch object.
	 * @param xid
	 * @param m OFStatistics objects
	 */
	public void deliverSwitchStatistics(int xid, List<OFStatistics> m);
	
	/**
	 * query FEATURE_REPLY to the switch.
	 * @return
	 */
	public OFFeaturesReply getFeaturesReply();
	
	/**
	 * Deliver a feature reply from switch to IOFSwitch object.
	 * @param xid
	 * @param reply
	 */
	public void deliverFeaturesReply(int xid, OFFeaturesReply reply);
}
