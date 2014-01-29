package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFVendor extends OFMessage implements org.openflow.protocol.interfaces.OFVendor {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 8;

    int  vendor_id;
	int  subtype;
	byte[]  data;

    public OFVendor() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)4));
    }
    
    public OFVendor(OFVendor other) {
    	super(other);
		this.vendor_id = other.vendor_id;
		this.subtype = other.subtype;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getVendorId() {
		return this.vendor_id;
	}
	
	public OFVendor setVendorId(int vendor_id) {
		this.vendor_id = vendor_id;
		return this;
	}
	
	public boolean isVendorIdSupported() {
		return true;
	}
			
	public int getSubtype() {
		return this.subtype;
	}
	
	public OFVendor setSubtype(int subtype) {
		this.subtype = subtype;
		return this;
	}
	
	public boolean isSubtypeSupported() {
		return true;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFVendor setData(byte[] data) {
		this.data = data;
		return this;
	}
	
	public boolean isDataSupported() {
		return true;
	}
			
	
	
	
	public OFVendor dup() {
		return new OFVendor(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.vendor_id = data.getInt();
		this.subtype = data.getInt();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.vendor_id);
		data.putInt(this.subtype);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFVendor-"+":vendor_id=" + U32.f(vendor_id) + 
		":subtype=" + U32.f(subtype) + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
        		
		final int prime = 2957;
		int result = super.hashCode() * prime;
		result = prime * result + (int) vendor_id;
		result = prime * result + (int) subtype;
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
        if (!(obj instanceof OFVendor)) {
            return false;
        }
        OFVendor other = (OFVendor) obj;
		if ( vendor_id != other.vendor_id ) return false;
		if ( subtype != other.subtype ) return false;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
