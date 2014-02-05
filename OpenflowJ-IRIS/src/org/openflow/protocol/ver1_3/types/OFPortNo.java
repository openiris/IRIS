
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFPortNo {
  
	MAX(0xffffff00, org.openflow.protocol.interfaces.OFPortNo.MAX),
	IN_PORT(0xfffffff8, org.openflow.protocol.interfaces.OFPortNo.IN_PORT),
	TABLE(0xfffffff9, org.openflow.protocol.interfaces.OFPortNo.TABLE),
	NORMAL(0xfffffffa, org.openflow.protocol.interfaces.OFPortNo.NORMAL),
	FLOOD(0xfffffffb, org.openflow.protocol.interfaces.OFPortNo.FLOOD),
	ALL(0xfffffffc, org.openflow.protocol.interfaces.OFPortNo.ALL),
	CONTROLLER(0xfffffffd, org.openflow.protocol.interfaces.OFPortNo.CONTROLLER),
	LOCAL(0xfffffffe, org.openflow.protocol.interfaces.OFPortNo.LOCAL),
	ANY(0xffffffff, org.openflow.protocol.interfaces.OFPortNo.ANY);
  
	private static Map<Integer, OFPortNo> __index;
	static Map<Integer, org.openflow.protocol.interfaces.OFPortNo> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFPortNo, OFPortNo> __compatMappingReverse;
    
	private static OFPortNo __array[];
	private static int __array_index = 0;
  
	private int value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFPortNo cvalue, OFPortNo obj) {
		if (__index == null) __index = new ConcurrentHashMap<Integer, OFPortNo>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Integer, org.openflow.protocol.interfaces.OFPortNo>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortNo, OFPortNo>();
		if (__array == null) __array = new OFPortNo[9];
		
		__index.put((int)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((int)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFPortNo first() {
		return __array[0];
	}
  
	public static OFPortNo last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFPortNo(int value, org.openflow.protocol.interfaces.OFPortNo cvalue) {
		this.value = (int)value;
		OFPortNo.addMapping(value, cvalue, this);
	}
  
	public int getValue() {
		return this.value;
	}
  
	public static OFPortNo valueOf(int value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFPortNo to(OFPortNo i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFPortNo from(org.openflow.protocol.interfaces.OFPortNo c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
}