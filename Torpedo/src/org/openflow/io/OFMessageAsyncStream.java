/**
 *
 */
package org.openflow.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.openflow.protocol.factory.OFMessageParser;
import org.openflow.protocol.factory.VersionedMessageParser;
import org.projectfloodlight.openflow.protocol.OFMessage;

/**
 * Asynchronous OpenFlow message marshalling and unmarshalling stream wrapped
 * around an NIO SocketChannel
 * 
 * @author Rob Sherwood (rob.sherwood@stanford.edu)
 * @author David Erickson (daviderickson@cs.stanford.edu)
 * 
 */
public class OFMessageAsyncStream implements OFMessageInStream,
OFMessageOutStream {
	static public int DEFAULT_BUFFER_SIZE = 65536;

	protected ByteBuffer inBuf, outBuf;
	protected ChannelBuffer outChannel;
	protected OFMessageParser messageFactory;
	protected SocketChannel sock;
	protected int partialReadCount = 0;

	public OFMessageAsyncStream(SocketChannel sock /*,
            OFMessageFactory messageFactory */) throws IOException {
		this.inBuf = ByteBuffer.allocateDirect(DEFAULT_BUFFER_SIZE);
		this.outBuf = ByteBuffer.allocateDirect(DEFAULT_BUFFER_SIZE);
		this.outChannel = ChannelBuffers.wrappedBuffer(this.outBuf);
		this.outChannel.writerIndex(0);
		this.sock = sock;
		this.messageFactory = new VersionedMessageParser(this.inBuf);
		// this.sock.configureBlocking(false);
	}

	@Override
	public List<OFMessage> read() throws IOException {
		return this.read(0);
	}

	@Override
	public List<OFMessage> read(int limit) throws IOException {
		List<OFMessage> l;
		int read = sock.read(inBuf);
		if (read == -1) {
			if ( sock.isConnectionPending() ) {
				return null;
			}
			throw new IOException("connection closed");
		}
		inBuf.flip();
		l = messageFactory.parseMessages(inBuf, limit);
		if (inBuf.hasRemaining())
			inBuf.compact();
		else
			inBuf.clear();
		return l;
	}

	protected void appendMessageToOutBuf(OFMessage m) throws IOException {

		do {
			try { 
//				System.out.println("1:"+this.outBuf);
				ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
				m.writeTo( cb);
				this.outBuf.put( cb.toByteBuffer() );
//				this.outBuf.position( this.outBuf.position() + this.outChannel.writerIndex() );
//				System.out.println("2:"+this.outBuf);
//				this.outChannel.writerIndex(0);
				this.flush();
			} catch ( IndexOutOfBoundsException eo ) {
				eo.printStackTrace();
				this.flush();
				continue;
			}
		} while ( false );
	}

	/**
	 * Buffers a single outgoing openflow message
	 */
	@Override
	public void write(OFMessage m) throws IOException {
		appendMessageToOutBuf(m);
	}

	/**
	 * Buffers a list of OpenFlow messages
	 */
	@Override
	public void write(List<OFMessage> l) throws IOException {
		for (OFMessage m : l) {
			appendMessageToOutBuf(m);
		}
	}

	/**
	 * Flush buffered outgoing data. Keep flushing until needsFlush() returns
	 * false. Each flush() corresponds to a SocketChannel.write(), so this is
	 * designed for one flush() per select() event
	 */
	public void flush() throws IOException {
		outBuf.flip(); // swap pointers; lim = pos; pos = 0;
		sock.write(outBuf); // write data starting at pos up to lim
		outBuf.compact();
	}

	/**
	 * Is there outgoing buffered data that needs to be flush()'d?
	 */
	public boolean needsFlush() {
		return outBuf.position() > 0;
	}

	/**
	 * @return the messageFactory
	 */
	public OFMessageParser getMessageFactory() {
		return messageFactory;
	}

	/**
	 * @param messageFactory
	 *            the messageFactory to set
	 */
	public void setMessageFactory(OFMessageParser messageFactory) {
		this.messageFactory = messageFactory;
	}
}
