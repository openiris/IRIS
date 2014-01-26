package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFGetConfigReply extends OFMessage implements org.openflow.protocol.interfaces.OFGetConfigReply {
    public static int MINIMUM_LENGTH = 12;

    short  flags;
	short  miss_send_length;

    public OFGetConfigReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)8));
    }
    
    public OFGetConfigReply(OFGetConfigReply other) {
    	super(other);
		this.flags = other.flags;
		this.miss_send_length = other.miss_send_length;
    }

	public short getFlags() {
		return this.flags;
	}
	
	public OFGetConfigReply setFlags(short flags) {
		this.flags = flags;
		return this;
	}
	
	public boolean isFlagsSupported() {
		return true;
	}
			
	public short getMissSendLength() {
		return this.miss_send_length;
	}
	
	public OFGetConfigReply setMissSendLength(short miss_send_length) {
		this.miss_send_length = miss_send_length;
		return this;
	}
	
	public boolean isMissSendLengthSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFGetConfigReply setMissSendLen(short value) {
		throw new UnsupportedOperationException("setMissSendLen is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getMissSendLen() {
		throw new UnsupportedOperationException("getMissSendLen is not supported operation");
	}
	
	public boolean isMissSendLenSupported() {
		return false;
	}
	
	
	
	
	public OFGetConfigReply dup() {
		return new OFGetConfigReply(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.flags = data.getShort();
		this.miss_send_length = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.flags);
		data.putShort(this.miss_send_length);
    }

    public String toString() {
        return super.toString() +  ":OFGetConfigReply-"+":flags=" + U16.f(flags) + 
		":miss_send_length=" + U16.f(miss_send_length);
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
        		
		final int prime = 2917;
		int result = super.hashCode() * prime;
		result = prime * result + (int) flags;
		result = prime * result + (int) miss_send_length;
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
        if (!(obj instanceof OFGetConfigReply)) {
            return false;
        }
        OFGetConfigReply other = (OFGetConfigReply) obj;
		if ( flags != other.flags ) return false;
		if ( miss_send_length != other.miss_send_length ) return false;
        return true;
    }
}
