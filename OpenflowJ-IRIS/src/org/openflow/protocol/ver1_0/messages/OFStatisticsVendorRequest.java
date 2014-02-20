package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsVendorRequest extends OFStatisticsRequest implements org.openflow.protocol.interfaces.OFStatisticsVendorRequest {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 4;

    org.openflow.protocol.interfaces.OFVendor  vendor;
	byte[]  data;

    public OFStatisticsVendorRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)16));
		setStatisticsType(OFStatisticsType.valueOf((short)0xffff, this.type));
    }
    
    public OFStatisticsVendorRequest(OFStatisticsVendorRequest other) {
    	super(other);
		this.vendor = new OFVendor((OFVendor)other.vendor);
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public org.openflow.protocol.interfaces.OFVendor getVendor() {
		return this.vendor;
	}
	
	public OFStatisticsVendorRequest setVendor(org.openflow.protocol.interfaces.OFVendor vendor) {
		this.vendor = vendor;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isVendorSupported() {
		return true;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFStatisticsVendorRequest setData(byte[] data) {
		this.data = data;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataSupported() {
		return true;
	}
			
	
	
	
	public OFStatisticsVendorRequest dup() {
		return new OFStatisticsVendorRequest(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.vendor == null) this.vendor = new OFVendor();
		this.vendor.readFrom(data);
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        vendor.writeTo(data);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsVendorRequest-"+":vendor=" + vendor + 
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
        		
		final int prime = 2539;
		int result = super.hashCode() * prime;
		result = prime * result + ((vendor == null)?0:vendor.hashCode());
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
        if (!(obj instanceof OFStatisticsVendorRequest)) {
            return false;
        }
        OFStatisticsVendorRequest other = (OFStatisticsVendorRequest) obj;
		if ( vendor == null && other.vendor != null ) { return false; }
		else if ( !vendor.equals(other.vendor) ) { return false; }
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
