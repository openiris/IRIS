package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFFlowMod extends OFMessage  {
    public static int MINIMUM_LENGTH = 52;

    long  cookie;
	long  cookie_mask;
	byte  table_id;
	OFFlowModCommand  _command;
	short  idle_timeout;
	short  hard_timeout;
	short  priority;
	int  buffer_id;
	int  out_port;
	int  out_group;
	OFFlowModFlags  flags;
	short pad_1th;
	OFMatch  match;
	List<OFInstruction>  instructions;

    public OFFlowMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)14));
		this.match = new OFMatch();
		this.instructions = new LinkedList<OFInstruction>();
    }
    
    public OFFlowMod(OFFlowMod other) {
    	super(other);
		this.cookie = other.cookie;
		this.cookie_mask = other.cookie_mask;
		this.table_id = other.table_id;
		this._command = other._command;
		this.idle_timeout = other.idle_timeout;
		this.hard_timeout = other.hard_timeout;
		this.priority = other.priority;
		this.buffer_id = other.buffer_id;
		this.out_port = other.out_port;
		this.out_group = other.out_group;
		this.flags = other.flags;
		this.match = new OFMatch(other.match);
		this.instructions = (other.instructions == null)? null: new LinkedList<OFInstruction>();
		for ( OFInstruction i : other.instructions ) { this.instructions.add( new OFInstruction(i) ); }
    }

	public long getCookie() {
		return this.cookie;
	}
	
	public OFFlowMod setCookie(long cookie) {
		this.cookie = cookie;
		return this;
	}
			
	public long getCookieMask() {
		return this.cookie_mask;
	}
	
	public OFFlowMod setCookieMask(long cookie_mask) {
		this.cookie_mask = cookie_mask;
		return this;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFFlowMod setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public OFFlowModCommand getCommand() {
		return this._command;
	}
	
	public OFFlowMod setCommand(OFFlowModCommand _command) {
		this._command = _command;
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
			
	public int getOutPort() {
		return this.out_port;
	}
	
	public OFFlowMod setOutPort(int out_port) {
		this.out_port = out_port;
		return this;
	}
			
	public int getOutGroup() {
		return this.out_group;
	}
	
	public OFFlowMod setOutGroup(int out_group) {
		this.out_group = out_group;
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
	public OFMatch getMatch() {
		return this.match;
	}
	
	public OFFlowMod setMatch(OFMatch match) {
		this.match = match;
		return this;
	}
			
	public List<OFInstruction> getInstructions() {
		return this.instructions;
	}
	
	public OFFlowMod setInstructions(List<OFInstruction> instructions) {
		this.instructions = instructions;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.cookie = data.getLong();
		this.cookie_mask = data.getLong();
		this.table_id = data.get();
		this._command = OFFlowModCommand.valueOf(OFFlowModCommand.readFrom(data));
		this.idle_timeout = data.getShort();
		this.hard_timeout = data.getShort();
		this.priority = data.getShort();
		this.buffer_id = data.getInt();
		this.out_port = data.getInt();
		this.out_group = data.getInt();
		if (this.flags == null) this.flags = new OFFlowModFlags();
		this.flags.setValue( OFFlowModFlags.readFrom(data) );
		this.pad_1th = data.getShort();
		if (this.match == null) this.match = new OFMatch();
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
    	super.writeTo(data);
        data.putLong(this.cookie);
		data.putLong(this.cookie_mask);
		data.put(this.table_id);
		data.put(this._command.getValue());
		data.putShort(this.idle_timeout);
		data.putShort(this.hard_timeout);
		data.putShort(this.priority);
		data.putInt(this.buffer_id);
		data.putInt(this.out_port);
		data.putInt(this.out_group);
		data.putShort(this.flags.getValue());
		data.putShort(this.pad_1th);
		match.writeTo(data);
		if (this.instructions != null ) for (OFInstruction t: this.instructions) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFFlowMod-"+":cookie=" + U64.f(cookie) + 
		":cookie_mask=" + U64.f(cookie_mask) + 
		":table_id=" + U8.f(table_id) + 
		":_command=" + _command.toString() + 
		":idle_timeout=" + U16.f(idle_timeout) + 
		":hard_timeout=" + U16.f(hard_timeout) + 
		":priority=" + U16.f(priority) + 
		":buffer_id=" + U32.f(buffer_id) + 
		":out_port=" + U32.f(out_port) + 
		":out_group=" + U32.f(out_group) + 
		":flags=" + flags.toString() + 
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
        		
		final int prime = 2083;
		int result = super.hashCode() * prime;
		result = prime * result + (int) cookie;
		result = prime * result + (int) cookie_mask;
		result = prime * result + (int) table_id;
		result = prime * result + ((_command == null)?0:_command.hashCode());
		result = prime * result + (int) idle_timeout;
		result = prime * result + (int) hard_timeout;
		result = prime * result + (int) priority;
		result = prime * result + (int) buffer_id;
		result = prime * result + (int) out_port;
		result = prime * result + (int) out_group;
		result = prime * result + ((flags == null)?0:flags.hashCode());
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
        if (!(obj instanceof OFFlowMod)) {
            return false;
        }
        OFFlowMod other = (OFFlowMod) obj;
		if ( cookie != other.cookie ) return false;
		if ( cookie_mask != other.cookie_mask ) return false;
		if ( table_id != other.table_id ) return false;
		if ( _command == null && other._command != null ) { return false; }
		else if ( !_command.equals(other._command) ) { return false; }
		if ( idle_timeout != other.idle_timeout ) return false;
		if ( hard_timeout != other.hard_timeout ) return false;
		if ( priority != other.priority ) return false;
		if ( buffer_id != other.buffer_id ) return false;
		if ( out_port != other.out_port ) return false;
		if ( out_group != other.out_group ) return false;
		if ( flags == null && other.flags != null ) { return false; }
		else if ( !flags.equals(other.flags) ) { return false; }
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( instructions == null && other.instructions != null ) { return false; }
		else if ( !instructions.equals(other.instructions) ) { return false; }
        return true;
    }
}
