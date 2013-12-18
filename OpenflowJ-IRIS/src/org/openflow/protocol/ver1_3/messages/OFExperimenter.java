package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFExperimenter extends OFMessage implements org.openflow.protocol.ver1_3.interfaces.OFExperimenter {
    public static int MINIMUM_LENGTH = 16;

    int  experimenter_id;
	int  subtype;
	byte[]  data;

    public OFExperimenter() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)4));
    }
    
    public OFExperimenter(OFExperimenter other) {
    	super(other);
		this.experimenter_id = other.experimenter_id;
		this.subtype = other.subtype;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getExperimenterId() {
		return this.experimenter_id;
	}
	
	public OFExperimenter setExperimenterId(int experimenter_id) {
		this.experimenter_id = experimenter_id;
		return this;
	}
			
	public int getSubtype() {
		return this.subtype;
	}
	
	public OFExperimenter setSubtype(int subtype) {
		this.subtype = subtype;
		return this;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFExperimenter setData(byte[] data) {
		this.data = data;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.experimenter_id = data.getInt();
		this.subtype = data.getInt();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.experimenter_id);
		data.putInt(this.subtype);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFExperimenter-"+":experimenter_id=" + U32.f(experimenter_id) + 
		":subtype=" + U32.f(subtype) + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 2399;
		int result = super.hashCode() * prime;
		result = prime * result + (int) experimenter_id;
		result = prime * result + (int) subtype;
		result = prime * result + ((data == null)?0:java.util.Arrays.hashCode(data));
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
        if (!(obj instanceof OFExperimenter)) {
            return false;
        }
        OFExperimenter other = (OFExperimenter) obj;
		if ( experimenter_id != other.experimenter_id ) return false;
		if ( subtype != other.subtype ) return false;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
