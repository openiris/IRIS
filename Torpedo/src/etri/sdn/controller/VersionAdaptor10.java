package etri.sdn.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFMessage;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.Logger;

import org.openflow.protocol.ver1_0.types.*;
import org.openflow.protocol.ver1_0.messages.*;
import org.openflow.util.HexString;
import org.openflow.util.U16;
import org.openflow.util.U8;

public class VersionAdaptor10 extends VersionAdaptor {
	
	public static final byte VERSION = 0x01;						// 1.0
	
	private Object portLock = new Object();
	
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
	
	/*
	 * Per-switch data structures
	 */
	private Map<IOFSwitch, Long> datapathIdMap = new ConcurrentHashMap<IOFSwitch, Long>();
	private Map<IOFSwitch, String> stringIdMap = new ConcurrentHashMap<IOFSwitch, String>();
	private Map<IOFSwitch, Integer> capabilitiesMap = new ConcurrentHashMap<IOFSwitch, Integer>();
	private Map<IOFSwitch, Integer> buffersMap = new ConcurrentHashMap<IOFSwitch, Integer>();
	private Map<IOFSwitch, Integer> actionsMap = new ConcurrentHashMap<IOFSwitch, Integer>();
	private Map<IOFSwitch, Byte> tablesMap = new ConcurrentHashMap<IOFSwitch, Byte>();
	private Map<IOFSwitch, OFStatisticsDescReply> descriptionsMap = new ConcurrentHashMap<IOFSwitch, OFStatisticsDescReply>();
	
	private Map<IOFSwitch, Map<Short, OFPortDesc>> portsByNumber = new ConcurrentHashMap<IOFSwitch, Map<Short, OFPortDesc>>();
	private Map<IOFSwitch, Map<String, OFPortDesc>> portsByName = new ConcurrentHashMap<IOFSwitch, Map<String, OFPortDesc>>();
	
	/**
	 * This field is used to exchange information with switch.
	 */
	private Map<IOFSwitch, Map<Integer/*xid*/, Object>> responsesCache = new ConcurrentHashMap<IOFSwitch, Map<Integer, Object>>();
	
	public VersionAdaptor10(OFController controller) {
		super(controller);
	}

	public static byte getVersion() {
		return VERSION;
	}
	
	public void setId(IOFSwitch sw, long id) {
		this.datapathIdMap.put(sw, id);
		this.stringIdMap.put(sw, HexString.toHexString(id));
	}
	
	public long getId(IOFSwitch sw) {
		Long r = this.datapathIdMap.get(sw);
		if ( r == null ) {
			return -1;
		}
		return r;
	}
	
	public String getStringId(IOFSwitch sw) {
		return this.stringIdMap.get(sw);
	}
	
	public void setCapabilities(IOFSwitch sw, int capabilities) {
		this.capabilitiesMap.put(sw, capabilities);
	}
	
	public int getCapabilities(IOFSwitch sw) {
		Integer r = this.capabilitiesMap.get(sw);
		if ( r == null ) {
			return -1;
		}
		return r;
	}

	public void setBuffers(IOFSwitch sw, int buffers) {
		this.buffersMap.put(sw, buffers);
	}
	
	public int getBuffers(IOFSwitch sw) {
		Integer r = this.buffersMap.get(sw);
		if ( r == null ) {
			return -1;
		}
		return r;
	}
	
	public void setActions(IOFSwitch sw, int actions) {
		this.actionsMap.put(sw, actions);
	}
	
	public int getActions(IOFSwitch sw) {
		Integer r = this.actionsMap.get(sw);
		if ( r == null ) {
			return -1;
		}
		return r;
	}
	
	public void setTables(IOFSwitch sw, byte tables) {
		this.tablesMap.put(sw, tables);
	}
	
	public byte getTables(IOFSwitch sw) {
		Byte r = this.tablesMap.get(sw);
		if ( r == null ) {
			return -1;
		}
		return r;
	}
	
	public void setDescription(IOFSwitch sw, OFStatisticsDescReply r) {
		this.descriptionsMap.put(sw, r);
	}
	
	public OFStatisticsDescReply getDescription(IOFSwitch sw) {
		return this.descriptionsMap.get(sw);
	}
	
	public Collection<OFPortDesc> getPorts(IOFSwitch sw) {
		Map<Short, OFPortDesc> sm = portsByNumber.get(sw);
		if ( sm == null ) {
			return Collections.emptySet();
		}
		return sm.values();
	}
	
	public OFPortDesc getPort(IOFSwitch sw, short portNum) {
		Map<Short, OFPortDesc> sm = portsByNumber.get(sw);
		if ( sm == null ) {
			return null;
		}
		return sm.get(portNum);
	}
	
	public void setPort(IOFSwitch sw, OFPortDesc portDesc) {
		Map<Short, OFPortDesc> sm = portsByNumber.get(sw);
		if ( sm == null ) {
			sm = new ConcurrentHashMap<Short, OFPortDesc>();
			portsByNumber.put(sw, sm);
		}
		Map<String, OFPortDesc> rm = portsByName.get(sw);
		if ( rm == null ) {
			rm = new ConcurrentHashMap<String, OFPortDesc>();
			portsByName.put(sw, rm);
		}
		
		sm.put(portDesc.getPort(), portDesc);
		rm.put(new String(portDesc.getName()), portDesc);
	}
	
	public void deletePort(IOFSwitch sw, short portNumber) {
		Map<Short, OFPortDesc> sm = portsByNumber.get(sw);
		if ( sm == null ) {
			return;
		}
		Map<String, OFPortDesc> rm = portsByName.get(sw);
		if ( rm == null ) {
			return;
		}
		
		rm.remove(new String(sm.get(portNumber).getName()));
		sm.remove(portNumber);
	}
	
	@Override
	public boolean handleConnectedEvent(Connection conn) {
		// when we are connected, we send a HELLO.
		OFHello hello = (OFHello) OFMessageType.HELLO.newInstance();
		hello.setVersion((byte)0x01);
		conn.write(hello);
		return true;
	}

	@Override
	public boolean process(Connection conn, MessageContext context, OFMessage m) {
		OFMessageType t = OFMessageType.valueOf(m.getTypeByte());

		switch (t) {
		case PACKET_IN:					
			if ( ! getController().handlePacketIn(conn, context, m) ) {
				return false;
			}
			break;
		case HELLO:
			// if HELLO received, we send features request.
			try {
				System.err.println("GOT HELLO from " + conn.getClient().getRemoteAddress().toString());
			} catch (IOException e1) {
				return false;
			}
			// need to send feature request message.
			OFFeaturesRequest freq = (OFFeaturesRequest) OFMessageType.FEATURES_REQUEST.newInstance();
			conn.write(freq);
			break;
		case ERROR:
			Logger.stderr("GET ERROR : " + new String(((OFError)m).getData()));
			return false;
		case ECHO_REQUEST:
			// for echo request, we send a echo message with the same XID.
			Logger.debug("ECHO_REQUEST is received");
			OFEchoReply reply = (OFEchoReply) OFMessageType.ECHO_REPLY.newInstance();
			reply.setXid(m.getXid());
			conn.write(reply);
			break;
		case FEATURES_REPLY:
			// we should do basic processing on the reply, and pass the object
			// to the controller.
			
			IOFSwitch sw = conn.getSwitch();
			if ( sw == null ) {
				return false;
			}
			
			synchronized ( portLock ) {
				OFFeaturesReply fr = (OFFeaturesReply) m;
				sw.setId(fr.getDatapathId());
				this.setId(sw, fr.getDatapathId());
				this.setCapabilities(sw, fr.getCapabilities());
				this.setBuffers(sw, fr.getNBuffers());
				this.setActions(sw, fr.getActions());
				this.setTables(sw, fr.getNTables());
				
				// now we must add port information from the features-reply msg.
				List<OFPortDesc> ports = fr.getPorts();
				for (OFPortDesc port : ports ) {
					setPort(sw, port);
				}
			}
			
			if ( getDescription(sw) == null ) {
				OFStatisticsRequest req = 
					(OFStatisticsRequest) OFStatisticsType.DESC.newInstance(OFMessageType.STATISTICS_REQUEST);
				req.setXid(conn.getSwitch().getNextTransactionId());
				conn.write(req);
			}
			
			// now the handshaking is fully done.
			// therefore, we can safely register the switch object
			// into the switches map. This map is used heavily by
			// link discovery module.
//			Logger.stdout("adding a switch with id = " + conn.getSwitch().getId());
			getController().addSwitch( conn.getSwitch().getId(), conn.getSwitch() );
			
			deliverFeaturesReply( conn.getSwitch(), m.getXid(), (OFFeaturesReply) m );

			if ( ! getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			
			break;
			
		case PORT_STATUS:
			
			OFPortStatus ps = (OFPortStatus) m;
			OFPortDesc phyport = ps.getDesc();
			if ( ps.getReason() == OFPortReason.OFPPR_DELETE.ordinal() ) {
				deletePort( conn.getSwitch(), phyport.getPort() );
			}
			else if ( ps.getReason() == OFPortReason.OFPPR_MODIFY.ordinal() ) {
				deletePort(conn.getSwitch(), phyport.getPort() );
				setPort(conn.getSwitch(), phyport);
			}
			else { /*ps.getReason() == OFPortReason.OFPPR_ADD.ordinal() */ 
				setPort(conn.getSwitch(), phyport);
			}
			
			if ( ! getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			
			break;
			
		case STATISTICS_REPLY:
			
			OFStatisticsReply stat = (OFStatisticsReply) m;

			if ( stat.getStatisticsType() == OFStatisticsType.DESC ) {
				setDescription(conn.getSwitch(), (OFStatisticsDescReply) stat );
			}
			else {
				deliverSwitchStatistics( conn.getSwitch(), stat );
			}
			
			break;
			
		default:
			if ( ! getController().handleGeneric(conn, context, m) ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Modules that use IOFSwitch objects use this method to request statistics 
	 * to the switch.
	 * @param req OFStatisticsRequest object.
	 */
	public List<OFStatisticsReply> getSwitchStatistics(IOFSwitch sw, OFStatisticsRequest req) {
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			rcache = new ConcurrentHashMap<Integer, Object>();
			this.responsesCache.put(sw, rcache);
		}
		
		req.setXid( sw.getNextTransactionId() );
		
		List<OFStatisticsReply> response = new LinkedList<OFStatisticsReply>();
		int xid = req.getXid();
		rcache.put( xid, response );
//		System.out.println("xid -- " + xid);
		sw.getConnection().write(req);
		synchronized ( response ) {
			try {
				response.wait(1000);		// wait for 3 second
			} catch (InterruptedException e) {
				// does nothing.
			} finally {
				this.responsesCache.remove( xid );
			}
		}
//		System.out.println("a -- " + response.size());
		return response;
	}
	
	public void deliverSwitchStatistics(IOFSwitch sw, OFStatisticsReply m) {
//		System.out.println("delivering..." + m.toString());
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
//			System.out.println("returning...");
			return;
		}
		Object response = rcache.get( m.getXid() );
		if ( response != null && response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFStatisticsReply> rl = (List<OFStatisticsReply>) response;
			synchronized ( response ) {
				rl.add( m );
//				System.out.println("added...");
				response.notifyAll();
			}
		}
	}
	
	public OFFeaturesReply getFeaturesReply(IOFSwitch sw) {
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			rcache = new ConcurrentHashMap<Integer, Object>();
			this.responsesCache.put(sw, rcache);
		}
		OFFeaturesRequest req = new OFFeaturesRequest();
		req.setXid( sw.getNextTransactionId() );
		List<OFFeaturesReply> response = new LinkedList<OFFeaturesReply>();
		rcache.put( req.getXid(), response );
		sw.getConnection().write( req );
		synchronized( response ) {
			try { 
				response.wait(1000);				// wait for 3 seconds
			} catch ( InterruptedException e ) {
				// does nothing
			} finally {
				rcache.remove( req.getXid() );
			}
		}
		if ( response.isEmpty() ) {
			return null;
		}
		else {
			return response.remove(0);
		}
	}
	
	public void deliverFeaturesReply(IOFSwitch sw, int xid, OFFeaturesReply reply) {
//		System.out.println("delivering...");
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			return;
		}
		Object response = rcache.get( xid );
		if ( response != null && response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFFeaturesReply> rl = (List<OFFeaturesReply>) response;
			synchronized ( response ) {
				rl.add( reply );
				response.notifyAll();
			}
		}
	}
	
	public Collection<OFPortDesc> getEnabledPorts(IOFSwitch sw) {
		List<OFPortDesc> result = new ArrayList<OFPortDesc>();
		Map<Short, OFPortDesc> map = portsByNumber.get(sw);
		if ( map == null ) return null;
		
		for (OFPortDesc port : map.values()) {
			if (portEnabled(port)) {
				result.add(port);
			}
		}
		return result;
	}
	
	public Collection<Short> getEnabledPortNumbers(IOFSwitch sw) {
		List<Short> result = new ArrayList<Short>();
		Map<Short, OFPortDesc> map = portsByNumber.get(sw);
		if ( map == null ) return null;
		
		for (OFPortDesc port : map.values()) {
			if (portEnabled(port)) {
				result.add(port.getPort());
			}
		}
		return result;
	}
	
	public boolean portEnabled(OFPortDesc port) {
		if (port == null)
			return false;
		if ((port.getConfig() & OFPortConfig.OFPPC_PORT_DOWN) > 0)
			return false;
		if ((port.getState() & OFPortState.OFPPS_LINK_DOWN) > 0)
			return false;
		// Port STP state doesn't work with multiple VLANs, so ignore it for now
		//if ((port.getState() & OFPortState.OFPPS_STP_MASK.getValue()) == OFPortState.OFPPS_STP_BLOCK.getValue())
		//    return false;
		return true;
	}
	
	public boolean portEnabled(IOFSwitch sw, short port) {
		Map<Short, OFPortDesc> map = portsByNumber.get(sw);
		if ( map == null ) {
			return false;
		}
		OFPortDesc pdesc = map.get(port);
		if ( pdesc == null ) {
			return false;
		}
		return portEnabled(pdesc);
	}
	
	public OFMatch loadOFMatchFromPacket(byte[] packetData, short inputPort) {
		OFMatch ret = new OFMatch();
		
		short scratch;
        int transportOffset = 34;
        ByteBuffer packetDataBB = ByteBuffer.wrap(packetData);
        int limit = packetDataBB.limit();

        ret.setWildcards(0); // all fields have explicit entries
        ret.setInputPort(inputPort);

        if (inputPort == OFPortNo.OFPP_ALL.getValue())
//            this.wildcards |= OFFlowWildcards.OFPFW_IN_PORT;
        	ret.setWildcards( ret.getWildcards() | OFFlowWildcards.OFPFW_IN_PORT );

        assert (limit >= 14);
        // dl dst
        byte[] eth_dst = new byte[6];
        packetDataBB.get(eth_dst);
        ret.setDataLayerDestination(eth_dst);
        
        // dl src
        byte[] eth_src = new byte[6];
        packetDataBB.get(eth_src);
        ret.setDataLayerSource(eth_src);
        
        // dl type
        ret.setDataLayerType(packetDataBB.getShort());

        if (ret.getDataLayerType() != (short) 0x8100) { // need cast to avoid signed
            // bug
        	ret.setDataLayerVirtualLan((short) 0xffff);
        	ret.setDataLayerVirtualLanPriorityCodePoint((byte) 0);
        } else {
            // has vlan tag
            scratch = packetDataBB.getShort();
            ret.setDataLayerVirtualLan((short)(0xfff & scratch));
            ret.setDataLayerVirtualLanPriorityCodePoint((byte)((0xe000 & scratch) >> 13));
            ret.setDataLayerType(packetDataBB.getShort());
        }

        switch (ret.getDataLayerType()) {
        case 0x0800:
            // ipv4
            // check packet length
            scratch = packetDataBB.get();
            scratch = (short) (0xf & scratch);
            transportOffset = (packetDataBB.position() - 1) + (scratch * 4);
            // nw tos (dscp)
            scratch = packetDataBB.get();
            ret.setNetworkTypeOfService((byte) ((0xfc & scratch) >> 2));
            // nw protocol
            packetDataBB.position(packetDataBB.position() + 7);
            ret.setNetworkProtocol(packetDataBB.get());
            // nw src
            packetDataBB.position(packetDataBB.position() + 2);
            ret.setNetworkSource(packetDataBB.getInt());
            // nw dst
            ret.setNetworkDestination(packetDataBB.getInt());
            packetDataBB.position(transportOffset);
            break;
        case 0x0806:
            // arp
            int arpPos = packetDataBB.position();
            // opcode
            scratch = packetDataBB.getShort(arpPos + 6);
            ret.setNetworkProtocol((byte) (0xff & scratch));

            scratch = packetDataBB.getShort(arpPos + 2);
            // if ipv4 and addr len is 4
            if (scratch == 0x800 && packetDataBB.get(arpPos + 5) == 4) {
                // nw src
            	ret.setNetworkSource(packetDataBB.getInt(arpPos + 14));
            	// nw dst
            	ret.setNetworkDestination(packetDataBB.getInt(arpPos + 24));
            } else {
            	ret.setNetworkSource(0);
            	ret.setNetworkDestination(0);
            }
            break;
        default:
        	ret.setNetworkTypeOfService((byte)0);
        	ret.setNetworkProtocol((byte)0);
        	ret.setNetworkSource(0);
        	ret.setNetworkDestination(0);

            break;
        }

        switch (ret.getNetworkProtocol()) {
        case 0x01:
            // icmp
            // type
        	ret.setTransportSource(U8.f(packetDataBB.get()));
            // code
        	ret.setTransportDestination(U8.f(packetDataBB.get()));
            break;
        case 0x06:
            // tcp
            // tcp src
        	ret.setTransportSource(packetDataBB.getShort());
        	// tcp dst
        	ret.setTransportDestination(packetDataBB.getShort());
            break;
        case 0x11:
            // udp
            // udp src
        	ret.setTransportSource(packetDataBB.getShort());
            // udp dest
        	ret.setTransportDestination(packetDataBB.getShort());
            break;
        default:
        	ret.setTransportSource((short)0);
        	ret.setTransportDestination((short)0);
            break;
        }
        return ret;
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
    private void setFromCIDR(OFMatch match, String cidr, String which)
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
        int mask = 32 - prefix;
        if (which.equals(STR_NW_DST)) {
        	match.setNetworkDestination(ip);
        	match.setWildcards((match.getWildcards() & ~OFFlowWildcards.OFPFW_NW_DST_MASK) | 
        					(mask << OFFlowWildcards.OFPFW_NW_DST_SHIFT));
        } else if (which.equals(STR_NW_SRC)) {
        	match.setNetworkSource(ip);
        	match.setWildcards((match.getWildcards() & ~OFFlowWildcards.OFPFW_NW_SRC_MASK) | 
					(mask << OFFlowWildcards.OFPFW_NW_SRC_SHIFT));
        }
    }
    
	/**
     * Set this OFMatch's parameters based on a comma-separated key=value pair
     * dpctl-style string, e.g., from the output of OFMatch.toString() <br>
     * <p>
     * Supported keys/values include <br>
     * <p>
     * <TABLE border=1>
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
     * <p>
     * The CIDR-style netmasks assume 32 netmask if none given, so:
     * "128.8.128.118/32" is the same as "128.8.128.118"
     * 
     * @param match
     *            a key=value comma separated string, e.g.
     *            "in_port=5,ip_dst=192.168.0.0/16,tp_src=80"
	 * @return 
     * @throws IllegalArgumentException
     *             on unexpected key or value
     */

    public OFMatch loadOFMatchFromString(String match) throws IllegalArgumentException {
    	OFMatch ret = new OFMatch();
    	
        if (match.equals("") || match.equalsIgnoreCase("any")
                || match.equalsIgnoreCase("all") || match.equals("[]"))
            match = "OFMatch[]";
        String[] tokens = match.split("[\\[,\\]]");
        String[] values;
        int initArg = 0;
        if (tokens[0].equals("OFMatch"))
            initArg = 1;
        ret.setWildcards(OFFlowWildcards.OFPFW_ALL);
        int i;
        for (i = initArg; i < tokens.length; i++) {
            values = tokens[i].split("=");
            if (values.length != 2)
                throw new IllegalArgumentException("Token " + tokens[i]
                        + " does not have form 'key=value' parsing " + match);
            values[0] = values[0].toLowerCase(); // try to make this case insens
            if (values[0].equals(STR_IN_PORT) || values[0].equals("input_port")) {
            	ret.setInputPort(U16.t(Integer.valueOf(values[1])));
            	ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_IN_PORT);
            } else if (values[0].equals(STR_DL_DST)
                    || values[0].equals("eth_dst")) {
            	ret.setDataLayerDestination(HexString.fromHexString(values[1]));
            	ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_DL_DST);
            } else if (values[0].equals(STR_DL_SRC)
                    || values[0].equals("eth_src")) {
            	ret.setDataLayerSource(HexString.fromHexString(values[1]));
            	ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_DL_SRC);
            } else if (values[0].equals(STR_DL_TYPE)
                    || values[0].equals("eth_type")) {
                if (values[1].startsWith("0x"))
                	ret.setDataLayerType(U16.t(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
                else
                	ret.setDataLayerType(U16.t(Integer.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_DL_TYPE);
            } else if (values[0].equals(STR_DL_VLAN)) {
                if (values[1].contains("0x")) {
                	ret.setDataLayerVirtualLan(U16.t(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
                } else {
                	ret.setDataLayerVirtualLan(U16.t(Integer.valueOf(values[1])));
                }
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_DL_VLAN);
            } else if (values[0].equals(STR_DL_VLAN_PCP)) {
            	ret.setDataLayerVirtualLanPriorityCodePoint(U8.t(Short.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_DL_VLAN_PCP);
            } else if (values[0].equals(STR_NW_DST)
                    || values[0].equals("ip_dst"))
                setFromCIDR(ret, values[1], STR_NW_DST);
            else if (values[0].equals(STR_NW_SRC) || values[0].equals("ip_src"))
                setFromCIDR(ret, values[1], STR_NW_SRC);
            else if (values[0].equals(STR_NW_PROTO)) {
            	ret.setNetworkProtocol(U8.t(Short.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_NW_PROTO);
            } else if (values[0].equals(STR_NW_TOS)) {
            	ret.setNetworkTypeOfService(U8.t(Short.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_NW_TOS);
            } else if (values[0].equals(STR_TP_DST)) {
            	ret.setTransportDestination(U16.t(Integer.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_TP_DST);
            } else if (values[0].equals(STR_TP_SRC)) {
                ret.setTransportSource(U16.t(Integer.valueOf(values[1])));
                ret.setWildcards(ret.getWildcards() & ~OFFlowWildcards.OFPFW_TP_SRC);
            } else
                throw new IllegalArgumentException("unknown token " + tokens[i]
                        + " parsing " + match);
        }
        return ret;
    }

}
