package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;
import java.util.Set;
import org.openflow.protocol.OFPort;
import java.util.Map;
import java.util.LinkedList;

public class OFMatchOxm extends OFMatch implements org.openflow.protocol.interfaces.OFMatch, org.openflow.protocol.interfaces.OFMatchOxm {
    public static int MINIMUM_LENGTH = 4;
    public static int CORE_LENGTH = 0;

    private Map<org.openflow.protocol.interfaces.OFOxmMatchFields, org.openflow.protocol.interfaces.OFOxm> index = 
		new ConcurrentHashMap<org.openflow.protocol.interfaces.OFOxmMatchFields, org.openflow.protocol.interfaces.OFOxm>();
	List<org.openflow.protocol.interfaces.OFOxm>  oxm_fields;

    public OFMatchOxm() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMatchType.valueOf((short)1));
    }
    
    public OFMatchOxm(OFMatchOxm other) {
    	super(other);
		this.oxm_fields = (other.oxm_fields == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
		for ( org.openflow.protocol.interfaces.OFOxm i : other.oxm_fields ) { this.oxm_fields.add( i.dup() ); }
    }

	public void addOxmToIndex(org.openflow.protocol.interfaces.OFOxm oxm) {
		index.put(OFOxmMatchFields.to(OFOxmMatchFields.valueOf(oxm.getField())), oxm);
	}

	@org.codehaus.jackson.annotate.JsonIgnore	
	public org.openflow.protocol.interfaces.OFOxm getOxmFromIndex(org.openflow.protocol.interfaces.OFOxmMatchFields clazz) {
		return index.get(clazz);
	}
	
	public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() {
		return this.oxm_fields;
	}
	
	public OFMatchOxm setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> oxm_fields) {
		this.oxm_fields = oxm_fields;
		for ( org.openflow.protocol.interfaces.OFOxm oxm : this.oxm_fields ) {
			addOxmToIndex(oxm);
		}
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isOxmFieldsSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
		throw new UnsupportedOperationException("setWildcards is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() {
		throw new UnsupportedOperationException("getWildcards is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isWildcardsSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setWildcards(org.openflow.protocol.interfaces.OFFlowWildcards ... value) {
		throw new UnsupportedOperationException("setWildcards is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setWildcardsWire(int value) {
		throw new UnsupportedOperationException("setWildcardsWire is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getWildcardsWire() {
		throw new UnsupportedOperationException("getWildcardsWire is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isWildcardsWireSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setInputPort(OFPort value) {
		throw new UnsupportedOperationException("setInputPort is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFPort getInputPort() {
		throw new UnsupportedOperationException("getInputPort is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isInputPortSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setDataLayerSource(byte[] value) {
		throw new UnsupportedOperationException("setDataLayerSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerSource() {
		throw new UnsupportedOperationException("getDataLayerSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataLayerSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setDataLayerDestination(byte[] value) {
		throw new UnsupportedOperationException("setDataLayerDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte[] getDataLayerDestination() {
		throw new UnsupportedOperationException("getDataLayerDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataLayerDestinationSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setDataLayerVirtualLan(short value) {
		throw new UnsupportedOperationException("setDataLayerVirtualLan is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerVirtualLan() {
		throw new UnsupportedOperationException("getDataLayerVirtualLan is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataLayerVirtualLanSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setDataLayerVirtualLanPriorityCodePoint(byte value) {
		throw new UnsupportedOperationException("setDataLayerVirtualLanPriorityCodePoint is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getDataLayerVirtualLanPriorityCodePoint() {
		throw new UnsupportedOperationException("getDataLayerVirtualLanPriorityCodePoint is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataLayerVirtualLanPriorityCodePointSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setDataLayerType(short value) {
		throw new UnsupportedOperationException("setDataLayerType is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getDataLayerType() {
		throw new UnsupportedOperationException("getDataLayerType is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDataLayerTypeSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setNetworkTypeOfService(byte value) {
		throw new UnsupportedOperationException("setNetworkTypeOfService is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkTypeOfService() {
		throw new UnsupportedOperationException("getNetworkTypeOfService is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNetworkTypeOfServiceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setNetworkProtocol(byte value) {
		throw new UnsupportedOperationException("setNetworkProtocol is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getNetworkProtocol() {
		throw new UnsupportedOperationException("getNetworkProtocol is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNetworkProtocolSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setNetworkSource(int value) {
		throw new UnsupportedOperationException("setNetworkSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkSource() {
		throw new UnsupportedOperationException("getNetworkSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNetworkSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setNetworkDestination(int value) {
		throw new UnsupportedOperationException("setNetworkDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getNetworkDestination() {
		throw new UnsupportedOperationException("getNetworkDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNetworkDestinationSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setTransportSource(short value) {
		throw new UnsupportedOperationException("setTransportSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportSource() {
		throw new UnsupportedOperationException("getTransportSource is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTransportSourceSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFMatchOxm setTransportDestination(short value) {
		throw new UnsupportedOperationException("setTransportDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public short getTransportDestination() {
		throw new UnsupportedOperationException("getTransportDestination is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTransportDestinationSupported() {
		return false;
	}
	
	
	public static class Builder 
	implements	org.openflow.protocol.interfaces.OFMatch.Builder {
	
		static byte[] mac_mask = { (byte)0xff, (byte)0xff, (byte)0xff, 
								   (byte)0xff, (byte)0xff, (byte)0xff };
		
		private OFMatchOxm object;
		
		public Builder() {
			this.object = new OFMatchOxm();
		}
		
		public Builder setValue(org.openflow.protocol.interfaces.OFOxmMatchFields match_field, byte mask, byte[] data) {
			return setValue(OFOxmMatchFields.from(match_field), mask, data);
		}
		
		public Builder setValue(OFOxmMatchFields match_field, byte mask, byte[] data) {
			List<org.openflow.protocol.interfaces.OFOxm> oxm_fields = object.getOxmFields();
			if ( oxm_fields == null ) {
				oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
				object.setOxmFields(oxm_fields);
			}
			
			OFOxm oxm = new OFOxm();
			oxm.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
			oxm.setField(match_field.getValue()); //OFOxmMatchFields.
			oxm.setBitmask(mask);
			oxm.setData(data);
			oxm.setPayloadLength((byte) data.length);

			oxm_fields.add( oxm );
			object.addOxmToIndex( oxm );
			
			return this;
		}
		
		public org.openflow.protocol.interfaces.OFOxm getValue(org.openflow.protocol.interfaces.OFOxmMatchFields match_field) {
			return object.getOxmFromIndex(match_field);
		}
		
		public boolean isSetValueSupported() { return true; }
		
		public Builder setOxmFields(List<org.openflow.protocol.interfaces.OFOxm> oxm_fields) {
			this.object.setOxmFields(oxm_fields);
			return this;
		}
		
		public List<org.openflow.protocol.interfaces.OFOxm> getOxmFields() {
			return this.object.getOxmFields();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isOxmFieldsSupported() {
			return true;
		}
		
		public Builder setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) {
			throw new UnsupportedOperationException("setWildcards(Set<org.openflow.protocol.interfaces.OFFlowWildcards> value) is not supported for this version.");
		}
		
		public Set<org.openflow.protocol.interfaces.OFFlowWildcards> getWildcards() {
			throw new UnsupportedOperationException("getWildcards is not supported for this version.");
		}
		
		public boolean isWildcardsSupported() {
			return false;
		}
		
		public Builder setWildcards(org.openflow.protocol.interfaces.OFFlowWildcards ... value) {
			throw new UnsupportedOperationException("setWildcards(org.openflow.protocol.interfaces.OFFlowWildcards ... value) is not supported for this version.");
		}
		
		public Builder setWildcardsWire(int value) {
			throw new UnsupportedOperationException("setWildcardsWire(int value) is not supported for this version.");
		}
		
		public int getWildcardsWire() {
			throw new UnsupportedOperationException("getWildcardsWire is not supported for this version.");
		}
		
		public boolean isWildcardsWireSupported() {
			return false;
		}
		
		public Builder setInputPort(OFPort value) {
			return setValue(OFOxmMatchFields.OFB_IN_PORT, (byte) 0, ByteBuffer.allocate(4).putInt(value.get()).array());
		}
		
		public OFPort getInputPort() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IN_PORT);
			if ( oxm == null || oxm.getData() == null ) {
				return null;
			}
			return OFPort.of(ByteBuffer.wrap(oxm.getData()).getInt());
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isInputPortSupported() {
			return true;
		}
		
		public Builder setDataLayerSource(byte[] value) {
			byte[] mac = java.util.Arrays.copyOf(value, value.length + mac_mask.length);
			System.arraycopy(mac_mask, 0, mac, value.length, mac_mask.length);
			return setValue(OFOxmMatchFields.OFB_ETH_SRC, (byte) 1, mac);
		}
		
		public byte[] getDataLayerSource() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ETH_SRC);
			if ( oxm == null || oxm.getData() == null ) {
				return null;
			}
			byte[] ret = new byte[6];
			System.arraycopy(oxm.getData(), 0, ret, 0, 6);
			return ret;
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isDataLayerSourceSupported() {
			return true;
		}
		
		public Builder setDataLayerDestination(byte[] value) {
			byte[] mac = java.util.Arrays.copyOf(value, value.length + mac_mask.length);
			System.arraycopy(mac_mask, 0, mac, value.length, mac_mask.length);
			return setValue(OFOxmMatchFields.OFB_ETH_DST, (byte) 1, mac);
		}
		
		public byte[] getDataLayerDestination() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ETH_DST);
			if ( oxm == null || oxm.getData() == null ) {
				return null;
			}
			byte[] ret = new byte[6];
			System.arraycopy(oxm.getData(), 0, ret, 0, 6);
			return ret;
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isDataLayerDestinationSupported() {
			return true;
		}
		
		public Builder setDataLayerVirtualLan(short value) {
			return setValue(OFOxmMatchFields.OFB_VLAN_VID, (byte) 0, ByteBuffer.allocate(2).putShort(value).array());
		}
		
		public short getDataLayerVirtualLan() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_PCP);
			if ( oxm == null || oxm.getData() == null ) {
				return (short) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getShort();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isDataLayerVirtualLanSupported() {
			return true;
		}
		
		public Builder setDataLayerVirtualLanPriorityCodePoint(byte value) {
			return setValue(OFOxmMatchFields.OFB_VLAN_PCP, (byte) 0, new byte[] { value });
		}
		
		public byte getDataLayerVirtualLanPriorityCodePoint() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_PCP);
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return oxm.getData()[0];
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isDataLayerVirtualLanPriorityCodePointSupported() {
			return true;
		}
		
		public Builder setDataLayerType(short value) {
			return setValue(OFOxmMatchFields.OFB_ETH_TYPE, (byte) 0, ByteBuffer.allocate(2).putShort(value).array());
		}
		
		public short getDataLayerType() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_PCP);
			if ( oxm == null || oxm.getData() == null ) {
				return (short) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getShort();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isDataLayerTypeSupported() {
			return true;
		}
		
		public Builder setNetworkTypeOfService(byte value) {
			return setValue(OFOxmMatchFields.OFB_IP_DSCP, (byte) 0, new byte[] { value });
		}
		
		public byte getNetworkTypeOfService() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IP_DSCP);
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return oxm.getData()[0];
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isNetworkTypeOfServiceSupported() {
			return true;
		}
		
		public Builder setNetworkProtocol(byte value) {
			return setValue(OFOxmMatchFields.OFB_IP_PROTO, (byte) 0, new byte[] { value });
		}
		
		public byte getNetworkProtocol() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IP_PROTO);
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return oxm.getData()[0];
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isNetworkProtocolSupported() {
			return true;
		}
		
		public Builder setNetworkSource(int value) {
			return setValue(OFOxmMatchFields.OFB_IPV4_SRC, (byte) 1, ByteBuffer.allocate(8).putInt(value).putInt(0xffffffff).array());
		}
		
		public int getNetworkSource() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV4_SRC);
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getInt();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isNetworkSourceSupported() {
			return true;
		}
		
		public Builder setNetworkDestination(int value) {
			return setValue(OFOxmMatchFields.OFB_IPV4_DST, (byte) 1, ByteBuffer.allocate(8).putInt(value).putInt(0xffffffff).array());
		}
		
		public int getNetworkDestination() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_IPV4_DST);
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getInt();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isNetworkDestinationSupported() {
			return true;
		}
		
		public Builder setTransportSource(short source) {
			byte network_protocol = this.getNetworkProtocol();
			switch ( network_protocol ) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_SRC, (byte) 0, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_SRC, (byte) 0, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 132:
				return setValue(OFOxmMatchFields.OFB_SCTP_SRC, (byte) 0, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 1:
				return setValue(OFOxmMatchFields.OFB_ICMPV4_TYPE, (byte) 0, new byte[] { (byte)source });
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + network_protocol);
			}
		}
		
		public short getTransportSource() {
			byte network_protocol = this.getNetworkProtocol();
			org.openflow.protocol.interfaces.OFOxmMatchFields match_field = null;
			switch ( network_protocol ) {
			case (byte) 6:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_TCP_SRC;
				break;
			case (byte) 17:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_UDP_SRC;
				break;
			case (byte) 132:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_SCTP_SRC;
				break;
			case (byte) 1:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV4_CODE;
				break;
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + network_protocol);
			}
			org.openflow.protocol.interfaces.OFOxm oxm = getValue(match_field);
			if ( oxm == null || oxm.getData() == null ) {
				return (short) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getShort();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isTransportSourceSupported() {
			return true;
		}
		
		public Builder setTransportDestination(short destination) {
			byte network_protocol = this.getNetworkProtocol();
			switch ( network_protocol ) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_DST, (byte) 0, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_DST, (byte) 0, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 132:
				return setValue(OFOxmMatchFields.OFB_SCTP_DST, (byte) 0, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 1:
				return setValue(OFOxmMatchFields.OFB_ICMPV4_CODE, (byte) 0, new byte[] { (byte)destination });
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + network_protocol);
			}
		}
		
		public short getTransportDestination() {
			byte network_protocol = this.getNetworkProtocol();
			org.openflow.protocol.interfaces.OFOxmMatchFields match_field = null;
			switch ( network_protocol ) {
			case (byte) 6:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_TCP_DST;
				break;
			case (byte) 17:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_UDP_DST;
				break;
			case (byte) 132:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_SCTP_DST;
				break;
			case (byte) 1:
				match_field = org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_ICMPV4_CODE;
				break;
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + network_protocol);
			}
			org.openflow.protocol.interfaces.OFOxm oxm = getValue(match_field);
			if ( oxm == null || oxm.getData() == null ) {
				return (short) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getShort();
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean isTransportDestinationSupported() {
			return true;
		}
		
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object.setLength(object.computeLength());
		}
	}
	
	public OFMatchOxm dup() {
		return new OFMatchOxm(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		if (this.oxm_fields == null) this.oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFOxm t = new OFOxm(); t.readFrom(data); this.oxm_fields.add(t); addOxmToIndex(t); __cnt -= (OFOxm.MINIMUM_LENGTH + t.getPayloadLength()); }
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
    	short len = (short)(CORE_LENGTH + super.computeLength());
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
