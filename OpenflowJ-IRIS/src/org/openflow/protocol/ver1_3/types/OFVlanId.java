
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFVlanId {
  
  OFPVID_NONE	(0),
	OFPVID_PRESENT	(0x1000);
  
  private static Map<Short, OFVlanId> __index;
  private static OFVlanId __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFVlanId obj) {
  	if (__index == null) __index = new HashMap<Short, OFVlanId>();
  	if (__array == null) __array = new OFVlanId[2];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFVlanId first() {
  	return __array[0];
  }
  
  public static OFVlanId last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFVlanId(int value) {
    this.value = (short)value;
    OFVlanId.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFVlanId valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}