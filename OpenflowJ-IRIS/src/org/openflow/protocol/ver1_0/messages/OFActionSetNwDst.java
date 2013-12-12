package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFActionSetNwDst extends OFAction  {
    public static int MINIMUM_LENGTH = 8;

    int  nw_addr;

    public OFActionSetNwDst() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)7));
    }
    
    public OFActionSetNwDst(OFActionSetNwDst other) {
    	super(other);
		this.nw_addr = other.nw_addr;
    }

	public int getNwAddr() {
		return this.nw_addr;
	}
	
	public OFActionSetNwDst setNwAddr(int nw_addr) {
		this.nw_addr = nw_addr;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.nw_addr = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.nw_addr);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetNwDst-"+":nw_addr=" + U32.f(nw_addr);
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
        		
		final int prime = 2791;
		int result = super.hashCode() * prime;
		result = prime * result + (int) nw_addr;
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
        if (!(obj instanceof OFActionSetNwDst)) {
            return false;
        }
        OFActionSetNwDst other = (OFActionSetNwDst) obj;
		if ( nw_addr != other.nw_addr ) return false;
        return true;
    }
}
