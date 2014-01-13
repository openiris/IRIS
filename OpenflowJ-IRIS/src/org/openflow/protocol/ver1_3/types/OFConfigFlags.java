package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFConfigFlags {
	private short value = (short) 0;
	
	public static short	FRAG_NORMAL	=	0;
	public static short	FRAG_DROP	=	1;
	public static short	FRAG_REASM	=	2;
	public static short	FRAG_MASK	=	3;
	
	
	public static Map<org.openflow.protocol.interfaces.OFConfigFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFConfigFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_NORMAL, FRAG_NORMAL);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_DROP, FRAG_DROP);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_REASM, FRAG_REASM);
		mappings.put(org.openflow.protocol.interfaces.OFConfigFlags.FRAG_MASK, FRAG_MASK);
	}
	
	public OFConfigFlags() {
		// does nothing
	}
	
	public OFConfigFlags(short v) {
		this.value = v;
	}
	
	public static OFConfigFlags of(short v) {
		return new OFConfigFlags(v);
	}
	
	public static OFConfigFlags of(org.openflow.protocol.interfaces.OFConfigFlags e) {
		Short v = mappings.get(e);
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
		
	
	public OFConfigFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFConfigFlags set(org.openflow.protocol.interfaces.OFConfigFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFConfigFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFConfigFlags or(org.openflow.protocol.interfaces.OFConfigFlags ... values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFConfigFlags or(Collection<org.openflow.protocol.interfaces.OFConfigFlags> values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFConfigFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFConfigFlags and(org.openflow.protocol.interfaces.OFConfigFlags ... values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFConfigFlags and(Collection<org.openflow.protocol.interfaces.OFConfigFlags> values) {
		for (org.openflow.protocol.interfaces.OFConfigFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFConfigFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFConfigFlags.set is called with illegal parameter.");
		}
		return (this.value & v) == v;
	}
	
	public short get() {
		return this.value;
	}
	
	public static short readFrom(ByteBuffer data) {
		return data.getShort();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}