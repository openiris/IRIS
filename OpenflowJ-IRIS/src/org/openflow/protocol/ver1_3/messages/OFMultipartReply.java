package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import java.util.Set;

public class OFMultipartReply extends OFMultipart implements org.openflow.protocol.interfaces.OFMultipartReply {
    public static int MINIMUM_LENGTH = 16;

    OFMultipartType  multipart_type;
	short  flags;
	int pad_1th;

    public OFMultipartReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		this.flags = 0;
    }
    
    public OFMultipartReply(OFMultipartReply other) {
    	super(other);
		this.multipart_type = other.multipart_type;
		this.flags = other.flags;
    }

	public org.openflow.protocol.interfaces.OFMultipartType getMultipartType() {
		return OFMultipartType.to(this.multipart_type.getTypeValue(), this.type);
	}
	
	public OFMultipartReply setMultipartType(org.openflow.protocol.interfaces.OFMultipartType multipart_type) {
		this.multipart_type = OFMultipartType.from(multipart_type, this.type);
		return this;
	}
	
	public OFMultipartReply setMultipartType(OFMultipartType multipart_type) {
		this.multipart_type = multipart_type;
		return this;
	}
	
	public short getFlagsWire() {
		return this.flags;
	}
	
	public OFMultipartReply setFlagsWire(short flags) {
		this.flags = flags;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFMultipartReplyFlags> getFlags() {
		OFMultipartReplyFlags tmp = OFMultipartReplyFlags.of(this.flags);
		Set<org.openflow.protocol.interfaces.OFMultipartReplyFlags> ret = new HashSet<org.openflow.protocol.interfaces.OFMultipartReplyFlags>();
		for ( org.openflow.protocol.interfaces.OFMultipartReplyFlags v : org.openflow.protocol.interfaces.OFMultipartReplyFlags.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFMultipartReply setFlags(Set<org.openflow.protocol.interfaces.OFMultipartReplyFlags> values) {
		OFMultipartReplyFlags tmp = OFMultipartReplyFlags.of(this.flags);
		tmp.and( values );
		this.flags = tmp.get();
		return this;
	}
		
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.multipart_type = OFMultipartType.valueOf(OFMultipartType.readFrom(data), this.type);
		this.flags = data.getShort();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.multipart_type.getTypeValue());
		data.putShort(this.flags);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartReply-"+":multipart_type=" + multipart_type.toString() + 
		":flags=" + U16.f(flags);
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
        		
		final int prime = 1877;
		int result = super.hashCode() * prime;
		result = prime * result + ((multipart_type == null)?0:multipart_type.hashCode());
		result = prime * result + (int) flags;
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
        if (!(obj instanceof OFMultipartReply)) {
            return false;
        }
        OFMultipartReply other = (OFMultipartReply) obj;
		if ( multipart_type == null && other.multipart_type != null ) { return false; }
		else if ( !multipart_type.equals(other.multipart_type) ) { return false; }
		if ( flags != other.flags ) return false;
        return true;
    }
}
