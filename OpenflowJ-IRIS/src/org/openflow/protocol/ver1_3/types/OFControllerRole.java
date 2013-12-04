
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum OFControllerRole {
  
  OFPCR_ROLE_NOCHANGE	(0),
	OFPCR_ROLE_EQUAL	(1),
	OFPCR_ROLE_MASTER	(2),
	OFPCR_ROLE_SLAVE	(3);
  
  private static Map<Integer, OFControllerRole> __index;
  private static OFControllerRole __array[];
  private static int __array_index = 0;
  
  private int value;
  
  private static void addMapping(int value, OFControllerRole obj) {
  	if (__index == null) __index = new HashMap<Integer, OFControllerRole>();
  	if (__array == null) __array = new OFControllerRole[4];
  	__index.put((int)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static OFControllerRole first() {
  	return __array[0];
  }
  
  public static OFControllerRole last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private OFControllerRole(int value) {
    this.value = (int)value;
    OFControllerRole.addMapping(value, this);
  }
  
  public int getValue() {
    return this.value;
  }
  
  public static OFControllerRole valueOf(int value) {
  	return __index.get(value);
  }
  
  public static int readFrom(ByteBuffer data) {
  	return data.getInt();
  }
}