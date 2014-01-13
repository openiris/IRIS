
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFGroupCategory {
  
	ALL(0, org.openflow.protocol.interfaces.OFGroupCategory.ALL),
	SELECT(1, org.openflow.protocol.interfaces.OFGroupCategory.SELECT),
	INDIRECT(2, org.openflow.protocol.interfaces.OFGroupCategory.INDIRECT),
	FF(3, org.openflow.protocol.interfaces.OFGroupCategory.FF);
  
	private static Map<Byte, OFGroupCategory> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFGroupCategory> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFGroupCategory, OFGroupCategory> __compatMappingReverse;
    
	private static OFGroupCategory __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFGroupCategory cvalue, OFGroupCategory obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFGroupCategory>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFGroupCategory>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFGroupCategory, OFGroupCategory>();
		if (__array == null) __array = new OFGroupCategory[4];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFGroupCategory first() {
		return __array[0];
	}
  
	public static OFGroupCategory last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFGroupCategory(int value, org.openflow.protocol.interfaces.OFGroupCategory cvalue) {
		this.value = (byte)value;
		OFGroupCategory.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFGroupCategory valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFGroupCategory to(OFGroupCategory i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFGroupCategory from(org.openflow.protocol.interfaces.OFGroupCategory c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}