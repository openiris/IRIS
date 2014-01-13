package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFConfigFlags {
	private int value = (int) 0;
	
	public static int	FRAG_NORMAL	=	0x0;
	public static int	FRAG_DROP	=	0x1;
	public static int	FRAG_REASM	=	0x2;
	public static int	FRAG_MASK	=	0x3;
	
	
	public static Map<org.openflow.protocol.interfaces.OFConfigFlags, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFConfigFlags, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_NORMAL, FRAG_NORMAL);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_DROP, FRAG_DROP);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_REASM, FRAG_REASM);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_MASK, FRAG_MASK);
	}
	
	public OFConfigFlags() {
		// does nothing
	}
	
	public OFConfigFlags(int v) {
		this.value = v;
	}
	
	public static OFConfigFlags of(int v) {
		return new OFConfigFlags(v);
	}
	
	public static OFConfigFlags of(org.openflow.protocol.interfaces.OFConfigFlags e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
		}
		return new OFConfigFlags(v);
	}
	
	public static OFConfigFlags of(Collection<org.openflow.protocol.interfaces.OFConfigFlags> values) {
		OFConfigFlags ret = new OFConfigFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFConfigFlags set(int value) {
		this.value = value;
		return this;
	}
	
	public OFConfigFlags set(org.openflow.protocol.interfaces.OFConfigFlags value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFConfigFlags or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFConfigFlags or(org.openflow.protocol.interfaces.OFConfigFlags ... values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFConfigFlags or(Collection<org.openflow.protocol.interfaces.OFConfigFlags> values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFConfigFlags and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFConfigFlags and(org.openflow.protocol.interfaces.OFConfigFlags ... values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFConfigFlags and(Collection<org.openflow.protocol.interfaces.OFConfigFlags> values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFConfigFlags value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
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