package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsReply extends OFStatistics  {
    public static int MINIMUM_LENGTH = 12;

    OFStatisticsType  statistics_type;
	short  flags;

    public OFStatisticsReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
    }
    
    public OFStatisticsReply(OFStatisticsReply other) {
    	super(other);
		this.statistics_type = other.statistics_type;
		this.flags = other.flags;
    }

	public OFStatisticsType getStatisticsType() {
		return this.statistics_type;
	}
	
	public OFStatisticsReply setStatisticsType(OFStatisticsType statistics_type) {
		this.statistics_type = statistics_type;
		return this;
	}
			
	public short getFlags() {
		return this.flags;
	}
	
	public OFStatisticsReply setFlags(short flags) {
		this.flags = flags;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.statistics_type = OFStatisticsType.valueOf(OFStatisticsType.readFrom(data), super.getType());
		this.flags = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.statistics_type.getTypeValue());
		data.putShort(this.flags);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsReply-"+":statistics_type=" + statistics_type.toString() + 
		":flags=" + U16.f(flags);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
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
        		
		final int prime = 2657;
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
        if (!(obj instanceof OFStatisticsReply)) {
            return false;
        }
        OFStatisticsReply other = (OFStatisticsReply) obj;
		if ( statistics_type == null && other.statistics_type != null ) { return false; }
		else if ( !statistics_type.equals(other.statistics_type) ) { return false; }
		if ( flags != other.flags ) return false;
        return true;
    }
}
