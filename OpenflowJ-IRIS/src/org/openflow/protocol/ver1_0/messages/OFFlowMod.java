package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.util.OFPort;
import java.util.LinkedList;
import java.util.Set;
import org.openflow.protocol.ver1_0.types.*;
import java.util.HashSet;
import java.util.List;

public class OFFlowMod extends OFMessage implements org.openflow.protocol.interfaces.OFFlowMod {
    public static int MINIMUM_LENGTH = 72;

    org.openflow.protocol.interfaces.OFMatch  match;
	long  cookie;
	OFFlowModCommand  command;
	short  idle_timeout;
	short  hard_timeout;
	short  priority;
	int  buffer_id;
	short  out_port;
	short  flags;
	List<org.openflow.protocol.interfaces.OFAction>  actions;

    public OFFlowMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)14));
    }
    
    public OFFlowMod(OFFlowMod other) {
    	super(other);
		this.match = new OFMatch((OFMatch)other.match);
		this.cookie = other.cookie;
		this.command = other.command;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.priority = other.priority;
		this.buffer_id = other.buffer_id;
		this.out_port = other.out_port;
		this.flags = other.flags;
		this.actions = (other.actions == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		for ( org.openflow.protocol.interfaces.OFAction i : other.actions ) { this.actions.add( new OFAction((OFAction)i) ); }
    }

	public org.openflow.protocol.interfaces.OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowMod setMatch(org.openflow.protocol.interfaces.OFMatch match) {
		this.match = match;
		return this;
	}
			
	public long getCookie() {
		return this.cookie;
	}
	
	public OFFlowMod setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
			

	public org.openflow.protocol.interfaces.OFFlowModCommand getCommand() {
		return OFFlowModCommand.to(this.command);
	}
	
	public OFFlowMod setCommand(org.openflow.protocol.interfaces.OFFlowModCommand command) {
		this.command = OFFlowModCommand.from(command);
		return this;
	}
	
	public OFFlowMod setCommand(OFFlowModCommand command) {
		this.command = command;
		return this;
	}
	
	public short getIdleTimeout() {
		return this.idle_timeout;
	}
	
	public OFFlowMod setIdleTimeout(short idle_timeout) {
		this.idle_timeout = idle_timeout;
		return this;
	}
			
	public short getHardTimeout() {
		return this.hard_timeout;
	}
	
	public OFFlowMod setHardTimeout(short hard_timeout) {
		this.hard_timeout = hard_timeout;
		return this;
	}
			
	public short getPriority() {
		return this.priority;
	}
	
	public OFFlowMod setPriority(short priority) {
		this.priority = priority;
		return this;
	}
			
	public int getBufferId() {
		return this.buffer_id;
	}
	
	public OFFlowMod setBufferId(int buffer_id) {
		this.buffer_id = buffer_id;
		return this;
	}
			
	public OFPort getOutPort() {
		return new OFPort(this.out_port);
	}
	
	public OFFlowMod setOutPort(OFPort port) {
		this.out_port = (short) port.get();
		return this;
	}
	
	public short getFlagsWire() {
		return this.flags;
	}
	
	public OFFlowMod setFlagsWire(short flags) {
		this.flags = flags;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFFlowModFlags> getFlags() {
		OFFlowModFlags tmp = OFFlowModFlags.of(this.flags);
		Set<org.openflow.protocol.interfaces.OFFlowModFlags> ret = new HashSet<org.openflow.protocol.interfaces.OFFlowModFlags>();
		for ( org.openflow.protocol.interfaces.OFFlowModFlags v : org.openflow.protocol.interfaces.OFFlowModFlags.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFFlowMod setFlags(Set<org.openflow.protocol.interfaces.OFFlowModFlags> values) {
		OFFlowModFlags tmp = OFFlowModFlags.of(this.flags);
		tmp.and( values );
		this.flags = tmp.get();
		return this;
	}
		
	public List<org.openflow.protocol.interfaces.OFAction> getActions() {
		return this.actions;
	}
	
	public OFFlowMod setActions(List<org.openflow.protocol.interfaces.OFAction> actions) {
		this.actions = actions;
		return this;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public long getCookieMask() {
		throw new UnsupportedOperationException("public long getCookieMask() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFFlowMod setCookieMask(long value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFFlowMod setCookieMask(long value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getTableId() {
		throw new UnsupportedOperationException("public byte getTableId() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFFlowMod setTableId(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFFlowMod setTableId(byte value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getOutGroup() {
		throw new UnsupportedOperationException("public int getOutGroup() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFFlowMod setOutGroup(int value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFFlowMod setOutGroup(int value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public List<org.openflow.protocol.interfaces.OFInstruction> getInstructions() {
		throw new UnsupportedOperationException("public List<org.openflow.protocol.interfaces.OFInstruction> getInstructions() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFFlowMod setInstructions(List<org.openflow.protocol.interfaces.OFInstruction> value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFFlowMod setInstructions(List<org.openflow.protocol.interfaces.OFInstruction> value) is not supported operation");
	}
	
	
	
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.cookie = data.getLong();
		this.command = OFFlowModCommand.valueOf(OFFlowModCommand.readFrom(data));
		this.idle_timeout = data.getShort();
		this.hard_timeout = data.getShort();
		this.priority = data.getShort();
		this.buffer_id = data.getInt();
		this.out_port = data.getShort();
		this.flags = data.getShort();
		if (this.actions == null) this.actions = new LinkedList<org.openflow.protocol.interfaces.OFAction>();
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
    	super.writeTo(data);
        match.writeTo(data);
		data.putLong(this.cookie);
		data.putShort(this.command.getValue());
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putShort(this.priority);
		data.putInt(this.buffer_id);
		data.putShort(this.out_port);
		data.putShort(this.flags);
		if (this.actions != null ) for (org.openflow.protocol.interfaces.OFAction t: this.actions) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFFlowMod-"+":match=" + match.toString() + 
		":cookie=" + U64.f(cookie) + 
		":command=" + command.toString() + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":hard_timeout=" + U16.f(hard_timeout) + 
		":priority=" + U16.f(priority) + 
		":buffer_id=" + U32.f(buffer_id) + 
		":out_port=" + U16.f(out_port) + 
		":flags=" + U16.f(flags) + 
		":actions=" + actions.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.actions != null ) for ( org.openflow.protocol.interfaces.OFAction i : this.actions ) { len += i.computeLength(); }
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
        		
		final int prime = 2729;
		int result = super.hashCode() * prime;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + (int) cookie;
		result = prime * result + ((command == null)?0:command.hashCode());
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) hard_timeout;
		result = prime * result + (int) priority;
		result = prime * result + (int) buffer_id;
		result = prime * result + (int) out_port;
		result = prime * result + (int) flags;
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
        if (!(obj instanceof OFFlowMod)) {
            return false;
        }
        OFFlowMod other = (OFFlowMod) obj;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( cookie != other.cookie ) return false;
		if ( command == null && other.command != null ) { return false; }
		else if ( !command.equals(other.command) ) { return false; }
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( hard_timeout != other.hard_timeout ) return false;
		if ( priority != other.priority ) return false;
		if ( buffer_id != other.buffer_id ) return false;
		if ( out_port != other.out_port ) return false;
		if ( flags != other.flags ) return false;
		if ( actions == null && other.actions != null ) { return false; }
		else if ( !actions.equals(other.actions) ) { return false; }
        return true;
    }
}
