package org.openflow.protocol.factory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.openflow.protocol.OFMessage;
import org.openflow.util.U16;
import org.openflow.util.U32;
import org.openflow.util.U8;

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

/**
 * A basic OpenFlow factory that supports naive creation of both Messages and
 * Actions.
 *
 * @deprecated
 * @author David Erickson (daviderickson@cs.stanford.edu)
 * @author Rob Sherwood (rob.sherwood@stanford.edu)
 *
 */
public class BasicFactory implements OFMessageParser {

    public List<OFMessage> parseMessages(ByteBuffer data) {
        return parseMessages(data, 0);
    }

    public List<OFMessage> parseMessages(ByteBuffer data, int limit) {
        List<OFMessage> results = new ArrayList<OFMessage>();
        
        OFHeader demux = new OFHeader();

        while (limit == 0 || results.size() <= limit) {
            if (data.remaining() < OFHeader.MINIMUM_LENGTH)
                return results;

            data.mark();
            demux.readFrom(data);
            data.reset();
            
            if (demux.getLengthU() > data.remaining())
                return results;
            
            switch ( demux.getVersion() ) {
            case 0x01:		// 1.0
            	org.openflow.protocol.ver1_0.messages.OFMessage n10 = 
            		org.openflow.protocol.ver1_0.types.OFMessageType.valueOf(demux.getType()).newInstance();
            	n10.readFrom(data);
            	results.add(n10);
            case 0x04:		// 1.3
            	org.openflow.protocol.ver1_3.messages.OFMessage n13 = 
            		org.openflow.protocol.ver1_3.types.OFMessageType.valueOf(demux.getType()).newInstance();
            	n13.readFrom(data);
            	results.add(n13);
            }
        }

        return results;
    }
}
