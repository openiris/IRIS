
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFFlowRemovedReason {
  
  OFPRR_IDLE_TIMEOUT	(0),
	OFPRR_HARD_TIMEOUT	(1),
	OFPRR_DELETE	(2);
  
  private static Map<Byte, OFFlowRemovedReason> __index;
  private static OFFlowRemovedReason __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFFlowRemovedReason obj) {
  	if (__index == null) __index = new HashMap<Byte, OFFlowRemovedReason>();
  	if (__array == null) __array = new OFFlowRemovedReason[3];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFFlowRemovedReason first() {
  	return __array[0];
  }
  
  public static OFFlowRemovedReason last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFFlowRemovedReason(int value) {
    this.value = (byte)value;
    OFFlowRemovedReason.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFFlowRemovedReason valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}