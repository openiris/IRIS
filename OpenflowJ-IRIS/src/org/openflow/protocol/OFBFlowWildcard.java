package org.openflow.protocol;

public abstract class OFBFlowWildcard {
	public static int	IN_PORT	=	0x1;
	public static int	DL_VLAN	=	0x2;
	public static int	DL_SRC	=	0x4;
	public static int	DL_DST	=	0x8;
	public static int	DL_TYPE	=	0x10;
	public static int	DL_VLAN_PCP	=	0x100000;
	public static int	NW_DST_BITS	=	0x6;
	public static int	NW_SRC_BITS	=	0x6;
	public static int	NW_SRC_SHIFT	=	0x8;
	public static int	NW_DST_SHIFT	=	0xe;
	public static int	NW_PROTO	=	0x20;
	public static int	NW_SRC_ALL	=	0x2000;
	public static int	NW_SRC_MASK	=	0x3f00;
	public static int	NW_DST_ALL	=	0x80000;
	public static int	NW_DST_MASK	=	0xfc000;
	public static int	NW_TOS	=	0x200000;
	public static int	TP_SRC	=	0x40;
	public static int	TP_DST	=	0x80;
	public static int	ALL	=	0x3fffff;
}
