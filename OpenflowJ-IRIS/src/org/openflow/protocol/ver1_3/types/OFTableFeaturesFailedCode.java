
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFTableFeaturesFailedCode {
  
	BAD_TABLE(0, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.BAD_TABLE),
	BAD_METADATA(1, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.BAD_METADATA),
	BAD_TYPE(2, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.BAD_TYPE),
	BAD_LEN(3, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.BAD_LEN),
	BAD_ARGUMENT(4, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.BAD_ARGUMENT),
	EPERM(5, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode.EPERM);
  
	private static Map<Short, OFTableFeaturesFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFTableFeaturesFailedCode, OFTableFeaturesFailedCode> __compatMappingReverse;
    
	private static OFTableFeaturesFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode cvalue, OFTableFeaturesFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFTableFeaturesFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFTableFeaturesFailedCode, OFTableFeaturesFailedCode>();
		if (__array == null) __array = new OFTableFeaturesFailedCode[6];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFTableFeaturesFailedCode first() {
		return __array[0];
	}
  
	public static OFTableFeaturesFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFTableFeaturesFailedCode(int value, org.openflow.protocol.interfaces.OFTableFeaturesFailedCode cvalue) {
		this.value = (short)value;
		OFTableFeaturesFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFTableFeaturesFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFTableFeaturesFailedCode to(OFTableFeaturesFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFTableFeaturesFailedCode from(org.openflow.protocol.interfaces.OFTableFeaturesFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}