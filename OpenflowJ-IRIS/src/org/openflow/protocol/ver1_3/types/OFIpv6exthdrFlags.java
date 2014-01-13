package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFIpv6exthdrFlags {
	private short value = (short) 0;
	
	public static short	NONEXT	=	0x1;
	public static short	ESP	=	0x2;
	public static short	AUTH	=	0x4;
	public static short	DEST	=	0x8;
	public static short	FRAG	=	0x10;
	public static short	ROUTER	=	0x20;
	public static short	HOP	=	0x40;
	public static short	UNREP	=	0x80;
	public static short	UNSEQ	=	0x100;
	
	
	public static Map<org.openflow.protocol.interfaces.OFIpv6exthdrFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFIpv6exthdrFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.NONEXT, NONEXT);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.ESP, ESP);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.AUTH, AUTH);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.DEST, DEST);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.FRAG, FRAG);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.ROUTER, ROUTER);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.HOP, HOP);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.UNREP, UNREP);
		mappings.put(org.openflow.protocol.interfaces.OFIpv6exthdrFlags.UNSEQ, UNSEQ);
	}
	
	public OFIpv6exthdrFlags() {
		// does nothing
	}
	
	public OFIpv6exthdrFlags(short v) {
		this.value = v;
	}
	
	public static OFIpv6exthdrFlags of(short v) {
		return new OFIpv6exthdrFlags(v);
	}
	
	public static OFIpv6exthdrFlags of(org.openflow.protocol.interfaces.OFIpv6exthdrFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
		}
		return new OFIpv6exthdrFlags(v);
	}
	
	public static OFIpv6exthdrFlags of(Collection<org.openflow.protocol.interfaces.OFIpv6exthdrFlags> values) {
		OFIpv6exthdrFlags ret = new OFIpv6exthdrFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFIpv6exthdrFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFIpv6exthdrFlags set(org.openflow.protocol.interfaces.OFIpv6exthdrFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFIpv6exthdrFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFIpv6exthdrFlags or(org.openflow.protocol.interfaces.OFIpv6exthdrFlags ... values) {
		for (org.openflow.protocol.interfaces.OFIpv6exthdrFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFIpv6exthdrFlags or(Collection<org.openflow.protocol.interfaces.OFIpv6exthdrFlags> values) {
		for (org.openflow.protocol.interfaces.OFIpv6exthdrFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFIpv6exthdrFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFIpv6exthdrFlags and(org.openflow.protocol.interfaces.OFIpv6exthdrFlags ... values) {
		for (org.openflow.protocol.interfaces.OFIpv6exthdrFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFIpv6exthdrFlags and(Collection<org.openflow.protocol.interfaces.OFIpv6exthdrFlags> values) {
		for (org.openflow.protocol.interfaces.OFIpv6exthdrFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFIpv6exthdrFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFIpv6exthdrFlags.set is called with illegal parameter.");
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