package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFStatsReplyFlags {
	private short value = (short) 0;
	
	public static short	REPLY_MORE	=	0x1;
	
	
	public static Map<org.openflow.protocol.interfaces.OFStatsReplyFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFStatsReplyFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFStatsReplyFlags.REPLY_MORE, REPLY_MORE);
	}
	
	public OFStatsReplyFlags() {
		// does nothing
	}
	
	public OFStatsReplyFlags(short v) {
		this.value = v;
	}
	
	public static OFStatsReplyFlags of(short v) {
		return new OFStatsReplyFlags(v);
	}
	
	public static OFStatsReplyFlags of(org.openflow.protocol.interfaces.OFStatsReplyFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
		}
		return new OFStatsReplyFlags(v);
	}
	
	public static OFStatsReplyFlags of(Collection<org.openflow.protocol.interfaces.OFStatsReplyFlags> values) {
		OFStatsReplyFlags ret = new OFStatsReplyFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFStatsReplyFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFStatsReplyFlags set(org.openflow.protocol.interfaces.OFStatsReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFStatsReplyFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFStatsReplyFlags or(org.openflow.protocol.interfaces.OFStatsReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatsReplyFlags or(Collection<org.openflow.protocol.interfaces.OFStatsReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatsReplyFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFStatsReplyFlags and(org.openflow.protocol.interfaces.OFStatsReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFStatsReplyFlags and(Collection<org.openflow.protocol.interfaces.OFStatsReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFStatsReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			// throw new IllegalArgumentException("OFStatsReplyFlags.set is called with illegal parameter.");
			return false;
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