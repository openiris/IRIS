package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFFlowRemoved extends OFMessage implements org.openflow.protocol.interfaces.OFFlowRemoved {
    public static int MINIMUM_LENGTH = 52;

    long  cookie;
	short  priority;
	OFFlowRemovedReason  reason;
	byte  table_id;
	int  duration_sec;
	int  duration_nsec;
	short  idle_timeout;
	short  hard_timeout;
	long  packet_count;
	long  byte_count;
	org.openflow.protocol.interfaces.OFMatchOxm  match;

    public OFFlowRemoved() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)11));
    }
    
    public OFFlowRemoved(OFFlowRemoved other) {
    	super(other);
		this.cookie = other.cookie;
		this.priority = other.priority;
		this.reason = other.reason;
		this.table_id = other.table_id;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
		this.match = new OFMatchOxm((OFMatchOxm)other.match);
    }

	public long getCookie() {
		return this.cookie;
	}
	
	public OFFlowRemoved setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
	
	public boolean isCookieSupported() {
		return true;
	}
			
	public short getPriority() {
		return this.priority;
	}
	
	public OFFlowRemoved setPriority(short priority) {
		this.priority = priority;
		return this;
	}
	
	public boolean isPrioritySupported() {
		return true;
	}
			

	public org.openflow.protocol.interfaces.OFFlowRemovedReason getReason() {
		return OFFlowRemovedReason.to(this.reason);
	}
	
	public OFFlowRemoved setReason(org.openflow.protocol.interfaces.OFFlowRemovedReason reason) {
		this.reason = OFFlowRemovedReason.from(reason);
		return this;
	}
	
	public OFFlowRemoved setReason(OFFlowRemovedReason reason) {
		this.reason = reason;
		return this;
	}
	
	public boolean isReasonSupported() {
		return true;
	}
	
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFFlowRemoved setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
	
	public boolean isTableIdSupported() {
		return true;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFFlowRemoved setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
	
	public boolean isDurationSecSupported() {
		return true;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFFlowRemoved setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
	
	public boolean isDurationNsecSupported() {
		return true;
	}
			
	public short getIdleTimeout() {
		return this.idle_timeout;
	}
	
	public OFFlowRemoved setIdleTimeout(short idle_timeout) {
		this.idle_timeout = idle_timeout;
		return this;
	}
	
	public boolean isIdleTimeoutSupported() {
		return true;
	}
			
	public short getHardTimeout() {
		return this.hard_timeout;
	}
	
	public OFFlowRemoved setHardTimeout(short hard_timeout) {
		this.hard_timeout = hard_timeout;
		return this;
	}
	
	public boolean isHardTimeoutSupported() {
		return true;
	}
			
	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFFlowRemoved setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
	
	public boolean isPacketCountSupported() {
		return true;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFFlowRemoved setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
	
	public boolean isByteCountSupported() {
		return true;
	}
			
	public org.openflow.protocol.interfaces.OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowRemoved setMatch(org.openflow.protocol.interfaces.OFMatch match) {
		this.match = (OFMatchOxm) match;
		return this;
	}
	
	public boolean isMatchSupported() {
		return true;
	}
			
	
	
	
	public OFFlowRemoved dup() {
		return new OFFlowRemoved(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.cookie = data.getLong();
		this.priority = data.getShort();
		this.reason = OFFlowRemovedReason.valueOf(OFFlowRemovedReason.readFrom(data));
		this.table_id = data.get();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
		this.idle_timeout = data.getShort();
		this.hard_timeout = data.getShort();
		this.packet_count = data.getLong();
		this.byte_count = data.getLong();
		if (this.match == null) this.match = new OFMatchOxm();
		this.match.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putLong(this.cookie);
		data.putShort(this.priority);
		data.put(this.reason.getValue());
		data.put(this.table_id);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putLong(this.packet_count);
		data.putLong(this.byte_count);
		match.writeTo(data);
    }

    public String toString() {
        return super.toString() +  ":OFFlowRemoved-"+":cookie=" + U64.f(cookie) + 
		":priority=" + U16.f(priority) + 
		":reason=" + reason.toString() + 
		":table_id=" + U8.f(table_id) + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec) + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":hard_timeout=" + U16.f(hard_timeout) + 
		":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count) + 
		":match=" + match.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	len += match.lengthDiff();
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
        		
		final int prime = 2003;
		int result = super.hashCode() * prime;
		result = prime * result + (int) cookie;
		result = prime * result + (int) priority;
		result = prime * result + ((reason == null)?0:reason.hashCode());
		result = prime * result + (int) table_id;
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) hard_timeout;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
		result = prime * result + ((match == null)?0:match.hashCode());
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
        if (!(obj instanceof OFFlowRemoved)) {
            return false;
        }
        OFFlowRemoved other = (OFFlowRemoved) obj;
		if ( cookie != other.cookie ) return false;
		if ( priority != other.priority ) return false;
		if ( reason == null && other.reason != null ) { return false; }
		else if ( !reason.equals(other.reason) ) { return false; }
		if ( table_id != other.table_id ) return false;
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( hard_timeout != other.hard_timeout ) return false;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
        return true;
    }
}
