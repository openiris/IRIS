package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFMessage   implements org.openflow.protocol.OFMessage {
    public static int MINIMUM_LENGTH = 8;

    byte  version;
	OFMessageType  type;
	short  length;
	int  xid;

    public OFMessage() {
        this.version = (byte)0x01;
    }
    
    public OFMessage(OFMessage other) {
    	this.version = other.version;
		this.type = other.type;
		this.length = other.length;
		this.xid = other.xid;
    }

	public byte getTypeByte() { return this.type.getTypeValue(); }

	public byte getVersion() {
		return this.version;
	}
	
	public OFMessage setVersion(byte version) {
		this.version = version;
		return this;
	}
			
	public short getVersionU() {
		return U8.f(this.version);
	}
	
	public OFMessage setVersionU(short version) {
		this.version = U8.t(version);
		return this;
	}
	
	public OFMessageType getType() {
		return this.type;
	}
	
	public OFMessage setType(OFMessageType type) {
		this.type = type;
		return this;
	}
			
	public short getLength() {
		return this.length;
	}
	
	public OFMessage setLength(short length) {
		this.length = length;
		return this;
	}
			
	public int getLengthU() {
		return U16.f(this.length);
	}
	
	public OFMessage setLengthU(int length) {
		this.length = U16.t(length);
		return this;
	}
	
	public int getXid() {
		return this.xid;
	}
	
	public OFMessage setXid(int xid) {
		this.xid = xid;
		return this;
	}
			
	public long getXidU() {
		return U32.f(this.xid);
	}
	
	public OFMessage setXidU(long xid) {
		this.xid = U32.t(xid);
		return this;
	}
	

    public void readFrom(ByteBuffer data) {
        this.version = data.get();
		this.type = OFMessageType.valueOf(OFMessageType.readFrom(data));
		this.length = data.getShort();
		this.xid = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.put(this.version);
		data.put(this.type.getTypeValue());
		data.putShort(this.length);
		data.putInt(this.xid);
    }

    public String toString() {
        return  ":OFMessage-"+":version=" + U8.f(version) + 
		":type=" + type.toString() + 
		":length=" + U16.f(length) + 
		":xid=" + U32.f(xid);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2999;
		int result = super.hashCode() * prime;
		result = prime * result + (int) version;
		result = prime * result + ((type == null)?0:type.hashCode());
		result = prime * result + (int) length;
		result = prime * result + (int) xid;
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
        if (!(obj instanceof OFMessage)) {
            return false;
        }
        OFMessage other = (OFMessage) obj;
		if ( version != other.version ) return false;
		if ( type == null && other.type != null ) { return false; }
		else if ( !type.equals(other.type) ) { return false; }
		if ( length != other.length ) return false;
		if ( xid != other.xid ) return false;
        return true;
    }
}
