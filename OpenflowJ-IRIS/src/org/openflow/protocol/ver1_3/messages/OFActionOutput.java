package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionOutput extends OFAction  {
    public static int MINIMUM_LENGTH = 16;

    int  port;
	short  max_length;
	int pad_1th;
	short pad_2th;

    public OFActionOutput() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)0));
    }
    
    public OFActionOutput(OFActionOutput other) {
    	super(other);
		this.port = other.port;
		this.max_length = other.max_length;
    }

	public int getPort() {
		return this.port;
	}
	
	public OFActionOutput setPort(int port) {
		this.port = port;
		return this;
	}
			
	public short getMaxLength() {
		return this.max_length;
	}
	
	public OFActionOutput setMaxLength(short max_length) {
		this.max_length = max_length;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port = data.getInt();
		this.max_length = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.port);
		data.putShort(this.max_length);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFActionOutput-"+":port=" + U32.f(port) + 
		":max_length=" + U16.f(max_length);
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
        		
		final int prime = 2281;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port;
		result = prime * result + (int) max_length;
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
        if (!(obj instanceof OFActionOutput)) {
            return false;
        }
        OFActionOutput other = (OFActionOutput) obj;
		if ( port != other.port ) return false;
		if ( max_length != other.max_length ) return false;
        return true;
    }
}
