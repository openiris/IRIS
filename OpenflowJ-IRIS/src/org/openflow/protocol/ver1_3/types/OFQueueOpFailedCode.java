
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFQueueOpFailedCode {
  
  OFPQOFC_BAD_PORT	(0),
	OFPQOFC_BAD_QUEUE	(1),
	OFPQOFC_EPERM	(2);
  
  private static Map<Short, OFQueueOpFailedCode> __index;
  private static OFQueueOpFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFQueueOpFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFQueueOpFailedCode>();
  	if (__array == null) __array = new OFQueueOpFailedCode[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFQueueOpFailedCode first() {
  	return __array[0];
  }
  
  public static OFQueueOpFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFQueueOpFailedCode(int value) {
    this.value = (short)value;
    OFQueueOpFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFQueueOpFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}