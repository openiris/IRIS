package etri.sdn.controller.module.linkdiscovery;

import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.util.HexString;

/**
 * A NodePortTuple is similar to a SwitchPortTuple
 * but it only stores IDs instead of references
 * to the actual objects.
 * 
 * @author srini
 */
public class NodePortTuple {
	protected long nodeId; // switch DPID
	protected OFPort portId; // switch port id

	/**
	 * Creates a NodePortTuple
	 * @param nodeId The DPID of the switch
	 * @param portId The port of the switch
	 */
	public NodePortTuple(long nodeId, OFPort portId) {
		this.nodeId = nodeId;
		this.portId = portId;
	}

	public NodePortTuple(long nodeId, short portId) {
		this.nodeId = nodeId;
		this.portId = OFPort.of(portId);
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public OFPort getPortId() {
		return portId;
	}

	public void setPortId(OFPort portId) {
		this.portId = portId;
	}
	
	public void setPortId(short portId) {
		this.portId = OFPort.of(portId);
	}

	public String toString() {
		return "[id=" + HexString.toHexString(nodeId) + ", port=" + portId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (nodeId ^ (nodeId >>> 32));
		result = prime * result + portId.getPortNumber();
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
		NodePortTuple other = (NodePortTuple) obj;
		if (nodeId != other.nodeId)
			return false;
		if (!portId.equals(other.portId))
			return false;
		return true;
	}

	/**
	 * API to return a String value formed with NodeID and PortID
	 * The portID is a 16-bit field, so mask it as an integer to get full
	 * positive value
	 * @return	String
	 */
	public String toKeyString() {
		return (HexString.toHexString(nodeId)+ "|" + (portId.getPortNumber() & 0xffff));
	}
}
