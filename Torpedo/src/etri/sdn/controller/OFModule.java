package etri.sdn.controller;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFHandler;

/**
 * This abstract class is mother of all Torpedo modules. 
 * OFModule represents a reusable unit in defining a custom controller.
 * 
 * @author bjlee
 *
 */
public abstract class OFModule {

	/**
	 * static map that holds all the references to module instances.
	 */
	private static ConcurrentMap<Class<? extends IService>, OFModule> services
	= new ConcurrentHashMap<Class<? extends IService>, OFModule>();

	/**
	 * private map that holds filters for OFMessage objects for this module.
	 */
	private ConcurrentMap<OFType, OFMFilter> filters = new ConcurrentHashMap<OFType, OFMFilter>();

	/**
	 * reference to the controller implementation. 
	 */
	protected IOFHandler controller = null;
	
	/**
	 * Return services that a module implements
	 * @return	Collection<IService>
	 */
	protected abstract Collection<Class<? extends IService>> services();

	/**
	 * return a module that implements a specific service from the 
	 * static map {@link #services}.
	 * @param c class of the service that this module implements
	 * @return	OFModule object
	 */
	public static OFModule getModule(Class<? extends IService> c) {
		return services.get( c );
	}

	/**
	 * register a filter that only selects messages that matches 
	 * the condition described by the {@link OFMFilter} object.
	 * 
	 * @param messageType type of the OFMessage that the filter applies
	 * @param filter a filter object to be applied to OFMessage objects
	 */
	public void registerFilter(OFType messageType, OFMFilter filter) {
		filters.put(messageType, filter);
	}

	/**
	 * initialize module. 
	 * This method is called by {@link OFController#init()}.
	 * 
	 * @param ctrl reference to the controller implementation
	 */
	public final void init(IOFHandler ctrl) {
		this.controller = ctrl;
		ctrl.addModule(this);
		for ( Class<? extends IService> srv : this.services() ) {
			services.put( srv, this );
		}
	}
	
	/**
	 * start all modules by calling their initialize() method.
	 * This method is called by {@link OFController#init()}.
	 */
	public final void start() {
		initialize();
	}
	
	/**
	 * returns the controller that this modules is attached.
	 * 
	 * @return reference to the controller implementation
	 */
	public IOFHandler getController() {
		return this.controller;
	}

	/**
	 * method that initializes this module. 
	 * Subclass of OFModule must implement this method.
	 * You can find the example of this method in 
	 * {@link etri.sdn.controller.module.linkdiscovery.OFMLinkDiscovery#initialize()}. 
	 */
	protected abstract void initialize();

	/**
	 * This method is called after FEATURE-REQUEST and FEATUER-REPLY is correctly exchanged.
	 * This method only calls {@link OFModule#handleHandshakedEvent(Connection, MessageContext)},
	 * which is an abstract method that all subclasses should implement.
	 * 
	 * @param conn connection on which the handshaking is done
	 * @param context message context for the handshaking messages
	 * @return false when there is an critical error which prevents the successful operation of this module.
	 */
	public boolean processHandshakeFinished(Connection conn, MessageContext context) {
		return handleHandshakedEvent(conn, context);
	}

	/**
	 * An abstract method that all subclasses should implement.
	 * This method is called after FEATURE-REQUEST and FEATURE-REPLY is correct exchanged,
	 * by {@link #processHandshakeFinished(Connection, MessageContext)}. 
	 * Normally, most of modules have very simple implementation for this method,
	 * only returning true.
	 * 
	 * @param conn connection that the event has occurred
	 * @param context message context for the handshaking messages
	 * @return true is returned when the event has been correctly processed. false otherwise.
	 */
	protected abstract boolean handleHandshakedEvent(Connection conn, MessageContext context);

	/**
	 * Handle incoming messages and return the processing result.
	 * This method first applies filters for the message type to find out 
	 * the message needs to be processed by this module. 
	 * if the answer is yes, the message is passed to 
	 * {@link #handleMessage(Connection, MessageContext, OFMessage, List)}. 
	 * 
	 * @param conn 		connection that the message has arrived
	 * @param context 	message context for the message
	 * @param msg 		the actual message object
	 * @param outgoing	responses for the message arrived
	 * @return 			true if the message has been correctly processed. false otherwise.
	 */
	public boolean processMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing) {
		OFMFilter f = filters.get(msg.getType());
		if ( f != null && f.filter(msg) == true ) {
			return handleMessage(conn, context, msg, outgoing);
		}
		return true;
	}

	/**
	 * Handle incoming messages that pass the test of filters. 
	 * All subclasses of OFModule must implement this method.
	 * This method is called by {@link #processMessage(Connection, MessageContext, OFMessage, List)}.
	 * 
	 * @param conn		connection that the message has arrived
	 * @param context	message context for the message
	 * @param msg		the actual message object
	 * @param outgoing	responses for the message arrived, which is filled by the handleMessage implementation
	 * @return			true if the message has been correctly processed. false otherwise.
	 */
	protected abstract boolean handleMessage(Connection conn, MessageContext context, OFMessage msg, List<OFMessage> outgoing);
	
	/**
	 * Process the disconnection event from a switch.
	 * This method calls {@link #handleDisconnect(Connection)} callback.
	 * 
	 * @param conn 		connection that the event has occurred
 	 * @return			true if the event has been correctly processed. false otherwise.
	 */
	public boolean processDisconnect(Connection conn) {
		return handleDisconnect(conn);
	}
	
	/**
	 * Process the disconnection event from a switch.
	 * This callback method is called by {@link #processDisconnect(Connection)}.
	 * All subclasses of OFModule should implement this method.
	 * 
	 * @param conn		connection that the event has occurred
	 * @return			true if the event has been correctly processed. false otherwise.
	 */
	protected abstract boolean handleDisconnect(Connection conn);
	
	/**
	 * returns the array of all {@link OFModel} objects associated with this module.
	 * Normally the size of the array is one, but not limited to.
	 * 
	 * @return			the array of {@link OFModel} objects
	 */
	abstract public OFModel[] getModels();
}
