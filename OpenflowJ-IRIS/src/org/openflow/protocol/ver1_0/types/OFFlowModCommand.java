
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFFlowModCommand {
  
  OFPFC_ADD	(0),
	OFPFC_MODIFY	(1),
	OFPFC_MODIFY_STRICT	(2),
	OFPFC_DELETE	(3),
	OFPFC_DELETE_STRICT	(4);
  
  private static Map<Short, OFFlowModCommand> __index;
  private static OFFlowModCommand __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFFlowModCommand obj) {
  	if (__index == null) __index = new HashMap<Short, OFFlowModCommand>();
  	if (__array == null) __array = new OFFlowModCommand[5];
  	__index.put((short)value, obj);
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
    this.value = (short)value;
    OFFlowModCommand.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFFlowModCommand valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}