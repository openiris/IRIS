package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.util.OFPort;
import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFBucket   implements org.openflow.protocol.interfaces.OFBucket {
    public static int MINIMUM_LENGTH = 16;

    short  length;
	short  weight;
	int  watch_port;
	int  watch_group;
	int pad_1th;
	List<org.openflow.protocol.interfaces.OFAction>  actions;

    public OFBucket() {
        
    }
    
    public OFBucket(OFBucket other) {
    	this.length = other.length;
		this.weight = other.weight;
		this.watch_port = other.watch_port;
		this.watch_group = other.watch_group;
		this.actions = (other.actions == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		for ( org.openflow.protocol.interfaces.OFAction i : other.actions ) { this.actions.add( new OFAction((OFAction)i) ); }
    }

	public short getLength() {
		return this.length;
	}
	
	public OFBucket setLength(short length) {
		this.length = length;
		return this;
	}
	
	public boolean isLengthSupported() {
		return true;
	}
			
	public short getWeight() {
		return this.weight;
	}
	
	public OFBucket setWeight(short weight) {
		this.weight = weight;
		return this;
	}
	
	public boolean isWeightSupported() {
		return true;
	}
			
	public OFPort getWatchPort() {
		return new OFPort(this.watch_port);
	}
	
	public OFBucket setWatchPort(OFPort port) {
		this.watch_port = (int) port.get();
		return this;
	}
	
	public boolean isWatchPortSupported() {
		return true;
	}
	
	public int getWatchGroup() {
		return this.watch_group;
	}
	
	public OFBucket setWatchGroup(int watch_group) {
		this.watch_group = watch_group;
		return this;
	}
	
	public boolean isWatchGroupSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFAction> getActions() {
		return this.actions;
	}
	
	public OFBucket setActions(List<org.openflow.protocol.interfaces.OFAction> actions) {
		this.actions = actions;
		return this;
	}
	
	public boolean isActionsSupported() {
		return true;
	}
			
	
	
	
	public OFBucket dup() {
		return new OFBucket(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.length = data.getShort();
		this.weight = data.getShort();
		this.watch_port = data.getInt();
		this.watch_group = data.getInt();
		this.pad_1th = data.getInt();
		if (this.actions == null) this.actions = new LinkedList<org.openflow.protocol.interfaces.OFAction>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFAction t = OFActionType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.actions.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.length);
		data.putShort(this.weight);
		data.putInt(this.watch_port);
		data.putInt(this.watch_group);
		data.putInt(this.pad_1th);
		if (this.actions != null ) for (org.openflow.protocol.interfaces.OFAction t: this.actions) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFBucket-"+":length=" + U16.f(length) + 
		":weight=" + U16.f(weight) + 
		":watch_port=" + U32.f(watch_port) + 
		":watch_group=" + U32.f(watch_group) + 
		":actions=" + actions.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.actions != null ) for ( org.openflow.protocol.interfaces.OFAction i : this.actions ) { len += i.computeLength(); }
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
        		
		final int prime = 2029;
		int result = super.hashCode() * prime;
		result = prime * result + (int) length;
		result = prime * result + (int) weight;
		result = prime * result + (int) watch_port;
		result = prime * result + (int) watch_group;
		result = prime * result + ((actions == null)?0:actions.hashCode());
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
        if (!(obj instanceof OFBucket)) {
            return false;
        }
        OFBucket other = (OFBucket) obj;
		if ( length != other.length ) return false;
		if ( weight != other.weight ) return false;
		if ( watch_port != other.watch_port ) return false;
		if ( watch_group != other.watch_group ) return false;
		if ( actions == null && other.actions != null ) { return false; }
		else if ( !actions.equals(other.actions) ) { return false; }
        return true;
    }
}
