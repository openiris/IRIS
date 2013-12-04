package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

public class OFTableConfig {
	private int value;
	
	public static int	OFPTC_DEPRECATED_MASK	=	0x3;
	
	
	public OFTableConfig() {
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