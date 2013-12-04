package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFGetAsyncRequest extends OFMessage  {
    public static int MINIMUM_LENGTH = 32;

    int  packet_in_mask_equal_master;
	int  packet_in_mask_slave;
	int  port_status_mask_equal_master;
	int  port_status_mask_slave;
	int  flow_removed_mask_equal_master;
	int  flow_removed_mask_slave;

    public OFGetAsyncRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)26));
    }
    
    public OFGetAsyncRequest(OFGetAsyncRequest other) {
    	super(other);
		this.packet_in_mask_equal_master = other.packet_in_mask_equal_master;
		this.packet_in_mask_slave = other.packet_in_mask_slave;
		this.port_status_mask_equal_master = other.port_status_mask_equal_master;
		this.port_status_mask_slave = other.port_status_mask_slave;
		this.flow_removed_mask_equal_master = other.flow_removed_mask_equal_master;
		this.flow_removed_mask_slave = other.flow_removed_mask_slave;
    }

	public int getPacketInMaskEqualMaster() {
		return this.packet_in_mask_equal_master;
	}
	
	public OFGetAsyncRequest setPacketInMaskEqualMaster(int packet_in_mask_equal_master) {
		this.packet_in_mask_equal_master = packet_in_mask_equal_master;
		return this;
	}
			
	public int getPacketInMaskSlave() {
		return this.packet_in_mask_slave;
	}
	
	public OFGetAsyncRequest setPacketInMaskSlave(int packet_in_mask_slave) {
		this.packet_in_mask_slave = packet_in_mask_slave;
		return this;
	}
			
	public int getPortStatusMaskEqualMaster() {
		return this.port_status_mask_equal_master;
	}
	
	public OFGetAsyncRequest setPortStatusMaskEqualMaster(int port_status_mask_equal_master) {
		this.port_status_mask_equal_master = port_status_mask_equal_master;
		return this;
	}
			
	public int getPortStatusMaskSlave() {
		return this.port_status_mask_slave;
	}
	
	public OFGetAsyncRequest setPortStatusMaskSlave(int port_status_mask_slave) {
		this.port_status_mask_slave = port_status_mask_slave;
		return this;
	}
			
	public int getFlowRemovedMaskEqualMaster() {
		return this.flow_removed_mask_equal_master;
	}
	
	public OFGetAsyncRequest setFlowRemovedMaskEqualMaster(int flow_removed_mask_equal_master) {
		this.flow_removed_mask_equal_master = flow_removed_mask_equal_master;
		return this;
	}
			
	public int getFlowRemovedMaskSlave() {
		return this.flow_removed_mask_slave;
	}
	
	public OFGetAsyncRequest setFlowRemovedMaskSlave(int flow_removed_mask_slave) {
		this.flow_removed_mask_slave = flow_removed_mask_slave;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.packet_in_mask_equal_master = data.getInt();
		this.packet_in_mask_slave = data.getInt();
		this.port_status_mask_equal_master = data.getInt();
		this.port_status_mask_slave = data.getInt();
		this.flow_removed_mask_equal_master = data.getInt();
		this.flow_removed_mask_slave = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.packet_in_mask_equal_master);
		data.putInt(this.packet_in_mask_slave);
		data.putInt(this.port_status_mask_equal_master);
		data.putInt(this.port_status_mask_slave);
		data.putInt(this.flow_removed_mask_equal_master);
		data.putInt(this.flow_removed_mask_slave);
    }

    public String toString() {
        return super.toString() +  ":OFGetAsyncRequest-"+":packet_in_mask_equal_master=" + U32.f(packet_in_mask_equal_master) + 
		":packet_in_mask_slave=" + U32.f(packet_in_mask_slave) + 
		":port_status_mask_equal_master=" + U32.f(port_status_mask_equal_master) + 
		":port_status_mask_slave=" + U32.f(port_status_mask_slave) + 
		":flow_removed_mask_equal_master=" + U32.f(flow_removed_mask_equal_master) + 
		":flow_removed_mask_slave=" + U32.f(flow_removed_mask_slave);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1433;
		int result = super.hashCode() * prime;
		result = prime * result + (int) packet_in_mask_equal_master;
		result = prime * result + (int) packet_in_mask_slave;
		result = prime * result + (int) port_status_mask_equal_master;
		result = prime * result + (int) port_status_mask_slave;
		result = prime * result + (int) flow_removed_mask_equal_master;
		result = prime * result + (int) flow_removed_mask_slave;
		return result;
    }

    @Override
    public boolean equals(Object obj) {
        
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFGetAsyncRequest)) {
            return false;
        }
        OFGetAsyncRequest other = (OFGetAsyncRequest) obj;
		if ( packet_in_mask_equal_master != other.packet_in_mask_equal_master ) return false;
		if ( packet_in_mask_slave != other.packet_in_mask_slave ) return false;
		if ( port_status_mask_equal_master != other.port_status_mask_equal_master ) return false;
		if ( port_status_mask_slave != other.port_status_mask_slave ) return false;
		if ( flow_removed_mask_equal_master != other.flow_removed_mask_equal_master ) return false;
		if ( flow_removed_mask_slave != other.flow_removed_mask_slave ) return false;
        return true;
    }
}
