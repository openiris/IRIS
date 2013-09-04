package etri.sdn.controller.module.devicemanager;

import java.util.Iterator;

/**
 * This class consists of devices and an iterator for device keys.
 *
 */
public class DeviceIdIterator implements Iterator<Device> {

	private Devices devices;
    private Iterator<Long> subIterator;

    /**
     * Construct a new device index iterator referring to a device manager
     * instance and an iterator over device keys
     * 
     * @param devices the devices
     * @param subIterator an iterator over device keys
     */
    public DeviceIdIterator(Devices devices, Iterator<Long> subIterator) {
        super();
        this.devices = devices;
        this.subIterator = subIterator;
    }

    @Override
    public boolean hasNext() {
        return subIterator.hasNext();
    }

    @Override
    public Device next() {
        Long next = subIterator.next();
        return devices.getDeviceMap().get(next);
    }

    @Override
    public void remove() {
        subIterator.remove();
    }

}
