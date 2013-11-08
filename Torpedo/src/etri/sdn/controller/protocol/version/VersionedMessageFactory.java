package etri.sdn.controller.protocol.version;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.factory.OFMessageFactory;

import etri.sdn.controller.OFHeader;

class VersionedMessageFactory implements OFMessageFactory {

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
            short subtype = 0;
            if ( data.remaining() >= 2 /*sizeof short*/) 
            	subtype = data.getShort();
            data.reset();
            
            if (demux.getLengthU() > data.remaining())
                break;
            
            switch ( demux.getVersion() ) {
            //
            // FOR VERSION 1.0
            //
            case VersionAdaptor10.VERSION:		// 1.0
            	org.openflow.protocol.ver1_0.types.OFMessageType t10 = 
            		org.openflow.protocol.ver1_0.types.OFMessageType.valueOf(demux.getType());
            	
            	switch (t10) {
            	case STATISTICS_REQUEST:
            	case STATISTICS_REPLY:
            		// read subtype information first.
            		org.openflow.protocol.ver1_0.messages.OFStatistics sn = 
            			org.openflow.protocol.ver1_0.types.OFStatisticsType.valueOf(subtype, t10).newInstance(t10);
            		sn.readFrom(data);
            		results.add(sn);
            		break;
            	default:
            		org.openflow.protocol.ver1_0.messages.OFMessage nn = 
                		org.openflow.protocol.ver1_0.types.OFMessageType.valueOf(demux.getType()).newInstance();
                	nn.readFrom(data);
                	results.add(nn);
            	}	
            	break;
            case VersionAdaptor13.VERSION:
            	org.openflow.protocol.ver1_3.types.OFMessageType t13 =
            		org.openflow.protocol.ver1_3.types.OFMessageType.valueOf(demux.getType());
            	
            	switch (t13) {
            	case MULTIPART_REQUEST:
            	case MULTIPART_REPLY:
            		org.openflow.protocol.ver1_3.messages.OFMultipart mn = 
            			org.openflow.protocol.ver1_3.types.OFMultipartType.valueOf(subtype, t13).newInstance(t13);
            		mn.readFrom(data);
            		results.add(mn);
            		break;
            	default:
            		org.openflow.protocol.ver1_3.messages.OFMessage ns = 
            			org.openflow.protocol.ver1_3.types.OFMessageType.valueOf(demux.getType()).newInstance();
            		ns.readFrom(data);
            		results.add(ns);
            	}
            	break;
            }
        }
        return results;
	}
	
}