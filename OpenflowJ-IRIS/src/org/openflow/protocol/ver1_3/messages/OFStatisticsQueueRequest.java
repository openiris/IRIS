package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFStatisticsQueueRequest extends OFStatisticsRequest implements org.openflow.protocol.interfaces.OFStatisticsQueueRequest {
    public static int MINIMUM_LENGTH = 24;
    public static int CORE_LENGTH = 8;

    OFPortNo  port_no;
	int  queue_id;

    public OFStatisticsQueueRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
		setStatisticsType(OFStatisticsType.valueOf((short)5, this.type));
    }
    
    public OFStatisticsQueueRequest(OFStatisticsQueueRequest other) {
    	super(other);
		this.port_no = other.port_no;
		this.queue_id = other.queue_id;
    }

	public org.openflow.protocol.interfaces.OFPortNo getPortNo() {
		return OFPortNo.to(this.port_no);
	}
	
	public OFStatisticsQueueRequest setPortNo(org.openflow.protocol.interfaces.OFPortNo port_no) {
		this.port_no = OFPortNo.from(port_no);
		return this;
	}
	
	public OFStatisticsQueueRequest setPortNo(OFPortNo port_no) {
		this.port_no = port_no;
		return this;
	}
	
	public boolean isPortNoSupported() {
		return true;
	}
	
	public int getQueueId() {
		return this.queue_id;
	}
	
	public OFStatisticsQueueRequest setQueueId(int queue_id) {
		this.queue_id = queue_id;
		return this;
	}
	
	public boolean isQueueIdSupported() {
		return true;
	}
			
	
	
	
	public OFStatisticsQueueRequest dup() {
		return new OFStatisticsQueueRequest(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port_no = OFPortNo.valueOf(OFPortNo.readFrom(data));
		this.queue_id = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.port_no.getValue());
		data.putInt(this.queue_id);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsQueueRequest-"+":port_no=" + port_no.toString() + 
		":queue_id=" + U32.f(queue_id);
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
        		
		final int prime = 1627;
		int result = super.hashCode() * prime;
		result = prime * result + ((port_no == null)?0:port_no.hashCode());
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
        if (!(obj instanceof OFStatisticsQueueRequest)) {
            return false;
        }
        OFStatisticsQueueRequest other = (OFStatisticsQueueRequest) obj;
		if ( port_no == null && other.port_no != null ) { return false; }
		else if ( !port_no.equals(other.port_no) ) { return false; }
		if ( queue_id != other.queue_id ) return false;
        return true;
    }
}
