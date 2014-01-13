
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFQueueOpFailedCode {
  
	BAD_PORT(0, org.openflow.protocol.interfaces.OFQueueOpFailedCode.BAD_PORT),
	BAD_QUEUE(1, org.openflow.protocol.interfaces.OFQueueOpFailedCode.BAD_QUEUE),
	EPERM(2, org.openflow.protocol.interfaces.OFQueueOpFailedCode.EPERM);
  
	private static Map<Short, OFQueueOpFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFQueueOpFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFQueueOpFailedCode, OFQueueOpFailedCode> __compatMappingReverse;
    
	private static OFQueueOpFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFQueueOpFailedCode cvalue, OFQueueOpFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFQueueOpFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFQueueOpFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFQueueOpFailedCode, OFQueueOpFailedCode>();
		if (__array == null) __array = new OFQueueOpFailedCode[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFQueueOpFailedCode first() {
		return __array[0];
	}
  
	public static OFQueueOpFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFQueueOpFailedCode(int value, org.openflow.protocol.interfaces.OFQueueOpFailedCode cvalue) {
		this.value = (short)value;
		OFQueueOpFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFQueueOpFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFQueueOpFailedCode to(OFQueueOpFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFQueueOpFailedCode from(org.openflow.protocol.interfaces.OFQueueOpFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}