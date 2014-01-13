package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFFlowModFlags {
	private short value = (short) 0;
	
	public static short	SEND_FLOW_REM	=	0x1;
	public static short	CHECK_OVERLAP	=	0x2;
	public static short	RESET_COUNTS	=	0x4;
	public static short	NO_PKT_COUNTS	=	0x8;
	public static short	NO_BYT_COUNTS	=	0x10;
	
	
	public static Map<org.openflow.protocol.interfaces.OFFlowModFlags, Short> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFFlowModFlags, Short>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFFlowModFlags.SEND_FLOW_REM, SEND_FLOW_REM);
		mappings.put(org.openflow.protocol.interfaces.OFFlowModFlags.CHECK_OVERLAP, CHECK_OVERLAP);
		mappings.put(org.openflow.protocol.interfaces.OFFlowModFlags.RESET_COUNTS, RESET_COUNTS);
		mappings.put(org.openflow.protocol.interfaces.OFFlowModFlags.NO_PKT_COUNTS, NO_PKT_COUNTS);
		mappings.put(org.openflow.protocol.interfaces.OFFlowModFlags.NO_BYT_COUNTS, NO_BYT_COUNTS);
	}
	
	public OFFlowModFlags() {
		// does nothing
	}
	
	public OFFlowModFlags(short v) {
		this.value = v;
	}
	
	public static OFFlowModFlags of(short v) {
		return new OFFlowModFlags(v);
	}
	
	public static OFFlowModFlags of(org.openflow.protocol.interfaces.OFFlowModFlags e) {
		Short v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
		}
		return new OFFlowModFlags(v);
	}
	
	public static OFFlowModFlags of(Collection<org.openflow.protocol.interfaces.OFFlowModFlags> values) {
		OFFlowModFlags ret = new OFFlowModFlags();
		ret.and(values);
		return ret;
	}
		
	
	public OFFlowModFlags set(short value) {
		this.value = value;
		return this;
	}
	
	public OFFlowModFlags set(org.openflow.protocol.interfaces.OFFlowModFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFFlowModFlags or(short value) {
		this.value |= value;
		return this;
	}
	
	public OFFlowModFlags or(org.openflow.protocol.interfaces.OFFlowModFlags ... values) {
		for (org.openflow.protocol.interfaces.OFFlowModFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFFlowModFlags or(Collection<org.openflow.protocol.interfaces.OFFlowModFlags> values) {
		for (org.openflow.protocol.interfaces.OFFlowModFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFFlowModFlags and(short value) {
		this.value &= value;
		return this;
	}
	
	public OFFlowModFlags and(org.openflow.protocol.interfaces.OFFlowModFlags ... values) {
		for (org.openflow.protocol.interfaces.OFFlowModFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFFlowModFlags and(Collection<org.openflow.protocol.interfaces.OFFlowModFlags> values) {
		for (org.openflow.protocol.interfaces.OFFlowModFlags value : values ) {
			Short v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(short value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFFlowModFlags value) {
		Short v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFFlowModFlags.set is called with illegal parameter.");
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