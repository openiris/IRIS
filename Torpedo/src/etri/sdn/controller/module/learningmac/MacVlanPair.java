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

package etri.sdn.controller.module.learningmac;

import etri.sdn.controller.protocol.io.IOFSwitch;

/**
 * This is a modified version of the original MacVlanPair of Floodlight.
 * This class manages association between MAC address, VLAN value,
 * and the switch that reported the MAC and VLAN to the controller.
 * 
 * @author bjlee
 *
 */
public class MacVlanPair {
    private final Long mac;
    private final Short vlan;
    private final IOFSwitch sw;
    
    /**
     * Constructor to create the MacVlanPair object.
     * 
     * @param mac	MAC address
     * @param vlan	VLAN value
     * @param sw	switch that reported the MAC and VLAN value
     */
    public MacVlanPair(Long mac, Short vlan, IOFSwitch sw) {
        this.mac = mac;
        this.vlan = vlan;
        this.sw = sw;
    }
    
    /**
     * Get the MAC address
     * 
     * @return	MAC address (long type)
     */
    public long getMac() {
        return mac.longValue();
    }
    
    /**
     * Get the VLAN value
     * 
     * @return	VLAN value (short type)
     */
    public short getVlan() {
        return vlan.shortValue();
    }
    
    /**
     * Get the switch associated with the MAC address
     * 
     * @return	reference to the switch (IOFSwitch object)
     */
    public IOFSwitch getSwitch() {
    	return this.sw;
    }
    
    /**
     * check if this object has the same MAC, VLAN, and switch.
     */
    public boolean equals(Object o) {
    	if ( o == null ) {
    		return false;
    	}
    	
    	if ( getClass() != o.getClass() ) {
    		return false;
    	}
    	
    	MacVlanPair other = (MacVlanPair) o;
    	return this.sw == other.sw && mac.equals(other.mac) && vlan.equals(other.vlan); 
    }
    
    /**
     * Calculate the hashCode for this object.
     */
    public int hashCode() {
        return sw.hashCode() ^ mac.hashCode() ^ vlan.hashCode();
    }
}