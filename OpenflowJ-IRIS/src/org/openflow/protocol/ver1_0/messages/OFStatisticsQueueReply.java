package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsQueueReply extends OFStatisticsReply  {
    public static int MINIMUM_LENGTH = 12;

    List<OFQueueStatsEntry>  entries;

    public OFStatisticsQueueReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
		setStatisticsType(OFStatisticsType.valueOf((short)5, getType()));
		this.entries = new LinkedList<OFQueueStatsEntry>();
    }
    
    public OFStatisticsQueueReply(OFStatisticsQueueReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<OFQueueStatsEntry>();
		for ( OFQueueStatsEntry i : other.entries ) { this.entries.add( new OFQueueStatsEntry(i) ); }
    }

	public List<OFQueueStatsEntry> getEntries() {
		return this.entries;
	}
	
	public OFStatisticsQueueReply setEntries(List<OFQueueStatsEntry> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<OFQueueStatsEntry>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFQueueStatsEntry t = new OFQueueStatsEntry(); t.readFrom(data); this.entries.add(t); __cnt -= OFQueueStatsEntry.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (OFQueueStatsEntry t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsQueueReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFQueueStatsEntry i : this.entries ) { len += i.computeLength(); }
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
        		
		final int prime = 2543;
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
        if (!(obj instanceof OFStatisticsQueueReply)) {
            return false;
        }
        OFStatisticsQueueReply other = (OFStatisticsQueueReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
