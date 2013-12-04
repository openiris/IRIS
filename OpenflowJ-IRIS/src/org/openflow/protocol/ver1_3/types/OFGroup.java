
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFGroup {
  
  OFPG_MAX	(0xffffff00),
	OFPG_ALL	(0xfffffffc),
	OFPG_ANY	(0xffffffff);
  
  private static Map<Integer, OFGroup> __index;
  private static OFGroup __array[];
  private static int __array_index = 0;
  
  private int value;
  
  private static void addMapping(int value, OFGroup obj) {
  	if (__index == null) __index = new HashMap<Integer, OFGroup>();
  	if (__array == null) __array = new OFGroup[3];
  	__index.put((int)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFGroup first() {
  	return __array[0];
  }
  
  public static OFGroup last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFGroup(int value) {
    this.value = (int)value;
    OFGroup.addMapping(value, this);
  }
  
  public int getValue() {
    return this.value;
  }
  
  public static OFGroup valueOf(int value) {
  	return __index.get(value);
  }
  
  public static int readFrom(ByteBuffer data) {
  	return data.getInt();
  }
}