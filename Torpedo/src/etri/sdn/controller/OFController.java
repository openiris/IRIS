package etri.sdn.controller;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import org.openflow.protocol.OFMessage;

import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFHandler;
import etri.sdn.controller.protocol.io.IOFProtocolServer;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.version.VersionAdaptor;
import etri.sdn.controller.protocol.version.VersionAdaptor10;
import etri.sdn.controller.protocol.version.VersionAdaptor13;
import etri.sdn.controller.util.Logger;

/**
 * Mother of all OpenFlow controller implementations. 
 * 
 * @author bjlee
 *
 */
public abstract class OFController implements IOFHandler, Comparable<IOFHandler> {

	/**
	 * Timer for scheduling asynchronous jobs.
	 */
	private Timer timer = new Timer();

	/** 
	 * index to all switches.
	 */
	private ConcurrentHashMap<Long, IOFSwitch> switches = new ConcurrentHashMap<Long, IOFSwitch>();
	
	/**
	 * set of all modules.
	 */
	private Set<OFModule> modules = Collections.synchronizedSet(new HashSet<OFModule>());

	/**
	 * role of this controller. (EQUAL, MASTER, SLAVE)
	 */
	private Role role = null;

	/**
	 * Queue Item
	 * @author bjlee
	 *
	 */
	private class QI {
		Connection conn;
		List<OFMessage> msgs;

		public QI(Connection conn, List<OFMessage> msgs) {
			this.conn = conn;
			this.msgs = msgs;
		}

		Connection connection() { 
			return this.conn;
		}

		List<OFMessage> messages() {
			return this.msgs;
		}
	}

	/**
	 * Queue Item Processor, which is a thread that pulls QI object
	 * from the queue that is associated with the thread.
	 * Each QP thread has one queue to receive QI object.
	 * Through the experiment, we have found out that TransferQueue is the most 
	 * effective queueing mechanism for this thread.
	 * 
	 * @author bjlee
	 *
	 */
	private class QP extends Thread {
		private volatile boolean quit = false;
		private TransferQueue<QI> queue = new LinkedTransferQueue<QI>();
		private MessageContext context = new MessageContext();
		private OFController controller;

		/**
		 * Constructor
		 * @param ctrl reference to the OFController object
		 */
		public QP(OFController ctrl) {
			this.controller = ctrl;
		}

		/**
		 * Shutdown this thread to stop.
		 */
		public void shutdown() {
			quit = true;
		}

		/**
		 * This function delivers a read event to the QP thread.
		 * @param conn
		 * @param msgs
		 * @return
		 */
		public boolean handleReadEvent(Connection conn, List<OFMessage> msgs) {

			do {
				try {
					this.queue.transfer( new QI(conn, msgs) );
				} catch (InterruptedException e) {
					continue;
				}
			} while ( false );

			return true;
		}

		/**
		 * Read one read event from the queue, and process it. 
		 */
		@Override
		public void run() {
			List<QI> qis = new LinkedList<QI>();

			try { 
				while ( !quit ) { 
					try {
						qis.clear();

						// I don't know why 300 guarantees the best performance (-_-)
						QI qi = this.queue.poll(300, TimeUnit.MILLISECONDS);						
						if ( qi == null ) {
							continue;
						}

						qis.add(qi);
						this.queue.drainTo( qis );

						for ( QI item : qis ) {
							process( item.connection(), item.messages() );
						}

					} catch (InterruptedException e) {
						continue;
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				this.controller.removeSelf();
				return;					// end this controller thread.
			} catch ( Error e ) {
				e.printStackTrace();
				this.controller.removeSelf();
				return;					// end this controller thread.
			}
		}
		
		/**
		 * process a list of OFMessage objects received from the connection.
		 * 
		 * @param conn connection to switch
		 * @param msgs messages received through the connection
		 * @return true if successfully processed. However, currently the return value 
		 *              of this function is not used by {@link QP#run()}.
		 */
		private boolean process(Connection conn, List<OFMessage> msgs) {
			VersionAdaptor vadp = null;

			for (OFMessage m : msgs) {
//				System.out.println(m.toString());
				context.getStorage().clear();

				if ( vadp == null ) {
					vadp = version_adaptors.get(m.getVersion());
					if ( vadp == null ) {
						Logger.debug("Cannot find suitable version adaptor for version " + m.getVersion());
						return false;
					}
				}
				
				if ( !vadp.process(conn, context, m) ) {
					return false;
				}

			}
			return true;
		}
	}

	/**
	 * Queue Item Processors 
	 */
	private QP[] processors = null;
	
	/**
	 * Protocol Server object, which is currently {@link TcpServer}.
	 */
	private IOFProtocolServer server = null;
	
	private Map<Byte/*version*/, VersionAdaptor> version_adaptors = new ConcurrentHashMap<Byte, VersionAdaptor>();
	private byte max_version = (byte)0x00;

	/**
	 * OFController constructor.
	 * 
	 * @param num_of_queue number of Queue Item Processors
	 */
	public OFController(int num_of_queue, String role) {
		this.processors = new QP[num_of_queue];
		for ( int i = 0; i < this.processors.length; ++i ) {
			this.processors[i] = new QP(this);
		}

		if ( role.equals("MASTER") ) {
			this.role = Role.MASTER;
		}
		else if ( role.equals("EQUAL") ) {
			this.role = Role.EQUAL;
		}
		else if ( role.equals("SLAVE") ) {
			this.role = Role.SLAVE;
		}
		else {
			this.role = null;
		}
		
		setVersionAdaptor(VersionAdaptor10.VERSION, new VersionAdaptor10(this));
		setVersionAdaptor(VersionAdaptor13.VERSION, new VersionAdaptor13(this));
	}
	
	@Override
	public VersionAdaptor getVersionAdaptor(byte version) {
		return version_adaptors.get(version);
	}
	
	@Override
	public void setVersionAdaptor(byte version, VersionAdaptor adaptor) {
		if (version > max_version) {
			max_version = version;
		}
		version_adaptors.put(version, adaptor);
	}
	
	/**
	 * Every controller implementation that inherits OFController should implement this function
	 * to handle all the chores related to the controller initialization. 
	 * You can use SimpleOFController class as a reference to see what kind of 
	 * initialization you should do. 
	 */
	public abstract void init();

	/**
	 * get the role of this controller. But currently, the return value of this function 
	 * only set to MASTER. 
	 */
	@Override
	public Role getRole() {
		return this.role;
	}

	/**
	 * Register a protocol server that actually handles the underlying bearer protocol.
	 */
	@Override
	public void registerProtocolServer(IOFProtocolServer server) {
		this.server = server;
	}

	/**
	 * This method is called by {@link QP#run} when a controller fails
	 * to remove itself from the protocol server, which prevents this controller
	 * from receiving other Openflow messages from the protocol server.
	 */
	public void removeSelf() {
		Logger.stderr("We're now removing this controller from the protocol server.");
		Logger.stderr("If you want to, please fix the controller error by following procedure.");
		Logger.stderr("First, remove the controller jar file from the controllers directory.");
		Logger.stderr("Second, fix the bug and put the fixed jar file under controllers directory.");
		Logger.stderr("That's it. For more information, plz contact bjlee@etri.re.kr");

		if ( this.server != null ) {
			this.server.deregisterConroller( this );
		}
	}

	/**
	 * Shutdown this OF Controller instance including all the Queue Processors
	 */
	public void shutdown() {
		for ( QP qp : processors ) {
			qp.shutdown();
		}
	}

	/**
	 * Start all Queue Processors.
	 */
	public void start() {
		for ( QP qp : processors ) {
			qp.start();
		}
	}

	/**
	 * Schedule a task to be executed after 'after'. 
	 * Unless the task.execute() returns false, the task periodically repeated 
	 * using 'after' as a period value.
	 * 
	 * @param task
	 * @param time millisecond since Epoch
	 * @param after re-schedule this task after 'after' milliseconds. If zero is given,
	 *              the task will start immediately, and never be repeated.
	 */
	@Override
	public void scheduleTask(final IOFTask task, final long after) {
		timer.scheduleAtFixedRate(
				new TimerTask() { 
					public void run() {
						if ( !task.execute() ) {
							// no further re-scheduling is possible.
							this.cancel();
							return;
						}

						if ( after == 0 ) {
							// this guarantees only one-time execution.
							this.cancel();
						}
					}
				},
				after,
				after
		);
	}

	/*
	 * Following methods are something you should implement.
	 */

	/** 
	 * This callback is called when a new connection to a new switch is made.
	 * Internally, the initial HELLO handshaking to the switch is made.
	 */
	@Override
	public final boolean handleConnectedEvent(Connection conn) {
		VersionAdaptor va = getVersionAdaptor(this.max_version);
		// This will handle the initial HELLO handshaking to the switch.
		return va.handleConnectedEvent(conn);
	}
	
	/**
	 * handle Packet-in Message.
	 * @param conn underlying connection
	 * @param context 
	 * @param m Packet-in message.
	 * @return returns false when the underling connection caused error.
	 */
	public abstract boolean handlePacketIn(Connection conn, MessageContext context, OFMessage m);

	/**
	 * handle the other messages.
	 * @param conn underlying connection
	 * @param context 
	 * @param m message which is not packet-in, hello, or echo request message.
	 * @return returns false when the underling connection caused error.
	 */
	public abstract boolean handleGeneric(Connection conn, MessageContext context, OFMessage m);

	/**
	 * This is a method that cannot be overridden. This is an internal method 
	 * only called by ClientChannelWatcher object. 
	 */
	@Override
	public final boolean handleReadEvent(Connection conn, List<OFMessage> msgs) {
		return processors[ conn.getSeq() % processors.length ].handleReadEvent(conn, msgs);
	}

	/*
	 * You can freely modify following methods.
	 */

	/**
	 * return the set of all switch identifiers
	 */
	@Override
	public final Set<Long> getSwitchIdentifiers() {
		return switches.keySet();
	}

	/**
	 * return the set of all switches
	 */
	@Override
	public final Collection<IOFSwitch> getSwitches() {
		return this.switches.values();
	}

	/**
	 * return a specific switch with a given identifier
	 */
	@Override
	public final IOFSwitch getSwitch(long id) {
		return this.switches.get(id);
	}
	
	@Override
	public void addSwitch(long id, IOFSwitch sw) {
		this.switches.put(id, sw);
	}

	/**
	 * This callback is called when a connection to a switch is lost.
	 */
	@Override
	public final boolean handleDisconnectEvent(Connection conn) {
		System.out.println("disconnected");
		if ( conn.getSwitch() != null ) {
			
			for (OFModule m: modules) {
				m.processDisconnect(conn);
			}
			
			try { 
				switches.remove( conn.getSwitch().getId() );
			} catch ( RuntimeException e ) {
				// this catch clause is for catching RuntimeException
				// raised within conn.getSwitch().getId(). 
				// this exception is raised when the connection is abruptly cut
				// before FEATURE_REPLY is received from the peer.
				// So, we do nothing for this exception.
			}
		}
		return true;
	}

	/**
	 * This function returns true if a given switch is assigned to this controller.
	 * But this function is not used currently, so should be removed from the source.
	 */
	@Override
	public boolean isMySwitch(Connection conn) {
		return true;
	}

	/**
	 * This function returns true if a given messages is assigned to this controller.
	 * But this function is not used currently, so should be removed from the source.
	 */
	@Override
	public boolean isMyFlow(Connection conn, List<OFMessage> msgs) {
		return true;
	}
	
	/**
	 * This method is automatically called by {@link OFModule#init(IOFHandler)}.
	 * This method is for adding a module as a part of this controller object.
	 */
	@Override
	public final void addModule(OFModule module) {
		this.modules.add(module);
	}

	@Override
	public final int compareTo(IOFHandler o) {
		if ( this == o ) {
			return 0;
		}
		else if ( this.hashCode() < o.hashCode() ) {
			return -1;
		}
		return +1;
	}

	/**
	 * This method returns all the {@link OFModel} object 
	 * which are associated with this controller. 
	 * As an {@link OFModel} object is normally associated with 
	 * a {@link OFModule} object, this method calls {@link OFModule#getModels} method
	 * of each {@link OFModule} object in {@link OFController#modules}. 
	 */
	@Override
	public final OFModel[] getModels() {
		ArrayList<OFModel> l = new ArrayList<OFModel>();
		
		for ( OFModule m : this.modules ) {
			OFModel[] models = m.getModels();
			if ( models == null ) {
				continue;
			}
			for ( int i = 0; i < models.length; ++i ) {
				l.add( models[i] );
			}
		}
		
		return l.toArray(new OFModel[l.size()]);
	}
	
	/**
	 * get a String representation of all module names 
	 * linked with ','.
	 */
	@Override
	public final String getConcatenatedModuleNames() {
		StringBuffer buf = new StringBuffer();
		int i = 0;
		for ( OFModule m : this.modules ) {
			String name = m.getClass().getCanonicalName();
			if ( i > 0 ) {
				buf.append(", ");
			}
			buf.append(name);
			++i;
		}
		return buf.toString();
	}
	
	/**
	 * get all the {@link OFModule} objects associated with this controller.
	 */
	@Override
	public final Collection<OFModule> getModules() {
		return this.modules;
	}
	
	/**
	 * get an array that holds all the module names associated with this controller.
	 */
	@Override
	public final String[] getModuleNames() {
		if ( this.modules.size() <= 0 ) {
			return null;
		}
		
		String[] ret = new String[ this.modules.size() ];
		
		int i = 0;
		for ( OFModule m : this.modules ) {
			ret[ i++ ] = m.getClass().getCanonicalName();
		}
		
		return ret;
	}
}
