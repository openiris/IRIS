
package $packagename;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum $typename {
  
	$enumdef;
  
	private static Map<$otype, $typename> __index;
	static Map<$otype, org.openflow.protocol.interfaces.$typename> __compatMapping;
    static Map<org.openflow.protocol.interfaces.$typename, $typename> __compatMappingReverse;
    
	private static $typename __array[];
	private static int __array_index = 0;
  
	private $type value;
  
	private static void addMapping(int value, org.openflow.protocol.interfaces.${typename} cvalue, $typename obj) {
		if (__index == null) __index = new ConcurrentHashMap<$otype, $typename>();
		if (__compatMapping == null) 
			__compatMapping = new ConcurrentHashMap<$otype, org.openflow.protocol.interfaces.$typename>();
		if (__compatMappingReverse == null) 
			__compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.$typename, $typename>();
		if (__array == null) __array = new $typename[$length];
		
		__index.put(($type)value, obj);
		__array[__array_index++] = obj;
		
		__compatMapping.put(($type)value, cvalue);
		__compatMappingReverse.put(cvalue, obj);
	}
  
	public static $typename first() {
		return __array[0];
	}
  
	public static $typename last() {
		return __array[__array_index - 1];
	}
  
	// constructor
	private $typename(int value, org.openflow.protocol.interfaces.${typename} cvalue) {
		this.value = ($type)value;
		$typename.addMapping(value, cvalue, this);
	}
  
	public $type getValue() {
		return this.value;
	}
  
	public static $typename valueOf($type value) {
		return __index.get(value);
	}
	
	/**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.$typename to($typename i) {
    	return __compatMapping.get(i.getValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public $typename from(org.openflow.protocol.interfaces.$typename c) {
    	return __compatMappingReverse.get(c);
    }
  
	public static $type readFrom(ByteBuffer data) {
		return data.get${bytegetname}();
	}
}