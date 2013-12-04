package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFConfigFlags {
	private short value;
	
	public static short	OFPC_FRAG_NORMAL	=	0;
	public static short	OFPC_FRAG_DROP	=	1;
	public static short	OFPC_FRAG_REASM	=	2;
	public static short	OFPC_FRAG_MASK	=	3;
	
	
	public OFConfigFlags() {
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