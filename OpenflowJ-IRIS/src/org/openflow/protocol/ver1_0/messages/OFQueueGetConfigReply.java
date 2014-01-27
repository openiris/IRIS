package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import java.util.List;
import org.openflow.protocol.OFPort;
import java.util.LinkedList;

public class OFQueueGetConfigReply extends OFMessage implements org.openflow.protocol.interfaces.OFQueueGetConfigReply {
    public static int MINIMUM_LENGTH = 16;

    short  port;
	int pad_1th;
	short pad_2th;
	List<org.openflow.protocol.interfaces.OFPacketQueue>  queues;

    public OFQueueGetConfigReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)21));
    }
    
    public OFQueueGetConfigReply(OFQueueGetConfigReply other) {
    	super(other);
		this.port = other.port;
		this.queues = (other.queues == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFPacketQueue>();
		for ( org.openflow.protocol.interfaces.OFPacketQueue i : other.queues ) { this.queues.add( new OFPacketQueue((OFPacketQueue)i) ); }
    }

	public OFPort getPort() {
		return new OFPort(this.port);
	}
	
	public OFQueueGetConfigReply setPort(OFPort port) {
		this.port = (short) port.get();
		return this;
	}
	
	public boolean isPortSupported() {
		return true;
	}
	
	public List<org.openflow.protocol.interfaces.OFPacketQueue> getQueues() {
		return this.queues;
	}
	
	public OFQueueGetConfigReply setQueues(List<org.openflow.protocol.interfaces.OFPacketQueue> queues) {
		this.queues = queues;
		return this;
	}
	
	public boolean isQueuesSupported() {
		return true;
	}
			
	
	
	
	public OFQueueGetConfigReply dup() {
		return new OFQueueGetConfigReply(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.port = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		if (this.queues == null) this.queues = new LinkedList<org.openflow.protocol.interfaces.OFPacketQueue>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFPacketQueue t = new OFPacketQueue(); t.readFrom(data); this.queues.add(t); __cnt -= t.getLength(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.port);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		if (this.queues != null ) for (org.openflow.protocol.interfaces.OFPacketQueue t: this.queues) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFQueueGetConfigReply-"+":port=" + U16.f(port) + 
		":queues=" + queues.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.queues != null ) for ( org.openflow.protocol.interfaces.OFPacketQueue i : this.queues ) { len += i.computeLength(); }
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
        		
		final int prime = 2459;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port;
		result = prime * result + ((queues == null)?0:queues.hashCode());
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
        if (!(obj instanceof OFQueueGetConfigReply)) {
            return false;
        }
        OFQueueGetConfigReply other = (OFQueueGetConfigReply) obj;
		if ( port != other.port ) return false;
		if ( queues == null && other.queues != null ) { return false; }
		else if ( !queues.equals(other.queues) ) { return false; }
        return true;
    }
}
