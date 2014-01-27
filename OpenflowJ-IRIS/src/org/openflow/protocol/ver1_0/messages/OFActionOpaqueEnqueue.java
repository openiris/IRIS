package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import org.openflow.protocol.OFPort;

public class OFActionOpaqueEnqueue extends OFAction implements org.openflow.protocol.interfaces.OFActionOpaqueEnqueue {
    public static int MINIMUM_LENGTH = 16;

    short  port;
	int pad_1th;
	short pad_2th;
	int  queue_id;

    public OFActionOpaqueEnqueue() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)11));
    }
    
    public OFActionOpaqueEnqueue(OFActionOpaqueEnqueue other) {
    	super(other);
		this.port = other.port;
		this.queue_id = other.queue_id;
    }

	public OFPort getPort() {
		return new OFPort(this.port);
	}
	
	public OFActionOpaqueEnqueue setPort(OFPort port) {
		this.port = (short) port.get();
		return this;
	}
	
	public boolean isPortSupported() {
		return true;
	}
	
	public int getQueueId() {
		return this.queue_id;
	}
	
	public OFActionOpaqueEnqueue setQueueId(int queue_id) {
		this.queue_id = queue_id;
		return this;
	}
	
	public boolean isQueueIdSupported() {
		return true;
	}
			
	
	
	
	public OFActionOpaqueEnqueue dup() {
		return new OFActionOpaqueEnqueue(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		this.queue_id = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.port);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		data.putInt(this.queue_id);
    }

    public String toString() {
        return super.toString() +  ":OFActionOpaqueEnqueue-"+":port=" + U16.f(port) + 
		":queue_id=" + U32.f(queue_id);
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
        		
		final int prime = 2749;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port;
		result = prime * result + (int) queue_id;
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
        if (!(obj instanceof OFActionOpaqueEnqueue)) {
            return false;
        }
        OFActionOpaqueEnqueue other = (OFActionOpaqueEnqueue) obj;
		if ( port != other.port ) return false;
		if ( queue_id != other.queue_id ) return false;
        return true;
    }
}
