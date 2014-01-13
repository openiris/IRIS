
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFOxmMatchFields {
  
	OFB_IN_PORT(0, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IN_PORT),
	OFB_IN_PHY_PORT(1, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IN_PHY_PORT),
	OFB_METADATA(2, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_METADATA),
	OFB_ETH_DST(3, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ETH_DST),
	OFB_ETH_SRC(4, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ETH_SRC),
	OFB_ETH_TYPE(5, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ETH_TYPE),
	OFB_VLAN_VID(6, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_VID),
	OFB_VLAN_PCP(7, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_PCP),
	OFB_IP_DSCP(8, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IP_DSCP),
	OFB_IP_ECN(9, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IP_ECN),
	OFB_IP_PROTO(10, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IP_PROTO),
	OFB_IPV4_SRC(11, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV4_SRC),
	OFB_IPV4_DST(12, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV4_DST),
	OFB_TCP_SRC(13, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_TCP_SRC),
	OFB_TCP_DST(14, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_TCP_DST),
	OFB_UDP_SRC(15, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_UDP_SRC),
	OFB_UDP_DST(16, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_UDP_DST),
	OFB_SCTP_SRC(17, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_SCTP_SRC),
	OFB_SCTP_DST(18, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_SCTP_DST),
	OFB_ICMPV4_TYPE(19, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV4_TYPE),
	OFB_ICMPV4_CODE(20, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV4_CODE),
	OFB_ARP_OP(21, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ARP_OP),
	OFB_ARP_SPA(22, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ARP_SPA),
	OFB_ARP_TPA(23, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ARP_TPA),
	OFB_ARP_SHA(24, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ARP_SHA),
	OFB_ARP_THA(25, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ARP_THA),
	OFB_IPV6_SRC(26, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_SRC),
	OFB_IPV6_DST(27, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_DST),
	OFB_IPV6_FLABEL(28, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_FLABEL),
	OFB_ICMPV6_TYPE(29, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV6_TYPE),
	OFB_ICMPV6_CODE(30, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV6_CODE),
	OFB_IPV6_ND_TARGET(31, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_ND_TARGET),
	OFB_IPV6_ND_SLL(32, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_ND_SLL),
	OFB_IPV6_ND_TLL(33, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_ND_TLL),
	OFB_MPLS_LABEL(34, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_MPLS_LABEL),
	OFB_MPLS_TC(35, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_MPLS_TC),
	OFP_MPLS_BOS(36, org.openflow.protocol.interfaces.OFOxmMatchFields.OFP_MPLS_BOS),
	OFB_PBB_ISID(37, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_PBB_ISID),
	OFB_TUNNEL_ID(38, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_TUNNEL_ID),
	OFB_IPV6_EXTHDR(39, org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV6_EXTHDR);
  
	private static Map<Byte, OFOxmMatchFields> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFOxmMatchFields> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFOxmMatchFields, OFOxmMatchFields> __compatMappingReverse;
    
	private static OFOxmMatchFields __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFOxmMatchFields cvalue, OFOxmMatchFields obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFOxmMatchFields>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFOxmMatchFields>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFOxmMatchFields, OFOxmMatchFields>();
		if (__array == null) __array = new OFOxmMatchFields[40];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFOxmMatchFields first() {
		return __array[0];
	}
  
	public static OFOxmMatchFields last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFOxmMatchFields(int value, org.openflow.protocol.interfaces.OFOxmMatchFields cvalue) {
		this.value = (byte)value;
		OFOxmMatchFields.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFOxmMatchFields valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFOxmMatchFields to(OFOxmMatchFields i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFOxmMatchFields from(org.openflow.protocol.interfaces.OFOxmMatchFields c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}