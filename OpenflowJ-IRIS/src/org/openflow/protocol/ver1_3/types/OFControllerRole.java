
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFControllerRole {
  
	ROLE_NOCHANGE(0, org.openflow.protocol.interfaces.OFControllerRole.ROLE_NOCHANGE),
	ROLE_EQUAL(1, org.openflow.protocol.interfaces.OFControllerRole.ROLE_EQUAL),
	ROLE_MASTER(2, org.openflow.protocol.interfaces.OFControllerRole.ROLE_MASTER),
	ROLE_SLAVE(3, org.openflow.protocol.interfaces.OFControllerRole.ROLE_SLAVE);
  
	private static Map<Integer, OFControllerRole> __index;
	static Map<Integer, org.openflow.protocol.interfaces.OFControllerRole> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFControllerRole, OFControllerRole> __compatMappingReverse;
    
	private static OFControllerRole __array[];
	private static int __array_index = 0;
  
	private int value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFControllerRole cvalue, OFControllerRole obj) {
		if (__index == null) __index = new ConcurrentHashMap<Integer, OFControllerRole>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Integer, org.openflow.protocol.interfaces.OFControllerRole>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFControllerRole, OFControllerRole>();
		if (__array == null) __array = new OFControllerRole[4];
		
		__index.put((int)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((int)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFControllerRole first() {
		return __array[0];
	}
  
	public static OFControllerRole last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFControllerRole(int value, org.openflow.protocol.interfaces.OFControllerRole cvalue) {
		this.value = (int)value;
		OFControllerRole.addMapping(value, cvalue, this);
	}
  
	public int getValue() {
		return this.value;
	}
  
	public static OFControllerRole valueOf(int value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFControllerRole to(OFControllerRole i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFControllerRole from(org.openflow.protocol.interfaces.OFControllerRole c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
}