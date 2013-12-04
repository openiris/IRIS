package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFPortFeatures {
	private int value;
	
	public static int	OFPPF_10MB_HD	=	0x1;
	public static int	OFPPF_10MB_FD	=	0x2;
	public static int	OFPPF_100MB_HD	=	0x4;
	public static int	OFPPF_100MB_FD	=	0x8;
	public static int	OFPPF_1GB_HD	=	0x10;
	public static int	OFPPF_1GB_FD	=	0x20;
	public static int	OFPPF_10GB_FD	=	0x40;
	public static int	OFPPF_COPPER	=	0x80;
	public static int	OFPPF_FIBER	=	0x100;
	public static int	OFPPF_AUTONEG	=	0x200;
	public static int	OFPPF_PAUSE	=	0x400;
	public static int	OFPPF_PAUSE_ASYM	=	0x800;
	
	
	public OFPortFeatures() {
		// does nothing
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}