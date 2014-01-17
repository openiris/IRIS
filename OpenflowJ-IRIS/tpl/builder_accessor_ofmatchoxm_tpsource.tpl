		public Builder setTransportSource(short source) {
			if ( this.network_protocol == null ) {
				throw new IllegalStateException("Before calling setTransportSource, you should first call setNetworkProtocol()");
			}
			switch (this.network_protocol) {
			case (byte) 6:
				return setValue(OFOxmMatchFields.OFB_TCP_SRC, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 17:
				return setValue(OFOxmMatchFields.OFB_UDP_SRC, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 132:
				return setValue(OFOxmMatchFields.OFB_SCTP_SRC, ByteBuffer.allocate(2).putShort(source).array());
			case (byte) 1:
				return setValue(OFOxmMatchFields.OFB_ICMPV4_TYPE, new byte[] { (byte)source });
			default:
				throw new IllegalStateException("Network protocol is wrongfully set to " + this.network_protocol);
			}
		}
		
		public boolean isTransportSourceSupported() {
			return true;
		}
		