/**
*    Copyright 2011, Big Switch Networks, Inc. 
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

import java.util.Map;

/**
 * This is an interface for a class that exports some information 
 * as a map object. Currently, the only class that implements this interface 
 * is {@link etri.sdn.controller.module.devicemanager.OFMDeviceManager}.
 *
 * @author Shudong Zhou
 */
public interface IInfoProvider {

    /**
     * Called when rest API requests information of a particular type
     * @param type	type of the information (string)
     * @return		Map<String, Object> - information map
     */
    public Map<String, Object> getInfo(String type);
}
