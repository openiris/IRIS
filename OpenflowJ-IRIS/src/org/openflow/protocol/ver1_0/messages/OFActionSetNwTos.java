package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFActionSetNwTos extends OFAction  {
    public static int MINIMUM_LENGTH = 8;

    byte  nw_tos;
	short pad_1th;
	byte pad_2th;

    public OFActionSetNwTos() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)8));
    }
    
    public OFActionSetNwTos(OFActionSetNwTos other) {
    	super(other);
		this.nw_tos = other.nw_tos;
    }

	public byte getNwTos() {
		return this.nw_tos;
	}
	
	public OFActionSetNwTos setNwTos(byte nw_tos) {
		this.nw_tos = nw_tos;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.nw_tos = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.nw_tos);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetNwTos-"+":nw_tos=" + U8.f(nw_tos);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2767;
		int result = super.hashCode() * prime;
		result = prime * result + (int) nw_tos;
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
        if (!(obj instanceof OFActionSetNwTos)) {
            return false;
        }
        OFActionSetNwTos other = (OFActionSetNwTos) obj;
		if ( nw_tos != other.nw_tos ) return false;
        return true;
    }
}
