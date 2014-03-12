package etri.sdn.controller.module.firewall;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.util.HexString;

import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPacket;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.protocol.packet.TCP;
import etri.sdn.controller.protocol.packet.UDP;

/**
 * This class defines the firewall rule.
 * This class is used when the firewall module carries out its operation.
 * This firewall rule is converted into Map type when it is put in the storage.
 *  
 */
public class FirewallRule implements Comparable<FirewallRule>, Serializable {
	
    // BEGIN WILDCARD RELATED
    final public static int OFPFW_ALL = ((1 << 22) - 1);

    final public static int OFPFW_IN_PORT = 1 << 0; /* Switch input port. */
    final public static int OFPFW_DL_VLAN = 1 << 1; /* VLAN id. */
    final public static int OFPFW_DL_SRC = 1 << 2; /* Ethernet source address. */
    final public static int OFPFW_DL_DST = 1 << 3; /*
                                                    * Ethernet destination
                                                    * address.
                                                    */
    final public static int OFPFW_DL_TYPE = 1 << 4; /* Ethernet frame type. */
    final public static int OFPFW_NW_PROTO = 1 << 5; /* IP protocol. */
    final public static int OFPFW_TP_SRC = 1 << 6; /* TCP/UDP source port. */
    final public static int OFPFW_TP_DST = 1 << 7; /* TCP/UDP destination port. */

    /*
     * IP source address wildcard bit count. 0 is exact match, 1 ignores the
     * LSB, 2 ignores the 2 least-significant bits, ..., 32 and higher wildcard
     * the entire field. This is the *opposite* of the usual convention where
     * e.g. /24 indicates that 8 bits (not 24 bits) are wildcarded.
     */
    final public static int OFPFW_NW_SRC_SHIFT = 8;
    final public static int OFPFW_NW_SRC_BITS = 6;
    final public static int OFPFW_NW_SRC_MASK = ((1 << OFPFW_NW_SRC_BITS) - 1) << OFPFW_NW_SRC_SHIFT;
    final public static int OFPFW_NW_SRC_ALL = 32 << OFPFW_NW_SRC_SHIFT;

    /* IP destination address wildcard bit count. Same format as source. */
    final public static int OFPFW_NW_DST_SHIFT = 14;
    final public static int OFPFW_NW_DST_BITS = 6;
    final public static int OFPFW_NW_DST_MASK = ((1 << OFPFW_NW_DST_BITS) - 1) << OFPFW_NW_DST_SHIFT;
    final public static int OFPFW_NW_DST_ALL = 32 << OFPFW_NW_DST_SHIFT;

    final public static int OFPFW_DL_VLAN_PCP = 1 << 20; /* VLAN priority. */
    final public static int OFPFW_NW_TOS = 1 << 21; /*
                                                     * IP ToS (DSCP field, 6
                                                     * bits).
                                                     */
    // END WILDCARD RELATED

    public static final short OFP_VLAN_NONE = (short) 0xffff;

    /* List of Strings for marshalling and unmarshalling to human readable forms */
    final public static String STR_IN_PORT = "in_port";
    final public static String STR_DL_DST = "dl_dst";
    final public static String STR_DL_SRC = "dl_src";
    final public static String STR_DL_TYPE = "dl_type";
    final public static String STR_DL_VLAN = "dl_vlan";
    final public static String STR_DL_VLAN_PCP = "dl_vpcp";
    final public static String STR_NW_DST = "nw_dst";
    final public static String STR_NW_SRC = "nw_src";
    final public static String STR_NW_PROTO = "nw_proto";
    final public static String STR_NW_TOS = "nw_tos";
    final public static String STR_TP_DST = "tp_dst";
    final public static String STR_TP_SRC = "tp_src";
	
	private static final long serialVersionUID = 1L;

	public int ruleid;

	public long dpid; 
	public short in_port; 
	public long dl_src; 
	public long dl_dst; 
	public short dl_type; 
	public int nw_src_prefix; 
	public int nw_src_maskbits;
	public int nw_dst_prefix;
	public int nw_dst_maskbits;
	public short nw_proto;
	public short tp_src;
	public short tp_dst;

	public boolean wildcard_dpid;
	public boolean wildcard_in_port; 
	public boolean wildcard_dl_src;
	public boolean wildcard_dl_dst;
	public boolean wildcard_dl_type;
	public boolean wildcard_nw_src;
	public boolean wildcard_nw_dst;
	public boolean wildcard_nw_proto;
	public boolean wildcard_tp_src;
	public boolean wildcard_tp_dst;

	public int priority = 0;

	public FirewallAction action;

	/**
	 * This enum constant defines two firewall actions.
	 * : DENY when denies rule, ALLOW when allows rule
	 *
	 */
	public enum FirewallAction {
		DENY, ALLOW
	}

	public FirewallRule() {
		this.in_port = 0; 
		this.dl_src = 0;
		this.nw_src_prefix = 0;
		this.nw_src_maskbits = 0; 
		this.dl_dst = 0;
		this.nw_proto = 0;
		this.tp_src = 0;
		this.tp_dst = 0;
		this.dl_dst = 0;
		this.nw_dst_prefix = 0;
		this.nw_dst_maskbits = 0; 
		this.dpid = -1;
		this.wildcard_dpid = true; 
		this.wildcard_in_port = true; 
		this.wildcard_dl_src = true; 
		this.wildcard_dl_dst = true; 
		this.wildcard_dl_type = true; 
		this.wildcard_nw_src = true; 
		this.wildcard_nw_dst = true; 
		this.wildcard_nw_proto = true; 
		this.wildcard_tp_src = true; 
		this.wildcard_tp_dst = true; 
		this.priority = 0; 
		this.action = FirewallAction.ALLOW; 
		this.ruleid = 0; 
	}

	/**
	 * Generates a unique ID for the instance.
	 * 
	 * @return integer representing the unique id
	 */
	public int genID() {
		int uid = this.hashCode();
		if (uid < 0) {
			uid = Math.abs(uid);
			uid = uid * 15551;
		}
		return uid;
	}

	/**
	 * Comparison method for Collections.sort method
	 * 
	 * @param rule the rule to compare with
	 * 
	 * @return integer representing the result of comparison, 0 if equal, negative
	 *         if less than 'rule', greater than zero if greater priority rule than 'rule'
	 *         
	 */
	@Override
	public int compareTo(FirewallRule rule) {
		return this.priority - rule.priority;
	}

	/**
	 * Determines if this instance matches an existing rule instance.
	 * 
	 * @param r the instance to compare with
	 * 
	 * @return true if a match is found
	 * 
	 **/
	public boolean isSameAs(FirewallRule r) {
		if (this.action != r.action
				|| this.wildcard_dl_type != r.wildcard_dl_type
				|| (this.wildcard_dl_type == false && this.dl_type == r.dl_type)
				|| this.wildcard_tp_src != r.wildcard_tp_src
				|| (this.wildcard_tp_src == false && this.tp_src != r.tp_src)
				|| this.wildcard_tp_dst != r.wildcard_tp_dst
				|| (this.wildcard_tp_dst == false &&this.tp_dst != r.tp_dst)
				|| this.wildcard_dpid != r.wildcard_dpid
				|| (this.wildcard_dpid == false && this.dpid != r.dpid)
				|| this.wildcard_in_port != r.wildcard_in_port
				|| (this.wildcard_in_port == false && this.in_port != r.in_port)
				|| this.wildcard_nw_src != r.wildcard_nw_src
				|| (this.wildcard_nw_src == false && (this.nw_src_prefix != r.nw_src_prefix || this.nw_src_maskbits != r.nw_src_maskbits))
				|| this.wildcard_dl_src != r.wildcard_dl_src
				|| (this.wildcard_dl_src == false && this.dl_src != r.dl_src)
				|| this.wildcard_nw_proto != r.wildcard_nw_proto
				|| (this.wildcard_nw_proto == false && this.nw_proto != r.nw_proto)
				|| this.wildcard_nw_dst != r.wildcard_nw_dst
				|| (this.wildcard_nw_dst == false && (this.nw_dst_prefix != r.nw_dst_prefix || this.nw_dst_maskbits != r.nw_dst_maskbits))
				|| this.wildcard_dl_dst != r.wildcard_dl_dst                
				|| (this.wildcard_dl_dst == false && this.dl_dst != r.dl_dst)) {
			return false;
		}
		return true;
	}

	/**
	 * Matches this rule to a given flow - incoming packet.
	 * 
	 * @param switchDpid the dpid of the connected switch
	 * @param inPort the switch port where the packet originated from
	 * @param packet the Ethernet packet that arrives at the switch
	 * @param wildcards the pair of wildcards (allow and deny) given by Firewall
	 *        module that is used by the Firewall module's matchWithRule method 
	 *        to derive wildcards for the decision to be taken
	 *            
	 * @return true if the rule matches the given packetin, false otherwise
	 * 
	 */
	public boolean matchesFlow(long switchDpid, OFPort inPort, Ethernet packet,
			WildcardsPair wildcards) {
		IPacket pkt = packet.getPayload();

		// dl_type type
		IPv4 pkt_ip = null;

		// nw_proto types
		TCP pkt_tcp = null;
		UDP pkt_udp = null;

		// tp_src and tp_dst (tp port numbers)
		short pkt_tp_src = 0;
		short pkt_tp_dst = 0;

		// switchID matches?
		if (wildcard_dpid == false && dpid != switchDpid)
			return false;

		// in_port matches?
		if (wildcard_in_port == false && in_port != inPort.getShortPortNumber())
			return false;
		if (action == FirewallRule.FirewallAction.DENY) {
			wildcards.drop &= ~OFPFW_IN_PORT;
		} else {
			wildcards.allow &= ~OFPFW_IN_PORT;
		}

		// mac address (src and dst) match?
		if (wildcard_dl_src == false
				&& dl_src != packet.getSourceMAC().toLong())
			return false;
		if (action == FirewallRule.FirewallAction.DENY) {
			wildcards.drop &= ~OFPFW_DL_SRC;
		} else {
			wildcards.allow &= ~OFPFW_DL_SRC;
		}

		if (wildcard_dl_dst == false
				&& dl_dst != packet.getDestinationMAC().toLong())
			return false;
		if (action == FirewallRule.FirewallAction.DENY) {
			wildcards.drop &= ~OFPFW_DL_DST;
		} else {
			wildcards.allow &= ~OFPFW_DL_DST;
		}

		// dl_type check: ARP, IP

		// if this is not an ARP rule but the pkt is ARP,
		// return false match - no need to continue protocol specific check
		if (wildcard_dl_type == false) {
			if (dl_type == Ethernet.TYPE_ARP) {
				if (packet.getEtherType() != Ethernet.TYPE_ARP)
					return false;
				else {
					if (action == FirewallRule.FirewallAction.DENY) {
						wildcards.drop &= ~OFPFW_DL_TYPE;
					} else {
						wildcards.allow &= ~OFPFW_DL_TYPE;
					}
				}
			} else if (dl_type == Ethernet.TYPE_IPv4) {
				if (packet.getEtherType() != Ethernet.TYPE_IPv4)
					return false;
				else {
					if (action == FirewallRule.FirewallAction.DENY) {
						wildcards.drop &= ~OFPFW_NW_PROTO;
					} else {
						wildcards.allow &= ~OFPFW_NW_PROTO;
					}
					// IP packets, proceed with ip address check
					pkt_ip = (IPv4) pkt;

					// IP addresses (src and dst) match?
					if (wildcard_nw_src == false
							&& this.matchIPAddress(nw_src_prefix,
									nw_src_maskbits, pkt_ip.getSourceAddress()) == false)
						return false;
					if (action == FirewallRule.FirewallAction.DENY) {
						wildcards.drop &= ~OFPFW_NW_SRC_ALL;
						wildcards.drop |= (nw_src_maskbits << OFPFW_NW_SRC_SHIFT);
					} else {
						wildcards.allow &= ~OFPFW_NW_SRC_ALL;
						wildcards.allow |= (nw_src_maskbits << OFPFW_NW_SRC_SHIFT);
					}

					if (wildcard_nw_dst == false
							&& this.matchIPAddress(nw_dst_prefix,
									nw_dst_maskbits,
									pkt_ip.getDestinationAddress()) == false)
						return false;
					if (action == FirewallRule.FirewallAction.DENY) {
						wildcards.drop &= ~OFPFW_NW_DST_ALL;
						wildcards.drop |= (nw_dst_maskbits << OFPFW_NW_DST_SHIFT);
					} else {
						wildcards.allow &= ~OFPFW_NW_DST_ALL;
						wildcards.allow |= (nw_dst_maskbits << OFPFW_NW_DST_SHIFT);
					}

					// nw_proto check
					if (wildcard_nw_proto == false) {
						if (nw_proto == IPv4.PROTOCOL_TCP) {
							if (pkt_ip.getProtocol() != IPv4.PROTOCOL_TCP)
								return false;
							else {
								pkt_tcp = (TCP) pkt_ip.getPayload();
								pkt_tp_src = pkt_tcp.getSourcePort();
								pkt_tp_dst = pkt_tcp.getDestinationPort();
							}
						} else if (nw_proto == IPv4.PROTOCOL_UDP) {
							if (pkt_ip.getProtocol() != IPv4.PROTOCOL_UDP)
								return false;
							else {
								pkt_udp = (UDP) pkt_ip.getPayload();
								pkt_tp_src = pkt_udp.getSourcePort();
								pkt_tp_dst = pkt_udp.getDestinationPort();
							}
						} else if (nw_proto == IPv4.PROTOCOL_ICMP) {
							if (pkt_ip.getProtocol() != IPv4.PROTOCOL_ICMP)
								return false;
							else {
								// nothing more needed for ICMP
							}
						}
						if (action == FirewallRule.FirewallAction.DENY) {
							wildcards.drop &= ~OFPFW_NW_PROTO;
						} else {
							wildcards.allow &= ~OFPFW_NW_PROTO;
						}

						// TCP/UDP source and destination ports match?
						if (pkt_tcp != null || pkt_udp != null) {
							// does the source port match?
							if (tp_src != 0 && tp_src != pkt_tp_src)
								return false;
							if (action == FirewallRule.FirewallAction.DENY) {
								wildcards.drop &= ~OFPFW_TP_SRC;
							} else {
								wildcards.allow &= ~OFPFW_TP_SRC;
							}

							// does the destination port match?
							if (tp_dst != 0 && tp_dst != pkt_tp_dst)
								return false;
							if (action == FirewallRule.FirewallAction.DENY) {
								wildcards.drop &= ~OFPFW_TP_DST;
							} else {
								wildcards.allow &= ~OFPFW_TP_DST;
							}
						}
					}

				}
			} else {
				// non-IP packet - not supported - report no match
				return false;
			}
		}
		if (action == FirewallRule.FirewallAction.DENY) {
			wildcards.drop &= ~OFPFW_DL_TYPE;
		} else {
			wildcards.allow &= ~OFPFW_DL_TYPE;
		}

		// all applicable checks passed
		return true;
	}

	/**
	 * Determines if rule's CIDR address matches IP address of the packet.
	 * 
	 * @param rulePrefix the prefix part of the CIDR address
	 * @param ruleBits the size of mask of the CIDR address
	 * @param packetAddress the IP address of the incoming packet to match with
	 * 
	 * @return true if CIDR address matches the packet's IP address, false otherwise
	 * 
	 */
	protected boolean matchIPAddress(int rulePrefix, int ruleBits,
			int packetAddress) {
		boolean matched = true;

		int rule_iprng = 32 - ruleBits;
		int rule_ipint = rulePrefix;
		int pkt_ipint = packetAddress;
		// if there's a subnet range (bits to be wildcarded > 0)
		if (rule_iprng > 0) {
			// right shift bits to remove rule_iprng of LSB that are to be wildcarded
			rule_ipint = rule_ipint >> rule_iprng;
						pkt_ipint = pkt_ipint >> rule_iprng;
						// now left shift to return to normal range, except that the
						// rule_iprng number of LSB are now zeroed
						rule_ipint = rule_ipint << rule_iprng;
						pkt_ipint = pkt_ipint << rule_iprng;
		}
		// check if we have a match
		if (rule_ipint != pkt_ipint)
			matched = false;

		return matched;
	}

	@Override
	public int hashCode() {
		final int prime = 2521;
		int result = super.hashCode();
		
		result = prime * result + (int) dpid;
		result = prime * result + in_port;
		result = prime * result + (int) dl_src;
		result = prime * result + (int) dl_dst;
		result = prime * result + dl_type;
		result = prime * result + nw_src_prefix;
		result = prime * result + nw_src_maskbits;
		result = prime * result + nw_dst_prefix;
		result = prime * result + nw_dst_maskbits;
		result = prime * result + nw_proto;
		result = prime * result + tp_src;
		result = prime * result + tp_dst;
		result = prime * result + action.ordinal();
		result = prime * result + priority;
		result = prime * result + (new Boolean(wildcard_dpid)).hashCode();
		result = prime * result + (new Boolean(wildcard_in_port)).hashCode();
		result = prime * result + (new Boolean(wildcard_dl_src)).hashCode();
		result = prime * result + (new Boolean(wildcard_dl_dst)).hashCode();
		result = prime * result + (new Boolean(wildcard_dl_type)).hashCode();
		result = prime * result + (new Boolean(wildcard_nw_src)).hashCode();
		result = prime * result + (new Boolean(wildcard_nw_dst)).hashCode();
		result = prime * result + (new Boolean(wildcard_nw_proto)).hashCode();
		result = prime * result + (new Boolean(wildcard_tp_src)).hashCode();
		result = prime * result + (new Boolean(wildcard_tp_dst)).hashCode();
		
		return result;
	}

	/**
	 * Turns a JSON formatted Firewall Rule string into a {@link FirewallRule} instance
	 * 
	 * @param fmJson The JSON formatted static firewall rule
	 * @return The {@link FirewallRule} instance
	 * 
	 * @throws IOException If there was an error parsing the JSON
	 * 
	 */
	public static FirewallRule jsonToFirewallRule(String fmJson) throws IOException {
		FirewallRule rule = new FirewallRule();
		MappingJsonFactory f = new MappingJsonFactory();
		JsonParser jp;

		try {
			jp = f.createJsonParser(fmJson);
		} catch (JsonParseException e) {
			throw new IOException(e);
		}

		jp.nextToken();
		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw new IOException("Expected START_OBJECT");
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			if (jp.getCurrentToken() != JsonToken.FIELD_NAME) {
				throw new IOException("Expected FIELD_NAME");
			}

			String n = jp.getCurrentName();
			jp.nextToken();
			if (jp.getText().equals("")) 
				continue;

			String tmp;

			// This is currently only applicable for remove().  In store(), ruleid takes a random number
			if (n == "ruleid") {
				rule.ruleid = Integer.parseInt((String)jp.getText());
			}

			// This assumes user having dpid info for involved switches
			else if (n == "switchid") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("-1") == false) {
					// user inputs hex format dpid 
					rule.dpid = HexString.toLong(tmp);                    
					rule.wildcard_dpid = false;
				}
			} 

			else if (n == "src-inport") {
				rule.in_port = Short.parseShort(jp.getText());
				rule.wildcard_in_port = false;
			} 

			else if (n == "src-mac") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("ANY") == false) {
					rule.wildcard_dl_src = false;
					rule.dl_src = Ethernet.toLong(Ethernet.toMACAddress(tmp));
				}
			} 

			else if (n == "dst-mac") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("ANY") == false) {
					rule.wildcard_dl_dst = false;
					rule.dl_dst = Ethernet.toLong(Ethernet.toMACAddress(tmp));
				}
			} 

			else if (n == "dl-type") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("ARP")) {
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_ARP;
				}
			} 

			else if (n == "src-ip") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("ANY") == false) {
					rule.wildcard_nw_src = false;
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_IPv4;
					int[] cidr = IPCIDRToPrefixBits(tmp);
					rule.nw_src_prefix = cidr[0];
					rule.nw_src_maskbits = cidr[1];
				}
			} 

			else if (n == "dst-ip") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("ANY") == false) {
					rule.wildcard_nw_dst = false;
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_IPv4;
					int[] cidr = IPCIDRToPrefixBits(tmp);
					rule.nw_dst_prefix = cidr[0];
					rule.nw_dst_maskbits = cidr[1];
				}
			} 

			else if (n == "nw-proto") {
				tmp = jp.getText();
				if (tmp.equalsIgnoreCase("TCP")) {
					rule.wildcard_nw_proto = false;
					rule.nw_proto = IPv4.PROTOCOL_TCP;
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_IPv4;
				} else if (tmp.equalsIgnoreCase("UDP")) {
					rule.wildcard_nw_proto = false;
					rule.nw_proto = IPv4.PROTOCOL_UDP;
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_IPv4;
				} else if (tmp.equalsIgnoreCase("ICMP")) {
					rule.wildcard_nw_proto = false;
					rule.nw_proto = IPv4.PROTOCOL_ICMP;
					rule.wildcard_dl_type = false;
					rule.dl_type = Ethernet.TYPE_IPv4;
				} 
			} 

			else if (n == "tp-src") {
				rule.wildcard_tp_src = false;
				rule.tp_src = Short.parseShort(jp.getText());
			} 

			else if (n == "tp-dst") {
				rule.wildcard_tp_dst = false;
				rule.tp_dst = Short.parseShort(jp.getText());
			} 

			else if (n == "priority") {
				rule.priority = Integer.parseInt(jp.getText());
			} 

			else if (n == "action") {
				if (jp.getText().equalsIgnoreCase("allow") == true) {
					rule.action = FirewallRule.FirewallAction.ALLOW;
				} else if (jp.getText().equalsIgnoreCase("deny") == true) {
					rule.action = FirewallRule.FirewallAction.DENY;
				}
			}
		}

		return rule;
	}

	/**
	 * Divides CIDR address into IP address and the length of mask.  
	 * 
	 * @param cidr CIDR address of string
	 * 
	 * @return an array integer consisting of IP address and mask
	 */
	public static int[] IPCIDRToPrefixBits(String cidr) {
		int ret[] = new int[2];

		// as IP can also be a prefix rather than an absolute address
		// split it over "/" to get the bit range
		String[] parts = cidr.split("/");
		String cidr_prefix = parts[0].trim();
		int cidr_bits = 0;
		if (parts.length == 2) {
			try {
				cidr_bits = Integer.parseInt(parts[1].trim());
			} catch (Exception exp) {
				cidr_bits = 32;
			}
		}
		ret[0] = IPv4.toIPv4Address(cidr_prefix);
		ret[1] = cidr_bits;

		return ret;
	}

}
