
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFBadRequestCode {
  
  OFPBRC_BAD_VERSION	(0),
	OFPBRC_BAD_TYPE	(1),
	OFPBRC_BAD_MULTIPART	(2),
	OFPBRC_BAD_EXPERIMENTER	(3),
	OFPBRC_BAD_EXP_TYPE	(4),
	OFPBRC_EPERM	(5),
	OFPBRC_BAD_LEN	(6),
	OFPBRC_BUFFER_EMPTY	(7),
	OFPBRC_BUFFER_UNKNOWN	(8),
	OFPBRC_BAD_TABLE_ID	(9),
	OFPBRC_IS_SLAVE	(10),
	OFPBRC_BAD_PORT	(11),
	OFPBRC_BAD_PACKET	(12),
	OFPBRC_MULTIPART_BUFFER_OVERFLOW	(13);
  
  private static Map<Short, OFBadRequestCode> __index;
  private static OFBadRequestCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFBadRequestCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFBadRequestCode>();
  	if (__array == null) __array = new OFBadRequestCode[14];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFBadRequestCode first() {
  	return __array[0];
  }
  
  public static OFBadRequestCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFBadRequestCode(int value) {
    this.value = (short)value;
    OFBadRequestCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFBadRequestCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}