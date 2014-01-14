package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFBucketCounter   implements org.openflow.protocol.interfaces.OFBucketCounter {
    public static int MINIMUM_LENGTH = 16;

    long  packet_count;
	long  byte_count;

    public OFBucketCounter() {
        
    }
    
    public OFBucketCounter(OFBucketCounter other) {
    	this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
    }

	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFBucketCounter setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
	
	public boolean isPacketCountSupported() {
		return true;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFBucketCounter setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
	
	public boolean isByteCountSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        this.packet_count = data.getLong();
		this.byte_count = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putLong(this.packet_count);
		data.putLong(this.byte_count);
    }

    public String toString() {
        return  ":OFBucketCounter-"+":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count);
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
        		
		final int prime = 1913;
		int result = super.hashCode() * prime;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
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
        if (!(obj instanceof OFBucketCounter)) {
            return false;
        }
        OFBucketCounter other = (OFBucketCounter) obj;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
        return true;
    }
}
