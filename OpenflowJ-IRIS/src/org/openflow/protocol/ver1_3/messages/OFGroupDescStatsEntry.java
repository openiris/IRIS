package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFGroupDescStatsEntry   implements org.openflow.protocol.interfaces.OFGroupDescStatsEntry {
    public static int MINIMUM_LENGTH = 8;

    short  length;
	byte  type;
	byte pad_1th;
	int  group_id;
	List<org.openflow.protocol.interfaces.OFBucket>  buckets;

    public OFGroupDescStatsEntry() {
        
    }
    
    public OFGroupDescStatsEntry(OFGroupDescStatsEntry other) {
    	this.length = other.length;
		this.type = other.type;
		this.group_id = other.group_id;
		this.buckets = (other.buckets == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFBucket>();
		for ( org.openflow.protocol.interfaces.OFBucket i : other.buckets ) { this.buckets.add( new OFBucket((OFBucket)i) ); }
    }

	public short getLength() {
		return this.length;
	}
	
	public OFGroupDescStatsEntry setLength(short length) {
		this.length = length;
		return this;
	}
	
	public boolean isLengthSupported() {
		return true;
	}
			
	public byte getType() {
		return this.type;
	}
	
	public OFGroupDescStatsEntry setType(byte type) {
		this.type = type;
		return this;
	}
	
	public boolean isTypeSupported() {
		return true;
	}
			
	public int getGroupId() {
		return this.group_id;
	}
	
	public OFGroupDescStatsEntry setGroupId(int group_id) {
		this.group_id = group_id;
		return this;
	}
	
	public boolean isGroupIdSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFBucket> getBuckets() {
		return this.buckets;
	}
	
	public OFGroupDescStatsEntry setBuckets(List<org.openflow.protocol.interfaces.OFBucket> buckets) {
		this.buckets = buckets;
		return this;
	}
	
	public boolean isBucketsSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.length = data.getShort();
		this.type = data.get();
		this.pad_1th = data.get();
		this.group_id = data.getInt();
		if (this.buckets == null) this.buckets = new LinkedList<org.openflow.protocol.interfaces.OFBucket>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFBucket t = new OFBucket(); t.readFrom(data); this.buckets.add(t); __cnt -= t.getLength(); }
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.length);
		data.put(this.type);
		data.put(this.pad_1th);
		data.putInt(this.group_id);
		if (this.buckets != null ) for (org.openflow.protocol.interfaces.OFBucket t: this.buckets) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFGroupDescStatsEntry-"+":length=" + U16.f(length) + 
		":type=" + U8.f(type) + 
		":group_id=" + U32.f(group_id) + 
		":buckets=" + buckets.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.buckets != null ) for ( org.openflow.protocol.interfaces.OFBucket i : this.buckets ) { len += i.computeLength(); }
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
        		
		final int prime = 1901;
		int result = super.hashCode() * prime;
		result = prime * result + (int) length;
		result = prime * result + (int) type;
		result = prime * result + (int) group_id;
		result = prime * result + ((buckets == null)?0:buckets.hashCode());
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
        if (!(obj instanceof OFGroupDescStatsEntry)) {
            return false;
        }
        OFGroupDescStatsEntry other = (OFGroupDescStatsEntry) obj;
		if ( length != other.length ) return false;
		if ( type != other.type ) return false;
		if ( group_id != other.group_id ) return false;
		if ( buckets == null && other.buckets != null ) { return false; }
		else if ( !buckets.equals(other.buckets) ) { return false; }
        return true;
    }
}
