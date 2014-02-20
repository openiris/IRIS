package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFStatisticsMeterFeaturesReply extends OFStatisticsReply implements org.openflow.protocol.interfaces.OFStatisticsMeterFeaturesReply {
    public static int MINIMUM_LENGTH = 32;
    public static int CORE_LENGTH = 16;

    org.openflow.protocol.interfaces.OFMeterFeatures  features;

    public OFStatisticsMeterFeaturesReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setStatisticsType(OFStatisticsType.valueOf((short)11, this.type));
    }
    
    public OFStatisticsMeterFeaturesReply(OFStatisticsMeterFeaturesReply other) {
    	super(other);
		this.features = new OFMeterFeatures((OFMeterFeatures)other.features);
    }

	public org.openflow.protocol.interfaces.OFMeterFeatures getFeatures() {
		return this.features;
	}
	
	public OFStatisticsMeterFeaturesReply setFeatures(org.openflow.protocol.interfaces.OFMeterFeatures features) {
		this.features = features;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isFeaturesSupported() {
		return true;
	}
			
	
	
	
	public OFStatisticsMeterFeaturesReply dup() {
		return new OFStatisticsMeterFeaturesReply(this);
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
        return super.toString() +  ":OFStatisticsMeterFeaturesReply-"+":features=" + features;
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
        if (!(obj instanceof OFStatisticsMeterFeaturesReply)) {
            return false;
        }
        OFStatisticsMeterFeaturesReply other = (OFStatisticsMeterFeaturesReply) obj;
		if ( features == null && other.features != null ) { return false; }
		else if ( !features.equals(other.features) ) { return false; }
        return true;
    }
}
