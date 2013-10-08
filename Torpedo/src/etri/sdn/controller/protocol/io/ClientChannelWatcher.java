package etri.sdn.controller.protocol.io;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.openflow.protocol.OFMessage;
//import org.openflow.protocol.ver1_0.messages.OFHello;
//import org.openflow.protocol.OFType;

import etri.sdn.controller.util.Logger;

/**
 * Watcher thread that monitors channels which are able to be read.
 * @author bjlee
 *
 */
public final class ClientChannelWatcher extends Thread {

	private volatile boolean quit = false;
	private Object guard = new Object();
	private Selector read_selector;
	private static Set<IOFHandler> controllers = new ConcurrentSkipListSet<IOFHandler>();

	ClientChannelWatcher() throws IOException {
		this.read_selector = Selector.open();
	}
	
	public static void registerController(IOFHandler controller) {
		controllers.add(controller);
	}
	
	public static void deregisterController(IOFHandler controller) {
		controllers.remove(controller);
	}
	
	public static Set<IOFHandler> getHandlersFor(Connection conn) {
		Set<IOFHandler> ret = new ConcurrentSkipListSet<IOFHandler>();
		for ( IOFHandler h : controllers ) {
			if ( h.isMySwitch(conn) ) {
				ret.add(h);
			}
		}
		return ret;
	}

	void shutdown() {
		quit = true;
	}

	void addClient(SocketChannel client) {
		synchronized ( guard ) {
			try {
				Connection new_conn = new Connection(client);
				Set<IOFHandler> hset = getHandlersFor(new_conn);
				if ( hset == null || hset.isEmpty() ) {
					try {
						// TODO: Does client need some indication for this disconnection?
						client.close();
					} catch (IOException e) {
						// does nothing
					}
					return;
				}
				
				new_conn.addHandler( hset );
				
				client.register( 
						read_selector.wakeup(), 
						SelectionKey.OP_READ | SelectionKey.OP_WRITE, 
						new_conn
				);
			} catch (ClosedChannelException e) {
				// channel is closed. 
				try {
					client.close();
				} catch (IOException e1) {
					// does nothing.
				}
			}
		}
	}

	void wakeup() {
		synchronized ( guard ) {
			read_selector.wakeup();
		}
	}

	@Override
	public void run() {
		while ( !quit ) {
			try {
				// guard idiom to prevent deadlock at client.register() call
				synchronized (guard) {}

				int r = read_selector.select();
				if ( r > 0 ) { // there's something to read.

					Set<SelectionKey> keys = read_selector.selectedKeys();
					for ( Iterator<SelectionKey> i = keys.iterator(); i.hasNext(); ) {
						SelectionKey key = i.next();
						i.remove();
						try { 
							Connection conn = (Connection) key.attachment();
							
							if ( !key.isValid() ) {
								handleDisconnectedEvent( conn );
								key.cancel();
								conn.close();
								continue;
							}
							
							if ( !conn.isConnected() ) {
								handleDisconnectedEvent( conn );
								key.cancel();
								conn.close();
								continue;
							}
							
							if ( key.isWritable() ) {
								if ( conn.getStatus() == Connection.STATUS.CONNECTED ) {
									// handle initial connection setup
									if ( !handleConnectedEvent( conn ) ) {
										handleDisconnectedEvent( conn );
										key.cancel();
										conn.close();
										continue;
									}
									conn.setStatus( Connection.STATUS.RUNNING );
								}
								else if ( conn.getStatus() == Connection.STATUS.RUNNING ) {
									// this is for flushing stream whenever possible.
									// without this line, this server will not respond to 
									// cbench response-time test (without -t option) because 
									// it will not give immediate response to the request
									// until the byte buffer is filled enough to flush.
									conn.flush();
								}
							}
							if ( conn.getStatus() == Connection.STATUS.RUNNING && key.isReadable() ) {
								if ( !handleReadEvent(conn) ) {
									handleDisconnectedEvent( conn );
									key.cancel();
									conn.close();
								}
							}							
						} catch ( CancelledKeyException e ) {
							e.printStackTrace();
							continue;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				// just break this watcher.
				return;
			}
		}
	}

	private boolean handleConnectedEvent(Connection conn) {
		
		// create a switch object and set to the connection.
		IOFSwitch sw = new OFSwitchImpl();
		conn.setSwitch( sw );
		
		try {
			Logger.stderr("connected with " + conn.getClient().getRemoteAddress());
		} catch (IOException e1) {
			// does nothing
		}
		
		List<OFMessage> l = new ArrayList<OFMessage>();
		
		//
		// for hello/features_request from controller, we can use version 1.0 
		//
		org.openflow.protocol.ver1_0.messages.OFHello hello = 
			(org.openflow.protocol.ver1_0.messages.OFHello) org.openflow.protocol.ver1_0.types.OFMessageType.HELLO.newInstance();
		org.openflow.protocol.ver1_0.messages.OFFeaturesRequest features_request = 
			(org.openflow.protocol.ver1_0.messages.OFFeaturesRequest) org.openflow.protocol.ver1_0.types.OFMessageType.FEATURES_REQUEST.newInstance();
		
		l.add(hello);
		l.add(features_request);
		
//		l.add(conn.getFactory().getMessage(OFType.HELLO));
//		l.add(conn.getFactory().getMessage(OFType.FEATURES_REQUEST));
		try {
			conn.getStream().write(l);
			conn.getStream().flush();
		} catch (IOException e) {
			Logger.stderr("Connection is unable to handshake");
			return false;
		}
		
		boolean ret = true;
		Set<IOFHandler> handlers = conn.getHandlers();

		for ( IOFHandler h : handlers ) {
			ret = h.handleConnectedEvent( conn ) == false ? false : ret ;
		}
		return ret;
	}
	
	private boolean handleReadEvent(Connection conn) {
		List<OFMessage> msgs = null;
		try {
			msgs = conn.read();
			if ( msgs == null ) { return true; }
		} catch (IOException e) {
			return false;
		}

		boolean ret = true;
		Set<IOFHandler> handlers = conn.getHandlers();
		for ( IOFHandler h : handlers ) {
			// if not my flow, we do not pass the msg to the controller.
			if ( !h.isMyFlow(conn, msgs) ) { continue; }
			ret = h.handleReadEvent(conn, msgs) == false ? false : ret ;
		}
		return ret;
	}

	private void handleDisconnectedEvent(Connection conn) {
		try {
			Logger.stderr("disconnected with " + conn.getClient().getRemoteAddress());
		} catch (IOException e) {
			Logger.stderr("disconnected with a switch (reason is unknown)");
		}

		Set<IOFHandler> handlers = conn.getHandlers();
		for ( IOFHandler h : handlers ) {
			h.handleDisconnectEvent( conn );
		}
	}
}