package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFStatisticsExperimenterReply extends OFStatisticsReply implements org.openflow.protocol.interfaces.OFStatisticsExperimenterReply {
    public static int MINIMUM_LENGTH = 24;

    int  experimenter_id;
	int  experiment_type;
	byte[]  data;

    public OFStatisticsExperimenterReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setStatisticsType(OFStatisticsType.valueOf((short)0xffff, this.type));
    }
    
    public OFStatisticsExperimenterReply(OFStatisticsExperimenterReply other) {
    	super(other);
		this.experimenter_id = other.experimenter_id;
		this.experiment_type = other.experiment_type;
		if (other.data != null) { this.data = java.util.Arrays.copyOf(other.data, other.data.length); }
    }

	public int getExperimenterId() {
		return this.experimenter_id;
	}
	
	public OFStatisticsExperimenterReply setExperimenterId(int experimenter_id) {
		this.experimenter_id = experimenter_id;
		return this;
	}
	
	public boolean isExperimenterIdSupported() {
		return true;
	}
			
	public int getExperimentType() {
		return this.experiment_type;
	}
	
	public OFStatisticsExperimenterReply setExperimentType(int experiment_type) {
		this.experiment_type = experiment_type;
		return this;
	}
	
	public boolean isExperimentTypeSupported() {
		return true;
	}
			
	public byte[] getData() {
		return this.data;
	}
	
	public OFStatisticsExperimenterReply setData(byte[] data) {
		this.data = data;
		return this;
	}
	
	public boolean isDataSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.experimenter_id = data.getInt();
		this.experiment_type = data.getInt();
		if ( this.data == null ) this.data = new byte[(getLength() - (data.position() - mark))];
		data.get(this.data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.experimenter_id);
		data.putInt(this.experiment_type);
		if ( this.data != null ) { data.put(this.data); }
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsExperimenterReply-"+":experimenter_id=" + U32.f(experimenter_id) + 
		":experiment_type=" + U32.f(experiment_type) + 
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
        		
		final int prime = 1489;
		int result = super.hashCode() * prime;
		result = prime * result + (int) experimenter_id;
		result = prime * result + (int) experiment_type;
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
        if (!(obj instanceof OFStatisticsExperimenterReply)) {
            return false;
        }
        OFStatisticsExperimenterReply other = (OFStatisticsExperimenterReply) obj;
		if ( experimenter_id != other.experimenter_id ) return false;
		if ( experiment_type != other.experiment_type ) return false;
		if ( data == null && other.data != null ) { return false; }
		else if ( !java.util.Arrays.equals(data, other.data) ) { return false; }
        return true;
    }
}
