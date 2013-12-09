package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartMeterConfigReply extends OFMultipartReply  {
    public static int MINIMUM_LENGTH = 16;

    List<OFMeterBand>  entries;

    public OFMultipartMeterConfigReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)10, getType()));
		this.entries = new LinkedList<OFMeterBand>();
    }
    
    public OFMultipartMeterConfigReply(OFMultipartMeterConfigReply other) {
    	super(other);
		this.entries = (other.entries == null)? null: new LinkedList<OFMeterBand>();
		for ( OFMeterBand i : other.entries ) { this.entries.add( new OFMeterBand(i) ); }
    }

	public List<OFMeterBand> getEntries() {
		return this.entries;
	}
	
	public OFMultipartMeterConfigReply setEntries(List<OFMeterBand> entries) {
		this.entries = entries;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.entries == null) this.entries = new LinkedList<OFMeterBand>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFMeterBand t = OFMeterBandType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.entries.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.entries != null ) for (OFMeterBand t: this.entries) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFMultipartMeterConfigReply-"+":entries=" + entries.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFMeterBand i : this.entries ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
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
        		
		final int prime = 1553;
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
        if (!(obj instanceof OFMultipartMeterConfigReply)) {
            return false;
        }
        OFMultipartMeterConfigReply other = (OFMultipartMeterConfigReply) obj;
		if ( entries == null && other.entries != null ) { return false; }
		else if ( !entries.equals(other.entries) ) { return false; }
        return true;
    }
}
