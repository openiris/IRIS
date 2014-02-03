package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;
import java.util.List;
import org.openflow.protocol.OFPort;
import java.util.LinkedList;

public class OFPacketQueue   implements org.openflow.protocol.interfaces.OFPacketQueue {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 16;

    int  queue_id;
	int  port;
	short  length;
	int pad_1th;
	short pad_2th;
	List<org.openflow.protocol.interfaces.OFQueueProperty>  properties;

    public OFPacketQueue() {
        
    }
    
    public OFPacketQueue(OFPacketQueue other) {
    	this.queue_id = other.queue_id;
		this.port = other.port;
		this.length = other.length;
		this.properties = (other.properties == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFQueueProperty>();
		for ( org.openflow.protocol.interfaces.OFQueueProperty i : other.properties ) { this.properties.add( i.dup() ); }
    }

	public int getQueueId() {
		return this.queue_id;
	}
	
	public OFPacketQueue setQueueId(int queue_id) {
		this.queue_id = queue_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isQueueIdSupported() {
		return true;
	}
			
	public OFPort getPort() {
		return new OFPort(this.port);
	}
	
	public OFPacketQueue setPort(OFPort port) {
		this.port = (int) port.get();
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPortSupported() {
		return true;
	}
	
	public short getLength() {
		return this.length;
	}
	
	public OFPacketQueue setLength(short length) {
		this.length = length;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isLengthSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFQueueProperty> getProperties() {
		return this.properties;
	}
	
	public OFPacketQueue setProperties(List<org.openflow.protocol.interfaces.OFQueueProperty> properties) {
		this.properties = properties;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPropertiesSupported() {
		return true;
	}
			
	
	
	
	public OFPacketQueue dup() {
		return new OFPacketQueue(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.queue_id = data.getInt();
		this.port = data.getInt();
		this.length = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		if (this.properties == null) this.properties = new LinkedList<org.openflow.protocol.interfaces.OFQueueProperty>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFQueueProperty t = OFQueuePropertyType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.properties.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.queue_id);
		data.putInt(this.port);
		data.putShort(this.length);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		if (this.properties != null ) for (org.openflow.protocol.interfaces.OFQueueProperty t: this.properties) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFPacketQueue-"+":queue_id=" + U32.f(queue_id) + 
		":port=" + U32.f(port) + 
		":length=" + U16.f(length) + 
		":properties=" + properties.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
		if ( this.properties != null ) for ( org.openflow.protocol.interfaces.OFQueueProperty i : this.properties ) { len += i.computeLength(); }
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
        		
		final int prime = 1459;
		int result = super.hashCode() * prime;
		result = prime * result + (int) queue_id;
		result = prime * result + (int) port;
		result = prime * result + (int) length;
		result = prime * result + ((properties == null)?0:properties.hashCode());
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
        if (!(obj instanceof OFPacketQueue)) {
            return false;
        }
        OFPacketQueue other = (OFPacketQueue) obj;
		if ( queue_id != other.queue_id ) return false;
		if ( port != other.port ) return false;
		if ( length != other.length ) return false;
		if ( properties == null && other.properties != null ) { return false; }
		else if ( !properties.equals(other.properties) ) { return false; }
        return true;
    }
}
