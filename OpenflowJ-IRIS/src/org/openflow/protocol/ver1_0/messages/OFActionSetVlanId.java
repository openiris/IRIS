package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFActionSetVlanId extends OFAction implements org.openflow.protocol.interfaces.OFActionSetVlanId {
    public static int MINIMUM_LENGTH = 8;
    public static int CORE_LENGTH = 4;

    short  vlan_id;
	short pad_1th;

    public OFActionSetVlanId() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)1));
    }
    
    public OFActionSetVlanId(OFActionSetVlanId other) {
    	super(other);
		this.vlan_id = other.vlan_id;
    }

	public short getVlanId() {
		return this.vlan_id;
	}
	
	public OFActionSetVlanId setVlanId(short vlan_id) {
		this.vlan_id = vlan_id;
		return this;
	}
	
	public boolean isVlanIdSupported() {
		return true;
	}
			
	
	
	
	public OFActionSetVlanId dup() {
		return new OFActionSetVlanId(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.vlan_id = data.getShort();
		this.pad_1th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.vlan_id);
		data.putShort(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetVlanId-"+":vlan_id=" + U16.f(vlan_id);
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
        		
		final int prime = 2837;
		int result = super.hashCode() * prime;
		result = prime * result + (int) vlan_id;
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
        if (!(obj instanceof OFActionSetVlanId)) {
            return false;
        }
        OFActionSetVlanId other = (OFActionSetVlanId) obj;
		if ( vlan_id != other.vlan_id ) return false;
        return true;
    }
}
