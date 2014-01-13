package org.openflow.util;

/**
 * @author bjlee
 *
 */
public class OFPort {
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
	
	public static OFPort of(int v) {
		return new OFPort(v);
	}
}
