package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFStatisticsReplyFlags {
	private short value = (short) 0;
	
	public static short	REPLY_NONE	=	0x0;
	public static short	REPLY_MORE	=	0x1;
	
	
	public static Map<org.openflow.protocol.interfaces.OFStatisticsReplyFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFStatisticsReplyFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFStatisticsReplyFlags.REPLY_NONE, REPLY_NONE);
		mappings.put(org.openflow.protocol.interfaces.OFStatisticsReplyFlags.REPLY_MORE, REPLY_MORE);
	}
	
	public OFStatisticsReplyFlags() {
		// does nothing
	}
	
	public OFStatisticsReplyFlags(short v) {
		this.value = v;
	}
	
	public static OFStatisticsReplyFlags of(short v) {
		return new OFStatisticsReplyFlags(v);
	}
	
	public static OFStatisticsReplyFlags of(org.openflow.protocol.interfaces.OFStatisticsReplyFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
		}
		return new OFStatisticsReplyFlags(v);
	}
	
	public static OFStatisticsReplyFlags of(Collection<org.openflow.protocol.interfaces.OFStatisticsReplyFlags> values) {
		OFStatisticsReplyFlags ret = new OFStatisticsReplyFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFStatisticsReplyFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFStatisticsReplyFlags set(org.openflow.protocol.interfaces.OFStatisticsReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFStatisticsReplyFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFStatisticsReplyFlags or(org.openflow.protocol.interfaces.OFStatisticsReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatisticsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatisticsReplyFlags or(Collection<org.openflow.protocol.interfaces.OFStatisticsReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatisticsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatisticsReplyFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFStatisticsReplyFlags and(org.openflow.protocol.interfaces.OFStatisticsReplyFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatisticsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFStatisticsReplyFlags and(Collection<org.openflow.protocol.interfaces.OFStatisticsReplyFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatisticsReplyFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFStatisticsReplyFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			// throw new IllegalArgumentException("OFStatisticsReplyFlags.set is called with illegal parameter.");
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