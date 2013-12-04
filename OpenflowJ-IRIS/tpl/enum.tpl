
package $packagename;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;

public enum $typename {
  
  $enumdef;
  
  private static Map<$otype, $typename> __index;
  private static $typename __array[];
  private static int __array_index = 0;
  
  private $type value;
  
  private static void addMapping(int value, $typename obj) {
  	if (__index == null) __index = new HashMap<$otype, $typename>();
  	if (__array == null) __array = new $typename[$length];
  	__index.put(($type)value, obj);
  	__array[__array_index++] = obj;
  }
  
  public static $typename first() {
  	return __array[0];
  }
  
  public static $typename last() {
  	return __array[__array_index - 1];
  }
  
  // constructor
  private $typename(int value) {
    this.value = ($type)value;
    $typename.addMapping(value, this);
  }
  
  public $type getValue() {
    return this.value;
  }
  
  public static $typename valueOf($type value) {
  	return __index.get(value);
  }
  
  public static $type readFrom(ByteBuffer data) {
  	return data.get${bytegetname}();
  }
}