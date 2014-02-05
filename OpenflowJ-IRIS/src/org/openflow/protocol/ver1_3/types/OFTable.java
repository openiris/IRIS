
package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OFTable {
  
	MAX(0xfe, org.openflow.protocol.interfaces.OFTable.MAX),
	ALL(0xff, org.openflow.protocol.interfaces.OFTable.ALL);
  
	private static Map<Byte, OFTable> __index;
	static Map<Byte, org.openflow.protocol.interfaces.OFTable> __compatMapping;
    static Map<org.openflow.protocol.interfaces.OFTable, OFTable> __compatMappingReverse;
    
	private static OFTable __array[];
	private static int __array_index = 0;
  
	private byte value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.OFTable cvalue, OFTable obj) {
		if (__index == null) __index = new ConcurrentHashMap<Byte, OFTable>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFTable>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFTable, OFTable>();
		if (__array == null) __array = new OFTable[2];
		
		__index.put((byte)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put((byte)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static OFTable first() {
		return __array[0];
	}
  
	public static OFTable last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private OFTable(int value, org.openflow.protocol.interfaces.OFTable cvalue) {
		this.value = (byte)value;
		OFTable.addMapping(value, cvalue, this);
	}
  
	public byte getValue() {
		return this.value;
	}
  
	public static OFTable valueOf(byte value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFTable to(OFTable i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFTable from(org.openflow.protocol.interfaces.OFTable c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static byte readFrom(ByteBuffer data) {
		return data.get();
	}
}