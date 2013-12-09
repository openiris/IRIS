package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionId    {
    public static int MINIMUM_LENGTH = 8;

    short  type;
	short  length;
	int pad_1th;

    public OFActionId() {
        
    }
    
    public OFActionId(OFActionId other) {
    	this.type = other.type;
		this.length = other.length;
    }

	public short getType() {
		return this.type;
	}
	
	public OFActionId setType(short type) {
		this.type = type;
		return this;
	}
			
	public short getLength() {
		return this.length;
	}
	
	public OFActionId setLength(short length) {
		this.length = length;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.type = data.getShort();
		this.length = data.getShort();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.type);
		data.putShort(this.length);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return  ":OFActionId-"+":type=" + U16.f(type) + 
		":length=" + U16.f(length);
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
        		
		final int prime = 2293;
		int result = super.hashCode() * prime;
		result = prime * result + (int) type;
		result = prime * result + (int) length;
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
        if (!(obj instanceof OFActionId)) {
            return false;
        }
        OFActionId other = (OFActionId) obj;
		if ( type != other.type ) return false;
		if ( length != other.length ) return false;
        return true;
    }
}
