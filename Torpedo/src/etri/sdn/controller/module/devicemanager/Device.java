/**
 *    Copyright 2011,2012 Big Switch Networks, Inc.
 *    Originally created by David Erickson, Stanford University
 *
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 **/

package etri.sdn.controller.module.devicemanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.util.HexString;

import etri.sdn.controller.module.devicemanager.IDeviceService.DeviceField;
import etri.sdn.controller.module.devicemanager.SwitchPort.ErrorStatus;
import etri.sdn.controller.protocol.packet.Ethernet;
import etri.sdn.controller.protocol.packet.IPv4;

/**
 * Concrete implementation of {@link IDevice}.
 * This class defines a device.
 * 
 * @author readams
 */
public class Device implements IDevice {

	protected final Long deviceKey;
	protected final Entity[] entities;
	protected final IEntityClass entityClass;
	protected final String macAddressString;

	/**
	 * The old attachment points for the device that were valid 
	 * no more than INACTIVITY_TIME ago.
	 */
	protected final CopyOnWriteArrayList<AttachmentPoint> oldAPs;

	/**
	 * The current attachment points for the device.
	 */
	protected final CopyOnWriteArrayList<AttachmentPoint> attachmentPoints;

	// ************
	// Constructors
	// ************

	/**
	 * Creates a device from an entities.
	 * 
	 * @param deviceKey the unique identifier for this device object
	 * @param entity the initial entity for the device
	 * @param entityClass the entity classes associated with the entity
	 */
	private Device(Long deviceKey,
			Entity entity,
			IEntityClass entityClass,
			Collection<AttachmentPoint> attachmentPoints) {
		this.deviceKey = deviceKey;
		this.entities = new Entity[] {entity};
		this.macAddressString = HexString.toHexString(entity.getMacAddress(), 6);
		this.entityClass = entityClass;
		Arrays.sort(this.entities);

		this.oldAPs = new CopyOnWriteArrayList<AttachmentPoint>();
		this.attachmentPoints = new CopyOnWriteArrayList<AttachmentPoint>();
		
		this.attachmentPoints.addAll( attachmentPoints );
	}
	
	public static Device allocateDevice(Long deviceKey, Entity entity, IEntityClass entityClass) {
		List<AttachmentPoint> aps = new LinkedList<AttachmentPoint>();
		if (entity.getSwitchDPID() != null && entity.getSwitchPort() != null){
			long sw = entity.getSwitchDPID();
			OFPort port = entity.getSwitchPort();

			if (Devices.getInstance().isValidAttachmentPoint(sw, port)) {
				AttachmentPoint ap = 
					new AttachmentPoint(sw, port, entity.getLastSeenTimestamp().getTime());
				aps.add(ap);
			}
		}
		if ( aps.isEmpty() ) {
			return null;
		}
		
		Device ret = new Device(deviceKey, entity, entityClass, aps);
		return ret;
	}

	/**
	 * Creates a device from a set of entities.
	 * 
	 * @param deviceKey the unique identifier for this device object
	 * @param oldAPs old attachment points for the device
	 * @param attachmentPoints current attachment points for the device
	 * @param entities the initial entities for the device
	 * @param entityClass the entity class associated with the entities
	 */
	public Device(Long deviceKey,
			Collection<AttachmentPoint> oldAPs,
			Collection<AttachmentPoint> attachmentPoints,
			Collection<Entity> entities,
			IEntityClass entityClass) {
		this.deviceKey = deviceKey;
		this.entities = entities.toArray(new Entity[entities.size()]);
		this.oldAPs = new CopyOnWriteArrayList<AttachmentPoint>();
		this.attachmentPoints = new CopyOnWriteArrayList<AttachmentPoint>();
		
		if (oldAPs != null) {
			this.oldAPs.addAll(oldAPs);
		}
		if (attachmentPoints != null) {
			this.attachmentPoints.addAll(attachmentPoints); 
		}
		this.macAddressString = HexString.toHexString(this.entities[0].getMacAddress(), 6);
		this.entityClass = entityClass;
		Arrays.sort(this.entities);
	}

	/**
	 * Constructs a new device consisting of the entities from the old device
	 * plus an additional entity.
	 * 
	 * @param device the old device object
	 * @param newEntity the entity to add. newEntity must be have the same
	 *        entity class as device
	 */
	public Device(Device device,
			Entity newEntity) {
		this.deviceKey = device.deviceKey;
		this.entities = Arrays.<Entity>copyOf(device.entities, device.entities.length + 1);
		this.entities[this.entities.length - 1] = newEntity;
		this.oldAPs = new CopyOnWriteArrayList<AttachmentPoint>();
		this.attachmentPoints = new CopyOnWriteArrayList<AttachmentPoint>();
		Arrays.sort(this.entities);
		
		if (device.oldAPs != null) {
			this.oldAPs.addAll(device.oldAPs);
		}
		if (device.attachmentPoints != null) {
			this.attachmentPoints.addAll(device.attachmentPoints);
		}

		this.macAddressString = HexString.toHexString(this.entities[0].getMacAddress(), 6);

		this.entityClass = device.entityClass;
	}

	/**
	 * Given a list of attachment points, the procedure would return
	 * a map of attachment points for each L2 domain. L2 domain id is the key.
	 * 
	 * @param apList a list of attachment points
	 * 
	 * @return a map of attachment points for each L2 domain
	 */
	private Map<Long, AttachmentPoint> getAPMap(final List<AttachmentPoint> apList) {

		if (apList == null) return null;

		// Map of attachment point by L2 domain Id.
		Map<Long, AttachmentPoint> apMap = new HashMap<Long, AttachmentPoint>();

		for(int i=0; i < apList.size(); ++i) {
			AttachmentPoint ap = apList.get(i);
			// if this is not a valid attachment point, continue
			if (!Devices.getInstance().isValidAttachmentPoint(ap.getSw(), ap.getPort()))
				continue;

			long id = Devices.getInstance().topology.getL2DomainId(ap.getSw());
			apMap.put(id, ap);
		}

		return apMap;
	}

	/**
	 * Removes all attachment points that are older than 
	 * {@link etri.sdn.controller.module.devicemanager.AttachmentPoint#INACTIVITY_INTERVAL} 
	 * from the list.
	 * 
	 * @param apList the list of attachment points
	 * 
	 * @return true if removed more than one attachment point, false otherwise
	 */
	private boolean removeExpiredAttachmentPoints(List<AttachmentPoint> apList) {
		
		if (apList == null) return false;

		List<AttachmentPoint> expiredAPs = new ArrayList<AttachmentPoint>();

		for(AttachmentPoint ap: apList) {
			if (ap.getLastSeen() + AttachmentPoint.INACTIVITY_INTERVAL < System.currentTimeMillis())
				expiredAPs.add(ap);
		}
		if (expiredAPs.size() > 0) {
			apList.removeAll(expiredAPs);
			return true;
		} else { 
			return false;
		}
	}

	/**
	 * Gets a list of duplicate attachment points, given a list of old attachment
	 * points and one attachment point per L2 domain. Given a true attachment
	 * point in the L2 domain, say trueAP, another attachment point in the
	 * same L2 domain, say ap, is duplicate if
	 * <p>
	 * <ol>
	 * <li>ap is inconsistent with trueAP, and
	 * <li>active time of ap is after that of trueAP; and
	 * <li>last seen time of ap is within the last 
	 * {@link etri.sdn.controller.module.devicemanager.AttachmentPoint#INACTIVITY_INTERVAL}
	 * </ol>
	 * @param oldAPList a list of old attachment points
	 * @param apMap a map of attachment points for each L2 domain 
	 * 
	 * @return a list of duplicated attachment points
	 */
	private List<AttachmentPoint> getDuplicateAttachmentPoints(List<AttachmentPoint> oldAPList,
			Map<Long, AttachmentPoint> apMap) {
		List<AttachmentPoint> dupAPs = new ArrayList<AttachmentPoint>();
		long timeThreshold = System.currentTimeMillis() - AttachmentPoint.INACTIVITY_INTERVAL;

		if (oldAPList == null || apMap == null)
			return dupAPs;

		for(AttachmentPoint ap: oldAPList) {
			long id = Devices.getInstance().topology.getL2DomainId(ap.getSw());
			AttachmentPoint trueAP = apMap.get(id);

			if (trueAP == null) continue;
			boolean c = (Devices.getInstance().topology.isConsistent(
					trueAP.getSw(), trueAP.getPort(), ap.getSw(), ap.getPort())
			);
			boolean active = (ap.getActiveSince() > trueAP.getActiveSince());
			boolean last = ap.getLastSeen() > timeThreshold;
			if (!c && active && last) {
				dupAPs.add(ap);
			}
		}

		return dupAPs;
	}

	/**
	 * Updates the known attachment points. This method is called whenever
	 * topology changes. 
	 * 
	 * @return true if there is any change to the list of attachment points 
	 *         -- which indicates a possible device move
	 */
	protected boolean updateAttachmentPoint() {
		
		if (attachmentPoints == null || attachmentPoints.isEmpty())
			return false;

		boolean moved = false;

		Map<Long, AttachmentPoint> newMap = getAPMap(this.attachmentPoints);
		
		if ( newMap.size() != this.attachmentPoints.size() ) {
			moved = true;
		}

		// Prepare the new attachment point list.
		if (moved) {
			if ( newMap != null ) {
				this.attachmentPoints.retainAll( newMap.values() );
				this.attachmentPoints.addAllAbsent( newMap.values() );
			}
		}

		// empty the old ap list.
		this.oldAPs.clear();
		
		return moved;
	}

	/**
	 * Updates the list of attachment points given that a new packetin 
	 * was seen from (sw, port) at time (lastSeen). 
	 * 
	 * @param sw the switch that received packetin
	 * @param port the switch port that received packetin
	 * @param lastSeen the timestamp we observed on the network
	 * 
	 * @return true if there was any change to the list of attachment points 
	 *         for the device -- which indicates a device move
	 */
	public boolean updateAttachmentPoint(long sw, OFPort port, long lastSeen){

		if (!Devices.getInstance().isValidAttachmentPoint(sw, port)) 
			return false;

		boolean oldAPFlag = false;

		AttachmentPoint newAP = new AttachmentPoint(sw, port, lastSeen);
		
		do {
			int index = this.oldAPs.indexOf(newAP);
			if ( index >= 0 ) {
				try { 
					AttachmentPoint tmp = this.oldAPs.remove(index);
					if ( ! tmp.equals(newAP) ) {
						// this is not what we are looking for.
						// so, just put it back.
						this.oldAPs.add(tmp);
						continue;
					} 
					
					// now the tmp is the new AP that we are looking for. 
					newAP = tmp;
				} catch ( IndexOutOfBoundsException e ) {
					// the element might be already removed.
					continue;
				}
				newAP.setLastSeen(lastSeen);
				// in the oldAPs, there was newAP.
				oldAPFlag = true;
			}
		} while ( false );

		Map<Long, AttachmentPoint> apMap = getAPMap(this.attachmentPoints);
		if ( apMap.isEmpty() ) {
			this.attachmentPoints.add( newAP );
			return true;
		}

		long id = Devices.getInstance().topology.getL2DomainId(sw);
		AttachmentPoint oldAP = apMap.get(id);

		if (oldAP == null) // No attachment on this L2 domain.
		{
			this.attachmentPoints.clear();
			this.attachmentPoints.addAllAbsent(apMap.values());
			this.attachmentPoints.addIfAbsent(newAP);
			
			return true; // new AP found on an L2 island.
		}

		// There is already a known attachment point on the same L2 island.
		// we need to compare oldAP and newAP.
		else if (oldAP.equals(newAP)) {
			// nothing to do here. just the last seen has to be changed.
			if (newAP.lastSeen > oldAP.lastSeen) {
				oldAP.setLastSeen(newAP.lastSeen);
			}

			this.attachmentPoints.retainAll(apMap.values());
			
			return false; // nothing to do here.
		}

		int x = Devices.getInstance().apComparator.compare(oldAP, newAP);
		if (x < 0) {
			this.attachmentPoints.retainAll(apMap.values());
			this.attachmentPoints.addIfAbsent(newAP);

			this.oldAPs.add(oldAP);
			
			if (!Devices.getInstance().topology.isInSameBroadcastDomain(
					oldAP.getSw(), oldAP.getPort(), newAP.getSw(), newAP.getPort())) {
				return true; // attachment point changed.
			}
		} 
		else if (oldAPFlag) {
			// retain oldAP  as is.  Put the newAP in oldAPs for flagging
			// possible duplicates.
			this.oldAPs.add( newAP );
		}
		return false;
	}
	
	/**
	 * get the number of the attachment points.
	 * @return the number of the attachment points.
	 */
	public int getNumberOfAttachmentPoints() {
		return this.attachmentPoints.size();
	}

	/**
	 * Deletes (sw,port) from the list of list of attachment points and oldAPs.
	 * 
	 * @param sw a switch to delete
	 * @param port a switch port to delete
	 * 
	 * @return true if deleted more than one attachment point, false otherwise
	 */
	public boolean deleteAttachmentPoint(long sw, OFPort port) {
		AttachmentPoint ap = new AttachmentPoint(sw, port, 0);

		this.oldAPs.remove(ap);

		return this.attachmentPoints.remove(ap);
	}

	/**
	 * Deletes attachment points of the given switch from the list of attachment 
	 * points and oldAPs.
	 * 
	 * @param sw a switch to delete
	 * 
	 * @return true if deleted more than one attachment point, false otherwise
	 */
	public boolean deleteAttachmentPoint(long sw) {
		boolean deletedFlag = false;
		final ArrayList<AttachmentPoint> apList = new ArrayList<AttachmentPoint>();
		final ArrayList<AttachmentPoint> modifiedList = new ArrayList<AttachmentPoint>();

		// Delete the APs on switch sw in oldAPs.
		
		apList.addAll(this.oldAPs);
		
		for(AttachmentPoint ap: apList) {
			if (ap.getSw() == sw) {
				deletedFlag = true;
			} else {
				modifiedList.add(ap);
			}
		}

		if (deletedFlag) {
			this.oldAPs.retainAll(modifiedList);
		}

		// Delete the APs on switch sw in attachmentPoints.

		deletedFlag = false;
		apList.clear();
		apList.addAll(this.attachmentPoints);
		modifiedList.clear();
		
		for(AttachmentPoint ap: apList) {
			if (ap.getSw() == sw) {
				deletedFlag = true;
			} else {
				modifiedList.add(ap);
			}
		}

		if (deletedFlag) {
			this.attachmentPoints.retainAll(modifiedList);
			return true;
		}

		return false;
	}

	@Override
	public SwitchPort[] getAttachmentPoints() {
		return getAttachmentPoints(false);
	}

	@Override
	public SwitchPort[] getAttachmentPoints(boolean includeError) {
		List<SwitchPort> sp = new ArrayList<SwitchPort>();
		SwitchPort [] returnSwitchPorts = new SwitchPort[] {};
		if (attachmentPoints.isEmpty()) return returnSwitchPorts;

		// copy ap list.
		List<AttachmentPoint> apList = new ArrayList<AttachmentPoint>(this.attachmentPoints);
		
		// get AP map.
		Map<Long, AttachmentPoint> apMap = getAPMap(apList);

		for(AttachmentPoint ap: apMap.values()) {
			SwitchPort swport = new SwitchPort(ap.getSw(), ap.getPort());
			sp.add(swport);
		}

		if (!includeError)
			return sp.toArray(new SwitchPort[sp.size()]);

		List<AttachmentPoint> oldAPList = new ArrayList<AttachmentPoint>(this.oldAPs);

		if ( removeExpiredAttachmentPoints(oldAPList) ) {
			this.oldAPs.retainAll(oldAPList);
		}
		
		List<AttachmentPoint> dupList = this.getDuplicateAttachmentPoints(oldAPList, apMap);
		if (dupList != null) {
			for(AttachmentPoint ap: dupList) {
				SwitchPort swport = new SwitchPort(ap.getSw(),
						ap.getPort(),
						ErrorStatus.DUPLICATE_DEVICE);
				sp.add(swport);
			}
		}
		return sp.toArray(new SwitchPort[sp.size()]);
	}

	// *******
	// IDevice
	// *******

	@Override
	public Long getDeviceKey() {
		return deviceKey;
	}

	@Override
	public long getMACAddress() {
		// we assume only one MAC per device for now.
		return entities[0].getMacAddress();
	}

	@Override
	public String getMACAddressString() {
		return macAddressString;
	}

	@Override
	public Short[] getVlanId() {
		if (entities.length == 1) {
			if (entities[0].getVlan() != null) {
				return new Short[]{ entities[0].getVlan() };
			} else {
				return new Short[] { Short.valueOf((short)-1) };
			}
		}

		TreeSet<Short> vals = new TreeSet<Short>();
		for (Entity e : entities) {
			if (e.getVlan() == null)
				vals.add((short)-1);
			else
				vals.add(e.getVlan());
		}
		return vals.toArray(new Short[vals.size()]);
	}

	static final EnumSet<DeviceField> ipv4Fields = EnumSet.of(DeviceField.IPV4);

	@Override
	public Integer[] getIPv4Addresses() {
		// XXX - TODO we can cache this result.  Let's find out if this
		// is really a performance bottleneck first though.

		TreeSet<Integer> vals = new TreeSet<Integer>();
		for (Entity e : entities) {
			if (e.getIpv4Address() == null) continue;

			// We have an IP address only if among the devices within the class
			// we have the most recent entity with that IP.
			boolean validIP = true;
			Iterator<Device> devices = Devices.getInstance().queryClassByEntity(entityClass, ipv4Fields, e);

			while (devices.hasNext()) {
				Device d = devices.next();
				if (deviceKey.equals(d.getDeviceKey())) 
					continue;
				for (Entity se : d.entities) {
					if (se.getIpv4Address() != null &&
							se.getIpv4Address().equals(e.getIpv4Address()) &&
							se.getLastSeenTimestamp() != null &&
							0 < se.getLastSeenTimestamp().compareTo(e.getLastSeenTimestamp())) {
						validIP = false;
						break;
					}
				}
				if (!validIP)
					break;
			}

			if (validIP)
				vals.add(e.getIpv4Address());
		}

		return vals.toArray(new Integer[vals.size()]);
	}

	@Override
	public Short[] getSwitchPortVlanIds(SwitchPort swp) {
		TreeSet<Short> vals = new TreeSet<Short>();
		for (Entity e : entities) {
			if (e.switchDPID == swp.getSwitchDPID() && e.switchPort == swp.getPort()) {
				if (e.getVlan() == null)
					vals.add(Ethernet.VLAN_UNTAGGED);
				else
					vals.add(e.getVlan());
			}
		}
		return vals.toArray(new Short[vals.size()]);
	}

	@Override
	public Date getLastSeen() {
		Date d = null;
		for (int i = 0; i < entities.length; i++) {
			if (d == null || entities[i].getLastSeenTimestamp().compareTo(d) > 0)
				d = entities[i].getLastSeenTimestamp();
		}
		return d;
	}

	// ***************
	// Getters/Setters
	// ***************

	@Override
	public IEntityClass getEntityClass() {
		return entityClass;
	}

	public Entity[] getEntities() {
		return entities;
	}

	// ***************
	// Utility Methods
	// ***************

	/**
	 * Checks whether the device contains the specified entity.
	 * 
	 * @param entity the entity to search for
	 * 
	 * @return the index of the entity, or <0 if not found
	 */
	protected int entityIndex(Entity entity) {
		return Arrays.binarySearch(entities, entity);
	}

	// ******
	// Object
	// ******

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(entities);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Device other = (Device) obj;
		if (!deviceKey.equals(other.deviceKey)) return false;
		if (!Arrays.equals(entities, other.entities)) return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Device [deviceKey=");
		builder.append(deviceKey);
		builder.append(", entityClass=");
		builder.append(entityClass.getName());
		builder.append(", MAC=");
		builder.append(macAddressString);
		builder.append(", IPs=[");
		boolean isFirst = true;
		for (Integer ip: getIPv4Addresses()) {
			if (!isFirst)
				builder.append(", ");
			isFirst = false;
			builder.append(IPv4.fromIPv4Address(ip));
		}
		builder.append("], APs=");
		builder.append(Arrays.toString(getAttachmentPoints(true)));
		builder.append("]");
		return builder.toString();
	}
}
