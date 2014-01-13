
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFGroupModCommand {
  
	ADD(0, org.openflow.protocol.interfaces.OFGroupModCommand.ADD),
	MODIFY(1, org.openflow.protocol.interfaces.OFGroupModCommand.MODIFY),
	DELETE(2, org.openflow.protocol.interfaces.OFGroupModCommand.DELETE);
  
	private static Map<Short, OFGroupModCommand> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFGroupModCommand> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFGroupModCommand, OFGroupModCommand> __compatMappingReverse;
    
	private static OFGroupModCommand __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFGroupModCommand cvalue, OFGroupModCommand obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFGroupModCommand>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFGroupModCommand>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFGroupModCommand, OFGroupModCommand>();
		if (__array == null) __array = new OFGroupModCommand[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFGroupModCommand first() {
		return __array[0];
	}
  
	public static OFGroupModCommand last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFGroupModCommand(int value, org.openflow.protocol.interfaces.OFGroupModCommand cvalue) {
		this.value = (short)value;
		OFGroupModCommand.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFGroupModCommand valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFGroupModCommand to(OFGroupModCommand i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFGroupModCommand from(org.openflow.protocol.interfaces.OFGroupModCommand c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}