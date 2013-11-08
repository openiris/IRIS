package etri.sdn.controller.protocol.version;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.factory.OFMessageFactory;


import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.PortInformation;
import etri.sdn.controller.SwitchInformation;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;

public abstract class VersionAdaptor {

//	private static VersionedMessageFactory message_factory = new VersionedMessageFactory();
	
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
	
	public VersionAdaptor(OFController controller) {
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
	
	public static final OFMessageFactory getMessageFactory() {
		return new VersionedMessageFactory();
	}
	
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

	
	public abstract boolean handleConnectedEvent(Connection conn);
	
	public abstract boolean process(Connection conn, MessageContext context, OFMessage msg);
}
