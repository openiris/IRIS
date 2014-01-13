package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFGroupCapabilities {
	private int value = (int) 0;
	
	public static int	SELECT_WEIGHT	=	0x1;
	public static int	SELECT_LIVENESS	=	0x2;
	public static int	CHAINING	=	0x4;
	public static int	CHAINING_CHECKS	=	0x8;
	
	
	public static Map<org.openflow.protocol.interfaces.OFGroupCapabilities, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFGroupCapabilities, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFGroupCapabilities.SELECT_WEIGHT, SELECT_WEIGHT);
		mappings.put(org.openflow.protocol.interfaces.OFGroupCapabilities.SELECT_LIVENESS, SELECT_LIVENESS);
		mappings.put(org.openflow.protocol.interfaces.OFGroupCapabilities.CHAINING, CHAINING);
		mappings.put(org.openflow.protocol.interfaces.OFGroupCapabilities.CHAINING_CHECKS, CHAINING_CHECKS);
	}
	
	public OFGroupCapabilities() {
		// does nothing
	}
	
	public OFGroupCapabilities(int v) {
		this.value = v;
	}
	
	public static OFGroupCapabilities of(int v) {
		return new OFGroupCapabilities(v);
	}
	
	public static OFGroupCapabilities of(org.openflow.protocol.interfaces.OFGroupCapabilities e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
		}
		return new OFGroupCapabilities(v);
	}
	
	public static OFGroupCapabilities of(Collection<org.openflow.protocol.interfaces.OFGroupCapabilities> values) {
		OFGroupCapabilities ret = new OFGroupCapabilities();
		ret.and(values);
		return ret;
	}
		
	
	public OFGroupCapabilities set(int value) {
		this.value = value;
		return this;
	}
	
	public OFGroupCapabilities set(org.openflow.protocol.interfaces.OFGroupCapabilities value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFGroupCapabilities or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFGroupCapabilities or(org.openflow.protocol.interfaces.OFGroupCapabilities ... values) {
		for (org.openflow.protocol.interfaces.OFGroupCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFGroupCapabilities or(Collection<org.openflow.protocol.interfaces.OFGroupCapabilities> values) {
		for (org.openflow.protocol.interfaces.OFGroupCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFGroupCapabilities and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFGroupCapabilities and(org.openflow.protocol.interfaces.OFGroupCapabilities ... values) {
		for (org.openflow.protocol.interfaces.OFGroupCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFGroupCapabilities and(Collection<org.openflow.protocol.interfaces.OFGroupCapabilities> values) {
		for (org.openflow.protocol.interfaces.OFGroupCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFGroupCapabilities value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFGroupCapabilities.set is called with illegal parameter.");
		}
		return (this.value & v) == v;
	}
	
	public int get() {
		return this.value;
	}
	
	public static int readFrom(ByteBuffer data) {
		return data.getInt();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}