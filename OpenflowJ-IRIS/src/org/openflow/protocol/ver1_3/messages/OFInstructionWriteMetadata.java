package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFInstructionWriteMetadata extends OFInstruction  {
    public static int MINIMUM_LENGTH = 24;

    int pad_1th;
	long  metadata;
	long  metadata_mask;

    public OFInstructionWriteMetadata() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFInstructionType.valueOf((short)2));
    }
    
    public OFInstructionWriteMetadata(OFInstructionWriteMetadata other) {
    	super(other);
		this.metadata = other.metadata;
		this.metadata_mask = other.metadata_mask;
    }

	public long getMetadata() {
		return this.metadata;
	}
	
	public OFInstructionWriteMetadata setMetadata(long metadata) {
		this.metadata = metadata;
		return this;
	}
			
	public long getMetadataMask() {
		return this.metadata_mask;
	}
	
	public OFInstructionWriteMetadata setMetadataMask(long metadata_mask) {
		this.metadata_mask = metadata_mask;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.pad_1th = data.getInt();
		this.metadata = data.getLong();
		this.metadata_mask = data.getLong();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.pad_1th);
		data.putLong(this.metadata);
		data.putLong(this.metadata_mask);
    }

    public String toString() {
        return super.toString() +  ":OFInstructionWriteMetadata-"+":metadata=" + U64.f(metadata) + 
		":metadata_mask=" + U64.f(metadata_mask);
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2129;
		int result = super.hashCode() * prime;
		result = prime * result + (int) metadata;
		result = prime * result + (int) metadata_mask;
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
        if (!(obj instanceof OFInstructionWriteMetadata)) {
            return false;
        }
        OFInstructionWriteMetadata other = (OFInstructionWriteMetadata) obj;
		if ( metadata != other.metadata ) return false;
		if ( metadata_mask != other.metadata_mask ) return false;
        return true;
    }
}
