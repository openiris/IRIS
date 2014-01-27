package etri.sdn.controller.module.linkdiscovery;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.openflow.protocol.OFBPortState;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.protocol.interfaces.OFAction;
import org.openflow.protocol.interfaces.OFActionOutput;
import org.openflow.protocol.interfaces.OFMessageType;
import org.openflow.protocol.interfaces.OFPacketIn;
import org.openflow.protocol.interfaces.OFPacketOut;
import org.openflow.protocol.interfaces.OFPortConfig;
import org.openflow.protocol.interfaces.OFPortDesc;
import org.openflow.protocol.interfaces.OFPortReason;
import org.openflow.protocol.interfaces.OFPortState;
import org.openflow.protocol.interfaces.OFPortStatus;
import org.openflow.util.HexString;

import etri.sdn.controller.IOFTask;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener.LDUpdate;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener.UpdateOperation;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFHandler.Role;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.packet.BSN;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.protocol.packet.LLDP;
import etri.sdn.controller.protocol.packet.LLDPTLV;
import etri.sdn.controller.util.Logger;

/**
 * Link Discovery Module.
 * This module implements ILinkDiscoveryService.
 * This service is used by {@link etri.sdn.controller.module.topologymanager.OFMTopologyManager}.
 * 
 * @author bjlee
 *
 */
public class OFMLinkDiscovery extends OFModule implements ILinkDiscoveryService {

	//
	// LLDP and BDDP fields
	//
	
	/**
	 * LLDP Data Units (LLDPDUs) are sent to the destination MAC address 01:80:c2:00:00:0e. 
	 * This address is defined as the "LLDP_Multicast" address. 
	 * This address is defined within a range of addresses reserved by the IEEE for protocols 
	 * that are to be constrained to an individual LAN. 
	 * AN LLDPDU will not be forwarded by MAC bridges (e.g. switches) that conform to IEEE Std 802.1D-2004.
	 */
	private static final byte[] LLDP_STANDARD_DST_MAC_STRING = 
		HexString.fromHexString("01:80:c2:00:00:0e");
	private static final long LINK_LOCAL_MASK  = 0xfffffffffff0L;
	private static final long LINK_LOCAL_VALUE = 0x0180c2000000L;

	// BigSwitch OUI is 5C:16:C7, so 5D:16:C7 is the multicast version
	// private static final String LLDP_BSN_DST_MAC_STRING = "5d:16:c7:00:00:01";
	private static final String LLDP_BSN_DST_MAC_STRING = "ff:ff:ff:ff:ff:ff";

	private static final byte TLV_DIRECTION_TYPE = 0x73;
	private static final short TLV_DIRECTION_LENGTH = 1;  // 1 byte
	private static final byte TLV_DIRECTION_VALUE_FORWARD[] = {0x01};
	private static final byte TLV_DIRECTION_VALUE_REVERSE[] = {0x02};

	protected final int DISCOVERY_TASK_INTERVAL = 1; 	// 1 second.
	protected final int LLDP_TO_ALL_INTERVAL = 15 ; 	//15 seconds.
	protected long lldpClock = 0;
	
	/**
	 * LLDP frequency for known links.
	 * This value is intentionally kept higher than LLDP_TO_ALL_INTERVAL.
	 * If we want to identify link failures faster, we could decrease this
	 * value to a small number, say 1 or 2 sec.
	 */
	protected final int LLDP_TO_KNOWN_INTERVAL = 20;

	/**
	 * Quarantine Task. 100ms.
	 */
	protected final int BDDP_TASK_INTERVAL = 100; 
	
	/**
	 * Quarantine Task. # of ports per iteration.
	 */
	protected final int BDDP_TASK_SIZE = 5;

	private static final LLDPTLV forwardTLV 
	= new LLDPTLV().
	setType((byte)TLV_DIRECTION_TYPE).
	setLength((short)TLV_DIRECTION_LENGTH).
	setValue(TLV_DIRECTION_VALUE_FORWARD);

	private static final LLDPTLV reverseTLV 
	= new LLDPTLV().
	setType((byte)TLV_DIRECTION_TYPE).
	setLength((short)TLV_DIRECTION_LENGTH).
	setValue(TLV_DIRECTION_VALUE_REVERSE);

	protected LLDPTLV controllerTLV;

	/** 
	 * A list of ports that are quarantined for discovering links through
	 * them.  Data traffic from these ports are not allowed until the ports
	 * are released from quarantine.
	 */
	protected LinkedBlockingQueue<NodePortTuple> quarantineQueue = new LinkedBlockingQueue<NodePortTuple>();
	
	protected LinkedBlockingQueue<NodePortTuple> maintenanceQueue = new LinkedBlockingQueue<NodePortTuple>();
	
	protected BlockingQueue<LDUpdate> updates = new LinkedBlockingQueue<LDUpdate>();
	
	/** 
	 * topology aware components are called in the order they were added to the
	 * the array 
	 */
	private ArrayList<ILinkDiscoveryListener> linkDiscoveryAware = new ArrayList<ILinkDiscoveryListener>();
	
	/**
	 * structure that holds a disconnected switches temporarily.
	 */
	private class Disconnection {
		private long timestamp;
		private long switchId;
		private Set<Short> ports;
		
		public Disconnection(IOFSwitch sw) {
			this.timestamp = Calendar.getInstance().getTimeInMillis();
			this.switchId = sw.getId();
			this.ports = new HashSet<Short>();
//			for ( OFPhysicalPort p : sw.getPorts() ) {
			for ( OFPortDesc p : protocol.getPorts(sw) ) {
				this.ports.add((short) p.getPort().get());
			}
		}
		
		public long getSwitchId() {
			return this.switchId;
		}
		
		public long getTimestamp() {
			return this.timestamp;
		}
		
		public Set<Short> getPorts() {
			return this.ports;
		}
	}
	
	/**
	 * This is a set to hold information about disconnected switches and their ports temporarily.
	 */
	private Set<Disconnection> disconnections = Collections.synchronizedSet(new HashSet<Disconnection>());
	
	/**
	 * Model of this module. initialized within initialize()
	 */
	private Links links = null;
	private OFProtocol protocol;
	
	/**
	 * Constructor that does nothing.
	 */
	public OFMLinkDiscovery() {
		// does nothing.
	}
	
	/**
	 * Initialize this module to receive 
	 * 
	 * <ol>
	 * <li> PACKET_IN messages with LLDP packets (Ethertype 0x88cc)
	 * <li> PORT_STATUS messages 
	 * </ol>
	 * 
	 * Periodic tasks are also initiated. 
	 * 
	 */
	@Override
	public void initialize() {
		// initialize Links object with proper manager reference.
		this.links = new Links(this);
		
		this.protocol = getController().getProtocol();
		
		// initialize controller TLV
		setControllerTLV();
		
		registerModule(ILinkDiscoveryService.class, this);

		// I will receive PACKET_IN messages selectively.
		registerFilter(
				OFMessageType.PACKET_IN, 
				new OFMFilter() {

					@Override
					public boolean filter(OFMessage m) {
						// we process all PACKET_IN regardless of its version.
						OFPacketIn pi = (OFPacketIn) m;
						
						// this checks if the Packet-In is for LLDP!
						// This is very important to guarantee maximum performance. (-_-;)
						if ( pi.getLength() < (short)14 || (pi.getData()[12] != (byte)0x88 || pi.getData()[13] != (byte)0xcc )) {
							// this packet is not mine!
							return false;
						}
						return true;
					}

				}
		);
		
		// I will receive all PORT_STATUS messages.
		registerFilter(
				OFMessageType.PORT_STATUS,
				new OFMFilter() {
					@Override
					public boolean filter(OFMessage m) {
						// we process all PORT_STATUS messages regardless of their verison.
						return true;
					}
				}
		);
		
		// register discovery task
		initiatePeriodicDiscovery();
		initiatePeriodicQuarantineWorker();
		initiatePeriodicTopologyUpdate();
	}
	
	/**
	 * This method is called by Links object to pass link update event to its listeners. 
	 * called by the following methods: 
	 * 
	 * (1) {@link Links#addOrUpdateLink(Link, LinkInfo)}
	 * (2) {@link Links#timeoutLinks()}
	 * (3) {@link Links#updatePortStatus(Long, short, OFPortStatus)}
	 * 
	 * @param lt Link object
	 * @param info LinkInfo object
	 */
	public void addLinkUpdate(Link lt, LinkInfo info) {
		UpdateOperation operation = getUpdateOperation(info.getSrcPortState(),
				info.getDstPortState());
		updates.add(new LDUpdate(lt.getSrc(), lt.getSrcPort(),
				lt.getDst(), lt.getDstPort(),
				getLinkType(lt, info),
				operation));
	}
	
	/**
	 * This method is called by Links object to pass link update event to its listeners. 
	 * Called by the following methods:
	 * 
	 * (1) {@link Links#addOrUpdateLink(Link, LinkInfo)}
	 * (2) {@link Links#deleteLinks(List<Link>)}
	 * 
	 * @param lt Link object
	 * @param info LinkInfo object
	 * @param operation UpdateOperation value
	 */
	public void addLinkUpdate(Link lt, LinkInfo info, UpdateOperation operation) {
		updates.add(new LDUpdate(lt.getSrc(), lt.getSrcPort(),
				lt.getDst(), lt.getDstPort(),
				getLinkType(lt, info),
				operation));
	}
	
	/**
	 * Add a new LDUpdate object to the {@link #updates} queue. 
	 * 
	 * @param switchId	identifier of the switch
	 * @param portNum	number of the port
	 * @param op		update operation. see {@link ILinkDiscoveryService} 
	 */
	public void addLinkUpdate(long switchId, int portNum, UpdateOperation op) {
		updates.add(new LDUpdate(switchId, portNum, op));
	}
	
	/**
	 * Add a new LDUpdate object to the {@link #updates} queue. 
	 * 
	 * @param switchId	identifier of the switch
	 * @param portNum	port number
	 * @param status	status of the link. with this value, UpdateOperation is calculated. 
	 */
	public void addLinkUpdate(long switchId, int portNum, int status) {
		addLinkUpdate(switchId, portNum, getUpdateOperation(status));
	}
	
	/**
	 * This method is called by Links object to make manager to send link discovery messages to peers.
	 * internally call {@link #sendDiscoveryMessage(IOFSwitch, short, boolean, boolean)}.
	 * 
	 * @param switchId			ask this switch to send discovery message
	 * @param destinationPort	to which port to send the discovery message
	 * @param isStandard		LLDP(true) or BDDP(false)
	 * @param isReverse			reverse(true) or non-reverse(false)
	 */
	public void sendDiscoveryMessage(long switchId, short destinationPort, boolean isStandard, boolean isReverse) {
		// this internally calls private member function (relay).
		sendDiscoveryMessage(this.controller.getSwitch(switchId), destinationPort, isStandard, isReverse);
	}

	/**
	 * Find out if the port is enabled. 
	 * if the port or link is down, false is returned. 
	 * Otherwise, true is returned. 
	 * 
	 * @param port	OFPhysicalPort object
	 * @return		true(enabled), false(disabled)
	 */
//	private static boolean portEnabled(OFPhysicalPort port) {
	private static boolean portEnabled(OFPortDesc port) {
		if (port == null)
			return false;
		if (port.getConfig().contains(OFPortConfig.PORT_DOWN))
			return false;
		if (port.getState().contains(OFPortState.LINK_DOWN))
			return false;
		// Port STP state doesn't work with multiple VLANs, so ignore it for now
		// if ((port.getState() & OFPortState.STP_MASK.getValue()) == OFPortState.STP_BLOCK.getValue())
		//    return false;
		return true;
	}

	/**
	 * register listeners to the {@link ILinkDiscoveryService}. 
	 */
	@Override
	public void addListener(ILinkDiscoveryListener listener) {
		linkDiscoveryAware.add( listener );
	}

	/**
	 * This is an initialization routine for {@link #controllerTLV}. 
	 */
	private void setControllerTLV() {
		//Setting the controllerTLVValue based on current nano time,
		//controller's IP address, and the network interface object hash
		//the corresponding IP address.

		final int prime = 7867;
		InetAddress localIPAddress = null;
		NetworkInterface localInterface = null;

		byte[] controllerTLVValue = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};  // 8 byte value.
		ByteBuffer bb = ByteBuffer.allocate(10);

		try{
			localIPAddress = java.net.InetAddress.getLocalHost();
			localInterface = NetworkInterface.getByInetAddress(localIPAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long result = System.nanoTime();
		if (localIPAddress != null)
			result = result * prime + IPv4.toIPv4Address(localIPAddress.getHostAddress());
		if (localInterface != null)
			result = result * prime + localInterface.hashCode();
		// set the first 4 bits to 0.
		result = result & (0x0fffffffffffffffL);

		bb.putLong(result);

		bb.rewind();
		bb.get(controllerTLVValue, 0, 8);

		// type is set to 0x0c (12) which means the TLV is created by the controller. 
		this.controllerTLV = new LLDPTLV().setType((byte) 0x0c).setLength((short) controllerTLVValue.length).setValue(controllerTLVValue);
	}

	private void initiatePeriodicDiscovery() {

		// registered task will be re-executed every DISCOVERY_TASK_INTERVAL seconds.
		this.controller.scheduleTask(
				new IOFTask() {
					public boolean execute() {
						Role role = controller.getRole();
						if ( role == null || role == Role.MASTER || role == Role.EQUAL ) {
							cleanDisconnectedSwitches();
							discoverLinks();
						}
						return true;
					}
				}, 
				DISCOVERY_TASK_INTERVAL * 1000 /* milliseconds */);
	}
	
	/**
	 * This function removes link information on ports from the disconnected switches. 
	 */
	private void cleanDisconnectedSwitches() {
		Set<Disconnection> to_remove = new HashSet<Disconnection>();
		
		for ( Disconnection dcn : this.disconnections ) {
			if ( Calendar.getInstance().getTimeInMillis() - dcn.getTimestamp() > 10000 /* 10 seconds */ ) {
				to_remove.add(dcn);
				long id = dcn.getSwitchId();
				for ( Short port : dcn.getPorts() ) {
					this.links.deleteLinksOnPort(new NodePortTuple(id, port));
				}
			}
		}
		
		this.disconnections.removeAll(to_remove);
	}

	private void initiatePeriodicQuarantineWorker() {
		this.controller.scheduleTask(
				new IOFTask() {

					/**
					 * This method processes the quarantine list in bursts.  The task is
					 * at most once per BDDP_TASK_INTERVAL.
					 * One each call, BDDP_TASK_SIZE number of switch ports are processed.
					 * Once the BDDP packets are sent out through the switch ports, the ports
					 * are removed from the quarantine list.
					 */
					public boolean execute() {
						int count = 0;
						Set<NodePortTuple> nptList = new HashSet<NodePortTuple>();

						while(count < BDDP_TASK_SIZE && quarantineQueue.peek() !=null) {
							NodePortTuple npt;
							npt = quarantineQueue.remove();
							sendDiscoveryMessage(controller.getSwitch(npt.getNodeId()), npt.getPortId(), false, false);
							nptList.add(npt);
							count++;
						}

						count = 0;
						while (count < BDDP_TASK_SIZE && maintenanceQueue.peek() != null) {
							NodePortTuple npt;
							npt = maintenanceQueue.remove();
							sendDiscoveryMessage(controller.getSwitch(npt.getNodeId()), npt.getPortId(), false, false);
							count++;
						}

						for(NodePortTuple npt:nptList) {
							generateSwitchPortStatusUpdate(controller.getSwitch(npt.getNodeId()), npt.getPortId());
						}

						return true;
					}
				},
				BDDP_TASK_INTERVAL /* milliseconds */);
	}

	private void generateSwitchPortStatusUpdate(IOFSwitch sw, short port) {
		
		UpdateOperation operation;

		if (sw == null) return;

//		OFPhysicalPort ofp = sw.getPort(port);
		OFPortDesc ofp = protocol.getPort(sw, port);
		if (ofp == null) return;

		Set<OFPortState> state = ofp.getState();
		boolean portUp = (state.contains(OFPortState.STP_BLOCK) || 
						  state.contains(OFPortState.LINK_DOWN) ||
						  state.contains(OFPortState.BLOCKED))? false : true;

		if (portUp) operation = UpdateOperation.PORT_UP;
		else operation = UpdateOperation.PORT_DOWN;

		updates.add(new LDUpdate(sw.getId(), port, operation));
	}

	private void initiatePeriodicTopologyUpdate() {
		this.controller.scheduleTask(
				new IOFTask() {
					public boolean execute() {
						do {
							LDUpdate update = null;
							try { 
								update = updates.remove();
							} catch ( NoSuchElementException e ) {
								// no element in the queue.
								return true;
							}

							try {
								for (ILinkDiscoveryListener lda : linkDiscoveryAware) { // order maintained
									lda.linkDiscoveryUpdate(update);
								}
							}
							catch (Exception e) {
								Logger.stderr("Error in link discovery updates loop: " + e);
							}

						} while ( true );
					}
				},
				300 /* milliseconds */);
	}

	/**
	 * Add a switch port to the quarantine queue. Schedule the
	 * quarantine task if the quarantine queue was empty before adding
	 * this switch port.
	 * @param npt
	 */
	@SuppressWarnings("unused")
	private void addToQuarantineQueue(NodePortTuple npt) {
		if (quarantineQueue.contains(npt) == false)
			quarantineQueue.add(npt);
	}

	/**
	 * Remove a switch port from the quarantine queue.
	 */
	private void removeFromQuarantineQueue(NodePortTuple npt) {
		// Remove all occurrences of the node port tuple from the list.
		while (quarantineQueue.remove(npt));
	}

	/**
	 * Add a switch port to maintenance queue.
	 * 
	 * @param npt	{@link NodePortTuple} object.
	 */
	private void addToMaintenanceQueue(NodePortTuple npt) {
		// TODO We are not checking if the switch port tuple is already
		// in the maintenance list or not.  This will be an issue for
		// really large number of switch ports in the network.
		
		if (maintenanceQueue.contains(npt) == false)
			maintenanceQueue.add(npt);
	}

	/**
	 * Remove a switch port from maintenance queue.
	 * 
	 * @param npt	{@link NodePortTuple} object.
	 */
	private void removeFromMaintenanceQueue(NodePortTuple npt) {
		// Remove all occurrences of the node port tuple from the queue.
		while (maintenanceQueue.remove(npt));
	}

	@Override
	public ILinkDiscovery.LinkType getLinkType(Link lt, LinkInfo info) {
		if (info.getUnicastValidTime() != null) {
			return ILinkDiscovery.LinkType.DIRECT_LINK;
		} else if (info.getMulticastValidTime() != null) {
			return ILinkDiscovery.LinkType.MULTIHOP_LINK;
		}
		return ILinkDiscovery.LinkType.INVALID_LINK;
	}

	private void discoverLinks() {

		// timeout known links.
		this.links.timeoutLinks();

		//increment LLDP clock
		lldpClock = (lldpClock + 1)% LLDP_TO_ALL_INTERVAL;

		if (lldpClock == 0) {
			discoverOnAllPorts();
			//			Logger.stdout(this.toString());
		}
	}

	private UpdateOperation getUpdateOperation(int srcPortState) {
		boolean portUp = ((srcPortState & OFBPortState.STP_MASK) != OFBPortState.STP_BLOCK &&
				(srcPortState & OFBPortState.LINK_DOWN) == 0 &&
				(srcPortState & OFBPortState.BLOCKED) == 0)? true: false;

		if (portUp) return UpdateOperation.PORT_UP;
		else return UpdateOperation.PORT_DOWN;
	}

	private UpdateOperation getUpdateOperation(int srcPortState, int dstPortState) {
		boolean srcPortUp = ((srcPortState & OFBPortState.STP_MASK) != OFBPortState.STP_BLOCK &&
				(srcPortState & OFBPortState.LINK_DOWN) == 0 &&
				(srcPortState & OFBPortState.BLOCKED) == 0)? true: false;
		boolean dstPortUp = ((dstPortState & OFBPortState.STP_MASK) != OFBPortState.STP_BLOCK &&
				(dstPortState & OFBPortState.LINK_DOWN) == 0 &&
				(dstPortState & OFBPortState.BLOCKED) == 0)? true: false;

		boolean added = srcPortUp && dstPortUp;

		if (added) return UpdateOperation.LINK_UPDATED;
		return UpdateOperation.LINK_REMOVED;
	}

	/**
	 * Send LLDPs to all switch-ports
	 */
	private void discoverOnAllPorts() {

		for ( IOFSwitch sw : controller.getSwitches() ) {
			if ( sw == null ) continue;

			Collection<OFPortDesc> pports = protocol.getEnabledPorts(sw);

			if ( pports != null ) {

				for ( OFPortDesc ofp: pports ) {

					sendDiscoveryMessage(sw, (short) ofp.getPort().get(), true, false);

					// If the switch port is not already in the maintenance
					// queue, add it.
					NodePortTuple npt = new NodePortTuple(sw.getId(), ofp.getPort().get());
					addToMaintenanceQueue(npt);
				}
			}
		}
	}

	/**
	 * Send link discovery message out of a given switch port.
	 * The discovery message may be a standard LLDP or a modified
	 * LLDP, where the dst mac address is set to :ff.  
	 * 
	 * TODO: The modified LLDP will updated in the future and may use a different eth-type.
	 * 
	 * @param sw
	 * @param port
	 * @param isStandard   indicates standard or modified LLDP
	 * @param isReverse    indicates whether the LLDP was sent as a response
	 * @return return false value if we cannot send discovery message to output stream to switch
	 */
	private boolean sendDiscoveryMessage(IOFSwitch sw, int port, boolean isStandard, boolean isReverse) {

		if (sw == null) {
			return true;
		}

		if (port == OFPort.LOCAL.get())
			return true;

		OFPortDesc ofpPort = protocol.getPort(sw, port);

		if (ofpPort == null) {
			Logger.error("sw: %d,  port: %d is null", sw.getId(), port);
			return true;
		}

		// using "nearest customer bridge" MAC address for broadest possible propagation
		// through provider and TPMR bridges (see IEEE 802.1AB-2009 and 802.1Q-2011),
		// in particular the Linux bridge which behaves mostly like a provider bridge
		byte[] chassisId = new byte[] {4, 0, 0, 0, 0, 0, 0}; // filled in later
		byte[] portId = new byte[] {2, 0, 0}; // filled in later
		byte[] ttlValue = new byte[] {0, 0x78};
		// OpenFlow OUI - 00-26-E1
		byte[] dpidTLVValue = new byte[] {0x0, 0x26, (byte) 0xe1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		LLDPTLV dpidTLV = new LLDPTLV().setType((byte) 127).setLength((short) dpidTLVValue.length).setValue(dpidTLVValue);

		byte[] dpidArray = new byte[8];
		ByteBuffer dpidBB = ByteBuffer.wrap(dpidArray);
		ByteBuffer portBB = ByteBuffer.wrap(portId, 1, 2);

		Long dpid = sw.getId();

		dpidBB.putLong(dpid);
		// set the ethernet source mac to last 6 bytes of dpid
//		System.arraycopy(dpidArray, 2, ofpPort.getHardwareAddress(), 0, 6);
		System.arraycopy(dpidArray, 2, ofpPort.getHwAddr(), 0, 6);
		// set the chassis id's value to last 6 bytes of dpid
		System.arraycopy(dpidArray, 2, chassisId, 1, 6);
		// set the optional tlv to the full dpid
		System.arraycopy(dpidArray, 0, dpidTLVValue, 4, 8);

		// set the portId to the outgoing port
		portBB.putShort( (short) port );

		LLDP lldp = new LLDP();
		lldp.setChassisId(new LLDPTLV().setType((byte) 1).setLength((short) chassisId.length).setValue(chassisId));
		lldp.setPortId(new LLDPTLV().setType((byte) 2).setLength((short) portId.length).setValue(portId));
		lldp.setTtl(new LLDPTLV().setType((byte) 3).setLength((short) ttlValue.length).setValue(ttlValue));
		lldp.getOptionalTLVList().add(dpidTLV);

		// Add the controller identifier to the TLV value.
		lldp.getOptionalTLVList().add(controllerTLV);
		if (isReverse) {
			lldp.getOptionalTLVList().add(reverseTLV);
		}else {
			lldp.getOptionalTLVList().add(forwardTLV);
		}

		Ethernet ethernet;
		if (isStandard) {
			ethernet = new Ethernet()
//			.setSourceMACAddress(ofpPort.getHardwareAddress())
			.setSourceMACAddress(ofpPort.getHwAddr())
			.setDestinationMACAddress(LLDP_STANDARD_DST_MAC_STRING)
			.setEtherType(Ethernet.TYPE_LLDP);
			ethernet.setPayload(lldp);
		} else {
			BSN bsn = new BSN(BSN.BSN_TYPE_BDDP);
			bsn.setPayload(lldp);

			ethernet = new Ethernet()
//			.setSourceMACAddress(ofpPort.getHardwareAddress())
			.setSourceMACAddress(ofpPort.getHwAddr())
			.setDestinationMACAddress(LLDP_BSN_DST_MAC_STRING)
			.setEtherType(Ethernet.TYPE_BSN);
			ethernet.setPayload(bsn);
		}

		// serialize and wrap in a packet out
		byte[] data = ethernet.serialize();
		OFPacketOut po = OFMessageFactory.createPacketOut(sw.getVersion());
		
		po.setBufferId(0xffffffff /* OFPacketOut.BUFFER_ID_NONE */);
		po.setInputPort(OFPort.NONE);

		// set actions
		List<OFAction> actions = new ArrayList<OFAction>();
		OFActionOutput action_output = OFMessageFactory.createActionOutput(sw.getVersion());
		action_output.setPort(OFPort.of(port)).setMaxLength((short)0);
		actions.add(action_output);
		po.setActions(actions);
		po.setActionsLength( action_output.computeLength() );

		// set data
		po.setData(data);
		po.setLength( po.computeLength() );

		return sw.getConnection().write(po);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn, MessageContext context) {
		IOFSwitch sw = conn.getSwitch();
		if ( sw == null) {
			Logger.stderr("[Torpedo] CRITICAL: switch is null for connection");
			return false;
		}

//		if ( sw.getEnabledPorts() != null) {
//			for (Short p : sw.getEnabledPortNumbers()) {
//				processNewPort(sw, p);
//			}
//		}
		if ( protocol.getEnabledPorts(sw) != null ) {
			for ( Integer p : protocol.getEnabledPortNumbers(sw) ) {
				processNewPort(sw, p);
			}
		}
		
		Set<Disconnection> re_connected = new HashSet<Disconnection>();
		for( Disconnection d : this.disconnections ) {
			if ( d.getSwitchId() == conn.getSwitch().getId() ) {
				re_connected.add(d);
			}
		}
		this.disconnections.removeAll(re_connected);

		LDUpdate update = new LDUpdate(sw.getId(), null, UpdateOperation.SWITCH_UPDATED);
		updates.add(update);
		return true;
	}
	
	@Override
	protected boolean handleDisconnect(Connection conn) {
		try { 
			this.disconnections.add(new Disconnection(conn.getSwitch()));
		} catch ( Exception e ) {
			// this connection is cut before the FEATURE_REPLY is exchanged. 
			// thus, we do not need to add to the set.
		}
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing) {
		switch (msg.getType()) {
		case PACKET_IN:
			return this.handlePacketIn(conn.getSwitch(), context, (OFPacketIn) msg, outgoing);
		case PORT_STATUS:
			return this.handlePortStatus(conn.getSwitch(), context, (OFPortStatus) msg, outgoing);
		default:
			break;
		}

		return true;
	}

	/**
	 * This method handles a PACKET_IN message as follows:
	 * 
	 * <ol>
	 * <li> First, decapsulate the Ethernet part of the PACKET_IN payload.
	 * <li> if the ETHERTYPE of the header is BSN and its payload is LLDP,
	 *      that means a BDDP packet (non-standard bigswitch-specific LLDP) is 
	 *      received. If not, just return true to pass this PACKET_IN to other module. 
	 *      The BDDP packet is handled by calling {@link #handleLldp(LLDP, IOFSwitch, OFPacketIn, boolean, List)}.
	 * <li> If the ETHERTYPE of the header is LLDP, then handle the packet 
	 *      by calling {@link #handleLldp(LLDP, IOFSwitch, OFPacketIn, boolean, List)}.
	 * <li> If the ETHERTYPE of the header is smaller than (<) 1500 and 
	 *      destMac & LINK_LOCAL_MASK) == LINK_LOCAL_VALUE, then we just return false 
	 *      to suppress the further processing of the PACKET_IN msg. 
	 * </ol>
	 * 
	 * However, in the current OFMLinkDiscovery Implementation, 
	 * As {@link #initialize()} has been coded only to accept the standard LLDP 
	 * packets with Ethertype 0x88cc, there are other cases that never be executed. 
	 * 
	 * @param sw
	 * @param context
	 * @param pi
	 * @param outgoing
	 * @return
	 */
	private boolean handlePacketIn(IOFSwitch sw, MessageContext context, OFPacketIn pi, List<OFMessage> outgoing) {

		Ethernet eth = (Ethernet) context.get(MessageContext.ETHER_PAYLOAD);

		if ( eth == null ) {
			// parse Ethernet header and put into the context
			eth = new Ethernet();
//			eth.deserialize(pi.getPacketData(), 0, pi.getPacketData().length);
			eth.deserialize(pi.getData(), 0, pi.getData().length);
			context.put(MessageContext.ETHER_PAYLOAD, eth);
		}

		if(eth.getEtherType() == Ethernet.TYPE_BSN) {
			BSN bsn = (BSN) eth.getPayload();
			if (bsn == null) return false;
			if (bsn.getPayload() == null) return false;
			// It could be a packet other than BSN LLDP, therefore
			// continue with the regular processing.
			if (bsn.getPayload() instanceof LLDP == false) {
				// this packet-in is not for me
				return true;
			}

			return handleLldp((LLDP) bsn.getPayload(), sw, pi, false, outgoing);
		} else if (eth.getEtherType() == Ethernet.TYPE_LLDP)  {
			return handleLldp((LLDP) eth.getPayload(), sw, pi, true, outgoing);
		} else if (eth.getEtherType() < 1500) {
			long destMac = eth.getDestinationMAC().toLong();
			if ((destMac & LINK_LOCAL_MASK) == LINK_LOCAL_VALUE){
				return false;
			}
		}

		// If packet-in is from a quarantine port, stop processing.
		NodePortTuple npt = new NodePortTuple(sw.getId(), pi.getInputPort().get());
		if (quarantineQueue.contains(npt)) {
			return false;
		}

		// this packet-in is not for me
		return true;
	}

	/**
	 * From the LLDP packet received, this method first extract following information:
	 * 
	 * <ol>
	 * <li> remote switch information
	 * <li> controller id (if this message is created by a controller)
	 * <li> direction of the message 
	 * </ol>
	 * 
	 * The business logic of this method are as follows:
	 * 
	 * <ol>
	 * <li> if the message is a standard LLDP which is not created by this controller, then drop it. 
	 * <li> if the message is a non-standard LLDP which is not created by this controller, then return true
	 *     to allow the further processing. (maybe forwarding?)
	 * <li> if the remote switch or one of the sides of the link port is not enabled, then drop it. 
	 * <li> or, call {@link Links#addOrUpdateLink(long, short, OFPhysicalPort, long, short, OFPhysicalPort, boolean, boolean)},
	 *     remove the node & port pair (both side) from the Quarantine and Maintenance Queue and drop the message.
	 * </ol>
	 * 
	 * @param lldp			LLDP packet to process
	 * @param sw			the switch that the lldp packet is received
	 * @param pi			OFPacketIn message itself
	 * @param isStandard	true(standard LLDP), or false
	 * @param outgoing		list of OFMessage objects to be delivered to switches after this method ends the execution
	 * @return				true to further process the message, or false.
	 */
	private boolean handleLldp(LLDP lldp, IOFSwitch sw, OFPacketIn pi, boolean isStandard, List<OFMessage> outgoing) {
		// If LLDP is suppressed on this port, ignore received packet as well
		if (sw == null) {
			return false;
		}

		// If this is a malformed LLDP, or not from us, exit
		if (lldp.getPortId() == null || lldp.getPortId().getLength() != 3) {
			return true;
		}

		long myId = ByteBuffer.wrap(controllerTLV.getValue()).getLong();
		long otherId = 0;
		boolean myLLDP = false;
		Boolean isReverse = null;

		ByteBuffer portBB = ByteBuffer.wrap(lldp.getPortId().getValue());
		portBB.position(1);

		Short remotePort = portBB.getShort();
		IOFSwitch remoteSwitch = null;

		// Verify this LLDP packet matches what we're looking for
		for (LLDPTLV lldptlv : lldp.getOptionalTLVList()) {
			// '127' means 'Organizationally specific TLV'.
			if (lldptlv.getType() == 127 && lldptlv.getLength() == 12 &&
					lldptlv.getValue()[0] == 0x0 && lldptlv.getValue()[1] == 0x26 &&
					lldptlv.getValue()[2] == (byte)0xe1 && lldptlv.getValue()[3] == 0x0) {
				ByteBuffer dpidBB = ByteBuffer.wrap(lldptlv.getValue());
				remoteSwitch = this.controller.getSwitch(dpidBB.getLong(4));
			} 
			// if type is set to 12 (0x0c), it means the TLV is created by the controller
			// (controllerTLV). 
			else if (lldptlv.getType() == 12 && lldptlv.getLength() == 8){
				otherId = ByteBuffer.wrap(lldptlv.getValue()).getLong();
				if (myId == otherId)
					myLLDP = true;
			} 
			else if (lldptlv.getType() == TLV_DIRECTION_TYPE &&
					lldptlv.getLength() == TLV_DIRECTION_LENGTH) {
				if (lldptlv.getValue()[0] == TLV_DIRECTION_VALUE_FORWARD[0])
					isReverse = false;
				else if (lldptlv.getValue()[0] == TLV_DIRECTION_VALUE_REVERSE[0])
					isReverse = true;
			}
		}

		if (myLLDP == false) {
			// This is not the LLDP sent by this controller.
			// If the LLDP message has multicast bit set, then we need to broadcast
			// the packet as a regular packet.
			if (isStandard) {
				// if the packet is not standard LLDP (also not created by the controller) 
				// then we suppress the further processing of this packet. 
				return false;
			}
			else if (myId < otherId)  {
				// if this LLDP is created by the other controller 
				// and myId value is smaller than the ID of the controller,
				// we allow the further processing of this LLDP.
				return true;
			}
		}

		if (remoteSwitch == null) {
			// process no further
			return false;
		}

//		if (!remoteSwitch.portEnabled(remotePort)) {
		if ( !protocol.portEnabled(remoteSwitch, remotePort) ) {
			// process no further
			return false;
		}

//		if (!sw.portEnabled(pi.getInPort())) {
		if ( !protocol.portEnabled(sw, (short) pi.getInputPort().get())) {
			// process no further
			return false;
		}

		Link lt = this.links.addOrUpdateLink(
				remoteSwitch.getId(), 				// remote switch id
				remotePort,							// remote port number
//				remoteSwitch.getPort(remotePort), 	// remote physical port
				protocol.getPort(remoteSwitch, remotePort),
				sw.getId(), 						// local switch id
				(short) pi.getInputPort().get(),	// remote port number
//				sw.getPort(pi.getInPort()),			// local physical port
				protocol.getPort(sw, (short) pi.getInputPort().get()),
				isStandard,
				isReverse
		);

		// Remove the node ports from the quarantine and maintenance queues.
		NodePortTuple nptSrc = new NodePortTuple(lt.getSrc(), lt.getSrcPort());
		NodePortTuple nptDst = new NodePortTuple(lt.getDst(), lt.getDstPort());
		removeFromQuarantineQueue(nptSrc);
		removeFromMaintenanceQueue(nptSrc);
		removeFromQuarantineQueue(nptDst);
		removeFromMaintenanceQueue(nptDst);

		// Consume this message
		return false;
	}

	/**
	 * Handle PORT_STATUS message.
	 * 
	 * <ol>
	 * <li> if the PORT_STATUS message shows that a link has been deleted or 
	 *      modified where the port is down or configured down, 
	 *      we mark the link has been deleted by calling 
	 *      {@link Links#deleteLinksOnPort(NodePortTuple)}.
	 * <li> or if the PORT_STATUS indicates that the status of a link has been changed,
	 *      we mark the link has been changed by calling
	 *      {@link Links#updatePortStatus(Long, short, OFPortStatus)}.
	 * <li> and finally, if a link has NOT been deleted, 
	 *      we process the PORT_STATUS as an indication that a new port 
	 *      has been added, by calling {@link #processNewPort(IOFSwitch, short)}.
	 * </ol>
	 * 
	 * @param sw
	 * @param context
	 * @param ps
	 * @param outgoing
	 * @return
	 */
	private boolean handlePortStatus(IOFSwitch sw, MessageContext context, OFPortStatus ps, List<OFMessage> outgoing) {

		if (sw == null) {
			return false;
		}
		
		/*
		 * following is the original implementation by Floodlight.
		 */

//		short portnum = ps.getDesc().getPortNumber();
		int portnum = ps.getDesc().getPort().get();
		NodePortTuple npt = new NodePortTuple(sw.getId(), portnum);
		boolean linkDeleted  = false;
		boolean linkInfoChanged = false;

		// if ps is a delete, or a modify where the port is down or
		// configured down
		if (ps.getReason() == OFPortReason.DELETE ||
				(ps.getReason() == OFPortReason.MODIFY && !portEnabled((OFPortDesc) ps.getDesc()))) {
			// Reason: Port Status Changed
			this.links.deleteLinksOnPort( npt );

			linkDeleted = true;
		} 
		else if (ps.getReason() == OFPortReason.MODIFY) {
			// If ps is a port modification and the port state has changed
			// that affects links in the topology
			linkInfoChanged = links.updatePortStatus(sw.getId(), portnum, ps);
		}

		if (!linkDeleted && !linkInfoChanged){
			// does nothing
		}

		if (!linkDeleted) {
			// Send LLDP right away when port state is changed for faster
			// cluster-merge. If it is a link delete then there is not need
			// to send the LLDPs right away and instead we wait for the LLDPs
			// to be sent on the timer as it is normally done
			// do it outside the write-lock
			// sendLLDPTask.reschedule(1000, TimeUnit.MILLISECONDS);
			processNewPort(this.controller.getSwitch(sw.getId()), portnum);
		}

		return true;
	}

	/**
	 * Process a new port.
	 * (Send LLDP message.  Add the port to quarantine)
	 * 
	 * @param sw
	 * @param portnum
	 */
	private void processNewPort(IOFSwitch sw, int portnum) {
		if ( sw != null ) {
			sendDiscoveryMessage(sw, portnum, true, false);

			// Add to maintenance queue to ensure that BDDP packets
			// are sent out.
			addToMaintenanceQueue( new NodePortTuple(sw.getId(), portnum) );
		}
	}

	public String toString() {
		return this.links.getStringRepresentation();
	}

	@Override
	public Map<Link, LinkInfo> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, Set<Link>> getSwitchLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NodePortTuple> getSuppressLLDPsInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddToSuppressLLDPs(long sw, int port) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RemoveFromSuppressLLDPs(long sw, int port) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Integer> getQuarantinedPorts(long sw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoPortFastFeature() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutoPortFastFeature(boolean autoPortFastFeature) {
		// TODO Auto-generated method stub
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.links };
	}
}
