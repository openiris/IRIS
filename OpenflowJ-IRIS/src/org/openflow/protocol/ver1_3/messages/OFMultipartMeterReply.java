package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartMeterReply extends OFMultipartReply implements org.openflow.protocol.ver1_3.interfaces.OFMultipartMeterReply {
    public static int MINIMUM_LENGTH = 16;

    List<org.openflow.protocol.ver1_3.interfaces.OFMeterStats>  entries;

    public OFMultipartMeterReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)9, getType()));
		this.entries = new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFMeterStats>();
    }
    
    public OFMultipartMeterReply(OFMultipartMeterReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFMeterStats>();
		for ( org.openflow.protocol.ver1_3.interfaces.OFMeterStats i : other.entries ) { this.entries.add( new OFMeterStats((OFMeterStats)i) ); }
    }

	public List<org.openflow.protocol.ver1_3.interfaces.OFMeterStats> getEntries() {
		return this.entries;
	}
	
	public OFMultipartMeterReply setEntries(List<org.openflow.protocol.ver1_3.interfaces.OFMeterStats> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<org.openflow.protocol.ver1_3.interfaces.OFMeterStats>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFMeterStats t = new OFMeterStats(); t.readFrom(data); this.entries.add(t); __cnt -= t.getLength(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (org.openflow.protocol.ver1_3.interfaces.OFMeterStats t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFMultipartMeterReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( org.openflow.protocol.ver1_3.interfaces.OFMeterStats i : this.entries ) { len += i.computeLength(); }
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
        		
		final int prime = 1567;
		int result = super.hashCode() * prime;
		result = prime * result + ((entries == null)?0:entries.hashCode());
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
        if (!(obj instanceof OFMultipartMeterReply)) {
            return false;
        }
        OFMultipartMeterReply other = (OFMultipartMeterReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
