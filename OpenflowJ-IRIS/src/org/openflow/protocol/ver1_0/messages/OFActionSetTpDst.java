package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFActionSetTpDst extends OFAction  {
    public static int MINIMUM_LENGTH = 8;

    short  tp_port;
	short pad_1th;

    public OFActionSetTpDst() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)10));
    }
    
    public OFActionSetTpDst(OFActionSetTpDst other) {
    	super(other);
		this.tp_port = other.tp_port;
    }

	public short getTpPort() {
		return this.tp_port;
	}
	
	public OFActionSetTpDst setTpPort(short tp_port) {
		this.tp_port = tp_port;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.tp_port = data.getShort();
		this.pad_1th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.tp_port);
		data.putShort(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetTpDst-"+":tp_port=" + U16.f(tp_port);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2777;
		int result = super.hashCode() * prime;
		result = prime * result + (int) tp_port;
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
        if (!(obj instanceof OFActionSetTpDst)) {
            return false;
        }
        OFActionSetTpDst other = (OFActionSetTpDst) obj;
		if ( tp_port != other.tp_port ) return false;
        return true;
    }
}
