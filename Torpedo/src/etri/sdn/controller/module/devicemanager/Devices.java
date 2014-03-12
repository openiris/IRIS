package etri.sdn.controller.module.devicemanager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.codehaus.jackson.map.ObjectMapper;
import org.projectfloodlight.openflow.types.OFPort;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.devicemanager.SwitchPort.ErrorStatus;
import etri.sdn.controller.module.topologymanager.ITopologyService;
import etri.sdn.controller.protocol.packet.IPv4;
import etri.sdn.controller.util.Logger;
import etri.sdn.controller.util.MultiIterator;

/**
 * This class manages information of devices such as device map, 
 * device indices and so on.
 * 
 */
public class Devices extends OFModel implements IDeviceService {

	public ITopologyService topology;
	public IEntityClassifierService classifier;
	public AttachmentPointComparator apComparator;

	/**
	 * AttachmentPointComparator class compares two attachment points 
	 * and returns the latest one. It is assumed that the two attachment 
	 * points are in the same L2 domain.
	 * 
	 * @author srini
	 */
	class AttachmentPointComparator implements Comparator<AttachmentPoint> {

		@Override
		public int compare(AttachmentPoint oldAP, AttachmentPoint newAP) {

			//First compare based on L2 domain ID; 
			long oldSw = oldAP.getSw();
			OFPort oldPort = oldAP.getPort();
			long oldDomain = topology.getL2DomainId(oldSw);
			boolean oldBD = topology.isBroadcastDomainPort(oldSw, oldPort);

			long newSw = newAP.getSw();
			OFPort newPort = newAP.getPort();
			long newDomain = topology.getL2DomainId(newSw);
			boolean newBD = topology.isBroadcastDomainPort(newSw, newPort);

			if (oldDomain < newDomain) return -1;
			else if (oldDomain > newDomain) return 1;

			// We expect that the last seen of the new AP is higher than
			// old AP, if it is not, just reverse and send the negative
			// of the result.
			if (oldAP.getActiveSince() > newAP.getActiveSince())
				return -compare(newAP, oldAP);

			long activeOffset = 0;
			if (!topology.isConsistent(oldSw, oldPort, newSw, newPort)) {
				if (!newBD && oldBD) {
					return -1;
				}
				if (newBD && oldBD) {
					activeOffset = AttachmentPoint.EXTERNAL_TO_EXTERNAL_TIMEOUT;
				}
				else if (newBD && !oldBD){
					activeOffset = AttachmentPoint.OPENFLOW_TO_EXTERNAL_TIMEOUT;
				}

			} else {
				// The attachment point is consistent.
				activeOffset = AttachmentPoint.CONSISTENT_TIMEOUT;
			}


			if ((newAP.getActiveSince() > oldAP.getLastSeen() + activeOffset) ||
					(newAP.getLastSeen() > oldAP.getLastSeen() +
							AttachmentPoint.INACTIVITY_INTERVAL)) {
				return -1;
			}
			return 1;
		}
	}

	/**
	 * A device update event to be dispatched
	 */
	private static class DeviceUpdate {
		/**
		 * The enum set of DeviceUpdate events
		 */
		public enum Change {
			ADD, DELETE, CHANGE;
		}

		/**
		 * The affected device
		 */
		protected IDevice device;

		/**
		 * The change that was made
		 */
		protected Change change;

		/**
		 * If not added, then this is the list of fields changed.
		 */
		protected EnumSet<DeviceField> fieldsChanged;

		/**
		 * Constructor
		 * 
		 * @param device the device to be changed
		 * @param change the device update event
		 * @param fieldsChanged the set of fields to be changed
		 */
		public DeviceUpdate(IDevice device, Change change,
				EnumSet<DeviceField> fieldsChanged) {
			super();
			this.device = device;
			this.change = change;
			this.fieldsChanged = fieldsChanged;
		}

		@Override
		public String toString() {
			String devIdStr = device.getEntityClass().getName() + "::" +
			device.getMACAddressString();
			return "DeviceUpdate [device=" + devIdStr + ", change=" + change
			+ ", fieldsChanged=" + fieldsChanged + "]";
		}

	}

	/**
	 * Device manager event listeners
	 */
	private Set<IDeviceListener> deviceListeners;
	
	/**
	 * This is the master device map that maps device IDs to {@link Device}
	 * objects.
	 */
	private ConcurrentHashMap<Long, Device> deviceIdToDeviceMap;

	/**
	 * Counter used to generate device keys
	 */
	private long deviceKeyCounter = 0;

	/**
	 * Lock for incrementing the device key counter
	 */
	private Object deviceKeyCounterLock = new Object();

	/**
	 * This is the primary entity index that contains all entities.
	 */
	private EntityToSingleDeviceIdIndex primaryIndex;
	
	/**
	 * Single Indices (from an Entity to a device id).
	 * One of the Indices is the primaryIndex.
	 */
	private ConcurrentHashMap<EnumSet<DeviceField>, EntityToSingleDeviceIdIndex> singleIndices;
	
	/**
	 * Multi indices (from an Entity to a set of device IDs)
	 */
	private ConcurrentHashMap<EnumSet<DeviceField>, EntityToMultiDeviceIdIndex> multiIndices;

//	/**
//	 * This stores secondary indices over the fields in the devices.
//	 * The value of this map is the objects of {@link EntityToMultiDeviceIdIndex}.
//	 */
//	private Map<EnumSet<DeviceField>, DeviceIndex> classToSecondaryIndexMap;
//
//	/**
//	 * This map contains state for each of the {@link IEntityClass}
//	 * that exist
//	 */
//	private ConcurrentHashMap<String, ClassIndices> entityClassNameToClassIndicesMap;
//
//	/**
//	 * This is the set of all classes (actually, device fields)
//	 */
//	private Set<EnumSet<DeviceField>> classes;

	/**
	 * Switch ports where attachment points shouldn't be learned
	 */
	private Set<SwitchPort> suppressedAPs;


	/**
	 * Constructor
	 * 
	 * @param topo the reference of topology service module
	 * @param clf the entity classifier
	 */
	private Devices(ITopologyService topo, 
					IEntityClassifierService clf) {
		this.topology = topo;
		this.classifier = clf;
		this.apComparator = new AttachmentPointComparator();
		
		this.deviceListeners = new HashSet<IDeviceListener>();
		
		this.primaryIndex = new EntityToSingleDeviceIdIndex(classifier.getKeyFields());
		this.singleIndices = new ConcurrentHashMap<EnumSet<DeviceField>, EntityToSingleDeviceIdIndex>();
		this.singleIndices.put(classifier.getKeyFields(), primaryIndex);
		this.multiIndices = new ConcurrentHashMap<EnumSet<DeviceField>, EntityToMultiDeviceIdIndex>();
		
//		this.classToSecondaryIndexMap = new HashMap<EnumSet<DeviceField>, DeviceIndex>();
		this.deviceIdToDeviceMap = new ConcurrentHashMap<Long, Device>();
//		this.entityClassNameToClassIndicesMap = new ConcurrentHashMap<String, ClassIndices>();
		this.suppressedAPs = Collections.synchronizedSet(new HashSet<SwitchPort>());
	}
	
	private static AtomicReference<Devices> instanceRef = new AtomicReference<Devices>(null);
	
	/**
	 * Returns the instance of {@link Devices} class. If there is no instance,
	 * creates a new instance.
	 * 
	 * @param topo the reference of topology service module
	 * @param clf the entity classifier
	 * 
	 * @return the instance of Devices class
	 */
	public static Devices getInstance(ITopologyService topo, IEntityClassifierService clf) {
		if ( instanceRef.get() == null ) {
			Devices n = new Devices(topo, clf);
			if ( instanceRef.compareAndSet(null, n) ) {
				return n;
			}
			// else, somebody already created the Devices object and set it to instanceRef.
		}

		// therefore, for all the other cases, we return the result of following call.
		return instanceRef.get();
	}

	/**
	 * Returns the instance of Devices class
	 * 
	 * @return the instance of Devices class
	 */
	public static Devices getInstance() {
		return instanceRef.get();
	}

	/**
	 * Checks whether the given attachment point is valid given the current
	 * topology.
	 * 
	 * @param switchDPID the switch dpid
	 * @param switchPort the switch port
	 * 
	 * @return true if it's a valid attachment point
	 */
	public boolean isValidAttachmentPoint(long switchDPID, OFPort switchPort) {
		
		if ( !topology.isAttachmentPointPort(switchDPID, switchPort) )
			return false;

		if ( isSuppressedAP(new SwitchPort(switchDPID, switchPort) ) ) {
			return false;
		}

		return true;
	}

	/*
	 * static Device allocation functions.
	 */

	/**
	 * Returns a new device.
	 * 
	 * @param deviceKey the unique identifier for this device object
	 * @param entity the initial entity for the device
	 * @param entityClass the entity classes associated with the entity
	 * 
	 * @return a new device
	 */
	public static Device allocateDevice(Long deviceKey, Entity entity, IEntityClass entityClass) {
//		return new Device(deviceKey, entity, entityClass);
		return Device.allocateDevice(deviceKey, entity, entityClass);
	}

	/**
	 * Returns a new device.
	 * 
	 * @param deviceKey the unique identifier for this device object
	 * @param aps old attachment points for the device
	 * @param trueAPs current attachment points for the device
	 * @param entities the initial entities for the device
     * @param entityClass the entity class associated with the entities
	 * 
	 * @return a new device
	 */
	public static Device allocateDevice(Long deviceKey,
			List<AttachmentPoint> aps,
			List<AttachmentPoint> trueAPs,
			Collection<Entity> entities,
			IEntityClass entityClass) {
		return new Device(deviceKey, aps, trueAPs, entities, entityClass);
	}

	/**
	 * Returns a new device consisting of the entities from the old device
	 * and an additional entity.
	 * 
	 * @param device the old device object
	 * @param entity the entity to add. This must be have the same
	 *        entity class as device
	 * 
	 * @return a new device
	 */
	public static Device allocateDevice(Device device, Entity entity) {
		return new Device(device, entity);
	}

	/*
	 * Accessors.
	 */

	/**
	 * Returns the map of device and device key.
	 *  
	 * @return the deviceIdToDeviceMap
	 */
	public ConcurrentHashMap<Long, Device> getDeviceMap() {
		return this.deviceIdToDeviceMap;
	}

	/**
	 * Returns the size of {@link etri.sdn.controller.module.devicemanager.Devices#deviceIdToDeviceMap}.
	 * 
	 * @return the number of elements in deviceIdToDeviceMap
	 */
	public int size() {
		return this.deviceIdToDeviceMap.size();
	}

	/**
	 * Is this entity is allowed?
	 * 
	 * @return true
	 */
	public boolean isEntityAllowed(Entity entity, IEntityClass entityClass) {
		return true;
	}

	/**
	 * Look up a {@link Device} based on the provided {@link Entity}. Also
	 * learns based on the new entity, and will update existing devices as required.
	 *
	 * @param entity the {@link Entity}
	 * 
	 * @return The {@link Device} object if found
	 */
	public Device learnDeviceByEntity(Entity entity) {
		//		ArrayList<Long> deleteQueue = null;
		LinkedList<DeviceUpdate> deviceUpdates = null;
		Device device = null;

		// we may need to restart the learning process if we detect
		// concurrent modification.  Note that we ensure that at least
		// one thread should always succeed so we don't get into infinite
		// starvation loops
		while (true) {
			deviceUpdates = null;

			// Look up the fully-qualified entity to see if it already
			// exists in the primary entity index.
			Long deviceId = primaryIndex.findByEntity(entity);
			IEntityClass entityClass = null;

			if (deviceId == null) {
				// If the entity does not exist in the primary entity index,
				// use the entity classifier for find the classes for the
				// entity. Look up the entity in the returned class'
				// class entity index.
				entityClass = classifier.classifyEntity(entity);
				if (entityClass == null) {
					// could not classify entity. No device
					return null;
				}
				
//				ClassIndices classIndices = getClassIndices(entityClass);
//
//				// if there is a class-specific index (other than primary index) 
//				// we find out the device key using the index.
//				if (classIndices.classIndex != null) {
//					deviceId =	classIndices.classIndex.findByEntity(entity);
//				}
				
				EntityToSingleDeviceIdIndex alternative = singleIndices.get(entityClass.getKeyFields());
				if ( alternative != primaryIndex ) {
					deviceId = alternative.findByEntity(entity);
				}
			}

			if (deviceId != null) {
				// If the primary or secondary index contains the entity
				// use resulting device key to look up the device in the
				// device map, and use the referenced Device below.
				device = deviceIdToDeviceMap.get(deviceId);
				if (device == null) {
					// currupted device id. 
					// first we should remove the device id from the primary Index.
					primaryIndex.removeEntity(entity);
					// and we remove it from the alternative index.
					if ( entityClass != null ) {
						EntityToSingleDeviceIdIndex alternative = singleIndices.get(entityClass.getKeyFields());
						alternative.removeEntity(entity);
					}
					// we start it all over again.
					continue;
				}
					
			} else {
				// If the secondary index does not contain the entity,
				// create a new Device object containing the entity, and
				// generate a new device ID. However, we first check if 
				// the entity is allowed (e.g., for spoofing protection)
				if (!isEntityAllowed(entity, entityClass)) {
					Logger.stdout("PacketIn is not allowed {} {} : " + entityClass.getName() + ", " + entity);
					return null;
				}
				
				// assign new device id
				synchronized (deviceKeyCounterLock) {
					deviceId = Long.valueOf(deviceKeyCounter++);
				}
				
				
				device = Devices.allocateDevice(deviceId, entity, entityClass);
				
				if ( device == null ) {
					// this device is not a target to learn.
					return null;
				}

				// updateIndeces() updates the primaryIndex first.
				// and if succeeds, continues to update per-class index.
				// this does not include secondary index.
				if ( updateIndices(device, deviceId) ) {  
					// Add the new device to the primary map with a simple put
					Device d = deviceIdToDeviceMap.putIfAbsent(deviceId, device);
					if ( d != null ){
						// device for the id is already put in the map.
						// but this will NEVER happen.
						continue;
					}
					
					// TO DEBUG
//					Logger.debug("Entity: " + entity.toString());
//					Logger.debug("New Device: " + device.toString());
				}
				else {
					// if updateIndices fails because of other concurrent updates
					// (device using this entity has already been created in another thread)
//					if (deleteQueue == null)
//						deleteQueue = new ArrayList<Long>();
//					deleteQueue.add(deviceId);
					continue;
				}

				// this updates {@link deviceFieldsToSecondaryIndexMap} first,
				// and updates per-entityClass secondary indices.
				updateSecondaryIndices(entity, entityClass, deviceId);

				// generate new device update
				deviceUpdates =
					updateUpdates(deviceUpdates, new DeviceUpdate(device, DeviceUpdate.Change.ADD, null));

				break;
			}

			if (!isEntityAllowed(entity, device.getEntityClass())) {
				Logger.stderr("PacketIn is not allowed: " + 
						device.getEntityClass().getName() + ", " + entity);
				return null;
			}

			// first check if the device contains the entity.
			int entityindex = -1;
			if ((entityindex = device.entityIndex(entity)) >= 0) {
				// if the entity already within the device, 
				// update timestamp on the found entity
				Date lastSeen = entity.getLastSeenTimestamp();
				if (lastSeen == null) lastSeen = new Date();
				device.entities[entityindex].setLastSeenTimestamp(lastSeen);
				
				if (device.entities[entityindex].getSwitchDPID() != null &&
						device.entities[entityindex].getSwitchPort() != null) {
					long sw = device.entities[entityindex].getSwitchDPID();
					OFPort port = device.entities[entityindex].getSwitchPort();

					// TBD: to analysis
					boolean moved = device.updateAttachmentPoint(sw, port, lastSeen.getTime());
					
					if (moved) {
//						Logger.debug("----DeviceChanged:" + device.toString());
						sendDeviceMovedNotification(device);
					} 
				}
				break;
			} else {
				// if the entity is not within the device

				boolean moved = false;
				// construct a new device consisting of the entities from the old device
				// plust an additional one
				Device newDevice = Devices.allocateDevice(device, entity);
				if (entity.getSwitchDPID() != null && entity.getSwitchPort() != null) {
					moved = newDevice.updateAttachmentPoint(entity.getSwitchDPID(),
							entity.getSwitchPort(),
							entity.getLastSeenTimestamp().getTime());
				}

				// generate updates
				EnumSet<DeviceField> changedFields =
					findChangedFields(device, entity);
				if (changedFields.size() > 0)
					deviceUpdates =
						updateUpdates(deviceUpdates,
								new DeviceUpdate(newDevice, DeviceUpdate.Change.CHANGE,
										changedFields));

				// update the device map with a replace call
				boolean res = deviceIdToDeviceMap.replace(deviceId, device, newDevice);
				// If replace returns false, restart the process from the
				// beginning (this implies another thread concurrently
				// modified this Device).
				if (!res)
					continue;

				device = newDevice;

				// update indices
				if (!updateIndices(device, deviceId)) {
					continue;
				}
				updateSecondaryIndices(entity,
						device.getEntityClass(),
						deviceId);

				if (moved) {
					sendDeviceMovedNotification(device);
				} 

				break;
			}
		}

//		if (deleteQueue != null) {
//			for (Long l : deleteQueue) {
//				Device dev = deviceIdToDeviceMap.get(l);
//				this.deleteDevice(dev);
//
//
//				// generate new device update
//				deviceUpdates =
//						updateUpdates(deviceUpdates,
//								new DeviceUpdate(dev, DeviceUpdate.Change.DELETE, null));
//			}
//		}

		processUpdates(deviceUpdates);

		return device;
	}

	/**
	 * Reclassifies a devices if the device entity is null or is contained a device.
	 * 
	 * @param entityClassNames a set of device entity names 
	 */
	public void reclassify(Set<String> entityClassNames) {
		/* iterate through the devices, reclassify the devices that belong
		 * to these entity class names
		 */
		Iterator<Device> diter = deviceIdToDeviceMap.values().iterator();
		while (diter.hasNext()) {
			Device d = diter.next();
			if (d.getEntityClass() == null ||
					entityClassNames.contains(d.getEntityClass().getName()))
				reclassifyDevice(d);
		}
	}

	/**
	 * Updates attachment points of the device.
	 * 
	 */
	public void updateAttachmentPoints() {
		Iterator<Device> diter = deviceIdToDeviceMap.values().iterator();
		// List<LDUpdate> updateList = topology.getLastLinkUpdates();

//		System.out.println("update notified");
		while (diter.hasNext()) {
			Device d = diter.next();
			if (d.updateAttachmentPoint()) {
//				Logger.debug("++++ Device changed:" + d.toString());
				sendDeviceMovedNotification(d);
			}
			if (d.getAttachmentPoints().length == 0) {
				diter.remove();
			}
		}
	}

	/**
	 * Removes all entities of the device if the recent time observed
	 * is after the cutoff argument.
	 * 
	 * @param cutoff the time of threshold to delete entities 
	 */
	public void cleanupEntities(Date cutoff) {

		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		ArrayList<Entity> toKeep = new ArrayList<Entity>();

		Iterator<Device> diter = deviceIdToDeviceMap.values().iterator();
		LinkedList<DeviceUpdate> deviceUpdates =
			new LinkedList<DeviceUpdate>();

		while (diter.hasNext()) {
			Device d = diter.next();

			while (true) {
				deviceUpdates.clear();
				toRemove.clear();
				toKeep.clear();
				for (Entity e : d.getEntities()) {
					if (e.getLastSeenTimestamp() != null &&
							0 > e.getLastSeenTimestamp().compareTo(cutoff)) {
						// individual entity needs to be removed
						toRemove.add(e);
					} else {
						toKeep.add(e);
					}
				}
				if (toRemove.size() == 0) {
					break;
				}

				for (Entity e : toRemove) {
					removeEntity(e, d.getEntityClass(), d.deviceKey, toKeep);
				}

				if (toKeep.size() > 0) {
					Device newDevice = Devices.allocateDevice(d.getDeviceKey(),
							d.oldAPs,
							d.attachmentPoints,
							toKeep,
							d.entityClass);

					EnumSet<DeviceField> changedFields = EnumSet.noneOf(DeviceField.class);
					for (Entity e : toRemove) {
						changedFields.addAll(findChangedFields(newDevice, e));
					}
					if (changedFields.size() > 0)
						deviceUpdates.add(new DeviceUpdate(d, DeviceUpdate.Change.CHANGE,
								changedFields));

					if (!deviceIdToDeviceMap.replace(newDevice.getDeviceKey(),
							d,
							newDevice)) {
						// concurrent modification; try again
						// need to use device that is the map now for the next iteration
						d = deviceIdToDeviceMap.get(d.getDeviceKey());
						if (null != d)
							continue;
					}
				} else {
					deviceUpdates.add(new DeviceUpdate(d, DeviceUpdate.Change.DELETE, null));
					if (!deviceIdToDeviceMap.remove(d.getDeviceKey(), d))
						// concurrent modification; try again
						// need to use device that is the map now for the next iteration
						d = deviceIdToDeviceMap.get(d.getDeviceKey());
					if (null != d)
						continue;
				}
				processUpdates(deviceUpdates);
				break;
			}
		}
	}

	/**
	 * Gets a destination device using entity fields that corresponds with
	 * the given source device. The source device is important since
	 * there could be ambiguity in the destination device without the
	 * attachment point information.
	 * 
	 * @param source the source device. The returned destination will be
	 *        in the same entity class as the source.
	 * @param dstEntity the entity to look up
	 * 
	 * @return an {@link Device} or null if no device is found
	 */
	public Device findDestByEntity(IDevice source, Entity dstEntity) {

		// Look up the fully-qualified entity to see if it 
		// exists in the primary entity index
		Long deviceKey = primaryIndex.findByEntity(dstEntity);

		if (deviceKey == null) {
			// This could happen because:
			// 1) no destination known, or a broadcast destination
			// 2) if we have attachment point key fields since
			// attachment point information isn't available for
			// destination devices.
			// For the second case, we'll need to match up the
			// destination device with the class of the source
			// device.
			
//			ClassIndices classState = getClassIndices(source.getEntityClass());
//			if (classState.classIndex == null) {
//				return null;
//			}
//			deviceKey = classState.classIndex.findByEntity(dstEntity);
			EntityToSingleDeviceIdIndex index = singleIndices.get(source.getEntityClass().getKeyFields());
			if ( index == null ) {
				return null;
			}
			deviceKey = index.findByEntity(dstEntity);
		}
		if (deviceKey == null) return null;
		return deviceIdToDeviceMap.get(deviceKey);
	}

	/**
	 * Returns an iterator of devices that related to the entity argument.
	 * 
	 * @param clazz the entity class for the entity
	 * @param keyFields the key field to search for
	 * @param entity the entity class to search for
	 * 
	 * @return the iterator of empty set if the entity has no relation,
	 *         the {@link DeviceIdIterator} otherwise
	 */
	public Iterator<Device> queryClassByEntity(IEntityClass clazz,
			EnumSet<DeviceField> keyFields,
			Entity entity) {
//		ClassIndices classState = getClassIndices(clazz);
//		DeviceIndex index = classState.secondaryIndexMap.get(keyFields);
		EntityToMultiDeviceIdIndex index = multiIndices.get(keyFields);
		if (index == null) 
			return Collections.<Device>emptySet().iterator();
		return new DeviceIdIterator(this, index.queryByEntity(entity));
	}

	/**
	 * Updates both the primary and class indices for the provided device.
	 * If the update fails because of an concurrent update, will return false.
	 * 
	 * @param device the device to update
	 * @param deviceId the device key for the device
	 * 
	 * @return true if the update succeeded, false otherwise.
	 */
	private boolean updateIndices(Device device, Long deviceId) {
		if (!primaryIndex.updateIndex(device, deviceId)) {
			return false;
		}
		
//		IEntityClass entityClass = device.getEntityClass();
//		ClassIndices classIndices = getClassIndices(entityClass);
//
//		if (classIndices.classIndex != null) {
//			if (!classIndices.classIndex.updateIndex(device, deviceId))
//				return false;
//		}
		
		EntityToSingleDeviceIdIndex index = singleIndices.get(device.getEntityClass().getKeyFields());
		if ( index == null ) {
			EntityToSingleDeviceIdIndex nindex = 
				new EntityToSingleDeviceIdIndex(device.getEntityClass().getKeyFields());
			index = singleIndices.putIfAbsent(device.getEntityClass().getKeyFields(), nindex);
			if ( index == null ) {
				index = nindex;
			}
			// else, we just dump the nindex because an index is newly added by other thread.
		}
		
		if ( index != primaryIndex ) {
			if ( !index.updateIndex(device, deviceId) ) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Updates the secondary indices for the given entity and associated
	 * entity classes.
	 * 
	 * @param entity the entity to update
	 * @param entityClass the entity class for the entity
	 * @param deviceKey the device key to set up
	 */
	private void updateSecondaryIndices(Entity entity, IEntityClass entityClass, Long deviceKey) {
//		for (DeviceIndex index : classToSecondaryIndexMap.values()) {
//			index.updateIndex(entity, deviceKey);
//		}
//		ClassIndices classIndices = getClassIndices(entityClass);
//		for (DeviceIndex index : classIndices.secondaryIndexMap.values()) {
//			index.updateIndex(entity, deviceKey);
//		}
		
		EntityToMultiDeviceIdIndex index = multiIndices.get(entityClass.getKeyFields());
		
		if ( index == null ) {
			EntityToMultiDeviceIdIndex nindex = new EntityToMultiDeviceIdIndex(entityClass.getKeyFields());
			index = multiIndices.putIfAbsent(entityClass.getKeyFields(), nindex);
			if ( index == null ) {
				index = nindex;
			}
			// else, some other thread already added a new nindex object.
			// thus, we just dump the local nindex.
		}
		
		index.updateIndex(entity, deviceKey);
	}

	/**
	 * Inserts a new DeviceUpdate to the list of {@link DeviceUpdate}s.
	 * 
	 * @param list the list of {@link DeviceUpdate}s before update
	 * @param update the {@link DeviceUpdate} to update
	 * 
	 * @return the list of {@link DeviceUpdate}
	 */
	private LinkedList<DeviceUpdate>
	updateUpdates(LinkedList<DeviceUpdate> list, DeviceUpdate update) {
		if (update == null) return list;
		if (list == null)
			list = new LinkedList<DeviceUpdate>();
		list.add(update);

		return list;
	}

	/**
	 * Checks the changed {@link etri.sdn.controller.module.devicemanager.IDeviceService.DeviceField} of the device.
	 * 
	 * @param device the device to check its entities
	 * @param newEntity the entity to check
	 * 
	 * @return the enum set of DeviceField that changed
	 */
	private EnumSet<DeviceField> findChangedFields(Device device, Entity newEntity) {
		EnumSet<DeviceField> changedFields =
			EnumSet.of(DeviceField.IPV4,
					DeviceField.VLAN,
					DeviceField.SWITCH);

		if (newEntity.getIpv4Address() == null)
			changedFields.remove(DeviceField.IPV4);
		if (newEntity.getVlan() == null)
			changedFields.remove(DeviceField.VLAN);
		if (newEntity.getSwitchDPID() == null ||
				newEntity.getSwitchPort() == null)
			changedFields.remove(DeviceField.SWITCH);

		if (changedFields.size() == 0) return changedFields;

		for (Entity entity : device.getEntities()) {
			if (newEntity.getIpv4Address() == null ||
					(entity.getIpv4Address() != null &&
							entity.getIpv4Address().equals(newEntity.getIpv4Address())))
				changedFields.remove(DeviceField.IPV4);
			if (newEntity.getVlan() == null ||
					(entity.getVlan() != null &&
							entity.getVlan().equals(newEntity.getVlan())))
				changedFields.remove(DeviceField.VLAN);
			if (newEntity.getSwitchDPID() == null ||
					newEntity.getSwitchPort() == null ||
					(entity.getSwitchDPID() != null &&
							entity.getSwitchPort() != null &&
							entity.getSwitchDPID().equals(newEntity.getSwitchDPID()) &&
							entity.getSwitchPort().equals(newEntity.getSwitchPort())))
				changedFields.remove(DeviceField.SWITCH);
		}

		return changedFields;
	}

	/**
	 * Deletes the device from {@link etri.sdn.controller.module.devicemanager.Devices#deviceIdToDeviceMap} and its entities.
	 * 
	 * @param device the device to delete
	 */
	private void deleteDevice(Device device) {
		ArrayList<Entity> emptyToKeep = new ArrayList<Entity>();
		for (Entity entity : device.getEntities()) {
			this.removeEntity(entity, device.getEntityClass(), 
					device.getDeviceKey(), emptyToKeep);
		}
		if (!deviceIdToDeviceMap.remove(device.getDeviceKey(), device)) {
			Logger.stderr("device map does not have this device -" + device.toString());
		}
	}
	
	/**
	 * delete all device attached to a given switch.
	 * 
	 * @param datapathid datapath id of the switch.
	 */
	public void deleteDevice(long datapathid) {
		List<Device> to_remove = new LinkedList<Device>();
		for ( Device d : deviceIdToDeviceMap.values() ) {
			for (SwitchPort sp : d.getAttachmentPoints() ) {
				if ( sp.getSwitchDPID() == datapathid ) {
					to_remove.add( d );
					break;
				}
			}
		}
		for ( Device d : to_remove ) {
			deleteDevice( d );
		}
	}	

	/**
	 * Removes the device of the deviceKey argument from the single and multi indices.
	 * 
	 * @param removed the entity to remove
	 * @param entityClass the entity class for the entity
	 * @param deviceKey the device key to remove
	 * @param others the others against which to check
	 */
	private void removeEntity(Entity removed,
			IEntityClass entityClass,
			Long deviceKey,
			Collection<Entity> others) {
//		for (DeviceIndex index : classToSecondaryIndexMap.values()) {
//			index.removeEntityIfNeeded(removed, deviceKey, others);
//		}
//		ClassIndices classState = getClassIndices(entityClass);
//		for (DeviceIndex index : classState.secondaryIndexMap.values()) {
//			index.removeEntityIfNeeded(removed, deviceKey, others);
//		}
//
//		primaryIndex.removeEntityIfNeeded(removed, deviceKey, others);
//
//		if (classState.classIndex != null) {
//			classState.classIndex.removeEntityIfNeeded(removed,
//					deviceKey,
//					others);
//		}
		EntityToSingleDeviceIdIndex sindex = singleIndices.get(entityClass.getKeyFields());
		if ( sindex != null ) {
			sindex.removeEntityIfNeeded(removed, deviceKey, others);
		}
		EntityToMultiDeviceIdIndex mindex = multiIndices.get(entityClass.getKeyFields());
		if ( mindex != null ) {
			mindex.removeEntityIfNeeded(removed, deviceKey, others);
		}
	}

	/**
	 * This method will reclassify and reconcile a device - possibilities
	 * are - create new device(s), remove entities from this device. 
	 * 
	 * @param device the device to reclassify
	 * 
	 * @return true if the device entity class is changed, false otherwise
	 */
	private boolean reclassifyDevice(Device device)
	{
		// first classify all entities of this device
		if (device == null) {
			return false;
		}
		boolean needToReclassify = false;
		for (Entity entity : device.entities) {
			IEntityClass entityClass = classifier.classifyEntity(entity);
			if (entityClass == null || device.getEntityClass() == null) {
				needToReclassify = true;                
				break;
			}
			if (!entityClass.getName().equals(device.getEntityClass().getName())) {
				needToReclassify = true;
				break;
			}
		}
		if (needToReclassify == false) {
			return false;
		}

		LinkedList<DeviceUpdate> deviceUpdates =
			new LinkedList<DeviceUpdate>();
		// delete this device and then re-learn all the entities
		this.deleteDevice(device);
		deviceUpdates.add(new DeviceUpdate(device, DeviceUpdate.Change.DELETE, null));
		if (!deviceUpdates.isEmpty()) {
			processUpdates(deviceUpdates);
		}
		for (Entity entity: device.entities ) {
			this.learnDeviceByEntity(entity);
		}
		return true;
	}    

	/**
	 * Sends update notifications to listeners.
	 * 
	 * @param updates the updates to process.
	 */
	private void processUpdates(Queue<DeviceUpdate> updates) {
		if (updates == null) return;
		DeviceUpdate update = null;
		while (null != (update = updates.poll())) {
			for (IDeviceListener listener : deviceListeners) {
				switch (update.change) {
				case ADD:
					listener.deviceAdded(update.device);
					break;
				case DELETE:
					listener.deviceRemoved(update.device);
					break;
				case CHANGE:
					for (DeviceField field : update.fieldsChanged) {
						switch (field) {
						case IPV4:
							listener.deviceIPV4AddrChanged(update.device);
							break;
						case SWITCH:
						case PORT:
							//listener.deviceMoved(update.device);
							break;
						case VLAN:
							listener.deviceVlanChanged(update.device);
							break;
						default:
							Logger.stderr("Unknown device field changed {}: " + 
									update.fieldsChanged.toString());
							break;
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Sends update notifications to listeners.
	 * 
	 * @param d the device to updates
	 */
	private void sendDeviceMovedNotification(Device d) {
		for (IDeviceListener listener : deviceListeners) {
			listener.deviceMoved(d);
		}
	}

	/**
	 * Checks if the entity e has all the keyFields set. Returns false if not.
	 * 
	 * @param e entity to check 
	 * @param keyFields the key fields to check e against
	 * 
	 * @return true if the entity argument has all the key fields, false otherwise
	 */
	private boolean allKeyFieldsPresent(Entity e, EnumSet<DeviceField> keyFields) {
		for (DeviceField f : keyFields) {
			switch (f) {
			case MAC:
				// MAC address is always present
				break;
			case IPV4:
				if (e.ipv4Address == null) return false;
				break;
			case SWITCH:
				if (e.switchDPID == null) return false;
				break;
			case PORT:
				if (e.switchPort == null) return false;
				break;
			case VLAN:
				// FIXME: vlan==null is ambiguous: it can mean: not present
				// or untagged
				//if (e.vlan == null) return false;
				break;
			default:
				// we should never get here. unless somebody extended 
				// DeviceFields
				throw new IllegalStateException();
			}
		}
		return true;
	}

	/**
	 * Looks up a {@link Device} based on the provided {@link Entity}. We first
	 * check the primary index. If we do not find an entry there we classify
	 * the device into its {@link IEntityClass} and query the classIndex. 
	 * This implies that all key field of the current {@link IEntityClassifierService} 
	 * must be present in the entity for the lookup to succeed!
	 * 
	 * @param entity the entity to search for
	 * 
	 * @return The {@link Device} object if found
	 */
	private Device findDeviceByEntity(Entity entity) {
		// Look up the fully-qualified entity to see if it already
		// exists in the primary entity index.
		Long deviceKey = primaryIndex.findByEntity(entity);
		IEntityClass entityClass = null;

		if (deviceKey == null) {
			// If the entity does not exist in the primary entity index,
			// use the entity classifier for find the classes for the
			// entity. Look up the entity in the returned class'
			// class entity index.
			entityClass = classifier.classifyEntity(entity);
			if (entityClass == null) {
				return null;
			}
//			ClassIndices classState = getClassIndices(entityClass);
//
//			if (classState.classIndex != null) {
//				deviceKey = classState.classIndex.findByEntity(entity);
//			}
			EntityToSingleDeviceIdIndex index = singleIndices.get(entityClass.getKeyFields());
			if ( index != null && index != primaryIndex ) {
				deviceKey = index.findByEntity(entity);
			}
		}
		if (deviceKey == null) return null;
		return deviceIdToDeviceMap.get(deviceKey);
	}

//	/**
//	 * Gets the secondary index for a class.  Will return null if the
//	 * secondary index was created concurrently in another thread.
//	 * 
//	 * @param clazz the class for the index
//	 * 
//	 * @return
//	 */
//	private ClassIndices getClassIndices(IEntityClass clazz) {
//		ClassIndices classIndices = entityClassNameToClassIndicesMap.get(clazz.getName());
//		if (classIndices != null) return classIndices;
//
//		classIndices = new ClassIndices(clazz);
//		ClassIndices r = entityClassNameToClassIndicesMap.putIfAbsent(clazz.getName(), classIndices);
//		if (r != null) {
//			// concurrent add
//			return r;
//		}
//		return classIndices;
//	}

	/**
	 * Returns the key fields for each argument that is not null.
	 * 
	 * @param macAddress MAC address
	 * @param vlan VLAN ID
	 * @param ipv4Address IPv4 address
	 * @param switchDPID the switch dpid
	 * @param switchPort the switch port
	 * 
	 * @return the enum set of key fields
	 */
	private EnumSet<DeviceField> getEntityKeys(Long macAddress,
			Short vlan,
			Integer ipv4Address,
			Long switchDPID,
			OFPort switchPort) {
		// FIXME: vlan==null is a valid search. Need to handle this
		// case correctly. Note that the code will still work correctly. 
		// But we might do a full device search instead of using an index.
		EnumSet<DeviceField> keys = EnumSet.noneOf(DeviceField.class);
		if (macAddress != null) keys.add(DeviceField.MAC);
		if (vlan != null) keys.add(DeviceField.VLAN);
		if (ipv4Address != null) keys.add(DeviceField.IPV4);
		if (switchDPID != null) keys.add(DeviceField.SWITCH);
		if (switchPort != null) keys.add(DeviceField.PORT);
		return keys;
	}

	/*
	 * IDeviceService methods
	 * @see etri.sdn.controller.module.devicemanager.IDeviceService#getDevice(java.lang.Long)
	 */

	@Override
	public IDevice getDevice(Long deviceKey) {
		return deviceIdToDeviceMap.get(deviceKey);
	}

	@Override
	public IDevice findDevice(long macAddress, Short vlan, 
			Integer ipv4Address, Long switchDPID, 
			OFPort switchPort)	throws IllegalArgumentException {
		if (vlan != null && vlan.shortValue() <= 0)
			vlan = null;
		if (ipv4Address != null && ipv4Address == 0)
			ipv4Address = null;
		Entity e = new Entity(macAddress, vlan, ipv4Address, switchDPID,
				switchPort, null);
		if (!allKeyFieldsPresent(e, classifier.getKeyFields())) {
			throw new IllegalArgumentException("Not all key fields specified."
					+ " Required fields: " + classifier.getKeyFields());
		}
		return findDeviceByEntity(e);
	}

	@Override
	public IDevice findDestDevice(IDevice source, long macAddress, Short vlan,
			Integer ipv4Address) throws IllegalArgumentException {
		if (vlan != null && vlan.shortValue() <= 0)
			vlan = null;
		if (ipv4Address != null && ipv4Address == 0)
			ipv4Address = null;
		Entity e = new Entity(macAddress, vlan, ipv4Address, null, null, null);
		if (source == null || 
				!allKeyFieldsPresent(e, source.getEntityClass().getKeyFields())) {
			throw new IllegalArgumentException("Not all key fields and/or "
					+ " no source device specified. Required fields: " + 
					classifier.getKeyFields());
		}
		return findDestByEntity(source, e);
	}

	@Override
	public Collection<? extends IDevice> getAllDevices() {
		return Collections.unmodifiableCollection(deviceIdToDeviceMap.values());
	}

	@Override
	public void addIndex(boolean perClass, EnumSet<DeviceField> keyFields) {
//		if (perClass) {
//			/*
//			 * This will make a ClassIndices object build its {@link ClassIndices#secondaryIndexMap} 
//			 * using the keyFields within {@link #classes} set. 
//			 */
//			classes.add(keyFields);
//		} else {
//			classToSecondaryIndexMap.put(keyFields, new EntityToMultiDeviceIdIndex(keyFields));
//		}
		// no-op.
	}

	@Override
	public Iterator<? extends IDevice> queryDevices(Long macAddress,
			Short vlan, Integer ipv4Address, Long switchDPID, OFPort switchPort) {
		DeviceIndex index = null;
//		if (classToSecondaryIndexMap.size() > 0) {
//			EnumSet<DeviceField> keys = getEntityKeys(macAddress, vlan, ipv4Address, switchDPID, switchPort);
//			index = classToSecondaryIndexMap.get(keys);
//		}
		EnumSet<DeviceField> keys = getEntityKeys(macAddress, vlan, ipv4Address, switchDPID, switchPort);
		index = multiIndices.get(keys);

		Iterator<Device> deviceIterator = null;
		if (index == null) {
			// Do a full table scan
			deviceIterator = deviceIdToDeviceMap.values().iterator();
		} else {
			// index lookup
			Entity entity = new Entity((macAddress == null ? 0 : macAddress),
					vlan,
					ipv4Address,
					switchDPID,
					switchPort,
					null);
			deviceIterator = new DeviceIdIterator(this, index.queryByEntity(entity));
		}

		DeviceIterator di =
			new DeviceIterator(deviceIterator,
					null,
					macAddress,
					vlan,
					ipv4Address,
					switchDPID,
					switchPort);
		return di;
	}

	@Override
	public Iterator<? extends IDevice> queryClassDevices(IDevice reference,
			Long macAddress, Short vlan, Integer ipv4Address, Long switchDPID,
			OFPort switchPort) {
		IEntityClass entityClass = reference.getEntityClass();
		ArrayList<Iterator<Device>> iterators = new ArrayList<Iterator<Device>>();
//		ClassIndices classState = getClassIndices(entityClass);

		DeviceIndex index = null;
//		if (classState.secondaryIndexMap.size() > 0) {
//			EnumSet<DeviceField> keys = getEntityKeys(macAddress, vlan, ipv4Address, switchDPID, switchPort);
//			index = classState.secondaryIndexMap.get(keys);
//		}
		EnumSet<DeviceField> keys = getEntityKeys(macAddress, vlan, ipv4Address, switchDPID, switchPort);
		index = multiIndices.get(keys);

		Iterator<Device> iter;
		if (index == null) {
//			index = classState.classIndex;
			index = singleIndices.get(keys);
			if (index == null) {
				// scan all devices
				return new DeviceIterator(deviceIdToDeviceMap.values().iterator(),
						new IEntityClass[] { entityClass },
						macAddress, vlan, ipv4Address,
						switchDPID, switchPort);
			} else {
				// scan the entire class
				iter = new DeviceIdIterator(this, index.getAll());
			}
		} else {
			// index lookup
			Entity entity =
				new Entity((macAddress == null ? 0 : macAddress),
						vlan,
						ipv4Address,
						switchDPID,
						switchPort,
						null);
			iter = new DeviceIdIterator(this, index.queryByEntity(entity));
		}
		iterators.add(iter);

		return new MultiIterator<Device>(iterators.iterator());
	}

	@Override
	public void addListener(IDeviceListener listener) {
		this.deviceListeners.add(listener);
	}

	@Override
	public void addSuppressAPs(long swId, OFPort port) {
		this.suppressedAPs.add(new SwitchPort(swId, port));
	}

	@Override
	public void removeSuppressAPs(long swId, OFPort port) {
		this.suppressedAPs.remove(new SwitchPort(swId, port));
	}

	/**
	 * Is the switchPort argument contained to the set of switch ports
	 * not to be learned?
	 * 
	 * @param switchPort the switch port to check 
	 * 
	 * @return true if suppressedAPs includes switchPort, false otherwise 
	 */
	public boolean isSuppressedAP(SwitchPort switchPort) {
		if ( suppressedAPs.contains(switchPort) )
			return true;
		return false;
	}

	/**
	 * Returns information of devices that have more than one attachment point.
	 * This method is used only for debugging.
	 * 
	 * @return String of device list or null
	 */
	public String getHostDebugInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for ( Device d : this.deviceIdToDeviceMap.values() ) {
			if ( d.attachmentPoints.size() > 0 ) {
				sb.append(d.toString() + "\n");
			}
		}
		return sb.toString();
	}
	
	
	/*
	 * OFModel methods which must be overridden.
	 * 
	 * @see etri.sdn.controller.OFModel#getModelURI()
	 * @see etri.sdn.controller.OFModel#handle()
	 */
	
	/**
	 * Class to convert Attachment Point information into JSON format
	 *  
	 * @author bjlee
	 *
	 */
	class RESTAttachmentPoint {
		public final OFPort port;
		public final String switchDPID;
		public final ErrorStatus errorStatus;
		
		public RESTAttachmentPoint(SwitchPort sport) {
			this.port = sport.getPort();
			byte[] bDPID = ByteBuffer.allocate(8).putLong(sport.getSwitchDPID()).array();
			this.switchDPID = String.format("%02x:%02x:%02x:%02x:%02x:%02x:%02x:%02x",
					bDPID[0], bDPID[1], bDPID[2], bDPID[3], bDPID[4], bDPID[5], bDPID[6], bDPID[7]);
			this.errorStatus =  sport.getErrorStatus();
		}
	}
	
	/** 
	 * Class to convert Device information into JSON format
	 * 
	 * @author bjlee
	 *
	 */
	class RESTDevice {
		
		public final String entityClass;
		public final List<String> mac;
		public final List<String> ipv4;
		public final List<Short> vlan;
		public final List<RESTAttachmentPoint> attachmentPoint;
		public final long lastSeen;
		
		public RESTDevice(Device dev) {
			this.entityClass = dev.getEntityClass().getName();
			this.mac = new LinkedList<String>();
			this.mac.add(dev.getMACAddressString());

			this.ipv4 = new LinkedList<String>();
			Integer[] ipaddrs = dev.getIPv4Addresses();
			for ( int i = 0; ipaddrs != null && i < ipaddrs.length; ++i ) {
				this.ipv4.add(IPv4.fromIPv4Address(ipaddrs[i]));
			}

			this.vlan = new LinkedList<Short>();
			Short[] vids = dev.getVlanId();
			for ( int i = 0; vids != null && i < vids.length; ++i ) {
				if ( vids[i] > 0 ) vlan.add(vids[i]);
			}
			
			this.attachmentPoint = new LinkedList<RESTAttachmentPoint>();
			SwitchPort[] apoints = dev.getAttachmentPoints();
			for ( int i = 0; apoints != null && i < apoints.length; ++i ) {
				attachmentPoint.add(new RESTAttachmentPoint(apoints[i]));
			}
			
			this.lastSeen = dev.getLastSeen().getTime();
		}
		
	}
	
	/**
	 * Array of RESTApi objects.
	 * Each objects represent a REST call handler routine bound to a specific URI.
	 */
	private RESTApi[] apis = {
		new RESTApi(
			"/wm/device/all/json",
			new Restlet() {
				
				@Override
				public void handle(Request request, Response response) {
					// create an object mapper.
					ObjectMapper om = new ObjectMapper();

					// retrieve all device information as JSON.
					List<RESTDevice> list = new LinkedList<RESTDevice>();
					for ( Long l : deviceIdToDeviceMap.keySet() ) {
						list.add( new RESTDevice(deviceIdToDeviceMap.get(l)) );
					}

					try {
						String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(list);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		)
	};

	/**
	 * Returns the list of RESTApi objects.
	 * 
	 * @return array of all RESTApi objects 
	 */
	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}
}
