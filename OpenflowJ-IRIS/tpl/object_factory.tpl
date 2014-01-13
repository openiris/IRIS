package org.openflow.protocol.factory;

import org.openflow.protocol.interfaces.*;

public class OFMessageFactory {

	/**
	 * String such as '1.0' and '1.3' are allowed.
	 */
	String version;
	
	public OFMessageFactory(String version) {
		this.version = version;
	}
	
	$create_methods
}
