package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartMeterFeaturesReply extends OFMultipartReply implements org.openflow.protocol.interfaces.OFMultipartMeterFeaturesReply {
    public static int MINIMUM_LENGTH = 32;

    org.openflow.protocol.interfaces.OFMeterFeatures  features;

    public OFMultipartMeterFeaturesReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)11, this.type));
    }
    
    public OFMultipartMeterFeaturesReply(OFMultipartMeterFeaturesReply other) {
    	super(other);
		this.features = new OFMeterFeatures((OFMeterFeatures)other.features);
    }

	public org.openflow.protocol.interfaces.OFMeterFeatures getFeatures() {
		return this.features;
	}
	
	public OFMultipartMeterFeaturesReply setFeatures(org.openflow.protocol.interfaces.OFMeterFeatures features) {
		this.features = features;
		return this;
	}
	
	public boolean isFeaturesSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		if (this.features == null) this.features = new OFMeterFeatures();
		this.features.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        features.writeTo(data);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartMeterFeaturesReply-"+":features=" + features.toString();
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
        		
		final int prime = 1543;
		int result = super.hashCode() * prime;
		result = prime * result + ((features == null)?0:features.hashCode());
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
        if (!(obj instanceof OFMultipartMeterFeaturesReply)) {
            return false;
        }
        OFMultipartMeterFeaturesReply other = (OFMultipartMeterFeaturesReply) obj;
		if ( features == null && other.features != null ) { return false; }
		else if ( !features.equals(other.features) ) { return false; }
        return true;
    }
}
