package org.openflow.protocol.ver1_3.types;

public interface OFDefinitions {
	int	MAX_TABLE_NAME_LEN	=	32;
	int	MAX_PORT_NAME_LEN	=	16;
	int	TCP_PORT	=	6633;
	int	SSL_PORT	=	6633;
	int	ETH_ALEN	=	6;
	int	DEFAULT_MISS_SEND_LEN	=	128;
	int	VLAN_NONE	=	0;
	int	FLOW_PERMANENT	=	0;
	int	DEFAULT_PRIORITY	=	0x8000;
	int	NO_BUFFER	=	0xffffffff;
	int	STRING_LEN	=	256;
	int	NUMBER_LEN	=	32;
	int	ALL	=	0xffffffff;
	int	MAX_RATE_UNCFG	=	0xffff;
	int	MIN_RATE_UNCFG	=	0xffff;
	
}