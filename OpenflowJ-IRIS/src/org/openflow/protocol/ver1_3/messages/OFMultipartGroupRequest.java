package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartGroupRequest extends OFMultipartRequest implements org.openflow.protocol.interfaces.OFMultipartGroupRequest {
    public static int MINIMUM_LENGTH = 24;

    int  group_id;
	int pad_1th;

    public OFMultipartGroupRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
		setMultipartType(OFMultipartType.valueOf((short)6, this.type));
    }
    
    public OFMultipartGroupRequest(OFMultipartGroupRequest other) {
    	super(other);
		this.group_id = other.group_id;
    }

	public int getGroupId() {
		return this.group_id;
	}
	
	public OFMultipartGroupRequest setGroupId(int group_id) {
		this.group_id = group_id;
		return this;
	}
	
	public boolean isGroupIdSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.group_id = data.getInt();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.group_id);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartGroupRequest-"+":group_id=" + U32.f(group_id);
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
        		
		final int prime = 1619;
		int result = super.hashCode() * prime;
		result = prime * result + (int) group_id;
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
        if (!(obj instanceof OFMultipartGroupRequest)) {
            return false;
        }
        OFMultipartGroupRequest other = (OFMultipartGroupRequest) obj;
		if ( group_id != other.group_id ) return false;
        return true;
    }
}
