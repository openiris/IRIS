package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMeterBandStats   implements org.openflow.protocol.ver1_3.interfaces.OFMeterBandStats {
    public static int MINIMUM_LENGTH = 16;

    long  packet_band_count;
	long  byte_band_count;

    public OFMeterBandStats() {
        
    }
    
    public OFMeterBandStats(OFMeterBandStats other) {
    	this.packet_band_count = other.packet_band_count;
		this.byte_band_count = other.byte_band_count;
    }

	public long getPacketBandCount() {
		return this.packet_band_count;
	}
	
	public OFMeterBandStats setPacketBandCount(long packet_band_count) {
		this.packet_band_count = packet_band_count;
		return this;
	}
			
	public long getByteBandCount() {
		return this.byte_band_count;
	}
	
	public OFMeterBandStats setByteBandCount(long byte_band_count) {
		this.byte_band_count = byte_band_count;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.packet_band_count = data.getLong();
		this.byte_band_count = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putLong(this.packet_band_count);
		data.putLong(this.byte_band_count);
    }

    public String toString() {
        return  ":OFMeterBandStats-"+":packet_band_count=" + U64.f(packet_band_count) + 
		":byte_band_count=" + U64.f(byte_band_count);
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
        		
		final int prime = 1579;
		int result = super.hashCode() * prime;
		result = prime * result + (int) packet_band_count;
		result = prime * result + (int) byte_band_count;
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
        if (!(obj instanceof OFMeterBandStats)) {
            return false;
        }
        OFMeterBandStats other = (OFMeterBandStats) obj;
		if ( packet_band_count != other.packet_band_count ) return false;
		if ( byte_band_count != other.byte_band_count ) return false;
        return true;
    }
}
