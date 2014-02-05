package org.openflow.protocol;

public abstract class OFBPortState {
	public static int	STP_LISTEN	=	0x00;	// 1.0
	public static int	LINK_DOWN	=	0x01;	// 1.3
	public static int	BLOCKED		=	0x02;	// 1.3
	public static int	LIVE		=	0x04;	// 1.3
	public static int	STP_LEARN	=	0x100;	// 1.0
	public static int	STP_FORWARD	=	0x200;	// 1.0
	public static int	STP_BLOCK	=	0x300;	// 1.0
	public static int	STP_MASK	=	0x300;	// 1.0
}
