package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFTableStatsEntry    {
    public static int MINIMUM_LENGTH = 64;

    byte  table_id;
	short pad_1th;
	byte pad_2th;
	byte[]  name;
	OFFlowWildcards  wildcards;
	int  max_entries;
	int  active_count;
	long  lookup_count;
	long  matched_count;

    public OFTableStatsEntry() {
        name = new byte[32];
    }
    
    public OFTableStatsEntry(OFTableStatsEntry other) {
    	this.table_id = other.table_id;
		if (other.name != null) { this.name = java.util.Arrays.copyOf(other.name, other.name.length); }
		this.wildcards = other.wildcards;
		this.max_entries = other.max_entries;
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
			
	public byte[] getName() {
		return this.name;
	}
	
	public OFTableStatsEntry setName(byte[] name) {
		this.name = name;
		return this;
	}
			
	public int getWildcards() {
		return this.wildcards.getValue();
	}
	
	public OFTableStatsEntry setWildcards(int wildcards) {
		if (this.wildcards == null) this.wildcards = new OFFlowWildcards();
		this.wildcards.setValue( wildcards );
		return this;
	}
	public int getMaxEntries() {
		return this.max_entries;
	}
	
	public OFTableStatsEntry setMaxEntries(int max_entries) {
		this.max_entries = max_entries;
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
		if ( this.name == null ) this.name = new byte[32];
		data.get(this.name);
		if (this.wildcards == null) this.wildcards = new OFFlowWildcards();
		this.wildcards.setValue( OFFlowWildcards.readFrom(data) );
		this.max_entries = data.getInt();
		this.active_count = data.getInt();
		this.lookup_count = data.getLong();
		this.matched_count = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.put(this.table_id);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
		if ( this.name != null ) { data.put(this.name); }
		data.putInt(this.wildcards.getValue());
		data.putInt(this.max_entries);
		data.putInt(this.active_count);
		data.putLong(this.lookup_count);
		data.putLong(this.matched_count);
    }

    public String toString() {
        return  ":OFTableStatsEntry-"+":table_id=" + U8.f(table_id) + 
		":name=" + java.util.Arrays.toString(name) + 
		":wildcards=" + wildcards.toString() + 
		":max_entries=" + U32.f(max_entries) + 
		":active_count=" + U32.f(active_count) + 
		":lookup_count=" + U64.f(lookup_count) + 
		":matched_count=" + U64.f(matched_count);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.name != null ) { len += this.name.length; } 
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
        		
		final int prime = 2683;
		int result = super.hashCode() * prime;
		result = prime * result + (int) table_id;
		result = prime * result + ((name == null)?0:java.util.Arrays.hashCode(name));
		result = prime * result + ((wildcards == null)?0:wildcards.hashCode());
		result = prime * result + (int) max_entries;
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
		if ( name == null && other.name != null ) { return false; }
		else if ( !java.util.Arrays.equals(name, other.name) ) { return false; }
		if ( wildcards == null && other.wildcards != null ) { return false; }
		else if ( !wildcards.equals(other.wildcards) ) { return false; }
		if ( max_entries != other.max_entries ) return false;
		if ( active_count != other.active_count ) return false;
		if ( lookup_count != other.lookup_count ) return false;
		if ( matched_count != other.matched_count ) return false;
        return true;
    }
}
