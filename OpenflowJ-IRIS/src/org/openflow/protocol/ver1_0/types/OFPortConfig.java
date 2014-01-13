package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFPortConfig {
	private int value = (int) 0;
	
	public static int	PORT_DOWN	=	0x1;
	public static int	NO_STP	=	0x2;
	public static int	NO_RECV	=	0x4;
	public static int	NO_RECV_STP	=	0x8;
	public static int	NO_FLOOD	=	0x10;
	public static int	NO_FWD	=	0x20;
	public static int	NO_PACKET_IN	=	0x40;
	
	
	public static Map<org.openflow.protocol.interfaces.OFPortConfig, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortConfig, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.PORT_DOWN, PORT_DOWN);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_STP, NO_STP);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_RECV, NO_RECV);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_RECV_STP, NO_RECV_STP);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_FLOOD, NO_FLOOD);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_FWD, NO_FWD);
		mappings.put(org.openflow.protocol.interfaces.OFPortConfig.NO_PACKET_IN, NO_PACKET_IN);
	}
	
	public OFPortConfig() {
		// does nothing
	}
	
	public OFPortConfig(int v) {
		this.value = v;
	}
	
	public static OFPortConfig of(int v) {
		return new OFPortConfig(v);
	}
	
	public static OFPortConfig of(org.openflow.protocol.interfaces.OFPortConfig e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
		}
		return new OFPortConfig(v);
	}
	
	public static OFPortConfig of(Collection<org.openflow.protocol.interfaces.OFPortConfig> values) {
		OFPortConfig ret = new OFPortConfig();
		ret.and(values);
		return ret;
	}
		
	
	public OFPortConfig set(int value) {
		this.value = value;
		return this;
	}
	
	public OFPortConfig set(org.openflow.protocol.interfaces.OFPortConfig value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFPortConfig or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFPortConfig or(org.openflow.protocol.interfaces.OFPortConfig ... values) {
		for (org.openflow.protocol.interfaces.OFPortConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortConfig or(Collection<org.openflow.protocol.interfaces.OFPortConfig> values) {
		for (org.openflow.protocol.interfaces.OFPortConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortConfig and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFPortConfig and(org.openflow.protocol.interfaces.OFPortConfig ... values) {
		for (org.openflow.protocol.interfaces.OFPortConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFPortConfig and(Collection<org.openflow.protocol.interfaces.OFPortConfig> values) {
		for (org.openflow.protocol.interfaces.OFPortConfig value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFPortConfig value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFPortConfig.set is called with illegal parameter.");
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