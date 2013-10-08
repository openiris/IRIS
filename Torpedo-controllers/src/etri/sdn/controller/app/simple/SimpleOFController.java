package etri.sdn.controller.app.simple;

import java.util.LinkedList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFType;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.devicemanager.OFMDefaultEntityClassifier;
import etri.sdn.controller.module.devicemanager.OFMDeviceManager;
import etri.sdn.controller.module.learningmac.OFMLearningMac;
import etri.sdn.controller.module.linkdiscovery.OFMLinkDiscovery;
import etri.sdn.controller.module.statemanager.OFMStateManager;
import etri.sdn.controller.module.staticentrypusher.OFMStaticFlowEntryPusher;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.module.topologymanager.OFMTopologyManager;
import etri.sdn.controller.module.ui.OFMUserInterface;
import etri.sdn.controller.protocol.io.Connection;

/**
 * <p>
 * This is a simple OF controller which is mainly used for demonstration.
 * This controller allows all kind of packets to go to their destinations 
 * using the {@link etri.sdn.controller.module.learningmac.OFMLearningMac} module.
 * </p>
 * <p>
 * The other modules are included to test their basic operations.
 * With this controller, you can check all the basic functionalities of IRIS 
 * are fully up and operational.
 * </p>
 * 
 * @author bjlee
 *
 */
public class SimpleOFController extends OFController {

	private OFMUserInterface m_user_interface = new OFMUserInterface();
	private OFMLearningMac m_learning_mac = new OFMLearningMac();
	private OFMLinkDiscovery m_link_discovery = new OFMLinkDiscovery();
	private OFMTopologyManager m_topology_manager = new OFMTopologyManager();
	private OFMDefaultEntityClassifier m_entity_classifier = new OFMDefaultEntityClassifier();
	private OFMDeviceManager m_device_manager = new OFMDeviceManager();
	private OFMStateManager m_state_manager = new OFMStateManager();
	private OFMStaticFlowEntryPusher m_static_entry_pusher = new OFMStaticFlowEntryPusher();
	private OFMStorageManager m_storage_manager = new OFMStorageManager();
	
	private OFModule[] packet_in_pipeline = { 
			m_learning_mac,
			m_link_discovery, 
			m_topology_manager,
			m_entity_classifier, 
			m_device_manager
	};

	public SimpleOFController(int num_of_queue, String role) {
		super(num_of_queue, role);
	}
	
	/**
	 * This method is automatically called by IRIS core to do initialization chores.
	 * Module initializations need to be done in this function. 
	 */
	@Override
	public void init() {
		m_learning_mac.init(this);
		m_link_discovery.init(this);
		m_topology_manager.init(this);
		m_entity_classifier.init(this);
		m_device_manager.init(this);
		m_state_manager.init(this);			// this is not a part of the pipeline.
		m_static_entry_pusher.init(this);	// this is not a part of the pipeline.
		m_user_interface.init(this);		// this is not a part of the pipeline.
		m_storage_manager.init(this);		// this is not a part of the pipeline.
	}

	/**
	 * This is a method that must be overridden to handle PACKET_IN messages. 
	 * 
	 * @param conn		Connection to a switch
	 * @param context	{@link etri.sdn.controller.MessageContext} object which is 
	 * 					created per every incoming OFMessage
	 * @param pi		PACKET_IN message
	 */
	@Override
//	public boolean handlePacketIn(Connection conn, MessageContext context, OFPacketIn pi) {
	public boolean handlePacketIn(Connection conn, MessageContext context, OFMessage m) {
		OFPacketIn pi = (OFPacketIn) m;

		List<OFMessage> out = new LinkedList<OFMessage>();
		for ( int i = 0; i < packet_in_pipeline.length; ++i ) {
			boolean cont = packet_in_pipeline[i].processMessage( conn, context, pi, out );
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
	public boolean isMySwitch(Connection conn) {
		return true;
	}

	@Override
	public boolean isMyFlow(Connection conn, List<OFMessage> msgs) {
		return true;
	}

	/**
	 * <p>
	 * This accepts all OFMessage objects other than PACKET_IN.
	 * By tweaking this implementation, you can adjust how the messages 
	 * are processes. 
	 * </p>
	 * <p>
	 * By default, PORT_STATUS and FEATURES_REPLY messages are passed only to 
	 * {@link etri.sdn.controller.module.linkdiscovery.OFMLinkDiscovery} module. 
	 * Other messages are not handled. 
	 * </p>
	 * 
	 * @param conn		{@link etri.sdn.controller.protocol.io.Connection} object to a switch.
	 * @param context	{@link etri.sdn.controller.MessageContext} object which is 
	 * 					created per every OFMessage object. 
	 * @param m			OFMessage object
	 */
	@Override
	public boolean handleGeneric(Connection conn, MessageContext context, OFMessage m) {
		if ( m.getType() == OFType.PORT_STATUS ) {
			List<OFMessage> out = new LinkedList<OFMessage>();

			m_link_discovery.processMessage( conn, context, m, out );
			if ( !conn.write(out) ) {
				// no further processing is possible.
				return true;
			}
		}
		else if ( m.getType() == OFType.FEATURES_REPLY ) {
			return m_link_discovery.processHandshakeFinished( conn, context );
		}
		else {
			System.err.println("Unhandled OF message: "
					+ m.getType() + " from "
					+ conn.getClient().socket().getInetAddress());
		}
		return true;
	}
}
