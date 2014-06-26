/**
 *
 */
package org.openflow.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.openflow.protocol.factory.OFHeader;
import org.openflow.protocol.factory.OFMessageParser;
import org.projectfloodlight.openflow.exceptions.OFParseError;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFMessage;

/**
 * Asynchronous OpenFlow message marshalling and unmarshalling stream wrapped
 * around an NIO SocketChannel
 * 
 * @author Rob Sherwood (rob.sherwood@stanford.edu)
 * @author David Erickson (daviderickson@cs.stanford.edu)
 * @author Byungjoon Lee (bjlee@etri.re.kr)
 * 
 */
public class OFMessageAsyncStream implements OFMessageInStream, OFMessageOutStream, OFMessageParser {
	static public int DEFAULT_BUFFER_SIZE = 65536;

	protected SocketReadByteChannelBuffer inBuf;
	protected SocketWriteByteChannelBuffer outBuf;
	protected SocketChannel sock;
	protected int partialReadCount = 0;

	public OFMessageAsyncStream(SocketChannel sock) throws IOException {
		this.inBuf = new SocketReadByteChannelBuffer(DEFAULT_BUFFER_SIZE);
		this.outBuf = new SocketWriteByteChannelBuffer(DEFAULT_BUFFER_SIZE);
		this.sock = sock;
		// this.sock.configureBlocking(false);
	}

	@Override
	public List<OFMessage> read() throws IOException {
		return this.read(0);
	}

	@Override
	public List<OFMessage> read(int limit) throws IOException {
		List<OFMessage> l;
		// int read = sock.read(inBuf);
		int read = this.inBuf.read(sock);
		if (read == -1) {
			if ( sock.isConnectionPending() ) {
				return null;
			}
			throw new IOException("connection closed");
		}
		
		l = this.parseMessages(inBuf, limit);
		if (inBuf.readable())
			inBuf.discardReadBytes();
		else
			inBuf.clear();
		return l;
	}

	protected void appendMessageToOutBuf(OFMessage m) throws IOException {
		m.writeTo(outBuf);
	}

	/**
	 * Buffers a single outgoing openflow message
	 */
	@Override
	public void write(OFMessage m) throws IOException {
		if ( this.outBuf.writableBytes() < 2048 ) {
			flush();
		}
		appendMessageToOutBuf(m);
	}

	/**
	 * Buffers a list of OpenFlow messages
	 */
	@Override
	public void write(List<OFMessage> l) throws IOException {
		for (OFMessage m : l) {
			this.write(m);
		}
	}
	
	@Override
	public List<OFMessage> parseMessages(ChannelBuffer data) {
		return parseMessages(data, 0);
	}

	@Override
	public List<OFMessage> parseMessages(ChannelBuffer data, int limit) {
		
		List<OFMessage> results = new ArrayList<OFMessage>();
		OFHeader demux = new OFHeader();

		while (limit == 0 || results.size() <= limit) {
			if (data.readableBytes() < OFHeader.MINIMUM_LENGTH)
				break;

			data.markReaderIndex();
			demux.readFrom(data);
			data.resetReaderIndex();
			
			if (demux.getLengthU() > data.readableBytes())
				break;
			
			int start = data.readerIndex();
			try {
				OFMessage msg = OFFactories.getGenericReader().readFrom( data );
				if ( msg != null ) {
					results.add( msg );
				} else {
					data.readerIndex( start );
				}
				
			} catch (OFParseError e) {
//				e.printStackTrace();
//				System.err.println(data);
				// we skip this message: not parse
				data.readerIndex( start + demux.getLengthU() );
				continue;
			}
		}
		return results;
	}

	/**
	 * Flush buffered outgoing data. Keep flushing until needsFlush() returns
	 * false. Each flush() corresponds to a SocketChannel.write(), so this is
	 * designed for one flush() per select() event
	 */
	public void flush() throws IOException {
		
//		outBuf.flip();			// swap pointers; lim = pos; pos = 0;
		do {	
			try { 
				this.outBuf.write(sock);
//			sock.write(outBuf); // write data starting at pos up to lim
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		} while ( outBuf.writableBytes() > 0 );
			
		outBuf.clear();
	}

	/**
	 * Is there outgoing buffered data that needs to be flush()'d?
	 */
	public boolean needsFlush() {
		return this.outBuf.writerIndex() > 0;
//		return outBuf.position() > 0;
	}
}
