package $packagename;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class $typename {
	private $rep value = ($rep) 0;
	
	$body
	
	public static Map<org.openflow.protocol.interfaces.$typename, $orep> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.$typename, $orep>();
	
	static {
	$staticbody
	}
	
	public $typename() {
		// does nothing
	}
	
	public $typename($rep v) {
		this.value = v;
	}
	
	public static $typename of($rep v) {
		return new $typename(v);
	}
	
	public static $typename of(org.openflow.protocol.interfaces.$typename e) {
		$orep v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
		}
		return new ${typename}(v);
	}
	
	public static $typename of(Collection<org.openflow.protocol.interfaces.$typename> values) {
		$typename ret = new $typename();
		ret.and(values);
		return ret;
	}
		
	
	public $typename set($rep value) {
		this.value = value;
		return this;
	}
	
	public $typename set(org.openflow.protocol.interfaces.$typename value) {
		$orep v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public $typename or($rep value) {
		this.value |= value;
		return this;
	}
	
	public $typename or(org.openflow.protocol.interfaces.$typename ... values) {
		for (org.openflow.protocol.interfaces.$typename value : values ) {
			$orep v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public $typename or(Collection<org.openflow.protocol.interfaces.$typename> values) {
		for (org.openflow.protocol.interfaces.$typename value : values ) {
			$orep v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public $typename and($rep value) {
		this.value &= value;
		return this;
	}
	
	public $typename and(org.openflow.protocol.interfaces.$typename ... values) {
		for (org.openflow.protocol.interfaces.$typename value : values ) {
			$orep v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public $typename and(Collection<org.openflow.protocol.interfaces.$typename> values) {
		for (org.openflow.protocol.interfaces.$typename value : values ) {
			$orep v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has($rep value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.$typename value) {
		$orep v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("${typename}.set is called with illegal parameter.");
		}
		return (this.value & v) == v;
	}
	
	public $rep get() {
		return this.value;
	}
	
	public static $rep readFrom(ByteBuffer data) {
		return data.get$methodname();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}