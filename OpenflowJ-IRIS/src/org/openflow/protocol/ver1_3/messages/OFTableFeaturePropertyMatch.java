package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyMatch extends OFTableFeatureProperty implements org.openflow.protocol.interfaces.OFTableFeaturePropertyMatch {
    public static int MINIMUM_LENGTH = 4;

    List<Integer>  oxm_ids;

    public OFTableFeaturePropertyMatch() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)8));
    }
    
    public OFTableFeaturePropertyMatch(OFTableFeaturePropertyMatch other) {
    	super(other);
		this.oxm_ids = (other.oxm_ids == null)? null: new LinkedList<Integer>();
		for ( Integer i : other.oxm_ids ) { this.oxm_ids.add( new Integer(i) ); }
    }

	public List<Integer> getOxmIds() {
		return this.oxm_ids;
	}
	
	public OFTableFeaturePropertyMatch setOxmIds(List<Integer> oxm_ids) {
		this.oxm_ids = oxm_ids;
		return this;
	}
	
	public boolean isOxmIdsSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeaturePropertyMatch dup() {
		return new OFTableFeaturePropertyMatch(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.oxm_ids == null) this.oxm_ids = new LinkedList<Integer>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { this.oxm_ids.add( data.getInt() ); __cnt -= 4; } 
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if ( this.oxm_ids != null ) for (Integer t: this.oxm_ids) { data.putInt(t); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyMatch-"+":oxm_ids=" + oxm_ids.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.oxm_ids != null ) { len += 4 * this.oxm_ids.size(); }
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
        		
		final int prime = 1733;
		int result = super.hashCode() * prime;
		result = prime * result + ((oxm_ids == null)?0:oxm_ids.hashCode());
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
        if (!(obj instanceof OFTableFeaturePropertyMatch)) {
            return false;
        }
        OFTableFeaturePropertyMatch other = (OFTableFeaturePropertyMatch) obj;
		if ( oxm_ids == null && other.oxm_ids != null ) { return false; }
		else if ( !oxm_ids.equals(other.oxm_ids) ) { return false; }
        return true;
    }
}
