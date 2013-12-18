package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFFlowAdd extends OFFlowMod implements org.openflow.protocol.ver1_0.interfaces.OFFlowAdd {
    public static int MINIMUM_LENGTH = 72;

    

    public OFFlowAdd() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)14));
		setCommand(OFFlowModCommand.valueOf((short)0));
    }
    
    public OFFlowAdd(OFFlowAdd other) {
    	super(other);
    }



    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        
    }

    public String toString() {
        return super.toString() +  ":OFFlowAdd";
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
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
        		
		final int prime = 2719;
		int result = super.hashCode() * prime;
		
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
        if (!(obj instanceof OFFlowAdd)) {
            return false;
        }
        
        return true;
    }
}
