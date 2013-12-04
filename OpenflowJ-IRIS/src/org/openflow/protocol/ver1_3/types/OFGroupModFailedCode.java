
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFGroupModFailedCode {
  
  OFPGMFC_GROUP_EXISTS	(0),
	OFPGMFC_INVALID_GROUP	(1),
	OFPGMFC_WEIGHT_UNSUPPORTED	(2),
	OFPGMFC_OUT_OF_GROUPS	(3),
	OFPGMFC_OUT_OF_BUCKETS	(4),
	OFPGMFC_CHAINING_UNSUPPORTED	(5),
	OFPGMFC_WATCH_UNSUPPORTED	(6),
	OFPGMFC_LOOP	(7),
	OFPGMFC_UNKNOWN_GROUP	(8),
	OFPGMFC_CHAINED_GROUP	(9),
	OFPGMFC_BAD_TYPE	(10),
	OFPGMFC_BAD_COMMAND	(11),
	OFPGMFC_BAD_BUCKET	(12),
	OFPGMFC_BAD_WATCH	(13),
	OFPGMFC_EPERM	(14);
  
  private static Map<Short, OFGroupModFailedCode> __index;
  private static OFGroupModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFGroupModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFGroupModFailedCode>();
  	if (__array == null) __array = new OFGroupModFailedCode[15];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFGroupModFailedCode first() {
  	return __array[0];
  }
  
  public static OFGroupModFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFGroupModFailedCode(int value) {
    this.value = (short)value;
    OFGroupModFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFGroupModFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}