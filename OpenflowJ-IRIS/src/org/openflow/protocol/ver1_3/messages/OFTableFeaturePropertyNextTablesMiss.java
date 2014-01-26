package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeaturePropertyNextTablesMiss extends OFTableFeatureProperty implements org.openflow.protocol.interfaces.OFTableFeaturePropertyNextTablesMiss {
    public static int MINIMUM_LENGTH = 4;

    List<Byte>  next_table_ids;

    public OFTableFeaturePropertyNextTablesMiss() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFTableFeaturePropertyType.valueOf((short)3));
    }
    
    public OFTableFeaturePropertyNextTablesMiss(OFTableFeaturePropertyNextTablesMiss other) {
    	super(other);
		this.next_table_ids = (other.next_table_ids == null)? null: new LinkedList<Byte>();
		for ( Byte i : other.next_table_ids ) { this.next_table_ids.add( new Byte((Byte)i) ); }
    }

	public List<Byte> getNextTableIds() {
		return this.next_table_ids;
	}
	
	public OFTableFeaturePropertyNextTablesMiss setNextTableIds(List<Byte> next_table_ids) {
		this.next_table_ids = next_table_ids;
		return this;
	}
	
	public boolean isNextTableIdsSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeaturePropertyNextTablesMiss dup() {
		return new OFTableFeaturePropertyNextTablesMiss(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.next_table_ids == null) this.next_table_ids = new LinkedList<Byte>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { this.next_table_ids.add( data.get() ); __cnt -= 1; } 
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if ( this.next_table_ids != null ) for (Byte t: this.next_table_ids) { data.put(t); }
    }

    public String toString() {
        return super.toString() +  ":OFTableFeaturePropertyNextTablesMiss-"+":next_table_ids=" + next_table_ids.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.next_table_ids != null ) { len += 1 * this.next_table_ids.size(); }
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
        		
		final int prime = 1777;
		int result = super.hashCode() * prime;
		result = prime * result + ((next_table_ids == null)?0:next_table_ids.hashCode());
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
        if (!(obj instanceof OFTableFeaturePropertyNextTablesMiss)) {
            return false;
        }
        OFTableFeaturePropertyNextTablesMiss other = (OFTableFeaturePropertyNextTablesMiss) obj;
		if ( next_table_ids == null && other.next_table_ids != null ) { return false; }
		else if ( !next_table_ids.equals(other.next_table_ids) ) { return false; }
        return true;
    }
}
