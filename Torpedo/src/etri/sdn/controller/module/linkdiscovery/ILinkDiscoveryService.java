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

package etri.sdn.controller.module.linkdiscovery;

import java.util.Map;
import java.util.Set;

import etri.sdn.controller.IService;

/**
 * This interface is used to define a service that 
 * {@OFMLinkDiscovery} should implement. 
 * 
 * This file is no different with Floodlight version.
 * 
 * @author bjlee
 *
 */
public interface ILinkDiscoveryService extends IService {
    /**
     * Retrieves a map of all known link connections between OpenFlow switches
     * and the associated info (valid time, port states) for the link.
     */
    public Map<Link, LinkInfo> getLinks();

    /**
     * Returns link type of a given link.
     * Modified by bjlee
     * @param info
     * @return
     */
    public ILinkDiscovery.LinkType getLinkType(Link lt, LinkInfo info);

    /**
     * Returns an unmodifiable map from switch id to a set of all links with it 
     * as an endpoint.
     */
    public Map<Long, Set<Link>> getSwitchLinks();

    /**
     * Adds a listener to listen for ILinkDiscoveryService messages
     * @param listener The listener that wants the notifications
     */
    public void addListener(ILinkDiscoveryListener listener);

    /**
     * Retrieves a set of all switch ports on which lldps are suppressed.
     */
    public Set<NodePortTuple> getSuppressLLDPsInfo();

    /**
     * Adds a switch port to suppress lldp set
     */
    public void AddToSuppressLLDPs(long sw, int port);

    /**
     * Removes a switch port from suppress lldp set
     */
    public void RemoveFromSuppressLLDPs(long sw, int port);

    /**
     * Get the set of quarantined ports on a switch
     */
    public Set<Integer> getQuarantinedPorts(long sw);

    /**
     * Get the status of auto port fast feature.
     */
    public boolean isAutoPortFastFeature();

    /**
     * Set the state for auto port fast feature.
     * @param autoPortFastFeature
     */
    public void setAutoPortFastFeature(boolean autoPortFastFeature);
}
