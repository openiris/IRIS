package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFFlowRemoved extends OFMessage  {
    public static int MINIMUM_LENGTH = 88;

    OFMatch  match;
	long  cookie;
	short  priority;
	OFFlowRemovedReason  reason;
	byte pad_1th;
	int  duration_sec;
	int  duration_nsec;
	short  idle_timeout;
	short pad_2th;
	long  packet_count;
	long  byte_count;

    public OFFlowRemoved() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)11));
		this.match = new OFMatch();
    }
    
    public OFFlowRemoved(OFFlowRemoved other) {
    	super(other);
		this.match = new OFMatch(other.match);
		this.cookie = other.cookie;
		this.priority = other.priority;
		this.reason = other.reason;
		this.duration_sec = other.duration_sec;
		this.duration_nsec = other.duration_nsec;
		this.idle_timeout = other.idle_timeout;
		this.packet_count = other.packet_count;
		this.byte_count = other.byte_count;
    }

	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowRemoved setMatch(OFMatch match) {
		this.match = match;
		return this;
	}
			
	public long getCookie() {
		return this.cookie;
	}
	
	public OFFlowRemoved setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
			
	public short getPriority() {
		return this.priority;
	}
	
	public OFFlowRemoved setPriority(short priority) {
		this.priority = priority;
		return this;
	}
			
	public OFFlowRemovedReason getReason() {
		return this.reason;
	}
	
	public OFFlowRemoved setReason(OFFlowRemovedReason reason) {
		this.reason = reason;
		return this;
	}
			
	public int getDurationSec() {
		return this.duration_sec;
	}
	
	public OFFlowRemoved setDurationSec(int duration_sec) {
		this.duration_sec = duration_sec;
		return this;
	}
			
	public int getDurationNsec() {
		return this.duration_nsec;
	}
	
	public OFFlowRemoved setDurationNsec(int duration_nsec) {
		this.duration_nsec = duration_nsec;
		return this;
	}
			
	public short getIdleTimeout() {
		return this.idle_timeout;
	}
	
	public OFFlowRemoved setIdleTimeout(short idle_timeout) {
		this.idle_timeout = idle_timeout;
		return this;
	}
			
	public long getPacketCount() {
		return this.packet_count;
	}
	
	public OFFlowRemoved setPacketCount(long packet_count) {
		this.packet_count = packet_count;
		return this;
	}
			
	public long getByteCount() {
		return this.byte_count;
	}
	
	public OFFlowRemoved setByteCount(long byte_count) {
		this.byte_count = byte_count;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.cookie = data.getLong();
		this.priority = data.getShort();
		this.reason = OFFlowRemovedReason.valueOf(OFFlowRemovedReason.readFrom(data));
		this.pad_1th = data.get();
		this.duration_sec = data.getInt();
		this.duration_nsec = data.getInt();
		this.idle_timeout = data.getShort();
		this.pad_2th = data.getShort();
		this.packet_count = data.getLong();
		this.byte_count = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        match.writeTo(data);
		data.putLong(this.cookie);
		data.putShort(this.priority);
		data.put(this.reason.getValue());
		data.put(this.pad_1th);
		data.putInt(this.duration_sec);
		data.putInt(this.duration_nsec);
		data.putShort(this.idle_timeout);
		data.putShort(this.pad_2th);
		data.putLong(this.packet_count);
		data.putLong(this.byte_count);
    }

    public String toString() {
        return super.toString() +  ":OFFlowRemoved-"+":match=" + match.toString() + 
		":cookie=" + U64.f(cookie) + 
		":priority=" + U16.f(priority) + 
		":reason=" + reason.toString() + 
		":duration_sec=" + U32.f(duration_sec) + 
		":duration_nsec=" + U32.f(duration_nsec) + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":packet_count=" + U64.f(packet_count) + 
		":byte_count=" + U64.f(byte_count);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2693;
		int result = super.hashCode() * prime;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + (int) cookie;
		result = prime * result + (int) priority;
		result = prime * result + ((reason == null)?0:reason.hashCode());
		result = prime * result + (int) duration_sec;
		result = prime * result + (int) duration_nsec;
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) packet_count;
		result = prime * result + (int) byte_count;
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
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( cookie != other.cookie ) return false;
		if ( priority != other.priority ) return false;
		if ( reason == null && other.reason != null ) { return false; }
		else if ( !reason.equals(other.reason) ) { return false; }
		if ( duration_sec != other.duration_sec ) return false;
		if ( duration_nsec != other.duration_nsec ) return false;
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( packet_count != other.packet_count ) return false;
		if ( byte_count != other.byte_count ) return false;
        return true;
    }
}
