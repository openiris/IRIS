package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFPortFeatures {
	private int value = (int) 0;
	
	public static int	N_10MB_HD	=	0x1;
	public static int	N_10MB_FD	=	0x2;
	public static int	N_100MB_HD	=	0x4;
	public static int	N_100MB_FD	=	0x8;
	public static int	N_1GB_HD	=	0x10;
	public static int	N_1GB_FD	=	0x20;
	public static int	N_10GB_FD	=	0x40;
	public static int	COPPER	=	0x80;
	public static int	FIBER	=	0x100;
	public static int	AUTONEG	=	0x200;
	public static int	PAUSE	=	0x400;
	public static int	PAUSE_ASYM	=	0x800;
	
	
	public static Map<org.openflow.protocol.interfaces.OFPortFeatures, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortFeatures, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_10MB_HD, N_10MB_HD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_10MB_FD, N_10MB_FD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_100MB_HD, N_100MB_HD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_100MB_FD, N_100MB_FD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_1GB_HD, N_1GB_HD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_1GB_FD, N_1GB_FD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.N_10GB_FD, N_10GB_FD);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.COPPER, COPPER);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.FIBER, FIBER);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.AUTONEG, AUTONEG);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.PAUSE, PAUSE);
		mappings.put(org.openflow.protocol.interfaces.OFPortFeatures.PAUSE_ASYM, PAUSE_ASYM);
	}
	
	public OFPortFeatures() {
		// does nothing
	}
	
	public OFPortFeatures(int v) {
		this.value = v;
	}
	
	public static OFPortFeatures of(int v) {
		return new OFPortFeatures(v);
	}
	
	public static OFPortFeatures of(org.openflow.protocol.interfaces.OFPortFeatures e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
		}
		return new OFPortFeatures(v);
	}
	
	public static OFPortFeatures of(Collection<org.openflow.protocol.interfaces.OFPortFeatures> values) {
		OFPortFeatures ret = new OFPortFeatures();
		ret.and(values);
		return ret;
	}
		
	
	public OFPortFeatures set(int value) {
		this.value = value;
		return this;
	}
	
	public OFPortFeatures set(org.openflow.protocol.interfaces.OFPortFeatures value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFPortFeatures or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFPortFeatures or(org.openflow.protocol.interfaces.OFPortFeatures ... values) {
		for (org.openflow.protocol.interfaces.OFPortFeatures value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortFeatures or(Collection<org.openflow.protocol.interfaces.OFPortFeatures> values) {
		for (org.openflow.protocol.interfaces.OFPortFeatures value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortFeatures and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFPortFeatures and(org.openflow.protocol.interfaces.OFPortFeatures ... values) {
		for (org.openflow.protocol.interfaces.OFPortFeatures value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFPortFeatures and(Collection<org.openflow.protocol.interfaces.OFPortFeatures> values) {
		for (org.openflow.protocol.interfaces.OFPortFeatures value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFPortFeatures value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFPortFeatures.set is called with illegal parameter.");
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