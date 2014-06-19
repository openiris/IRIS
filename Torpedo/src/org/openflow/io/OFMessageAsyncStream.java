/**
 *
 */
package org.openflow.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

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

	protected ByteBuffer inBuf, outBuf;
	protected SocketChannel sock;
	protected int partialReadCount = 0;

	public OFMessageAsyncStream(SocketChannel sock) throws IOException {
		this.inBuf = ByteBuffer.allocateDirect(DEFAULT_BUFFER_SIZE);
		this.outBuf = ByteBuffer.allocateDirect(DEFAULT_BUFFER_SIZE);
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
		int read = sock.read(inBuf);
		if (read == -1) {
			if ( sock.isConnectionPending() ) {
				return null;
			}
			throw new IOException("connection closed");
		}
		inBuf.flip();
		l = this.parseMessages(inBuf, limit);
		if (inBuf.hasRemaining())
			inBuf.compact();
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
		if ( this.outBuf.remaining() < 2048 ) {
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
	public List<OFMessage> parseMessages(ByteBuffer data) {
		return parseMessages(data, 0);
	}

	@Override
	public List<OFMessage> parseMessages(ByteBuffer data, int limit) {
		
		
		List<OFMessage> results = new ArrayList<OFMessage>();
		OFHeader demux = new OFHeader();

		while (limit == 0 || results.size() <= limit) {
			if (data.remaining() < OFHeader.MINIMUM_LENGTH)
				break;

			data.mark();
			demux.readFrom(data);
			data.reset();
			
			if (demux.getLengthU() > data.remaining())
				break;
			
			int start = data.position();
			try {
				OFMessage msg = OFFactories.getGenericReader().readFrom( data );
				if ( msg != null ) {
					results.add( msg );
				} else {
					data.position( start );
				}
				
			} catch (OFParseError e) {
//				e.printStackTrace();
//				System.err.println(data);
				// we skip this message: not parse
				data.position( start + demux.getLengthU() );
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
		
		outBuf.flip();			// swap pointers; lim = pos; pos = 0;
		do {	
			sock.write(outBuf); // write data starting at pos up to lim
		} while ( outBuf.remaining() > 0 );
			
		outBuf.clear();
	}

	/**
	 * Is there outgoing buffered data that needs to be flush()'d?
	 */
	public boolean needsFlush() {
		return outBuf.position() > 0;
	}
}
