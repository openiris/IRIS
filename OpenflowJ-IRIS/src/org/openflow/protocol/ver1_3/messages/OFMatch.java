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
	
	public short getLength() {
		return this.length;
	}
	
	public OFMatch setLength(short length) {
		this.length = length;
		return this;
	}
			
	public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() {
		throw new UnsupportedOperationException("public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) is not supported operation");
	}
	
	public OFPort getInputPort() {
		throw new UnsupportedOperationException("public OFPort getInputPort() is not supported operation");
	}
	
	public OFMatch setInputPort(OFPort value) {
		throw new UnsupportedOperationException("public OFMatch setInputPort(OFPort value) is not supported operation");
	}
	
	public byte[] getDataLayerSource() {
		throw new UnsupportedOperationException("public byte[] getDataLayerSource() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setDataLayerSource(byte[] value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerSource(byte[] value) is not supported operation");
	}
	
	public byte[] getDataLayerDestination() {
		throw new UnsupportedOperationException("public byte[] getDataLayerDestination() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setDataLayerDestination(byte[] value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerDestination(byte[] value) is not supported operation");
	}
	
	public short getDataLayerVirtualLan() {
		throw new UnsupportedOperationException("public short getDataLayerVirtualLan() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLan(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLan(short value) is not supported operation");
	}
	
	public byte getDataLayerVirtualLanPriorityCodePoint() {
		throw new UnsupportedOperationException("public byte getDataLayerVirtualLanPriorityCodePoint() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value) is not supported operation");
	}
	
	public short getDataLayerType() {
		throw new UnsupportedOperationException("public short getDataLayerType() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setDataLayerType(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerType(short value) is not supported operation");
	}
	
	public byte getNetworkTypeOfService() {
		throw new UnsupportedOperationException("public byte getNetworkTypeOfService() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setNetworkTypeOfService(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkTypeOfService(byte value) is not supported operation");
	}
	
	public byte getNetworkProtocol() {
		throw new UnsupportedOperationException("public byte getNetworkProtocol() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setNetworkProtocol(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkProtocol(byte value) is not supported operation");
	}
	
	public int getNetworkSource() {
		throw new UnsupportedOperationException("public int getNetworkSource() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setNetworkSource(int value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkSource(int value) is not supported operation");
	}
	
	public int getNetworkDestination() {
		throw new UnsupportedOperationException("public int getNetworkDestination() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setNetworkDestination(int value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkDestination(int value) is not supported operation");
	}
	
	public short getTransportSource() {
		throw new UnsupportedOperationException("public short getTransportSource() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setTransportSource(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setTransportSource(short value) is not supported operation");
	}
	
	public short getTransportDestination() {
		throw new UnsupportedOperationException("public short getTransportDestination() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatch setTransportDestination(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setTransportDestination(short value) is not supported operation");
	}
	
	public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() {
		throw new UnsupportedOperationException("public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() is not supported operation");
	}
	
	public org.openflow.protocol.interfaces.OFMatchOxm setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatchOxm setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> value) is not supported operation");
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
