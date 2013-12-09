package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsAggregateRequest extends OFStatisticsRequest  {
    public static int MINIMUM_LENGTH = 56;

    OFMatch  match;
	byte  table_id;
	byte pad_1th;
	short  out_port;

    public OFStatisticsAggregateRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)16));
		setStatisticsType(OFStatisticsType.valueOf((short)2, getType()));
		this.match = new OFMatch();
    }
    
    public OFStatisticsAggregateRequest(OFStatisticsAggregateRequest other) {
    	super(other);
		this.match = new OFMatch(other.match);
		this.table_id = other.table_id;
		this.out_port = other.out_port;
    }

	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFStatisticsAggregateRequest setMatch(OFMatch match) {
		this.match = match;
		return this;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFStatisticsAggregateRequest setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public short getOutPort() {
		return this.out_port;
	}
	
	public OFStatisticsAggregateRequest setOutPort(short out_port) {
		this.out_port = out_port;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.table_id = data.get();
		this.pad_1th = data.get();
		this.out_port = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        match.writeTo(data);
		data.put(this.table_id);
		data.put(this.pad_1th);
		data.putShort(this.out_port);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsAggregateRequest-"+":match=" + match.toString() + 
		":table_id=" + U8.f(table_id) + 
		":out_port=" + U16.f(out_port);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	len += match.lengthDiff();
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
        		
		final int prime = 2609;
		int result = super.hashCode() * prime;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + (int) table_id;
		result = prime * result + (int) out_port;
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
        if (!(obj instanceof OFStatisticsAggregateRequest)) {
            return false;
        }
        OFStatisticsAggregateRequest other = (OFStatisticsAggregateRequest) obj;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( table_id != other.table_id ) return false;
		if ( out_port != other.out_port ) return false;
        return true;
    }
}
