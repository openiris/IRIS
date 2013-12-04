package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFPortConfig {
	private int value;
	
	public static int	OFPPC_PORT_DOWN	=	0x1;
	public static int	OFPPC_NO_RECV	=	0x4;
	public static int	OFPPC_NO_FWD	=	0x20;
	public static int	OFPPC_NO_PACKET_IN	=	0x40;
	
	
	public OFPortConfig() {
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