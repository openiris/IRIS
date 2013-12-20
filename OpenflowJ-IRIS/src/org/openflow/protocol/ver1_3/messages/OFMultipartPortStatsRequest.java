package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartPortStatsRequest extends OFMultipartRequest implements org.openflow.protocol.interfaces.OFMultipartPortStatsRequest {
    public static int MINIMUM_LENGTH = 24;

    OFPortNo  port_no;
	int pad_1th;

    public OFMultipartPortStatsRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
		setMultipartType(OFMultipartType.valueOf((short)4, getType()));
    }
    
    public OFMultipartPortStatsRequest(OFMultipartPortStatsRequest other) {
    	super(other);
		this.port_no = other.port_no;
    }

	public OFPortNo getPortNo() {
		return this.port_no;
	}
	
	public OFMultipartPortStatsRequest setPortNo(OFPortNo port_no) {
		this.port_no = port_no;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port_no = OFPortNo.valueOf(OFPortNo.readFrom(data));
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.port_no.getValue());
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartPortStatsRequest-"+":port_no=" + port_no.toString();
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
        		
		final int prime = 1657;
		int result = super.hashCode() * prime;
		result = prime * result + ((port_no == null)?0:port_no.hashCode());
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
        if (!(obj instanceof OFMultipartPortStatsRequest)) {
            return false;
        }
        OFMultipartPortStatsRequest other = (OFMultipartPortStatsRequest) obj;
		if ( port_no == null && other.port_no != null ) { return false; }
		else if ( !port_no.equals(other.port_no) ) { return false; }
        return true;
    }
}
