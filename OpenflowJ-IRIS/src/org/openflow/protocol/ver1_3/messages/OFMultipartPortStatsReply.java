package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartPortStatsReply extends OFMultipartReply implements org.openflow.protocol.interfaces.OFMultipartPortStatsReply {
    public static int MINIMUM_LENGTH = 16;

    List<org.openflow.protocol.interfaces.OFPortStatsEntry>  entries;

    public OFMultipartPortStatsReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)4, getType()));
		this.entries = new LinkedList<org.openflow.protocol.interfaces.OFPortStatsEntry>();
    }
    
    public OFMultipartPortStatsReply(OFMultipartPortStatsReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFPortStatsEntry>();
		for ( org.openflow.protocol.interfaces.OFPortStatsEntry i : other.entries ) { this.entries.add( new OFPortStatsEntry((OFPortStatsEntry)i) ); }
    }

	public List<org.openflow.protocol.interfaces.OFPortStatsEntry> getEntries() {
		return this.entries;
	}
	
	public OFMultipartPortStatsReply setEntries(List<org.openflow.protocol.interfaces.OFPortStatsEntry> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<org.openflow.protocol.interfaces.OFPortStatsEntry>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFPortStatsEntry t = new OFPortStatsEntry(); t.readFrom(data); this.entries.add(t); __cnt -= OFPortStatsEntry.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (org.openflow.protocol.interfaces.OFPortStatsEntry t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFMultipartPortStatsReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( org.openflow.protocol.interfaces.OFPortStatsEntry i : this.entries ) { len += i.computeLength(); }
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
        		
		final int prime = 1637;
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
        if (!(obj instanceof OFMultipartPortStatsReply)) {
            return false;
        }
        OFMultipartPortStatsReply other = (OFMultipartPortStatsReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
