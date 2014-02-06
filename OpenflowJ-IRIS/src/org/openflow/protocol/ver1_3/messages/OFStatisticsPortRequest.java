package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;
import org.openflow.protocol.OFPort;

public class OFStatisticsPortRequest extends OFStatisticsRequest implements org.openflow.protocol.interfaces.OFStatisticsPortRequest {
    public static int MINIMUM_LENGTH = 24;
    public static int CORE_LENGTH = 8;

    int  port;
	int pad_1th;

    public OFStatisticsPortRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
		setStatisticsType(OFStatisticsType.valueOf((short)4, this.type));
    }
    
    public OFStatisticsPortRequest(OFStatisticsPortRequest other) {
    	super(other);
		this.port = other.port;
    }

	public OFPort getPort() {
		return OFPort.of(this.port);
	}
	
	public OFStatisticsPortRequest setPort(OFPort port) {
		this.port = (int) port.get();
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPortSupported() {
		return true;
	}
	
	
	
	
	public OFStatisticsPortRequest dup() {
		return new OFStatisticsPortRequest(this);
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
        return super.toString() +  ":OFStatisticsPortRequest-"+":port=" + U32.f(port);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
        		
		final int prime = 1657;
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
        if (!(obj instanceof OFStatisticsPortRequest)) {
            return false;
        }
        OFStatisticsPortRequest other = (OFStatisticsPortRequest) obj;
		if ( port != other.port ) return false;
        return true;
    }
}
