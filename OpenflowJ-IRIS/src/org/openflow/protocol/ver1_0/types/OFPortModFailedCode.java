
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFPortModFailedCode {
  
	BAD_PORT(0, org.openflow.protocol.interfaces.OFPortModFailedCode.BAD_PORT),
	BAD_HW_ADDR(1, org.openflow.protocol.interfaces.OFPortModFailedCode.BAD_HW_ADDR);
  
	private static Map<Short, OFPortModFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFPortModFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFPortModFailedCode, OFPortModFailedCode> __compatMappingReverse;
    
	private static OFPortModFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFPortModFailedCode cvalue, OFPortModFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFPortModFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFPortModFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortModFailedCode, OFPortModFailedCode>();
		if (__array == null) __array = new OFPortModFailedCode[2];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFPortModFailedCode first() {
		return __array[0];
	}
  
	public static OFPortModFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFPortModFailedCode(int value, org.openflow.protocol.interfaces.OFPortModFailedCode cvalue) {
		this.value = (short)value;
		OFPortModFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFPortModFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFPortModFailedCode to(OFPortModFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFPortModFailedCode from(org.openflow.protocol.interfaces.OFPortModFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}