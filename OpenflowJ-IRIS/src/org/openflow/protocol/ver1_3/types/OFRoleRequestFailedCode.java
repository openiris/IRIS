
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFRoleRequestFailedCode {
  
	STALE(0, org.openflow.protocol.interfaces.OFRoleRequestFailedCode.STALE),
	UNSUP(1, org.openflow.protocol.interfaces.OFRoleRequestFailedCode.UNSUP),
	BAD_ROLE(2, org.openflow.protocol.interfaces.OFRoleRequestFailedCode.BAD_ROLE);
  
	private static Map<Short, OFRoleRequestFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFRoleRequestFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFRoleRequestFailedCode, OFRoleRequestFailedCode> __compatMappingReverse;
    
	private static OFRoleRequestFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFRoleRequestFailedCode cvalue, OFRoleRequestFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFRoleRequestFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFRoleRequestFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFRoleRequestFailedCode, OFRoleRequestFailedCode>();
		if (__array == null) __array = new OFRoleRequestFailedCode[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFRoleRequestFailedCode first() {
		return __array[0];
	}
  
	public static OFRoleRequestFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFRoleRequestFailedCode(int value, org.openflow.protocol.interfaces.OFRoleRequestFailedCode cvalue) {
		this.value = (short)value;
		OFRoleRequestFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFRoleRequestFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFRoleRequestFailedCode to(OFRoleRequestFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFRoleRequestFailedCode from(org.openflow.protocol.interfaces.OFRoleRequestFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}