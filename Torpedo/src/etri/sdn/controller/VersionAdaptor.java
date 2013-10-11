package etri.sdn.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.util.U16;
import org.openflow.util.U32;
import org.openflow.util.U8;


import etri.sdn.controller.protocol.io.Connection;

class OFHeader {
    public static int MINIMUM_LENGTH = 8;

    byte  version;
	byte  type;
	short  length;
	int  xid;

    public OFHeader() {
    }

	public byte getVersion() {
		return this.version;
	}
			
	public short getVersionU() {
		return U8.f(this.version);
	}
	
	public byte getType() {
		return this.type;
	}
			
	public short getLength() {
		return this.length;
	}
			
	public int getLengthU() {
		return U16.f(this.length);
	}
	
	public int getXid() {
		return this.xid;
	}
			
	public long getXidU() {
		return U32.f(this.xid);
	}	

    public void readFrom(ByteBuffer data) {
    	
        this.version = data.get();
		this.type = data.get();
		this.length = data.getShort();
		this.xid = data.getInt();
    }

    public String toString() {
        return "OFHeader-"+":version=" + U8.f(version) + 
		":type=" + U8.f(type) + 
		":length=" + U16.f(length) + 
		":xid=" + U32.f(xid);
    }
}

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
            	org.openflow.protocol.ver1_0.types.OFMessageType t = 
            		org.openflow.protocol.ver1_0.types.OFMessageType.valueOf(demux.getType());
            	
            	switch (t) {
            	case STATISTICS_REQUEST:
            	case STATISTICS_REPLY:
            		// read subtype information first.
            		org.openflow.protocol.ver1_0.messages.OFStatistics sn = 
            			org.openflow.protocol.ver1_0.types.OFStatisticsType.valueOf(subtype, t).newInstance(t);
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
            }
        }
        return results;
	}
	
}

public abstract class VersionAdaptor {

//	private static VersionedMessageFactory message_factory = new VersionedMessageFactory();
	
	private OFController controller;
	
	public VersionAdaptor(OFController controller) {
		this.controller = controller;
	}
	
	public OFController getController() {
		return this.controller;
	}
	
	
	public static final OFMessageFactory getMessageFactory() {
		return new VersionedMessageFactory();
	}
	
	public abstract boolean handleConnectedEvent(Connection conn);
	
	public abstract boolean process(Connection conn, MessageContext context, OFMessage msg);
}
