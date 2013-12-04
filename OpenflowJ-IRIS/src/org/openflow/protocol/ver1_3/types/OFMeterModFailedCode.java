
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFMeterModFailedCode {
  
  OFPMMFC_UNKNOWN	(0),
	OFPMMFC_METER_EXISTS	(1),
	OFPMMFC_INVALID_METER	(2),
	OFPMMFC_UNKNOWN_METER	(3),
	OFPMMFC_BAD_COMMAND	(4),
	OFPMMFC_BAD_FLAGS	(5),
	OFPMMFC_BAD_RATE	(6),
	OFPMMFC_BAD_BURST	(7),
	OFPMMFC_BAD_BAND	(8),
	OFPMMFC_BAD_BAND_VALUE	(9),
	OFPMMFC_OUT_OF_METERS	(10),
	OFPMMFC_OUT_OF_BANDS	(11);
  
  private static Map<Short, OFMeterModFailedCode> __index;
  private static OFMeterModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFMeterModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFMeterModFailedCode>();
  	if (__array == null) __array = new OFMeterModFailedCode[12];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFMeterModFailedCode first() {
  	return __array[0];
  }
  
  public static OFMeterModFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFMeterModFailedCode(int value) {
    this.value = (short)value;
    OFMeterModFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFMeterModFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}