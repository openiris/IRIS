package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFPortStatus extends OFMessage implements org.openflow.protocol.ver1_0.interfaces.OFPortStatus {
    public static int MINIMUM_LENGTH = 64;

    byte  reason;
	int pad_1th;
	short pad_2th;
	byte pad_3th;
	org.openflow.protocol.ver1_0.interfaces.OFPortDesc  desc;

    public OFPortStatus() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)12));
		this.desc = new OFPortDesc();
    }
    
    public OFPortStatus(OFPortStatus other) {
    	super(other);
		this.reason = other.reason;
		this.desc = new OFPortDesc((OFPortDesc)other.desc);
    }

	public byte getReason() {
		return this.reason;
	}
	
	public OFPortStatus setReason(byte reason) {
		this.reason = reason;
		return this;
	}
			
	public org.openflow.protocol.ver1_0.interfaces.OFPortDesc getDesc() {
		return this.desc;
	}
	
	public OFPortStatus setDesc(org.openflow.protocol.ver1_0.interfaces.OFPortDesc desc) {
		this.desc = desc;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.reason = data.get();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		this.pad_3th = data.get();
		if (this.desc == null) this.desc = new OFPortDesc();
		this.desc.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.reason);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		data.put(this.pad_3th);
		desc.writeTo(data);
    }

    public String toString() {
        return super.toString() +  ":OFPortStatus-"+":reason=" + U8.f(reason) + 
		":desc=" + desc.toString();
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
        		
		final int prime = 2879;
		int result = super.hashCode() * prime;
		result = prime * result + (int) reason;
		result = prime * result + ((desc == null)?0:desc.hashCode());
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
        if (!(obj instanceof OFPortStatus)) {
            return false;
        }
        OFPortStatus other = (OFPortStatus) obj;
		if ( reason != other.reason ) return false;
		if ( desc == null && other.desc != null ) { return false; }
		else if ( !desc.equals(other.desc) ) { return false; }
        return true;
    }
}
