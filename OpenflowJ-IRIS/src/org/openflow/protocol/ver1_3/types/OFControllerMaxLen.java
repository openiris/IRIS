
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFControllerMaxLen {
  
	MAX(0xffe5, org.openflow.protocol.interfaces.OFControllerMaxLen.MAX),
	NO_BUFFER(0xffff, org.openflow.protocol.interfaces.OFControllerMaxLen.NO_BUFFER);
  
	private static Map<Short, OFControllerMaxLen> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFControllerMaxLen> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFControllerMaxLen, OFControllerMaxLen> __compatMappingReverse;
    
	private static OFControllerMaxLen __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFControllerMaxLen cvalue, OFControllerMaxLen obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFControllerMaxLen>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFControllerMaxLen>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFControllerMaxLen, OFControllerMaxLen>();
		if (__array == null) __array = new OFControllerMaxLen[2];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFControllerMaxLen first() {
		return __array[0];
	}
  
	public static OFControllerMaxLen last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFControllerMaxLen(int value, org.openflow.protocol.interfaces.OFControllerMaxLen cvalue) {
		this.value = (short)value;
		OFControllerMaxLen.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFControllerMaxLen valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFControllerMaxLen to(OFControllerMaxLen i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFControllerMaxLen from(org.openflow.protocol.interfaces.OFControllerMaxLen c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}