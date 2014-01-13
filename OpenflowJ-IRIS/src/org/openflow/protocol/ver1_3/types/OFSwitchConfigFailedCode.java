
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFSwitchConfigFailedCode {
  
	BAD_FLAGS(0, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode.BAD_FLAGS),
	BAD_LEN(1, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode.BAD_LEN),
	EPERM(2, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode.EPERM);
  
	private static Map<Short, OFSwitchConfigFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFSwitchConfigFailedCode, OFSwitchConfigFailedCode> __compatMappingReverse;
    
	private static OFSwitchConfigFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode cvalue, OFSwitchConfigFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFSwitchConfigFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFSwitchConfigFailedCode, OFSwitchConfigFailedCode>();
		if (__array == null) __array = new OFSwitchConfigFailedCode[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFSwitchConfigFailedCode first() {
		return __array[0];
	}
  
	public static OFSwitchConfigFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFSwitchConfigFailedCode(int value, org.openflow.protocol.interfaces.OFSwitchConfigFailedCode cvalue) {
		this.value = (short)value;
		OFSwitchConfigFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFSwitchConfigFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFSwitchConfigFailedCode to(OFSwitchConfigFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFSwitchConfigFailedCode from(org.openflow.protocol.interfaces.OFSwitchConfigFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}