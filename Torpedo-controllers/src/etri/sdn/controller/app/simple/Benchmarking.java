package etri.sdn.controller.app.simple;

import java.util.LinkedList;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFController;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.learningmac.OFMLearningMac;
import etri.sdn.controller.protocol.io.Connection;

/**
 * <p>
 * This controller is only for cbench-based benchmarking.
 * </p>
 * 
 * @author bjlee
 *
 */
public class Benchmarking extends OFController {

	private OFMLearningMac m_learning_mac = new OFMLearningMac();
	
	private OFModule[] packet_in_pipeline = { 
			m_learning_mac
	};

	public Benchmarking(int num_of_queue, String role) {
		super(num_of_queue, role);
	}
	
	/**
	 * This method is automatically called by IRIS core to do initialization chores.
	 * Module initializations need to be done in this function. 
	 */
	@Override
	public void init() {
		m_learning_mac.init(this);
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
		return true;
	}
}
