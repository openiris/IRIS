
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFMeter {
  
	MAX(0xffff0000, org.openflow.protocol.interfaces.OFMeter.MAX),
	SLOWPATH(0xfffffffd, org.openflow.protocol.interfaces.OFMeter.SLOWPATH),
	CONTROLLER(0xfffffffe, org.openflow.protocol.interfaces.OFMeter.CONTROLLER),
	ALL(0xffffffff, org.openflow.protocol.interfaces.OFMeter.ALL);
  
	private static Map<Integer, OFMeter> __index;
	static Map<Integer, org.openflow.protocol.interfaces.OFMeter> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFMeter, OFMeter> __compatMappingReverse;
    
	private static OFMeter __array[];
	private static int __array_index = 0;
  
	private int value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFMeter cvalue, OFMeter obj) {
		if (__index == null) __index = new ConcurrentHashMap<Integer, OFMeter>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Integer, org.openflow.protocol.interfaces.OFMeter>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMeter, OFMeter>();
		if (__array == null) __array = new OFMeter[4];
		
		__index.put((int)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((int)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFMeter first() {
		return __array[0];
	}
  
	public static OFMeter last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFMeter(int value, org.openflow.protocol.interfaces.OFMeter cvalue) {
		this.value = (int)value;
		OFMeter.addMapping(value, cvalue, this);
	}
  
	public int getValue() {
		return this.value;
	}
  
	public static OFMeter valueOf(int value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFMeter to(OFMeter i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFMeter from(org.openflow.protocol.interfaces.OFMeter c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
}