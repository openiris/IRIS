
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFErrorCode {
  
	HELLO_FAILED(0, org.openflow.protocol.interfaces.OFErrorCode.HELLO_FAILED),
	BAD_REQUEST(1, org.openflow.protocol.interfaces.OFErrorCode.BAD_REQUEST),
	BAD_ACTION(2, org.openflow.protocol.interfaces.OFErrorCode.BAD_ACTION),
	BAD_INSTRUCTION(3, org.openflow.protocol.interfaces.OFErrorCode.BAD_INSTRUCTION),
	BAD_MATCH(4, org.openflow.protocol.interfaces.OFErrorCode.BAD_MATCH),
	FLOW_MOD_FAILED(5, org.openflow.protocol.interfaces.OFErrorCode.FLOW_MOD_FAILED),
	GROUP_MOD_FAILED(6, org.openflow.protocol.interfaces.OFErrorCode.GROUP_MOD_FAILED),
	PORT_MOD_FAILED(7, org.openflow.protocol.interfaces.OFErrorCode.PORT_MOD_FAILED),
	TABLE_MOD_FAILED(8, org.openflow.protocol.interfaces.OFErrorCode.TABLE_MOD_FAILED),
	QUEUE_OP_FAILED(9, org.openflow.protocol.interfaces.OFErrorCode.QUEUE_OP_FAILED),
	SWITCH_CONFIG_FAILED(10, org.openflow.protocol.interfaces.OFErrorCode.SWITCH_CONFIG_FAILED),
	ROLE_REQUEST_FAILED(11, org.openflow.protocol.interfaces.OFErrorCode.ROLE_REQUEST_FAILED),
	METER_MOD_FAILED(12, org.openflow.protocol.interfaces.OFErrorCode.METER_MOD_FAILED),
	TABLE_FEATURES_FAILED(13, org.openflow.protocol.interfaces.OFErrorCode.TABLE_FEATURES_FAILED),
	EXPERIMENTER(0xffff, org.openflow.protocol.interfaces.OFErrorCode.EXPERIMENTER);
  
	private static Map<Short, OFErrorCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFErrorCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFErrorCode, OFErrorCode> __compatMappingReverse;
    
	private static OFErrorCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFErrorCode cvalue, OFErrorCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFErrorCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFErrorCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFErrorCode, OFErrorCode>();
		if (__array == null) __array = new OFErrorCode[15];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFErrorCode first() {
		return __array[0];
	}
  
	public static OFErrorCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFErrorCode(int value, org.openflow.protocol.interfaces.OFErrorCode cvalue) {
		this.value = (short)value;
		OFErrorCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFErrorCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFErrorCode to(OFErrorCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFErrorCode from(org.openflow.protocol.interfaces.OFErrorCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}