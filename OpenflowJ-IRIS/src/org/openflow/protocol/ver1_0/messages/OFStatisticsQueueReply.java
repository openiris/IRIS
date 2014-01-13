package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import java.util.LinkedList;
import java.util.List;

public class OFStatisticsQueueReply extends OFStatisticsReply implements org.openflow.protocol.interfaces.OFStatisticsQueueReply {
    public static int MINIMUM_LENGTH = 12;

    List<org.openflow.protocol.interfaces.OFQueueStatsEntry>  entries;

    public OFStatisticsQueueReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
		setStatisticsType(OFStatisticsType.valueOf((short)5, this.type));
    }
    
    public OFStatisticsQueueReply(OFStatisticsQueueReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFQueueStatsEntry>();
		for ( org.openflow.protocol.interfaces.OFQueueStatsEntry i : other.entries ) { this.entries.add( new OFQueueStatsEntry((OFQueueStatsEntry)i) ); }
    }

	public List<org.openflow.protocol.interfaces.OFQueueStatsEntry> getEntries() {
		return this.entries;
	}
	
	public OFStatisticsQueueReply setEntries(List<org.openflow.protocol.interfaces.OFQueueStatsEntry> entries) {
		this.entries = entries;
		return this;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<org.openflow.protocol.interfaces.OFQueueStatsEntry>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFQueueStatsEntry t = new OFQueueStatsEntry(); t.readFrom(data); this.entries.add(t); __cnt -= OFQueueStatsEntry.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (org.openflow.protocol.interfaces.OFQueueStatsEntry t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsQueueReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.entries != null ) for ( org.openflow.protocol.interfaces.OFQueueStatsEntry i : this.entries ) { len += i.computeLength(); }
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
