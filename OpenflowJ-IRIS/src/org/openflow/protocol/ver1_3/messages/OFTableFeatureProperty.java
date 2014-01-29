package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeatureProperty   implements org.openflow.protocol.interfaces.OFTableFeatureProperty {
    public static int MINIMUM_LENGTH = 4;
    public static int CORE_LENGTH = 4;

    OFTableFeaturePropertyType  type;
	short  length;

    public OFTableFeatureProperty() {
        
    }
    
    public OFTableFeatureProperty(OFTableFeatureProperty other) {
    	this.type = other.type;
		this.length = other.length;
    }

	public org.openflow.protocol.interfaces.OFTableFeaturePropertyType getType() {
		return OFTableFeaturePropertyType.to(this.type);
	}
	
	public OFTableFeatureProperty setType(org.openflow.protocol.interfaces.OFTableFeaturePropertyType type) {
		this.type = OFTableFeaturePropertyType.from(type);
		return this;
	}
	
	public OFTableFeatureProperty setType(OFTableFeaturePropertyType type) {
		this.type = type;
		return this;
	}
	
	public boolean isTypeSupported() {
		return true;
	}
	
	public short getLength() {
		return this.length;
	}
	
	public OFTableFeatureProperty setLength(short length) {
		this.length = length;
		return this;
	}
	
	public boolean isLengthSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeatureProperty dup() {
		return new OFTableFeatureProperty(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.type = OFTableFeaturePropertyType.valueOf(OFTableFeaturePropertyType.readFrom(data));
		this.length = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.type.getTypeValue());
		data.putShort(this.length);
    }

    public String toString() {
        return  ":OFTableFeatureProperty-"+":type=" + type.toString() + 
		":length=" + U16.f(length);
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
        		
		final int prime = 1801;
		int result = super.hashCode() * prime;
		result = prime * result + ((type == null)?0:type.hashCode());
		result = prime * result + (int) length;
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
        if (!(obj instanceof OFTableFeatureProperty)) {
            return false;
        }
        OFTableFeatureProperty other = (OFTableFeatureProperty) obj;
		if ( type == null && other.type != null ) { return false; }
		else if ( !type.equals(other.type) ) { return false; }
		if ( length != other.length ) return false;
        return true;
    }
}
