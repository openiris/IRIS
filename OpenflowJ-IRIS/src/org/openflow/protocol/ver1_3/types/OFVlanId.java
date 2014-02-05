
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFVlanId {
  
	NONE(0, org.openflow.protocol.interfaces.OFVlanId.NONE),
	PRESENT(0x1000, org.openflow.protocol.interfaces.OFVlanId.PRESENT);
  
	private static Map<Short, OFVlanId> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFVlanId> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFVlanId, OFVlanId> __compatMappingReverse;
    
	private static OFVlanId __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFVlanId cvalue, OFVlanId obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFVlanId>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFVlanId>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFVlanId, OFVlanId>();
		if (__array == null) __array = new OFVlanId[2];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFVlanId first() {
		return __array[0];
	}
  
	public static OFVlanId last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFVlanId(int value, org.openflow.protocol.interfaces.OFVlanId cvalue) {
		this.value = (short)value;
		OFVlanId.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFVlanId valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFVlanId to(OFVlanId i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFVlanId from(org.openflow.protocol.interfaces.OFVlanId c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}