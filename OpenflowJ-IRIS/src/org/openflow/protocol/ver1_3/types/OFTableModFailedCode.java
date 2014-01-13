
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFTableModFailedCode {
  
	BAD_TABLE(0, org.openflow.protocol.interfaces.OFTableModFailedCode.BAD_TABLE),
	BAD_CONFIG(1, org.openflow.protocol.interfaces.OFTableModFailedCode.BAD_CONFIG),
	EPERM(2, org.openflow.protocol.interfaces.OFTableModFailedCode.EPERM);
  
	private static Map<Short, OFTableModFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFTableModFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFTableModFailedCode, OFTableModFailedCode> __compatMappingReverse;
    
	private static OFTableModFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFTableModFailedCode cvalue, OFTableModFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFTableModFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFTableModFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFTableModFailedCode, OFTableModFailedCode>();
		if (__array == null) __array = new OFTableModFailedCode[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFTableModFailedCode first() {
		return __array[0];
	}
  
	public static OFTableModFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFTableModFailedCode(int value, org.openflow.protocol.interfaces.OFTableModFailedCode cvalue) {
		this.value = (short)value;
		OFTableModFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFTableModFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFTableModFailedCode to(OFTableModFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFTableModFailedCode from(org.openflow.protocol.interfaces.OFTableModFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}