
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFPortReason {
  
  OFPPR_ADD	(0),
	OFPPR_DELETE	(1),
	OFPPR_MODIFY	(2);
  
  private static Map<Byte, OFPortReason> __index;
  private static OFPortReason __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFPortReason obj) {
  	if (__index == null) __index = new HashMap<Byte, OFPortReason>();
  	if (__array == null) __array = new OFPortReason[3];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFPortReason first() {
  	return __array[0];
  }
  
  public static OFPortReason last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFPortReason(int value) {
    this.value = (byte)value;
    OFPortReason.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFPortReason valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}