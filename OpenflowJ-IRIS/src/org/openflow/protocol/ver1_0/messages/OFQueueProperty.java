package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFQueueProperty   implements org.openflow.protocol.interfaces.OFQueueProperty {
    public static int MINIMUM_LENGTH = 8;
    public static int CORE_LENGTH = 8;

    OFQueuePropertyType  type;
	short  length;
	int pad_1th;

    public OFQueueProperty() {
        
    }
    
    public OFQueueProperty(OFQueueProperty other) {
    	this.type = other.type;
		this.length = other.length;
    }

	public org.openflow.protocol.interfaces.OFQueuePropertyType getType() {
		return OFQueuePropertyType.to(this.type);
	}
	
	public OFQueueProperty setType(org.openflow.protocol.interfaces.OFQueuePropertyType type) {
		this.type = OFQueuePropertyType.from(type);
		return this;
	}
	
	public OFQueueProperty setType(OFQueuePropertyType type) {
		this.type = type;
		return this;
	}

	@org.codehaus.jackson.annotate.JsonIgnore	
	public boolean isTypeSupported() {
		return true;
	}
	
	public short getLength() {
		return this.length;
	}
	
	public OFQueueProperty setLength(short length) {
		this.length = length;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isLengthSupported() {
		return true;
	}
			
	
	
	
	public OFQueueProperty dup() {
		return new OFQueueProperty(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.type = OFQueuePropertyType.valueOf(OFQueuePropertyType.readFrom(data));
		this.length = data.getShort();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.type.getTypeValue());
		data.putShort(this.length);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return  ":OFQueueProperty-"+":type=" + type + 
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
        		
		final int prime = 2521;
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
        if (!(obj instanceof OFQueueProperty)) {
            return false;
        }
        OFQueueProperty other = (OFQueueProperty) obj;
		if ( type == null && other.type != null ) { return false; }
		else if ( !type.equals(other.type) ) { return false; }
		if ( length != other.length ) return false;
        return true;
    }
}
