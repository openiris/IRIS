package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_0.types.*;

public class OFFlowMod extends OFMessage  {
    public static int MINIMUM_LENGTH = 72;

    OFMatch  match;
	long  cookie;
	OFFlowModCommand  command;
	short  idle_timeout;
	short  hard_timeout;
	short  priority;
	int  buffer_id;
	short  out_port;
	OFFlowModFlags  flags;
	List<OFAction>  actions;

    public OFFlowMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)14));
		this.match = new OFMatch();
		this.actions = new LinkedList<OFAction>();
    }
    
    public OFFlowMod(OFFlowMod other) {
    	super(other);
		this.match = new OFMatch(other.match);
		this.cookie = other.cookie;
		this.command = other.command;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.priority = other.priority;
		this.buffer_id = other.buffer_id;
		this.out_port = other.out_port;
		this.flags = other.flags;
		this.actions = (other.actions == null)? null: new LinkedList<OFAction>();
		for ( OFAction i : other.actions ) { this.actions.add( new OFAction(i) ); }
    }

	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowMod setMatch(OFMatch match) {
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
			
	public OFFlowModCommand getCommand() {
		return this.command;
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
			
	public short getOutPort() {
		return this.out_port;
	}
	
	public OFFlowMod setOutPort(short out_port) {
		this.out_port = out_port;
		return this;
	}
			
	public short getFlags() {
		return this.flags.getValue();
	}
	
	public OFFlowMod setFlags(short flags) {
		if (this.flags == null) this.flags = new OFFlowModFlags();
		this.flags.setValue( flags );
		return this;
	}
	public List<OFAction> getActions() {
		return this.actions;
	}
	
	public OFFlowMod setActions(List<OFAction> actions) {
		this.actions = actions;
		return this;
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
		if (this.flags == null) this.flags = new OFFlowModFlags();
		this.flags.setValue( OFFlowModFlags.readFrom(data) );
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
    	super.writeTo(data);
        match.writeTo(data);
		data.putLong(this.cookie);
		data.putShort(this.command.getValue());
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putShort(this.priority);
		data.putInt(this.buffer_id);
		data.putShort(this.out_port);
		data.putShort(this.flags.getValue());
		if (this.actions != null ) for (OFAction t: this.actions) { t.writeTo(data); }
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
		":flags=" + flags.toString() + 
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
		result = prime * result + ((flags == null)?0:flags.hashCode());
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
		if ( flags == null && other.flags != null ) { return false; }
		else if ( !flags.equals(other.flags) ) { return false; }
		if ( actions == null && other.actions != null ) { return false; }
		else if ( !actions.equals(other.actions) ) { return false; }
        return true;
    }
}
