package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMeterBandDscpRemark extends OFMeterBand implements org.openflow.protocol.interfaces.OFMeterBandDscpRemark {
    public static int MINIMUM_LENGTH = 16;

    byte  prec_level;
	short pad_1th;
	byte pad_2th;

    public OFMeterBandDscpRemark() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMeterBandType.valueOf((short)2));
    }
    
    public OFMeterBandDscpRemark(OFMeterBandDscpRemark other) {
    	super(other);
		this.prec_level = other.prec_level;
    }

	public byte getPrecLevel() {
		return this.prec_level;
	}
	
	public OFMeterBandDscpRemark setPrecLevel(byte prec_level) {
		this.prec_level = prec_level;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.prec_level = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.prec_level);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFMeterBandDscpRemark-"+":prec_level=" + U8.f(prec_level);
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
        		
		final int prime = 1993;
		int result = super.hashCode() * prime;
		result = prime * result + (int) prec_level;
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
        if (!(obj instanceof OFMeterBandDscpRemark)) {
            return false;
        }
        OFMeterBandDscpRemark other = (OFMeterBandDscpRemark) obj;
		if ( prec_level != other.prec_level ) return false;
        return true;
    }
}
