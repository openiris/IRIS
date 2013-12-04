
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFRoleRequestFailedCode {
  
  OFPRRFC_STALE	(0),
	OFPRRFC_UNSUP	(1),
	OFPRRFC_BAD_ROLE	(2);
  
  private static Map<Short, OFRoleRequestFailedCode> __index;
  private static OFRoleRequestFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFRoleRequestFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFRoleRequestFailedCode>();
  	if (__array == null) __array = new OFRoleRequestFailedCode[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFRoleRequestFailedCode first() {
  	return __array[0];
  }
  
  public static OFRoleRequestFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFRoleRequestFailedCode(int value) {
    this.value = (short)value;
    OFRoleRequestFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFRoleRequestFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}