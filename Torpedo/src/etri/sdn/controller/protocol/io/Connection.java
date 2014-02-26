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

	public Connection(SocketChannel client) {
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
	
	public IOFSwitch getSwitch () {
		return this.sw;
	}
	
	public void setSwitch(IOFSwitch sw) {
		this.sw = sw;
		this.sw.setConnection(this);
	}
	
	public int getSeq() { 
		return this.seq;
	}

	public SocketChannel getClient() {
		return client;
	}

	public Set<IOFHandler> getHandlers() {
		return handlers;
	}
	
	public void addHandler(IOFHandler handler) {
		handlers.add(handler);
	}
	
	public void addHandler(Set<IOFHandler> handlers ) {
		this.handlers.addAll( handlers );
	}

	public STATUS getStatus() {
		return client_status;
	}

	public void setStatus(STATUS stat) {
		this.client_status = stat;
	}

	public OFMessageAsyncStream getStream() {
		return stream;
	}

	public synchronized void close() {
		client_status = STATUS.CLOSED;
		try {
			client.close();
		} catch (IOException e) {
			// does nothing.
		}
	}

	public boolean isConnected() {
		// client.isConnected() does not correctly return the 
		// status of the channel after close() is called.
		// thus, we choose to use isOpen() instead.
		return client.isOpen();
	}

	public synchronized List<OFMessage> read() throws IOException {
		return getStream().read();
	}

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

	public synchronized boolean flush() {
		try {
			getStream().flush();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public synchronized boolean write(List<OFMessage> out) {
		for ( OFMessage m : out ) {
			try { 
				getStream().write( m );
				
				// watch the channel 'client' for write!
				this.markToWrite();
			} catch ( IOException e ) {
				return false;
			}
		}
		return true;
	}
		
	public void setSelector(Selector read_selector) {
		this.selector = read_selector;
	}
	
	private void markToWrite() {
		if ( this.write_set.compareAndSet(false, true) ) {
			this.client.keyFor(this.selector).interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			this.selector.wakeup();
		}
	}

	public void markFlushed() {
		if ( this.write_set.compareAndSet(true, false) ) {
			this.client.keyFor(this.selector).interestOps(SelectionKey.OP_READ);
			this.selector.wakeup();
		}
	}
}
