
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFFlowModFailedCode {
  
  OFPFMFC_ALL_TABLES_FULL	(0),
	OFPFMFC_OVERLAP	(1),
	OFPFMFC_EPERM	(2),
	OFPFMFC_BAD_EMERG_TIMEOUT	(3),
	OFPFMFC_BAD_COMMAND	(4),
	OFPFMFC_UNSUPPORTED	(5);
  
  private static Map<Short, OFFlowModFailedCode> __index;
  private static OFFlowModFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFFlowModFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFFlowModFailedCode>();
  	if (__array == null) __array = new OFFlowModFailedCode[6];
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