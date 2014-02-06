package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import java.util.List;
import org.openflow.protocol.OFPort;
import java.util.LinkedList;

public class OFPacketOut extends OFMessage implements org.openflow.protocol.interfaces.OFPacketOut {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 8;

    int  buffer_id;
	short  input_port;
	short  actions_length;
	List<org.openflow.protocol.interfaces.OFAction>  actions;
	byte[]  data;

    public OFPacketOut() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)13));
    }
    
    public OFPacketOut(OFPacketOut other) {
    	super(other);
		this.buffer_id = other.buffer_id;
		this.input_port = other.input_port;
		this.actions_length = other.actions_length;
		this.actions = (other.actions == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		for ( org.openflow.protocol.interfaces.OFAction i : other.actions ) { this.actions.add( i.dup() ); }
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getBufferId() {
		return this.buffer_id;
	}
	
	public OFPacketOut setBufferId(int buffer_id) {
		this.buffer_id = buffer_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isBufferIdSupported() {
		return true;
	}
			
	public OFPort getInputPort() {
		return OFPort.of(this.input_port);
	}
	
	public OFPacketOut setInputPort(OFPort port) {
		this.input_port = (short) port.get();
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isInputPortSupported() {
		return true;
	}
	
	public short getActionsLength() {
		return this.actions_length;
	}
	
	public OFPacketOut setActionsLength(short actions_length) {
		this.actions_length = actions_length;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isActionsLengthSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFAction> getActions() {
		return this.actions;
	}
	
	public OFPacketOut setActions(List<org.openflow.protocol.interfaces.OFAction> actions) {
		this.actions = actions;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isActionsSupported() {
		return true;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFPacketOut setData(byte[] data) {
		this.data = data;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataSupported() {
		return true;
	}
			
	
	
	
	public OFPacketOut dup() {
		return new OFPacketOut(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.buffer_id = data.getInt();
		this.input_port = data.getShort();
		this.actions_length = data.getShort();
		if (this.actions == null) this.actions = new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		for (int i = 0; i < this.actions_length; ) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFAction t = OFActionType.valueOf(__t).newInstance();
		  t.readFrom(data);
		  this.actions.add(t);
		  i += t.getLength();
		}
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.buffer_id);
		data.putShort(this.input_port);
		data.putShort(this.actions_length);
		if (this.actions != null ) for (org.openflow.protocol.interfaces.OFAction t: this.actions) { t.writeTo(data); }
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFPacketOut-"+":buffer_id=" + U32.f(buffer_id) + 
		":input_port=" + U16.f(input_port) + 
		":actions_length=" + U16.f(actions_length) + 
		":actions=" + actions.toString() + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.actions != null ) for ( org.openflow.protocol.interfaces.OFAction i : this.actions ) { len += i.computeLength(); }
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
        		
		final int prime = 2741;
		int result = super.hashCode() * prime;
		result = prime * result + (int) buffer_id;
		result = prime * result + (int) input_port;
		result = prime * result + (int) actions_length;
		result = prime * result + ((actions == null)?0:actions.hashCode());
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
        if (!(obj instanceof OFPacketOut)) {
            return false;
        }
        OFPacketOut other = (OFPacketOut) obj;
		if ( buffer_id != other.buffer_id ) return false;
		if ( input_port != other.input_port ) return false;
		if ( actions_length != other.actions_length ) return false;
		if ( actions == null && other.actions != null ) { return false; }
		else if ( !actions.equals(other.actions) ) { return false; }
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
