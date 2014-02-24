package etri.sdn.controller;

import org.openflow.protocol.OFMessage;

/**
 * This class is to filer {@link OFMessage} objects coming from 
 * the connections to switches with given condition. 
 * you can find the example usage of this class 
 * in {@link etri.sdn.controller.module.linkdiscovery.OFMLinkDiscovery#initialize()} method. 
 * This class is normally used as a second parameter to 
 * {@link OFModule#registerFilter(org.openflow.protocol.interfaces.OFMessageType, OFMFilter)}.
 * 
 * @author bjlee
 *
 */
public abstract class OFMFilter {

	/**
	 * OFMFilter method to override to define a message filter.
	 * If a filter is not given by a module, that module does not accept any messages.
	 * 
	 * @param m message to accept
	 * @return returns true to accept the message, or returns false
	 */
	public abstract boolean filter(OFMessage m);
	
}
