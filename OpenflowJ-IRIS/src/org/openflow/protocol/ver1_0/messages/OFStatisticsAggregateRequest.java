package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import org.openflow.protocol.OFPort;

public class OFStatisticsAggregateRequest extends OFStatisticsRequest implements org.openflow.protocol.interfaces.OFStatisticsAggregateRequest {
    public static int MINIMUM_LENGTH = 56;
    public static int CORE_LENGTH = 44;

    org.openflow.protocol.interfaces.OFMatch  match;
	byte  table_id;
	byte pad_1th;
	short  out_port;

    public OFStatisticsAggregateRequest() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)16));
		setStatisticsType(OFStatisticsType.valueOf((short)2, this.type));
    }
    
    public OFStatisticsAggregateRequest(OFStatisticsAggregateRequest other) {
    	super(other);
		this.match = new OFMatch((OFMatch)other.match);
		this.table_id = other.table_id;
		this.out_port = other.out_port;
    }

	public org.openflow.protocol.interfaces.OFMatch getMatch() {
		return this.match;
	}
	
	public OFStatisticsAggregateRequest setMatch(org.openflow.protocol.interfaces.OFMatch match) {
		this.match = match;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMatchSupported() {
		return true;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFStatisticsAggregateRequest setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTableIdSupported() {
		return true;
	}
			
	public OFPort getOutPort() {
		return OFPort.of(this.out_port);
	}
	
	public OFStatisticsAggregateRequest setOutPort(OFPort port) {
		this.out_port = (short) port.get();
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isOutPortSupported() {
		return true;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFStatisticsAggregateRequest setOutGroup(int value) {
		throw new UnsupportedOperationException("setOutGroup is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getOutGroup() {
		throw new UnsupportedOperationException("getOutGroup is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isOutGroupSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFStatisticsAggregateRequest setCookie(long value) {
		throw new UnsupportedOperationException("setCookie is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public long getCookie() {
		throw new UnsupportedOperationException("getCookie is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isCookieSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFStatisticsAggregateRequest setCookieMask(long value) {
		throw new UnsupportedOperationException("setCookieMask is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public long getCookieMask() {
		throw new UnsupportedOperationException("getCookieMask is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isCookieMaskSupported() {
		return false;
	}
	
	
	
	
	public OFStatisticsAggregateRequest dup() {
		return new OFStatisticsAggregateRequest(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		if (this.match == null) this.match = new OFMatch();
		this.match.readFrom(data);
		this.table_id = data.get();
		this.pad_1th = data.get();
		this.out_port = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        match.writeTo(data);
		data.put(this.table_id);
		data.put(this.pad_1th);
		data.putShort(this.out_port);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsAggregateRequest-"+":match=" + match + 
		":table_id=" + U8.f(table_id) + 
		":out_port=" + U16.f(out_port);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
        		
		final int prime = 2609;
		int result = super.hashCode() * prime;
		result = prime * result + ((match == null)?0:match.hashCode());
		result = prime * result + (int) table_id;
		result = prime * result + (int) out_port;
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
        if (!(obj instanceof OFStatisticsAggregateRequest)) {
            return false;
        }
        OFStatisticsAggregateRequest other = (OFStatisticsAggregateRequest) obj;
		if ( match == null && other.match != null ) { return false; }
		else if ( !match.equals(other.match) ) { return false; }
		if ( table_id != other.table_id ) return false;
		if ( out_port != other.out_port ) return false;
        return true;
    }
}
