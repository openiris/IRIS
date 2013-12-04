package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFFlowModFlags {
	private short value;
	
	public static short	OFPFF_SEND_FLOW_REM	=	0x1;
	public static short	OFPFF_CHECK_OVERLAP	=	0x2;
	public static short	OFPFF_RESET_COUNTS	=	0x4;
	public static short	OFPFF_NO_PKT_COUNTS	=	0x8;
	public static short	OFPFF_NO_BYT_COUNTS	=	0x10;
	
	
	public OFFlowModFlags() {
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