/**
 *    Copyright 2011, Big Switch Networks, Inc. 
 *    Originally created by David Erickson, Stanford University
 *    Modified by Byungjoon Lee, ETRI
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

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import etri.sdn.controller.module.linkdiscovery.NodePortTuple;


/**
 * Represents a route between two switches
 *
 * @author David Erickson (daviderickson@cs.stanford.edu)
 */
public class Route implements Comparable<Route> {
	protected RouteId id;
	protected List<NodePortTuple> switchPorts;
	
	private static enum RouteFunnel implements Funnel<NodePortTuple> {
		INSTANCE;
		@Override
		public void funnel(NodePortTuple tuple, PrimitiveSink sink) {
			sink.putLong(tuple.getNodeId())
				.putInt(tuple.getPortId().getPortNumber());
		}
	}

	public Route(RouteId id, List<NodePortTuple> switchPorts) {
		super();
		this.id = id;
		this.switchPorts = switchPorts;
	}

	public Route(Long src, Long dst) {
		super();
		this.id = new RouteId(src, dst);
		this.switchPorts = new ArrayList<NodePortTuple>();
	}

	/**
	 * @return the id
	 */
	public RouteId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(RouteId id) {
		this.id = id;
	}

	/**
	 * @return the path
	 */
	public List<NodePortTuple> getPath() {
		return switchPorts;
	}

	/**
	 * Set path using the switch port list
	 * @param switchPorts	path to set
	 */
	public void setPath(List<NodePortTuple> switchPorts) {
		this.switchPorts = switchPorts;
	}

	@Override
	public int hashCode() {
		final int prime = 5791;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((switchPorts == null) ? 0 : switchPorts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (switchPorts == null) {
			if (other.switchPorts != null)
				return false;
		} else if (!switchPorts.equals(other.switchPorts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", switchPorts=" + switchPorts + "]";
	}

	/**
	 * Compares the path lengths between Routes.
	 */
	@Override
	public int compareTo(Route o) {
		return ((Integer)switchPorts.size()).compareTo(o.switchPorts.size());
	}

	public boolean has(long srcId, long dstId) {
		NodePortTuple prev = null;
		boolean ret = false;
		for ( NodePortTuple npt: this.switchPorts ) {
			if ( prev != null && 
					((prev.getNodeId() == srcId && npt.getNodeId() == dstId) ||
					 (prev.getNodeId() == dstId && npt.getNodeId() == srcId)) ) {
				return true;
			}
			prev = npt;
		}
		return ret;
	}
	/**
	 * This method returns a bloom filter (set) that contains all node-port tuple 
	 * inside the route. This bloom filter is used to test a link is within the 
	 * route very fast.
	 * 
	 * @return BloomFilter<NodePortTuple>
	 */
	public BloomFilter<NodePortTuple> getBloomFilter() {
		BloomFilter<NodePortTuple> ret = BloomFilter.create(RouteFunnel.INSTANCE, 20);
		for ( NodePortTuple npt: this.switchPorts ) {
			ret.put(npt);
		}
		return ret;
	}
}
