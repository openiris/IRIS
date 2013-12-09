package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFActionSetVlanId extends OFAction  {
    public static int MINIMUM_LENGTH = 8;

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
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
    	short l = (short)(computeLength() % req);
    	if ( l == 0 ) { return 0; }
    	return (short)( req - l );
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	return (short)(computeLength() - (short)MINIMUM_LENGTH + alignment((short)0));
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
