package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.List;
import java.util.LinkedList;
import org.openflow.protocol.ver1_3.types.*;

public class OFHello extends OFMessage  {
    public static int MINIMUM_LENGTH = 8;

    List<OFHelloElem>  elements;

    public OFHello() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)0));
		this.elements = new LinkedList<OFHelloElem>();
    }
    
    public OFHello(OFHello other) {
    	super(other);
		this.elements = (other.elements == null)? null: new LinkedList<OFHelloElem>();
		for ( OFHelloElem i : other.elements ) { this.elements.add( new OFHelloElem(i) ); }
    }

	public List<OFHelloElem> getElements() {
		return this.elements;
	}
	
	public OFHello setElements(List<OFHelloElem> elements) {
		this.elements = elements;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.elements == null) this.elements = new LinkedList<OFHelloElem>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFHelloElem t = OFHelloElemType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.elements.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.elements != null ) for (OFHelloElem t: this.elements) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFHello-"+":elements=" + elements.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	for ( OFHelloElem i : this.elements ) { len += i.computeLength(); }
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
        		
		final int prime = 2423;
		int result = super.hashCode() * prime;
		result = prime * result + ((elements == null)?0:elements.hashCode());
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
        OFHello other = (OFHello) obj;
		if ( elements == null && other.elements != null ) { return false; }
		else if ( !elements.equals(other.elements) ) { return false; }
        return true;
    }
}
