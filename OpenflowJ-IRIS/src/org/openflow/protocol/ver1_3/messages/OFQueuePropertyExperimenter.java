package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFQueuePropertyExperimenter extends OFQueueProperty  {
    public static int MINIMUM_LENGTH = 16;

    int  experimenter_id;
	int pad_1th;
	byte[]  data;

    public OFQueuePropertyExperimenter() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFQueuePropertyType.valueOf((short)65535));
    }
    
    public OFQueuePropertyExperimenter(OFQueuePropertyExperimenter other) {
    	super(other);
		this.experimenter_id = other.experimenter_id;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getExperimenterId() {
		return this.experimenter_id;
	}
	
	public OFQueuePropertyExperimenter setExperimenterId(int experimenter_id) {
		this.experimenter_id = experimenter_id;
		return this;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFQueuePropertyExperimenter setData(byte[] data) {
		this.data = data;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.experimenter_id = data.getInt();
		this.pad_1th = data.getInt();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.experimenter_id);
		data.putInt(this.pad_1th);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFQueuePropertyExperimenter-"+":experimenter_id=" + U32.f(experimenter_id) + 
		":data=" + java.util.Arrays.toString(data);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.data != null ) { len += this.data.length; } 
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
        		
		final int prime = 1471;
		int result = super.hashCode() * prime;
		result = prime * result + (int) experimenter_id;
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
        if (!(obj instanceof OFQueuePropertyExperimenter)) {
            return false;
        }
        OFQueuePropertyExperimenter other = (OFQueuePropertyExperimenter) obj;
		if ( experimenter_id != other.experimenter_id ) return false;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
