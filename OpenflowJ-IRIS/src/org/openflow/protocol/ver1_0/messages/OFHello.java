package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import java.util.List;

public class OFHello extends OFMessage implements org.openflow.protocol.interfaces.OFHello {
    public static int MINIMUM_LENGTH = 8;

    

    public OFHello() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)0));
    }
    
    public OFHello(OFHello other) {
    	super(other);
    }

	@org.codehaus.jackson.annotate.JsonIgnore
	public OFHello setElements(List<org.openflow.protocol.interfaces.OFHelloElem> value) {
		throw new UnsupportedOperationException("setElements is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public List<org.openflow.protocol.interfaces.OFHelloElem> getElements() {
		throw new UnsupportedOperationException("getElements is not supported operation");
	}
	
	public boolean isElementsSupported() {
		return false;
	}
	
	
	
	
	public OFHello dup() {
		return new OFHello(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        
    }

    public String toString() {
        return super.toString() +  ":OFHello";
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
        		
		final int prime = 2971;
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
        if (!(obj instanceof OFHello)) {
            return false;
        }
        
        return true;
    }
}
