package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMeterBand    {
    public static int MINIMUM_LENGTH = 12;

    OFMeterBandType  type;
	short  length;
	int  rate;
	int  burst_size;

    public OFMeterBand() {
        
    }
    
    public OFMeterBand(OFMeterBand other) {
    	this.type = other.type;
		this.length = other.length;
		this.rate = other.rate;
		this.burst_size = other.burst_size;
    }

	public OFMeterBandType getType() {
		return this.type;
	}
	
	public OFMeterBand setType(OFMeterBandType type) {
		this.type = type;
		return this;
	}
			
	public short getLength() {
		return this.length;
	}
	
	public OFMeterBand setLength(short length) {
		this.length = length;
		return this;
	}
			
	public int getRate() {
		return this.rate;
	}
	
	public OFMeterBand setRate(int rate) {
		this.rate = rate;
		return this;
	}
			
	public int getBurstSize() {
		return this.burst_size;
	}
	
	public OFMeterBand setBurstSize(int burst_size) {
		this.burst_size = burst_size;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.type = OFMeterBandType.valueOf(OFMeterBandType.readFrom(data));
		this.length = data.getShort();
		this.rate = data.getInt();
		this.burst_size = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.type.getTypeValue());
		data.putShort(this.length);
		data.putInt(this.rate);
		data.putInt(this.burst_size);
    }

    public String toString() {
        return  ":OFMeterBand-"+":type=" + type.toString() + 
		":length=" + U16.f(length) + 
		":rate=" + U32.f(rate) + 
		":burst_size=" + U32.f(burst_size);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1999;
		int result = super.hashCode() * prime;
		result = prime * result + ((type == null)?0:type.hashCode());
		result = prime * result + (int) length;
		result = prime * result + (int) rate;
		result = prime * result + (int) burst_size;
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
        if (!(obj instanceof OFMeterBand)) {
            return false;
        }
        OFMeterBand other = (OFMeterBand) obj;
		if ( type == null && other.type != null ) { return false; }
		else if ( !type.equals(other.type) ) { return false; }
		if ( length != other.length ) return false;
		if ( rate != other.rate ) return false;
		if ( burst_size != other.burst_size ) return false;
        return true;
    }
}
