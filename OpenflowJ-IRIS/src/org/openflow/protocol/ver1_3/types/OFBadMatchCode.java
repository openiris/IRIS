
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFBadMatchCode {
  
  OFPBMC_BAD_TYPE	(0),
	OFPBMC_BAD_LEN	(1),
	OFPBMC_BAD_TAG	(2),
	OFPBMC_BAD_DL_ADDR_MASK	(3),
	OFPBMC_BAD_NW_ADDR_MASK	(4),
	OFPBMC_BAD_WILDCARDS	(5),
	OFPBMC_BAD_FIELD	(6),
	OFPBMC_BAD_VALUE	(7),
	OFPBMC_BAD_MASK	(8),
	OFPBMC_BAD_PREREQ	(9),
	OFPBMC_DUP_FIELD	(10),
	OFPBMC_EPERM	(11);
  
  private static Map<Short, OFBadMatchCode> __index;
  private static OFBadMatchCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFBadMatchCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFBadMatchCode>();
  	if (__array == null) __array = new OFBadMatchCode[12];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFBadMatchCode first() {
  	return __array[0];
  }
  
  public static OFBadMatchCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFBadMatchCode(int value) {
    this.value = (short)value;
    OFBadMatchCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFBadMatchCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}