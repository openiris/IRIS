/**
*    Copyright 2011,2012, Big Switch Networks, Inc. 
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

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

import org.projectfloodlight.openflow.types.OFPort;

import etri.sdn.controller.IService;

/**
 * The device manager allows interacting with devices on the network. Note
 * that under normal circumstances, {@link Device} objects should be retrieved
 * from the {@link etri.sdn.controller.MessageContext}.
 */
public interface IDeviceService extends IService {
    /**
     * Fields used in devices for indexes and querying
     * 
     * @see IDeviceService#addIndex
     */
    enum DeviceField {
        MAC, IPV4, VLAN, SWITCH, PORT
    }

    /**
     * Gets the device with the given device key.
     * 
     * @param deviceKey the key to search for
     * 
     * @return the device associated with the key, or null if no such device
     * 
     * @see IDevice#getDeviceKey()
     */
    public IDevice getDevice(Long deviceKey);
    
    /**
     * Searches for a device exactly matching the provided device fields. This 
     * is the same lookup process that is used for packet_in processing and 
     * device learning. Thus, findDevice() can be used to match flow entries
     * from switches to devices. 
     * <p>
     * Only the key fields as defined by the {@link IEntityClassifierService} will
     * be important in this search. All key fields MUST be supplied. 
     * 
     *{@link #queryDevices} might be more appropriate!
     * 
     * @param macAddress The MAC address
     * @param vlan the VLAN. Null means no VLAN and is valid even if VLAN is a key field.
     * @param ipv4Address the ipv4 address
     * @param switchDPID the switch dpid
     * @param switchPort the switch port
     * 
     * @return an {@link IDevice} or null if no device is found.
     * 
     * @throws IllegalArgumentException if not all key fields of the
     *         current {@link IEntityClassifierService} are specified.
     */
    public IDevice findDevice(long macAddress, Short vlan,
                              Integer ipv4Address, Long switchDPID,
                              OFPort switchPort)
                              throws IllegalArgumentException;
    
    /**
     * Gets a destination device using entity fields that corresponds with
     * the given source device. The source device is important since
     * there could be ambiguity in the destination device without the
     * attachment point information. 
     * 
     * @param source the source device.  The returned destination will be
     *        in the same entity class as the source.
     * @param macAddress The MAC address for the destination
     * @param vlan the VLAN if available
     * @param ipv4Address The IP address if available.
     * 
     * @return an {@link IDevice} or null if no device is found.
     * 
     * @see IDeviceService#findDevice(long, Short, Integer, Long, Integer)
     * 
     * @throws IllegalArgumentException if not all key fields of the
     *         source's {@link IEntityClass} are specified.
     */
    public IDevice findDestDevice(IDevice source,
                                  long macAddress, Short vlan,
                                  Integer ipv4Address)
                                  throws IllegalArgumentException;

    /**
     * Gets an unmodifiable collection view over all devices currently known.
     * 
     * @return the collection of all devices
     */
    public Collection<? extends IDevice> getAllDevices();

    /**
     * Creates an index over a set of fields. This allows efficient lookup
     * of devices when querying using the indexed set of specified fields.
     * The index must be registered before any device learning takes place,
     * or it may be incomplete.  It's OK if this is called multiple times with
     * the same fields; only one index will be created for each unique set of 
     * fields.
     * 
     * @param perClass set to true if the index should be maintained for each
     *        entity class separately.
     * @param keyFields the set of fields on which to index
     */
    public void addIndex(boolean perClass,
                         EnumSet<DeviceField> keyFields);
    
    /**
     * Finds devices that match the provided query. Any fields that are
     * null will not be included in the query. If there is an index for 
     * the query, then it will be performed efficiently using the index.
     * Otherwise, there will be a full scan of the device list.
     * 
     * @param macAddress The MAC address
     * @param vlan the VLAN
     * @param ipv4Address the ipv4 address
     * @param switchDPID the switch dpid
     * @param switchPort the switch port
     * 
     * @return an iterator over a set of devices matching the query
     * 
     */
    public Iterator<? extends IDevice> queryDevices(Long macAddress,
                                                    Short vlan,
                                                    Integer ipv4Address, 
                                                    Long switchDPID,
                                                    OFPort switchPort);

    /**
     * Finds devices that match the provided query. Only the index for
     * the class of the specified reference device will be searched.  
     * Any fields that are null will not be included in the query. 
     * If there is an index for the query, then it will be performed
     * efficiently using the index. Otherwise, there will be a full scan
     * of the device list.
     * 
     * @param reference The reference device to refer to when finding
     *        entity classes.
     * @param macAddress The MAC address
     * @param vlan the VLAN
     * @param ipv4Address the ipv4 address
     * @param switchDPID the switch dpid
     * @param switchPort the switch port
     * 
     * @return an iterator over a set of devices matching the query
     * 
     */
    public Iterator<? extends IDevice> queryClassDevices(IDevice reference,
                                                         Long macAddress,
                                                         Short vlan,
                                                         Integer ipv4Address, 
                                                         Long switchDPID,
                                                         OFPort switchPort);
    
    /**
     * Adds a listener to listen for IDeviceManagerServices notifications.
     * 
     * @param listener The listener that wants the notifications
     */
    public void addListener(IDeviceListener listener);
    
    /**
     * Specifies points in the network where attachment points are not to
     * be learned.
     * 
     * @param swId a switchid
     * @param port a port number
     */
	public void addSuppressAPs(long swId, OFPort port);

	/**
     * Removes setting of points in the network where attachment points
     * are not to be learned.
     * 
     * @param swId a switchid
     * @param port a port number
     */
	public void removeSuppressAPs(long swId, OFPort port);

}
