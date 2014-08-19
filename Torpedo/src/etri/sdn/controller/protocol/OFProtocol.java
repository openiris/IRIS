package etri.sdn.controller.protocol;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.projectfloodlight.openflow.protocol.OFConfigFlags;
import org.projectfloodlight.openflow.protocol.OFDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFEchoReply;
import org.projectfloodlight.openflow.protocol.OFEchoRequest;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFFeaturesRequest;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFFlowModFlags;
import org.projectfloodlight.openflow.protocol.OFHello;
import org.projectfloodlight.openflow.protocol.OFHelloElem;
import org.projectfloodlight.openflow.protocol.OFHelloElemVersionbitmap;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPortConfig;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFPortReason;
import org.projectfloodlight.openflow.protocol.OFPortState;
import org.projectfloodlight.openflow.protocol.OFPortStatus;
import org.projectfloodlight.openflow.protocol.OFSetConfig;
import org.projectfloodlight.openflow.protocol.OFStatsReply;
import org.projectfloodlight.openflow.protocol.OFStatsRequest;
import org.projectfloodlight.openflow.protocol.OFStatsRequestFlags;
import org.projectfloodlight.openflow.protocol.OFStatsType;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionApplyActions;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.ArpOpcode;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.ICMPv4Code;
import org.projectfloodlight.openflow.types.ICMPv4Type;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpDscp;
import org.projectfloodlight.openflow.types.IpEcn;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.Masked;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFGroup;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.OFVlanVidMatch;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.TransportPort;
import org.projectfloodlight.openflow.types.U32;
import org.projectfloodlight.openflow.types.VlanPcp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;

/**
 * This class is for handling Openflow protocol handshaking.
 * @author bjlee
 *
 */
public class OFProtocol {
	
	private static final Logger logger = LoggerFactory.getLogger(OFProtocol.class);

	public static final String STR_IN_PORT = "in_port";
	public static final String STR_DL_DST = "dl_dst";
	public static final String STR_DL_SRC = "dl_src";
	public static final String STR_DL_TYPE = "dl_type";
	public static final String STR_DL_VLAN = "dl_vlan";
	public static final String STR_DL_VLAN_PCP = "dl_vpcp";
	public static final String STR_NW_DST = "nw_dst";
	public static final String STR_NW_SRC = "nw_src";
	public static final String STR_NW_PROTO = "nw_proto";
	public static final String STR_NW_TOS = "nw_tos";
	public static final String STR_TP_DST = "tp_dst";
	public static final String STR_TP_SRC = "tp_src";
	
	/**
	 * IOFSwitch to SwitchInformation map.
	 */
	private Map<IOFSwitch, SwitchInfo> switchInformations = 
			new ConcurrentHashMap<IOFSwitch, SwitchInfo>();
	
	/**
	 * Port Number to PortInformation map.
	 */
	private Map<IOFSwitch, Map<OFPort, OFPortDesc>> portInformations = 
			new ConcurrentHashMap<IOFSwitch, Map<OFPort, OFPortDesc>>();

	/**
	 * This field is used to exchange information with switch.
	 */
	private Map<IOFSwitch, Map<Long/*xid*/, Object>> responsesCache = 
			new ConcurrentHashMap<IOFSwitch, Map<Long, Object>>();
	
	/**
	 * This field used to maintain the list of hello-failed switches.
	 */
	private Set<String> helloFailedSwitches = new ConcurrentSkipListSet<>();

	/**
	 * reference to OFController object.
	 */
	private OFController controller;
	
	/**
	 * lock to synchronize.
	 */
	private Object portLock = new Object();

	/**
	 * Initialize OFProtocol object with OFController reference.
	 * @param controller	OFController object.
	 */
	public OFProtocol(OFController controller) {
		this.controller = controller;
	}

	/**
	 * Get reference to OFController object.
	 * @return	OFController object.
	 */
	public OFController getController() {
		return this.controller;
	}

	/**
	 * This method is used with two other companion methods: getResponseCacheItem and removeResponseCacheItem.
	 * @param sw	IOFSwitch object
	 * @param xid	transaction id
	 * @param item	object to set as a response for the transaction id
	 */
	public void setResponseCacheItem(IOFSwitch sw, long xid, Object item) {
		Map<Long, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			rcache = new ConcurrentHashMap<Long, Object>();
			this.responsesCache.put(sw, rcache);
		}
		rcache.put( xid, item );
	}

	/**
	 * This method is used with two other companion methods: setResponseCacheItem and removeResponseCacheItem.
	 * @param sw	IOFSwitch object
	 * @param xid	transaction id to get the response object
	 * @return		Object set by setResponseCacheItem as a response for the transaction id
	 */
	public Object getResponseCacheItem(IOFSwitch sw, long xid) {
		Map<Long, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			return null;
		}
		return rcache.get(xid);
	}

	/**
	 * This method is used with two other companion methods: setResponseCacheItem and getResponseCacheItem.
	 * @param sw	IOFSwitch object
	 * @param xid	transaction id to remove the response object
	 */
	public void removeResponseCacheItem(IOFSwitch sw, long xid) {
		Map<Long, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			return;
		}
		rcache.remove(xid);
	}

	public SwitchInfo getSwitchInformation(IOFSwitch sw) {
		return this.switchInformations.get(sw);
	}
	
	public void setSwitchInformation(IOFSwitch sw, OFDescStatsReply r) {
		SwitchInfo swinfo = this.getSwitchInformation(sw);
		if ( swinfo == null ) {
			swinfo = new SwitchInfo();
			this.switchInformations.put(sw, swinfo);
		}
		swinfo.setDescStatsReply(r);
	}
	
	public void setSwitchInformation(IOFSwitch sw, OFFeaturesReply r) {
		SwitchInfo swinfo = this.getSwitchInformation(sw);
		if ( swinfo == null ) {
			swinfo = new SwitchInfo();
			this.switchInformations.put(sw, swinfo);
		}
		swinfo.setFeaturesReply(r);
	}

	public void setPortInformation(IOFSwitch sw, OFPortDesc desc) {
		Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
		if ( inner == null ) {
			inner = new ConcurrentHashMap<OFPort, OFPortDesc>();
			portInformations.put(sw, inner);
		}
		inner.put( desc.getPortNo(), desc );
	}
	
	/**
	 * Get port information by the port number. 
	 * If none existent, null is returned.
	 * @param sw	Switch to retrieve the port information
	 * @param port	port number to retrieve the information
	 * @return		PortInformation object
	 */
	public OFPortDesc getPortInformation(IOFSwitch sw, OFPort port) {
		Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
		if ( inner == null ) {
			inner = new ConcurrentHashMap<OFPort, OFPortDesc>();
			portInformations.put(sw, inner);
		}
		return inner.get(port);
	}

	/**
	 * Get all PortInformation objects for the given switch
	 * @param sw	IOFSwitch object
	 * @return		Collection<PortInformation>
	 */
	public Collection<OFPortDesc> getPortInformations(IOFSwitch sw) {
		Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
		if ( inner != null ) {
			return inner.values();
		}
		return Collections.emptySet();
	}
	
	/**
	 * Get all the PortInformation objects for the given switch by datapath id
	 * @param datapathId	datapath id of the switch
	 * @return				Collection<PortInformation>
	 */
	public Collection<OFPortDesc> getPortInformations(long datapathId) {
		for ( IOFSwitch sw : portInformations.keySet() ) {
			if ( sw.getId() == datapathId ) {
				Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
				if ( inner != null ) {
					return inner.values();
				}
			}
		}
		return Collections.emptySet();
	}

	/**
	 * Remove a specific port information from the switch
	 * @param sw	IOFSwitch object
	 * @param pi	PortInformation object
	 */
	public void removePortInformation(IOFSwitch sw, OFPortDesc pi) {
		Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
		if ( inner != null ) {
			inner.remove(pi.getPortNo());
		}
	}

	/**
	 * This method should be called after port number is correctly set up.
	 * @param sw	IOFSwitch object
	 * @param name	name of the port
	 * @return		Null can be returned if the item that you looking for is non-existent
	 */
	public OFPortDesc getPortInformation(IOFSwitch sw, String name) {
		Map<OFPort, OFPortDesc> inner = portInformations.get(sw);
		for ( OFPortDesc i : inner.values() ) {
			if ( i.getName().equals(name) ) {
				return i;
			}
		}
		return null;
	}


	/**
	 * Callback called by underlying platform when a connection to a switch is established
	 * @param conn	Connection object
	 * @return		true if correctly handled (always)
	 */
	public boolean handleConnectedEvent(Connection conn) {
		InetSocketAddress peer = null;
		try {
			peer = (InetSocketAddress) conn.getClient().getRemoteAddress();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		if ( this.helloFailedSwitches.contains(peer.getHostString()) ) {
			OFHello hello = OFFactories.getFactory(OFVersion.OF_10).hello(Collections.<OFHelloElem>emptyList());
			conn.write(hello);
			
			this.helloFailedSwitches.remove( peer.getHostString() );
		} else {		
			OFHello hello = OFFactories.getFactory(OFVersion.OF_13).hello(Collections.<OFHelloElem>emptyList());
			conn.write(hello);
			
			logger.debug("hello={}", hello);
			
			this.helloFailedSwitches.add( peer.getHostString() );
		}
		return true;
	}

	/**
	 * Callback called by underlying platform when a new OFMessage is received for a connection
	 * @param conn		Connection object
	 * @param context	MessageContext object created for the message
	 * @param m			OFMessage object to handle
	 * @return			true of correctly handled, false otherwise
	 */
	public boolean process(Connection conn, MessageContext context, OFMessage m) {
		if ( !conn.isConnected() ) {
			return false;
		}
		
		IOFSwitch sw = conn.getSwitch();
		OFType t = m.getType();

		switch (t) {
		case HELLO:
			// if HELLO received, we send features request.
			try {
				logger.debug("GOT HELLO({}) from {}", m, conn.getClient().getRemoteAddress().toString());
			} catch (IOException e1) {
				return false;
			}
			
			// set the version number in the switch.
			if ( sw != null ) {
				sw.setVersion(m.getVersion());
			}
			
			// now the hello is successfully exchanged, so we remove the peer address 
			// from the helloFailedSwitches set. 
			try {
				InetSocketAddress peer = (InetSocketAddress) conn.getClient().getRemoteAddress();
				this.helloFailedSwitches.remove( peer.getHostString() );
			} catch (IOException e) {
				logger.debug("conn.getClient().getRemoteAddress() failed");
				e.printStackTrace();
				return false;
			}

			// send feature request message.
			OFFeaturesRequest freq = OFFactories.getFactory(m.getVersion()).featuresRequest();
			conn.write(freq);
			break;

		case ERROR:		
			logger.error("GET ERROR : {}", m.toString());
			return false;

		case ECHO_REQUEST:
			OFEchoReply.Builder builder = OFFactories.getFactory(m.getVersion()).buildEchoReply();
			builder
			.setXid(m.getXid())
			.setData( ((OFEchoRequest)m).getData() );
			conn.write( builder.build() );
			break;

		case FEATURES_REPLY:
			logger.debug("FEATURES_REPLY is recceived: {}", m.toString());
			
			if ( sw == null ) return false;

			synchronized ( portLock ) {
				OFFeaturesReply fr = (OFFeaturesReply) m;
				sw.setId( fr.getDatapathId().getLong() );

				this.setSwitchInformation(sw, fr);
				
				try { 
					List<OFPortDesc> ports = fr.getPorts();
					for ( OFPortDesc port: ports ) {
						setPortInformation(sw, port);
					}
				} catch ( UnsupportedOperationException e ) {
					// port list is not retrieved.
					
					// send port desc request message to retrieve all port list.
					// preq might be null if m.getVersion() == 0x01. But it's OK because
					// conn.write ignore null preq.
					OFPortDescStatsRequest preq = 
							OFFactories
							.getFactory(m.getVersion())
							.portDescStatsRequest(Collections.<OFStatsRequestFlags>emptySet());
					conn.write(preq);
				}
			}

			if ( m.getVersion() != OFVersion.OF_10 ) {
				// set configuration parameters in the switch.
				// The switch does not reply to a request to set the configuration.
				// The flags indicate whether IP fragments should be treated normally, dropped, or reassembled. [jshin]
				
				OFSetConfig.Builder sc = OFFactories.getFactory(m.getVersion()).buildSetConfig();
				sc
				.setFlags(EnumSet.<OFConfigFlags>of(OFConfigFlags.FRAG_NORMAL))
				.setMissSendLen(0xffff);
				conn.write(sc.build());
			}

			OFDescStatsRequest req = 
					OFFactories.getFactory(m.getVersion()).descStatsRequest(EnumSet.noneOf(OFStatsRequestFlags.class));
			conn.write(req);

			try {
				// send flow_mod to process table miss packets [jshin]
				OFInstructionApplyActions.Builder instruction = 
						OFFactories.getFactory(m.getVersion()).instructions().buildApplyActions();
				
				List<OFAction> actions = new LinkedList<OFAction>();
				OFActionOutput.Builder action = OFFactories.getFactory(m.getVersion()).actions().buildOutput();
				action.setPort(OFPort.CONTROLLER).setMaxLen(0xffff);
				actions.add( action.build() );
				
				instruction.setActions(actions);
				List<OFInstruction> instructions = new LinkedList<OFInstruction>();
				instructions.add( instruction.build() );
				
				OFFlowAdd.Builder fm = OFFactories.getFactory(m.getVersion()).buildFlowAdd();
				fm
				.setTableId(TableId.ZERO)
				.setIdleTimeout(0)
				.setHardTimeout(0)
				.setPriority(0)
				.setBufferId(OFBufferId.NO_BUFFER)
				.setOutGroup(OFGroup.ANY)
				.setOutPort(OFPort.ANY)
				.setMatch(OFFactories.getFactory(m.getVersion()).matchWildcardAll())
				.setInstructions(instructions)
				.setFlags(EnumSet.of(OFFlowModFlags.SEND_FLOW_REM));
				
				conn.write(fm.build());
				
			} catch ( UnsupportedOperationException e ) {
				// we can just ignore this exception.
			}

			// now the handshaking is fully done.
			// therefore, we can safely register the switch object
			// into the switches map. This map is used heavily by
			// link discovery module.
			//			Logger.stdout("adding a switch with id = " + conn.getSwitch().getId());
			this.getController().addSwitch( conn.getSwitch().getId(), conn.getSwitch() );

			this.deliverFeaturesReply( conn.getSwitch(), m.getXid(), (OFFeaturesReply) m );

			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}

			break;

		case PORT_STATUS:
			if ( sw == null ) return false;

			OFPortStatus ps = (OFPortStatus) m;
			OFPortDesc phyport = (OFPortDesc) ps.getDesc();
			if ( ps.getReason() == OFPortReason.DELETE ) {
				removePortInformation( sw, phyport );
			} else if ( ps.getReason() == OFPortReason.MODIFY ) {
				removePortInformation( sw, phyport );
				setPortInformation( sw, phyport );
			} else { /* ps.getReason() == OFPortReason.ADD */ 
				setPortInformation( sw, phyport );
			}

			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			break;

		case STATS_REPLY:
			if ( sw == null ) return false;

			OFStatsReply stat = (OFStatsReply) m;
			if ( stat.getStatsType() == OFStatsType.PORT_DESC ) {
				OFPortDescStatsReply portDesc = (OFPortDescStatsReply) m;
				synchronized ( portLock ) {
					List<OFPortDesc> ports = portDesc.getEntries();
					for ( OFPortDesc port: ports ) {
						setPortInformation(sw, port);
					}
				}
				
				deliverSwitchStatistics( sw, portDesc );
			} else if ( stat.getStatsType() == OFStatsType.DESC ) {
				this.setSwitchInformation(sw, (OFDescStatsReply) stat);
			} else {
				deliverSwitchStatistics( sw, stat );
			}
			break;

		case PACKET_IN:
			if ( sw.getStringId() == null ) {
				// FEATURES_REPLY is not received.
				return false;
			}

			if ( !getController().handlePacketIn(conn, context, m) ) {
				return false;
			}
			break;

		default:
			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Modules that use IOFSwitch objects use this method to request statistics to the switch.
	 * @param req OFStatisticsRequest object.
	 */
	public List<OFStatsReply> getSwitchStatistics(IOFSwitch sw, @SuppressWarnings("rawtypes") OFStatsRequest req) {
		List<OFStatsReply> response = new LinkedList<OFStatsReply>();
		long xid = req.getXid();

		this.setResponseCacheItem(sw, xid, response);
		if ( sw.getConnection() != null ) {
			sw.getConnection().write(req);
			synchronized ( response ) {
				try {
					response.wait(1000);
				} catch ( InterruptedException e ) {
					// does nothing.
				} finally {
					this.removeResponseCacheItem(sw, xid);
				}
			}
			return response;
		}
		return null;
	}

	private void deliverSwitchStatistics(IOFSwitch sw, OFStatsReply m) {
		Object response = getResponseCacheItem(sw, m.getXid());
		if ( response == null ) {
			return;
		}
		if ( response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFStatsReply> rl = (List<OFStatsReply>) response;
			synchronized ( response ) {
				rl.add( m );
				response.notifyAll();
			}
		}
	}

	/**
	 * Get OFFeaturesReply for the given switch. 
	 * @param sw	IOFSwitch object
	 * @return		OFFeaturesReply object
	 */
	public OFFeaturesReply getFeaturesReply(IOFSwitch sw) {
		OFFeaturesRequest req = OFFactories.getFactory(sw.getVersion()).featuresRequest();
		List<OFFeaturesReply> response = new LinkedList<OFFeaturesReply>();
		this.setResponseCacheItem(sw, req.getXid(), response);
		sw.getConnection().write( req );
		synchronized ( response ) {
			try { 
				response.wait(1000);
			} catch ( InterruptedException e ) {
				// does nothing
			} finally {
				this.removeResponseCacheItem(sw, req.getXid());
			}
		}
		if ( response.isEmpty() ) {
			return null;
		}
		else {
			return response.remove(0);
		}
	}

	private void deliverFeaturesReply(IOFSwitch sw, long xid, OFFeaturesReply reply) {
		Object response = this.getResponseCacheItem(sw, xid);
		if ( response == null ) {
			return;
		}
		if ( response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFFeaturesReply> rl = (List<OFFeaturesReply>) response;
			synchronized ( response ) {
				rl.add( reply );
				response.notifyAll();
			}
		}
	}

	/**
	 * Get all the enabled ports
	 * @param sw	IOFSwitch object
	 * @return		Collection<OFPortDesc>
	 */
	public Collection<OFPortDesc> getEnabledPorts(IOFSwitch sw) {		
		List<OFPortDesc> result = new ArrayList<OFPortDesc>();
		Collection<OFPortDesc> allPorts = this.getPortInformations(sw);
		if ( allPorts == null ) return null;

		for (OFPortDesc port: allPorts) {
			if (portEnabled(port)) {
				result.add(port);
			}
		}
		return result;
	}

	/**
	 * Get all the enabled port numbers
	 * @param sw	IOFSwitch object
	 * @return		Collection<Integer>
	 */
	public Collection<OFPort> getEnabledPortNumbers(IOFSwitch sw) {
		List<OFPort> result = new ArrayList<OFPort>();
		Collection<OFPortDesc> allPorts = this.getPortInformations(sw);
		if ( allPorts == null ) return null;

		for (OFPortDesc port: allPorts) {
			if (portEnabled(port)) {
				result.add(port.getPortNo());
			}
		}
		return result;
	}

	/**
	 * Check if the given port is enabled
	 * @param port	OFPortDesc object
	 * @return		true if enabled, false otherwise
	 */
	public boolean portEnabled(OFPortDesc port) {
		if ( port == null ) 
			return false;
		if (port.getConfig().contains(OFPortConfig.PORT_DOWN))
			return false;
		Set<OFPortState> states = port.getState();
		if (states.contains(OFPortState.LINK_DOWN) || states.contains(OFPortState.BLOCKED) )
			return false;
		// Port STP state doesn't work with multiple VLANs, so ignore it for now
		//if ((port.getState() & OFPortState.STP_MASK.getValue()) == OFPortState.STP_BLOCK.getValue())
		//    return false;
		return true;
	}

	/**
	 * Check if a given port is enabled
	 * @param sw		IOFSwitch object to which the port is bound
	 * @param port		port number (short)
	 * @return			true if enabled, false otherwise
	 */
	public boolean portEnabled(IOFSwitch sw, OFPort port) {
		OFPortDesc desc = this.getPortInformation(sw, port);
		if ( desc == null ) {
			return false;
		}
		return portEnabled(desc);
	}

	/**
	 * Create an OFMatch object from the packet-in data. 
	 * This method is called by doDropFlow and doForwardFlow of Forwarding module, and processPacketInMesssage of LearningMac module.
	 * @param sw			IOFSwitch object
	 * @param packetIn	packet-in data array
	 * @param inputPort		input port (short)
	 * @return				OFMatch object
	 */
	public Match loadOFMatchFromPacket(IOFSwitch sw, OFPacketIn packetIn, OFPort inputPort, boolean l2only) {

		Match.Builder ret = OFFactories.getFactory(packetIn.getVersion()).buildMatch();

		short scratch = 0;
		int transportOffset = 34;
		ByteBuffer packetDataBB = ByteBuffer.wrap(packetIn.getData());
		int limit = packetDataBB.limit();

		ret.setExact(MatchField.IN_PORT, inputPort);

		assert (limit >= 14);
		
		// dl dst
		byte[] eth_dst = new byte[6];
		packetDataBB.get(eth_dst);
		ret.setExact(MatchField.ETH_DST, MacAddress.of(eth_dst));

		// dl src
		byte[] eth_src = new byte[6];
		packetDataBB.get(eth_src);
		ret.setExact(MatchField.ETH_SRC, MacAddress.of(eth_src));

		// dl type
		short data_layer_type = packetDataBB.getShort();
		ret.setExact(MatchField.ETH_TYPE, EthType.of(data_layer_type));

		// has vlan
		if ( data_layer_type == (short) 0x8100 ) {
			scratch = packetDataBB.getShort();
			if ( (0xfff & scratch) != 0 ) {
				ret.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlan((short)(0xfff & scratch)));
				ret.setExact(MatchField.VLAN_PCP, VlanPcp.of((byte)((0xe000 & scratch) >> 13)));
			}
		} 

		if ( l2only ) {
			return ret.build();
		}
		
		byte network_protocol = 0;

		switch (data_layer_type) {
		case 0x0800:							// IPv4
			// check packet length
			scratch = packetDataBB.get();
			scratch = (short) (0xf & scratch);
			transportOffset = (packetDataBB.position() - 1) + (scratch * 4);
			
			// nw tos (dscp & ecn)
			scratch = packetDataBB.get();
			ret.setExact(MatchField.IP_DSCP, IpDscp.of((byte)((0b11111100 & scratch) >> 2)));
			try { 
				ret.setExact(MatchField.IP_ECN, IpEcn.of((byte)(0b00000011 & scratch)));
			} catch (UnsupportedOperationException u) {
				// does nothing
			}

			// nw protocol
			packetDataBB.position(packetDataBB.position() + 7);
			ret.setExact(MatchField.IP_PROTO, IpProtocol.of(packetDataBB.get()));

			// nw src & dst
			packetDataBB.position(packetDataBB.position() + 2);
			ret.setExact(MatchField.IPV4_SRC, IPv4Address.of(packetDataBB.getInt()));
			ret.setExact(MatchField.IPV4_DST, IPv4Address.of(packetDataBB.getInt()));
			
			packetDataBB.position(transportOffset);
			break;
			
		case 0x0806:							// ARP
			// arp
			int arpPos = packetDataBB.position();
			// opcode
			scratch = packetDataBB.getShort(arpPos + 6);
			
			ret.setExact(MatchField.ARP_OP, ArpOpcode.of(network_protocol = (byte) (0xff & scratch)));

			scratch = packetDataBB.getShort(arpPos + 2);
			// if ipv4 and addr len is 4
			if (scratch == 0x800 && packetDataBB.get(arpPos + 5) == 4) {
				ret.setExact(MatchField.ARP_SPA, IPv4Address.of(packetDataBB.getInt(arpPos + 14)));
				ret.setExact(MatchField.ARP_TPA, IPv4Address.of(packetDataBB.getInt(arpPos + 24)));
			}
			break;
		default:
			break;
		}

		switch (network_protocol) {
		case 0x01:
			// icmp
//			ret.setExact(MatchField.ICMPV4_TYPE, ICMPv4Type.of(packetDataBB.get()));
//			ret.setExact(MatchField.ICMPV4_CODE, ICMPv4Code.of(packetDataBB.get()));
			break;
		case 0x06:
			// tcp
			ret.setExact(MatchField.TCP_SRC, TransportPort.of(0x0000ffff & packetDataBB.getShort()));
			ret.setExact(MatchField.TCP_DST, TransportPort.of(0x0000ffff & packetDataBB.getShort()));
			break;
		case 0x11:
			// udp
			ret.setExact(MatchField.UDP_SRC, TransportPort.of(0x0000ffff & packetDataBB.getShort()));
			ret.setExact(MatchField.UDP_DST, TransportPort.of(0x0000ffff & packetDataBB.getShort()));
			break;
		default:
			break;
		}

		return ret.build();
	}

	/**
	 * Set the networkSource or networkDestionation address and their wildcards
	 * from the CIDR string
	 * 
	 * @param cidr
	 *            "192.168.0.0/16" or "172.16.1.5"
	 * @param which
	 *            one of STR_NW_DST or STR_NW_SRC
	 * @throws IllegalArgumentException
	 */
	private void setFromCIDR(Match.Builder match, String cidr, String which)
			throws IllegalArgumentException {
		String values[] = cidr.split("/");
		String[] ip_str = values[0].split("\\.");
		int ip = 0;
		ip += Integer.valueOf(ip_str[0]) << 24;
		ip += Integer.valueOf(ip_str[1]) << 16;
		ip += Integer.valueOf(ip_str[2]) << 8;
		ip += Integer.valueOf(ip_str[3]);
		int prefix = 32; // all bits are fixed, by default

		if (values.length >= 2)
			prefix = Integer.valueOf(values[1]);
		
		int mask = 0x80000000 >> (prefix-1);
		if ( which.equals(STR_NW_DST) )
			match.setMasked(MatchField.IPV4_DST, Masked.of(IPv4Address.of(ip), IPv4Address.of(mask)));
		else if ( which.equals(STR_NW_SRC) ) 
			match.setMasked(MatchField.IPV4_SRC, Masked.of(IPv4Address.of(ip), IPv4Address.of(mask)));
	}

	/**
	 * Set this OFMatch's parameters based on a comma-separated key=value pair
	 * dpctl-style string, e.g., from the output of OFMatch.toString() <br>
	 * <br>
	 * Supported keys/values include <br>
	 * <br>
	 * <TABLE>
	 * <TR>
	 * <TD>KEY(s)
	 * <TD>VALUE
	 * </TR>
	 * <TR>
	 * <TD>"in_port","input_port"
	 * <TD>integer
	 * </TR>
	 * <TR>
	 * <TD>"dl_src","eth_src", "dl_dst","eth_dst"
	 * <TD>hex-string
	 * </TR>
	 * <TR>
	 * <TD>"dl_type", "dl_vlan", "dl_vlan_pcp"
	 * <TD>integer
	 * </TR>
	 * <TR>
	 * <TD>"nw_src", "nw_dst", "ip_src", "ip_dst"
	 * <TD>CIDR-style netmask
	 * </TR>
	 * <TR>
	 * <TD>"tp_src","tp_dst"
	 * <TD>integer (max 64k)
	 * </TR>
	 * </TABLE>
	 * <br>
	 * The CIDR-style netmasks assume 32 netmask if none given, so:
	 * "128.8.128.118/32" is the same as "128.8.128.118"
	 * 
	 * @param match
	 *            a key=value comma separated string, e.g.
	 *            "in_port=5,ip_dst=192.168.0.0/16,tp_src=80"
	 * @return OFMatch object
	 * @throws IllegalArgumentException on unexpected key or value
	 */
	public Match loadOFMatchFromString(IOFSwitch sw, String match)
			throws IllegalArgumentException {
		Match.Builder ret = OFFactories.getFactory(sw.getVersion()).buildMatch();

		if (match.equals("") || match.equalsIgnoreCase("any")
				|| match.equalsIgnoreCase("all") || match.equals("[]"))
			match = "OFMatch[]";
		String[] tokens = match.split("[\\[,\\]]");
		String[] values;
		int initArg = 0;
		if (tokens[0].equals("OFMatch"))
			initArg = 1;
		
		IpProtocol nw_proto = null;

		int i;
		for (i = initArg; i < tokens.length; i++) {
			values = tokens[i].split("=");
			if (values.length != 2)
				throw new IllegalArgumentException("Token " + tokens[i]
						+ " does not have form 'key=value' parsing " + match);
			values[0] = values[0].toLowerCase(); // try to make this case insens
			if (values[0].equals(STR_IN_PORT) || values[0].equals("input_port")) {
				ret.setExact(MatchField.IN_PORT, OFPort.of(Integer.valueOf(values[1])));
			} else if (values[0].equals(STR_DL_DST)
					|| values[0].equals("eth_dst")) {
				ret.setExact(MatchField.ETH_DST, MacAddress.of(values[1]));
			} else if (values[0].equals(STR_DL_SRC)
					|| values[0].equals("eth_src")) {
				ret.setExact(MatchField.ETH_SRC, MacAddress.of(values[1]));
			} else if (values[0].equals(STR_DL_TYPE)
					|| values[0].equals("eth_type")) {
				if (values[1].startsWith("0x"))
					ret.setExact(MatchField.ETH_TYPE, EthType.of(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
				else
					ret.setExact(MatchField.ETH_TYPE, EthType.of(Integer.valueOf(values[1])));
			} else if (values[0].equals(STR_DL_VLAN)) {
				if (values[1].contains("0x")) {
					ret.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlan(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
				} else {
					ret.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlan(Integer.valueOf(values[1])));
				}
			} else if (values[0].equals(STR_DL_VLAN_PCP)) {
				ret.setExact(MatchField.VLAN_PCP, VlanPcp.of(Byte.valueOf(values[1])));
			} else if (values[0].equals(STR_NW_DST)
					|| values[0].equals("ip_dst"))
				setFromCIDR(ret, values[1], STR_NW_DST);
			else if (values[0].equals(STR_NW_SRC) || values[0].equals("ip_src"))
				setFromCIDR(ret, values[1], STR_NW_SRC);
			else if (values[0].equals(STR_NW_PROTO)) {
				ret.setExact(MatchField.IP_PROTO, nw_proto = IpProtocol.of(Short.valueOf(values[1])));
			} else if (values[0].equals(STR_NW_TOS)) {
				byte scratch = Byte.valueOf(values[1]);
				ret.setExact(MatchField.IP_DSCP, IpDscp.of((byte)((0b11111100 & scratch) >> 2)));
				ret.setExact(MatchField.IP_ECN, IpEcn.of((byte)(0b00000011 & scratch)));
			} else if (values[0].equals(STR_TP_DST)) {
				if ( nw_proto != null ) {
					if ( nw_proto == IpProtocol.ICMP ) {
						ret.setExact(MatchField.ICMPV4_CODE, ICMPv4Code.of(Short.valueOf(values[1])));						
					} else if ( nw_proto == IpProtocol.TCP ) {
						ret.setExact(MatchField.TCP_DST, TransportPort.of(Short.valueOf(values[1])));
					} else if ( nw_proto == IpProtocol.UDP ) {
						ret.setExact(MatchField.UDP_DST, TransportPort.of(Short.valueOf(values[1])));
					}
				}
			} else if (values[0].equals(STR_TP_SRC)) {
				if ( nw_proto != null ) {
					if ( nw_proto == IpProtocol.ICMP ) {
						ret.setExact(MatchField.ICMPV4_TYPE, ICMPv4Type.of(Short.valueOf(values[1])));						
					} else if ( nw_proto == IpProtocol.TCP ) {
						ret.setExact(MatchField.TCP_SRC, TransportPort.of(Short.valueOf(values[1])));
					} else if ( nw_proto == IpProtocol.UDP ) {
						ret.setExact(MatchField.UDP_SRC, TransportPort.of(Short.valueOf(values[1])));
					}
				}				
			} else
				throw new IllegalArgumentException("unknown token " + tokens[i]
						+ " parsing " + match);
		}
		return ret.build();
	}
}
