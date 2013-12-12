package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFFlowStatsEntry    {
    public static int MINIMUM_LENGTH = 52;

    short  length;
	byte  table_id;
	byte pad_1th;
	int  duration_sec;
	int  duration_nsec;
	short  priority;
	short  idle_timeout;
	short  hard_timeout;
	short  flags;
	int pad_2th;
	long  cookie;
	long  packet_count;
	long  byte_count;
	OFMatchOxm  match;
	List<OFInstruction>  instructions;

    public OFFlowStatsEntry() {
        this.match = new OFMatchOxm();
		this.instructions = new LinkedList<OFInstruction>();
    }
    
    public OFFlowStatsEntry(OFFlowStatsEntry other) {
    	this.length = other.length;
		this.table_id = other.table_id;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
		this.priority = other.priority;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.flags = other.flags;
		this.cookie = other.cookie;
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
		this.match = new OFMatchOxm(other.match);
		this.instructions = (other.instructions == null)? null: new LinkedList<OFInstruction>();
		for ( OFInstruction i : other.instructions ) { this.instructions.add( new OFInstruction(i) ); }
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
			
	public short getFlags() {
		return this.flags;
	}
	
	public OFFlowStatsEntry setFlags(short flags) {
		this.flags = flags;
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
			
	public OFMatchOxm getMatch() {
		return this.match;
	}
	
	public OFFlowStatsEntry setMatch(OFMatchOxm match) {
		this.match = match;
		return this;
	}
			
	public List<OFInstruction> getInstructions() {
		return this.instructions;
	}
	
	public OFFlowStatsEntry setInstructions(List<OFInstruction> instructions) {
		this.instructions = instructions;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.length = data.getShort();
		this.table_id = data.get();
		this.pad_1th = data.get();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
		this.priority = data.getShort();
		this.idle_timeout = data.getShort();
		this.hard_timeout = data.getShort();
		this.flags = data.getShort();
		this.pad_2th = data.getInt();
		this.cookie = data.getLong();
		this.packet_count = data.getLong();
		this.byte_count = data.getLong();
		if (this.match == null) this.match = new OFMatchOxm();
		this.match.readFrom(data);
		if (this.instructions == null) this.instructions = new LinkedList<OFInstruction>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFInstruction t = OFInstructionType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.instructions.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.length);
		data.put(this.table_id);
		data.put(this.pad_1th);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
		data.putShort(this.priority);
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putShort(this.flags);
		data.putInt(this.pad_2th);
		data.putLong(this.cookie);
		data.putLong(this.packet_count);
		data.putLong(this.byte_count);
		match.writeTo(data);
		if (this.instructions != null ) for (OFInstruction t: this.instructions) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFFlowStatsEntry-"+":length=" + U16.f(length) + 
		":table_id=" + U8.f(table_id) + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec) + 
		":priority=" + U16.f(priority) + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":hard_timeout=" + U16.f(hard_timeout) + 
		":flags=" + U16.f(flags) + 
		":cookie=" + U64.f(cookie) + 
		":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count) + 
		":match=" + match.toString() + 
		":instructions=" + instructions.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	len += match.lengthDiff();
		for ( OFInstruction i : this.instructions ) { len += i.computeLength(); }
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
        		
		final int prime = 1951;
		int result = super.hashCode() * prime;
		result = prime * result + (int) length;
		result = prime * result + (int) table_id;
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
		result = prime * result + (int) priority;
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) hard_timeout;
		result = prime * result + (int) flags;
		result = prime * result + (int) cookie;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + ((instructions == null)?0:instructions.hashCode());
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
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
		if ( priority != other.priority ) return false;
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( hard_timeout != other.hard_timeout ) return false;
		if ( flags != other.flags ) return false;
		if ( cookie != other.cookie ) return false;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( instructions == null && other.instructions != null ) { return false; }
		else if ( !instructions.equals(other.instructions) ) { return false; }
        return true;
    }
}
