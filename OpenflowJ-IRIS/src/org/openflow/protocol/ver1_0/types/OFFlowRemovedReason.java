
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFFlowRemovedReason {
  
	IDLE_TIMEOUT(0, org.openflow.protocol.interfaces.OFFlowRemovedReason.IDLE_TIMEOUT),
	HARD_TIMEOUT(1, org.openflow.protocol.interfaces.OFFlowRemovedReason.HARD_TIMEOUT),
	DELETE(2, org.openflow.protocol.interfaces.OFFlowRemovedReason.DELETE);
  
	private static Map<Byte, OFFlowRemovedReason> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFFlowRemovedReason> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFFlowRemovedReason, OFFlowRemovedReason> __compatMappingReverse;
    
	private static OFFlowRemovedReason __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFFlowRemovedReason cvalue, OFFlowRemovedReason obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFFlowRemovedReason>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFFlowRemovedReason>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFFlowRemovedReason, OFFlowRemovedReason>();
		if (__array == null) __array = new OFFlowRemovedReason[3];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFFlowRemovedReason first() {
		return __array[0];
	}
  
	public static OFFlowRemovedReason last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFFlowRemovedReason(int value, org.openflow.protocol.interfaces.OFFlowRemovedReason cvalue) {
		this.value = (byte)value;
		OFFlowRemovedReason.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFFlowRemovedReason valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFFlowRemovedReason to(OFFlowRemovedReason i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFFlowRemovedReason from(org.openflow.protocol.interfaces.OFFlowRemovedReason c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}