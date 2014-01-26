		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, ByteBuffer.allocate(4).putInt(value.get()).array());
		}
		
		public OFPort get${method_name}() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.${match_field});
			if ( oxm == null || oxm.getData() == null ) {
				return null;
			}
			return OFPort.of(ByteBuffer.allocate(4).put(oxm.getData()).getInt());
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		