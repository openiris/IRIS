
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFGroup {
  
	MAX(0xffffff00, org.openflow.protocol.interfaces.OFGroup.MAX),
	ALL(0xfffffffc, org.openflow.protocol.interfaces.OFGroup.ALL),
	ANY(0xffffffff, org.openflow.protocol.interfaces.OFGroup.ANY);
  
	private static Map<Integer, OFGroup> __index;
	static Map<Integer, org.openflow.protocol.interfaces.OFGroup> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFGroup, OFGroup> __compatMappingReverse;
    
	private static OFGroup __array[];
	private static int __array_index = 0;
  
	private int value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFGroup cvalue, OFGroup obj) {
		if (__index == null) __index = new ConcurrentHashMap<Integer, OFGroup>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Integer, org.openflow.protocol.interfaces.OFGroup>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFGroup, OFGroup>();
		if (__array == null) __array = new OFGroup[3];
		
		__index.put((int)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((int)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFGroup first() {
		return __array[0];
	}
  
	public static OFGroup last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFGroup(int value, org.openflow.protocol.interfaces.OFGroup cvalue) {
		this.value = (int)value;
		OFGroup.addMapping(value, cvalue, this);
	}
  
	public int getValue() {
		return this.value;
	}
  
	public static OFGroup valueOf(int value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFGroup to(OFGroup i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFGroup from(org.openflow.protocol.interfaces.OFGroup c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
}