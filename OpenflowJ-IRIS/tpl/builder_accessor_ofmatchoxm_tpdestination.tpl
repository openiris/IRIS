		public Builder setTransportDestination(short destination) {
			if ( this.network_protocol == null ) {
				throw new IllegalStateException("Before calling setTransportDestination, you should first call setNetworkProtocol()");
			}
			switch (this.network_protocol) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_DST, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_DST, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 132:
				return setValue(OFOxmMatchFields.OFB_SCTP_DST, ByteBuffer.allocate(2).putShort(destination).array());
			case (byte) 1:
				return setValue(OFOxmMatchFields.OFB_ICMPV4_CODE, new byte[] { (byte)destination });
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + this.network_protocol);
			}
		}
		
		public boolean isTransportDestinationSupported() {
			return true;
		}
		