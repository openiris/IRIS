
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFFlowModFailedCode {
  
	UNKNOWN(0, org.openflow.protocol.interfaces.OFFlowModFailedCode.UNKNOWN),
	TABLE_FULL(1, org.openflow.protocol.interfaces.OFFlowModFailedCode.TABLE_FULL),
	BAD_TABLE_ID(2, org.openflow.protocol.interfaces.OFFlowModFailedCode.BAD_TABLE_ID),
	OVERLAP(3, org.openflow.protocol.interfaces.OFFlowModFailedCode.OVERLAP),
	EPERM(4, org.openflow.protocol.interfaces.OFFlowModFailedCode.EPERM),
	BAD_TIMEOUT(5, org.openflow.protocol.interfaces.OFFlowModFailedCode.BAD_TIMEOUT),
	BAD_COMMAND(6, org.openflow.protocol.interfaces.OFFlowModFailedCode.BAD_COMMAND),
	BAD_FLAGS(7, org.openflow.protocol.interfaces.OFFlowModFailedCode.BAD_FLAGS);
  
	private static Map<Short, OFFlowModFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFFlowModFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFFlowModFailedCode, OFFlowModFailedCode> __compatMappingReverse;
    
	private static OFFlowModFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFFlowModFailedCode cvalue, OFFlowModFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFFlowModFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFFlowModFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFFlowModFailedCode, OFFlowModFailedCode>();
		if (__array == null) __array = new OFFlowModFailedCode[8];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFFlowModFailedCode first() {
		return __array[0];
	}
  
	public static OFFlowModFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFFlowModFailedCode(int value, org.openflow.protocol.interfaces.OFFlowModFailedCode cvalue) {
		this.value = (short)value;
		OFFlowModFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFFlowModFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFFlowModFailedCode to(OFFlowModFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFFlowModFailedCode from(org.openflow.protocol.interfaces.OFFlowModFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}