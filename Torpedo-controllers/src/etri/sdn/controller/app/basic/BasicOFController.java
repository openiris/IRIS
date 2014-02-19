package etri.sdn.controller.app.basic;

import java.util.LinkedList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.interfaces.OFMessageType;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.devicemanager.OFMDefaultEntityClassifier;
import etri.sdn.controller.module.devicemanager.OFMDeviceManager;
import etri.sdn.controller.module.firewall.OFMFirewall;
import etri.sdn.controller.module.forwarding.Forwarding;
import etri.sdn.controller.module.linkdiscovery.OFMLinkDiscovery;
import etri.sdn.controller.module.statemanager.OFMStateManager;
//import etri.sdn.controller.module.staticentrypusher.OFMStaticFlowEntryPusher;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.module.topologymanager.OFMTopologyManager;
import etri.sdn.controller.module.ui.OFMUserInterface;
import etri.sdn.controller.protocol.io.Connection;

public class BasicOFController extends OFController {

	private OFMUserInterface m_user_interface = new OFMUserInterface();
	private OFMLinkDiscovery m_link_discovery = new OFMLinkDiscovery();
	private OFMTopologyManager m_topology_manager = new OFMTopologyManager();
	private OFMDefaultEntityClassifier m_entity_classifier = new OFMDefaultEntityClassifier();
	private OFMDeviceManager m_device_manager = new OFMDeviceManager();
	private OFMStateManager m_state_manager = new OFMStateManager();
	private OFMStorageManager m_storage_manager = new OFMStorageManager();	
	private Forwarding m_forwarding = new Forwarding();
	private OFMFirewall m_firewall = new OFMFirewall();
	
	private OFModule[] packet_in_pipeline = { 
			m_link_discovery, 
			m_topology_manager,
			m_entity_classifier, 
			m_device_manager,
			m_firewall,
			m_forwarding
			
	};

	public BasicOFController(int num_of_queue, String role) {
		super(num_of_queue, role);
	}
	
	/**
	 * This method is automatically called to do initialization chores.
	 */
	@Override
	public void init() {
		m_link_discovery.init(this);
		m_topology_manager.init(this);
		m_entity_classifier.init(this);
		m_device_manager.init(this);
		m_state_manager.init(this);			// this is not a part of the pipeline.
		m_user_interface.init(this);		// this is not a part of the pipeline.
		m_storage_manager.init(this);		// this is not a part of the pipeline.s
		m_firewall.init(this);
		m_forwarding.init(this);
	}

	@Override
	public boolean handlePacketIn(Connection conn, MessageContext context, OFMessage m) {
		List<OFMessage> out = new LinkedList<OFMessage>();
		for ( int i = 0; i < packet_in_pipeline.length; ++i ) {
			boolean cont = packet_in_pipeline[i].processMessage( conn, context, m, out );
			if ( !conn.write(out) ) {
				return false;
			}
			if ( !cont ) {
				// we process this packet no further.
				break;
			}
			out.clear();
		}
		return true;
	}

	@Override
	public boolean handleGeneric(Connection conn, MessageContext context, OFMessage m) {
		
		OFMessageType t = m.getType();
		
		if ( t == OFMessageType.PORT_STATUS ) {
			List<OFMessage> out = new LinkedList<OFMessage>();

			m_link_discovery.processMessage( conn, context, m, out );
			if ( !conn.write(out) ) {
				// no further processing is possible.
				return true;
			}
		}
		else if ( t == OFMessageType.FEATURES_REPLY ) {
			return m_link_discovery.processHandshakeFinished( conn, context );
		}
		else {
//			System.err.println("Unhandled OF message: "	+ m.toString());
		}
		return true;
	}
}
