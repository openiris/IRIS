package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFMeterStats   implements org.openflow.protocol.interfaces.OFMeterStats {
    public static int MINIMUM_LENGTH = 40;

    int  meter_id;
	short  length;
	int pad_1th;
	short pad_2th;
	int  flow_count;
	long  packet_in_count;
	long  byte_in_count;
	int  duration_sec;
	int  duration_nsec;
	List<org.openflow.protocol.interfaces.OFMeterBandStats>  band_stats;

    public OFMeterStats() {
        
    }
    
    public OFMeterStats(OFMeterStats other) {
    	this.meter_id = other.meter_id;
		this.length = other.length;
		this.flow_count = other.flow_count;
		this.packet_in_count = other.packet_in_count;
		this.byte_in_count = other.byte_in_count;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
		this.band_stats = (other.band_stats == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFMeterBandStats>();
		for ( org.openflow.protocol.interfaces.OFMeterBandStats i : other.band_stats ) { this.band_stats.add( i.dup() ); }
    }

	public int getMeterId() {
		return this.meter_id;
	}
	
	public OFMeterStats setMeterId(int meter_id) {
		this.meter_id = meter_id;
		return this;
	}
	
	public boolean isMeterIdSupported() {
		return true;
	}
			
	public short getLength() {
		return this.length;
	}
	
	public OFMeterStats setLength(short length) {
		this.length = length;
		return this;
	}
	
	public boolean isLengthSupported() {
		return true;
	}
			
	public int getFlowCount() {
		return this.flow_count;
	}
	
	public OFMeterStats setFlowCount(int flow_count) {
		this.flow_count = flow_count;
		return this;
	}
	
	public boolean isFlowCountSupported() {
		return true;
	}
			
	public long getPacketInCount() {
		return this.packet_in_count;
	}
	
	public OFMeterStats setPacketInCount(long packet_in_count) {
		this.packet_in_count = packet_in_count;
		return this;
	}
	
	public boolean isPacketInCountSupported() {
		return true;
	}
			
	public long getByteInCount() {
		return this.byte_in_count;
	}
	
	public OFMeterStats setByteInCount(long byte_in_count) {
		this.byte_in_count = byte_in_count;
		return this;
	}
	
	public boolean isByteInCountSupported() {
		return true;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFMeterStats setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
	
	public boolean isDurationSecSupported() {
		return true;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFMeterStats setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
	
	public boolean isDurationNsecSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFMeterBandStats> getBandStats() {
		return this.band_stats;
	}
	
	public OFMeterStats setBandStats(List<org.openflow.protocol.interfaces.OFMeterBandStats> band_stats) {
		this.band_stats = band_stats;
		return this;
	}
	
	public boolean isBandStatsSupported() {
		return true;
	}
			
	
	
	
	public OFMeterStats dup() {
		return new OFMeterStats(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.meter_id = data.getInt();
		this.length = data.getShort();
		this.pad_1th = data.getInt();
		this.pad_2th = data.getShort();
		this.flow_count = data.getInt();
		this.packet_in_count = data.getLong();
		this.byte_in_count = data.getLong();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
		if (this.band_stats == null) this.band_stats = new LinkedList<org.openflow.protocol.interfaces.OFMeterBandStats>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFMeterBandStats t = new OFMeterBandStats(); t.readFrom(data); this.band_stats.add(t); __cnt -= OFMeterBandStats.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.meter_id);
		data.putShort(this.length);
		data.putInt(this.pad_1th);
		data.putShort(this.pad_2th);
		data.putInt(this.flow_count);
		data.putLong(this.packet_in_count);
		data.putLong(this.byte_in_count);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
		if (this.band_stats != null ) for (org.openflow.protocol.interfaces.OFMeterBandStats t: this.band_stats) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFMeterStats-"+":meter_id=" + U32.f(meter_id) + 
		":length=" + U16.f(length) + 
		":flow_count=" + U32.f(flow_count) + 
		":packet_in_count=" + U64.f(packet_in_count) + 
		":byte_in_count=" + U64.f(byte_in_count) + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec) + 
		":band_stats=" + band_stats.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.band_stats != null ) for ( org.openflow.protocol.interfaces.OFMeterBandStats i : this.band_stats ) { len += i.computeLength(); }
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
        		
		final int prime = 1571;
		int result = super.hashCode() * prime;
		result = prime * result + (int) meter_id;
		result = prime * result + (int) length;
		result = prime * result + (int) flow_count;
		result = prime * result + (int) packet_in_count;
		result = prime * result + (int) byte_in_count;
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
		result = prime * result + ((band_stats == null)?0:band_stats.hashCode());
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
        if (!(obj instanceof OFMeterStats)) {
            return false;
        }
        OFMeterStats other = (OFMeterStats) obj;
		if ( meter_id != other.meter_id ) return false;
		if ( length != other.length ) return false;
		if ( flow_count != other.flow_count ) return false;
		if ( packet_in_count != other.packet_in_count ) return false;
		if ( byte_in_count != other.byte_in_count ) return false;
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
		if ( band_stats == null && other.band_stats != null ) { return false; }
		else if ( !band_stats.equals(other.band_stats) ) { return false; }
        return true;
    }
}
