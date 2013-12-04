
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFFlowModFailedCode {
  
  OFPFMFC_UNKNOWN	(0),
	OFPFMFC_TABLE_FULL	(1),
	OFPFMFC_BAD_TABLE_ID	(2),
	OFPFMFC_OVERLAP	(3),
	OFPFMFC_EPERM	(4),
	OFPFMFC_BAD_TIMEOUT	(5),
	OFPFMFC_BAD_COMMAND	(6),
	OFPFMFC_BAD_FLAGS	(7);
  
  private static Map<Short, OFFlowModFailedCode> __index;
  private static OFFlowModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFFlowModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFFlowModFailedCode>();
  	if (__array == null) __array = new OFFlowModFailedCode[8];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFFlowModFailedCode first() {
  	return __array[0];
  }
  
  public static OFFlowModFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFFlowModFailedCode(int value) {
    this.value = (short)value;
    OFFlowModFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFFlowModFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}