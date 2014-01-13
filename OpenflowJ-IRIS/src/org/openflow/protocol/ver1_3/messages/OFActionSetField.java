package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionSetField extends OFAction implements org.openflow.protocol.interfaces.OFActionSetField {
    public static int MINIMUM_LENGTH = 8;

    org.openflow.protocol.interfaces.OFOxm  field;

    public OFActionSetField() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)25));
    }
    
    public OFActionSetField(OFActionSetField other) {
    	super(other);
		this.field = new OFOxm((OFOxm)other.field);
    }

	public org.openflow.protocol.interfaces.OFOxm getField() {
		return this.field;
	}
	
	public OFActionSetField setField(org.openflow.protocol.interfaces.OFOxm field) {
		this.field = field;
		return this;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		if (this.field == null) this.field = new OFOxm();
		this.field.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        field.writeTo(data);
    }

    public String toString() {
        return super.toString() +  ":OFActionSetField-"+":field=" + field.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
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
        		
		final int prime = 2161;
		int result = super.hashCode() * prime;
		result = prime * result + ((field == null)?0:field.hashCode());
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
        if (!(obj instanceof OFActionSetField)) {
            return false;
        }
        OFActionSetField other = (OFActionSetField) obj;
		if ( field == null && other.field != null ) { return false; }
		else if ( !field.equals(other.field) ) { return false; }
        return true;
    }
}
