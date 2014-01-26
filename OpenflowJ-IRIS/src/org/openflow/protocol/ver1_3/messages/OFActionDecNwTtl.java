package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionDecNwTtl extends OFAction implements org.openflow.protocol.interfaces.OFActionDecNwTtl {
    public static int MINIMUM_LENGTH = 8;

    int pad_1th;

    public OFActionDecNwTtl() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)24));
    }
    
    public OFActionDecNwTtl(OFActionDecNwTtl other) {
    	super(other);
    }

	
	
	
	
	public OFActionDecNwTtl dup() {
		return new OFActionDecNwTtl(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFActionDecNwTtl";
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
        		
		final int prime = 2179;
		int result = super.hashCode() * prime;
		
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
        if (!(obj instanceof OFActionDecNwTtl)) {
            return false;
        }
        
        return true;
    }
}
