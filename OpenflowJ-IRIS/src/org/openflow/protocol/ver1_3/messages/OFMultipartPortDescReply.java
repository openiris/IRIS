package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartPortDescReply extends OFMultipartReply  {
    public static int MINIMUM_LENGTH = 16;

    List<OFPortDesc>  entries;

    public OFMultipartPortDescReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)13, getType()));
		this.entries = new LinkedList<OFPortDesc>();
    }
    
    public OFMultipartPortDescReply(OFMultipartPortDescReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<OFPortDesc>();
		for ( OFPortDesc i : other.entries ) { this.entries.add( new OFPortDesc(i) ); }
    }

	public List<OFPortDesc> getEntries() {
		return this.entries;
	}
	
	public OFMultipartPortDescReply setEntries(List<OFPortDesc> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<OFPortDesc>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFPortDesc t = new OFPortDesc(); t.readFrom(data); this.entries.add(t); __cnt -= OFPortDesc.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (OFPortDesc t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFMultipartPortDescReply-"+":entries=" + entries.toString();
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFPortDesc i : this.entries ) { len += i.computeLength(); }
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1499;
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
        if (!(obj instanceof OFMultipartPortDescReply)) {
            return false;
        }
        OFMultipartPortDescReply other = (OFMultipartPortDescReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
