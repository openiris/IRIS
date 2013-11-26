package etri.sdn.controller.protocol.version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.ver1_3.types.OFPortConfig;
import org.openflow.protocol.ver1_3.types.OFPortReason;
import org.openflow.protocol.ver1_3.types.OFMessageType;
import org.openflow.protocol.ver1_3.types.OFMultipartType;
import org.openflow.protocol.ver1_3.messages.OFEchoReply;
import org.openflow.protocol.ver1_3.messages.OFError;
import org.openflow.protocol.ver1_3.messages.OFFeaturesReply;
import org.openflow.protocol.ver1_3.messages.OFFeaturesRequest;
import org.openflow.protocol.ver1_3.messages.OFHello;
import org.openflow.protocol.ver1_3.messages.OFMatchOxm;
import org.openflow.protocol.ver1_3.messages.OFMultipartDescReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartDescRequest;
import org.openflow.protocol.ver1_3.messages.OFMultipartPortDescReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartPortDescRequest;
import org.openflow.protocol.ver1_3.messages.OFMultipartReply;
import org.openflow.protocol.ver1_3.messages.OFMultipartRequest;
import org.openflow.protocol.ver1_3.messages.OFOxm;
import org.openflow.protocol.ver1_3.messages.OFPortDesc;
import org.openflow.protocol.ver1_3.messages.OFPortStatus;

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
			.setPort(portNum)
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
			.setPort(pi.getPort())
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
		PortInformation pi = this.getPortInformation(sw, portDesc.getPort());
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
			// send port desc request message to retrieve all port list.
			OFMultipartPortDescRequest preq = (OFMultipartPortDescRequest) OFMultipartType.PORT_DESC.newInstance(OFMessageType.MULTIPART_REQUEST);
			conn.write(preq);
			// send switch description request message.
			OFMultipartDescRequest req = 
					(OFMultipartDescRequest) OFMultipartType.DESC.newInstance(OFMessageType.MULTIPART_REQUEST);
			conn.write(req);
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
			if ( r.getMultipartType() == OFMultipartType.PORT_DESC ) {
				OFMultipartPortDescReply portDesc = (OFMultipartPortDescReply) m;
				synchronized ( portLock ) {
					List<OFPortDesc> ports = portDesc.getEntries();
					for ( OFPortDesc port: ports ) {
						setPort(sw, port);
					}
				}
			} else if ( r.getMultipartType() == OFMultipartType.DESC ) {
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
				.setCapabilities(fr.getCapabilities())
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
			
		case PORT_STATUS:
			if ( sw == null ) return false;
			
			OFPortStatus ps = (OFPortStatus) m;
			OFPortDesc desc = ps.getDesc();
			if ( ps.getReason() == OFPortReason.OFPPR_DELETE.ordinal() ) {
				deletePort( sw, desc.getPort() );
			} else if ( ps.getReason() == OFPortReason.OFPPR_MODIFY.ordinal() ) {
				deletePort( sw, desc.getPort() );
				setPort( sw, desc );
			} else {
				setPort( sw, desc );
			}
			
			if ( !getController().handleGeneric(conn, context, m) ) {
				return false;
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
		if ( (port.getConfig() & OFPortConfig.OFPPC_PORT_DOWN) > 0 ) {
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
				result.add(port.getPort());
			}
		}
		return result;
	}
	
	public OFMatchOxm loadOFMatchFromPacket(byte[] packetData, short inputPort) {
		OFMatchOxm ret = new OFMatchOxm();
		
		List<OFOxm> oxmList = new LinkedList<OFOxm>();
		
		
		return ret;
	}
}
