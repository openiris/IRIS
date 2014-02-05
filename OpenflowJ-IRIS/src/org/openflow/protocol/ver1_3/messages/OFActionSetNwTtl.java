package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionSetNwTtl extends OFAction implements org.openflow.protocol.interfaces.OFActionSetNwTtl {
    public static int MINIMUM_LENGTH = 8;
    public static int CORE_LENGTH = 4;

    byte  nw_ttl;
	short pad_1th;
	byte pad_2th;

    public OFActionSetNwTtl() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)23));
    }
    
    public OFActionSetNwTtl(OFActionSetNwTtl other) {
    	super(other);
		this.nw_ttl = other.nw_ttl;
    }

	public byte getNwTtl() {
		return this.nw_ttl;
	}
	
	public OFActionSetNwTtl setNwTtl(byte nw_ttl) {
		this.nw_ttl = nw_ttl;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNwTtlSupported() {
		return true;
	}
			
	
	
	
	public OFActionSetNwTtl dup() {
		return new OFActionSetNwTtl(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.nw_ttl = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.nw_ttl);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetNwTtl-"+":nw_ttl=" + U8.f(nw_ttl);
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
        		
		final int prime = 2203;
		int result = super.hashCode() * prime;
		result = prime * result + (int) nw_ttl;
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
        if (!(obj instanceof OFActionSetNwTtl)) {
            return false;
        }
        OFActionSetNwTtl other = (OFActionSetNwTtl) obj;
		if ( nw_ttl != other.nw_ttl ) return false;
        return true;
    }
}
