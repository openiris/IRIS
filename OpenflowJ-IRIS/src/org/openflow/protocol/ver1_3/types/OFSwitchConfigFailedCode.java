
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFSwitchConfigFailedCode {
  
  OFPSCFC_BAD_FLAGS	(0),
	OFPSCFC_BAD_LEN	(1),
	OFPSCFC_EPERM	(2);
  
  private static Map<Short, OFSwitchConfigFailedCode> __index;
  private static OFSwitchConfigFailedCode __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFSwitchConfigFailedCode obj) {
  	if (__index == null) __index = new HashMap<Short, OFSwitchConfigFailedCode>();
  	if (__array == null) __array = new OFSwitchConfigFailedCode[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFSwitchConfigFailedCode first() {
  	return __array[0];
  }
  
  public static OFSwitchConfigFailedCode last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFSwitchConfigFailedCode(int value) {
    this.value = (short)value;
    OFSwitchConfigFailedCode.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFSwitchConfigFailedCode valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}