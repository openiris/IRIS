package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFTableConfig {
	private int value = (int) 0;
	
	public static int	DEPRECATED_MASK	=	0x3;
	
	
	public static Map<org.openflow.protocol.interfaces.OFTableConfig, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFTableConfig, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFTableConfig.DEPRECATED_MASK, DEPRECATED_MASK);
	}
	
	public OFTableConfig() {
		// does nothing
	}
	
	public OFTableConfig(int v) {
		this.value = v;
	}
	
	public static OFTableConfig of(int v) {
		return new OFTableConfig(v);
	}
	
	public static OFTableConfig of(org.openflow.protocol.interfaces.OFTableConfig e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
		}
		return new OFTableConfig(v);
	}
	
	public static OFTableConfig of(Collection<org.openflow.protocol.interfaces.OFTableConfig> values) {
		OFTableConfig ret = new OFTableConfig();
		ret.and(values);
		return ret;
	}
		
	
	public OFTableConfig set(int value) {
		this.value = value;
		return this;
	}
	
	public OFTableConfig set(org.openflow.protocol.interfaces.OFTableConfig value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFTableConfig or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFTableConfig or(org.openflow.protocol.interfaces.OFTableConfig ... values) {
		for (org.openflow.protocol.interfaces.OFTableConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFTableConfig or(Collection<org.openflow.protocol.interfaces.OFTableConfig> values) {
		for (org.openflow.protocol.interfaces.OFTableConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFTableConfig and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFTableConfig and(org.openflow.protocol.interfaces.OFTableConfig ... values) {
		for (org.openflow.protocol.interfaces.OFTableConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFTableConfig and(Collection<org.openflow.protocol.interfaces.OFTableConfig> values) {
		for (org.openflow.protocol.interfaces.OFTableConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFTableConfig value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFTableConfig.set is called with illegal parameter.");
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