package etri.sdn.controller.module.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.io.Connection;

public class OFMUserInterface extends OFModule {

	private WebUserInterface web;
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		return Collections.emptyList();
	}
	
	@Override
	protected void initialize() {
		this.web = new WebUserInterface(this);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return true;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.web };
	}

}
