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

package etri.sdn.controller;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a context object where listeners can register 
 * and later retrieve context information associated with an event.
 * 
 * The MessageContext object is created one per each OFMessage 
 * received from a switch. 
 * 
 * Originally, this was FloodlightContext in Floodlight.
 * 
 * @author readams
 * @author bjlee
 */
public final class MessageContext {
	
	/**
	 * Internal representation of the message context storage.
	 */
	private ConcurrentHashMap<String, Object> storage =
		new ConcurrentHashMap<String, Object>();

	/**
	 * a predefined key for the Ethernet payload.
	 */
	public static final String ETHER_PAYLOAD = 
		"net.floodlightcontroller.core.IFloodlightProvider.piPayload";

	/**
	 * a predefined key for the Routing Decisions.
	 */
	public static final String ROUTING_DECISION = 
		"net.floodlightcontroller.routing.decision";
	
    /**
     * a predefined key to find the source device 
     * for the current packet-in, if applicable.
     */
    public static final String SRC_DEVICE = 
            "net.floodlightcontroller.devicemanager.srcDevice"; 

    /**
     * a predefined key to find the destination device 
     * for the current packet-in, if applicable.
     */
    public static final String DST_DEVICE = 
            "net.floodlightcontroller.devicemanager.dstDevice";

    /**
     * Return the actual storage associated with the message context.
     * With put() and get() method, this method should be rarely used.
     * 
     * @return the actual storage object associated with the message context
     */
	public ConcurrentHashMap<String, Object> getStorage() {
		return storage;
	}

	/**
	 * retrieve an object saved with the given key
	 * @param key 		value that uniquely identifies an item saved in the message context
	 * @return			Object saved in the storage
	 */
	public Object get(String key) {
		return storage.get(key);
	}
	
	/**
	 * Put something in the message context with given key.
	 * 
	 * @param key 	value that uniquely identifies an item saved in the message context
	 * @param value value that is saved in the message context
	 */
	public void put(String key, Object value) {
		storage.put( key, value );
	}
}
