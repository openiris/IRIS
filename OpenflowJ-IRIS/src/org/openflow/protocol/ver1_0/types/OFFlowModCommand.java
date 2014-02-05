
package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFFlowModCommand {
  
	ADD(0, org.openflow.protocol.interfaces.OFFlowModCommand.ADD),
	MODIFY(1, org.openflow.protocol.interfaces.OFFlowModCommand.MODIFY),
	MODIFY_STRICT(2, org.openflow.protocol.interfaces.OFFlowModCommand.MODIFY_STRICT),
	DELETE(3, org.openflow.protocol.interfaces.OFFlowModCommand.DELETE),
	DELETE_STRICT(4, org.openflow.protocol.interfaces.OFFlowModCommand.DELETE_STRICT);
  
	private static Map<Short, OFFlowModCommand> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFFlowModCommand> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFFlowModCommand, OFFlowModCommand> __compatMappingReverse;
    
	private static OFFlowModCommand __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFFlowModCommand cvalue, OFFlowModCommand obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFFlowModCommand>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFFlowModCommand>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFFlowModCommand, OFFlowModCommand>();
		if (__array == null) __array = new OFFlowModCommand[5];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFFlowModCommand first() {
		return __array[0];
	}
  
	public static OFFlowModCommand last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFFlowModCommand(int value, org.openflow.protocol.interfaces.OFFlowModCommand cvalue) {
		this.value = (short)value;
		OFFlowModCommand.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFFlowModCommand valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFFlowModCommand to(OFFlowModCommand i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFFlowModCommand from(org.openflow.protocol.interfaces.OFFlowModCommand c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}