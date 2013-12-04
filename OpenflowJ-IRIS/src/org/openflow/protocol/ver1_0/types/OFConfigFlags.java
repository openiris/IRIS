package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFConfigFlags {
	private int value;
	
	public static int	OFPC_FRAG_NORMAL	=	0x0;
	public static int	OFPC_FRAG_DROP	=	0x1;
	public static int	OFPC_FRAG_REASM	=	0x2;
	public static int	OFPC_FRAG_MASK	=	0x3;
	
	
	public OFConfigFlags() {
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