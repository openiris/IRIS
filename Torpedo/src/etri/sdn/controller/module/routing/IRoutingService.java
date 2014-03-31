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

package etri.sdn.controller.module.routing;

import org.projectfloodlight.openflow.types.OFPort;

import etri.sdn.controller.IService;

public interface IRoutingService extends IService {

	//
	// APIs for current route
	//

	/** Provides a route between src and dst that allows tunnels. */
	public Route getRoute(long src, long dst);

	/** Provides a route between src and dst, with option to allow or 
	 *  not allow tunnels in the path.*/
	public Route getRoute(long src, long dst, boolean tunnelEnabled);


	public Route getRoute(long srcId, OFPort srcPort, long dstId, OFPort dstPort);

	public Route getRoute(long srcId, OFPort srcPort, 
			long dstId, OFPort dstPort, 
			boolean tunnelEnabled);

	/** Check if a route exists between src and dst, including tunnel links
	 *  in the path.
	 */
	public boolean routeExists(long src, long dst);

	/** Check if a route exists between src and dst, with option to have
	 *  or not have tunnels as part of the path.
	 */
	public boolean routeExists(long src, long dst, boolean tunnelEnabled);

	//
	// APIs for old route
	//

	/** Provides a route between src and dst that allows tunnels. */
	public Route getOldRoute(long src, long dst);

	/** Provides a route between src and dst, with option to allow or 
	 *  not allow tunnels in the path.*/
	public Route getOldRoute(long src, long dst, boolean tunnelEnabled);


	public Route getOldRoute(long srcId, OFPort srcPort, long dstId, OFPort dstPort);

	public Route getOldRoute(long srcId, OFPort srcPort, 
			long dstId, OFPort dstPort, 
			boolean tunnelEnabled);

	/** Check if a route exists between src and dst, including tunnel links
	 *  in the path.
	 */
	public boolean oldRouteExists(long src, long dst);

	/** Check if a route exists between src and dst, with option to have
	 *  or not have tunnels as part of the path.
	 */
	public boolean oldRouteExists(long src, long dst, boolean tunnelEnabled);
}