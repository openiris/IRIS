package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import java.util.Set;

public class OFStatisticsRequest extends OFStatistics implements org.openflow.protocol.interfaces.OFStatisticsRequest {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 8;

    OFStatisticsType  statistics_type;
	short  flags;
	int pad_1th;

    public OFStatisticsRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)18));
		this.flags = 0;
    }
    
    public OFStatisticsRequest(OFStatisticsRequest other) {
    	super(other);
		this.statistics_type = other.statistics_type;
		this.flags = other.flags;
    }

	public org.openflow.protocol.interfaces.OFStatisticsType getStatisticsType() {
		return OFStatisticsType.to(this.statistics_type.getTypeValue(), this.type);
	}
	
	public OFStatisticsRequest setStatisticsType(org.openflow.protocol.interfaces.OFStatisticsType statistics_type) {
		this.statistics_type = OFStatisticsType.from(statistics_type, this.type);
		return this;
	}
	
	public OFStatisticsRequest setStatisticsType(OFStatisticsType statistics_type) {
		this.statistics_type = statistics_type;
		return this;
	}
	
	public boolean isStatisticsTypeSupported() {
		return true;
	}
	
	public short getFlagsWire() {
		return this.flags;
	}
	
	public OFStatisticsRequest setFlagsWire(short flags) {
		this.flags = flags;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> getFlags() {
		OFStatisticsRequestFlags tmp = OFStatisticsRequestFlags.of(this.flags);
		Set<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> ret = new HashSet<org.openflow.protocol.interfaces.OFStatisticsRequestFlags>();
		for ( org.openflow.protocol.interfaces.OFStatisticsRequestFlags v : org.openflow.protocol.interfaces.OFStatisticsRequestFlags.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFStatisticsRequest setFlags(Set<org.openflow.protocol.interfaces.OFStatisticsRequestFlags> values) {
		OFStatisticsRequestFlags tmp = OFStatisticsRequestFlags.of(this.flags);
		tmp.or( values );
		this.flags = tmp.get();
		return this;
	}
	
	public OFStatisticsRequest setFlags(org.openflow.protocol.interfaces.OFStatisticsRequestFlags ... values) {
		OFStatisticsRequestFlags tmp = OFStatisticsRequestFlags.of(this.flags);
		tmp.or( values );
		this.flags = tmp.get();
		return this;
	}
	
	public boolean isFlagsSupported() {
		return true;
	}
		
	
	
	
	public OFStatisticsRequest dup() {
		return new OFStatisticsRequest(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.statistics_type = OFStatisticsType.valueOf(OFStatisticsType.readFrom(data), this.type);
		this.flags = data.getShort();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.statistics_type.getTypeValue());
		data.putShort(this.flags);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsRequest-"+":statistics_type=" + statistics_type.toString() + 
		":flags=" + U16.f(flags);
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
        		
		final int prime = 1879;
		int result = super.hashCode() * prime;
		result = prime * result + ((statistics_type == null)?0:statistics_type.hashCode());
		result = prime * result + (int) flags;
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
        if (!(obj instanceof OFStatisticsRequest)) {
            return false;
        }
        OFStatisticsRequest other = (OFStatisticsRequest) obj;
		if ( statistics_type == null && other.statistics_type != null ) { return false; }
		else if ( !statistics_type.equals(other.statistics_type) ) { return false; }
		if ( flags != other.flags ) return false;
        return true;
    }
}
