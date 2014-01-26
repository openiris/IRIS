package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyExperimenterMiss extends OFTableFeatureProperty implements org.openflow.protocol.interfaces.OFTableFeaturePropertyExperimenterMiss {
    public static int MINIMUM_LENGTH = 12;

    int  experimenter_id;
	int  subtype;
	byte[]  experimenter_data;

    public OFTableFeaturePropertyExperimenterMiss() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)65535));
    }
    
    public OFTableFeaturePropertyExperimenterMiss(OFTableFeaturePropertyExperimenterMiss other) {
    	super(other);
		this.experimenter_id = other.experimenter_id;
		this.subtype = other.subtype;
		if (other.experimenter_data != null) { this.experimenter_data = java.util.Arrays.copyOf(other.experimenter_data, other.experimenter_data.length); }
    }

	public int getExperimenterId() {
		return this.experimenter_id;
	}
	
	public OFTableFeaturePropertyExperimenterMiss setExperimenterId(int experimenter_id) {
		this.experimenter_id = experimenter_id;
		return this;
	}
	
	public boolean isExperimenterIdSupported() {
		return true;
	}
			
	public int getSubtype() {
		return this.subtype;
	}
	
	public OFTableFeaturePropertyExperimenterMiss setSubtype(int subtype) {
		this.subtype = subtype;
		return this;
	}
	
	public boolean isSubtypeSupported() {
		return true;
	}
			
	public byte[] getExperimenterData() {
		return this.experimenter_data;
	}
	
	public OFTableFeaturePropertyExperimenterMiss setExperimenterData(byte[] experimenter_data) {
		this.experimenter_data = experimenter_data;
		return this;
	}
	
	public boolean isExperimenterDataSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeaturePropertyExperimenterMiss dup() {
		return new OFTableFeaturePropertyExperimenterMiss(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.experimenter_id = data.getInt();
		this.subtype = data.getInt();
		if ( this.experimenter_data == null ) this.experimenter_data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.experimenter_data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.experimenter_id);
		data.putInt(this.subtype);
		if ( this.experimenter_data != null ) { data.put(this.experimenter_data); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyExperimenterMiss-"+":experimenter_id=" + U32.f(experimenter_id) + 
		":subtype=" + U32.f(subtype) + 
		":experimenter_data=" + java.util.Arrays.toString(experimenter_data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.experimenter_data != null ) { len += this.experimenter_data.length; } 
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
        		
		final int prime = 1669;
		int result = super.hashCode() * prime;
		result = prime * result + (int) experimenter_id;
		result = prime * result + (int) subtype;
		result = prime * result + ((experimenter_data == null)?0:java.util.Arrays.hashCode(experimenter_data));
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
        if (!(obj instanceof OFTableFeaturePropertyExperimenterMiss)) {
            return false;
        }
        OFTableFeaturePropertyExperimenterMiss other = (OFTableFeaturePropertyExperimenterMiss) obj;
		if ( experimenter_id != other.experimenter_id ) return false;
		if ( subtype != other.subtype ) return false;
		if ( experimenter_data == null && other.experimenter_data != null ) { return false; }
		else if ( !java.util.Arrays.equals(experimenter_data, other.experimenter_data) ) { return false; }
        return true;
    }
}
