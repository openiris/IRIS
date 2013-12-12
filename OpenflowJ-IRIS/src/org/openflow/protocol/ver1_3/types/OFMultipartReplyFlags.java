package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFMultipartReplyFlags {
	private short value;
	
	public static short	OFPMPF_REPLY_NONE	=	0x0;
	public static short	OFPMPF_REPLY_MORE	=	0x1;
	
	
	public OFMultipartReplyFlags() {
		// does nothing
	}
	
	public void setValue(short value) {
		this.value = value;
	}
	
	public short getValue() {
		return this.value;
	}
	
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}