
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFOxmClass {
  
	NXM_0(0, org.openflow.protocol.interfaces.OFOxmClass.NXM_0),
	NXM_1(1, org.openflow.protocol.interfaces.OFOxmClass.NXM_1),
	OPENFLOW_BASIC(0x8000, org.openflow.protocol.interfaces.OFOxmClass.OPENFLOW_BASIC),
	EXPERIMENTER(0xffff, org.openflow.protocol.interfaces.OFOxmClass.EXPERIMENTER);
  
	private static Map<Short, OFOxmClass> __index;
	static Map<Short, org.openflow.protocol.interfaces.OFOxmClass> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFOxmClass, OFOxmClass> __compatMappingReverse;
    
	private static OFOxmClass __array[];
	private static int __array_index = 0;
  
	private short value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFOxmClass cvalue, OFOxmClass obj) {
		if (__index == null) __index = new ConcurrentHashMap<Short, OFOxmClass>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFOxmClass>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFOxmClass, OFOxmClass>();
		if (__array == null) __array = new OFOxmClass[4];
		
		__index.put((short)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((short)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFOxmClass first() {
		return __array[0];
	}
  
	public static OFOxmClass last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFOxmClass(int value, org.openflow.protocol.interfaces.OFOxmClass cvalue) {
		this.value = (short)value;
		OFOxmClass.addMapping(value, cvalue, this);
	}
  
	public short getValue() {
		return this.value;
	}
  
	public static OFOxmClass valueOf(short value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFOxmClass to(OFOxmClass i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFOxmClass from(org.openflow.protocol.interfaces.OFOxmClass c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
}