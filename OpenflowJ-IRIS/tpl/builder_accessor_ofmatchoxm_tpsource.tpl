		public Builder setTransportSource(short source) {
			byte network_protocol = this.getNetworkProtocol();
			switch ( network_protocol ) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_SRC, (byte) ${mask}, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_SRC, (byte) ${mask}, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 132:
				return setValue(OFOxmMatchFields.OFB_SCTP_SRC, (byte) ${mask}, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 1:
				return setValue(OFOxmMatchFields.OFB_ICMPV4_TYPE, (byte) ${mask}, new byte[] { (byte)source });
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
		