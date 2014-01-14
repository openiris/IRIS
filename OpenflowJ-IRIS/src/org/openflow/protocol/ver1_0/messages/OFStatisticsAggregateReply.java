package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsAggregateReply extends OFStatisticsReply implements org.openflow.protocol.interfaces.OFStatisticsAggregateReply {
    public static int MINIMUM_LENGTH = 36;

    long  packet_count;
	long  byte_count;
	int  flow_count;
	int pad_1th;

    public OFStatisticsAggregateReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
		setStatisticsType(OFStatisticsType.valueOf((short)2, this.type));
    }
    
    public OFStatisticsAggregateReply(OFStatisticsAggregateReply other) {
    	super(other);
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
		this.flow_count = other.flow_count;
    }

	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFStatisticsAggregateReply setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
	
	public boolean isPacketCountSupported() {
		return true;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFStatisticsAggregateReply setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
	
	public boolean isByteCountSupported() {
		return true;
	}
			
	public int getFlowCount() {
		return this.flow_count;
	}
	
	public OFStatisticsAggregateReply setFlowCount(int flow_count) {
		this.flow_count = flow_count;
		return this;
	}
	
	public boolean isFlowCountSupported() {
		return true;
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
        return super.toString() +  ":OFStatisticsAggregateReply-"+":packet_count=" + U64.f(packet_count) + 
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
        		
		final int prime = 2593;
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
        if (!(obj instanceof OFStatisticsAggregateReply)) {
            return false;
        }
        OFStatisticsAggregateReply other = (OFStatisticsAggregateReply) obj;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
		if ( flow_count != other.flow_count ) return false;
        return true;
    }
}
