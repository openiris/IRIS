package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFCapabilities {
	private int value = (int) 0;
	
	public static int	FLOW_STATS	=	0x1;
	public static int	TABLE_STATS	=	0x2;
	public static int	PORT_STATS	=	0x4;
	public static int	STP	=	0x8;
	public static int	RESERVED	=	0x10;
	public static int	IP_REASM	=	0x20;
	public static int	QUEUE_STATS	=	0x40;
	public static int	ARP_MATCH_IP	=	0x80;
	
	
	public static Map<org.openflow.protocol.interfaces.OFCapabilities, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFCapabilities, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.FLOW_STATS, FLOW_STATS);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.TABLE_STATS, TABLE_STATS);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.PORT_STATS, PORT_STATS);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.STP, STP);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.RESERVED, RESERVED);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.IP_REASM, IP_REASM);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.QUEUE_STATS, QUEUE_STATS);
		mappings.put(org.openflow.protocol.interfaces.OFCapabilities.ARP_MATCH_IP, ARP_MATCH_IP);
	}
	
	public OFCapabilities() {
		// does nothing
	}
	
	public OFCapabilities(int v) {
		this.value = v;
	}
	
	public static OFCapabilities of(int v) {
		return new OFCapabilities(v);
	}
	
	public static OFCapabilities of(org.openflow.protocol.interfaces.OFCapabilities e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
		}
		return new OFCapabilities(v);
	}
	
	public static OFCapabilities of(Collection<org.openflow.protocol.interfaces.OFCapabilities> values) {
		OFCapabilities ret = new OFCapabilities();
		ret.and(values);
		return ret;
	}
		
	
	public OFCapabilities set(int value) {
		this.value = value;
		return this;
	}
	
	public OFCapabilities set(org.openflow.protocol.interfaces.OFCapabilities value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFCapabilities or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFCapabilities or(org.openflow.protocol.interfaces.OFCapabilities ... values) {
		for (org.openflow.protocol.interfaces.OFCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFCapabilities or(Collection<org.openflow.protocol.interfaces.OFCapabilities> values) {
		for (org.openflow.protocol.interfaces.OFCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFCapabilities and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFCapabilities and(org.openflow.protocol.interfaces.OFCapabilities ... values) {
		for (org.openflow.protocol.interfaces.OFCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFCapabilities and(Collection<org.openflow.protocol.interfaces.OFCapabilities> values) {
		for (org.openflow.protocol.interfaces.OFCapabilities value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFCapabilities value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFCapabilities.set is called with illegal parameter.");
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