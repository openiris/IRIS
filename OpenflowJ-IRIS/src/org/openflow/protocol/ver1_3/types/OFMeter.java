
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFMeter {
  
  OFPM_MAX	(0xffff0000),
	OFPM_SLOWPATH	(0xfffffffd),
	OFPM_CONTROLLER	(0xfffffffe),
	OFPM_ALL	(0xffffffff);
  
  private static Map<Integer, OFMeter> __index;
  private static OFMeter __array[];
  private static int __array_index = 0;
  
  private int value;
  
  private static void addMapping(int value, OFMeter obj) {
  	if (__index == null) __index = new HashMap<Integer, OFMeter>();
  	if (__array == null) __array = new OFMeter[4];
  	__index.put((int)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFMeter first() {
  	return __array[0];
  }
  
  public static OFMeter last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFMeter(int value) {
    this.value = (int)value;
    OFMeter.addMapping(value, this);
  }
  
  public int getValue() {
    return this.value;
  }
  
  public static OFMeter valueOf(int value) {
  	return __index.get(value);
  }
  
  public static int readFrom(ByteBuffer data) {
  	return data.getInt();
  }
}