
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFPacketInReason {
  
  OFPR_NO_MATCH	(0),
	OFPR_ACTION	(1);
  
  private static Map<Byte, OFPacketInReason> __index;
  private static OFPacketInReason __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFPacketInReason obj) {
  	if (__index == null) __index = new HashMap<Byte, OFPacketInReason>();
  	if (__array == null) __array = new OFPacketInReason[2];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFPacketInReason first() {
  	return __array[0];
  }
  
  public static OFPacketInReason last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFPacketInReason(int value) {
    this.value = (byte)value;
    OFPacketInReason.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFPacketInReason valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}