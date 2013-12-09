package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFPortStatsEntry    {
    public static int MINIMUM_LENGTH = 112;

    int  port_number;
	int pad_1th;
	long  receive_packets;
	long  transmit_packets;
	long  receive_bytes;
	long  transmit_bytes;
	long  receive_dropped;
	long  transmit_dropped;
	long  receive_errors;
	long  transmit_errors;
	long  receive_frame_errors;
	long  receive_overrun_errors;
	long  receive_CRC_errors;
	long  collisions;
	int  duration_sec;
	int  duration_nsec;

    public OFPortStatsEntry() {
        
    }
    
    public OFPortStatsEntry(OFPortStatsEntry other) {
    	this.port_number = other.port_number;
		this.receive_packets = other.receive_packets;
		this.transmit_packets = other.transmit_packets;
		this.receive_bytes = other.receive_bytes;
		this.transmit_bytes = other.transmit_bytes;
		this.receive_dropped = other.receive_dropped;
		this.transmit_dropped = other.transmit_dropped;
		this.receive_errors = other.receive_errors;
		this.transmit_errors = other.transmit_errors;
		this.receive_frame_errors = other.receive_frame_errors;
		this.receive_overrun_errors = other.receive_overrun_errors;
		this.receive_CRC_errors = other.receive_CRC_errors;
		this.collisions = other.collisions;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
    }

	public int getPortNumber() {
		return this.port_number;
	}
	
	public OFPortStatsEntry setPortNumber(int port_number) {
		this.port_number = port_number;
		return this;
	}
			
	public long getReceivePackets() {
		return this.receive_packets;
	}
	
	public OFPortStatsEntry setReceivePackets(long receive_packets) {
		this.receive_packets = receive_packets;
		return this;
	}
			
	public long getTransmitPackets() {
		return this.transmit_packets;
	}
	
	public OFPortStatsEntry setTransmitPackets(long transmit_packets) {
		this.transmit_packets = transmit_packets;
		return this;
	}
			
	public long getReceiveBytes() {
		return this.receive_bytes;
	}
	
	public OFPortStatsEntry setReceiveBytes(long receive_bytes) {
		this.receive_bytes = receive_bytes;
		return this;
	}
			
	public long getTransmitBytes() {
		return this.transmit_bytes;
	}
	
	public OFPortStatsEntry setTransmitBytes(long transmit_bytes) {
		this.transmit_bytes = transmit_bytes;
		return this;
	}
			
	public long getReceiveDropped() {
		return this.receive_dropped;
	}
	
	public OFPortStatsEntry setReceiveDropped(long receive_dropped) {
		this.receive_dropped = receive_dropped;
		return this;
	}
			
	public long getTransmitDropped() {
		return this.transmit_dropped;
	}
	
	public OFPortStatsEntry setTransmitDropped(long transmit_dropped) {
		this.transmit_dropped = transmit_dropped;
		return this;
	}
			
	public long getReceiveErrors() {
		return this.receive_errors;
	}
	
	public OFPortStatsEntry setReceiveErrors(long receive_errors) {
		this.receive_errors = receive_errors;
		return this;
	}
			
	public long getTransmitErrors() {
		return this.transmit_errors;
	}
	
	public OFPortStatsEntry setTransmitErrors(long transmit_errors) {
		this.transmit_errors = transmit_errors;
		return this;
	}
			
	public long getReceiveFrameErrors() {
		return this.receive_frame_errors;
	}
	
	public OFPortStatsEntry setReceiveFrameErrors(long receive_frame_errors) {
		this.receive_frame_errors = receive_frame_errors;
		return this;
	}
			
	public long getReceiveOverrunErrors() {
		return this.receive_overrun_errors;
	}
	
	public OFPortStatsEntry setReceiveOverrunErrors(long receive_overrun_errors) {
		this.receive_overrun_errors = receive_overrun_errors;
		return this;
	}
			
	public long getReceiveCrcErrors() {
		return this.receive_CRC_errors;
	}
	
	public OFPortStatsEntry setReceiveCrcErrors(long receive_CRC_errors) {
		this.receive_CRC_errors = receive_CRC_errors;
		return this;
	}
			
	public long getCollisions() {
		return this.collisions;
	}
	
	public OFPortStatsEntry setCollisions(long collisions) {
		this.collisions = collisions;
		return this;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFPortStatsEntry setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFPortStatsEntry setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.port_number = data.getInt();
		this.pad_1th = data.getInt();
		this.receive_packets = data.getLong();
		this.transmit_packets = data.getLong();
		this.receive_bytes = data.getLong();
		this.transmit_bytes = data.getLong();
		this.receive_dropped = data.getLong();
		this.transmit_dropped = data.getLong();
		this.receive_errors = data.getLong();
		this.transmit_errors = data.getLong();
		this.receive_frame_errors = data.getLong();
		this.receive_overrun_errors = data.getLong();
		this.receive_CRC_errors = data.getLong();
		this.collisions = data.getLong();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.port_number);
		data.putInt(this.pad_1th);
		data.putLong(this.receive_packets);
		data.putLong(this.transmit_packets);
		data.putLong(this.receive_bytes);
		data.putLong(this.transmit_bytes);
		data.putLong(this.receive_dropped);
		data.putLong(this.transmit_dropped);
		data.putLong(this.receive_errors);
		data.putLong(this.transmit_errors);
		data.putLong(this.receive_frame_errors);
		data.putLong(this.receive_overrun_errors);
		data.putLong(this.receive_CRC_errors);
		data.putLong(this.collisions);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
    }

    public String toString() {
        return  ":OFPortStatsEntry-"+":port_number=" + U32.f(port_number) + 
		":receive_packets=" + U64.f(receive_packets) + 
		":transmit_packets=" + U64.f(transmit_packets) + 
		":receive_bytes=" + U64.f(receive_bytes) + 
		":transmit_bytes=" + U64.f(transmit_bytes) + 
		":receive_dropped=" + U64.f(receive_dropped) + 
		":transmit_dropped=" + U64.f(transmit_dropped) + 
		":receive_errors=" + U64.f(receive_errors) + 
		":transmit_errors=" + U64.f(transmit_errors) + 
		":receive_frame_errors=" + U64.f(receive_frame_errors) + 
		":receive_overrun_errors=" + U64.f(receive_overrun_errors) + 
		":receive_CRC_errors=" + U64.f(receive_CRC_errors) + 
		":collisions=" + U64.f(collisions) + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
    	short l = (short)(computeLength() % req);
    	if ( l == 0 ) { return 0; }
    	return (short)( req - l );
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	return (short)(computeLength() - (short)MINIMUM_LENGTH + alignment((short)0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1933;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port_number;
		result = prime * result + (int) receive_packets;
		result = prime * result + (int) transmit_packets;
		result = prime * result + (int) receive_bytes;
		result = prime * result + (int) transmit_bytes;
		result = prime * result + (int) receive_dropped;
		result = prime * result + (int) transmit_dropped;
		result = prime * result + (int) receive_errors;
		result = prime * result + (int) transmit_errors;
		result = prime * result + (int) receive_frame_errors;
		result = prime * result + (int) receive_overrun_errors;
		result = prime * result + (int) receive_CRC_errors;
		result = prime * result + (int) collisions;
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
        if (!(obj instanceof OFPortStatsEntry)) {
            return false;
        }
        OFPortStatsEntry other = (OFPortStatsEntry) obj;
		if ( port_number != other.port_number ) return false;
		if ( receive_packets != other.receive_packets ) return false;
		if ( transmit_packets != other.transmit_packets ) return false;
		if ( receive_bytes != other.receive_bytes ) return false;
		if ( transmit_bytes != other.transmit_bytes ) return false;
		if ( receive_dropped != other.receive_dropped ) return false;
		if ( transmit_dropped != other.transmit_dropped ) return false;
		if ( receive_errors != other.receive_errors ) return false;
		if ( transmit_errors != other.transmit_errors ) return false;
		if ( receive_frame_errors != other.receive_frame_errors ) return false;
		if ( receive_overrun_errors != other.receive_overrun_errors ) return false;
		if ( receive_CRC_errors != other.receive_CRC_errors ) return false;
		if ( collisions != other.collisions ) return false;
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
        return true;
    }
}
