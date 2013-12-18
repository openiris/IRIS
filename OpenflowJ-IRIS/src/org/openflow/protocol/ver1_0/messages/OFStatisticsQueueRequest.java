package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsQueueRequest extends OFStatisticsRequest implements org.openflow.protocol.ver1_0.interfaces.OFStatisticsQueueRequest {
    public static int MINIMUM_LENGTH = 20;

    OFPortNo  port_no;
	short pad_1th;
	int  queue_id;

    public OFStatisticsQueueRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)16));
		setStatisticsType(OFStatisticsType.valueOf((short)5, getType()));
    }
    
    public OFStatisticsQueueRequest(OFStatisticsQueueRequest other) {
    	super(other);
		this.port_no = other.port_no;
		this.queue_id = other.queue_id;
    }

	public OFPortNo getPortNo() {
		return this.port_no;
	}
	
	public OFStatisticsQueueRequest setPortNo(OFPortNo port_no) {
		this.port_no = port_no;
		return this;
	}
			
	public int getQueueId() {
		return this.queue_id;
	}
	
	public OFStatisticsQueueRequest setQueueId(int queue_id) {
		this.queue_id = queue_id;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port_no = OFPortNo.valueOf(OFPortNo.readFrom(data));
		this.pad_1th = data.getShort();
		this.queue_id = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.port_no.getValue());
		data.putShort(this.pad_1th);
		data.putInt(this.queue_id);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsQueueRequest-"+":port_no=" + port_no.toString() + 
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
        		
		final int prime = 2549;
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
