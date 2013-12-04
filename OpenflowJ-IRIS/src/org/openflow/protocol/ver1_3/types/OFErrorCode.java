
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFErrorCode {
  
  OFPET_HELLO_FAILED	(0),
	OFPET_BAD_REQUEST	(1),
	OFPET_BAD_ACTION	(2),
	OFPET_BAD_INSTRUCTION	(3),
	OFPET_BAD_MATCH	(4),
	OFPET_FLOW_MOD_FAILED	(5),
	OFPET_GROUP_MOD_FAILED	(6),
	OFPET_PORT_MOD_FAILED	(7),
	OFPET_TABLE_MOD_FAILED	(8),
	OFPET_QUEUE_OP_FAILED	(9),
	OFPET_SWITCH_CONFIG_FAILED	(10),
	OFPET_ROLE_REQUEST_FAILED	(11),
	OFPET_METER_MOD_FAILED	(12),
	OFPET_TABLE_FEATURES_FAILED	(13),
	OFPET_EXPERIMENTER	(0xffff);
  
  private static Map<Short, OFErrorCode> __index;
  private static OFErrorCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFErrorCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFErrorCode>();
  	if (__array == null) __array = new OFErrorCode[15];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFErrorCode first() {
  	return __array[0];
  }
  
  public static OFErrorCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFErrorCode(int value) {
    this.value = (short)value;
    OFErrorCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFErrorCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}