package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFMultipartReplyFlags {
	private short value = (short) 0;
	
	public static short	REPLY_NONE	=	0x0;
	public static short	REPLY_MORE	=	0x1;
	
	
	public static Map<org.openflow.protocol.interfaces.OFMultipartReplyFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMultipartReplyFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFMultipartReplyFlags.REPLY_NONE, REPLY_NONE);
		mappings.put(org.openflow.protocol.interfaces.OFMultipartReplyFlags.REPLY_MORE, REPLY_MORE);
	}
	
	public OFMultipartReplyFlags() {
		// does nothing
	}
	
	public OFMultipartReplyFlags(short v) {
		this.value = v;
	}
	
	public static OFMultipartReplyFlags of(short v) {
		return new OFMultipartReplyFlags(v);
	}
	
	public static OFMultipartReplyFlags of(org.openflow.protocol.interfaces.OFMultipartReplyFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
		}
		return new OFMultipartReplyFlags(v);
	}
	
	public static OFMultipartReplyFlags of(Collection<org.openflow.protocol.interfaces.OFMultipartReplyFlags> values) {
		OFMultipartReplyFlags ret = new OFMultipartReplyFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFMultipartReplyFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFMultipartReplyFlags set(org.openflow.protocol.interfaces.OFMultipartReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFMultipartReplyFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFMultipartReplyFlags or(org.openflow.protocol.interfaces.OFMultipartReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMultipartReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMultipartReplyFlags or(Collection<org.openflow.protocol.interfaces.OFMultipartReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFMultipartReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMultipartReplyFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFMultipartReplyFlags and(org.openflow.protocol.interfaces.OFMultipartReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMultipartReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFMultipartReplyFlags and(Collection<org.openflow.protocol.interfaces.OFMultipartReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFMultipartReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFMultipartReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMultipartReplyFlags.set is called with illegal parameter.");
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