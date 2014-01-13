
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFBadActionCode {
  
	BAD_TYPE(0, org.openflow.protocol.interfaces.OFBadActionCode.BAD_TYPE),
	BAD_LEN(1, org.openflow.protocol.interfaces.OFBadActionCode.BAD_LEN),
	BAD_VENDOR(2, org.openflow.protocol.interfaces.OFBadActionCode.BAD_VENDOR),
	BAD_VENDOR_TYPE(3, org.openflow.protocol.interfaces.OFBadActionCode.BAD_VENDOR_TYPE),
	BAD_OUT_PORT(4, org.openflow.protocol.interfaces.OFBadActionCode.BAD_OUT_PORT),
	BAD_ARGUMENT(5, org.openflow.protocol.interfaces.OFBadActionCode.BAD_ARGUMENT),
	EPERM(6, org.openflow.protocol.interfaces.OFBadActionCode.EPERM),
	TOO_MANY(7, org.openflow.protocol.interfaces.OFBadActionCode.TOO_MANY),
	BAD_QUEUE(8, org.openflow.protocol.interfaces.OFBadActionCode.BAD_QUEUE);
  
	private static Map<Short, OFBadActionCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFBadActionCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFBadActionCode, OFBadActionCode> __compatMappingReverse;
    
	private static OFBadActionCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFBadActionCode cvalue, OFBadActionCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFBadActionCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFBadActionCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFBadActionCode, OFBadActionCode>();
		if (__array == null) __array = new OFBadActionCode[9];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFBadActionCode first() {
		return __array[0];
	}
  
	public static OFBadActionCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFBadActionCode(int value, org.openflow.protocol.interfaces.OFBadActionCode cvalue) {
		this.value = (short)value;
		OFBadActionCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFBadActionCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFBadActionCode to(OFBadActionCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFBadActionCode from(org.openflow.protocol.interfaces.OFBadActionCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}