
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFOxmClass {
  
  OFPXMC_NXM_0	(0),
	OFPXMC_NXM_1	(1),
	OFPXMC_OPENFLOW_BASIC	(0x8000),
	OFPXMC_EXPERIMENTER	(0xffff);
  
  private static Map<Short, OFOxmClass> __index;
  private static OFOxmClass __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFOxmClass obj) {
  	if (__index == null) __index = new HashMap<Short, OFOxmClass>();
  	if (__array == null) __array = new OFOxmClass[4];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFOxmClass first() {
  	return __array[0];
  }
  
  public static OFOxmClass last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFOxmClass(int value) {
    this.value = (short)value;
    OFOxmClass.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFOxmClass valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}