package etri.sdn.controller.protocol.io;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openflow.io.OFMessageAsyncStream;
import org.openflow.protocol.OFMessage;
//import org.openflow.protocol.factory.BasicFactory;

public final class Connection {
	public enum STATUS { CONNECTED, RUNNING, CLOSED };

	private static int SEQ = 0;

	private SocketChannel client;
	private IOFSwitch sw;
	private Set<IOFHandler> handlers = new ConcurrentSkipListSet<IOFHandler>();
	private STATUS client_status;
	private OFMessageAsyncStream stream;
	private int seq;

	private Selector selector;
	AtomicBoolean write_set = new AtomicBoolean(false);

	/**
	 * Constructor to create a new Connection object.
	 * @param client
	 */
	Connection(SocketChannel client) {
		this.client = client;
		this.sw = null;
		this.client_status = STATUS.CONNECTED;
		try {
			this.stream = new OFMessageAsyncStream( client /*, VersionAdaptor.getMessageFactory() */ );
		} catch (IOException e) {
			this.stream = null;
		}
		this.seq = ++SEQ;
	}
	
	/**
	 * Get IOFSwitch (switch) object
	 * @return
	 */
	public IOFSwitch getSwitch () {
		return this.sw;
	}
	
	/**
	 * Set switch connected via this connection
	 * @param sw	IOFSwitch object to set
	 */
	void setSwitch(IOFSwitch sw) {
		this.sw = sw;
		this.sw.setConnection(this);
	}
	
	/**
	 * Get sequence number (identifier for this connection)
	 * @return	sequence number (id) of this connection
	 */
	public int getSeq() { 
		return this.seq;
	}

	/**
	 * Get underlying socket channel
	 * @return	SocketChannel object
	 */
	public SocketChannel getClient() {
		return client;
	}

	/**
	 * Get all controller instances runninng
	 * @return	Set<IOFHandler>
	 */
	Set<IOFHandler> getHandlers() {
		return handlers;
	}
	
	/**
	 * Add controller instance
	 * @param handler	IOFHandler (Controller) object
	 */
	void addHandler(IOFHandler handler) {
		handlers.add(handler);
	}
	
	/**
	 * Add a set of controller instances
	 * @param handlers	Set<IOFHandler>
	 */
	void addHandler(Set<IOFHandler> handlers ) {
		this.handlers.addAll( handlers );
	}

	/**
	 * Return the status of corrent connection
	 * @return	STATUS value
	 */
	STATUS getStatus() {
		return client_status;
	}

	/**
	 * Set the status of the connection
	 * @param stat	STATUS value
	 */
	void setStatus(STATUS stat) {
		this.client_status = stat;
	}

	/**
	 * Return the underlying message stream
	 * @return	OFMessageAsyncStream object
	 */
	private OFMessageAsyncStream getStream() {
		return stream;
	}

	/**
	 * Close this connection
	 */
	public synchronized void close() {
		client_status = STATUS.CLOSED;
		try {
			client.close();
		} catch (IOException e) {
			// does nothing.
		}
	}

	/**
	 * Is this connection alive?
	 * @return	true if alive, false otherwise
	 */
	public boolean isConnected() {
		// client.isConnected() does not correctly return the 
		// status of the channel after close() is called.
		// thus, we choose to use isOpen() instead.
		return client.isOpen();
	}

	/**
	 * Read OF messages from the connection 
	 * @return	List<OFMessage> object
	 * @throws 	IOException
	 */
	synchronized List<OFMessage> read() throws IOException {
		return getStream().read();
	}

	/**
	 * Write an OF message to switch
	 * @param fm	OF Message to write
	 * @return		true if successful, false otherwise
	 */
	public synchronized boolean write(OFMessage fm) {
		if ( fm == null ) return true;
		
		if ( fm.getXid() == 0 ) {
			fm.setXid(sw.getNextTransactionId());
		}

		try {
			getStream().write( fm.setLength( fm.computeLength() ) );
			// watch the channel 'client' for write!
			this.markToWrite();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/** 
	 * Flush the connection to write all the pending buffer to switch
	 * @return true if successful, false otherwise
	 */
	synchronized boolean flush() {
		this.markFlushed();
		try {
			getStream().flush();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Write OF messages to switch
	 * @param out	OF messages to write
	 * @return		true if successful, false otherwise
	 */
	public synchronized boolean write(List<OFMessage> out) {
		
		for ( OFMessage m : out ) {
			try { 
				getStream().write( m );
			} catch ( IOException e ) {
				return false;
			}
		}
		// watch the channel 'client' for write!
		this.markToWrite();
		return true;
	}
	
	/**
	 * Append selector to this connection
	 * @param read_selector selector to attach 
	 */
	void setSelector(Selector read_selector) {
		this.selector = read_selector;
	}
	
	/**
	 * Mark the selector to monitor both read and write event
	 */
	private void markToWrite() {
		if ( this.write_set.compareAndSet(false, true) ) {
			this.client.keyFor(this.selector).interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			this.selector.wakeup();
		}
	}

	/**
	 * Mark the selector to only monitor read event
	 */
	private void markFlushed() {
		if ( this.write_set.compareAndSet(true, false) ) {
			this.client.keyFor(this.selector).interestOps(SelectionKey.OP_READ);
			this.selector.wakeup();
		}
	}
}
