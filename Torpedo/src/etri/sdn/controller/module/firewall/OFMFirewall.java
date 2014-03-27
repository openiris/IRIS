package etri.sdn.controller.module.firewall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowDelete;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IPv4AddressWithMask;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.TransportPort;
import org.projectfloodlight.openflow.types.U64;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.module.devicemanager.IDevice;
import etri.sdn.controller.module.forwarding.Forwarding;
import etri.sdn.controller.module.routing.IRoutingDecision;
import etri.sdn.controller.module.routing.RoutingDecision;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.util.AppCookie;
import etri.sdn.controller.util.Logger;

/**
 * This class implements the firewall module.
 * 
 * <p>Modified the original Firewall class of Floodlight.
 * 
 * @author jshin
 * 
 */
public class OFMFirewall extends OFModule implements IFirewallService
{

	private FirewallStorage firewallStorage;
	private OFMStorageManager storageInstance;

	private String dbName;
	private String collectionName;
	protected List<FirewallRule> rules;		// protected by synchronized
	protected boolean enabled;
	protected int subnet_mask = IPv4.toIPv4Address("255.255.255.0");

	private long cookie = AppCookie.makeCookie(Forwarding.FORWARDING_APP_ID, 0);

	@SuppressWarnings("unused")
	private OFProtocol protocol;

	// constant strings for storage/parsing
	public static final String TABLE_NAME = "controller_firewallrules";
	public static final String COLUMN_RULEID = "ruleid";
	public static final String COLUMN_DPID = "dpid";
	public static final String COLUMN_IN_PORT = "in_port";
	public static final String COLUMN_DL_SRC = "dl_src";
	public static final String COLUMN_DL_DST = "dl_dst";
	public static final String COLUMN_DL_TYPE = "dl_type";
	public static final String COLUMN_NW_SRC_PREFIX = "nw_src_prefix";
	public static final String COLUMN_NW_SRC_MASKBITS = "nw_src_maskbits";
	public static final String COLUMN_NW_DST_PREFIX = "nw_dst_prefix";
	public static final String COLUMN_NW_DST_MASKBITS = "nw_dst_maskbits";
	public static final String COLUMN_NW_PROTO = "nw_proto";
	public static final String COLUMN_TP_SRC = "tp_src";
	public static final String COLUMN_TP_DST = "tp_dst";
	public static final String COLUMN_WILDCARD_DPID = "wildcard_dpid";
	public static final String COLUMN_WILDCARD_IN_PORT = "wildcard_in_port";
	public static final String COLUMN_WILDCARD_DL_SRC = "wildcard_dl_src";
	public static final String COLUMN_WILDCARD_DL_DST = "wildcard_dl_dst";
	public static final String COLUMN_WILDCARD_DL_TYPE = "wildcard_dl_type";
	public static final String COLUMN_WILDCARD_NW_SRC = "wildcard_nw_src";
	public static final String COLUMN_WILDCARD_NW_DST = "wildcard_nw_dst";
	public static final String COLUMN_WILDCARD_NW_PROTO = "wildcard_nw_proto";
	public static final String COLUMN_WILDCARD_TP_SRC = "wildcard_tp_src";
	public static final String COLUMN_WILDCARD_TP_DST = "wildcard_tp_dst";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_ACTION = "action";
	public static String ColumnNames[] = { COLUMN_RULEID, COLUMN_DPID,
		COLUMN_IN_PORT, COLUMN_DL_SRC, COLUMN_DL_DST, COLUMN_DL_TYPE,
		COLUMN_NW_SRC_PREFIX, COLUMN_NW_SRC_MASKBITS, COLUMN_NW_DST_PREFIX,
		COLUMN_NW_DST_MASKBITS, COLUMN_NW_PROTO, COLUMN_TP_SRC,
		COLUMN_TP_DST, COLUMN_WILDCARD_DPID, COLUMN_WILDCARD_IN_PORT,
		COLUMN_WILDCARD_DL_SRC, COLUMN_WILDCARD_DL_DST,
		COLUMN_WILDCARD_DL_TYPE, COLUMN_WILDCARD_NW_SRC,
		COLUMN_WILDCARD_NW_DST, COLUMN_WILDCARD_NW_PROTO, COLUMN_PRIORITY,
		COLUMN_ACTION };


	public OFMStorageManager getStorageInstance(){
		return storageInstance;
	}

	public String getDbName(){
		return dbName;
	}

	public String getCollectionName(){
		return collectionName;
	}

	protected OFPort getInputPort(OFPacketIn pi) {
		if ( pi == null ) {
			throw new AssertionError("pi cannot refer null");
		}
		try {
			return pi.getInPort();
		} catch ( UnsupportedOperationException e ) {
			return pi.getMatch().get(MatchField.IN_PORT);
		}
	}

	/*
	 * Internal methods
	 */

	/**
	 * Checks the incoming packet with the firewall policy and create 
	 * a decision to handle the packet. 
	 * 
	 * @param sw the switch instance
	 * @param pi packetin
	 * @param decision the routing decision
	 * @param cntx the {@link MessageContext}
	 * 
	 * @return true when normally terminated
	 */
	private boolean processPacketInMessage(IOFSwitch sw, OFPacketIn pi, IRoutingDecision decision, MessageContext cntx){

		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);
		OFPort inPort = getInputPort(pi);

		// Allowing L2 broadcast + ARP broadcast request (also deny malformed
		// broadcasts -> L2 broadcast + L3 unicast)
		if (eth.isBroadcast() == true) {
			boolean allowBroadcast = true;
			// the case to determine if we have L2 broadcast + L3 unicast
			// don't allow this broadcast packet if such is the case (malformed packet)
			if (eth.getEtherType() == Ethernet.TYPE_IPv4
					&& this.IPIsBroadcast(((IPv4) eth.getPayload())
							.getDestinationAddress()) == false) {
				allowBroadcast = false;
			}
			if (allowBroadcast == true) {
				Logger.debug("Allowing broadcast traffic for PacketIn={}", pi);

				decision = new RoutingDecision(
						sw.getId(),
						inPort, 
						(IDevice) cntx.get(MessageContext.SRC_DEVICE),
						IRoutingDecision.RoutingAction.MULTICAST);
				decision.addToContext(cntx);
			} else {
				Logger.debug("Blocking malformed broadcast traffic for PacketIn={}", pi);

				decision = new RoutingDecision(
						sw.getId(),
						inPort, 
						(IDevice) cntx.get(MessageContext.SRC_DEVICE),
						IRoutingDecision.RoutingAction.DROP);
				decision.addToContext(cntx);
			}
			return true;
		}

		/*
		 * ARP response (unicast) can be let through without filtering through
		 * rules by uncommenting the code below
		 */
		/*
		 * else if (eth.getEtherType() == Ethernet.TYPE_ARP) {
		 * logger.info("allowing ARP traffic"); decision = new
		 * FirewallDecision(IRoutingDecision.RoutingAction.FORWARD_OR_FLOOD);
		 * decision.addToContext(cntx); return Command.CONTINUE; }
		 */

		// check if we have a matching rule for this packet/flow
		// and no decision is taken yet
		if (decision == null) {
			RuleWildcardsPair match_ret = this.matchWithRule(sw, pi, cntx);
			FirewallRule rule = match_ret.rule;

			if (rule == null || rule.action == FirewallRule.FirewallAction.DENY) {
				decision = new RoutingDecision(
						sw.getId(),
						inPort, 
						(IDevice) cntx.get(MessageContext.SRC_DEVICE),
						IRoutingDecision.RoutingAction.DROP);
				decision.setWildcards(match_ret.wildcards);
				decision.addToContext(cntx);

				if (rule == null){
					Logger.debug("No firewall rule found for PacketIn={}, blocking flow", pi);
				}
				else if (rule.action == FirewallRule.FirewallAction.DENY) {
					Logger.debug("Deny rule={} match for PacketIn={}", rule, pi);
				}

			} else {
				decision = new RoutingDecision(
						sw.getId(),
						inPort, 
						(IDevice) cntx.get(MessageContext.SRC_DEVICE),
						IRoutingDecision.RoutingAction.FORWARD_OR_FLOOD);
				decision.setWildcards(match_ret.wildcards);
				decision.addToContext(cntx);

				Logger.debug("Allow rule={} match for PacketIn={}", rule, pi);
			}
		}

		return true;
	}

	/**
	 * Iterates over the firewall rules and tries to match them with the
	 * incoming packet (flow). Uses the FirewallRule class's matchWithFlow
	 * method to perform matching. It maintains a pair of wildcards (allow and
	 * deny) which are assigned later to the firewall's decision, where 'allow'
	 * wildcards are applied if the matched rule turns out to be an ALLOW rule
	 * and 'deny' wildcards are applied otherwise. Wildcards are applied to
	 * firewall decision to optimize flows in the switch, ensuring least number
	 * of flows per firewall rule. So, if a particular field is not "ANY" (i.e.
	 * not wildcarded) in a higher priority rule, then if a lower priority rule
	 * matches the packet and wildcards it, it can't be wildcarded in the
	 * switch's flow entry, because otherwise some packets matching the higher
	 * priority rule might escape the firewall. The reason for keeping different
	 * two different wildcards is that if a field is not wildcarded in a higher
	 * priority allow rule, the same field shouldn't be wildcarded for packets
	 * matching the lower priority deny rule (non-wildcarded fields in higher
	 * priority rules override the wildcarding of those fields in lower priority
	 * rules of the opposite type). So, to ensure that wildcards are
	 * appropriately set for different types of rules (allow vs. deny), separate
	 * wildcards are maintained. Iteration is performed on the sorted list of
	 * rules (sorted in decreasing order of priority).
	 * 
	 * @param sw the switch instance
	 * @param pi packetin
	 * @param cntx the {@link MessageContext}
	 * 
	 * @return an instance of RuleWildcardsPair that specify rule that matches
	 *         and the wildcards for the firewall decision
	 */
	protected RuleWildcardsPair matchWithRule(IOFSwitch sw, OFPacketIn pi, MessageContext cntx) {
		FirewallRule matched_rule = null;
		OFPort inPort = getInputPort(pi);

		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);

		WildcardsPair wildcards = new WildcardsPair();

		synchronized (rules) {
			Iterator<FirewallRule> iter = this.rules.iterator();
			FirewallRule rule = null;
			// iterate through list to find a matching firewall rule
			while (iter.hasNext()) {
				// get next rule from list
				rule = iter.next();

				// check if rule matches
				if (rule.matchesFlow(sw.getId(), inPort, eth, wildcards) == true) {
					matched_rule = rule;
					break;
				}
			}
		}

		// make a pair of rule and wildcards, then return it
		RuleWildcardsPair ret = new RuleWildcardsPair();
		ret.rule = matched_rule;
		if (matched_rule == null || matched_rule.action == FirewallRule.FirewallAction.DENY) {
			ret.wildcards = wildcards.drop;
		} else {
			ret.wildcards = wildcards.allow;
		}
		return ret;
	}

	/**
	 * Checks whether an IP address is a broadcast address or not.
	 * 
	 * @param IPAddress the IP address to check
	 * 
	 * @return true if it is a broadcast address, false otherwise
	 */
	protected boolean IPIsBroadcast(int IPAddress) {
		// inverted subnet mask
		int inv_subnet_mask = ~this.subnet_mask;
		return ((IPAddress & inv_subnet_mask) == inv_subnet_mask);
	}

	/**
	 * Reads the rules from the persistent database and creates a sorted 
	 * array list of {@link FirewallRule}.
	 * 
	 * Similar to {@link #getStorageRules()}, which only reads contents for REST GET 
	 * and does no parsing, checking, nor putting into FirewallRule objects.
	 * 
	 * @return the sorted array list of {@link FirewallRule} instances 
	 * (rules from the database)
	 */
	protected ArrayList<FirewallRule> readRulesFromStorage() {

		ArrayList<FirewallRule> l = new ArrayList<FirewallRule>();

		Collection<Map<String, Object>> resultSet = firewallStorage.getAllDBEntries(storageInstance, dbName, collectionName);

		// Insert data of DB into FirewallEntryTable because it has no data at initialization
		firewallStorage.synchronizeStorage(resultSet);

		try {
			Map<String, Object> row;

			// put retrieved rows into FirewallRules
			for (Iterator<Map<String, Object>> it = resultSet.iterator(); it.hasNext();) {
				row = it.next();
				// now, parse row
				FirewallRule r = new FirewallRule();
				if (!row.containsKey(COLUMN_RULEID) || !row.containsKey(COLUMN_DPID)) {
					Logger.stderr("skipping entry with missing required 'ruleid' or 'switchid' entry: " + row);
					return l;
				}
				try {
					r.ruleid = Integer
							.parseInt((String) row.get(COLUMN_RULEID));
					r.dpid = Long.parseLong((String) row.get(COLUMN_DPID));

					for (String key : row.keySet()) {
						if (row.get(key) == null)
							continue;
						if (key.equals(COLUMN_RULEID)
								|| key.equals(COLUMN_DPID)
								|| key.equals("id")) {
							continue; // already handled
						} 

						else if (key.equals(COLUMN_IN_PORT)) {
							r.in_port = Short.parseShort((String) row
									.get(COLUMN_IN_PORT));
						} 

						else if (key.equals(COLUMN_DL_SRC)) {
							r.dl_src = Long.parseLong((String) row
									.get(COLUMN_DL_SRC));
						} 

						else if (key.equals(COLUMN_DL_DST)) {
							r.dl_dst = Long.parseLong((String) row
									.get(COLUMN_DL_DST));
						} 

						else if (key.equals(COLUMN_DL_TYPE)) {
							r.dl_type = Short.parseShort((String) row
									.get(COLUMN_DL_TYPE));
						} 

						else if (key.equals(COLUMN_NW_SRC_PREFIX)) {
							r.nw_src_prefix = Integer.parseInt((String) row
									.get(COLUMN_NW_SRC_PREFIX));
						} 

						else if (key.equals(COLUMN_NW_SRC_MASKBITS)) {
							r.nw_src_maskbits = Integer.parseInt((String) row
									.get(COLUMN_NW_SRC_MASKBITS));
						} 

						else if (key.equals(COLUMN_NW_DST_PREFIX)) {
							r.nw_dst_prefix = Integer.parseInt((String) row
									.get(COLUMN_NW_DST_PREFIX));
						} 

						else if (key.equals(COLUMN_NW_DST_MASKBITS)) {
							r.nw_dst_maskbits = Integer.parseInt((String) row
									.get(COLUMN_NW_DST_MASKBITS));
						} 

						else if (key.equals(COLUMN_NW_PROTO)) {
							r.nw_proto = Short.parseShort((String) row
									.get(COLUMN_NW_PROTO));
						} 

						else if (key.equals(COLUMN_TP_SRC)) {
							r.tp_src = Short.parseShort((String) row
									.get(COLUMN_TP_SRC));
						} 

						else if (key.equals(COLUMN_TP_DST)) {
							r.tp_dst = Short.parseShort((String) row
									.get(COLUMN_TP_DST));
						} 

						else if (key.equals(COLUMN_WILDCARD_DPID)) {
							r.wildcard_dpid = Boolean.parseBoolean((String) row
									.get(COLUMN_WILDCARD_DPID));
						} 

						else if (key.equals(COLUMN_WILDCARD_IN_PORT)) {
							r.wildcard_in_port = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_IN_PORT));
						} 

						else if (key.equals(COLUMN_WILDCARD_DL_SRC)) {
							r.wildcard_dl_src = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_DL_SRC));
						} 

						else if (key.equals(COLUMN_WILDCARD_DL_DST)) {
							r.wildcard_dl_dst = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_DL_DST));
						} 

						else if (key.equals(COLUMN_WILDCARD_DL_TYPE)) {
							r.wildcard_dl_type = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_DL_TYPE));
						} 

						else if (key.equals(COLUMN_WILDCARD_NW_SRC)) {
							r.wildcard_nw_src = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_NW_SRC));
						} 

						else if (key.equals(COLUMN_WILDCARD_NW_DST)) {
							r.wildcard_nw_dst = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_NW_DST));
						} 

						else if (key.equals(COLUMN_WILDCARD_NW_PROTO)) {
							r.wildcard_nw_proto = Boolean
									.parseBoolean((String) row
											.get(COLUMN_WILDCARD_NW_PROTO));
						} 

						else if (key.equals(COLUMN_PRIORITY)) {
							r.priority = Integer.parseInt((String) row
									.get(COLUMN_PRIORITY));
						} 

						else if (key.equals(COLUMN_ACTION)) {
							int tmp = Integer.parseInt((String) row.get(COLUMN_ACTION));
							if (tmp == FirewallRule.FirewallAction.DENY.ordinal())
								r.action = FirewallRule.FirewallAction.DENY;
							else if (tmp == FirewallRule.FirewallAction.ALLOW.ordinal())
								r.action = FirewallRule.FirewallAction.ALLOW;
							else {
								r.action = null;
								Logger.stderr("action not recognized");
							}
						}
					}
				} catch (ClassCastException e) {
					Logger.stderr("skipping rule " + r.ruleid + " with bad data : " + e.getMessage());
				}
				if (r.action != null)
					l.add(r);
			}
		} catch (Exception e) {
			Logger.stderr("failed to access storage: " + e.getMessage());
			// if the table doesn't exist, then wait to populate later via setStorageSource()
		}

		// now, sort the list based on priorities
		Collections.sort(l);

		return l;
	}


	/*
	 * OFModule methods
	 */
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		List<Class<? extends IService>> ret = new LinkedList<Class<? extends IService>>();
		ret.add(IFirewallService.class);
		return ret;
	}

	/**
	 * Initializes this module. As this module processes all PACKET_IN messages,
	 * it registers filter to receive those messages.
	 */
	@Override
	protected void initialize() {

		TorpedoProperties conf = TorpedoProperties.loadConfiguration();

		this.firewallStorage = new FirewallStorage (this, "FirewallStorage");
		this.storageInstance = (OFMStorageManager) getModule(IStorageService.class);
		this.dbName = conf.getString("storage-default-db");
		this.collectionName = firewallStorage.getName();

		this.protocol = getController().getProtocol();

		rules = new ArrayList<FirewallRule>();

		// start disabled
		enabled = false;

		registerFilter(
				OFType.PACKET_IN, 
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						return true;
					}
				});

		// Read rules
		synchronized (rules) {
			this.rules = readRulesFromStorage();
		}
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn, MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(
			Connection conn,
			MessageContext context,
			OFMessage msg,
			List<OFMessage> outgoing) {

		if (!this.enabled)
			return true;

		switch(msg.getType()) {
		case PACKET_IN:
			IRoutingDecision decision = null;
			if (context != null) {
				decision = (IRoutingDecision) context.get(MessageContext.ROUTING_DECISION);

				return this.processPacketInMessage(conn.getSwitch(), (OFPacketIn) msg, decision, context);
			}
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.firewallStorage };
	}

	private void purgeAllFlowRecords(IOFSwitch sw) {
		if ( sw == null ) {
			return;
		}

		OFFactory fac = OFFactories.getFactory(sw.getVersion());

		OFFlowDelete.Builder del = fac.buildFlowDelete();
		try {
			del
			.setCookie(U64.of(this.cookie))
			.setCookieMask(U64.of(0xffffffffffffffffL))
			.setOutPort(OFPort.ANY)
			.setMatch(fac.matchWildcardAll())
			.setTableId(TableId.ALL);
		} catch ( UnsupportedOperationException u ) {
			// does nothing.
		}

		sw.getConnection().write( del.build() );
	}

	/*
	 * IFirewallService methods
	 */
	@Override
	public void enableFirewall(boolean enabled) {
		Logger.debug("Setting firewall to {}", enabled);
		this.enabled = enabled;

		Collection<IOFSwitch> switches = getController().getSwitches();
		for ( IOFSwitch sw : switches ) {
			purgeAllFlowRecords(sw);
		}
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public List<FirewallRule> getRules() {
		return this.rules;
	}

	@Override
	public String getSubnetMask() {
		return IPv4.fromIPv4Address(this.subnet_mask);
	}

	@Override
	public void setSubnetMask(String newMask) {
		if (newMask.trim().isEmpty())
			return;
		this.subnet_mask = IPv4.toIPv4Address(newMask.trim());
	}

	@Override
	public List<Map<String, Object>> getStorageRules() {
		ArrayList<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

		Collection<Map<String, Object>> resultSet = firewallStorage.getAllDBEntries(storageInstance, dbName, collectionName);

		// Insert data of DB into FirewallEntryTable because it has no data at initialization
		firewallStorage.synchronizeStorage(resultSet);

		try {
			for (Iterator<Map<String, Object>> it = resultSet.iterator(); it.hasNext();) {
				l.add(it.next());
			}
		} catch (Exception e) {
			Logger.stderr("failed to access storage: " + e.getMessage());
			// if the table doesn't exist, then wait to populate later via setStorageSource()
		}
		return l;
	}

	private void purgeMatchingRulesFromSwitches(FirewallRule rule) {
		Collection<IOFSwitch> switches = getController().getSwitches();
		for ( IOFSwitch sw: switches ) {
			if ( !rule.wildcard_dpid ) {
				if ( sw.getId() != rule.dpid ) {
					continue;
				}
			}

			OFFactory fac = OFFactories.getFactory(sw.getVersion());
			OFFlowDelete.Builder del = fac.buildFlowDelete();

			try {
				del
				.setCookie(U64.of(this.cookie))
				.setCookieMask(U64.of(0xffffffffffffffffL));

				Match.Builder match = fac.buildMatch();
				if ( ! rule.wildcard_dl_type ) {
					match.setExact(MatchField.ETH_TYPE, EthType.of(rule.dl_type));
				}
				if ( ! rule.wildcard_dl_src ) {
					match.setExact(MatchField.ETH_SRC, MacAddress.of(rule.dl_src));
				}
				if ( ! rule.wildcard_dl_dst ) {
					match.setExact(MatchField.ETH_DST, MacAddress.of(rule.dl_dst));
				}
				if ( ! rule.wildcard_nw_proto ) {
					match.setExact(MatchField.IP_PROTO, IpProtocol.of(rule.nw_proto));
				}

				if ( ! rule.wildcard_in_port ) {
					match.setExact(MatchField.IN_PORT, OFPort.of(rule.in_port));
				}

				if ( ! rule.wildcard_nw_proto ) {
					switch ( rule.nw_proto ) {
					case 0x800:
						if ( rule.wildcard_nw_src ) {
							if ( rule.nw_src_maskbits == 32 ) {
								match.setExact(MatchField.IPV4_SRC, IPv4Address.of(rule.nw_src_prefix));
							} else {
								int mask = (-1) << (32 - rule.nw_src_maskbits);
								match.setMasked(MatchField.IPV4_SRC, IPv4AddressWithMask.of(IPv4Address.of(rule.nw_src_prefix), IPv4Address.of(mask)));
							}
						}
						if ( rule.wildcard_nw_dst ) {
							if ( rule.nw_dst_maskbits == 32 ) {
								match.setExact(MatchField.IPV4_DST, IPv4Address.of(rule.nw_dst_prefix));
							} else {
								int mask = (-1) << (32 - rule.nw_dst_maskbits);
								match.setMasked(MatchField.IPV4_DST, IPv4AddressWithMask.of(IPv4Address.of(rule.nw_dst_prefix), IPv4Address.of(mask)));
							}
						}
						break;
					case 0x806:
						if ( rule.wildcard_nw_src ) {
							if ( rule.nw_src_maskbits == 32 ) {
								match.setExact(MatchField.ARP_SPA, IPv4Address.of(rule.nw_src_prefix));
							} else {
								int mask = (-1) << (32 - rule.nw_src_maskbits);
								match.setMasked(MatchField.ARP_SPA, IPv4AddressWithMask.of(IPv4Address.of(rule.nw_src_prefix), IPv4Address.of(mask)));
							}
						}
						if ( rule.wildcard_nw_dst ) {
							if ( rule.nw_dst_maskbits == 32 ) {
								match.setExact(MatchField.ARP_TPA, IPv4Address.of(rule.nw_dst_prefix));
							} else {
								int mask = (-1) << (32 - rule.nw_dst_maskbits);
								match.setMasked(MatchField.ARP_TPA, IPv4AddressWithMask.of(IPv4Address.of(rule.nw_dst_prefix), IPv4Address.of(mask)));
							}
						}
						break;
					}
				}
				if ( ! rule.wildcard_tp_src ) {
					match.setExact(MatchField.TCP_SRC, TransportPort.of(rule.tp_src));
				}
				if ( ! rule.wildcard_tp_dst ) {
					match.setExact(MatchField.TCP_DST, TransportPort.of(rule.tp_dst));
				}
				del
				.setMatch( match.build() )
				.setOutPort( OFPort.ANY )
				.setTableId( TableId.ALL );
			} catch ( UnsupportedOperationException u ) {
				// does nothing. Probably because setTableId().
			}
			sw.getConnection().write( del.build() );
		}
	}

	@Override
	public void addRule(FirewallRule rule) {

		// generate random ruleid for each newly created rule
		// may want to return to caller if useful
		// may want to check conflict
		rule.ruleid = rule.genID();

		synchronized( this.rules ) {
			int i = 0;
			// locate the position of the new rule in the sorted arraylist
			for (i = 0; i < this.rules.size(); i++) {
				if (this.rules.get(i).priority >= rule.priority)
					break;
			}
			// now, add rule to the list
			if (i <= this.rules.size()) {
				this.rules.add(i, rule);
			} else {
				this.rules.add(rule);
			}
		}

		Map<String, Object> entry = new HashMap<String, Object>();
		entry.put(COLUMN_RULEID, Integer.toString(rule.ruleid));
		entry.put(COLUMN_DPID, Long.toString(rule.dpid));
		entry.put(COLUMN_IN_PORT, Short.toString(rule.in_port));
		entry.put(COLUMN_DL_SRC, Long.toString(rule.dl_src));
		entry.put(COLUMN_DL_DST, Long.toString(rule.dl_dst));
		entry.put(COLUMN_DL_TYPE, Short.toString(rule.dl_type));
		entry.put(COLUMN_NW_SRC_PREFIX, Integer.toString(rule.nw_src_prefix));
		entry.put(COLUMN_NW_SRC_MASKBITS, Integer.toString(rule.nw_src_maskbits));
		entry.put(COLUMN_NW_DST_PREFIX, Integer.toString(rule.nw_dst_prefix));
		entry.put(COLUMN_NW_DST_MASKBITS, Integer.toString(rule.nw_dst_maskbits));
		entry.put(COLUMN_NW_PROTO, Short.toString(rule.nw_proto));
		entry.put(COLUMN_TP_SRC, Integer.toString(rule.tp_src));
		entry.put(COLUMN_TP_DST, Integer.toString(rule.tp_dst));
		entry.put(COLUMN_WILDCARD_DPID,
				Boolean.toString(rule.wildcard_dpid));
		entry.put(COLUMN_WILDCARD_IN_PORT,
				Boolean.toString(rule.wildcard_in_port));
		entry.put(COLUMN_WILDCARD_DL_SRC,
				Boolean.toString(rule.wildcard_dl_src));
		entry.put(COLUMN_WILDCARD_DL_DST,
				Boolean.toString(rule.wildcard_dl_dst));
		entry.put(COLUMN_WILDCARD_DL_TYPE,
				Boolean.toString(rule.wildcard_dl_type));
		entry.put(COLUMN_WILDCARD_NW_SRC,
				Boolean.toString(rule.wildcard_nw_src));
		entry.put(COLUMN_WILDCARD_NW_DST,
				Boolean.toString(rule.wildcard_nw_dst));
		entry.put(COLUMN_WILDCARD_NW_PROTO,
				Boolean.toString(rule.wildcard_nw_proto));
		entry.put(COLUMN_WILDCARD_TP_SRC,
				Boolean.toString(rule.wildcard_tp_src));
		entry.put(COLUMN_WILDCARD_TP_DST,
				Boolean.toString(rule.wildcard_tp_dst));
		entry.put(COLUMN_PRIORITY, Integer.toString(rule.priority));
		entry.put(COLUMN_ACTION, Integer.toString(rule.action.ordinal()));

		firewallStorage.getFirewallEntryTable().insertFirewallEntry(Integer.toString(rule.ruleid), entry);
		firewallStorage.insertDBEntry(storageInstance, dbName, collectionName, entry);

		purgeMatchingRulesFromSwitches(rule);
	}

	@Override
	public void deleteRule(int ruleid) {
		firewallStorage.getFirewallEntryTable().deleteFirewallEntry(Integer.toString(ruleid));
		firewallStorage.deleteDBEntry(storageInstance, dbName, collectionName, ruleid);

		synchronized ( this.rules ) {
			Iterator<FirewallRule> iter = this.rules.iterator();
			while (iter.hasNext()) {
				FirewallRule r = iter.next();
				if (r.ruleid == ruleid) {
					// found the rule, now remove it
					purgeMatchingRulesFromSwitches(r);

					iter.remove();
					break;
				}
			}	
		}
	}

	@Override
	public void clearRules() {
		synchronized ( this.rules ) {
			for ( FirewallRule rule : this.rules ) {
				firewallStorage.getFirewallEntryTable().deleteFirewallEntry(Integer.toString(rule.ruleid));
				firewallStorage.deleteDBEntry(storageInstance, dbName, collectionName, rule.ruleid);

				// found the rule, now remove it
				purgeMatchingRulesFromSwitches(rule);
			}
			this.rules.clear();
		}
	}

}
