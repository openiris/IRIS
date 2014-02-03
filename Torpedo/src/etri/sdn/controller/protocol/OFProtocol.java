package etri.sdn.controller.protocol;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.protocol.interfaces.OFAction;
import org.openflow.protocol.interfaces.OFActionOutput;
import org.openflow.protocol.interfaces.OFEchoReply;
import org.openflow.protocol.interfaces.OFFeaturesReply;
import org.openflow.protocol.interfaces.OFFeaturesRequest;
import org.openflow.protocol.interfaces.OFFlowMod;
import org.openflow.protocol.interfaces.OFFlowModCommand;
import org.openflow.protocol.interfaces.OFFlowModFlags;
import org.openflow.protocol.interfaces.OFFlowWildcards;
import org.openflow.protocol.interfaces.OFHello;
import org.openflow.protocol.interfaces.OFInstruction;
import org.openflow.protocol.interfaces.OFInstructionApplyActions;
import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFMatchOxm;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.interfaces.OFOxmMatchFields;
import org.openflow.protocol.interfaces.OFPortConfig;
import org.openflow.protocol.interfaces.OFPortDesc;
import org.openflow.protocol.interfaces.OFPortReason;
import org.openflow.protocol.interfaces.OFPortState;
import org.openflow.protocol.interfaces.OFPortStatus;
import org.openflow.protocol.interfaces.OFSetConfig;
import org.openflow.protocol.interfaces.OFStatisticsDescReply;
import org.openflow.protocol.interfaces.OFStatisticsPortDescReply;
import org.openflow.protocol.interfaces.OFStatisticsPortDescRequest;
import org.openflow.protocol.interfaces.OFStatisticsReply;
import org.openflow.protocol.interfaces.OFStatisticsRequest;
import org.openflow.protocol.interfaces.OFStatisticsType;
import org.openflow.util.HexString;
import org.openflow.protocol.OFBFlowWildcard;
import org.openflow.protocol.OFPort;
import org.openflow.util.U16;
import org.openflow.util.U8;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.PortInformation;
import etri.sdn.controller.SwitchInformation;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.Logger;

public class OFProtocol {

	//	private static VersionedMessageFactory message_factory = new VersionedMessageFactory();

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
	/*
	 * Per-switch data structures
	 */
	private Map<IOFSwitch, SwitchInformation> switchInformations = 
			new ConcurrentHashMap<IOFSwitch, SwitchInformation>();
	private Map<IOFSwitch, Map<Integer, PortInformation>> portInformations = 
			new ConcurrentHashMap<IOFSwitch, Map<Integer, PortInformation>>();
	private Map<IOFSwitch, Map<String, PortInformation>> portInformationsByName = 
			new ConcurrentHashMap<IOFSwitch, Map<String, PortInformation>>();

	/**
	 * This field is used to exchange information with switch.
	 */
	private Map<IOFSwitch, Map<Integer/*xid*/, Object>> responsesCache = 
			new ConcurrentHashMap<IOFSwitch, Map<Integer, Object>>();

	private OFController controller;
	private Object portLock = new Object();

	public OFProtocol(OFController controller) {
		this.controller = controller;
	}

	public OFController getController() {
		return this.controller;
	}

	public void setResponseCacheItem(IOFSwitch sw, int xid, Object item) {
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			rcache = new ConcurrentHashMap<Integer, Object>();
			this.responsesCache.put(sw, rcache);
		}
		rcache.put( xid, item );
	}

	public Object getResponseCacheItem(IOFSwitch sw, int xid) {
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			return null;
		}
		return rcache.get(xid);
	}

	public void removeResponseCacheItem(IOFSwitch sw, int xid) {
		Map<Integer, Object> rcache = this.responsesCache.get(sw);
		if ( rcache == null ) {
			return;
		}
		rcache.remove(xid);
	}

	/*
	public static final OFMessageFactory getMessageFactory() {
		return new VersionedMessageFactory();
	}
	 */
	
	public SwitchInformation getSwitchInformation(IOFSwitch sw) {
		SwitchInformation si = this.switchInformations.get(sw);
		if ( si == null ) {
			si = new SwitchInformation();
			this.switchInformations.put(sw, si);
		}
		return si;
	}

	/**
	 * Get port information by the port number. 
	 * If none existent, a new PortInformation object is created and saved automatically.
	 * @param sw
	 * @param port
	 * @return
	 */
	public PortInformation getPortInformation(IOFSwitch sw, int port) {
		Map<Integer, PortInformation> inner = portInformations.get(sw);
		if ( inner == null ) {
			inner = new ConcurrentHashMap<Integer, PortInformation>();
			portInformations.put(sw, inner);
		}
		PortInformation pi = inner.get(port);
		if ( pi == null ) {
			pi = new PortInformation();
			pi.setPort(port);
			inner.put(port, pi);
		}
		return pi;
	}

	public Collection<PortInformation> getPortInformations(IOFSwitch sw) {
		Map<Integer, PortInformation> inner = portInformations.get(sw);
		if ( inner != null ) {
			return inner.values();
		}
		return Collections.emptySet();
	}
	
	public Collection<PortInformation> getPortInformations(long datapathId) {
		for ( IOFSwitch sw : portInformations.keySet() ) {
			if ( sw.getId() == datapathId ) {
				Map<Integer, PortInformation> inner = portInformations.get(sw);
				if ( inner != null ) {
					return inner.values();
				}
			}
		}
		return Collections.emptySet();
	}

	public void removePortInformation(IOFSwitch sw, PortInformation pi) {
		Map<Integer, PortInformation> inner = portInformations.get(sw);
		if ( inner != null ) {
			inner.remove(pi.getPort());
			Map<String, PortInformation> inner2 = portInformationsByName.get(sw);
			if ( inner2 != null ) {
				inner2.remove( pi.getNameString() );
			}
		}
	}

	/**
	 * This method should be called after port number is correctly set up.
	 * @param sw
	 * @param name
	 * @return	Null can be returned if the item that you looking for is non-existent
	 */
	public PortInformation getPortInformationByName(IOFSwitch sw, String name) {
		Map<String, PortInformation> inner = portInformationsByName.get(sw);
		if ( inner == null ) {
			inner = new ConcurrentHashMap<String, PortInformation>();
			portInformationsByName.put(sw, inner);
		}
		return inner.get(name);
	}

	public void setPortInformationByName(IOFSwitch sw, String name, PortInformation pi) {
		Map<String, PortInformation> inner = portInformationsByName.get(sw);
		if ( inner == null ) {
			inner = new ConcurrentHashMap<String, PortInformation>();
			portInformationsByName.put(sw, inner);
		}
		inner.put(name, pi);
	}

	public void setDescription(IOFSwitch sw, OFStatisticsDescReply r) {
		this.getSwitchInformation(sw)
		.setManufacturerDescription(r.getManufacturerDescription())
		.setDatapathDescription(r.getDatapathDescription())
		.setHardwareDescription(r.getHardwareDescription())
		.setSoftwareDescription(r.getSoftwareDescription())
		.setSerialNumber(r.getSerialNumber());
	}

	public OFStatisticsDescReply getDescription(IOFSwitch sw) {
		SwitchInformation si = this.getSwitchInformation(sw);
		if ( si == null || si.getManufacturerDescription() == null ) {
			return null;
		}

		OFStatisticsDescReply ret = OFMessageFactory.createStatisticsDescReply(sw.getVersion());
		ret.setManufacturerDescription(si.getManufacturerDescription());
		ret.setDatapathDescription(si.getDatapathDescription());
		ret.setHardwareDescription(si.getHardwareDescription());
		ret.setSoftwareDescription(si.getSoftwareDescription());
		ret.setSerialNumber(si.getSerialNumber());

		return ret;
	}

	public OFPortDesc getPort(IOFSwitch sw, int portNum) {
		PortInformation pi = this.getPortInformation(sw, portNum);
		if ( pi != null ) {
			OFPortDesc pd = OFMessageFactory.createPortDesc(sw.getVersion());
			pd
			.setPort(OFPort.of(portNum))
			.setHwAddr(pi.getHwAddr())
			.setName(pi.getName())
			.setConfigWire(pi.getConfig())
			.setStateWire(pi.getState())
			.setCurrentFeatures(pi.getCurrentFeatures())
			.setAdvertisedFeatures(pi.getAdvertisedFeatures())
			.setSupportedFeatures(pi.getSupportedFeatures())
			.setPeerFeatures(pi.getPeerFeatures());
			if ( pd.isCurrSpeedSupported() )
				pd.setCurrSpeed(pi.getCurrSpeed());
			if ( pd.isMaxSpeedSupported() ) 
				pd.setMaxSpeed(pi.getMaxSpeed());
			return pd;
		}
		return null;
	}

	public Collection<OFPortDesc> getPorts(IOFSwitch sw) {
		List<OFPortDesc> ret = new LinkedList<OFPortDesc>();
		for ( PortInformation pi : this.getPortInformations(sw) ) {
			OFPortDesc pd = OFMessageFactory.createPortDesc(sw.getVersion());
			pd
			.setPort(OFPort.of(pi.getPort()))
			.setHwAddr(pi.getHwAddr())
			.setName(pi.getName())
			.setConfigWire(pi.getConfig())
			.setStateWire(pi.getState())
			.setCurrentFeatures(pi.getCurrentFeatures())
			.setAdvertisedFeatures(pi.getAdvertisedFeatures())
			.setSupportedFeatures(pi.getSupportedFeatures())
			.setPeerFeatures(pi.getPeerFeatures());
			if ( pd.isCurrSpeedSupported() )
				pd.setCurrSpeed(pi.getCurrSpeed());
			if ( pd.isMaxSpeedSupported() ) 
				pd.setMaxSpeed(pi.getMaxSpeed());
			ret.add( pd );
		}
		return ret;
	}
	

	public List<OFPortDesc> getPorts(long datapathId) {
		List<OFPortDesc> ret = new LinkedList<OFPortDesc>();
		for ( PortInformation pi : this.getPortInformations(datapathId) ) {
			IOFSwitch sw = this.controller.getSwitch(datapathId);
			if ( sw == null ) {
				return Collections.emptyList();
			}
			OFPortDesc pd = OFMessageFactory.createPortDesc(sw.getVersion());
			pd
			.setPort(OFPort.of(pi.getPort()))
			.setHwAddr(pi.getHwAddr())
			.setName(pi.getName())
			.setConfigWire(pi.getConfig())
			.setStateWire(pi.getState())
			.setCurrentFeatures(pi.getCurrentFeatures())
			.setAdvertisedFeatures(pi.getAdvertisedFeatures())
			.setSupportedFeatures(pi.getSupportedFeatures())
			.setPeerFeatures(pi.getPeerFeatures());
			if ( pd.isCurrSpeedSupported() )
				pd.setCurrSpeed(pi.getCurrSpeed());
			if ( pd.isMaxSpeedSupported() ) 
				pd.setMaxSpeed(pi.getMaxSpeed());
			ret.add( pd );
		}
		return ret;
	}

	public void setPort(IOFSwitch sw, OFPortDesc portDesc) {
		PortInformation pi = this.getPortInformation(sw, portDesc.getPort().get());
		pi.setHwAddr(portDesc.getHwAddr())
		.setName(portDesc.getName())
		.setConfig(portDesc.getConfigWire())
		.setState(portDesc.getStateWire())
		.setCurrentFeatures(portDesc.getCurrentFeatures())
		.setAdvertisedFeatures(portDesc.getAdvertisedFeatures())
		.setSupportedFeatures(portDesc.getSupportedFeatures())
		.setPeerFeatures(portDesc.getPeerFeatures());
		if ( portDesc.isCurrSpeedSupported() )
			pi.setCurrSpeed(portDesc.getCurrSpeed());
		if ( portDesc.isMaxSpeedSupported() ) 
			pi.setMaxSpeed(portDesc.getMaxSpeed());
		this.setPortInformationByName(sw, new String(portDesc.getName()), pi);
	}

	public void deletePort(IOFSwitch sw, int portNumber) {
		PortInformation pi = this.getPortInformation(sw, portNumber);
		if ( pi != null ) {
			this.removePortInformation(sw, pi);
		}
	}

	public boolean handleConnectedEvent(Connection conn) {
		// This is a greeting that says 'Hey. We know up to 1.3.2.' 
		OFHello hello = OFMessageFactory.createHello((byte)0x04);
		hello.setVersion((byte)0x04);
		conn.write(hello);
		return true;
	}

	public boolean process(Connection conn, MessageContext context, OFMessage m) {
		IOFSwitch sw = conn.getSwitch();
		OFMessageType t = m.getType();

		switch (t) {
		case HELLO:
			// if HELLO received, we send features request.
			try {
				Logger.stderr("GOT HELLO from " + conn.getClient().getRemoteAddress().toString());
			} catch (IOException e1) {
				return false;
			}

			// set the version number in the switch.
			if ( sw != null ) {
				sw.setVersion(m.getVersion());
			}

			// send feature request message.
			OFFeaturesRequest freq = OFMessageFactory.createFeaturesRequest(m.getVersion());
			conn.write(freq);
			break;

		case ERROR:
			Logger.stderr("GET ERROR : " + m.toString());
			return false;

		case ECHO_REQUEST:
			Logger.debug("ECHO_REQUEST is received");
			OFEchoReply reply = OFMessageFactory.createEchoReply(m.getVersion());
			reply.setXid(m.getXid());
			conn.write(reply);
			break;

		case FEATURES_REPLY:
			if ( sw == null ) return false;

			synchronized ( portLock ) {
				OFFeaturesReply fr = (OFFeaturesReply) m;
				sw.setId(fr.getDatapathId());

				SwitchInformation si = this.getSwitchInformation(sw);
				si
				.setId(fr.getDatapathId())
				.setCapabilities(fr.getCapabilitiesWire())
				.setBuffers(fr.getNBuffers())
				.setTables(fr.getNTables());
				if (fr.isActionsSupported() ) 
					si.setActions(fr.getActions());

				// for version 1.3, there is no fr.getPorts() method.
				if ( fr.isPortsSupported() ) {
					List<OFPortDesc> ports = fr.getPorts();
					for ( OFPortDesc port: ports ) {
						setPort(sw, port);
					}
				}
			}

			if ( m.getVersion() > (byte)0x01 ) {
				// set configuration parameters in the switch.
				// The switch does not reply to a request to set the configuration.
				// The flags indicate whether IP fragments should be treated normally, dropped, or reassembled. [jshin]
				OFSetConfig sc = OFMessageFactory.createSetConfig(m.getVersion());
				sc.setFlags((short) 0).setMissSendLength((short) 0xffff);
				conn.write(sc);
			}

			// send port desc request message to retrieve all port list.
			// preq might be null if m.getVersion() == 0x01. But it's OK because
			// conn.write ignore null preq.
			OFStatisticsPortDescRequest preq = OFMessageFactory.createStatisticsPortDescRequest(m.getVersion());
			conn.write(preq);

			OFStatisticsRequest req = OFMessageFactory.createStatisticsDescRequest(m.getVersion());
			conn.write(req);

			// send flow_mod to process table miss packets [jshin]
			OFInstructionApplyActions instruction = 
					OFMessageFactory.createInstructionApplyActions(m.getVersion());
			if ( instruction != null ) {	// version > 1.0
				List<OFInstruction> instructions = new LinkedList<OFInstruction>();
				OFMatchOxm match = OFMessageFactory.createMatchOxm(m.getVersion());
				OFActionOutput action = OFMessageFactory.createActionOutput(m.getVersion());
				List<OFAction> actions = new LinkedList<OFAction>();

				OFFlowMod fm = OFMessageFactory.createFlowMod(m.getVersion());

				action.setPort(OFPort.CONTROLLER).setMaxLength((short)0).setLength(action.computeLength());
				actions.add(action);

				instruction.setActions(actions).setLength(instruction.computeLength());
				instructions.add(instruction);

				fm.setTableId((byte) 0x0)			//the table which the flow entry should be inserted
				.setCommand(OFFlowModCommand.ADD)
				.setIdleTimeout((short) 0)
				.setHardTimeout((short) 0)			//permanent if idle and hard timeout are zero
				.setPriority((short) 0)
				.setBufferId(0x00000000)			//refers to a packet buffered at the switch and sent to the controller
				.setOutGroup(OFPort.ANY.get())		
				.setOutPort(OFPort.ANY)
				.setMatch(match)
				.setInstructions(instructions)
				.setFlags( OFFlowModFlags.SEND_FLOW_REM );
				//send flow removed message when flow expires or is deleted

				conn.write(fm);
			}

			// now the handshaking is fully done.
			// therefore, we can safely register the switch object
			// into the switches map. This map is used heavily by
			// link discovery module.
			//			Logger.stdout("adding a switch with id = " + conn.getSwitch().getId());
			getController().addSwitch( conn.getSwitch().getId(), conn.getSwitch() );

			deliverFeaturesReply( conn.getSwitch(), m.getXid(), (OFFeaturesReply) m );

			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}

			break;

		case PORT_STATUS:
			if ( sw == null ) return false;

			OFPortStatus ps = (OFPortStatus) m;
			OFPortDesc phyport = (OFPortDesc) ps.getDesc();
			if ( ps.getReason() == OFPortReason.DELETE ) {
				deletePort( sw, phyport.getPort().get() );
			} else if ( ps.getReason() == OFPortReason.MODIFY ) {
				deletePort( sw, phyport.getPort().get() );
				setPort( sw, phyport );
			} else { /* ps.getReason() == OFPortReason.ADD */ 
				setPort( sw, phyport );
			}

			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			break;

		case STATISTICS_REPLY:
			if ( sw == null ) return false;

			OFStatisticsReply stat = (OFStatisticsReply) m;
			if ( stat.getStatisticsType() == OFStatisticsType.PORT_DESC ) {
				OFStatisticsPortDescReply portDesc = (OFStatisticsPortDescReply) m;
				synchronized ( portLock ) {
					List<OFPortDesc> ports = portDesc.getEntries();
					for ( OFPortDesc port: ports ) {
						setPort(sw, port);
					}
				}
			} else if ( stat.getStatisticsType() == OFStatisticsType.DESC ) {
				setDescription(sw, (OFStatisticsDescReply) stat);
			} else {
				deliverSwitchStatistics( sw, stat );
			}
			break;

		case PACKET_IN:
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
	 * Modules that use IOFSwitch objects use this method to request statistics 
	 * to the switch.
	 * @param req OFStatisticsRequest object.
	 */
	public List<OFStatisticsReply> getSwitchStatistics(IOFSwitch sw, OFStatisticsRequest req) {
		List<OFStatisticsReply> response = new LinkedList<OFStatisticsReply>();
		int xid = req.setXid(sw.getNextTransactionId()).getXid();

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

	public void deliverSwitchStatistics(IOFSwitch sw, OFStatisticsReply m) {
		Object response = getResponseCacheItem(sw, m.getXid());
		if ( response == null ) {
			return;
		}
		if ( response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFStatisticsReply> rl = (List<OFStatisticsReply>) response;
			synchronized ( response ) {
				rl.add( m );
				response.notifyAll();
			}
		}
	}

	public OFFeaturesReply getFeaturesReply(IOFSwitch sw) {
		OFFeaturesRequest req = OFMessageFactory.createFeaturesRequest(sw.getVersion());
		req.setXid( sw.getNextTransactionId() );
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

	public void deliverFeaturesReply(IOFSwitch sw, int xid, OFFeaturesReply reply) {
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

	public Collection<OFPortDesc> getEnabledPorts(IOFSwitch sw) {		
		List<OFPortDesc> result = new ArrayList<OFPortDesc>();
		Collection<OFPortDesc> allPorts = this.getPorts(sw);
		if ( allPorts == null ) return null;

		for (OFPortDesc port: allPorts) {
			if (portEnabled(port)) {
				result.add(port);
			}
		}
		return result;
	}

	public Collection<Integer> getEnabledPortNumbers(IOFSwitch sw) {
		List<Integer> result = new ArrayList<Integer>();
		Collection<OFPortDesc> allPorts = this.getPorts(sw);
		if ( allPorts == null ) return null;

		for (OFPortDesc port: allPorts) {
			if (portEnabled(port)) {
				result.add(port.getPort().get());
			}
		}
		return result;
	}

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

	public boolean portEnabled(IOFSwitch sw, short port) {
		OFPortDesc desc = this.getPort(sw, port);
		if ( desc == null ) {
			return false;
		}
		return portEnabled(desc);
	}

	public OFMatch loadOFMatchFromPacket(IOFSwitch sw, byte[] packetData, short inputPort) {

		OFMatch.Builder ret = OFMessageFactory.createMatchBuilder(sw.getVersion());

		short scratch = 0;
		int transportOffset = 34;
		ByteBuffer packetDataBB = ByteBuffer.wrap(packetData);
		int limit = packetDataBB.limit();

		ret.setInputPort(OFPort.of(inputPort));

		if ( ret.isWildcardsSupported() && inputPort == OFPort.ALL.get() ) {
			// ret.wildcards |= OFBFlowWildcards.IN_PORT
			ret.setWildcards( OFFlowWildcards.IN_PORT );
		}

		System.out.println(limit);
		assert (limit >= 14);
		// dl dst
		byte[] eth_dst = new byte[6];
		System.out.println(packetDataBB + " " + eth_dst + " " + limit);
		packetDataBB.get(eth_dst);
		ret.setDataLayerDestination(eth_dst);

		// dl src
		byte[] eth_src = new byte[6];
		packetDataBB.get(eth_src);
		ret.setDataLayerSource(eth_src);

		// dl type
		short data_layer_type = packetDataBB.getShort();
		ret.setDataLayerType(data_layer_type);

		//		if (data_layer_type != (short) 0x8100) { // need cast to avoid signed
		//			// bug
		//			ret.setDataLayerVirtualLan((short) 0xffff);
		//			ret.setDataLayerVirtualLanPriorityCodePoint((byte) 0);
		//		} else {
		//			// has vlan tag
		//			scratch = packetDataBB.getShort();
		//			ret.setDataLayerVirtualLan((short)(0xfff & scratch));
		//			ret.setDataLayerVirtualLanPriorityCodePoint((byte)((0xe000 & scratch) >> 13));
		//			ret.setDataLayerType(packetDataBB.getShort());
		//		}

		if ( data_layer_type == (short) 0x8100 ) {
			scratch = packetDataBB.getShort();
			ret.setDataLayerVirtualLan((short)(0xfff & scratch));
			ret.setDataLayerVirtualLanPriorityCodePoint((byte)((0xe000 & scratch) >> 13));
			//			ret.setDataLayerType(packetDataBB.getShort());
		}

		byte network_protocol = 0;

		switch (data_layer_type) {
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
			ret.setNetworkProtocol(network_protocol = packetDataBB.get());
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
			if ( sw.getVersion() == (byte) 0x01 ) {
				ret.setNetworkProtocol(network_protocol = (byte) (0xff & scratch));
			} else {
				ret.setValue(OFOxmMatchFields.OFB_ARP_OP, (byte) 0, ByteBuffer.allocate(2).putShort(scratch).array());
			}

			scratch = packetDataBB.getShort(arpPos + 2);
			// if ipv4 and addr len is 4
			if (scratch == 0x800 && packetDataBB.get(arpPos + 5) == 4) {
				if ( sw.getVersion() == (byte) 0x01 ) {
					// nw src
					ret.setNetworkSource(packetDataBB.getInt(arpPos + 14));
					// nw dst
					ret.setNetworkDestination(packetDataBB.getInt(arpPos + 24));
				} else {
					ret.setValue(OFOxmMatchFields.OFB_ARP_SPA, (byte) 0, 
							ByteBuffer.allocate(4).putInt(packetDataBB.getInt(arpPos + 14)).array());
					ret.setValue(OFOxmMatchFields.OFB_ARP_TPA, (byte) 0, 
							ByteBuffer.allocate(4).putInt(packetDataBB.getInt(arpPos + 24)).array());
				}
			}
			break;
		default:
			break;
		}

		switch (network_protocol) {
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
	private void setFromCIDR(OFMatch.Builder match, String cidr, String which)
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

		if ( match.isWildcardsSupported() ) {
			int mask = 32 - prefix;
			if (which.equals(STR_NW_DST)) {
				match.setNetworkDestination(ip);
				match.setWildcardsWire((match.getWildcardsWire() & ~OFBFlowWildcard.NW_DST_MASK) | 
						(mask << OFBFlowWildcard.NW_DST_SHIFT));
			} else if (which.equals(STR_NW_SRC)) {
				match.setNetworkSource(ip);
				match.setWildcardsWire((match.getWildcardsWire() & ~OFBFlowWildcard.NW_SRC_MASK) | 
						(mask << OFBFlowWildcard.NW_SRC_SHIFT));
			}
		} else {
			int mask = 0x80000000 >> (prefix-1);

						if (which.equals(STR_NW_DST)) {
							match.setValue(OFOxmMatchFields.OFB_IPV4_DST, (byte) 1, ByteBuffer.allocate(8).putInt(ip).putInt(mask).array());
						} else if (which.equals(STR_NW_SRC)) {
							match.setValue(OFOxmMatchFields.OFB_IPV4_SRC, (byte) 1, ByteBuffer.allocate(8).putInt(ip).putInt(mask).array());
						}
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
	public OFMatch loadOFMatchFromString(IOFSwitch sw, String match)
			throws IllegalArgumentException {
		OFMatch.Builder ret = OFMessageFactory.createMatchBuilder(sw.getVersion());

		if (match.equals("") || match.equalsIgnoreCase("any")
				|| match.equalsIgnoreCase("all") || match.equals("[]"))
			match = "OFMatch[]";
		String[] tokens = match.split("[\\[,\\]]");
		String[] values;
		int initArg = 0;
		if (tokens[0].equals("OFMatch"))
			initArg = 1;
		if ( ret.isWildcardsSupported() ) {
			ret.setWildcards(OFFlowWildcards.ALL);
		}

		int i;
		for (i = initArg; i < tokens.length; i++) {
			values = tokens[i].split("=");
			if (values.length != 2)
				throw new IllegalArgumentException("Token " + tokens[i]
						+ " does not have form 'key=value' parsing " + match);
			values[0] = values[0].toLowerCase(); // try to make this case insens
			if (values[0].equals(STR_IN_PORT) || values[0].equals("input_port")) {
				ret.setInputPort(OFPort.of(Integer.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.IN_PORT);
			} else if (values[0].equals(STR_DL_DST)
					|| values[0].equals("eth_dst")) {
				ret.setDataLayerDestination(HexString.fromHexString(values[1]));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.DL_DST);
			} else if (values[0].equals(STR_DL_SRC)
					|| values[0].equals("eth_src")) {
				ret.setDataLayerSource(HexString.fromHexString(values[1]));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.DL_SRC);
			} else if (values[0].equals(STR_DL_TYPE)
					|| values[0].equals("eth_type")) {
				if (values[1].startsWith("0x"))
					ret.setDataLayerType(U16.t(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
				else
					ret.setDataLayerType(U16.t(Integer.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.DL_TYPE);
			} else if (values[0].equals(STR_DL_VLAN)) {
				if (values[1].contains("0x")) {
					ret.setDataLayerVirtualLan(U16.t(Integer.valueOf(values[1].replaceFirst("0x", ""), 16)));
				} else {
					ret.setDataLayerVirtualLan(U16.t(Integer.valueOf(values[1])));
				}
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.DL_VLAN);
			} else if (values[0].equals(STR_DL_VLAN_PCP)) {
				ret.setDataLayerVirtualLanPriorityCodePoint(U8.t(Short.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.DL_VLAN_PCP);
			} else if (values[0].equals(STR_NW_DST)
					|| values[0].equals("ip_dst"))
				setFromCIDR(ret, values[1], STR_NW_DST);
			else if (values[0].equals(STR_NW_SRC) || values[0].equals("ip_src"))
				setFromCIDR(ret, values[1], STR_NW_SRC);
			else if (values[0].equals(STR_NW_PROTO)) {
				ret.setNetworkProtocol(U8.t(Short.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.NW_PROTO);
			} else if (values[0].equals(STR_NW_TOS)) {
				ret.setNetworkTypeOfService(U8.t(Short.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.NW_TOS);
			} else if (values[0].equals(STR_TP_DST)) {
				ret.setTransportDestination(U16.t(Integer.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.TP_DST);
			} else if (values[0].equals(STR_TP_SRC)) {
				ret.setTransportSource(U16.t(Integer.valueOf(values[1])));
				if ( ret.isWildcardsSupported() )
					ret.setWildcardsWire(ret.getWildcardsWire() & ~OFBFlowWildcard.TP_SRC);
			} else
				throw new IllegalArgumentException("unknown token " + tokens[i]
						+ " parsing " + match);
		}
		return ret.build();
	}
}
