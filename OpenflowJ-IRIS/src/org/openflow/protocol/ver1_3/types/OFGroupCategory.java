
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFGroupCategory {
  
  OFPGT_ALL	(0),
	OFPGT_SELECT	(1),
	OFPGT_INDIRECT	(2),
	OFPGT_FF	(3);
  
  private static Map<Byte, OFGroupCategory> __index;
  private static OFGroupCategory __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFGroupCategory obj) {
  	if (__index == null) __index = new HashMap<Byte, OFGroupCategory>();
  	if (__array == null) __array = new OFGroupCategory[4];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFGroupCategory first() {
  	return __array[0];
  }
  
  public static OFGroupCategory last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFGroupCategory(int value) {
    this.value = (byte)value;
    OFGroupCategory.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFGroupCategory valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}