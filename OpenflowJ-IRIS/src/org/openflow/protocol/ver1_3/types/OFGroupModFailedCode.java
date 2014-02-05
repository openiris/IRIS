
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFGroupModFailedCode {
  
	GROUP_EXISTS(0, org.openflow.protocol.interfaces.OFGroupModFailedCode.GROUP_EXISTS),
	INVALID_GROUP(1, org.openflow.protocol.interfaces.OFGroupModFailedCode.INVALID_GROUP),
	WEIGHT_UNSUPPORTED(2, org.openflow.protocol.interfaces.OFGroupModFailedCode.WEIGHT_UNSUPPORTED),
	OUT_OF_GROUPS(3, org.openflow.protocol.interfaces.OFGroupModFailedCode.OUT_OF_GROUPS),
	OUT_OF_BUCKETS(4, org.openflow.protocol.interfaces.OFGroupModFailedCode.OUT_OF_BUCKETS),
	CHAINING_UNSUPPORTED(5, org.openflow.protocol.interfaces.OFGroupModFailedCode.CHAINING_UNSUPPORTED),
	WATCH_UNSUPPORTED(6, org.openflow.protocol.interfaces.OFGroupModFailedCode.WATCH_UNSUPPORTED),
	LOOP(7, org.openflow.protocol.interfaces.OFGroupModFailedCode.LOOP),
	UNKNOWN_GROUP(8, org.openflow.protocol.interfaces.OFGroupModFailedCode.UNKNOWN_GROUP),
	CHAINED_GROUP(9, org.openflow.protocol.interfaces.OFGroupModFailedCode.CHAINED_GROUP),
	BAD_TYPE(10, org.openflow.protocol.interfaces.OFGroupModFailedCode.BAD_TYPE),
	BAD_COMMAND(11, org.openflow.protocol.interfaces.OFGroupModFailedCode.BAD_COMMAND),
	BAD_BUCKET(12, org.openflow.protocol.interfaces.OFGroupModFailedCode.BAD_BUCKET),
	BAD_WATCH(13, org.openflow.protocol.interfaces.OFGroupModFailedCode.BAD_WATCH),
	EPERM(14, org.openflow.protocol.interfaces.OFGroupModFailedCode.EPERM);
  
	private static Map<Short, OFGroupModFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFGroupModFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFGroupModFailedCode, OFGroupModFailedCode> __compatMappingReverse;
    
	private static OFGroupModFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFGroupModFailedCode cvalue, OFGroupModFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFGroupModFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFGroupModFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFGroupModFailedCode, OFGroupModFailedCode>();
		if (__array == null) __array = new OFGroupModFailedCode[15];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFGroupModFailedCode first() {
		return __array[0];
	}
  
	public static OFGroupModFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFGroupModFailedCode(int value, org.openflow.protocol.interfaces.OFGroupModFailedCode cvalue) {
		this.value = (short)value;
		OFGroupModFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFGroupModFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFGroupModFailedCode to(OFGroupModFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFGroupModFailedCode from(org.openflow.protocol.interfaces.OFGroupModFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}