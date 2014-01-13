package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.util.OFPort;
import org.openflow.protocol.ver1_3.types.*;

public class OFQueueGetConfigRequest extends OFMessage implements org.openflow.protocol.interfaces.OFQueueGetConfigRequest {
    public static int MINIMUM_LENGTH = 16;

    int  port;
	int pad_1th;

    public OFQueueGetConfigRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)22));
    }
    
    public OFQueueGetConfigRequest(OFQueueGetConfigRequest other) {
    	super(other);
		this.port = other.port;
    }

	public OFPort getPort() {
		return new OFPort(this.port);
	}
	
	public OFQueueGetConfigRequest setPort(OFPort port) {
		this.port = (int) port.get();
		return this;
	}
	
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port = data.getInt();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.port);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFQueueGetConfigRequest-"+":port=" + U32.f(port);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1453;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port;
		return result;
    }

    @Override
    public boolean equals(Object obj) {
        
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFQueueGetConfigRequest)) {
            return false;
        }
        OFQueueGetConfigRequest other = (OFQueueGetConfigRequest) obj;
		if ( port != other.port ) return false;
        return true;
    }
}
