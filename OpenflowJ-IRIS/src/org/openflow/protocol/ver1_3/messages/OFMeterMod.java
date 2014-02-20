package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFMeterMod extends OFMessage implements org.openflow.protocol.interfaces.OFMeterMod {
    public static int MINIMUM_LENGTH = 16;
    public static int CORE_LENGTH = 8;

    OFMeterModCommand  command;
	short  flags;
	int  meter_id;
	List<org.openflow.protocol.interfaces.OFMeterBand>  meters;

    public OFMeterMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)29));
    }
    
    public OFMeterMod(OFMeterMod other) {
    	super(other);
		this.command = other.command;
		this.flags = other.flags;
		this.meter_id = other.meter_id;
		this.meters = (other.meters == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFMeterBand>();
		for ( org.openflow.protocol.interfaces.OFMeterBand i : other.meters ) { this.meters.add( i.dup() ); }
    }

	public org.openflow.protocol.interfaces.OFMeterModCommand getCommand() {
		return OFMeterModCommand.to(this.command);
	}
	
	public OFMeterMod setCommand(org.openflow.protocol.interfaces.OFMeterModCommand command) {
		this.command = OFMeterModCommand.from(command);
		return this;
	}
	
	public OFMeterMod setCommand(OFMeterModCommand command) {
		this.command = command;
		return this;
	}

	@org.codehaus.jackson.annotate.JsonIgnore	
	public boolean isCommandSupported() {
		return true;
	}
	
	public short getFlags() {
		return this.flags;
	}
	
	public OFMeterMod setFlags(short flags) {
		this.flags = flags;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isFlagsSupported() {
		return true;
	}
			
	public int getMeterId() {
		return this.meter_id;
	}
	
	public OFMeterMod setMeterId(int meter_id) {
		this.meter_id = meter_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMeterIdSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFMeterBand> getMeters() {
		return this.meters;
	}
	
	public OFMeterMod setMeters(List<org.openflow.protocol.interfaces.OFMeterBand> meters) {
		this.meters = meters;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMetersSupported() {
		return true;
	}
			
	
	
	
	public OFMeterMod dup() {
		return new OFMeterMod(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.command = OFMeterModCommand.valueOf(OFMeterModCommand.readFrom(data));
		this.flags = data.getShort();
		this.meter_id = data.getInt();
		if (this.meters == null) this.meters = new LinkedList<org.openflow.protocol.interfaces.OFMeterBand>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFMeterBand t = OFMeterBandType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.meters.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.command.getValue());
		data.putShort(this.flags);
		data.putInt(this.meter_id);
		if (this.meters != null ) for (org.openflow.protocol.interfaces.OFMeterBand t: this.meters) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFMeterMod-"+":command=" + command + 
		":flags=" + U16.f(flags) + 
		":meter_id=" + U32.f(meter_id) + 
		":meters=" + meters;
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.meters != null ) for ( org.openflow.protocol.interfaces.OFMeterBand i : this.meters ) { len += i.computeLength(); }
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
        		
		final int prime = 1979;
		int result = super.hashCode() * prime;
		result = prime * result + ((command == null)?0:command.hashCode());
		result = prime * result + (int) flags;
		result = prime * result + (int) meter_id;
		result = prime * result + ((meters == null)?0:meters.hashCode());
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
        if (!(obj instanceof OFMeterMod)) {
            return false;
        }
        OFMeterMod other = (OFMeterMod) obj;
		if ( command == null && other.command != null ) { return false; }
		else if ( !command.equals(other.command) ) { return false; }
		if ( flags != other.flags ) return false;
		if ( meter_id != other.meter_id ) return false;
		if ( meters == null && other.meters != null ) { return false; }
		else if ( !meters.equals(other.meters) ) { return false; }
        return true;
    }
}
