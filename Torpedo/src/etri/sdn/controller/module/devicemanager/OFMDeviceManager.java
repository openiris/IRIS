package etri.sdn.controller.module.devicemanager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.OFPort;

import etri.sdn.controller.IInfoProvider;
import etri.sdn.controller.IOFTask;
import etri.sdn.controller.Main;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.flowcache.IFlowReconcileListener;
import etri.sdn.controller.module.flowcache.OFMatchReconcile;
import etri.sdn.controller.module.topologymanager.ITopologyListener;
import etri.sdn.controller.module.topologymanager.ITopologyService;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.ARP;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.util.Logger;

/**
 * This class implements the device manager module.
 * 
 * <p>Modified the original DeviceManagerImpl class of Floodlight.
 * 
 * @author bjlee, jshin
 *
 */
public class OFMDeviceManager 
extends OFModule 
implements IDeviceService, ITopologyListener, IEntityClassListener, IInfoProvider, IFlowReconcileListener
{
	/**
	 * Time in milliseconds before entities will expire
	 */
	private static final int ENTITY_TIMEOUT = 60*60*1000;

	/**
	 * Time in seconds between cleaning up old entities/devices
	 */
	private static final int ENTITY_CLEANUP_INTERVAL = 60*60;

	private ITopologyService topology;
	private IEntityClassifierService entityClassifier;


	/** 
	 * All the devices that you want.
	 *  
	 * @see Devices
	 */
	private Devices devices;

	/*
	 * Static member functions to reduce unnecessary references to OFMDeviceManager
	 */

	private static ITopologyService topologyServiceCache = null;

	public static ITopologyService getTopologyServiceRef() {
		if ( topologyServiceCache == null ) {
			return topologyServiceCache = (ITopologyService) getModule(ITopologyService.class);
		}
		else {
			return topologyServiceCache;
		}
	}

	private static IEntityClassifierService entityClassifierServiceCache = null;

	public static IEntityClassifierService getEntityClassifierServiceRef() {
		if ( entityClassifierServiceCache == null ) {
			return entityClassifierServiceCache = (IEntityClassifierService) getModule(IEntityClassifierService.class);
		}
		else {
			return entityClassifierServiceCache;
		}
	}

	private static OFMDeviceManager ofmDeviceManagerCache = null;

	public static OFMDeviceManager getRef() {
		if ( ofmDeviceManagerCache == null ) {
			return ofmDeviceManagerCache = (OFMDeviceManager) getModule(IDeviceService.class);
		}
		else {
			return ofmDeviceManagerCache;
		}
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

	/**
	 * Initializes this module.
	 */
	@Override
	protected void initialize() {

		registerModule(IDeviceService.class, this);

		this.topology = getTopologyServiceRef();		
		this.entityClassifier = getEntityClassifierServiceRef();
		this.devices = Devices.getInstance(topology, entityClassifier);

		// 'classes' now has an entry for the class IPv4,
		// and this will create an entry within 'secondaryIndexMap' of ClassIndices object.
		//		addIndex(true, EnumSet.of(DeviceField.IPV4));

		registerFilter(
				OFType.PACKET_IN,
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						OFPacketIn pi = (OFPacketIn) m;
						byte[] data = pi.getData();

						if ( data == null || data.length <= 0 ) {
							return false;
						}

						if ( data[12] == (byte)0x86 && data[13] == (byte)0xdd ) {
							// ethertype == IPv6
							return false;
						}

						if ( data[12] == (byte)0x00 && data[13] == (byte)0x01 ) {
							// ethertype == 0001 (mininet internal?)
							return false;
						}

						return true;		// accept all messages regardless versions.
					}
				}
				);

		if (topology != null) {
			topology.addListener(this);
		}

		entityClassifier.addListener(this);

		this.controller.scheduleTask(
				new IOFTask() {
					@Override
					public boolean execute() {
						cleanupEntities();
						return true;
					}
				}, 
				ENTITY_CLEANUP_INTERVAL * 1000 /* milliseconds */
				);

		if ( Main.debug ) {
			this.controller.scheduleTask(
					new IOFTask() {
						@Override
						public boolean execute() {
							Logger.debug(devices.getHostDebugInfo());
							return true;
						}

					}, 
					1000
					);
		}
	}

	/**
	 * Cleans up expired entities/devices.
	 */
	private void cleanupEntities () {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, -ENTITY_TIMEOUT);
		Date cutoff = c.getTime();

		devices.cleanupEntities(cutoff);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return processPacketInMessage(conn.getSwitch(), (OFPacketIn)msg, context);
	}

	private boolean processPacketInMessage(IOFSwitch sw, OFPacketIn pi, MessageContext cntx) {

		Ethernet eth = (Ethernet) cntx.get(MessageContext.ETHER_PAYLOAD);

		if ( eth == null ) {
			// parse Ethernet header and put into the context
			eth = new Ethernet();
			eth.deserialize(pi.getData(), 0, pi.getData().length);
			cntx.put(MessageContext.ETHER_PAYLOAD, eth);
		}

		// Extract source entity information
		Entity srcEntity = getSourceEntityFromPacket(eth, sw, getInputPort(pi));
		if (srcEntity == null)
			return true;

		// Learn/lookup device information
		Device srcDevice = devices.learnDeviceByEntity(srcEntity);
		if (srcDevice == null)
			return true;

		//		if ( Main.debug ) {
		//			byte[] srcMac = eth.getSourceMACAddress();
		//			byte[] dstMac = eth.getDestinationMACAddress();
		//			short etype = eth.getEtherType();
		//			System.out.printf("%d - src: %02x:%02x:%02x:%02x:%02x:%02x dst: %02x:%02x:%02x:%02x:%02x:%02x %04x\n", 
		//					sw.getId(),
		//					srcMac[0],srcMac[1],srcMac[2],srcMac[3],srcMac[4],srcMac[5], 
		//					dstMac[0],dstMac[1],dstMac[2],dstMac[3],dstMac[4],dstMac[5], etype);
		//		}

		// Store the source device in the context
		cntx.put(MessageContext.SRC_DEVICE, srcDevice);

		// Find the device matching the destination from the entity
		// classes of the source.
		Entity dstEntity = getDestEntityFromPacket(eth);
		Device dstDevice = null;
		if (dstEntity != null) {
			dstDevice = devices.findDestByEntity(srcDevice, dstEntity);
			if (dstDevice != null)
				cntx.put(MessageContext.DST_DEVICE, dstDevice);
		}

		return true;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		//		this.devices.deleteDevice(conn.getSwitch().getId());
		return true;
	}

	/**
	 * Parses an entity from an {@link Ethernet} packet.
	 * 
	 * @param eth 		the packet to parse
	 * @param sw	 	the switch on which the packet arrived
	 * @param ofPort 	the original packetin
	 * 
	 * @return the entity from the packet
	 */
	private Entity getSourceEntityFromPacket(Ethernet eth, IOFSwitch sw, OFPort ofPort) {
		long swdpid = sw.getId();

		byte[] dlAddrArr = eth.getSourceMACAddress();
		long dlAddr = Ethernet.toLong(dlAddrArr);

		// Ignore broadcast/multicast source
		if ((dlAddrArr[0] & 0x1) != 0)
			return null;

		short vlan = eth.getVlanID();
		int nwSrc = getSrcNwAddr(eth, dlAddr);
		return new Entity(dlAddr,
				((vlan >= 0) ? vlan : null),
				((nwSrc != 0) ? nwSrc : null),
				swdpid,
				ofPort,
				new Date());
	}

	/**
	 * Gets IP address from packet if the packet is either an ARP a DHCP packet.
	 * 
	 * @param eth the Ethernet packet
	 * @param dlAddr the Ethernet address
	 * 
	 * @return the source IP address 
	 */
	private int getSrcNwAddr(Ethernet eth, long dlAddr) {
		if (eth.getPayload() instanceof ARP) {
			ARP arp = (ARP) eth.getPayload();
			if ((arp.getProtocolType() == ARP.PROTO_TYPE_IP) &&
					(Ethernet.toLong(arp.getSenderHardwareAddress()) == dlAddr)) {
				return IPv4.toIPv4Address(arp.getSenderProtocolAddress());
			}
		} else if (eth.getPayload() instanceof IPv4) {
			IPv4 ipv4 = (IPv4) eth.getPayload();
			/*
			if (ipv4.getPayload() instanceof UDP) {
				UDP udp = (UDP)ipv4.getPayload();
				if (udp.getPayload() instanceof DHCP) {
					DHCP dhcp = (DHCP)udp.getPayload();
					if (dhcp.getOpCode() == DHCP.OPCODE_REPLY) {
						return ipv4.getSourceAddress();
					}
				}
			}
			 */

			// bjlee - 2013.10.11
			return ipv4.getSourceAddress();
		}
		return 0;
	}

	/**
	 * Gets a (partial) entity for the destination from the packet.
	 * 
	 * @param eth the Ethernet packet
	 * 
	 * @return the entity for the destination
	 */
	private Entity getDestEntityFromPacket(Ethernet eth) {
		byte[] dlAddrArr = eth.getDestinationMACAddress();
		long dlAddr = Ethernet.toLong(dlAddrArr);
		short vlan = eth.getVlanID();
		int nwDst = 0;

		// Ignore broadcast/multicast destination
		if ((dlAddrArr[0] & 0x1) != 0)
			return null;

		if (eth.getPayload() instanceof IPv4) {
			IPv4 ipv4 = (IPv4) eth.getPayload();
			nwDst = ipv4.getDestinationAddress();
		}

		return new Entity(dlAddr,
				((vlan >= 0) ? vlan : null),
				((nwDst != 0) ? nwDst : null),
				null,
				null,
				null);
	}

	/*
	 * Methods for Device class
	 * @see Device
	 */

	/**
	 * Returns the reference of the topology service.
	 * 
	 * @return the reference of the topology service
	 */
	public ITopologyService getTopologyService() {
		return this.topology;
	}

	public Iterator<Device> queryClassByEntity(IEntityClass clazz,
			EnumSet<DeviceField> keyFields,
			Entity entity) {
		return devices.queryClassByEntity(clazz, keyFields, entity);
	}

	/*
	 * IDeviceService methods
	 * @see etri.sdn.controller.module.devicemanager.IDeviceService#getDevice(java.lang.Long)
	 */

	@Override
	public IDevice getDevice(Long deviceKey) {
		return devices.getDevice(deviceKey);
	}

	@Override
	public IDevice findDevice(long macAddress, Short vlan, 
			Integer ipv4Address, Long switchDPID, 
			OFPort switchPort)	throws IllegalArgumentException {
		return devices.findDevice(macAddress, vlan, ipv4Address, switchDPID, switchPort);
	}

	@Override
	public IDevice findDestDevice(IDevice source, long macAddress, Short vlan,
			Integer ipv4Address) throws IllegalArgumentException {
		return devices.findDestDevice(source, macAddress, vlan, ipv4Address);
	}

	@Override
	public Collection<? extends IDevice> getAllDevices() {
		return devices.getAllDevices();
	}

	@Override
	public void addIndex(boolean perClass, EnumSet<DeviceField> keyFields) {
		devices.addIndex(perClass, keyFields);
	}

	@Override
	public Iterator<? extends IDevice> queryDevices(Long macAddress,
			Short vlan, Integer ipv4Address, Long switchDPID, OFPort switchPort) {
		return devices.queryDevices(macAddress, vlan, ipv4Address, switchDPID, switchPort);
	}

	@Override
	public Iterator<? extends IDevice> queryClassDevices(IDevice reference,
			Long macAddress, Short vlan, Integer ipv4Address, Long switchDPID,
			OFPort switchPort) {
		return devices.queryClassDevices(reference, macAddress, vlan, ipv4Address, switchDPID, switchPort);
	}

	@Override
	public void addListener(IDeviceListener listener) {
		devices.addListener(listener);
	}

	@Override
	public void addSuppressAPs(long swId, OFPort port) {
		devices.addSuppressAPs(swId, port);
	}

	@Override
	public void removeSuppressAPs(long swId, OFPort port) {
		devices.removeSuppressAPs(swId, port);
	}

	/*
	 * ITopologyListener methods
	 * @see etri.sdn.controller.module.topologymanager.ITopologyListener#topologyChanged()
	 */

	@Override
	public void topologyChanged() {
		devices.updateAttachmentPoints();
	}

	/*
	 * IEntityClassListener methods
	 * @see etri.sdn.controller.module.devicemanager.IEntityClassListener#entityClassChanged(java.util.Set)
	 */

	@Override
	public void entityClassChanged(Set<String> entityClassNames) {
		devices.reclassify(entityClassNames);
	}

	/*
	 * IInfoProvider methods
	 * @see etri.sdn.controller.IInfoProvider#getInfo(java.lang.String)
	 */

	@Override
	public Map<String, Object> getInfo(String type) {
		if (!"summary".equals(type))
			return null;

		Map<String, Object> info = new HashMap<String, Object>();
		info.put("# hosts", devices.size());
		return info;
	}

	/*
	 * IFlowReconcileListener methods
	 * @see etri.sdn.controller.module.flowcache.IFlowReconcileListener#reconcileFlows(java.util.ArrayList)
	 * 
	 * TODO:
	 * We temporarily dump this method because the Floodlight 0.90 does not fully implement
	 * the Flow Cache and Port Down Reconciliation facilities. (really?)
	 * But in the near future, we will implement this method. 
	 * And the implementation requires Counter services are also fully implemented.
	 */

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "OFMDeviceManager";
	}

	@Override
	public etri.sdn.controller.IListener.Command reconcileFlows(
			ArrayList<OFMatchReconcile> ofmRcList) {
		return etri.sdn.controller.IListener.Command.CONTINUE;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.devices };
	}
}
