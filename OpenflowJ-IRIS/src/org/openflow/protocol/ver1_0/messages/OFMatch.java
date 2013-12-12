package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFMatch    {
    public static int MINIMUM_LENGTH = 40;

    OFFlowWildcards  wildcards;
	short  input_port;
	byte[]  data_layer_source;
	byte[]  data_layer_destination;
	short  data_layer_virtual_lan;
	byte  data_layer_virtual_lan_priority_code_point;
	byte pad_1th;
	short  data_layer_type;
	byte  network_type_of_service;
	byte  network_protocol;
	short pad_2th;
	int  network_source;
	int  network_destination;
	short  transport_source;
	short  transport_destination;

    public OFMatch() {
        data_layer_source = new byte[6];
		data_layer_destination = new byte[6];
    }
    
    public OFMatch(OFMatch other) {
    	this.wildcards = other.wildcards;
		this.input_port = other.input_port;
		if (other.data_layer_source != null) { this.data_layer_source = java.util.Arrays.copyOf(other.data_layer_source, other.data_layer_source.length); }
		if (other.data_layer_destination != null) { this.data_layer_destination = java.util.Arrays.copyOf(other.data_layer_destination, other.data_layer_destination.length); }
		this.data_layer_virtual_lan = other.data_layer_virtual_lan;
		this.data_layer_virtual_lan_priority_code_point = other.data_layer_virtual_lan_priority_code_point;
		this.data_layer_type = other.data_layer_type;
		this.network_type_of_service = other.network_type_of_service;
		this.network_protocol = other.network_protocol;
		this.network_source = other.network_source;
		this.network_destination = other.network_destination;
		this.transport_source = other.transport_source;
		this.transport_destination = other.transport_destination;
    }

	public int getWildcards() {
		return this.wildcards.getValue();
	}
	
	public OFMatch setWildcards(int wildcards) {
		if (this.wildcards == null) this.wildcards = new OFFlowWildcards();
		this.wildcards.setValue( wildcards );
		return this;
	}
	public short getInputPort() {
		return this.input_port;
	}
	
	public OFMatch setInputPort(short input_port) {
		this.input_port = input_port;
		return this;
	}
			
	public byte[] getDataLayerSource() {
		return this.data_layer_source;
	}
	
	public OFMatch setDataLayerSource(byte[] data_layer_source) {
		this.data_layer_source = data_layer_source;
		return this;
	}
			
	public byte[] getDataLayerDestination() {
		return this.data_layer_destination;
	}
	
	public OFMatch setDataLayerDestination(byte[] data_layer_destination) {
		this.data_layer_destination = data_layer_destination;
		return this;
	}
			
	public short getDataLayerVirtualLan() {
		return this.data_layer_virtual_lan;
	}
	
	public OFMatch setDataLayerVirtualLan(short data_layer_virtual_lan) {
		this.data_layer_virtual_lan = data_layer_virtual_lan;
		return this;
	}
			
	public byte getDataLayerVirtualLanPriorityCodePoint() {
		return this.data_layer_virtual_lan_priority_code_point;
	}
	
	public OFMatch setDataLayerVirtualLanPriorityCodePoint(byte data_layer_virtual_lan_priority_code_point) {
		this.data_layer_virtual_lan_priority_code_point = data_layer_virtual_lan_priority_code_point;
		return this;
	}
			
	public short getDataLayerType() {
		return this.data_layer_type;
	}
	
	public OFMatch setDataLayerType(short data_layer_type) {
		this.data_layer_type = data_layer_type;
		return this;
	}
			
	public byte getNetworkTypeOfService() {
		return this.network_type_of_service;
	}
	
	public OFMatch setNetworkTypeOfService(byte network_type_of_service) {
		this.network_type_of_service = network_type_of_service;
		return this;
	}
			
	public byte getNetworkProtocol() {
		return this.network_protocol;
	}
	
	public OFMatch setNetworkProtocol(byte network_protocol) {
		this.network_protocol = network_protocol;
		return this;
	}
			
	public int getNetworkSource() {
		return this.network_source;
	}
	
	public OFMatch setNetworkSource(int network_source) {
		this.network_source = network_source;
		return this;
	}
			
	public int getNetworkDestination() {
		return this.network_destination;
	}
	
	public OFMatch setNetworkDestination(int network_destination) {
		this.network_destination = network_destination;
		return this;
	}
			
	public short getTransportSource() {
		return this.transport_source;
	}
	
	public OFMatch setTransportSource(short transport_source) {
		this.transport_source = transport_source;
		return this;
	}
			
	public short getTransportDestination() {
		return this.transport_destination;
	}
	
	public OFMatch setTransportDestination(short transport_destination) {
		this.transport_destination = transport_destination;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        if (this.wildcards == null) this.wildcards = new OFFlowWildcards();
		this.wildcards.setValue( OFFlowWildcards.readFrom(data) );
		this.input_port = data.getShort();
		if ( this.data_layer_source == null ) this.data_layer_source = new byte[6];
		data.get(this.data_layer_source);
		if ( this.data_layer_destination == null ) this.data_layer_destination = new byte[6];
		data.get(this.data_layer_destination);
		this.data_layer_virtual_lan = data.getShort();
		this.data_layer_virtual_lan_priority_code_point = data.get();
		this.pad_1th = data.get();
		this.data_layer_type = data.getShort();
		this.network_type_of_service = data.get();
		this.network_protocol = data.get();
		this.pad_2th = data.getShort();
		this.network_source = data.getInt();
		this.network_destination = data.getInt();
		this.transport_source = data.getShort();
		this.transport_destination = data.getShort();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.wildcards.getValue());
		data.putShort(this.input_port);
		if ( this.data_layer_source != null ) { data.put(this.data_layer_source); }
		if ( this.data_layer_destination != null ) { data.put(this.data_layer_destination); }
		data.putShort(this.data_layer_virtual_lan);
		data.put(this.data_layer_virtual_lan_priority_code_point);
		data.put(this.pad_1th);
		data.putShort(this.data_layer_type);
		data.put(this.network_type_of_service);
		data.put(this.network_protocol);
		data.putShort(this.pad_2th);
		data.putInt(this.network_source);
		data.putInt(this.network_destination);
		data.putShort(this.transport_source);
		data.putShort(this.transport_destination);
    }

    public String toString() {
        return  ":OFMatch-"+":wildcards=" + wildcards.toString() + 
		":input_port=" + U16.f(input_port) + 
		":data_layer_source=" + java.util.Arrays.toString(data_layer_source) + 
		":data_layer_destination=" + java.util.Arrays.toString(data_layer_destination) + 
		":data_layer_virtual_lan=" + U16.f(data_layer_virtual_lan) + 
		":data_layer_virtual_lan_priority_code_point=" + U8.f(data_layer_virtual_lan_priority_code_point) + 
		":data_layer_type=" + U16.f(data_layer_type) + 
		":network_type_of_service=" + U8.f(network_type_of_service) + 
		":network_protocol=" + U8.f(network_protocol) + 
		":network_source=" + U32.f(network_source) + 
		":network_destination=" + U32.f(network_destination) + 
		":transport_source=" + U16.f(transport_source) + 
		":transport_destination=" + U16.f(transport_destination);
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
        		
		final int prime = 2731;
		int result = super.hashCode() * prime;
		result = prime * result + ((wildcards == null)?0:wildcards.hashCode());
		result = prime * result + (int) input_port;
		result = prime * result + ((data_layer_source == null)?0:java.util.Arrays.hashCode(data_layer_source));
		result = prime * result + ((data_layer_destination == null)?0:java.util.Arrays.hashCode(data_layer_destination));
		result = prime * result + (int) data_layer_virtual_lan;
		result = prime * result + (int) data_layer_virtual_lan_priority_code_point;
		result = prime * result + (int) data_layer_type;
		result = prime * result + (int) network_type_of_service;
		result = prime * result + (int) network_protocol;
		result = prime * result + (int) network_source;
		result = prime * result + (int) network_destination;
		result = prime * result + (int) transport_source;
		result = prime * result + (int) transport_destination;
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
		if ( wildcards == null && other.wildcards != null ) { return false; }
		else if ( !wildcards.equals(other.wildcards) ) { return false; }
		if ( input_port != other.input_port ) return false;
		if ( data_layer_source == null && other.data_layer_source != null ) { return false; }
		else if ( !java.util.Arrays.equals(data_layer_source, other.data_layer_source) ) { return false; }
		if ( data_layer_destination == null && other.data_layer_destination != null ) { return false; }
		else if ( !java.util.Arrays.equals(data_layer_destination, other.data_layer_destination) ) { return false; }
		if ( data_layer_virtual_lan != other.data_layer_virtual_lan ) return false;
		if ( data_layer_virtual_lan_priority_code_point != other.data_layer_virtual_lan_priority_code_point ) return false;
		if ( data_layer_type != other.data_layer_type ) return false;
		if ( network_type_of_service != other.network_type_of_service ) return false;
		if ( network_protocol != other.network_protocol ) return false;
		if ( network_source != other.network_source ) return false;
		if ( network_destination != other.network_destination ) return false;
		if ( transport_source != other.transport_source ) return false;
		if ( transport_destination != other.transport_destination ) return false;
        return true;
    }
}
