package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFRoleReply extends OFMessage implements org.openflow.protocol.ver1_3.interfaces.OFRoleReply {
    public static int MINIMUM_LENGTH = 8;

    byte[]  data;

    public OFRoleReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)25));
    }
    
    public OFRoleReply(OFRoleReply other) {
    	super(other);
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public byte[] getData() {
		return this.data;
	}
	
	public OFRoleReply setData(byte[] data) {
		this.data = data;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFRoleReply-"+":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 1439;
		int result = super.hashCode() * prime;
		result = prime * result + ((data == null)?0:java.util.Arrays.hashCode(data));
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
        if (!(obj instanceof OFRoleReply)) {
            return false;
        }
        OFRoleReply other = (OFRoleReply) obj;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
