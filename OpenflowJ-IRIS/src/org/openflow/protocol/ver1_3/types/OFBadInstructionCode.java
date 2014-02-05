
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFBadInstructionCode {
  
	UNKNOWN_INST(0, org.openflow.protocol.interfaces.OFBadInstructionCode.UNKNOWN_INST),
	UNSUP_INST(1, org.openflow.protocol.interfaces.OFBadInstructionCode.UNSUP_INST),
	BAD_TABLE_ID(2, org.openflow.protocol.interfaces.OFBadInstructionCode.BAD_TABLE_ID),
	UNSUP_METADATA(3, org.openflow.protocol.interfaces.OFBadInstructionCode.UNSUP_METADATA),
	UNSUP_METADATA_MASK(4, org.openflow.protocol.interfaces.OFBadInstructionCode.UNSUP_METADATA_MASK),
	BAD_EXPERIMENTER(5, org.openflow.protocol.interfaces.OFBadInstructionCode.BAD_EXPERIMENTER),
	BAD_EXP_TYPE(6, org.openflow.protocol.interfaces.OFBadInstructionCode.BAD_EXP_TYPE),
	BAD_LEN(7, org.openflow.protocol.interfaces.OFBadInstructionCode.BAD_LEN),
	EPERM(8, org.openflow.protocol.interfaces.OFBadInstructionCode.EPERM);
  
	private static Map<Short, OFBadInstructionCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFBadInstructionCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFBadInstructionCode, OFBadInstructionCode> __compatMappingReverse;
    
	private static OFBadInstructionCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFBadInstructionCode cvalue, OFBadInstructionCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFBadInstructionCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFBadInstructionCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFBadInstructionCode, OFBadInstructionCode>();
		if (__array == null) __array = new OFBadInstructionCode[9];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFBadInstructionCode first() {
		return __array[0];
	}
  
	public static OFBadInstructionCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFBadInstructionCode(int value, org.openflow.protocol.interfaces.OFBadInstructionCode cvalue) {
		this.value = (short)value;
		OFBadInstructionCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFBadInstructionCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFBadInstructionCode to(OFBadInstructionCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFBadInstructionCode from(org.openflow.protocol.interfaces.OFBadInstructionCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}