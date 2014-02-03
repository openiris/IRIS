package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFHelloElemVersionbitmap extends OFHelloElem implements org.openflow.protocol.interfaces.OFHelloElemVersionbitmap {
    public static int MINIMUM_LENGTH = 4;
    public static int CORE_LENGTH = 0;

    List<Integer>  bitmaps;

    public OFHelloElemVersionbitmap() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFHelloElemType.valueOf((short)1));
    }
    
    public OFHelloElemVersionbitmap(OFHelloElemVersionbitmap other) {
    	super(other);
		this.bitmaps = (other.bitmaps == null)? null: new LinkedList<Integer>();
		for ( Integer i : other.bitmaps ) { this.bitmaps.add( new Integer(i) ); }
    }

	public List<Integer> getBitmaps() {
		return this.bitmaps;
	}
	
	public OFHelloElemVersionbitmap setBitmaps(List<Integer> bitmaps) {
		this.bitmaps = bitmaps;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isBitmapsSupported() {
		return true;
	}
			
	
	
	
	public OFHelloElemVersionbitmap dup() {
		return new OFHelloElemVersionbitmap(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.bitmaps == null) this.bitmaps = new LinkedList<Integer>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { this.bitmaps.add( data.getInt() ); __cnt -= 4; } 
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if ( this.bitmaps != null ) for (Integer t: this.bitmaps) { data.putInt(t); }
    }

    public String toString() {
        return super.toString() +  ":OFHelloElemVersionbitmap-"+":bitmaps=" + bitmaps.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.bitmaps != null ) { len += 4 * this.bitmaps.size(); }
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
        		
		final int prime = 2437;
		int result = super.hashCode() * prime;
		result = prime * result + ((bitmaps == null)?0:bitmaps.hashCode());
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
        if (!(obj instanceof OFHelloElemVersionbitmap)) {
            return false;
        }
        OFHelloElemVersionbitmap other = (OFHelloElemVersionbitmap) obj;
		if ( bitmaps == null && other.bitmaps != null ) { return false; }
		else if ( !bitmaps.equals(other.bitmaps) ) { return false; }
        return true;
    }
}
