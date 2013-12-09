package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsTableReply extends OFStatisticsReply  {
    public static int MINIMUM_LENGTH = 12;

    List<OFTableStatsEntry>  entries;

    public OFStatisticsTableReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
		setStatisticsType(OFStatisticsType.valueOf((short)3, getType()));
		this.entries = new LinkedList<OFTableStatsEntry>();
    }
    
    public OFStatisticsTableReply(OFStatisticsTableReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<OFTableStatsEntry>();
		for ( OFTableStatsEntry i : other.entries ) { this.entries.add( new OFTableStatsEntry(i) ); }
    }

	public List<OFTableStatsEntry> getEntries() {
		return this.entries;
	}
	
	public OFStatisticsTableReply setEntries(List<OFTableStatsEntry> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<OFTableStatsEntry>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFTableStatsEntry t = new OFTableStatsEntry(); t.readFrom(data); this.entries.add(t); __cnt -= OFTableStatsEntry.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (OFTableStatsEntry t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsTableReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFTableStatsEntry i : this.entries ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
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
        		
		final int prime = 2579;
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
        if (!(obj instanceof OFStatisticsTableReply)) {
            return false;
        }
        OFStatisticsTableReply other = (OFStatisticsTableReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
