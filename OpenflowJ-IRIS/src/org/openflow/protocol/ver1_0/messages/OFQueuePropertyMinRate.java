package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFQueuePropertyMinRate extends OFQueueProperty  {
    public static int MINIMUM_LENGTH = 16;

    short  rate;
	int pad_1th;
	short pad_2th;

    public OFQueuePropertyMinRate() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFQueuePropertyType.valueOf((int)1));
    }
    
    public OFQueuePropertyMinRate(OFQueuePropertyMinRate other) {
    	super(other);
		this.rate = other.rate;
    }

	public short getRate() {
		return this.rate;
	}
	
	public OFQueuePropertyMinRate setRate(short rate) {
		this.rate = rate;
		return this;
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
        return super.toString() +  ":OFQueuePropertyMinRate-"+":rate=" + U16.f(rate);
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
        		
		final int prime = 2477;
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
        if (!(obj instanceof OFQueuePropertyMinRate)) {
            return false;
        }
        OFQueuePropertyMinRate other = (OFQueuePropertyMinRate) obj;
		if ( rate != other.rate ) return false;
        return true;
    }
}
