package org.openflow.util;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.OFOxm;
import org.openflow.protocol.ver1_3.types.OFOxmClass;
import org.openflow.protocol.ver1_3.types.OFOxmMatchFields;

public class OFMatchOxmUtil {
	
	public class Builder {
		
		org.openflow.protocol.ver1_3.messages.OFMatchOxm match_oxm;
		Byte network_protocol;
		
		private Builder() {
			this.match_oxm = new org.openflow.protocol.ver1_3.messages.OFMatchOxm();
			this.network_protocol = null;
		}
		
		private Builder(org.openflow.protocol.ver1_3.messages.OFMatchOxm match_oxm) {
			this.match_oxm = match_oxm;
			this.network_protocol = null;
		}
		
		private Builder setValue(OFOxmMatchFields match_field, byte[] data) {
			List<org.openflow.protocol.interfaces.OFOxm> oxm_fields = match_oxm.getOxmFields();
			if ( oxm_fields == null ) {
				oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
				match_oxm.setOxmFields(oxm_fields);
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
		
		public Builder setInputPort(OFPort value) {
			return setValue(OFOxmMatchFields.OFB_IN_PORT, ByteBuffer.allocate(4).putInt(value.get()).array());
		}

		public Builder setDataLayerSource(byte[] source) {
			return setValue(OFOxmMatchFields.OFB_ETH_SRC, source);
		}
		
		public Builder setDataLayerDestination(byte[] destination) {
			return setValue(OFOxmMatchFields.OFB_ETH_DST, destination);
		}
		
		public Builder setDataLayerVirtualLan(short vlan) {
			return setValue(OFOxmMatchFields.OFB_VLAN_VID, ByteBuffer.allocate(2).putShort(vlan).array());
		}
		
		public Builder setDataLayerVirtualLanPriorityCodePoint(byte pcp) {
			return setValue(OFOxmMatchFields.OFB_VLAN_PCP, new byte[] { pcp });
		}
		
		public Builder setDataLayerType(short type) {
			return setValue(OFOxmMatchFields.OFB_ETH_TYPE, ByteBuffer.allocate(2).putShort(type).array());
		}
		
		public Builder setNetworkProtocol(byte protocol) {
			this.network_protocol = protocol;
			return setValue(OFOxmMatchFields.OFB_IP_PROTO, new byte[] { protocol });
		}
		
		public Builder setNetworkSource(int source) {
			return setValue(OFOxmMatchFields.OFB_IPV4_SRC, ByteBuffer.allocate(4).putInt(source).array());
		}
		
		public Builder setNetworkDestination(int destination) {
			return setValue(OFOxmMatchFields.OFB_IPV4_DST, ByteBuffer.allocate(4).putInt(destination).array());
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
		
		public org.openflow.protocol.ver1_3.messages.OFMatchOxm build() {
			return match_oxm;
		}
	}
}
