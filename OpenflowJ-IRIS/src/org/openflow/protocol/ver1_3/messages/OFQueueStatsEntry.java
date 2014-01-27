package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;
import org.openflow.protocol.OFPort;

public class OFQueueStatsEntry   implements org.openflow.protocol.interfaces.OFQueueStatsEntry {
    public static int MINIMUM_LENGTH = 40;

    int  port_number;
	int  queue_id;
	long  transmit_bytes;
	long  transmit_packets;
	long  transmit_errors;
	int  duration_sec;
	int  duration_nsec;

    public OFQueueStatsEntry() {
        
    }
    
    public OFQueueStatsEntry(OFQueueStatsEntry other) {
    	this.port_number = other.port_number;
		this.queue_id = other.queue_id;
		this.transmit_bytes = other.transmit_bytes;
		this.transmit_packets = other.transmit_packets;
		this.transmit_errors = other.transmit_errors;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
    }

	public OFPort getPortNumber() {
		return new OFPort(this.port_number);
	}
	
	public OFQueueStatsEntry setPortNumber(OFPort port) {
		this.port_number = (int) port.get();
		return this;
	}
	
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
	
	public boolean isTransmitErrorsSupported() {
		return true;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFQueueStatsEntry setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
	
	public boolean isDurationSecSupported() {
		return true;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFQueueStatsEntry setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
	
	public boolean isDurationNsecSupported() {
		return true;
	}
			
	
	
	
	public OFQueueStatsEntry dup() {
		return new OFQueueStatsEntry(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.port_number = data.getInt();
		this.queue_id = data.getInt();
		this.transmit_bytes = data.getLong();
		this.transmit_packets = data.getLong();
		this.transmit_errors = data.getLong();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.port_number);
		data.putInt(this.queue_id);
		data.putLong(this.transmit_bytes);
		data.putLong(this.transmit_packets);
		data.putLong(this.transmit_errors);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
    }

    public String toString() {
        return  ":OFQueueStatsEntry-"+":port_number=" + U32.f(port_number) + 
		":queue_id=" + U32.f(queue_id) + 
		":transmit_bytes=" + U64.f(transmit_bytes) + 
		":transmit_packets=" + U64.f(transmit_packets) + 
		":transmit_errors=" + U64.f(transmit_errors) + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec);
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
        		
		final int prime = 1931;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port_number;
		result = prime * result + (int) queue_id;
		result = prime * result + (int) transmit_bytes;
		result = prime * result + (int) transmit_packets;
		result = prime * result + (int) transmit_errors;
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
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
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
        return true;
    }
}
