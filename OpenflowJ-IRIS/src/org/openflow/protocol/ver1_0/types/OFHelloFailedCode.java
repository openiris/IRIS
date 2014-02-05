
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFHelloFailedCode {
  
	INCOMPATIBLE(0, org.openflow.protocol.interfaces.OFHelloFailedCode.INCOMPATIBLE),
	EPERM(1, org.openflow.protocol.interfaces.OFHelloFailedCode.EPERM);
  
	private static Map<Short, OFHelloFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFHelloFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFHelloFailedCode, OFHelloFailedCode> __compatMappingReverse;
    
	private static OFHelloFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFHelloFailedCode cvalue, OFHelloFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFHelloFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFHelloFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFHelloFailedCode, OFHelloFailedCode>();
		if (__array == null) __array = new OFHelloFailedCode[2];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFHelloFailedCode first() {
		return __array[0];
	}
  
	public static OFHelloFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFHelloFailedCode(int value, org.openflow.protocol.interfaces.OFHelloFailedCode cvalue) {
		this.value = (short)value;
		OFHelloFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFHelloFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFHelloFailedCode to(OFHelloFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFHelloFailedCode from(org.openflow.protocol.interfaces.OFHelloFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}