package etri.sdn.controller;

import java.nio.ByteBuffer;

import org.openflow.util.U16;
import org.openflow.util.U32;
import org.openflow.util.U8;

class OFHeader {
    public static int MINIMUM_LENGTH = 8;

    byte  version;
	byte  type;
	short  length;
	int  xid;

    public OFHeader() {
    }

	public byte getVersion() {
		return this.version;
	}
			
	public short getVersionU() {
		return U8.f(this.version);
	}
	
	public byte getType() {
		return this.type;
	}
			
	public short getLength() {
		return this.length;
	}
			
	public int getLengthU() {
		return U16.f(this.length);
	}
	
	public int getXid() {
		return this.xid;
	}
			
	public long getXidU() {
		return U32.f(this.xid);
	}	

    public void readFrom(ByteBuffer data) {
    	
        this.version = data.get();
		this.type = data.get();
		this.length = data.getShort();
		this.xid = data.getInt();
    }

    public String toString() {
        return "OFHeader-"+":version=" + U8.f(version) + 
		":type=" + U8.f(type) + 
		":length=" + U16.f(length) + 
		":xid=" + U32.f(xid);
    }
}