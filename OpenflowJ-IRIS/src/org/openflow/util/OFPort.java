package org.openflow.util;

/**
 * @author bjlee
 *
 */
public class OFPort {
	public static final OFPort MAX = new OFPort(0xffffff00);
	public static final OFPort IN_PORT = new OFPort(0xfffffff8);
	public static final OFPort TABLE = new OFPort(0xfffffff9);
	public static final OFPort NORMAL = new OFPort(0xfffffffa);
	public static final OFPort FLOOD = new OFPort(0xfffffffb);
	public static final OFPort ALL = new OFPort(0xfffffffc);
	public static final OFPort CONTROLLER = new OFPort(0xfffffffd);
	public static final OFPort LOCAL = new OFPort(0xfffffffe);
	public static final OFPort ANY = new OFPort(0xffffffff);
	public static final OFPort NONE = new OFPort(0xffffffff);
	
	private int port;

	public OFPort(short v) {
		this.port = v;
	}

	public OFPort(int v) {
		this.port = v;
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

	public static OFPort of(int v) {
		return new OFPort(v);
	}
}
