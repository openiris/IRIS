		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, (byte) 1, ByteBuffer.allocate(8).putInt(value).putInt(0xffffffff).array());
		}
		
		public int get${method_name}() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.${match_field});
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return ByteBuffer.allocate(8).put(oxm.getData()).getInt();
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		