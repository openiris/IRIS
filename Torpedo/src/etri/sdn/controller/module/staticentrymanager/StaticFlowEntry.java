package etri.sdn.controller.module.staticentrymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFFlowModCommand;
import org.projectfloodlight.openflow.protocol.OFFlowModFlags;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionCopyTtlIn;
import org.projectfloodlight.openflow.protocol.action.OFActionCopyTtlOut;
import org.projectfloodlight.openflow.protocol.action.OFActionDecMplsTtl;
import org.projectfloodlight.openflow.protocol.action.OFActionDecNwTtl;
import org.projectfloodlight.openflow.protocol.action.OFActionEnqueue;
import org.projectfloodlight.openflow.protocol.action.OFActionGroup;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.action.OFActionPopMpls;
import org.projectfloodlight.openflow.protocol.action.OFActionPopPbb;
import org.projectfloodlight.openflow.protocol.action.OFActionPopVlan;
import org.projectfloodlight.openflow.protocol.action.OFActionPushMpls;
import org.projectfloodlight.openflow.protocol.action.OFActionPushPbb;
import org.projectfloodlight.openflow.protocol.action.OFActionPushVlan;
import org.projectfloodlight.openflow.protocol.action.OFActionSetDlDst;
import org.projectfloodlight.openflow.protocol.action.OFActionSetDlSrc;
import org.projectfloodlight.openflow.protocol.action.OFActionSetField;
import org.projectfloodlight.openflow.protocol.action.OFActionSetMplsTtl;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwDst;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwSrc;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwTos;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwTtl;
import org.projectfloodlight.openflow.protocol.action.OFActionSetQueue;
import org.projectfloodlight.openflow.protocol.action.OFActionSetTpDst;
import org.projectfloodlight.openflow.protocol.action.OFActionSetTpSrc;
import org.projectfloodlight.openflow.protocol.action.OFActionSetVlanPcp;
import org.projectfloodlight.openflow.protocol.action.OFActionSetVlanVid;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionApplyActions;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionClearActions;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionGotoTable;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionWriteActions;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.protocol.oxm.OFOxm;
import org.projectfloodlight.openflow.types.ArpOpcode;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.ICMPv4Code;
import org.projectfloodlight.openflow.types.ICMPv4Type;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpDscp;
import org.projectfloodlight.openflow.types.IpEcn;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFGroup;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.OFVlanVidMatch;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.TransportPort;
import org.projectfloodlight.openflow.types.U32;
import org.projectfloodlight.openflow.types.U64;
import org.projectfloodlight.openflow.types.U8;
import org.projectfloodlight.openflow.types.VlanPcp;
import org.projectfloodlight.openflow.types.VlanVid;
import org.slf4j.Logger;

import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.IPv4;

/**
 * This class implements the functions related to static flow entry itself.
 * It check the user-input fields and prerequisite and builds OFFlowMod
 * including Match, OFInstruction and OFAction. Ant this class has the important 
 * functions to convert JSON format text into Map object automatically. 
 * Therefore users have to follow JSON format when they make static entry
 * using REST API.
 * 
 * @author jshin
 *
 */
public class StaticFlowEntry {
	
	private static final Logger logger = OFMStaticFlowEntryManager.logger;

	private static final long STATIC_FLOW_ENTRY_APP_ID = 10;

	private static final int APP_ID_BITS = 12;
	private static final int APP_ID_SHIFT = (64 - APP_ID_BITS);
	private static final long STATIC_FLOW_ENTRY_COOKIE = (long) (STATIC_FLOW_ENTRY_APP_ID & ((1 << APP_ID_BITS) - 1)) << APP_ID_SHIFT;

	private static final short IDLE_TIMEOUT_DEFAULT = 0;
	private static final short HARD_TIMEOUT_DEFAULT = 0;
	private static final short PRIORITY_DEFAULT = 100;

	/**
	 * This List includes all fields that supported by this module.
	 * If the unsupported field is input, the request is rejected
	 * by checkFieldName method. 
	 */
	@SuppressWarnings("serial")
	private static List<String> staticFlowEntryFields = new ArrayList<String>() {
		{
			add("name");
			add("switch");
			add("active");
			add("table_id");
			add("idle_timeout");
			add("hard_timeout");
			add("priority");
			add("actions");
			add("instructions");
			/**
			 * match
			 */
			//add("wildcards");			//unsupported, OF1.0 only
			add("in_port");
			//add("in_phy_port");		//unsupported
			//add("metadata");			//unsupported
			add("eth_dst");				//dl_src of OF1.0
			add("eth_src");				//dl_dst of OF1.0
			add("vlan_vid");			//dl_vlan of OF1.0
			add("vlan_pcp");			//dl_vlan_pcp of OF1.0
			add("eth_type");			//dl_type of OF1.0
			//add("nw_tos");			//unsupported, OF1.0 only
			add("ip_proto");			//nw_proto of OF1.0
			add("ipv4_src");			//nw_src of OF1.0
			add("ipv4_dst");			//nw_dst of OF1.0
			//add("tp_src");			//unsupported, OF1.0 only
			//add("tp_dst");			//unsupported, OF1.0 only
			add("ip_dscp");
			add("ip_ecn");
			add("tcp_src");
			add("tcp_dst");
			add("udp_src");
			add("udp_dst");
			add("sctp_src");
			add("sctp_dst");
			add("icmpv4_type");
			add("icmpv4_code");
			add("arp_op");
			add("arp_spa");
			add("arp_tpa");
			add("arp_sha");
			add("arp_tha");
			//add("ipv6_src");			//unsupported
			//add("ipv6_dst");			//unsupported
			//add("ipv6_nd_target");	//unsupported
			//add("ipv6_nd_sll");		//unsupported
			//add("ipv6_nd_tll");		//unsupported
			add("mpls_label");
			add("mpls_tc");
			//add("mpls_vos");			//unsupported
			//add("pbb_isid");			//unsupported
			//add("tunnel_id");			//unsupported
			//add("ipv6_exthdr");		//unsupported
		}
	};

	/**
	 * Builds the default values for a new FlowMod.
	 * 
	 * @param fm OFFlowMod object
	 * @param command ADD or DELETE_STRICT
	 * 
	 * @return OFFlowMod object that has default values
	 */
	private static OFFlowMod.Builder setDefaultFlowModFields(OFFlowMod.Builder fm, OFFlowModCommand command) {
		try {
			fm
			.setCookie(U64.of(STATIC_FLOW_ENTRY_COOKIE))
			.setIdleTimeout(IDLE_TIMEOUT_DEFAULT)
			.setHardTimeout(HARD_TIMEOUT_DEFAULT)
			.setPriority(PRIORITY_DEFAULT)
			.setBufferId(OFBufferId.NO_BUFFER)
			.setOutPort(OFPort.ANY /* NONE for OF1.0 */ )
			.setFlags((command==OFFlowModCommand.DELETE)?
					EnumSet.of(OFFlowModFlags.SEND_FLOW_REM):
						EnumSet.noneOf(OFFlowModFlags.class))
						.setTableId(TableId.ZERO);
		}
		catch (UnsupportedOperationException e) {
			//does nothing
		}

		return fm;
	}

	/**
	 * This makes OXM field.
	 * This method is called by makeMatch or makeAction(only when set_field is set).
	 * 
	 * @param sw IOFSwitch
	 * @param entry the entry to be converted to OFOxm
	 * 
	 * @return the OFOxm object or null when entry has no field
	 * 
	 * @throws StaticFlowEntryException
	 */
	private static OFOxm<?> makeOxm(IOFSwitch sw, String actionCommand, Map<String, Object> entry) throws StaticFlowEntryException {

		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		for (String fieldstr : entry.keySet()) {
			if (fieldstr.toLowerCase().equals("in_port")) {
				// Set-Field actions for OXM types OFPXMT_OFB_IN_PORT, OXM_OF_IN_PHY_PORT
				// and OFPXMT_OFB_METADATA are not supported.
				if (actionCommand.equals("set_field")) {
					throw new StaticFlowEntryException("Set-Field actions for OFPXMT_OFB_IN_PORT is not supported.");
				}
				else {
					return fac.oxms().inPort(OFPort.of(Integer.valueOf((String) entry.get("in_port"))));
				}
			}
			else if (fieldstr.toLowerCase().equals("eth_dst")) {
				return fac.oxms().ethDst(MacAddress.of((String) entry.get("eth_dst")));
			}
			else if (fieldstr.toLowerCase().equals("eth_src")) {
				return fac.oxms().ethSrc(MacAddress.of((String) entry.get("eth_src")));
			}
			else if (fieldstr.toLowerCase().equals("vlan_vid")) {
				return fac.oxms().vlanVid(OFVlanVidMatch.ofVlan(Integer.valueOf((String) entry.get("vlan_vid"))));
			}
			else if (fieldstr.toLowerCase().equals("vlan_pcp")) {
				return fac.oxms().vlanPcp(VlanPcp.of(Byte.valueOf(((String) entry.get("vlan_pcp")))));
			}
			else if (fieldstr.toLowerCase().equals("eth_type")) {
				return fac.oxms().ethType(EthType.of(Integer.valueOf(((String) entry.get("eth_type")).replaceAll("0x", ""), 16)));
			}
			else if (fieldstr.toLowerCase().equals("ip_proto")) {
				if ( (fieldstr.toLowerCase().startsWith("0x")) ) {
					String value = Integer.valueOf(((String) entry.get("ip_proto")).replaceAll("0x", ""), 16).toString();
					return fac.oxms().ipProto(IpProtocol.of(Short.valueOf(value)));
				}
				else {
					return fac.oxms().ipProto(IpProtocol.of(Short.valueOf((String) entry.get("ip_proto"))));
				}
			}
			else if (fieldstr.toLowerCase().equals("ipv4_src")) {
				return fac.oxms().ipv4Src(IPv4Address.of(IPv4.toIPv4AddressBytes((String) entry.get("ipv4_src"))));
			}
			else if (fieldstr.toLowerCase().equals("ipv4_dst")) {
				return fac.oxms().ipv4Dst(IPv4Address.of(IPv4.toIPv4AddressBytes((String) entry.get("ipv4_dst"))));
			}
			else if (fieldstr.toLowerCase().equals("ip_dscp")) {
				return fac.oxms().ipDscp(IpDscp.of(Byte.valueOf(((String) entry.get("ip_dscp")))));
			}
			else if (fieldstr.toLowerCase().equals("ip_ecn")) {
				return fac.oxms().ipEcn(IpEcn.of(Byte.valueOf(((String) entry.get("ip_ecn")))));
			}
			else if (fieldstr.toLowerCase().equals("tcp_src")) {
				return fac.oxms().tcpSrc(TransportPort.of(Integer.valueOf((String) entry.get("tcp_src"))));
			}
			else if (fieldstr.toLowerCase().equals("tcp_dst")) {
				return fac.oxms().tcpDst(TransportPort.of(Integer.valueOf((String) entry.get("tcp_dst"))));
			}
			else if (fieldstr.toLowerCase().equals("udp_src")) {
				return fac.oxms().udpSrc(TransportPort.of(Integer.valueOf((String) entry.get("udp_src"))));
			}
			else if (fieldstr.toLowerCase().equals("udp_dst")) {
				return fac.oxms().udpDst(TransportPort.of(Integer.valueOf((String) entry.get("udp_dst"))));
			}
			else if (fieldstr.toLowerCase().equals("sctp_src")) {
				return fac.oxms().sctpSrc(TransportPort.of(Integer.valueOf((String) entry.get("sctp_src"))));
			}
			else if (fieldstr.toLowerCase().equals("sctp_dst")) {
				return fac.oxms().sctpDst(TransportPort.of(Integer.valueOf((String) entry.get("sctp_dst"))));
			}
			else if (fieldstr.toLowerCase().equals("icmpv4_type")) {
				return fac.oxms().icmpv4Type(ICMPv4Type.of(Short.valueOf((String) entry.get("icmpv4_type"))));
			}
			else if (fieldstr.toLowerCase().equals("icmpv4_code")) {
				return fac.oxms().icmpv4Code(ICMPv4Code.of(Short.valueOf((String) entry.get("icmpv4_code"))));
			}
			else if (fieldstr.toLowerCase().equals("arp_op")) {
				return fac.oxms().arpOp(ArpOpcode.of(Integer.valueOf((String) entry.get("arp_op"))));
			}
			else if (fieldstr.toLowerCase().equals("arp_spa")) {
				return fac.oxms().arpSpa(IPv4Address.of(IPv4.toIPv4AddressBytes((String) entry.get("arp_spa"))));
			}
			else if (fieldstr.toLowerCase().equals("arp_tpa")) {
				return fac.oxms().arpTpa(IPv4Address.of((String) entry.get("arp_tpa")));
			}
			else if (fieldstr.toLowerCase().equals("arp_sha")) {
				return fac.oxms().arpSha(MacAddress.of((String) entry.get("arp_sha")));
			}
			else if (fieldstr.toLowerCase().equals("arp_tha")) {
				return fac.oxms().arpTha(MacAddress.of((String) entry.get("arp_tha")));
			}
			else if (fieldstr.toLowerCase().equals("mpls_label")) {
				return fac.oxms().mplsLabel(U32.of(Long.valueOf((String) entry.get("mpls_label"))));
			}
			else if (fieldstr.toLowerCase().equals("mpls_tc")) {
				return fac.oxms().mplsTc(U8.of(Short.valueOf((String) entry.get("mpls_tc"))));
			}
			else {
				throw new StaticFlowEntryException("Unsupported field " + fieldstr);
			}
		}

		return null;
	}

	/**
	 * This method build Match.
	 * 
	 * @param sw IOFSwitch
	 * @param keys the key list which is used for building Match among entry
	 * @param entry all input entry
	 * 
	 * @return Match object
	 * 
	 * @throws StaticFlowEntryException
	 */
	public static Match makeMatch(IOFSwitch sw, List<String> keys, Map<String, Object> entry) throws StaticFlowEntryException {
		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		Match.Builder builder = fac.buildMatch();

		if (keys.contains("eth_type")) {
			String dtype = ((String) entry.get("eth_type")).replaceAll("0x", "");
			builder.setExact(MatchField.ETH_TYPE, EthType.of(Integer.valueOf(dtype, 16)));
		}
		for (String key : keys) {
			if (key.toLowerCase().equals("in_port")) {
				builder.setExact(MatchField.IN_PORT, OFPort.of(Integer.valueOf((String) entry.get("in_port"))));
			}
			else if (key.toLowerCase().equals("eth_dst")) {
				builder.setExact(MatchField.ETH_DST, MacAddress.of((String) entry.get("eth_dst")));
			}
			else if (key.toLowerCase().equals("eth_src")) {
				builder.setExact(MatchField.ETH_SRC, MacAddress.of((String) entry.get("eth_src")));
			}
			else if (key.toLowerCase().equals("vlan_vid")) {
				builder.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlan(Integer.valueOf((String) entry.get("vlan_vid"))));
			}
			else if (key.toLowerCase().equals("vlan_pcp")) {
				builder.setExact(MatchField.VLAN_PCP, VlanPcp.of(Byte.valueOf((String) entry.get("vlan_pcp"))));
			}
			else if (key.toLowerCase().equals("eth_type")) {
				//already handled
			}
//			else if (key.toLowerCase().equals("nw_tos")) {
//				byte b = Byte.valueOf((String) entry.get("nw_tos"));
//				builder.setExact(MatchField.IP_DSCP, IpDscp.of((byte) (0b00111111 & (b >> 2))));
//				builder.setExact(MatchField.IP_ECN, IpEcn.of((byte) (0b00000011 & b)));
//			}
			else if (key.toLowerCase().equals("ip_proto")) {
				if ( ((String) entry.get("ip_proto")).startsWith("0x") ) {
					String value = Integer.valueOf(((String) entry.get("ip_proto")).replaceAll("0x", ""), 16).toString();
					builder.setExact(MatchField.IP_PROTO, IpProtocol.of(Short.valueOf(value)));
				} else {
					builder.setExact(MatchField.IP_PROTO, IpProtocol.of(Short.valueOf((String) entry.get("ip_proto"))));
				}
			}
			else if (key.toLowerCase().equals("ipv4_src")) {
				String value = entry.get("ipv4_src").toString();
				if ( builder.get(MatchField.ETH_TYPE) == EthType.ARP ) {
					if ( value.contains("/") ) {
						String[] arrayValue = value.split("/");
						builder.setMasked(MatchField.ARP_SPA, IPv4Address.of(arrayValue[0]), 
								IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
					} else {
						builder.setExact(MatchField.ARP_SPA, IPv4Address.of(value));
					}
				} else {
					if ( value.contains("/") ) {
						String[] arrayValue = value.split("/");
						builder.setMasked(MatchField.IPV4_SRC, IPv4Address.of(arrayValue[0]), 
								IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
					} else {
						builder.setExact(MatchField.IPV4_SRC, IPv4Address.of(value));
					}
				}
			}
			else if (key.toLowerCase().equals("ipv4_dst")) {
				String value = entry.get("ipv4_dst").toString();
				if ( builder.get(MatchField.ETH_TYPE) == EthType.ARP ) {
					if ( value.contains("/") ) {
						String[] arrayValue = value.split("/");
						builder.setMasked(MatchField.ARP_TPA, IPv4Address.of(arrayValue[0]), 
								IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
					} else {
						builder.setExact(MatchField.ARP_TPA, IPv4Address.of(value));
					}
				} else {
					if ( value.contains("/") ) {
						String[] arrayValue = value.split("/");
						builder.setMasked(MatchField.IPV4_DST, IPv4Address.of(arrayValue[0]), 
								IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
					} else {
						builder.setExact(MatchField.IPV4_DST, IPv4Address.of(value));
					}
				}
			}
			else if (key.toLowerCase().equals("ip_dscp")) {
				builder.setExact(MatchField.IP_DSCP, IpDscp.of(Byte.valueOf((String) entry.get("ip_dscp"))));
			}
			else if (key.toLowerCase().equals("ip_ecn")) {
				builder.setExact(MatchField.IP_ECN, IpEcn.of(Byte.valueOf((String) entry.get("ip_ecn"))));
			}
			else if (key.toLowerCase().equals("tcp_src")) {
				builder.setExact(MatchField.TCP_SRC, TransportPort.of(Integer.valueOf((String) entry.get("tcp_src"))));
			}
			else if (key.toLowerCase().equals("tcp_dst")) {
				builder.setExact(MatchField.TCP_DST, TransportPort.of(Integer.valueOf((String) entry.get("tcp_dst"))));
			}
			else if (key.toLowerCase().equals("udp_src")) {
				builder.setExact(MatchField.UDP_SRC, TransportPort.of(Integer.valueOf((String) entry.get("udp_src"))));
			}
			else if (key.toLowerCase().equals("udp_dst")) {
				builder.setExact(MatchField.UDP_DST, TransportPort.of(Integer.valueOf((String) entry.get("udp_dst"))));
			}
			else if (key.toLowerCase().equals("sctp_src")) {
				builder.setExact(MatchField.SCTP_SRC, TransportPort.of(Integer.valueOf((String) entry.get("sctp_src"))));
			}
			else if (key.toLowerCase().equals("sctp_dst")) {
				builder.setExact(MatchField.SCTP_DST, TransportPort.of(Integer.valueOf((String) entry.get("sctp_dst"))));
			}
			else if (key.toLowerCase().equals("icmpv4_type")) {
				builder.setExact(MatchField.ICMPV4_TYPE, ICMPv4Type.of(Short.valueOf((String) entry.get("icmpv4_type"))));
			}
			else if (key.toLowerCase().equals("icmpv4_code")) {
				builder.setExact(MatchField.ICMPV4_CODE, ICMPv4Code.of(Short.valueOf((String) entry.get("icmpv4_code"))));
			}
			else if (key.toLowerCase().equals("arp_op")) {
				builder.setExact(MatchField.ARP_OP, ArpOpcode.of(Integer.valueOf((String) entry.get("arp_op"))));
			}
			else if (key.toLowerCase().equals("arp_spa")) {
				String value = entry.get("arp_spa").toString();
				if ( value.contains("/") ) {
					String[] arrayValue = value.split("/");
					builder.setMasked(MatchField.ARP_SPA, IPv4Address.of(arrayValue[0]), 
							IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
				} else {
					builder.setExact(MatchField.ARP_SPA, IPv4Address.of(value));
				}
			}
			else if (key.toLowerCase().equals("arp_tpa")) {
				String value = entry.get("arp_tpa").toString();
				if ( value.contains("/") ) {
					String[] arrayValue = value.split("/");
					builder.setMasked(MatchField.ARP_TPA, IPv4Address.of(arrayValue[0]), 
							IPv4Address.ofCidrMaskLength( Integer.valueOf(arrayValue[1]) ));
				} else {
					builder.setExact(MatchField.ARP_TPA, IPv4Address.of(value));
				}
			}
			else if (key.toLowerCase().equals("arp_sha")) {
				builder.setExact(MatchField.ARP_SHA, MacAddress.of((String) entry.get("arp_sha")));
			}
			else if (key.toLowerCase().equals("arp_tha")) {
				builder.setExact(MatchField.ARP_THA, MacAddress.of((String) entry.get("arp_tha")));
			}
			else if (key.toLowerCase().equals("mpls_label")) {
				builder.setExact(MatchField.MPLS_LABEL, U32.of(Long.valueOf((String) entry.get("mpls_label"))));
			}
			else if (key.toLowerCase().equals("mpsl_tc")) {
				builder.setExact(MatchField.MPLS_TC, U8.of(Short.valueOf((String) entry.get("mpls_tc"))));
			}
			else {
				throw new StaticFlowEntryException("Unsupported field " + key);
			}
		}

		return builder.build();
	}

	/**
	 * This method builds Actions.
	 * 
	 * @param sw IOFSwitch
	 * @param entries one or more entries to make OFActions
	 * 
	 * @return the list of OFAction
	 * 
	 * @throws StaticFlowEntryException
	 */
	private static List<OFAction> makeActions(IOFSwitch sw, List<Map<String, Object>> entries) throws StaticFlowEntryException {
		if (entries == null)	return null;

		List<OFAction> actions = new ArrayList<OFAction>();

		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		for (Map<String, Object> entry : entries) {
			for (String actionstr : entry.keySet()) {
				if (actionstr.toLowerCase().equals("output")) {		//OF1.0 & 1.3
					OFActionOutput.Builder action = fac.actions().buildOutput();
					Long value  = null;
					if ( ((String) entry.get("output")).startsWith("0x") ) {
						value = Long.parseLong(((String) entry.get("output")).substring(2), 16);
					}
					else {
						value = Long.parseLong((String) entry.get("output"));
					}
					action
					.setMaxLen(0xffff)
					.setPort(OFPort.of( value.intValue() ));
					actions.add(action.build());
				}

				//OF1.0
				else if (actionstr.toLowerCase().equals("set_vlan_vid")) {
					OFActionSetVlanVid action = 
							fac.actions().setVlanVid(VlanVid.ofVlan(Short.valueOf((String) entry.get("set_vlan_vid"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_vlan_pcp")) {
					OFActionSetVlanPcp action = 
							fac.actions().setVlanPcp(VlanPcp.of(Byte.valueOf((String) entry.get("set_vlan_pcp"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("strip_vlan")) {
					OFActionPopVlan action = 
							fac.actions().popVlan();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_dl_src")) {
					OFActionSetDlSrc action = 
							fac.actions().setDlSrc(MacAddress.of((String) entry.get("set_dl_src")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_dl_dst")) {
					OFActionSetDlDst action = 
							fac.actions().setDlDst(MacAddress.of((String) entry.get("set_dl_dst")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_nw_src")) {
					OFActionSetNwSrc action = 
							fac.actions().setNwSrc(IPv4Address.of((String) entry.get("set_nw_src")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_nw_dst")) {
					OFActionSetNwDst action = 
							fac.actions().setNwDst(IPv4Address.of((String) entry.get("set_nw_dst")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_nw_tos")) {
					OFActionSetNwTos action = 
							fac.actions().setNwTos(Short.valueOf((String) entry.get("set_nw_tos")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_tp_src")) {
					OFActionSetTpSrc action = 
							fac.actions().setTpSrc(TransportPort.of(Short.valueOf((String) entry.get("set_tp_src"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_tp_dst")) {
					OFActionSetTpDst action = 
							fac.actions().setTpDst(TransportPort.of(Short.valueOf((String) entry.get("set_tp_dst"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("enqueue")) {
					String[] portqueue = ((String) entry.get("enqueue")).split(":");
					OFActionEnqueue action = 
							fac.actions().enqueue(
									OFPort.ofShort(Short.valueOf(portqueue[0])), 
									Long.valueOf(portqueue[1]));
					actions.add(action);
				}

				//OF1.3
				else if (actionstr.toLowerCase().equals("copy_ttl_out")) {
					OFActionCopyTtlOut action = 
							fac.actions().copyTtlOut();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("copy_ttl_in")) {
					OFActionCopyTtlIn action = 
							fac.actions().copyTtlIn();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_mpls_ttl")) {
					OFActionSetMplsTtl action = 
							fac.actions().setMplsTtl(Byte.valueOf((String) entry.get("set_mpls_ttl")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("dec_mpls_ttl")) {
					OFActionDecMplsTtl action = 
							fac.actions().decMplsTtl();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("push_vlan")) {
					OFActionPushVlan action = 
							fac.actions().pushVlan(EthType.of(Short.valueOf((String) entry.get("push_vlan"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("pop_vlan")) {
					OFActionPopVlan action = 
							fac.actions().popVlan();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("push_mpls")) {
					OFActionPushMpls action = 
							fac.actions().pushMpls(EthType.of(Short.valueOf((String) entry.get("push_mpls"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("pop_mpls")) {
					OFActionPopMpls action = 
							fac.actions().popMpls(EthType.of(Short.valueOf((String) entry.get("pop_mpls"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_queue")) {
					OFActionSetQueue action = 
							fac.actions().setQueue(Integer.valueOf((String) entry.get("set_queue")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("group")) {
					OFActionGroup action = 
							fac.actions().group(OFGroup.of(Integer.valueOf((String) entry.get("group"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_nw_ttl")) {
					OFActionSetNwTtl action = 
							fac.actions().setNwTtl(Byte.valueOf((String) entry.get("set_nw_ttl")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("dec_nw_ttl")) {
					OFActionDecNwTtl action = 
							fac.actions().decNwTtl();
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("set_field")) {
					@SuppressWarnings("unchecked")
					OFActionSetField action = 
					fac.actions().setField(makeOxm(sw, actionstr, (Map<String, Object>) entry.get("set_field")));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("push_pbb")) {
					OFActionPushPbb action = 
							fac.actions().pushPbb(EthType.of(Short.valueOf((String) entry.get("push_pbb"))));
					actions.add(action);
				}
				else if (actionstr.toLowerCase().equals("pop_pbb")) {
					OFActionPopPbb action = 
							fac.actions().popPbb();
					actions.add(action);
				}
				else {
					throw new StaticFlowEntryException("Unexpected action " + actionstr);
				}
			}
		}

		return actions;
	}

	/**
	 * This method builds Instructions.
	 * 
	 * @param sw IOFSwitch
	 * @param entries one or more entries to make OFInstructions
	 * 
	 * @return the list of OFInstruction
	 * 
	 * @throws StaticFlowEntryException
	 */
	@SuppressWarnings("unchecked")
	private static List<OFInstruction> makeInstructions(IOFSwitch sw, List<Map<String, Object>> entries) throws StaticFlowEntryException {
		if (entries == null)	return null;

		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		List<OFInstruction> instructions = new ArrayList<OFInstruction>();

		for (Map<String, Object> entry : entries) {
			for (String inststr : entry.keySet()) {
				if (inststr.toLowerCase().equals("goto_table")) {
					OFInstructionGotoTable inst = 
							fac.instructions().gotoTable(TableId.of(Byte.valueOf((String) entry.get("goto_table"))));
					instructions.add(inst);
				}
				else if (inststr.toLowerCase().equals("write_actions")) {
					OFInstructionWriteActions inst = 
							fac.instructions().writeActions(makeActions(sw, (List<Map<String, Object>>) entry.get("write_actions")));
					instructions.add(inst);
				}
				else if (inststr.toLowerCase().equals("apply_actions")) {
					OFInstructionApplyActions inst = 
							fac.instructions().applyActions(makeActions(sw, (List<Map<String, Object>>) entry.get("apply_actions")));
					instructions.add(inst);
				}
				else if (inststr.toLowerCase().equals("clear_actions")) {
					OFInstructionClearActions inst = 
							fac.instructions().clearActions();
					instructions.add(inst);
				}
				else {
					throw new StaticFlowEntryException("Unexpected instruction " + inststr);
				}
			}
		}

		return instructions;
	}

	/**
	 * This method makes and returns OFFlowMod.
	 * 
	 * @param sw IOFSwitch
	 * @param command ADD or DELETE_STRICT
	 * @param entry all entries to make OFFlowMod
	 * 
	 * @return OFFlowMod object or null when an unsupported command has received
	 * 
	 * @throws StaticFlowEntryException
	 */
	@SuppressWarnings("unchecked")
	protected static OFMessage makeFlowMod(IOFSwitch sw, OFFlowModCommand command, Map<String, Object> entry) throws StaticFlowEntryException {

		if ( !entry.containsKey("name") || !entry.containsKey("switch") ) {
			logger.debug("Skipping entry with missing required 'name' or 'switch' entry: {}", entry);
			return null;
		}

		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		OFFlowMod.Builder flowmod = null;
		switch( command ) {
		case ADD:
			flowmod = fac.buildFlowAdd();
			break;
		case DELETE:
			flowmod = fac.buildFlowDelete();
			break;
		case DELETE_STRICT:
			flowmod = fac.buildFlowDeleteStrict();
			break;
		default:
			throw new StaticFlowEntryException("Unsupported FlowMod command: " + command);
		}

		flowmod = setDefaultFlowModFields(flowmod, command);

		String entryName = null;
		String switchName = null;

		List<String> matchkeys = new LinkedList<String>();

		entryName = (String) entry.get("name");
		switchName = (String) entry.get("switch");

		for (String key : entry.keySet() ) {
			if (entry.get(key) == null) {
				continue;
			}
			else if (key.toLowerCase().equals("switch") || key.toLowerCase().equals("name")) {
				continue;	// already handled
			}
			else if (key.toLowerCase().equals("active")) {
				if (!Boolean.valueOf((String) entry.get("active"))) {
					logger.debug("Skipping inactive entry {} for switch {}", entryName, switchName);
					return null;
				}
			}
			else if (key.toLowerCase().equals("table_id")) {
				try {
					flowmod.setTableId(TableId.of(Byte.valueOf((String) entry.get("table_id"))));
				} catch ( UnsupportedOperationException u ) {
					// does nothing
				}
			}
			else if (key.toLowerCase().equals("idle_timeout")) {
				flowmod.setIdleTimeout(Short.valueOf((String) entry.get("idle_timeout")));
			}
			else if (key.toLowerCase().equals("hard_timeout")) {
				flowmod.setHardTimeout(Short.valueOf((String) entry.get("hard_timeout")));
			}
			else if (key.toLowerCase().equals("priority")) {
				flowmod.setPriority(Short.valueOf((String) entry.get("priority")));
			}
			else if (key.toLowerCase().equals("instructions")) {
				try {
					flowmod.setInstructions(makeInstructions(sw, (List<Map<String, Object>>) entry.get("instructions")));
				}
				catch (UnsupportedOperationException u) {
					List<Map<String, Object>> instructions = (List<Map<String, Object>>) entry.get("instructions");
					for (Map<String, Object> instruction : instructions) {
						List<Map<String, Object>> actions = (List<Map<String, Object>>) instruction.get("apply_actions");
						flowmod.setActions(makeActions(sw, actions));
					}
				}
			}
			else {		//match
				matchkeys.add(key);
			}
		}

		try {
			flowmod.setMatch( makeMatch(sw, matchkeys, entry) );
		}
		catch (ClassCastException e) {
			if (entryName != null && switchName != null) {
				throw new StaticFlowEntryException("skipping entry " + entryName + " on switch " + switchName + " with bad data: " + e.getMessage());
			}
			else {
				throw new StaticFlowEntryException("skipping entry with bad data: " + e.getMessage() + " :: " + e.getStackTrace());
			}
		}

		return flowmod.build();
	}

	/**
	 * Verifies the support of input field name. 
	 * 
	 * @param fields all key fields of input
	 * 
	 * @throws StaticFlowEntryException
	 */
	public static void checkInputField (Set<String> fields) throws StaticFlowEntryException {
		for (String field : fields) {
			if (!staticFlowEntryFields.contains(field)) {
				throw new StaticFlowEntryException("Unsupported field name: " + field);
			}
		}

		if ( !fields.contains("instructions") && fields.contains("actions") ) {
			throw new StaticFlowEntryException("Missing instructions field");
		}
	}

	/**
	 * Verifies prerequisite of input.
	 * 
	 * @param entries all entries to check
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public static void checkMatchPrerequisite (Map<String, Object> entries) throws StaticFlowEntryException {
		if ( entries.containsKey("ipv4_src") || entries.containsKey("ipv4_dst") ) {
			if ( !entries.containsKey("eth_type") ) {
				throw new StaticFlowEntryException("Check prerequisite: need eth_type field.");
			}
			if ( !( ((String) entries.get("eth_type")).equals("0x0800") ||
					((String) entries.get("eth_type")).equals("0x800") ||
					((String) entries.get("eth_type")).equals("800") ||
					((String) entries.get("eth_type")).equals("0x0806") ||
					((String) entries.get("eth_type")).equals("0x800") ||
					((String) entries.get("eth_type")).equals("806") ) ) {
				throw new StaticFlowEntryException("Check prerequisite: eth_type must be 0x0800 or 0x0806.");
			}
		}
		if ( entries.containsKey("tcp_src") || entries.containsKey("tcp_dst") ) {
			if ( !entries.containsKey("eth_type") || !entries.containsKey("ip_proto") ) {
				throw new StaticFlowEntryException("Check prerequisite: need eth_type and ip_proto fields.");
			}
			if ( !( ((String) entries.get("eth_type")).equals("0x0800") || 
					((String) entries.get("eth_type")).equals("0800") || 
					((String) entries.get("eth_type")).equals("0x800") || 
					((String) entries.get("eth_type")).equals("800") || 
					((String) entries.get("eth_type")).equals("0x86dd") || 
					((String) entries.get("eth_type")).equals("86dd") ) ) {
				throw new StaticFlowEntryException("Check prerequisite: eth_type must be 0x0800 or 0x86dd.");
			}
			if ( !( ((String) entries.get("ip_proto")).equals("6") ) ) {
				throw new StaticFlowEntryException("Check prerequisite: ip_proto must be 6");
			}
		}
		if ( entries.containsKey("ip_proto") ) {
			if ( !entries.containsKey("eth_type") ) {
				throw new StaticFlowEntryException("Check prerequisite: need eth_type field.");
			}
			if ( !( ((String) entries.get("eth_type")).equals("0x0800") || 
					((String) entries.get("eth_type")).equals("0800") || 
					((String) entries.get("eth_type")).equals("0x800") || 
					((String) entries.get("eth_type")).equals("800") || 
					((String) entries.get("eth_type")).equals("0x86dd") || 
					((String) entries.get("eth_type")).equals("86dd") ) ) {
				throw new StaticFlowEntryException("Check prerequisite: eth_type must be 0x0800 or 0x86dd.");
			}
		}
		if ( entries.containsKey("mpls_label") ) {
			if ( !entries.containsKey("eth_type") ) {
				throw new StaticFlowEntryException("Check prerequisite: need eth_type field.");
			}
			if ( !( ((String) entries.get("eth_type")).equals("0x8847") || 
					((String) entries.get("eth_type")).equals("8847") || 
					((String) entries.get("eth_type")).equals("0x8848") || 
					((String) entries.get("eth_type")).equals("8848") ) ) {
				throw new StaticFlowEntryException("Check prerequisite: eth_type must be 0x8847 or 0x8848.");
			}
		}
		if ( entries.containsKey("vlan_pcp") ) {
			if ( !entries.containsKey("vlan_vid") ) {
				throw new StaticFlowEntryException("Check prerequisite: need vlan_vid field.");
			}
		}
	}

	/**
	 * Converts input String of JSON format for Instructions into the Map<String, Object>.
	 * The Object can includes other Map or etc recursively. 
	 * 
	 * @param jsontext input string of JSON format
	 * 
	 * @return all entries in Map<String, Object> notation
	 * 
	 * @throws IOException
	 */
	private static List<Map<String, Object>> jsonToInstructions(String jsontext) throws IOException {
		MappingJsonFactory f = new MappingJsonFactory();		
		ObjectMapper mapper = new ObjectMapper(f);

		TypeReference<List<Map<String,Object>>> typeref = new TypeReference<List<Map<String,Object>>>() {};
		List<Map<String, Object>> list = mapper.readValue(jsontext, typeref);

		return list;
	}

	/**
	 * Converts input String of JSON format for Actions into the Map<String, Object>.
	 * The Object can includes other Map or etc recursively. 
	 * 
	 * @param jsontext input string of JSON format
	 * 
	 * @return all entries in Map<String, Object> notation
	 * 
	 * @throws IOException
	 */
	private static List<Map<String, Object>> jsonToActions(String jsontext) throws IOException {
		MappingJsonFactory f = new MappingJsonFactory();		
		ObjectMapper mapper = new ObjectMapper(f);

		TypeReference<List<Map<String,Object>>> typeref = new TypeReference<List<Map<String,Object>>>() {};
		List<Map<String, Object>> list = mapper.readValue(jsontext, typeref);

		return list;
	}

	/**
	 * Converts input String of JSON type into the Map<String, Object>.
	 * The Object can includes other Map or etc recursively.  
	 * 
	 * @param jsontext input string of JSON format
	 * 
	 * @return all entries in Map<String, Object> notation
	 * 
	 * @throws IOException
	 */
	public static Map<String, Object> jsonToStaticFlowEntry(String jsontext) throws IOException {
		Map<String, Object> entry = new LinkedHashMap<String, Object>();
		MappingJsonFactory f = new MappingJsonFactory();		
		ObjectMapper mapper = new ObjectMapper(f);
		JsonNode rootNode = mapper.readTree(jsontext);
		Iterator<Map.Entry<String, JsonNode>> fields = rootNode.getFields();

		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();

			if (field.getKey() == "instructions") {
				entry.put(field.getKey().toString(), jsonToInstructions(field.getValue().toString()));
			}
			else if (field.getKey() == "actions") {
				entry.put(field.getKey().toString(), jsonToActions(field.getValue().toString()));
			}
			else {
				entry.put(field.getKey().toString(), field.getValue().toString().replaceAll("\"", ""));
			}
		}

//		if (Main.debug){
//			System.out.println("input entry    : " + jsontext);
//			System.out.println("converted entry: " + entry);
//		}

		return entry;
	}
}
