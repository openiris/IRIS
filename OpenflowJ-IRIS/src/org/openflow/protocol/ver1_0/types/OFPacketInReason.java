
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFPacketInReason {
  
	NO_MATCH(0, org.openflow.protocol.interfaces.OFPacketInReason.NO_MATCH),
	ACTION(1, org.openflow.protocol.interfaces.OFPacketInReason.ACTION);
  
	private static Map<Byte, OFPacketInReason> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFPacketInReason> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFPacketInReason, OFPacketInReason> __compatMappingReverse;
    
	private static OFPacketInReason __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFPacketInReason cvalue, OFPacketInReason obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFPacketInReason>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFPacketInReason>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPacketInReason, OFPacketInReason>();
		if (__array == null) __array = new OFPacketInReason[2];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFPacketInReason first() {
		return __array[0];
	}
  
	public static OFPacketInReason last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFPacketInReason(int value, org.openflow.protocol.interfaces.OFPacketInReason cvalue) {
		this.value = (byte)value;
		OFPacketInReason.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFPacketInReason valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFPacketInReason to(OFPacketInReason i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFPacketInReason from(org.openflow.protocol.interfaces.OFPacketInReason c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}