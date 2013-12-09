package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFPacketIn extends OFMessage  {
    public static int MINIMUM_LENGTH = 30;

    int  buffer_id;
	short  total_length;
	OFPacketInReason  reason;
	byte  table_id;
	long  cookie;
	OFMatch  match;
	short pad_1th;
	byte[]  data;

    public OFPacketIn() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)10));
		this.match = new OFMatch();
    }
    
    public OFPacketIn(OFPacketIn other) {
    	super(other);
		this.buffer_id = other.buffer_id;
		this.total_length = other.total_length;
		this.reason = other.reason;
		this.table_id = other.table_id;
		this.cookie = other.cookie;
		this.match = new OFMatch(other.match);
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getBufferId() {
		return this.buffer_id;
	}
	
	public OFPacketIn setBufferId(int buffer_id) {
		this.buffer_id = buffer_id;
		return this;
	}
			
	public short getTotalLength() {
		return this.total_length;
	}
	
	public OFPacketIn setTotalLength(short total_length) {
		this.total_length = total_length;
		return this;
	}
			
	public OFPacketInReason getReason() {
		return this.reason;
	}
	
	public OFPacketIn setReason(OFPacketInReason reason) {
		this.reason = reason;
		return this;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFPacketIn setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public long getCookie() {
		return this.cookie;
	}
	
	public OFPacketIn setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
			
	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFPacketIn setMatch(OFMatch match) {
		this.match = match;
		return this;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFPacketIn setData(byte[] data) {
		this.data = data;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.buffer_id = data.getInt();
		this.total_length = data.getShort();
		this.reason = OFPacketInReason.valueOf(OFPacketInReason.readFrom(data));
		this.table_id = data.get();
		this.cookie = data.getLong();
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.pad_1th = data.getShort();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.buffer_id);
		data.putShort(this.total_length);
		data.put(this.reason.getValue());
		data.put(this.table_id);
		data.putLong(this.cookie);
		match.writeTo(data);
		data.putShort(this.pad_1th);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFPacketIn-"+":buffer_id=" + U32.f(buffer_id) + 
		":total_length=" + U16.f(total_length) + 
		":reason=" + reason.toString() + 
		":table_id=" + U8.f(table_id) + 
		":cookie=" + U64.f(cookie) + 
		":match=" + match.toString() + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	len += match.lengthDiff();
		if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 2011;
		int result = super.hashCode() * prime;
		result = prime * result + (int) buffer_id;
		result = prime * result + (int) total_length;
		result = prime * result + ((reason == null)?0:reason.hashCode());
		result = prime * result + (int) table_id;
		result = prime * result + (int) cookie;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + ((data == null)?0:java.util.Arrays.hashCode(data));
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
        if (!(obj instanceof OFPacketIn)) {
            return false;
        }
        OFPacketIn other = (OFPacketIn) obj;
		if ( buffer_id != other.buffer_id ) return false;
		if ( total_length != other.total_length ) return false;
		if ( reason == null && other.reason != null ) { return false; }
		else if ( !reason.equals(other.reason) ) { return false; }
		if ( table_id != other.table_id ) return false;
		if ( cookie != other.cookie ) return false;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
