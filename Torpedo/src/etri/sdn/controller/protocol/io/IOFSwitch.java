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

//import java.util.Collection;
import java.util.Date;
//import java.util.List;
import java.util.Map;

//import org.openflow.protocol.OFFeaturesReply;
//import org.openflow.protocol.OFPhysicalPort;
//import org.openflow.protocol.OFStatisticsRequest;
//import org.openflow.protocol.statistics.OFDescriptionStatistics;
//import org.openflow.protocol.statistics.OFStatistics;
//import org.openflow.util.HexString;

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
     * Get the datapathId of the switch
     * @return	datapath id (long)
     */
    public long getId();
    
    /**
     * Set the datapath id of the switch
     * @param id	datapath id (long)
     */
    public void setId(long id);
    
    /**
     * Get a string version of the ID for this switch
     * @return	String (string version of the datapath id)
     */
    public String getStringId();
    
    /**
     * Retrieves attributes of this switch
     * @return	Map<Object, Object>
     */
    public Map<Object, Object> getAttributes();

    /**
     * Retrieves the date the switch connected to this controller
     * @return the date
     */
    public Date getConnectedSince();

    /**
     * Returns the next available transaction id
     * @return	next transaction id (int)
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
     * Update broadcast cache
     * @param entry		Long
     * @param port		Short
     * @return true if there is a cache hit
     *         false if there is no cache hit.
     */
    public boolean updateBroadcastCache(Long entry, Short port);
    
    /**
     * Get the portBroadcastCacheHits
     * @return	Map<Short, Long> object
     */
    public Map<Short, Long> getPortBroadcastHits();
   
    /**
     * Get the version of the switch 
     * @return	version number (byte)
     */
	public byte getVersion();
	
	/**
	 * Set the version of the switch
	 * @param version	version number (byte)
	 */
	public void setVersion(byte version);
   
}
