
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFFlowModCommand {
  
  OFPFC_ADD	(0),
	OFPFC_MODIFY	(1),
	OFPFC_MODIFY_STRICT	(2),
	OFPFC_DELETE	(3),
	OFPFC_DELETE_STRICT	(4);
  
  private static Map<Byte, OFFlowModCommand> __index;
  private static OFFlowModCommand __array[];
  private static int __array_index = 0;
  
  private byte value;
  
  private static void addMapping(int value, OFFlowModCommand obj) {
  	if (__index == null) __index = new HashMap<Byte, OFFlowModCommand>();
  	if (__array == null) __array = new OFFlowModCommand[5];
  	__index.put((byte)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFFlowModCommand first() {
  	return __array[0];
  }
  
  public static OFFlowModCommand last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFFlowModCommand(int value) {
    this.value = (byte)value;
    OFFlowModCommand.addMapping(value, this);
  }
  
  public byte getValue() {
    return this.value;
  }
  
  public static OFFlowModCommand valueOf(byte value) {
  	return __index.get(value);
  }
  
  public static byte readFrom(ByteBuffer data) {
  	return data.get();
  }
}