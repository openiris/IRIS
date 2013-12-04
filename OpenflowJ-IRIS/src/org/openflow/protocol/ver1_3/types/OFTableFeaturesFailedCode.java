
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFTableFeaturesFailedCode {
  
  OFPTFFC_BAD_TABLE	(0),
	OFPTFFC_BAD_METADATA	(1),
	OFPTFFC_BAD_TYPE	(2),
	OFPTFFC_BAD_LEN	(3),
	OFPTFFC_BAD_ARGUMENT	(4),
	OFPTFFC_EPERM	(5);
  
  private static Map<Short, OFTableFeaturesFailedCode> __index;
  private static OFTableFeaturesFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFTableFeaturesFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFTableFeaturesFailedCode>();
  	if (__array == null) __array = new OFTableFeaturesFailedCode[6];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFTableFeaturesFailedCode first() {
  	return __array[0];
  }
  
  public static OFTableFeaturesFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFTableFeaturesFailedCode(int value) {
    this.value = (short)value;
    OFTableFeaturesFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFTableFeaturesFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}