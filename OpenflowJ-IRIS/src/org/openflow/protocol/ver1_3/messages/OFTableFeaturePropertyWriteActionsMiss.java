package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyWriteActionsMiss extends OFTableFeatureProperty implements org.openflow.protocol.interfaces.OFTableFeaturePropertyWriteActionsMiss {
    public static int MINIMUM_LENGTH = 4;
    public static int CORE_LENGTH = 0;

    List<org.openflow.protocol.interfaces.OFActionId>  action_ids;

    public OFTableFeaturePropertyWriteActionsMiss() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)5));
    }
    
    public OFTableFeaturePropertyWriteActionsMiss(OFTableFeaturePropertyWriteActionsMiss other) {
    	super(other);
		this.action_ids = (other.action_ids == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFActionId>();
		for ( org.openflow.protocol.interfaces.OFActionId i : other.action_ids ) { this.action_ids.add( i.dup() ); }
    }

	public List<org.openflow.protocol.interfaces.OFActionId> getActionIds() {
		return this.action_ids;
	}
	
	public OFTableFeaturePropertyWriteActionsMiss setActionIds(List<org.openflow.protocol.interfaces.OFActionId> action_ids) {
		this.action_ids = action_ids;
		return this;
	}
	
	public boolean isActionIdsSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeaturePropertyWriteActionsMiss dup() {
		return new OFTableFeaturePropertyWriteActionsMiss(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.action_ids == null) this.action_ids = new LinkedList<org.openflow.protocol.interfaces.OFActionId>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFActionId t = new OFActionId(); t.readFrom(data); this.action_ids.add(t); __cnt -= t.getLength(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.action_ids != null ) for (org.openflow.protocol.interfaces.OFActionId t: this.action_ids) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyWriteActionsMiss-"+":action_ids=" + action_ids.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.action_ids != null ) for ( org.openflow.protocol.interfaces.OFActionId i : this.action_ids ) { len += i.computeLength(); }
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
        		
		final int prime = 1753;
		int result = super.hashCode() * prime;
		result = prime * result + ((action_ids == null)?0:action_ids.hashCode());
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
        if (!(obj instanceof OFTableFeaturePropertyWriteActionsMiss)) {
            return false;
        }
        OFTableFeaturePropertyWriteActionsMiss other = (OFTableFeaturePropertyWriteActionsMiss) obj;
		if ( action_ids == null && other.action_ids != null ) { return false; }
		else if ( !action_ids.equals(other.action_ids) ) { return false; }
        return true;
    }
}
