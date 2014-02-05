
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFBadMatchCode {
  
	BAD_TYPE(0, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_TYPE),
	BAD_LEN(1, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_LEN),
	BAD_TAG(2, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_TAG),
	BAD_DL_ADDR_MASK(3, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_DL_ADDR_MASK),
	BAD_NW_ADDR_MASK(4, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_NW_ADDR_MASK),
	BAD_WILDCARDS(5, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_WILDCARDS),
	BAD_FIELD(6, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_FIELD),
	BAD_VALUE(7, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_VALUE),
	BAD_MASK(8, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_MASK),
	BAD_PREREQ(9, org.openflow.protocol.interfaces.OFBadMatchCode.BAD_PREREQ),
	DUP_FIELD(10, org.openflow.protocol.interfaces.OFBadMatchCode.DUP_FIELD),
	EPERM(11, org.openflow.protocol.interfaces.OFBadMatchCode.EPERM);
  
	private static Map<Short, OFBadMatchCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFBadMatchCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFBadMatchCode, OFBadMatchCode> __compatMappingReverse;
    
	private static OFBadMatchCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFBadMatchCode cvalue, OFBadMatchCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFBadMatchCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFBadMatchCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFBadMatchCode, OFBadMatchCode>();
		if (__array == null) __array = new OFBadMatchCode[12];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFBadMatchCode first() {
		return __array[0];
	}
  
	public static OFBadMatchCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFBadMatchCode(int value, org.openflow.protocol.interfaces.OFBadMatchCode cvalue) {
		this.value = (short)value;
		OFBadMatchCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFBadMatchCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFBadMatchCode to(OFBadMatchCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFBadMatchCode from(org.openflow.protocol.interfaces.OFBadMatchCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}