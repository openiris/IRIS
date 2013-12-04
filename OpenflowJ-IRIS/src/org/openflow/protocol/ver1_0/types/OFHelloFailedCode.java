
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFHelloFailedCode {
  
  OFPHFC_INCOMPATIBLE	(0),
	OFPHFC_EPERM	(1);
  
  private static Map<Short, OFHelloFailedCode> __index;
  private static OFHelloFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFHelloFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFHelloFailedCode>();
  	if (__array == null) __array = new OFHelloFailedCode[2];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFHelloFailedCode first() {
  	return __array[0];
  }
  
  public static OFHelloFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFHelloFailedCode(int value) {
    this.value = (short)value;
    OFHelloFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFHelloFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}