
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFMeterModFailedCode {
  
	UNKNOWN(0, org.openflow.protocol.interfaces.OFMeterModFailedCode.UNKNOWN),
	METER_EXISTS(1, org.openflow.protocol.interfaces.OFMeterModFailedCode.METER_EXISTS),
	INVALID_METER(2, org.openflow.protocol.interfaces.OFMeterModFailedCode.INVALID_METER),
	UNKNOWN_METER(3, org.openflow.protocol.interfaces.OFMeterModFailedCode.UNKNOWN_METER),
	BAD_COMMAND(4, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_COMMAND),
	BAD_FLAGS(5, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_FLAGS),
	BAD_RATE(6, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_RATE),
	BAD_BURST(7, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_BURST),
	BAD_BAND(8, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_BAND),
	BAD_BAND_VALUE(9, org.openflow.protocol.interfaces.OFMeterModFailedCode.BAD_BAND_VALUE),
	OUT_OF_METERS(10, org.openflow.protocol.interfaces.OFMeterModFailedCode.OUT_OF_METERS),
	OUT_OF_BANDS(11, org.openflow.protocol.interfaces.OFMeterModFailedCode.OUT_OF_BANDS);
  
	private static Map<Short, OFMeterModFailedCode> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFMeterModFailedCode> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFMeterModFailedCode, OFMeterModFailedCode> __compatMappingReverse;
    
	private static OFMeterModFailedCode __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFMeterModFailedCode cvalue, OFMeterModFailedCode obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFMeterModFailedCode>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFMeterModFailedCode>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMeterModFailedCode, OFMeterModFailedCode>();
		if (__array == null) __array = new OFMeterModFailedCode[12];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFMeterModFailedCode first() {
		return __array[0];
	}
  
	public static OFMeterModFailedCode last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFMeterModFailedCode(int value, org.openflow.protocol.interfaces.OFMeterModFailedCode cvalue) {
		this.value = (short)value;
		OFMeterModFailedCode.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFMeterModFailedCode valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFMeterModFailedCode to(OFMeterModFailedCode i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFMeterModFailedCode from(org.openflow.protocol.interfaces.OFMeterModFailedCode c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}