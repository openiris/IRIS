package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFMeterFlags {
	private short value = (short) 0;
	
	public static short	KBPS	=	0x1;
	public static short	PKTPS	=	0x2;
	public static short	BURST	=	0x4;
	public static short	STATS	=	0x8;
	
	
	public static Map<org.openflow.protocol.interfaces.OFMeterFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMeterFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFMeterFlags.KBPS, KBPS);
		mappings.put(org.openflow.protocol.interfaces.OFMeterFlags.PKTPS, PKTPS);
		mappings.put(org.openflow.protocol.interfaces.OFMeterFlags.BURST, BURST);
		mappings.put(org.openflow.protocol.interfaces.OFMeterFlags.STATS, STATS);
	}
	
	public OFMeterFlags() {
		// does nothing
	}
	
	public OFMeterFlags(short v) {
		this.value = v;
	}
	
	public static OFMeterFlags of(short v) {
		return new OFMeterFlags(v);
	}
	
	public static OFMeterFlags of(org.openflow.protocol.interfaces.OFMeterFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
		}
		return new OFMeterFlags(v);
	}
	
	public static OFMeterFlags of(Collection<org.openflow.protocol.interfaces.OFMeterFlags> values) {
		OFMeterFlags ret = new OFMeterFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFMeterFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFMeterFlags set(org.openflow.protocol.interfaces.OFMeterFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFMeterFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFMeterFlags or(org.openflow.protocol.interfaces.OFMeterFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMeterFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMeterFlags or(Collection<org.openflow.protocol.interfaces.OFMeterFlags> values) {
		for (org.openflow.protocol.interfaces.OFMeterFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFMeterFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFMeterFlags and(org.openflow.protocol.interfaces.OFMeterFlags ... values) {
		for (org.openflow.protocol.interfaces.OFMeterFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFMeterFlags and(Collection<org.openflow.protocol.interfaces.OFMeterFlags> values) {
		for (org.openflow.protocol.interfaces.OFMeterFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFMeterFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFMeterFlags.set is called with illegal parameter.");
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