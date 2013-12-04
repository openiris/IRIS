package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFQueueGetConfigRequest extends OFMessage  {
    public static int MINIMUM_LENGTH = 12;

    short  port;
	short pad_1th;

    public OFQueueGetConfigRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)20));
    }
    
    public OFQueueGetConfigRequest(OFQueueGetConfigRequest other) {
    	super(other);
		this.port = other.port;
    }

	public short getPort() {
		return this.port;
	}
	
	public OFQueueGetConfigRequest setPort(short port) {
		this.port = port;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port = data.getShort();
		this.pad_1th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.port);
		data.putShort(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFQueueGetConfigRequest-"+":port=" + U16.f(port);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2467;
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
