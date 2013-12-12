package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFOxm    {
    public static int MINIMUM_LENGTH = 4;

    OFOxmClass  oxm_class;
	byte  field_bitmask;
	byte  payload_length;
	byte[]  data;

    public OFOxm() {
        
    }
    
    public OFOxm(OFOxm other) {
    	this.oxm_class = other.oxm_class;
		this.field_bitmask = other.field_bitmask;
		this.payload_length = other.payload_length;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public OFOxmClass getOxmClass() {
		return this.oxm_class;
	}
	
	public OFOxm setOxmClass(OFOxmClass oxm_class) {
		this.oxm_class = oxm_class;
		return this;
	}
			
	public byte getField() {
		byte t__ = (byte)(this.field_bitmask & 0b111111110);
		return (byte)(t__ >> 1);
	}
	
	public OFOxm setField(byte field_bitmask) {
		this.field_bitmask |= (byte) field_bitmask << 1;
		return this;
	}
			
	public byte getBitmask() {
		byte t__ = (byte)(this.field_bitmask & 0b1);
		return (byte)(t__ >> 0);
	}
	
	public OFOxm setBitmask(byte field_bitmask) {
		this.field_bitmask |= (byte) field_bitmask << 0;
		return this;
	}
			
	public byte getPayloadLength() {
		return this.payload_length;
	}
	
	public OFOxm setPayloadLength(byte payload_length) {
		this.payload_length = payload_length;
		return this;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFOxm setData(byte[] data) {
		this.data = data;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.oxm_class = OFOxmClass.valueOf(OFOxmClass.readFrom(data));
		this.field_bitmask = data.get();
		this.payload_length = data.get();
		if ( this.payload_length > 0 ) {
			if ( this.data == null ) this.data = new byte[this.payload_length];
			data.get(this.data);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.oxm_class.getValue());
		data.put(this.field_bitmask);
		data.put(this.payload_length);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return  ":OFOxm-"+":oxm_class=" + oxm_class.toString() + 
		":field_bitmask=" + U8.f(field_bitmask) + 
		":payload_length=" + U8.f(payload_length) + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 2333;
		int result = super.hashCode() * prime;
		result = prime * result + ((oxm_class == null)?0:oxm_class.hashCode());
		result = prime * result + (int) field_bitmask;
		result = prime * result + (int) payload_length;
		result = prime * result + ((data == null)?0:java.util.Arrays.hashCode(data));
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
        if (!(obj instanceof OFOxm)) {
            return false;
        }
        OFOxm other = (OFOxm) obj;
		if ( oxm_class == null && other.oxm_class != null ) { return false; }
		else if ( !oxm_class.equals(other.oxm_class) ) { return false; }
		if ( field_bitmask != other.field_bitmask ) return false;
		if ( payload_length != other.payload_length ) return false;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
