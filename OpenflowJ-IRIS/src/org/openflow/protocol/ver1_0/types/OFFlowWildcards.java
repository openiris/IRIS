package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class OFFlowWildcards {
	private int value = (int) 0;
	
	public static int	IN_PORT	=	0x1;
	public static int	DL_VLAN	=	0x2;
	public static int	DL_SRC	=	0x4;
	public static int	NW_DST_BITS	=	0x6;
	public static int	NW_SRC_BITS	=	0x6;
	public static int	NW_SRC_SHIFT	=	0x8;
	public static int	DL_DST	=	0x8;
	public static int	NW_DST_SHIFT	=	0xe;
	public static int	DL_TYPE	=	0x10;
	public static int	NW_PROTO	=	0x20;
	public static int	TP_SRC	=	0x40;
	public static int	TP_DST	=	0x80;
	public static int	NW_SRC_ALL	=	0x2000;
	public static int	NW_SRC_MASK	=	0x3f00;
	public static int	NW_DST_ALL	=	0x80000;
	public static int	NW_DST_MASK	=	0xfc000;
	public static int	DL_VLAN_PCP	=	0x100000;
	public static int	NW_TOS	=	0x200000;
	public static int	ALL	=	0x3fffff;
	
	
	public static Map<org.openflow.protocol.interfaces.OFFlowWildcards, Integer> mappings
		= new ConcurrentHashMap<org.openflow.protocol.interfaces.OFFlowWildcards, Integer>();
	
	static {
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.IN_PORT, IN_PORT);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.DL_VLAN, DL_VLAN);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.DL_SRC, DL_SRC);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_DST_BITS, NW_DST_BITS);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_SRC_BITS, NW_SRC_BITS);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_SRC_SHIFT, NW_SRC_SHIFT);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.DL_DST, DL_DST);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_DST_SHIFT, NW_DST_SHIFT);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.DL_TYPE, DL_TYPE);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_PROTO, NW_PROTO);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.TP_SRC, TP_SRC);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.TP_DST, TP_DST);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_SRC_ALL, NW_SRC_ALL);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_SRC_MASK, NW_SRC_MASK);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_DST_ALL, NW_DST_ALL);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_DST_MASK, NW_DST_MASK);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.DL_VLAN_PCP, DL_VLAN_PCP);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.NW_TOS, NW_TOS);
		mappings.put(org.openflow.protocol.interfaces.OFFlowWildcards.ALL, ALL);
	}
	
	public OFFlowWildcards() {
		// does nothing
	}
	
	public OFFlowWildcards(int v) {
		this.value = v;
	}
	
	public static OFFlowWildcards of(int v) {
		return new OFFlowWildcards(v);
	}
	
	public static OFFlowWildcards of(org.openflow.protocol.interfaces.OFFlowWildcards e) {
		Integer v = mappings.get(e);
		if ( v == null ) {
			throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
		}
		return new OFFlowWildcards(v);
	}
	
	public static OFFlowWildcards of(Collection<org.openflow.protocol.interfaces.OFFlowWildcards> values) {
		OFFlowWildcards ret = new OFFlowWildcards();
		ret.and(values);
		return ret;
	}
		
	
	public OFFlowWildcards set(int value) {
		this.value = value;
		return this;
	}
	
	public OFFlowWildcards set(org.openflow.protocol.interfaces.OFFlowWildcards value) {
		Integer v = mappings.get(value);
		if ( v == null ) { 
			throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
		}
		this.value = v;
		return this;
	}
	
	public OFFlowWildcards or(int value) {
		this.value |= value;
		return this;
	}
	
	public OFFlowWildcards or(org.openflow.protocol.interfaces.OFFlowWildcards ... values) {
		for (org.openflow.protocol.interfaces.OFFlowWildcards value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFFlowWildcards or(Collection<org.openflow.protocol.interfaces.OFFlowWildcards> values) {
		for (org.openflow.protocol.interfaces.OFFlowWildcards value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
			}
			this.value |= v;
		}
		return this;
	}
	
	public OFFlowWildcards and(int value) {
		this.value &= value;
		return this;
	}
	
	public OFFlowWildcards and(org.openflow.protocol.interfaces.OFFlowWildcards ... values) {
		for (org.openflow.protocol.interfaces.OFFlowWildcards value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public OFFlowWildcards and(Collection<org.openflow.protocol.interfaces.OFFlowWildcards> values) {
		for (org.openflow.protocol.interfaces.OFFlowWildcards value : values ) {
			Integer v = mappings.get(value);
			if ( v == null ) { 
				throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
			}
			this.value &= v;
		}
		return this;
	}
	
	public boolean has(int value) {
		return (this.value & value) == value;
	}
	
	public boolean has(org.openflow.protocol.interfaces.OFFlowWildcards value) {
		Integer v = mappings.get(value);
		if ( v == null ) {
			throw new IllegalArgumentException("OFFlowWildcards.set is called with illegal parameter.");
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