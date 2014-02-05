
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFMeterModCommand {
  
	ADD(0, org.openflow.protocol.interfaces.OFMeterModCommand.ADD),
	MODIFY(1, org.openflow.protocol.interfaces.OFMeterModCommand.MODIFY),
	DELETE(2, org.openflow.protocol.interfaces.OFMeterModCommand.DELETE);
  
	private static Map<Short, OFMeterModCommand> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFMeterModCommand> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFMeterModCommand, OFMeterModCommand> __compatMappingReverse;
    
	private static OFMeterModCommand __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFMeterModCommand cvalue, OFMeterModCommand obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFMeterModCommand>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFMeterModCommand>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMeterModCommand, OFMeterModCommand>();
		if (__array == null) __array = new OFMeterModCommand[3];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFMeterModCommand first() {
		return __array[0];
	}
  
	public static OFMeterModCommand last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFMeterModCommand(int value, org.openflow.protocol.interfaces.OFMeterModCommand cvalue) {
		this.value = (short)value;
		OFMeterModCommand.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFMeterModCommand valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFMeterModCommand to(OFMeterModCommand i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFMeterModCommand from(org.openflow.protocol.interfaces.OFMeterModCommand c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}