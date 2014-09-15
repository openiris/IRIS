/**
 *
 */
package org.openflow.io;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.openflow.protocol.factory.OFHeader;
import org.openflow.protocol.factory.OFMessageParser;
import org.projectfloodlight.openflow.exceptions.OFParseError;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(OFMessageAsyncStream.class);
	
	static public int DEFAULT_BUFFER_SIZE = 65536;

	protected SocketReadByteChannelBuffer inBuf;
	protected SocketWriteByteChannelBuffer outBuf;
	protected SocketChannel sock;
	protected int partialReadCount = 0;

	public OFMessageAsyncStream(SocketChannel sock) throws IOException {
		this.inBuf = new SocketReadByteChannelBuffer(DEFAULT_BUFFER_SIZE);
		this.outBuf = new SocketWriteByteChannelBuffer(DEFAULT_BUFFER_SIZE);
		this.sock = sock;
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
		int writerIndex = outBuf.writerIndex();
		do { 
			try { 
				m.writeTo(outBuf);
				break;
			} catch ( IndexOutOfBoundsException e ) {
				outBuf.writerIndex( writerIndex );
				flush();
			}
		} while ( true );
	}

	/**
	 * Buffers a single outgoing openflow message
	 */
	@Override
	public void write(OFMessage m) throws IOException {
		appendMessageToOutBuf(m);
	}
	
	@Override
	public List<OFMessage> parseMessages(ChannelBuffer data) throws IOException {
		return parseMessages(data, 0);
	}

	@Override
	public List<OFMessage> parseMessages(ChannelBuffer data, int limit) throws IOException {
		
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
					logger.error("malformed msg. cannot parse. v={}:t={}:l={}", demux.getVersion(), demux.getType(), demux.getLengthU());
					throw new IOException("cannot parse malformed msg. we manually disconnect from this switch.");
				}
			} catch (OFParseError e) {
				logger.error("switch is sending wrong OF messages of size={}, e={}", demux.getLengthU(), e);
				throw new IOException(e);
			} catch (IllegalArgumentException e) {
				logger.error("switch is sending wrong version of OF messages={}, e={}", demux.getVersion(), e);
				throw new IOException(e);
			} catch ( Exception e ) {
				logger.error("exception during parsing: e={}", e);
				throw new IOException(e);
			} finally { 
				data.readerIndex( start + demux.getLengthU() );
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
		try { 
			this.outBuf.write(sock);
		} catch ( IOException e ) {
			throw e;
		} finally {
			this.outBuf.clear();
		}
	}
}
