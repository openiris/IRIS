package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

public class OFFlowWildcards {
	private int value;
	
	public static int	OFPFW_IN_PORT	=	0x1;
	public static int	OFPFW_DL_VLAN	=	0x2;
	public static int	OFPFW_DL_SRC	=	0x4;
	public static int	OFPFW_NW_DST_BITS	=	0x6;
	public static int	OFPFW_NW_SRC_BITS	=	0x6;
	public static int	OFPFW_NW_SRC_SHIFT	=	0x8;
	public static int	OFPFW_DL_DST	=	0x8;
	public static int	OFPFW_NW_DST_SHIFT	=	0xe;
	public static int	OFPFW_DL_TYPE	=	0x10;
	public static int	OFPFW_NW_PROTO	=	0x20;
	public static int	OFPFW_TP_SRC	=	0x40;
	public static int	OFPFW_TP_DST	=	0x80;
	public static int	OFPFW_NW_SRC_ALL	=	0x2000;
	public static int	OFPFW_NW_SRC_MASK	=	0x3f00;
	public static int	OFPFW_NW_DST_ALL	=	0x80000;
	public static int	OFPFW_NW_DST_MASK	=	0xfc000;
	public static int	OFPFW_DL_VLAN_PCP	=	0x100000;
	public static int	OFPFW_NW_TOS	=	0x200000;
	public static int	OFPFW_ALL	=	0x3fffff;
	
	
	public OFFlowWildcards() {
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