package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionSetMplsTtl extends OFAction implements org.openflow.protocol.interfaces.OFActionSetMplsTtl {
    public static int MINIMUM_LENGTH = 8;

    byte  mpls_ttl;
	short pad_1th;
	byte pad_2th;

    public OFActionSetMplsTtl() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)15));
    }
    
    public OFActionSetMplsTtl(OFActionSetMplsTtl other) {
    	super(other);
		this.mpls_ttl = other.mpls_ttl;
    }

	public byte getMplsTtl() {
		return this.mpls_ttl;
	}
	
	public OFActionSetMplsTtl setMplsTtl(byte mpls_ttl) {
		this.mpls_ttl = mpls_ttl;
		return this;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.mpls_ttl = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.mpls_ttl);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetMplsTtl-"+":mpls_ttl=" + U8.f(mpls_ttl);
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
        		
		final int prime = 2267;
		int result = super.hashCode() * prime;
		result = prime * result + (int) mpls_ttl;
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
        if (!(obj instanceof OFActionSetMplsTtl)) {
            return false;
        }
        OFActionSetMplsTtl other = (OFActionSetMplsTtl) obj;
		if ( mpls_ttl != other.mpls_ttl ) return false;
        return true;
    }
}
