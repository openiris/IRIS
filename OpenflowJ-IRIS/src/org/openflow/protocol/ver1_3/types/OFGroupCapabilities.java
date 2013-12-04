package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFGroupCapabilities {
	private int value;
	
	public static int	OFPGFC_SELECT_WEIGHT	=	0x1;
	public static int	OFPGFC_SELECT_LIVENESS	=	0x2;
	public static int	OFPGFC_CHAINING	=	0x4;
	public static int	OFPGFC_CHAINING_CHECKS	=	0x8;
	
	
	public OFGroupCapabilities() {
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