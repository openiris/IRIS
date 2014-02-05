package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyInstructions extends OFTableFeatureProperty implements org.openflow.protocol.interfaces.OFTableFeaturePropertyInstructions {
    public static int MINIMUM_LENGTH = 4;
    public static int CORE_LENGTH = 0;

    List<org.openflow.protocol.interfaces.OFInstruction>  instruction_ids;

    public OFTableFeaturePropertyInstructions() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)0));
    }
    
    public OFTableFeaturePropertyInstructions(OFTableFeaturePropertyInstructions other) {
    	super(other);
		this.instruction_ids = (other.instruction_ids == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFInstruction>();
		for ( org.openflow.protocol.interfaces.OFInstruction i : other.instruction_ids ) { this.instruction_ids.add( i.dup() ); }
    }

	public List<org.openflow.protocol.interfaces.OFInstruction> getInstructionIds() {
		return this.instruction_ids;
	}
	
	public OFTableFeaturePropertyInstructions setInstructionIds(List<org.openflow.protocol.interfaces.OFInstruction> instruction_ids) {
		this.instruction_ids = instruction_ids;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isInstructionIdsSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeaturePropertyInstructions dup() {
		return new OFTableFeaturePropertyInstructions(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.instruction_ids == null) this.instruction_ids = new LinkedList<org.openflow.protocol.interfaces.OFInstruction>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFInstruction t = OFInstructionType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.instruction_ids.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.instruction_ids != null ) for (org.openflow.protocol.interfaces.OFInstruction t: this.instruction_ids) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyInstructions-"+":instruction_ids=" + instruction_ids.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.instruction_ids != null ) for ( org.openflow.protocol.interfaces.OFInstruction i : this.instruction_ids ) { len += i.computeLength(); }
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
        		
		final int prime = 1789;
		int result = super.hashCode() * prime;
		result = prime * result + ((instruction_ids == null)?0:instruction_ids.hashCode());
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
        if (!(obj instanceof OFTableFeaturePropertyInstructions)) {
            return false;
        }
        OFTableFeaturePropertyInstructions other = (OFTableFeaturePropertyInstructions) obj;
		if ( instruction_ids == null && other.instruction_ids != null ) { return false; }
		else if ( !instruction_ids.equals(other.instruction_ids) ) { return false; }
        return true;
    }
}
