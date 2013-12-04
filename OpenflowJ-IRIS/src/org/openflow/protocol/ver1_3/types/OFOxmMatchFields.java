
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFOxmMatchFields {
  
  OFPXMT_OFB_IN_PORT	(0),
	OFPXMT_OFB_IN_PHY_PORT	(1),
	OFPXMT_OFB_METADATA	(2),
	OFPXMT_OFB_ETH_DST	(3),
	OFPXMT_OFB_ETH_SRC	(4),
	OFPXMT_OFB_ETH_TYPE	(5),
	OFPXMT_OFB_VLAN_VID	(6),
	OFPXMT_OFB_VLAN_PCP	(7),
	OFPXMT_OFB_IP_DSCP	(8),
	OFPXMT_OFB_IP_ECN	(9),
	OFPXMT_OFB_IP_PROTO	(10),
	OFPXMT_OFB_IPV4_SRC	(11),
	OFPXMT_OFB_IPV4_DST	(12),
	OFPXMT_OFB_TCP_SRC	(13),
	OFPXMT_OFB_TCP_DST	(14),
	OFPXMT_OFB_UDP_SRC	(15),
	OFPXMT_OFB_UDP_DST	(16),
	OFPXMT_OFB_SCTP_SRC	(17),
	OFPXMT_OFB_SCTP_DST	(18),
	OFPXMT_OFB_ICMPV4_TYPE	(19),
	OFPXMT_OFB_ICMPV4_CODE	(20),
	OFPXMT_OFB_ARP_OP	(21),
	OFPXMT_OFB_ARP_SPA	(22),
	OFPXMT_OFB_ARP_TPA	(23),
	OFPXMT_OFB_ARP_SHA	(24),
	OFPXMT_OFB_ARP_THA	(25),
	OFPXMT_OFB_IPV6_SRC	(26),
	OFPXMT_OFB_IPV6_DST	(27),
	OFPXMT_OFB_IPV6_FLABEL	(28),
	OFPXMT_OFB_ICMPV6_TYPE	(29),
	OFPXMT_OFB_ICMPV6_CODE	(30),
	OFPXMT_OFB_IPV6_ND_TARGET	(31),
	OFPXMT_OFB_IPV6_ND_SLL	(32),
	OFPXMT_OFB_IPV6_ND_TLL	(33),
	OFPXMT_OFB_MPLS_LABEL	(34),
	OFPXMT_OFB_MPLS_TC	(35),
	OFPXMT_OFP_MPLS_BOS	(36),
	OFPXMT_OFB_PBB_ISID	(37),
	OFPXMT_OFB_TUNNEL_ID	(38),
	OFPXMT_OFB_IPV6_EXTHDR	(39);
  
  private static Map<Byte, OFOxmMatchFields> __index;
  private static OFOxmMatchFields __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFOxmMatchFields obj) {
  	if (__index == null) __index = new HashMap<Byte, OFOxmMatchFields>();
  	if (__array == null) __array = new OFOxmMatchFields[40];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFOxmMatchFields first() {
  	return __array[0];
  }
  
  public static OFOxmMatchFields last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFOxmMatchFields(int value) {
    this.value = (byte)value;
    OFOxmMatchFields.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFOxmMatchFields valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}