package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFPortStatus extends OFMessage implements org.openflow.protocol.interfaces.OFPortStatus {
    public static int MINIMUM_LENGTH = 80;
    public static int CORE_LENGTH = 72;

    OFPortReason  reason;
	int pad_1th;
	short pad_2th;
	byte pad_3th;
	org.openflow.protocol.interfaces.OFPortDesc  desc;

    public OFPortStatus() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)12));
    }
    
    public OFPortStatus(OFPortStatus other) {
    	super(other);
		this.reason = other.reason;
		this.desc = new OFPortDesc((OFPortDesc)other.desc);
    }

	public org.openflow.protocol.interfaces.OFPortReason getReason() {
		return OFPortReason.to(this.reason);
	}
	
	public OFPortStatus setReason(org.openflow.protocol.interfaces.OFPortReason reason) {
		this.reason = OFPortReason.from(reason);
		return this;
	}
	
	public OFPortStatus setReason(OFPortReason reason) {
		this.reason = reason;
		return this;
	}

	@org.codehaus.jackson.annotate.JsonIgnore	
	public boolean isReasonSupported() {
		return true;
	}
	
	public org.openflow.protocol.interfaces.OFPortDesc getDesc() {
		return this.desc;
	}
	
	public OFPortStatus setDesc(org.openflow.protocol.interfaces.OFPortDesc desc) {
		this.desc = desc;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDescSupported() {
		return true;
	}
			
	
	
	
	public OFPortStatus dup() {
		return new OFPortStatus(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.reason = OFPortReason.valueOf(OFPortReason.readFrom(data));
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		this.pad_3th = data.get();
		if (this.desc == null) this.desc = new OFPortDesc();
		this.desc.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.reason.getValue());
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		data.put(this.pad_3th);
		desc.writeTo(data);
    }

    public String toString() {
        return super.toString() +  ":OFPortStatus-"+":reason=" + reason.toString() + 
		":desc=" + desc.toString();
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
        		
		final int prime = 2341;
		int result = super.hashCode() * prime;
		result = prime * result + ((reason == null)?0:reason.hashCode());
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
		if ( reason == null && other.reason != null ) { return false; }
		else if ( !reason.equals(other.reason) ) { return false; }
		if ( desc == null && other.desc != null ) { return false; }
		else if ( !desc.equals(other.desc) ) { return false; }
        return true;
    }
}
