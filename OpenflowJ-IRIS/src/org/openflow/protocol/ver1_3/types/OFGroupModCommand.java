
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFGroupModCommand {
  
  OFPGC_ADD	(0),
	OFPGC_MODIFY	(1),
	OFPGC_DELETE	(2);
  
  private static Map<Short, OFGroupModCommand> __index;
  private static OFGroupModCommand __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFGroupModCommand obj) {
  	if (__index == null) __index = new HashMap<Short, OFGroupModCommand>();
  	if (__array == null) __array = new OFGroupModCommand[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFGroupModCommand first() {
  	return __array[0];
  }
  
  public static OFGroupModCommand last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFGroupModCommand(int value) {
    this.value = (short)value;
    OFGroupModCommand.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFGroupModCommand valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}