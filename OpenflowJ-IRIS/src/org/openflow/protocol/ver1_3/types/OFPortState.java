package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFPortState {
	private int value = (int) 0;
	
	public static int	LINK_DOWN	=	0x1;
	public static int	BLOCKED	=	0x2;
	public static int	LIVE	=	0x4;
	
	
	public static Map<org.openflow.protocol.interfaces.OFPortState, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFPortState, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFPortState.LINK_DOWN, LINK_DOWN);
		mappings.put(org.openflow.protocol.interfaces.OFPortState.BLOCKED, BLOCKED);
		mappings.put(org.openflow.protocol.interfaces.OFPortState.LIVE, LIVE);
	}
	
	public OFPortState() {
		// does nothing
	}
	
	public OFPortState(int v) {
		this.value = v;
	}
	
	public static OFPortState of(int v) {
		return new OFPortState(v);
	}
	
	public static OFPortState of(org.openflow.protocol.interfaces.OFPortState e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
		}
		return new OFPortState(v);
	}
	
	public static OFPortState of(Collection<org.openflow.protocol.interfaces.OFPortState> values) {
		OFPortState ret = new OFPortState();
		ret.and(values);
		return ret;
	}
		
	
	public OFPortState set(int value) {
		this.value = value;
		return this;
	}
	
	public OFPortState set(org.openflow.protocol.interfaces.OFPortState value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFPortState or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFPortState or(org.openflow.protocol.interfaces.OFPortState ... values) {
		for (org.openflow.protocol.interfaces.OFPortState value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortState or(Collection<org.openflow.protocol.interfaces.OFPortState> values) {
		for (org.openflow.protocol.interfaces.OFPortState value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFPortState and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFPortState and(org.openflow.protocol.interfaces.OFPortState ... values) {
		for (org.openflow.protocol.interfaces.OFPortState value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFPortState and(Collection<org.openflow.protocol.interfaces.OFPortState> values) {
		for (org.openflow.protocol.interfaces.OFPortState value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFPortState value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			// throw new IllegalArgumentException("OFPortState.set is called with illegal parameter.");
			return false;
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