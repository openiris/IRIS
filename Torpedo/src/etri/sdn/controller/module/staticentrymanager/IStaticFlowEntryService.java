/**
 *    Copyright 2013, Big Switch Networks, Inc.
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

package etri.sdn.controller.module.staticentrymanager;

import java.util.Map;

import etri.sdn.controller.IService;

/**
 * Interface to define the services of StaticFlowEntryManager.
 * 
 * @author jshin
 */
public interface IStaticFlowEntryService extends IService {
	/**
	 * Adds a static flow.
	 * 
	 * @param name Name of the flow mod. Must be unique.
	 * @param fm The flow to push.
	 * @param dpid The switch DPID to push it to, in 00:00:00:00:00:00:00:01 notation.
	 */
	//public boolean addFlow(String name, OFFlowMod fm, String dpid);

	/**
	 * Adds a static flow as Map<String, Object>.
	 * 
	 * @param name Name of the static flow entry. Must be unique.
	 * @param entry The static flow entry to push.
	 * @param dpid The switch DPID to push it to, in 00:00:00:00:00:00:00:01 notation.
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void addFlow(String name, Map<String, Object> entry, String dpid) throws StaticFlowEntryException;

	/**
	 * Deletes a static flow by entry name
	 * 
	 * @param name The name of the static flow entry to delete.
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void deleteFlow(String name) throws StaticFlowEntryException;

	/**
	 * Deletes all static flows for a particular switch
	 * 
	 * @param dpid The DPID of the switch to delete flows for.
	 */
	//public boolean deleteFlowsForSwitch(long dpid);

	/**
	 * Deletes all static flows for a particular switch.
	 * 
	 * @param dpid The DPID of the switch to delete flows for.
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void deleteFlowsForSwitch(String dpid) throws StaticFlowEntryException;

	/**
	 * Deletes all flows.
	 * 
	 * @return true when successfully deleted, false otherwise
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void deleteAllFlows() throws StaticFlowEntryException;

	/**
	 * Gets all list of all flows
	 * The first key is the DPID, and the second key is the name of flow-mod record
	 * (for example, 'flow-mod-1').
	 */
	//public Map<String, Map<String, OFFlowMod>> getFlows();

	/**
	 * Gets all static flow entries.
	 * The first key is the name of the static flow entry, 
	 * and the second key is the field name of it.
	 * 
	 * @return Static flow entry map in which the first key is entry name
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public Map<String, Map<String, Object>> getAllFlows() throws StaticFlowEntryException;

	/**
	 * Gets all static flow entries for a particular switch.
	 * The first key is the DPID, 
	 * and the second key is the field name of the static flow entry.
	 * 
	 * @param dpid The DPID of the switch
	 * 
	 * @return Static flow entry map in which the first key is dpid
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public Map<String, Map<String, Object>> getFlowsForSwitch(String dpid) throws StaticFlowEntryException;

	/**
	 * Gets a list of flows by switch
	 * The first key is the name of flow-mod record (for example, 'flow-mod-1'). 
	 */
	//public Map<String, OFFlowMod> getFlows(String dpid);

	/**
	 * Reloads all static flow entries saved in the memory or DB to switches.
	 * This is used when the controller is rebooted.
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void reloadFlowsToSwitch() throws StaticFlowEntryException;
}
