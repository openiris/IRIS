package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.util.OFPort;
import org.openflow.protocol.ver1_3.types.*;
import java.util.List;
import java.util.Set;

public class OFMatch   implements org.openflow.protocol.interfaces.OFMatch {
    public static int MINIMUM_LENGTH = 4;

    OFMatchType  type;
	short  length;

    public OFMatch() {
        
    }
    
    public OFMatch(OFMatch other) {
    	this.type = other.type;
		this.length = other.length;
    }

	public org.openflow.protocol.interfaces.OFMatchType getType() {
		return OFMatchType.to(this.type);
	}
	
	public OFMatch setType(org.openflow.protocol.interfaces.OFMatchType type) {
		this.type = OFMatchType.from(type);
		return this;
	}
	
	public OFMatch setType(OFMatchType type) {
		this.type = type;
		return this;
	}
	
	public boolean isTypeSupported() {
		return true;
	}
	
	public short getLength() {
		return this.length;
	}
	
	public OFMatch setLength(short length) {
		this.length = length;
		return this;
	}
	
	public boolean isLengthSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() {
		throw new UnsupportedOperationException("getWildcards is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
		throw new UnsupportedOperationException("setWildcards is not supported operation");
	}
	
	public boolean isWildcardsSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPort getInputPort() {
		throw new UnsupportedOperationException("getInputPort is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setInputPort(OFPort value) {
		throw new UnsupportedOperationException("setInputPort is not supported operation");
	}
	
	public boolean isInputPortSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerSource() {
		throw new UnsupportedOperationException("getDataLayerSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setDataLayerSource(byte[] value) {
		throw new UnsupportedOperationException("setDataLayerSource is not supported operation");
	}
	
	public boolean isDataLayerSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerDestination() {
		throw new UnsupportedOperationException("getDataLayerDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setDataLayerDestination(byte[] value) {
		throw new UnsupportedOperationException("setDataLayerDestination is not supported operation");
	}
	
	public boolean isDataLayerDestinationSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerVirtualLan() {
		throw new UnsupportedOperationException("getDataLayerVirtualLan is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setDataLayerVirtualLan(short value) {
		throw new UnsupportedOperationException("setDataLayerVirtualLan is not supported operation");
	}
	
	public boolean isDataLayerVirtualLanSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getDataLayerVirtualLanPriorityCodePoint() {
		throw new UnsupportedOperationException("getDataLayerVirtualLanPriorityCodePoint is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value) {
		throw new UnsupportedOperationException("setDataLayerVirtualLanPriorityCodePoint is not supported operation");
	}
	
	public boolean isDataLayerVirtualLanPriorityCodePointSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerType() {
		throw new UnsupportedOperationException("getDataLayerType is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setDataLayerType(short value) {
		throw new UnsupportedOperationException("setDataLayerType is not supported operation");
	}
	
	public boolean isDataLayerTypeSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkTypeOfService() {
		throw new UnsupportedOperationException("getNetworkTypeOfService is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setNetworkTypeOfService(byte value) {
		throw new UnsupportedOperationException("setNetworkTypeOfService is not supported operation");
	}
	
	public boolean isNetworkTypeOfServiceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkProtocol() {
		throw new UnsupportedOperationException("getNetworkProtocol is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setNetworkProtocol(byte value) {
		throw new UnsupportedOperationException("setNetworkProtocol is not supported operation");
	}
	
	public boolean isNetworkProtocolSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkSource() {
		throw new UnsupportedOperationException("getNetworkSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setNetworkSource(int value) {
		throw new UnsupportedOperationException("setNetworkSource is not supported operation");
	}
	
	public boolean isNetworkSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkDestination() {
		throw new UnsupportedOperationException("getNetworkDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setNetworkDestination(int value) {
		throw new UnsupportedOperationException("setNetworkDestination is not supported operation");
	}
	
	public boolean isNetworkDestinationSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportSource() {
		throw new UnsupportedOperationException("getTransportSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setTransportSource(short value) {
		throw new UnsupportedOperationException("setTransportSource is not supported operation");
	}
	
	public boolean isTransportSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportDestination() {
		throw new UnsupportedOperationException("getTransportDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setTransportDestination(short value) {
		throw new UnsupportedOperationException("setTransportDestination is not supported operation");
	}
	
	public boolean isTransportDestinationSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() {
		throw new UnsupportedOperationException("getOxmFields is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> value) {
		throw new UnsupportedOperationException("setOxmFields is not supported operation");
	}
	
	public boolean isOxmFieldsSupported() {
		return false;
	}
	
	
	
	
    public void readFrom(ByteBuffer data) {
        this.type = OFMatchType.valueOf(OFMatchType.readFrom(data));
		this.length = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.type.getTypeValue());
		data.putShort(this.length);
    }

    public String toString() {
        return  ":OFMatch-"+":type=" + type.toString() + 
		":length=" + U16.f(length);
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
        		
		final int prime = 2311;
		int result = super.hashCode() * prime;
		result = prime * result + ((type == null)?0:type.hashCode());
		result = prime * result + (int) length;
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
        if (!(obj instanceof OFMatch)) {
            return false;
        }
        OFMatch other = (OFMatch) obj;
		if ( type == null && other.type != null ) { return false; }
		else if ( !type.equals(other.type) ) { return false; }
		if ( length != other.length ) return false;
        return true;
    }
}
