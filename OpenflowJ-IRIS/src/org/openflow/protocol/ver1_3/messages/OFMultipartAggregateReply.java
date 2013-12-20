package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartAggregateReply extends OFMultipartReply implements org.openflow.protocol.interfaces.OFMultipartAggregateReply {
    public static int MINIMUM_LENGTH = 40;

    long  packet_count;
	long  byte_count;
	int  flow_count;
	int pad_1th;

    public OFMultipartAggregateReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)2, getType()));
    }
    
    public OFMultipartAggregateReply(OFMultipartAggregateReply other) {
    	super(other);
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
		this.flow_count = other.flow_count;
    }

	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFMultipartAggregateReply setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFMultipartAggregateReply setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
			
	public int getFlowCount() {
		return this.flow_count;
	}
	
	public OFMultipartAggregateReply setFlowCount(int flow_count) {
		this.flow_count = flow_count;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.packet_count = data.getLong();
		this.byte_count = data.getLong();
		this.flow_count = data.getInt();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putLong(this.packet_count);
		data.putLong(this.byte_count);
		data.putInt(this.flow_count);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartAggregateReply-"+":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count) + 
		":flow_count=" + U32.f(flow_count);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1831;
		int result = super.hashCode() * prime;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
		result = prime * result + (int) flow_count;
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
        if (!(obj instanceof OFMultipartAggregateReply)) {
            return false;
        }
        OFMultipartAggregateReply other = (OFMultipartAggregateReply) obj;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
		if ( flow_count != other.flow_count ) return false;
        return true;
    }
}
