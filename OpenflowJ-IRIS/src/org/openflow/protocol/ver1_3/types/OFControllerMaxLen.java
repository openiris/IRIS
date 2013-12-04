
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFControllerMaxLen {
  
  OFPCML_MAX	(0xffe5),
	OFPCML_NO_BUFFER	(0xffff);
  
  private static Map<Short, OFControllerMaxLen> __index;
  private static OFControllerMaxLen __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFControllerMaxLen obj) {
  	if (__index == null) __index = new HashMap<Short, OFControllerMaxLen>();
  	if (__array == null) __array = new OFControllerMaxLen[2];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFControllerMaxLen first() {
  	return __array[0];
  }
  
  public static OFControllerMaxLen last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFControllerMaxLen(int value) {
    this.value = (short)value;
    OFControllerMaxLen.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFControllerMaxLen valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}