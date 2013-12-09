package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartRequest extends OFMultipart  {
    public static int MINIMUM_LENGTH = 16;

    OFMultipartType  multipart_type;
	OFMultipartRequestFlags  flags;
	int pad_1th;

    public OFMultipartRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
    }
    
    public OFMultipartRequest(OFMultipartRequest other) {
    	super(other);
		this.multipart_type = other.multipart_type;
		this.flags = other.flags;
    }

	public OFMultipartType getMultipartType() {
		return this.multipart_type;
	}
	
	public OFMultipartRequest setMultipartType(OFMultipartType multipart_type) {
		this.multipart_type = multipart_type;
		return this;
	}
			
	public short getFlags() {
		return this.flags.getValue();
	}
	
	public OFMultipartRequest setFlags(short flags) {
		if (this.flags == null) this.flags = new OFMultipartRequestFlags();
		this.flags.setValue( flags );
		return this;
	}

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.multipart_type = OFMultipartType.valueOf(OFMultipartType.readFrom(data), super.getType());
		if (this.flags == null) this.flags = new OFMultipartRequestFlags();
		this.flags.setValue( OFMultipartRequestFlags.readFrom(data) );
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.multipart_type.getTypeValue());
		data.putShort(this.flags.getValue());
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartRequest-"+":multipart_type=" + multipart_type.toString() + 
		":flags=" + flags.toString();
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
        		
		final int prime = 1879;
		int result = super.hashCode() * prime;
		result = prime * result + ((multipart_type == null)?0:multipart_type.hashCode());
		result = prime * result + ((flags == null)?0:flags.hashCode());
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
        if (!(obj instanceof OFMultipartRequest)) {
            return false;
        }
        OFMultipartRequest other = (OFMultipartRequest) obj;
		if ( multipart_type == null && other.multipart_type != null ) { return false; }
		else if ( !multipart_type.equals(other.multipart_type) ) { return false; }
		if ( flags == null && other.flags != null ) { return false; }
		else if ( !flags.equals(other.flags) ) { return false; }
        return true;
    }
}
