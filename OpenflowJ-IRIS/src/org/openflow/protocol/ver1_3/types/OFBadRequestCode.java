
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFBadRequestCode {
  
	BAD_VERSION(0, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_VERSION),
	BAD_TYPE(1, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_TYPE),
	BAD_statistics(2, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_statistics),
	BAD_EXPERIMENTER(3, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_EXPERIMENTER),
	BAD_EXP_TYPE(4, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_EXP_TYPE),
	EPERM(5, org.openflow.protocol.interfaces.OFBadRequestCode.EPERM),
	BAD_LEN(6, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_LEN),
	BUFFER_EMPTY(7, org.openflow.protocol.interfaces.OFBadRequestCode.BUFFER_EMPTY),
	BUFFER_UNKNOWN(8, org.openflow.protocol.interfaces.OFBadRequestCode.BUFFER_UNKNOWN),
	BAD_TABLE_ID(9, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_TABLE_ID),
	IS_SLAVE(10, org.openflow.protocol.interfaces.OFBadRequestCode.IS_SLAVE),
	BAD_PORT(11, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_PORT),
	BAD_PACKET(12, org.openflow.protocol.interfaces.OFBadRequestCode.BAD_PACKET),
	statistics_BUFFER_OVERFLOW(13, org.openflow.protocol.interfaces.OFBadRequestCode.statistics_BUFFER_OVERFLOW);
  
	private static Map<Short, OFBadRequestCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFBadRequestCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFBadRequestCode, OFBadRequestCode> __compatMappingReverse;
    
	private static OFBadRequestCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFBadRequestCode cvalue, OFBadRequestCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFBadRequestCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFBadRequestCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFBadRequestCode, OFBadRequestCode>();
		if (__array == null) __array = new OFBadRequestCode[14];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFBadRequestCode first() {
		return __array[0];
	}
  
	public static OFBadRequestCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFBadRequestCode(int value, org.openflow.protocol.interfaces.OFBadRequestCode cvalue) {
		this.value = (short)value;
		OFBadRequestCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFBadRequestCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFBadRequestCode to(OFBadRequestCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFBadRequestCode from(org.openflow.protocol.interfaces.OFBadRequestCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}