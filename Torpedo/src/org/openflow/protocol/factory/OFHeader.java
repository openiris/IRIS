package org.openflow.protocol.factory;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.projectfloodlight.openflow.types.U16;
import org.projectfloodlight.openflow.types.U32;
import org.projectfloodlight.openflow.types.U8;

public class OFHeader {
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

	public void readFrom(ChannelBuffer data) {

		this.version = data.readByte();
		this.type = data.readByte();
		this.length = data.readShort();
		this.xid = data.readInt();
	}

	public String toString() {
		return "OFHeader-"+":version=" + U8.f(version) + 
				":type=" + U8.f(type) + 
				":length=" + U16.f(length) + 
				":xid=" + U32.f(xid);
	}
}