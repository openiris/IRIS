package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFStatsReplyFlags {
	private short value;
	
	public static short	OFPSF_REPLY_MORE	=	0x1;
	
	
	public OFStatsReplyFlags() {
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