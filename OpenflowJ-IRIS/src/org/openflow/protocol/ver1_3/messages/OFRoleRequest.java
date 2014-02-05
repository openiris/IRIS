package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFRoleRequest extends OFMessage implements org.openflow.protocol.interfaces.OFRoleRequest {
    public static int MINIMUM_LENGTH = 24;
    public static int CORE_LENGTH = 16;

    int  role;
	int pad_1th;
	long  generation_id;

    public OFRoleRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)24));
    }
    
    public OFRoleRequest(OFRoleRequest other) {
    	super(other);
		this.role = other.role;
		this.generation_id = other.generation_id;
    }

	public int getRole() {
		return this.role;
	}
	
	public OFRoleRequest setRole(int role) {
		this.role = role;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isRoleSupported() {
		return true;
	}
			
	public long getGenerationId() {
		return this.generation_id;
	}
	
	public OFRoleRequest setGenerationId(long generation_id) {
		this.generation_id = generation_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isGenerationIdSupported() {
		return true;
	}
			
	
	
	
	public OFRoleRequest dup() {
		return new OFRoleRequest(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.role = data.getInt();
		this.pad_1th = data.getInt();
		this.generation_id = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.role);
		data.putInt(this.pad_1th);
		data.putLong(this.generation_id);
    }

    public String toString() {
        return super.toString() +  ":OFRoleRequest-"+":role=" + U32.f(role) + 
		":generation_id=" + U64.f(generation_id);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
        		
		final int prime = 1447;
		int result = super.hashCode() * prime;
		result = prime * result + (int) role;
		result = prime * result + (int) generation_id;
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
        if (!(obj instanceof OFRoleRequest)) {
            return false;
        }
        OFRoleRequest other = (OFRoleRequest) obj;
		if ( role != other.role ) return false;
		if ( generation_id != other.generation_id ) return false;
        return true;
    }
}
