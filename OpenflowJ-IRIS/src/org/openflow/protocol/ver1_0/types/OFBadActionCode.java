
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFBadActionCode {
  
  OFPBAC_BAD_TYPE	(0),
	OFPBAC_BAD_LEN	(1),
	OFPBAC_BAD_VENDOR	(2),
	OFPBAC_BAD_VENDOR_TYPE	(3),
	OFPBAC_BAD_OUT_PORT	(4),
	OFPBAC_BAD_ARGUMENT	(5),
	OFPBAC_EPERM	(6),
	OFPBAC_TOO_MANY	(7),
	OFPBAC_BAD_QUEUE	(8);
  
  private static Map<Short, OFBadActionCode> __index;
  private static OFBadActionCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFBadActionCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFBadActionCode>();
  	if (__array == null) __array = new OFBadActionCode[9];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFBadActionCode first() {
  	return __array[0];
  }
  
  public static OFBadActionCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFBadActionCode(int value) {
    this.value = (short)value;
    OFBadActionCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFBadActionCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}