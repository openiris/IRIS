
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFPortModFailedCode {
  
  OFPPMFC_BAD_PORT	(0),
	OFPPMFC_BAD_HW_ADDR	(1),
	OFPPMFC_BAD_CONFIG	(2),
	OFPPMFC_BAD_ADVERTISE	(3),
	OFPPMFC_EPERM	(4);
  
  private static Map<Short, OFPortModFailedCode> __index;
  private static OFPortModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFPortModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFPortModFailedCode>();
  	if (__array == null) __array = new OFPortModFailedCode[5];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFPortModFailedCode first() {
  	return __array[0];
  }
  
  public static OFPortModFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFPortModFailedCode(int value) {
    this.value = (short)value;
    OFPortModFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFPortModFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}