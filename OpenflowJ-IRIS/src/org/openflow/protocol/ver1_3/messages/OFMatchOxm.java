package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFMatchOxm extends OFMatch implements org.openflow.protocol.ver1_3.interfaces.OFMatchOxm {
    public static int MINIMUM_LENGTH = 4;

    List<org.openflow.protocol.ver1_3.interfaces.OFOxm>  oxm_fields;

    public OFMatchOxm() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMatchType.valueOf((short)1));
		this.oxm_fields = new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFOxm>();
    }
    
    public OFMatchOxm(OFMatchOxm other) {
    	super(other);
		this.oxm_fields = (other.oxm_fields == null)? null: new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFOxm>();
		for ( org.openflow.protocol.ver1_3.interfaces.OFOxm i : other.oxm_fields ) { this.oxm_fields.add( new OFOxm((OFOxm)i) ); }
    }

	public List<org.openflow.protocol.ver1_3.interfaces.OFOxm> getOxmFields() {
		return this.oxm_fields;
	}
	
	public OFMatchOxm setOxmFields(List<org.openflow.protocol.ver1_3.interfaces.OFOxm> oxm_fields) {
		this.oxm_fields = oxm_fields;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.oxm_fields == null) this.oxm_fields = new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFOxm>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFOxm t = new OFOxm(); t.readFrom(data); this.oxm_fields.add(t); __cnt -= (OFOxm.MINIMUM_LENGTH + t.getPayloadLength()); }
		int __align = alignment(getLength(), 8);
		for (int i = 0; i < __align; ++i ) { data.get(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.oxm_fields != null ) for (org.openflow.protocol.ver1_3.interfaces.OFOxm t: this.oxm_fields) { t.writeTo(data); }
		int __align = alignment(computeLength(), 8);
		for (int i = 0; i < __align; ++i ) { data.put((byte)0); }
    }

    public String toString() {
        return super.toString() +  ":OFMatchOxm-"+":oxm_fields=" + oxm_fields.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( org.openflow.protocol.ver1_3.interfaces.OFOxm i : this.oxm_fields ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 8));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2297;
		int result = super.hashCode() * prime;
		result = prime * result + ((oxm_fields == null)?0:oxm_fields.hashCode());
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
        if (!(obj instanceof OFMatchOxm)) {
            return false;
        }
        OFMatchOxm other = (OFMatchOxm) obj;
		if ( oxm_fields == null && other.oxm_fields != null ) { return false; }
		else if ( !oxm_fields.equals(other.oxm_fields) ) { return false; }
        return true;
    }
}
