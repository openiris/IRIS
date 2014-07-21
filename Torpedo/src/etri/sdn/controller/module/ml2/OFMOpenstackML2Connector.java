package etri.sdn.controller.module.ml2;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.io.Connection;

public class OFMOpenstackML2Connector extends OFModule implements IOpenstackML2ConnectorService {
	
	private NetworkConfiguration netConf = null;

	@Override
	protected Collection<Class<? extends IService>> services() {
		List<Class<? extends IService>> ret = new LinkedList<>();
		ret.add( IOpenstackML2ConnectorService.class);
		return ret;
	}
	
	public OFMOpenstackML2Connector() {
		this.netConf = new NetworkConfiguration(this);
	}

	@Override
	protected void initialize() {
		// because this module does not receive any message from the Openflow layer,
		// there's nothing to do here.
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn, MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing) {
		return true;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.netConf };
	}

}
