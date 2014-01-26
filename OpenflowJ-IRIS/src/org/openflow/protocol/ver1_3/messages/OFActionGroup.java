package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionGroup extends OFAction implements org.openflow.protocol.interfaces.OFActionGroup {
    public static int MINIMUM_LENGTH = 8;

    int  group_id;

    public OFActionGroup() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)22));
    }
    
    public OFActionGroup(OFActionGroup other) {
    	super(other);
		this.group_id = other.group_id;
    }

	public int getGroupId() {
		return this.group_id;
	}
	
	public OFActionGroup setGroupId(int group_id) {
		this.group_id = group_id;
		return this;
	}
	
	public boolean isGroupIdSupported() {
		return true;
	}
			
	
	
	
	public OFActionGroup dup() {
		return new OFActionGroup(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.group_id = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.group_id);
    }

    public String toString() {
        return super.toString() +  ":OFActionGroup-"+":group_id=" + U32.f(group_id);
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
        		
		final int prime = 2207;
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
        if (!(obj instanceof OFActionGroup)) {
            return false;
        }
        OFActionGroup other = (OFActionGroup) obj;
		if ( group_id != other.group_id ) return false;
        return true;
    }
}
