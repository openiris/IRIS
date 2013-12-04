package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFCapabilities {
	private int value;
	
	public static int	OFPC_FLOW_STATS	=	0x1;
	public static int	OFPC_TABLE_STATS	=	0x2;
	public static int	OFPC_PORT_STATS	=	0x4;
	public static int	OFPC_STP	=	0x8;
	public static int	OFPC_RESERVED	=	0x10;
	public static int	OFPC_IP_REASM	=	0x20;
	public static int	OFPC_QUEUE_STATS	=	0x40;
	public static int	OFPC_ARP_MATCH_IP	=	0x80;
	
	
	public OFCapabilities() {
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