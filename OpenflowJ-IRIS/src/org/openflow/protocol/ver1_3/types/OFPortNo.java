
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFPortNo {
  
  OFPP_MAX	(0xffffff00),
	OFPP_IN_PORT	(0xfffffff8),
	OFPP_TABLE	(0xfffffff9),
	OFPP_NORMAL	(0xfffffffa),
	OFPP_FLOOD	(0xfffffffb),
	OFPP_ALL	(0xfffffffc),
	OFPP_CONTROLLER	(0xfffffffd),
	OFPP_LOCAL	(0xfffffffe),
	OFPP_ANY	(0xffffffff);
  
  private static Map<Integer, OFPortNo> __index;
  private static OFPortNo __array[];
  private static int __array_index = 0;
  
  private int value;
  
  private static void addMapping(int value, OFPortNo obj) {
  	if (__index == null) __index = new HashMap<Integer, OFPortNo>();
  	if (__array == null) __array = new OFPortNo[9];
  	__index.put((int)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFPortNo first() {
  	return __array[0];
  }
  
  public static OFPortNo last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFPortNo(int value) {
    this.value = (int)value;
    OFPortNo.addMapping(value, this);
  }
  
  public int getValue() {
    return this.value;
  }
  
  public static OFPortNo valueOf(int value) {
  	return __index.get(value);
  }
  
  public static int readFrom(ByteBuffer data) {
  	return data.getInt();
  }
}