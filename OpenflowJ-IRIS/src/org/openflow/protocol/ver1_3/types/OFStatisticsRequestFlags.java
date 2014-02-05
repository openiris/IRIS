package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFStatisticsRequestFlags {
	private short value = (short) 0;
	
	public static short	REQ_NONE	=	0x0;
	public static short	REQ_MORE	=	0x1;
	
	
	public static Map<org.openflow.protocol.interfaces.OFStatisticsRequestFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFStatisticsRequestFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFStatisticsRequestFlags.REQ_NONE, REQ_NONE);
		mappings.put(org.openflow.protocol.interfaces.OFStatisticsRequestFlags.REQ_MORE, REQ_MORE);
	}
	
	public OFStatisticsRequestFlags() {
		// does nothing
	}
	
	public OFStatisticsRequestFlags(short v) {
		this.value = v;
	}
	
	public static OFStatisticsRequestFlags of(short v) {
		return new OFStatisticsRequestFlags(v);
	}
	
	public static OFStatisticsRequestFlags of(org.openflow.protocol.interfaces.OFStatisticsRequestFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
		}
		return new OFStatisticsRequestFlags(v);
	}
	
	public static OFStatisticsRequestFlags of(Collection<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> values) {
		OFStatisticsRequestFlags ret = new OFStatisticsRequestFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFStatisticsRequestFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFStatisticsRequestFlags set(org.openflow.protocol.interfaces.OFStatisticsRequestFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFStatisticsRequestFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFStatisticsRequestFlags or(org.openflow.protocol.interfaces.OFStatisticsRequestFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatisticsRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatisticsRequestFlags or(Collection<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatisticsRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFStatisticsRequestFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFStatisticsRequestFlags and(org.openflow.protocol.interfaces.OFStatisticsRequestFlags ... values) {
		for (org.openflow.protocol.interfaces.OFStatisticsRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFStatisticsRequestFlags and(Collection<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> values) {
		for (org.openflow.protocol.interfaces.OFStatisticsRequestFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFStatisticsRequestFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			// throw new IllegalArgumentException("OFStatisticsRequestFlags.set is called with illegal parameter.");
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