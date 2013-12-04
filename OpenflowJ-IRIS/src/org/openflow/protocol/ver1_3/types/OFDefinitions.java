package org.openflow.protocol.ver1_3.types;

public interface OFDefinitions {
	int	OFP_MAX_TABLE_NAME_LEN	=	32;
	int	OFP_MAX_PORT_NAME_LEN	=	16;
	int	OFP_TCP_PORT	=	6633;
	int	OFP_SSL_PORT	=	6633;
	int	OFP_ETH_ALEN	=	6;
	int	OFP_DEFAULT_MISS_SEND_LEN	=	128;
	int	OFP_VLAN_NONE	=	0;
	int	OFP_FLOW_PERMANENT	=	0;
	int	OFP_DEFAULT_PRIORITY	=	0x8000;
	int	OFP_NO_BUFFER	=	0xffffffff;
	int	DESCRIPTION_STRING_LEN	=	256;
	int	SERIAL_NUMBER_LEN	=	32;
	int	OFPQ_ALL	=	0xffffffff;
	int	OFPQ_MAX_RATE_UNCFG	=	0xffff;
	int	OFPQ_MIN_RATE_UNCFG	=	0xffff;
	
}