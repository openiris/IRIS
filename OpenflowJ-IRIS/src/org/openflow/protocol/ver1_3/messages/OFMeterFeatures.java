package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import java.util.Set;

public class OFMeterFeatures   implements org.openflow.protocol.interfaces.OFMeterFeatures {
    public static int MINIMUM_LENGTH = 16;

    int  max_meter;
	int  band_types;
	int  capabilities;
	byte  max_bands;
	byte  max_color;
	short pad_1th;

    public OFMeterFeatures() {
        
    }
    
    public OFMeterFeatures(OFMeterFeatures other) {
    	this.max_meter = other.max_meter;
		this.band_types = other.band_types;
		this.capabilities = other.capabilities;
		this.max_bands = other.max_bands;
		this.max_color = other.max_color;
    }

	public int getMaxMeter() {
		return this.max_meter;
	}
	
	public OFMeterFeatures setMaxMeter(int max_meter) {
		this.max_meter = max_meter;
		return this;
	}
	
	public boolean isMaxMeterSupported() {
		return true;
	}
			
	public int getBandTypes() {
		return this.band_types;
	}
	
	public OFMeterFeatures setBandTypes(int band_types) {
		this.band_types = band_types;
		return this;
	}
	
	public boolean isBandTypesSupported() {
		return true;
	}
			
	public int getCapabilitiesWire() {
		return this.capabilities;
	}
	
	public OFMeterFeatures setCapabilitiesWire(int capabilities) {
		this.capabilities = capabilities;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFCapabilities> getCapabilities() {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		Set<org.openflow.protocol.interfaces.OFCapabilities> ret = new HashSet<org.openflow.protocol.interfaces.OFCapabilities>();
		for ( org.openflow.protocol.interfaces.OFCapabilities v : org.openflow.protocol.interfaces.OFCapabilities.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFMeterFeatures setCapabilities(Set<org.openflow.protocol.interfaces.OFCapabilities> values) {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		tmp.or( values );
		this.capabilities = tmp.get();
		return this;
	}
	
	public OFMeterFeatures setCapabilities(org.openflow.protocol.interfaces.OFCapabilities ... values) {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		tmp.or( values );
		this.capabilities = tmp.get();
		return this;
	}
	
	public boolean isCapabilitiesSupported() {
		return true;
	}
		
	public byte getMaxBands() {
		return this.max_bands;
	}
	
	public OFMeterFeatures setMaxBands(byte max_bands) {
		this.max_bands = max_bands;
		return this;
	}
	
	public boolean isMaxBandsSupported() {
		return true;
	}
			
	public byte getMaxColor() {
		return this.max_color;
	}
	
	public OFMeterFeatures setMaxColor(byte max_color) {
		this.max_color = max_color;
		return this;
	}
	
	public boolean isMaxColorSupported() {
		return true;
	}
			
	
	
	
	public OFMeterFeatures dup() {
		return new OFMeterFeatures(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.max_meter = data.getInt();
		this.band_types = data.getInt();
		this.capabilities = data.getInt();
		this.max_bands = data.get();
		this.max_color = data.get();
		this.pad_1th = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.max_meter);
		data.putInt(this.band_types);
		data.putInt(this.capabilities);
		data.put(this.max_bands);
		data.put(this.max_color);
		data.putShort(this.pad_1th);
    }

    public String toString() {
        return  ":OFMeterFeatures-"+":max_meter=" + U32.f(max_meter) + 
		":band_types=" + U32.f(band_types) + 
		":capabilities=" + U32.f(capabilities) + 
		":max_bands=" + U8.f(max_bands) + 
		":max_color=" + U8.f(max_color);
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
        		
		final int prime = 1663;
		int result = super.hashCode() * prime;
		result = prime * result + (int) max_meter;
		result = prime * result + (int) band_types;
		result = prime * result + (int) capabilities;
		result = prime * result + (int) max_bands;
		result = prime * result + (int) max_color;
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
        if (!(obj instanceof OFMeterFeatures)) {
            return false;
        }
        OFMeterFeatures other = (OFMeterFeatures) obj;
		if ( max_meter != other.max_meter ) return false;
		if ( band_types != other.band_types ) return false;
		if ( capabilities != other.capabilities ) return false;
		if ( max_bands != other.max_bands ) return false;
		if ( max_color != other.max_color ) return false;
        return true;
    }
}
