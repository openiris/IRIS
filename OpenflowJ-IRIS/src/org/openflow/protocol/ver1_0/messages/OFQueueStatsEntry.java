package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import org.openflow.protocol.OFPort;

public class OFQueueStatsEntry   implements org.openflow.protocol.interfaces.OFQueueStatsEntry {
    public static int MINIMUM_LENGTH = 32;
    public static int CORE_LENGTH = 32;

    short  port_number;
	short pad_1th;
	int  queue_id;
	long  transmit_bytes;
	long  transmit_packets;
	long  transmit_errors;

    public OFQueueStatsEntry() {
        
    }
    
    public OFQueueStatsEntry(OFQueueStatsEntry other) {
    	this.port_number = other.port_number;
		this.queue_id = other.queue_id;
		this.transmit_bytes = other.transmit_bytes;
		this.transmit_packets = other.transmit_packets;
		this.transmit_errors = other.transmit_errors;
    }

	public OFPort getPortNumber() {
		return OFPort.of(this.port_number);
	}
	
	public OFQueueStatsEntry setPortNumber(OFPort port) {
		this.port_number = (short) port.get();
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPortNumberSupported() {
		return true;
	}
	
	public int getQueueId() {
		return this.queue_id;
	}
	
	public OFQueueStatsEntry setQueueId(int queue_id) {
		this.queue_id = queue_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isQueueIdSupported() {
		return true;
	}
			
	public long getTransmitBytes() {
		return this.transmit_bytes;
	}
	
	public OFQueueStatsEntry setTransmitBytes(long transmit_bytes) {
		this.transmit_bytes = transmit_bytes;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTransmitBytesSupported() {
		return true;
	}
			
	public long getTransmitPackets() {
		return this.transmit_packets;
	}
	
	public OFQueueStatsEntry setTransmitPackets(long transmit_packets) {
		this.transmit_packets = transmit_packets;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTransmitPacketsSupported() {
		return true;
	}
			
	public long getTransmitErrors() {
		return this.transmit_errors;
	}
	
	public OFQueueStatsEntry setTransmitErrors(long transmit_errors) {
		this.transmit_errors = transmit_errors;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTransmitErrorsSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFQueueStatsEntry setDurationSec(int value) {
		throw new UnsupportedOperationException("setDurationSec is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getDurationSec() {
		throw new UnsupportedOperationException("getDurationSec is not supported operation");
	}
	
	public boolean isDurationSecSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFQueueStatsEntry setDurationNsec(int value) {
		throw new UnsupportedOperationException("setDurationNsec is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getDurationNsec() {
		throw new UnsupportedOperationException("getDurationNsec is not supported operation");
	}
	
	public boolean isDurationNsecSupported() {
		return false;
	}
	
	
	
	
	public OFQueueStatsEntry dup() {
		return new OFQueueStatsEntry(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.port_number = data.getShort();
		this.pad_1th = data.getShort();
		this.queue_id = data.getInt();
		this.transmit_bytes = data.getLong();
		this.transmit_packets = data.getLong();
		this.transmit_errors = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.port_number);
		data.putShort(this.pad_1th);
		data.putInt(this.queue_id);
		data.putLong(this.transmit_bytes);
		data.putLong(this.transmit_packets);
		data.putLong(this.transmit_errors);
    }

    public String toString() {
        return  ":OFQueueStatsEntry-"+":port_number=" + U16.f(port_number) + 
		":queue_id=" + U32.f(queue_id) + 
		":transmit_bytes=" + U64.f(transmit_bytes) + 
		":transmit_packets=" + U64.f(transmit_packets) + 
		":transmit_errors=" + U64.f(transmit_errors);
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
        		
		final int prime = 2671;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port_number;
		result = prime * result + (int) queue_id;
		result = prime * result + (int) transmit_bytes;
		result = prime * result + (int) transmit_packets;
		result = prime * result + (int) transmit_errors;
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
        if (!(obj instanceof OFQueueStatsEntry)) {
            return false;
        }
        OFQueueStatsEntry other = (OFQueueStatsEntry) obj;
		if ( port_number != other.port_number ) return false;
		if ( queue_id != other.queue_id ) return false;
		if ( transmit_bytes != other.transmit_bytes ) return false;
		if ( transmit_packets != other.transmit_packets ) return false;
		if ( transmit_errors != other.transmit_errors ) return false;
        return true;
    }
}
