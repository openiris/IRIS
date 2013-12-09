package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartMeterFeaturesReply extends OFMultipartReply  {
    public static int MINIMUM_LENGTH = 32;

    OFMeterFeatures  features;

    public OFMultipartMeterFeaturesReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)11, getType()));
		this.features = new OFMeterFeatures();
    }
    
    public OFMultipartMeterFeaturesReply(OFMultipartMeterFeaturesReply other) {
    	super(other);
		this.features = new OFMeterFeatures(other.features);
    }

	public OFMeterFeatures getFeatures() {
		return this.features;
	}
	
	public OFMultipartMeterFeaturesReply setFeatures(OFMeterFeatures features) {
		this.features = features;
		return this;
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
    public short alignment(short req) {
    	if (req == 0) return 0;
    	short l = (short)(computeLength() % req);
    	if ( l == 0 ) { return 0; }
    	return (short)( req - l );
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	return (short)(computeLength() - (short)MINIMUM_LENGTH + alignment((short)0));
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
