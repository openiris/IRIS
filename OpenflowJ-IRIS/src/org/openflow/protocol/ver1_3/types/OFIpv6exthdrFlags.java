package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFIpv6exthdrFlags {
	private short value;
	
	public static short	OFPIEH_NONEXT	=	0x1;
	public static short	OFPIEH_ESP	=	0x2;
	public static short	OFPIEH_AUTH	=	0x4;
	public static short	OFPIEH_DEST	=	0x8;
	public static short	OFPIEH_FRAG	=	0x10;
	public static short	OFPIEH_ROUTER	=	0x20;
	public static short	OFPIEH_HOP	=	0x40;
	public static short	OFPIEH_UNREP	=	0x80;
	public static short	OFPIEH_UNSEQ	=	0x100;
	
	
	public OFIpv6exthdrFlags() {
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