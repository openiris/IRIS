package etri.sdn.controller;

import org.openflow.protocol.OFMessage;

import etri.sdn.controller.protocol.io.Connection;

public abstract class VersionAdaptor {

	private OFController controller;
	
	public VersionAdaptor(OFController controller) {
		this.controller = controller;
	}
	
	public OFController getController() {
		return this.controller;
	}
	
	public abstract boolean process(Connection conn, MessageContext context, OFMessage msg);
}
