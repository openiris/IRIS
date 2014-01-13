package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFGroupMod extends OFMessage implements org.openflow.protocol.interfaces.OFGroupMod {
    public static int MINIMUM_LENGTH = 16;

    OFGroupModCommand  command;
	OFGroupCategory  group_category;
	byte pad_1th;
	int  group_id;
	List<org.openflow.protocol.interfaces.OFBucket>  buckets;

    public OFGroupMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)15));
    }
    
    public OFGroupMod(OFGroupMod other) {
    	super(other);
		this.command = other.command;
		this.group_category = other.group_category;
		this.group_id = other.group_id;
		this.buckets = (other.buckets == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFBucket>();
		for ( org.openflow.protocol.interfaces.OFBucket i : other.buckets ) { this.buckets.add( new OFBucket((OFBucket)i) ); }
    }

	public org.openflow.protocol.interfaces.OFGroupModCommand getCommand() {
		return OFGroupModCommand.to(this.command);
	}
	
	public OFGroupMod setCommand(org.openflow.protocol.interfaces.OFGroupModCommand command) {
		this.command = OFGroupModCommand.from(command);
		return this;
	}
	
	public OFGroupMod setCommand(OFGroupModCommand command) {
		this.command = command;
		return this;
	}
	

	public org.openflow.protocol.interfaces.OFGroupCategory getGroupCategory() {
		return OFGroupCategory.to(this.group_category);
	}
	
	public OFGroupMod setGroupCategory(org.openflow.protocol.interfaces.OFGroupCategory group_category) {
		this.group_category = OFGroupCategory.from(group_category);
		return this;
	}
	
	public OFGroupMod setGroupCategory(OFGroupCategory group_category) {
		this.group_category = group_category;
		return this;
	}
	
	public int getGroupId() {
		return this.group_id;
	}
	
	public OFGroupMod setGroupId(int group_id) {
		this.group_id = group_id;
		return this;
	}
			
	public List<org.openflow.protocol.interfaces.OFBucket> getBuckets() {
		return this.buckets;
	}
	
	public OFGroupMod setBuckets(List<org.openflow.protocol.interfaces.OFBucket> buckets) {
		this.buckets = buckets;
		return this;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.command = OFGroupModCommand.valueOf(OFGroupModCommand.readFrom(data));
		this.group_category = OFGroupCategory.valueOf(OFGroupCategory.readFrom(data));
		this.pad_1th = data.get();
		this.group_id = data.getInt();
		if (this.buckets == null) this.buckets = new LinkedList<org.openflow.protocol.interfaces.OFBucket>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFBucket t = new OFBucket(); t.readFrom(data); this.buckets.add(t); __cnt -= t.getLength(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.command.getValue());
		data.put(this.group_category.getValue());
		data.put(this.pad_1th);
		data.putInt(this.group_id);
		if (this.buckets != null ) for (org.openflow.protocol.interfaces.OFBucket t: this.buckets) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFGroupMod-"+":command=" + command.toString() + 
		":group_category=" + group_category.toString() + 
		":group_id=" + U32.f(group_id) + 
		":buckets=" + buckets.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.buckets != null ) for ( org.openflow.protocol.interfaces.OFBucket i : this.buckets ) { len += i.computeLength(); }
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
        		
		final int prime = 2027;
		int result = super.hashCode() * prime;
		result = prime * result + ((command == null)?0:command.hashCode());
		result = prime * result + ((group_category == null)?0:group_category.hashCode());
		result = prime * result + (int) group_id;
		result = prime * result + ((buckets == null)?0:buckets.hashCode());
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
        if (!(obj instanceof OFGroupMod)) {
            return false;
        }
        OFGroupMod other = (OFGroupMod) obj;
		if ( command == null && other.command != null ) { return false; }
		else if ( !command.equals(other.command) ) { return false; }
		if ( group_category == null && other.group_category != null ) { return false; }
		else if ( !group_category.equals(other.group_category) ) { return false; }
		if ( group_id != other.group_id ) return false;
		if ( buckets == null && other.buckets != null ) { return false; }
		else if ( !buckets.equals(other.buckets) ) { return false; }
        return true;
    }
}
