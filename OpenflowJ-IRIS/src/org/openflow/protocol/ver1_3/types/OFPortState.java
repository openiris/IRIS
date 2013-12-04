package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFPortState {
	private int value;
	
	public static int	OFPPS_LINK_DOWN	=	0x1;
	public static int	OFPPS_BLOCKED	=	0x2;
	public static int	OFPPS_LIVE	=	0x4;
	
	
	public OFPortState() {
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