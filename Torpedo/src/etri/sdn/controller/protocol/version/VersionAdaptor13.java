package etri.sdn.controller.protocol.version;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.ver1_0.types.OFFlowWildcards;
import org.openflow.protocol.ver1_0.types.OFPortNo;
import org.openflow.protocol.ver1_3.messages.OFAction;
import org.openflow.protocol.ver1_3.messages.OFActionOutput;
import org.openflow.protocol.ver1_3.messages.OFEchoReply;
import org.openflow.protocol.ver1_3.messages.OFError;
import org.openflow.protocol.ver1_3.messages.OFFeaturesReply;
import org.openflow.protocol.ver1_3.messages.OFFeaturesRequest;
import org.openflow.protocol.ver1_3.messages.OFFlowMod;
import org.openflow.protocol.ver1_3.messages.OFHello;
import org.openflow.protocol.ver1_3.messages.OFInstruction;
import org.openflow.protocol.ver1_3.messages.OFInstructionApplyActions;
import org.openflow.protocol.ver1_3.messages.OFMatchOxm;
import org.openflow.protocol.ver1_3.messages.OFMultipartDescReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartDescRequest;
import org.openflow.protocol.ver1_3.messages.OFMultipartPortDescReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartPortDescRequest;
import org.openflow.protocol.ver1_3.messages.OFMultipartReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartRequest;
import org.openflow.protocol.ver1_3.messages.OFPortDesc;
import org.openflow.protocol.ver1_3.messages.OFPortStatus;
import org.openflow.protocol.ver1_3.messages.OFSetConfig;
import org.openflow.protocol.ver1_3.types.OFActionType;
import org.openflow.protocol.ver1_3.types.OFFlowModCommand;
import org.openflow.protocol.ver1_3.types.OFInstructionType;
import org.openflow.protocol.ver1_3.types.OFMatchType;
import org.openflow.protocol.ver1_3.types.OFMessageType;
import org.openflow.protocol.ver1_3.types.OFMultipartType;
import org.openflow.protocol.ver1_3.types.OFPortConfig;
import org.openflow.protocol.ver1_3.types.OFPortReason;
import org.openflow.util.OFPort;
import org.openflow.util.U8;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.PortInformation;
import etri.sdn.controller.SwitchInformation;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.Logger;

public class VersionAdaptor13 extends VersionAdaptor {
	
	public static final byte VERSION = 0x04;						// 1.3
	
	private Object portLock = new Object();
	
	public static byte getVersion() {
		return VERSION;
	}

	public VersionAdaptor13(OFController controller) {
		super(controller);
	}
	
	public void setDescription(IOFSwitch sw, OFMultipartDescReply r) {
		this.getSwitchInformation(sw)
		.setDatapathDescription(r.getDatapathDescription())
		.setHardwareDescription(r.getHardwareDescription())
		.setSoftwareDescription(r.getSoftwareDescription())
		.setSerialNumber(r.getSerialNumber())
		.setDatapathDescription(r.getDatapathDescription());
	}
	
	public OFMultipartDescReply getDescription(IOFSwitch sw) {
		SwitchInformation si = this.getSwitchInformation(sw);
		if ( si == null || si.getManufacturerDescription() == null ) {
			return null;
		}
		
		OFMultipartDescReply ret = new OFMultipartDescReply();
		ret.setManufacturerDescription(si.getManufacturerDescription());
		ret.setHardwareDescription(si.getHardwareDescription());
		ret.setSoftwareDescription(si.getSoftwareDescription());
		ret.setSerialNumber(si.getSerialNumber());
		ret.setDatapathDescription(si.getDatapathDescription());
		
		return ret;
	}
	
	public OFPortDesc getPort(IOFSwitch sw, short portNum) {
		PortInformation pi = this.getPortInformation(sw, (int)portNum);
		if ( pi != null ) {
			OFPortDesc pd = new OFPortDesc()
			.setPort(OFPort.of(portNum))
			.setHwAddr(pi.getHwAddr())
			.setName(pi.getName())
			.setConfig(pi.getConfig())
			.setState(pi.getState())
			.setCurrentFeatures(pi.getCurrentFeatures())
			.setAdvertisedFeatures(pi.getAdvertisedFeatures())
			.setSupportedFeatures(pi.getSupportedFeatures())
			.setPeerFeatures(pi.getPeerFeatures())
			.setCurrSpeed(pi.getCurrSpeed())
			.setMaxSpeed(pi.getMaxSpeed());
			return pd;
		}
		return null;
	}
	
	public Collection<OFPortDesc> getPorts(IOFSwitch sw) {
		List<OFPortDesc> ret = new LinkedList<OFPortDesc>();
		for ( PortInformation pi : this.getPortInformations(sw) ) {
			OFPortDesc pd = new OFPortDesc()
			.setPort(OFPort.of(pi.getPort()))
			.setHwAddr(pi.getHwAddr())
			.setName(pi.getName())
			.setConfig(pi.getConfig())
			.setState(pi.getState())
			.setCurrentFeatures(pi.getCurrentFeatures())
			.setAdvertisedFeatures(pi.getAdvertisedFeatures())
			.setSupportedFeatures(pi.getSupportedFeatures())
			.setPeerFeatures(pi.getPeerFeatures())
			.setCurrSpeed(pi.getCurrSpeed())
			.setMaxSpeed(pi.getMaxSpeed());
			ret.add( pd );
		}
		return ret;
	}
	
	public void setPort(IOFSwitch sw, OFPortDesc portDesc) {
		PortInformation pi = this.getPortInformation(sw, portDesc.getPort().get());
		pi.setHwAddr(portDesc.getHwAddr())
		.setName(portDesc.getName())
		.setConfig(portDesc.getConfig())
		.setState(portDesc.getState())
		.setCurrentFeatures(portDesc.getCurrentFeatures())
		.setAdvertisedFeatures(portDesc.getAdvertisedFeatures())
		.setSupportedFeatures(portDesc.getSupportedFeatures())
		.setPeerFeatures(portDesc.getPeerFeatures())
		.setCurrSpeed(portDesc.getCurrSpeed())
		.setMaxSpeed(portDesc.getMaxSpeed());
		this.setPortInformationByName(sw, new String(portDesc.getName()), pi);
	}
	
	public void deletePort(IOFSwitch sw, int portNumber) {
		PortInformation pi = this.getPortInformation(sw, portNumber);
		if ( pi != null ) {
			this.removePortInformation(sw, pi);
		}
	}

	@Override
	public boolean handleConnectedEvent(Connection conn) {
		OFHello hello = (OFHello) OFMessageType.HELLO.newInstance();
		hello.setVersion((byte)0x04);		// 1.3
		conn.write(hello);
		return true;
	}

	@Override
	public boolean process(Connection conn, MessageContext context, OFMessage m) {
		IOFSwitch sw = conn.getSwitch();
		OFMessageType t = OFMessageType.valueOf(m.getTypeByte());
		switch (t) {
		case HELLO:
			// if HELLO received, we send features request.
			try {
				Logger.stderr("GET 1.3 HELLO from " + conn.getClient().getRemoteAddress().toString());
			} catch (IOException el ) {
				return false;
			}
			// send feature request message.
			OFFeaturesRequest freq = (OFFeaturesRequest) OFMessageType.FEATURES_REQUEST.newInstance();
			conn.write(freq);
			
			// set configuration parameters in the switch.
			// The switch does not reply to a request to set the configuration.
			// The flags indicate whether IP fragments should be threated normally, dropped, or reassembled. [jshin]
			OFSetConfig sc = (OFSetConfig) OFMessageType.SET_CONFIG.newInstance();
			sc.setFlags((short) 0).setMissSendLength((short) 128);
			conn.write(sc);
						
//			try {
//				Thread.sleep(2500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			conn.write(fm);
			break;
			
		case ERROR:
			Logger.stderr("GET 1.3 ERROR : " + new String(((OFError)m).getData()));
			return false;
			
		case ECHO_REQUEST:
			Logger.debug("GET 1.3 ECHO_REQUEST");
			OFEchoReply reply = (OFEchoReply) OFMessageType.ECHO_REPLY.newInstance();
			reply.setXid(m.getXid());
			conn.write(reply);
			break;
			
		case MULTIPART_REPLY:
			if ( sw == null ) return false;
			
			OFMultipartReply r = (OFMultipartReply) m;
			if ( r.getMultipartType() == org.openflow.protocol.interfaces.OFMultipartType.PORT_DESC ) {
				OFMultipartPortDescReply portDesc = (OFMultipartPortDescReply) m;
				synchronized ( portLock ) {
					List<org.openflow.protocol.interfaces.OFPortDesc> ports = portDesc.getEntries();
					for ( org.openflow.protocol.interfaces.OFPortDesc port: ports ) {
						setPort(sw, (OFPortDesc) port);
					}
				}
			} else if ( r.getMultipartType() == org.openflow.protocol.interfaces.OFMultipartType.DESC ) {
				OFMultipartDescReply desc = (OFMultipartDescReply) m;
				this.getSwitchInformation(sw)
				.setDatapathDescription(desc.getDatapathDescription())
				.setHardwareDescription(desc.getHardwareDescription())
				.setManufacturerDescription(desc.getManufacturerDescription())
				.setSerialNumber(desc.getSerialNumber())
				.setSoftwareDescription(desc.getSoftwareDescription());
			} else {
				deliverMultipartReply( conn.getSwitch(), r );
			}
			break;
			
		case FEATURES_REPLY:
			if ( sw == null ) return false;
			
			synchronized ( portLock ) {
				OFFeaturesReply fr = (OFFeaturesReply) m;
				sw.setId(fr.getDatapathId());
				
				this.getSwitchInformation(sw)
				.setId(fr.getDatapathId())
				.setCapabilities(fr.getCapabilitiesWire())
				.setBuffers(fr.getNBuffers())
				.setTables(fr.getNTables());
				
				// for version 1.3, there is no fr.getActions() method.
				// and there's no fr.getPorts() method.
			}
			
			getController().addSwitch( conn.getSwitch().getId(), conn.getSwitch() );
			
			deliverFeaturesReply( conn.getSwitch(), m.getXid(), (OFFeaturesReply) m);
			
			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			
			// send port desc request message to retrieve all port list.
			OFMultipartPortDescRequest preq = (OFMultipartPortDescRequest) OFMultipartType.PORT_DESC.newInstance(OFMessageType.MULTIPART_REQUEST);
//			System.out.println(preq);
			conn.write(preq);
			
			// send switch description request message.
			OFMultipartDescRequest req = 
					(OFMultipartDescRequest) OFMultipartType.DESC.newInstance(OFMessageType.MULTIPART_REQUEST);
//			System.out.println(req);
			conn.write(req);
			
						
			// send flow_mod to process table miss packets [jshin]
			OFInstructionApplyActions instruction = new OFInstructionApplyActions();
			List<org.openflow.protocol.interfaces.OFInstruction> instructions = 
					new LinkedList<org.openflow.protocol.interfaces.OFInstruction>();
			OFMatchOxm match = new OFMatchOxm();
			OFActionOutput action = new OFActionOutput();
			List<org.openflow.protocol.interfaces.OFAction> actions = 
					new LinkedList<org.openflow.protocol.interfaces.OFAction>();
			
			OFFlowMod fm = (OFFlowMod) OFMessageType.FLOW_MOD.newInstance();
			action.setType(OFActionType.OUTPUT);
			action.setPort(OFPort.of(0xfffffffd)).setMaxLength((short) 0);		//OFPP_CONTROLLER
			action.setMaxLength((short) 0x0);
			action.setLength(action.computeLength());
			actions.add(action);
			instruction.setActions(actions);
			instruction.setType(OFInstructionType.APPLY_ACTIONS); //labry added
			instruction.setLength(instruction.computeLength());//labry added
			instructions.clear();
			instructions.add(instruction);

			fm.setTableId((byte) 0x0)						//the table which the flow entry should be inserted
			.setCommand(OFFlowModCommand.ADD)
			.setIdleTimeout((short) 0)
			.setHardTimeout((short) 0)					//permanent if idle and hard timeout are zero
			.setPriority((short) 0)
			.setBufferId(0x00000000)					//refers to a packet buffered at the switch and sent to the controller
			.setOutGroup(0xffffffff)					//OFPP_ANY
			.setOutPort(OFPort.of(0xffffffff))
			.setMatch(match)
			.setInstructions(instructions)
			.setFlagsWire((short) 0x0001);					//send flow removed message when flow expires or is deleted
			
			conn.write(fm);			
			
			break; //was missing ... labry
		case PORT_STATUS:
			if ( sw == null ) return false;
			
			OFPortStatus ps = (OFPortStatus) m;
			OFPortDesc desc = (OFPortDesc) ps.getDesc();
			if ( ps.getReason() == OFPortReason.DELETE.ordinal() ) {
				deletePort( sw, desc.getPort().get() );
			} else if ( ps.getReason() == OFPortReason.MODIFY.ordinal() ) {
				deletePort( sw, desc.getPort().get() );
				setPort( sw, desc );
			} else {
				setPort( sw, desc );
			}
			
			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
			}
			break;
			
		case PACKET_IN:
			System.out.println("i got it!-versionAdaptor13");
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

	public List<OFMultipartReply> getSwitchStatistics(IOFSwitch sw, OFMultipartRequest req) {
		List<OFMultipartReply> response = new LinkedList<OFMultipartReply>();
		int xid = req.getXid();
		
		this.setResponseCacheItem(sw, xid, response);
		sw.getConnection().write(req);
		synchronized ( response ) {
			try {
				response.wait(1000);
			} catch ( InterruptedException e ) {
				// does nothing
			} finally {
				this.removeResponseCacheItem(sw, xid);
			}
		}
		return response;
	}
	
	public void deliverMultipartReply(IOFSwitch sw, OFMultipartReply m) {
		Object response = getResponseCacheItem(sw, m.getXid());
		if ( response == null ) {
			return;
		}
		if ( response instanceof List<?> ) {
			@SuppressWarnings("unchecked")
			List<OFMultipartReply> rl = (List<OFMultipartReply>) response;
			synchronized( response ) {
				rl.add( m );
				response.notifyAll();
			}
		}
	}
	
	public OFFeaturesReply getFeaturesReply(IOFSwitch sw) {
		OFFeaturesRequest req = new OFFeaturesRequest();
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
	
	public boolean portEnabled(OFPortDesc port) {
		if ( port == null ) 
			return false;
		if ( (port.getConfig() & OFPortConfig.PORT_DOWN) > 0 ) {
			return false;
		}
		return true;
	}
	
	public boolean portEnabled(IOFSwitch sw, short port) {
		OFPortDesc desc = this.getPort(sw, port);
		if ( desc == null ) {
			return false;
		}
		return portEnabled(desc);
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
	
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	
	public OFMatchOxm loadOFMatchFromPacket(byte[] packetData, short inputPort) {
		
		System.out.println("packetData " + bytesToHex(packetData));
		OFMatchOxm.Builder ret = new OFMatchOxm.Builder();
		
		short scratch;
        int transportOffset = 34;
        ByteBuffer packetDataBB = ByteBuffer.wrap(packetData);
        int limit = packetDataBB.limit();

        ret.setInputPort(OFPort.of(inputPort));

//        if (inputPort == OFPortNo.ALL.getValue())
//        	ret.setWildcardsWire( ret.getWildcardsWire() | OFFlowWildcards.IN_PORT );

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
        short data_layer_type = packetDataBB.getShort();
        ret.setDataLayerType(data_layer_type);

        if (data_layer_type != (short) 0x8100) { // need cast to avoid signed
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
            ret.setNetworkProtocol(network_protocol = (byte) (0xff & scratch));

            scratch = packetDataBB.getShort(arpPos + 2);
            // if ipv4 and addr len is 4
            if (scratch == 0x800 && packetDataBB.get(arpPos + 5) == 4) {
                // nw src
            	ret.setNetworkSource(packetDataBB.getInt(arpPos + 14));
            	// nw dst
            	ret.setNetworkDestination(packetDataBB.getInt(arpPos + 24));
//            } else {
//            	ret.setNetworkSource(0);
//            	ret.setNetworkDestination(0);
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
//        	ret.setTransportSource((short)0);
//        	ret.setTransportDestination((short)0);
            break;
        }
        
		return (OFMatchOxm) ret.build();
	}
}
