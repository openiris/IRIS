package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFQueuePropertyNone extends OFQueueProperty  {
    public static int MINIMUM_LENGTH = 8;

    

    public OFQueuePropertyNone() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFQueuePropertyType.valueOf((int)0));
    }
    
    public OFQueuePropertyNone(OFQueuePropertyNone other) {
    	super(other);
    }



    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        
    }

    public String toString() {
        return super.toString() +  ":OFQueuePropertyNone";
    }
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2503;
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
        if (!(obj instanceof OFQueuePropertyNone)) {
            return false;
        }
        
        return true;
    }
}
