package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFMeterFlags {
	private short value;
	
	public static short	OFPMF_KBPS	=	0x1;
	public static short	OFPMF_PKTPS	=	0x2;
	public static short	OFPMF_BURST	=	0x4;
	public static short	OFPMF_STATS	=	0x8;
	
	
	public OFMeterFlags() {
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