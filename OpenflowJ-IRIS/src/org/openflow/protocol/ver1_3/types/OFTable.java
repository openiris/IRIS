
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFTable {
  
  OFPTT_MAX	(0xfe),
	OFPTT_ALL	(0xff);
  
  private static Map<Byte, OFTable> __index;
  private static OFTable __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFTable obj) {
  	if (__index == null) __index = new HashMap<Byte, OFTable>();
  	if (__array == null) __array = new OFTable[2];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFTable first() {
  	return __array[0];
  }
  
  public static OFTable last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFTable(int value) {
    this.value = (byte)value;
    OFTable.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFTable valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}