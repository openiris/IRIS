package org.openflow.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bjlee
 *
 */
public class OFPort {

	public static Map<Integer, OFPort> ports_map = new ConcurrentHashMap<Integer, OFPort>();

	public static final OFPort MAX = new OFPort(0xffffff00, true);
	public static final OFPort IN_PORT = new OFPort(0xfffffff8, true);
	public static final OFPort TABLE = new OFPort(0xfffffff9, true);
	public static final OFPort NORMAL = new OFPort(0xfffffffa, true);
	public static final OFPort FLOOD = new OFPort(0xfffffffb, true);
	public static final OFPort ALL = new OFPort(0xfffffffc, true);
	public static final OFPort CONTROLLER = new OFPort(0xfffffffd, true);
	public static final OFPort LOCAL = new OFPort(0xfffffffe, true);
	public static final OFPort ANY = new OFPort(0xffffffff, true);
	public static final OFPort NONE = ANY;

	private int port;

	private OFPort(short v) {
		this.port = v;
	}

	private OFPort(int v) {
		this(v, false);
	}

	private OFPort(int v, boolean add_to_index) {
		this.port = v;
		if (add_to_index) {
			ports_map.put(v, this);
		}
	}

	public int get() {
		return this.port;
	}

	/**
	 * Only for serialization (Jackson)
	 * @return
	 */
	public int getPort() {
		return this.get();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o == null || !(o instanceof OFPort) ) {
			return false;
		}
		return this.port == ((OFPort)o).port;
	}
	
	@Override
	public int hashCode() {
		int ret = 17;
		ret = 31 * ret + this.port;
		return ret;
		
	}

	public static OFPort of(int v) {
		OFPort p = ports_map.get(v);
		if ( p != null ) return p;
		return new OFPort(v);
	}

	public static OFPort of(short v) {
		OFPort p = ports_map.get(0xffff0000 | (int)v);
		if ( p != null ) return p;
		return new OFPort(v);
	}
}