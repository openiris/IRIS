package org.openflow.protocol.factory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.openflow.protocol.factory.OFMessageParser;
import org.projectfloodlight.openflow.exceptions.OFParseError;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFMessage;


public class VersionedMessageParser implements OFMessageParser {
	
//	private ChannelBuffer input = null;
	
	public VersionedMessageParser(ByteBuffer src) {
//		this.input = ChannelBuffers.wrappedBuffer( src );
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
			
			try {
				ChannelBuffer cb = ChannelBuffers.wrappedBuffer(data);
				OFMessage msg = OFFactories.getGenericReader().readFrom( cb );
				if ( msg != null ) {
					results.add( msg );
					data.position( data.position() + cb.readerIndex() );
				}
				
			} catch (OFParseError e) {
				// we skip this message: not parse
				continue;
			}
		}
		return results;
	}

}