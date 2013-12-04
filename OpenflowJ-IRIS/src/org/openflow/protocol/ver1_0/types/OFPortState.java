package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFPortState {
	private int value;
	
	public static int	OFPPS_STP_LISTEN	=	0;
	public static int	OFPPS_LINK_DOWN	=	1;
	public static int	OFPPS_STP_LEARN	=	0x100;
	public static int	OFPPS_STP_FORWARD	=	0x200;
	public static int	OFPPS_STP_BLOCK	=	0x300;
	public static int	OFPPS_STP_MASK	=	0x300;
	
	
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