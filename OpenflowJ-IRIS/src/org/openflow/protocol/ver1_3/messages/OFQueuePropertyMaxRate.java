package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFQueuePropertyMaxRate extends OFQueueProperty implements org.openflow.protocol.interfaces.OFQueuePropertyMaxRate {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 8;

    short  rate;
	int pad_1th;
	short pad_2th;

    public OFQueuePropertyMaxRate() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFQueuePropertyType.valueOf((short)2));
    }
    
    public OFQueuePropertyMaxRate(OFQueuePropertyMaxRate other) {
    	super(other);
		this.rate = other.rate;
    }

	public short getRate() {
		return this.rate;
	}
	
	public OFQueuePropertyMaxRate setRate(short rate) {
		this.rate = rate;
		return this;
	}
	
	public boolean isRateSupported() {
		return true;
	}
			
	
	
	
	public OFQueuePropertyMaxRate dup() {
		return new OFQueuePropertyMaxRate(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.rate = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.rate);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFQueuePropertyMaxRate-"+":rate=" + U16.f(rate);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
        		
		final int prime = 1481;
		int result = super.hashCode() * prime;
		result = prime * result + (int) rate;
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
        if (!(obj instanceof OFQueuePropertyMaxRate)) {
            return false;
        }
        OFQueuePropertyMaxRate other = (OFQueuePropertyMaxRate) obj;
		if ( rate != other.rate ) return false;
        return true;
    }
}
