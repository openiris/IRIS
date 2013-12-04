
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFTableModFailedCode {
  
  OFPTMFC_BAD_TABLE	(0),
	OFPTMFC_BAD_CONFIG	(1),
	OFPTMFC_EPERM	(2);
  
  private static Map<Short, OFTableModFailedCode> __index;
  private static OFTableModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFTableModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFTableModFailedCode>();
  	if (__array == null) __array = new OFTableModFailedCode[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFTableModFailedCode first() {
  	return __array[0];
  }
  
  public static OFTableModFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFTableModFailedCode(int value) {
    this.value = (short)value;
    OFTableModFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFTableModFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}