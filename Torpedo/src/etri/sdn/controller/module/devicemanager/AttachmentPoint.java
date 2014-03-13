/**
 *    Copyright 2011,2012 Big Switch Networks, Inc.
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

/**
 * @author Srini
 */

package etri.sdn.controller.module.devicemanager;

import org.projectfloodlight.openflow.types.OFPort;

/**
 * The AttachmentPoint class consists of various information for a device
 * such as the switch, the port number, and the time activated and last seen.
 *
 */
public class AttachmentPoint {
    long  sw;
    OFPort port;
    long  activeSince;
    long  lastSeen;

    // Timeout for moving attachment points from OF/broadcast
    // domain to another.
    public static final long INACTIVITY_INTERVAL = 30000; // 30 seconds
    public static final long EXTERNAL_TO_EXTERNAL_TIMEOUT = 5000;  // 5 seconds
    public static final long OPENFLOW_TO_EXTERNAL_TIMEOUT = 30000; // 30 seconds
    public static final long CONSISTENT_TIMEOUT = 30000;           // 30 seconds

    /**
     * Constructor
     * 
     * @param sw the switch dpid
     * @param port the switch port
     * @param activeSince the timestamp when this instance is activated
     * @param lastSeen the recent timestamp when this instance is used 
     */
    public AttachmentPoint(long sw, OFPort port, long activeSince,
                           long lastSeen) {
        this.sw = sw;
        this.port = port;
        this.activeSince = activeSince;
        this.lastSeen = lastSeen;
    }

    /**
     * Constructor 
     * 
     * @param sw the switch dpid
     * @param port the switch port
     * @param lastSeen the recent timestamp when this instance is used 
     */
    public AttachmentPoint(long sw, OFPort port, long lastSeen) {
        this.sw = sw;
        this.port = port;
        this.lastSeen = lastSeen;
        this.activeSince = lastSeen;
    }

    /**
     * Constructor with an AttachmentPoint instance 
     * 
     * @param ap an AttachmentPoint instance 
     */
    public AttachmentPoint(AttachmentPoint ap) {
        this.sw = ap.sw;
        this.port = ap.port;
        this.activeSince = ap.activeSince;
        this.lastSeen = ap.lastSeen;
    }

    /**
     * Returns the switch dpid.
     * 
     * @return the switch dpid
     */
    public long getSw() {
        return sw;
    }
    
    /**
     * Sets the switch dpid.
     * 
     * @param sw the new switch dpid
     */
    public void setSw(long sw) {
        this.sw = sw;
    }
    
    /**
     * Returns the switch port.
     * 
     * @return the switch port
     */
    public OFPort getPort() {
        return port;
    }
    
    /**
     * Sets the switch port.
     * 
     * @param port the new switch port
     */
    public void setPort(OFPort port) {
        this.port = port;
    }
    
    /**
     * Returns the time when this instance is activated.
     *  
     * @return the activated time
     */
    public long getActiveSince() {
        return activeSince;
    }
    
    /**
     * Sets the time when this instance is activated.
     *  
     * @param activeSince the activated time
     */
    public void setActiveSince(long activeSince) {
        this.activeSince = activeSince;
    }
    
    /**
     * Returns the recent time when this instance is used.
     *  
     * @return the recent used time
     */
    public long getLastSeen() {
        return lastSeen;
    }
    
    /**
     * Updates the recent time when this instance is used.
     *  
     * @param lastSeen the recent used time
     */
    public void setLastSeen(long lastSeen) {
        if (this.lastSeen + INACTIVITY_INTERVAL < lastSeen)
            this.activeSince = lastSeen;
        if (this.lastSeen < lastSeen)
            this.lastSeen = lastSeen;
    }

    /**
     *  Hash is generated using only switch and port.
     *  
     *  @return a hash value
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((port!=null)?port.getPortNumber():0);
        result = prime * result + (int) (sw ^ (sw >>> 32));
        return result;
    }

    /**
     * Compares with an input object about the switch and port.
     * 
     * @param obj an object to compare
     * 
     * @return true if the input object is identical to this instance, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttachmentPoint other = (AttachmentPoint) obj;
        if (! port.equals(other.port) )
            return false;
        if (sw != other.sw)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttachmentPoint [sw=" + sw + ", port=" + port
               + ", activeSince=" + activeSince + ", lastSeen=" + lastSeen
               + "]";
    }
}
