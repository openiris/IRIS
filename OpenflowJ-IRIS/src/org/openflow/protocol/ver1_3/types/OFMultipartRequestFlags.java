package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFMultipartRequestFlags {
	private short value = (short) 0;
	
	public static short	REQ_NONE	=	0x0;
	public static short	REQ_MORE	=	0x1;
	
	
	public static Map<org.openflow.protocol.interfaces.OFMultipartRequestFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMultipartRequestFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFMultipartRequestFlags.REQ_NONE, REQ_NONE);
		mappings.put(org.openflow.protocol.interfaces.OFMultipartRequestFlags.REQ_MORE, REQ_MORE);
	}
	
	public OFMultipartRequestFlags() {
		// does nothing
	}
	
	public OFMultipartRequestFlags(short v) {
		this.value = v;
	}
	
	public static OFMultipartRequestFlags of(short v) {
		return new OFMultipartRequestFlags(v);
	}
	
	public static OFMultipartRequestFlags of(org.openflow.protocol.interfaces.OFMultipartRequestFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
		}
		return new OFMultipartRequestFlags(v);
	}
	
	public static OFMultipartRequestFlags of(Collection<org.openflow.protocol.interfaces.OFMultipartRequestFlags> values) {
		OFMultipartRequestFlags ret = new OFMultipartRequestFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFMultipartRequestFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFMultipartRequestFlags set(org.openflow.protocol.interfaces.OFMultipartRequestFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFMultipartRequestFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFMultipartRequestFlags or(org.openflow.protocol.interfaces.OFMultipartRequestFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMultipartRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMultipartRequestFlags or(Collection<org.openflow.protocol.interfaces.OFMultipartRequestFlags> values) {
		for (org.openflow.protocol.interfaces.OFMultipartRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMultipartRequestFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFMultipartRequestFlags and(org.openflow.protocol.interfaces.OFMultipartRequestFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMultipartRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFMultipartRequestFlags and(Collection<org.openflow.protocol.interfaces.OFMultipartRequestFlags> values) {
		for (org.openflow.protocol.interfaces.OFMultipartRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFMultipartRequestFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMultipartRequestFlags.set is called with illegal parameter.");
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