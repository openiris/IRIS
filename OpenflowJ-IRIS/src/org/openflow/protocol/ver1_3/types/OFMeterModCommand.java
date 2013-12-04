
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFMeterModCommand {
  
  OFPMC_ADD	(0),
	OFPMC_MODIFY	(1),
	OFPMC_DELETE	(2);
  
  private static Map<Short, OFMeterModCommand> __index;
  private static OFMeterModCommand __array[];
  private static int __array_index = 0;
  
  private short value;
  
  private static void addMapping(int value, OFMeterModCommand obj) {
  	if (__index == null) __index = new HashMap<Short, OFMeterModCommand>();
  	if (__array == null) __array = new OFMeterModCommand[3];
  	__index.put((short)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFMeterModCommand first() {
  	return __array[0];
  }
  
  public static OFMeterModCommand last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFMeterModCommand(int value) {
    this.value = (short)value;
    OFMeterModCommand.addMapping(value, this);
  }
  
  public short getValue() {
    return this.value;
  }
  
  public static OFMeterModCommand valueOf(short value) {
  	return __index.get(value);
  }
  
  public static short readFrom(ByteBuffer data) {
  	return data.getShort();
  }
}