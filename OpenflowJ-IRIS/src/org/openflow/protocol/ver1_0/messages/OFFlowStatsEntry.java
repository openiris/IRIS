package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_0.types.*;

public class OFFlowStatsEntry    {
    public static int MINIMUM_LENGTH = 88;

    short  length;
	byte  table_id;
	byte pad_1th;
	OFMatch  match;
	int  duration_sec;
	int  duration_nsec;
	short  priority;
	short  idle_timeout;
	short  hard_timeout;
	int pad_2th;
	short pad_3th;
	long  cookie;
	long  packet_count;
	long  byte_count;
	List<OFAction>  actions;

    public OFFlowStatsEntry() {
        this.match = new OFMatch();
		this.actions = new LinkedList<OFAction>();
    }
    
    public OFFlowStatsEntry(OFFlowStatsEntry other) {
    	this.length = other.length;
		this.table_id = other.table_id;
		this.match = new OFMatch(other.match);
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
		this.priority = other.priority;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.cookie = other.cookie;
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
		this.actions = (other.actions == null)? null: new LinkedList<OFAction>();
		for ( OFAction i : other.actions ) { this.actions.add( new OFAction(i) ); }
    }

	public short getLength() {
		return this.length;
	}
	
	public OFFlowStatsEntry setLength(short length) {
		this.length = length;
		return this;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFFlowStatsEntry setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowStatsEntry setMatch(OFMatch match) {
		this.match = match;
		return this;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFFlowStatsEntry setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFFlowStatsEntry setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
			
	public short getPriority() {
		return this.priority;
	}
	
	public OFFlowStatsEntry setPriority(short priority) {
		this.priority = priority;
		return this;
	}
			
	public short getIdleTimeout() {
		return this.idle_timeout;
	}
	
	public OFFlowStatsEntry setIdleTimeout(short idle_timeout) {
		this.idle_timeout = idle_timeout;
		return this;
	}
			
	public short getHardTimeout() {
		return this.hard_timeout;
	}
	
	public OFFlowStatsEntry setHardTimeout(short hard_timeout) {
		this.hard_timeout = hard_timeout;
		return this;
	}
			
	public long getCookie() {
		return this.cookie;
	}
	
	public OFFlowStatsEntry setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
			
	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFFlowStatsEntry setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFFlowStatsEntry setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
			
	public List<OFAction> getActions() {
		return this.actions;
	}
	
	public OFFlowStatsEntry setActions(List<OFAction> actions) {
		this.actions = actions;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.length = data.getShort();
		this.table_id = data.get();
		this.pad_1th = data.get();
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
		this.priority = data.getShort();
		this.idle_timeout = data.getShort();
		this.hard_timeout = data.getShort();
		this.pad_2th = data.getInt();
		this.pad_3th = data.getShort();
		this.cookie = data.getLong();
		this.packet_count = data.getLong();
		this.byte_count = data.getLong();
		if (this.actions == null) this.actions = new LinkedList<OFAction>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFAction t = OFActionType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.actions.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.length);
		data.put(this.table_id);
		data.put(this.pad_1th);
		match.writeTo(data);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
		data.putShort(this.priority);
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putInt(this.pad_2th);
		data.putShort(this.pad_3th);
		data.putLong(this.cookie);
		data.putLong(this.packet_count);
		data.putLong(this.byte_count);
		if (this.actions != null ) for (OFAction t: this.actions) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFFlowStatsEntry-"+":length=" + U16.f(length) + 
		":table_id=" + U8.f(table_id) + 
		":match=" + match.toString() + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec) + 
		":priority=" + U16.f(priority) + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":hard_timeout=" + U16.f(hard_timeout) + 
		":cookie=" + U64.f(cookie) + 
		":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count) + 
		":actions=" + actions.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	len += match.lengthDiff();
		for ( OFAction i : this.actions ) { len += i.computeLength(); }
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
        		
		final int prime = 2687;
		int result = super.hashCode() * prime;
		result = prime * result + (int) length;
		result = prime * result + (int) table_id;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
		result = prime * result + (int) priority;
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) hard_timeout;
		result = prime * result + (int) cookie;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
		result = prime * result + ((actions == null)?0:actions.hashCode());
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
        if (!(obj instanceof OFFlowStatsEntry)) {
            return false;
        }
        OFFlowStatsEntry other = (OFFlowStatsEntry) obj;
		if ( length != other.length ) return false;
		if ( table_id != other.table_id ) return false;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
		if ( priority != other.priority ) return false;
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( hard_timeout != other.hard_timeout ) return false;
		if ( cookie != other.cookie ) return false;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
		if ( actions == null && other.actions != null ) { return false; }
		else if ( !actions.equals(other.actions) ) { return false; }
        return true;
    }
}
