
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFPortReason {
  
	ADD(0, org.openflow.protocol.interfaces.OFPortReason.ADD),
	DELETE(1, org.openflow.protocol.interfaces.OFPortReason.DELETE),
	MODIFY(2, org.openflow.protocol.interfaces.OFPortReason.MODIFY);
  
	private static Map<Byte, OFPortReason> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFPortReason> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFPortReason, OFPortReason> __compatMappingReverse;
    
	private static OFPortReason __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFPortReason cvalue, OFPortReason obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFPortReason>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFPortReason>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortReason, OFPortReason>();
		if (__array == null) __array = new OFPortReason[3];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFPortReason first() {
		return __array[0];
	}
  
	public static OFPortReason last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFPortReason(int value, org.openflow.protocol.interfaces.OFPortReason cvalue) {
		this.value = (byte)value;
		OFPortReason.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFPortReason valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFPortReason to(OFPortReason i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFPortReason from(org.openflow.protocol.interfaces.OFPortReason c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}