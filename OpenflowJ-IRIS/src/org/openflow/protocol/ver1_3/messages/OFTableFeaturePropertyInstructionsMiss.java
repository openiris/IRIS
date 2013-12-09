package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyInstructionsMiss extends OFTableFeatureProperty  {
    public static int MINIMUM_LENGTH = 4;

    List<OFInstruction>  instruction_ids;

    public OFTableFeaturePropertyInstructionsMiss() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)1));
		this.instruction_ids = new LinkedList<OFInstruction>();
    }
    
    public OFTableFeaturePropertyInstructionsMiss(OFTableFeaturePropertyInstructionsMiss other) {
    	super(other);
		this.instruction_ids = (other.instruction_ids == null)? null: new LinkedList<OFInstruction>();
		for ( OFInstruction i : other.instruction_ids ) { this.instruction_ids.add( new OFInstruction(i) ); }
    }

	public List<OFInstruction> getInstructionIds() {
		return this.instruction_ids;
	}
	
	public OFTableFeaturePropertyInstructionsMiss setInstructionIds(List<OFInstruction> instruction_ids) {
		this.instruction_ids = instruction_ids;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.instruction_ids == null) this.instruction_ids = new LinkedList<OFInstruction>();
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
        if (this.instruction_ids != null ) for (OFInstruction t: this.instruction_ids) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyInstructionsMiss-"+":instruction_ids=" + instruction_ids.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFInstruction i : this.instruction_ids ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
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
        		
		final int prime = 1787;
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
        if (!(obj instanceof OFTableFeaturePropertyInstructionsMiss)) {
            return false;
        }
        OFTableFeaturePropertyInstructionsMiss other = (OFTableFeaturePropertyInstructionsMiss) obj;
		if ( instruction_ids == null && other.instruction_ids != null ) { return false; }
		else if ( !instruction_ids.equals(other.instruction_ids) ) { return false; }
        return true;
    }
}
