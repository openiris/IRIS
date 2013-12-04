package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFActionSetField extends OFAction  {
    public static int MINIMUM_LENGTH = 8;

    OFOxm  field;

    public OFActionSetField() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFActionType.valueOf((short)25));
		this.field = new OFOxm();
    }
    
    public OFActionSetField(OFActionSetField other) {
    	super(other);
		this.field = new OFOxm(other.field);
    }

	public OFOxm getField() {
		return this.field;
	}
	
	public OFActionSetField setField(OFOxm field) {
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
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
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
