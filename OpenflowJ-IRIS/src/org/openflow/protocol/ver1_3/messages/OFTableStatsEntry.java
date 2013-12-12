package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFTableStatsEntry    {
    public static int MINIMUM_LENGTH = 24;

    byte  table_id;
	short pad_1th;
	byte pad_2th;
	int  active_count;
	long  lookup_count;
	long  matched_count;

    public OFTableStatsEntry() {
        
    }
    
    public OFTableStatsEntry(OFTableStatsEntry other) {
    	this.table_id = other.table_id;
		this.active_count = other.active_count;
		this.lookup_count = other.lookup_count;
		this.matched_count = other.matched_count;
    }

	public byte getTableId() {
		return this.table_id;
	}
	
	public OFTableStatsEntry setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public int getActiveCount() {
		return this.active_count;
	}
	
	public OFTableStatsEntry setActiveCount(int active_count) {
		this.active_count = active_count;
		return this;
	}
			
	public long getLookupCount() {
		return this.lookup_count;
	}
	
	public OFTableStatsEntry setLookupCount(long lookup_count) {
		this.lookup_count = lookup_count;
		return this;
	}
			
	public long getMatchedCount() {
		return this.matched_count;
	}
	
	public OFTableStatsEntry setMatchedCount(long matched_count) {
		this.matched_count = matched_count;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        this.table_id = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
		this.active_count = data.getInt();
		this.lookup_count = data.getLong();
		this.matched_count = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.put(this.table_id);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
		data.putInt(this.active_count);
		data.putLong(this.lookup_count);
		data.putLong(this.matched_count);
    }

    public String toString() {
        return  ":OFTableStatsEntry-"+":table_id=" + U8.f(table_id) + 
		":active_count=" + U32.f(active_count) + 
		":lookup_count=" + U64.f(lookup_count) + 
		":matched_count=" + U64.f(matched_count);
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
        		
		final int prime = 1949;
		int result = super.hashCode() * prime;
		result = prime * result + (int) table_id;
		result = prime * result + (int) active_count;
		result = prime * result + (int) lookup_count;
		result = prime * result + (int) matched_count;
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
        if (!(obj instanceof OFTableStatsEntry)) {
            return false;
        }
        OFTableStatsEntry other = (OFTableStatsEntry) obj;
		if ( table_id != other.table_id ) return false;
		if ( active_count != other.active_count ) return false;
		if ( lookup_count != other.lookup_count ) return false;
		if ( matched_count != other.matched_count ) return false;
        return true;
    }
}
