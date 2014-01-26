package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.OFPort;
import org.openflow.protocol.ver1_0.types.*;

public class OFPacketIn extends OFMessage implements org.openflow.protocol.interfaces.OFPacketIn {
    public static int MINIMUM_LENGTH = 18;

    int  buffer_id;
	short  total_length;
	short  input_port;
	OFPacketInReason  reason;
	byte pad_1th;
	byte[]  data;

    public OFPacketIn() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)10));
    }
    
    public OFPacketIn(OFPacketIn other) {
    	super(other);
		this.buffer_id = other.buffer_id;
		this.total_length = other.total_length;
		this.input_port = other.input_port;
		this.reason = other.reason;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getBufferId() {
		return this.buffer_id;
	}
	
	public OFPacketIn setBufferId(int buffer_id) {
		this.buffer_id = buffer_id;
		return this;
	}
	
	public boolean isBufferIdSupported() {
		return true;
	}
			
	public short getTotalLength() {
		return this.total_length;
	}
	
	public OFPacketIn setTotalLength(short total_length) {
		this.total_length = total_length;
		return this;
	}
	
	public boolean isTotalLengthSupported() {
		return true;
	}
			
	public OFPort getInputPort() {
		return new OFPort(this.input_port);
	}
	
	public OFPacketIn setInputPort(OFPort port) {
		this.input_port = (short) port.get();
		return this;
	}
	
	public boolean isInputPortSupported() {
		return true;
	}
	

	public org.openflow.protocol.interfaces.OFPacketInReason getReason() {
		return OFPacketInReason.to(this.reason);
	}
	
	public OFPacketIn setReason(org.openflow.protocol.interfaces.OFPacketInReason reason) {
		this.reason = OFPacketInReason.from(reason);
		return this;
	}
	
	public OFPacketIn setReason(OFPacketInReason reason) {
		this.reason = reason;
		return this;
	}
	
	public boolean isReasonSupported() {
		return true;
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public OFPacketIn setData(byte[] data) {
		this.data = data;
		return this;
	}
	
	public boolean isDataSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPacketIn setTableId(byte value) {
		throw new UnsupportedOperationException("setTableId is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getTableId() {
		throw new UnsupportedOperationException("getTableId is not supported operation");
	}
	
	public boolean isTableIdSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPacketIn setCookie(long value) {
		throw new UnsupportedOperationException("setCookie is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public long getCookie() {
		throw new UnsupportedOperationException("getCookie is not supported operation");
	}
	
	public boolean isCookieSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPacketIn setMatch(org.openflow.protocol.interfaces.OFMatch value) {
		throw new UnsupportedOperationException("setMatch is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch getMatch() {
		throw new UnsupportedOperationException("getMatch is not supported operation");
	}
	
	public boolean isMatchSupported() {
		return false;
	}
	
	
	
	
	public OFPacketIn dup() {
		return new OFPacketIn(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.buffer_id = data.getInt();
		this.total_length = data.getShort();
		this.input_port = data.getShort();
		this.reason = OFPacketInReason.valueOf(OFPacketInReason.readFrom(data));
		this.pad_1th = data.get();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.buffer_id);
		data.putShort(this.total_length);
		data.putShort(this.input_port);
		data.put(this.reason.getValue());
		data.put(this.pad_1th);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFPacketIn-"+":buffer_id=" + U32.f(buffer_id) + 
		":total_length=" + U16.f(total_length) + 
		":input_port=" + U16.f(input_port) + 
		":reason=" + reason.toString() + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 2857;
		int result = super.hashCode() * prime;
		result = prime * result + (int) buffer_id;
		result = prime * result + (int) total_length;
		result = prime * result + (int) input_port;
		result = prime * result + ((reason == null)?0:reason.hashCode());
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
		if ( input_port != other.input_port ) return false;
		if ( reason == null && other.reason != null ) { return false; }
		else if ( !reason.equals(other.reason) ) { return false; }
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
