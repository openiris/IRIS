package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.util.OFPort;
import org.openflow.protocol.ver1_3.types.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OFMatchOxm extends OFMatch implements org.openflow.protocol.interfaces.OFMatch, org.openflow.protocol.interfaces.OFMatchOxm {
    public static int MINIMUM_LENGTH = 4;

    List<org.openflow.protocol.interfaces.OFOxm>  oxm_fields;

    public OFMatchOxm() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMatchType.valueOf((short)1));
    }
    
    public OFMatchOxm(OFMatchOxm other) {
    	super(other);
		this.oxm_fields = (other.oxm_fields == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
		for ( org.openflow.protocol.interfaces.OFOxm i : other.oxm_fields ) { this.oxm_fields.add( new OFOxm((OFOxm)i) ); }
    }

	public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() {
		return this.oxm_fields;
	}
	
	public OFMatchOxm setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> oxm_fields) {
		this.oxm_fields = oxm_fields;
		return this;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() {
		throw new UnsupportedOperationException("public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPort getInputPort() {
		throw new UnsupportedOperationException("public OFPort getInputPort() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatch setInputPort(OFPort value) {
		throw new UnsupportedOperationException("public OFMatch setInputPort(OFPort value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerSource() {
		throw new UnsupportedOperationException("public byte[] getDataLayerSource() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setDataLayerSource(byte[] value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerSource(byte[] value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerDestination() {
		throw new UnsupportedOperationException("public byte[] getDataLayerDestination() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setDataLayerDestination(byte[] value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerDestination(byte[] value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerVirtualLan() {
		throw new UnsupportedOperationException("public short getDataLayerVirtualLan() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLan(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLan(short value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getDataLayerVirtualLanPriorityCodePoint() {
		throw new UnsupportedOperationException("public byte getDataLayerVirtualLanPriorityCodePoint() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerType() {
		throw new UnsupportedOperationException("public short getDataLayerType() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setDataLayerType(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setDataLayerType(short value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkTypeOfService() {
		throw new UnsupportedOperationException("public byte getNetworkTypeOfService() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setNetworkTypeOfService(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkTypeOfService(byte value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkProtocol() {
		throw new UnsupportedOperationException("public byte getNetworkProtocol() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setNetworkProtocol(byte value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkProtocol(byte value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkSource() {
		throw new UnsupportedOperationException("public int getNetworkSource() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setNetworkSource(int value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkSource(int value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkDestination() {
		throw new UnsupportedOperationException("public int getNetworkDestination() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setNetworkDestination(int value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setNetworkDestination(int value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportSource() {
		throw new UnsupportedOperationException("public short getTransportSource() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setTransportSource(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setTransportSource(short value) is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportDestination() {
		throw new UnsupportedOperationException("public short getTransportDestination() is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public org.openflow.protocol.interfaces.OFMatch setTransportDestination(short value) {
		throw new UnsupportedOperationException("public org.openflow.protocol.interfaces.OFMatch setTransportDestination(short value) is not supported operation");
	}
	
	
	public class Builder 
	implements	org.openflow.util.Builder<org.openflow.protocol.interfaces.OFMatch>,
				org.openflow.protocol.interfaces.OFMatch.Builder, 
				org.openflow.protocol.interfaces.OFMatchOxm.Builder {
	
		private OFMatchOxm object;
		Byte network_protocol;
		
		public Builder() {
			this.object = new OFMatchOxm();
			this.network_protocol = null;
		}
		
		private Builder setValue(OFOxmMatchFields match_field, byte[] data) {
			List<org.openflow.protocol.interfaces.OFOxm> oxm_fields = object.getOxmFields();
			if ( oxm_fields == null ) {
				oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
				object.setOxmFields(oxm_fields);
			}
			
			OFOxm oxm = new OFOxm();
			oxm.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
			oxm.setField(match_field.getValue()); //OFOxmMatchFields.
			oxm.setBitmask((byte) 0);
			oxm.setData(data);
			oxm.setPayloadLength((byte) data.length);

			oxm_fields.add( oxm );
			
			return this;
		}
		
		public Builder setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> oxm_fields) {
			this.object.setOxmFields(oxm_fields);
			return this;
		}
		
		public Builder setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
			throw new UnsupportedOperationException("setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) is not supported for this version.");
		}
		
		public Builder setInputPort(OFPort value) {
			return setValue(OFOxmMatchFields.OFB_IN_PORT, ByteBuffer.allocate(4).putInt(value.get()).array());
		}
		public Builder setDataLayerSource(byte[] value) {
			return setValue(OFOxmMatchFields.OFB_ETH_SRC, value);
		}
		public Builder setDataLayerDestination(byte[] value) {
			return setValue(OFOxmMatchFields.OFB_ETH_DST, value);
		}
		public Builder setDataLayerVirtualLan(short value) {
			return setValue(OFOxmMatchFields.OFB_VLAN_VID, ByteBuffer.allocate(2).putShort(value).array());
		}
		public Builder setDataLayerVirtualLanPriorityCodePoint(byte value) {
			
			return setValue(OFOxmMatchFields.OFB_VLAN_PCP, new byte[] { value });
		}
		public Builder setDataLayerType(short value) {
			return setValue(OFOxmMatchFields.OFB_ETH_TYPE, ByteBuffer.allocate(2).putShort(value).array());
		}
		public Builder setNetworkTypeOfService(byte value) {
			
			return setValue(OFOxmMatchFields.OFB_IP_DSCP, new byte[] { value });
		}
		public Builder setNetworkProtocol(byte value) {
			this.network_protocol = value;
			return setValue(OFOxmMatchFields.OFB_IP_PROTO, new byte[] { value });
		}
		public Builder setNetworkSource(int value) {
			return setValue(OFOxmMatchFields.OFB_IPV4_SRC, ByteBuffer.allocate(4).putInt(value).array());
		}
		public Builder setNetworkDestination(int value) {
			return setValue(OFOxmMatchFields.OFB_IPV4_DST, ByteBuffer.allocate(4).putInt(value).array());
		}
		public Builder setTransportSource(short source) {
			if ( this.network_protocol == null ) {
				throw new IllegalStateException("Before calling setTransportSource, you should first call setNetworkProtocol()");
			}
			switch (this.network_protocol) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_SRC, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_SRC, ByteBuffer.allocate(2).putShort(source).array());
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + this.network_protocol);
			}
		}
		public Builder setTransportDestination(short destination) {
			if ( this.network_protocol == null ) {
				throw new IllegalStateException("Before calling setTransportDestination, you should first call setNetworkProtocol()");
			}
			switch (this.network_protocol) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_DST, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_DST, ByteBuffer.allocate(2).putShort(destination).array());
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + this.network_protocol);
			}
		}
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object;
		}
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.oxm_fields == null) this.oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFOxm t = new OFOxm(); t.readFrom(data); this.oxm_fields.add(t); __cnt -= (OFOxm.MINIMUM_LENGTH + t.getPayloadLength()); }
		int __align = alignment(getLength(), 8);
		for (int i = 0; i < __align; ++i ) { data.get(); }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        if (this.oxm_fields != null ) for (org.openflow.protocol.interfaces.OFOxm t: this.oxm_fields) { t.writeTo(data); }
		int __align = alignment(computeLength(), 8);
		for (int i = 0; i < __align; ++i ) { data.put((byte)0); }
    }

    public String toString() {
        return super.toString() +  ":OFMatchOxm-"+":oxm_fields=" + oxm_fields.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.oxm_fields != null ) for ( org.openflow.protocol.interfaces.OFOxm i : this.oxm_fields ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 8));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2297;
		int result = super.hashCode() * prime;
		result = prime * result + ((oxm_fields == null)?0:oxm_fields.hashCode());
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
        if (!(obj instanceof OFMatchOxm)) {
            return false;
        }
        OFMatchOxm other = (OFMatchOxm) obj;
		if ( oxm_fields == null && other.oxm_fields != null ) { return false; }
		else if ( !oxm_fields.equals(other.oxm_fields) ) { return false; }
        return true;
    }
}
