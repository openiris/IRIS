
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFBadInstructionCode {
  
  OFPBIC_UNKNOWN_INST	(0),
	OFPBIC_UNSUP_INST	(1),
	OFPBIC_BAD_TABLE_ID	(2),
	OFPBIC_UNSUP_METADATA	(3),
	OFPBIC_UNSUP_METADATA_MASK	(4),
	OFPBIC_BAD_EXPERIMENTER	(5),
	OFPBIC_BAD_EXP_TYPE	(6),
	OFPBIC_BAD_LEN	(7),
	OFPBIC_EPERM	(8);
  
  private static Map<Short, OFBadInstructionCode> __index;
  private static OFBadInstructionCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFBadInstructionCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFBadInstructionCode>();
  	if (__array == null) __array = new OFBadInstructionCode[9];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFBadInstructionCode first() {
  	return __array[0];
  }
  
  public static OFBadInstructionCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFBadInstructionCode(int value) {
    this.value = (short)value;
    OFBadInstructionCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFBadInstructionCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}