		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, ByteBuffer.allocate(2).putShort(value).array());
		}
		
		public short get${method_name}() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.OFB_VLAN_PCP);
			if ( oxm == null || oxm.getData() == null ) {
				return (short) 0;
			}
			return ByteBuffer.wrap(oxm.getData()).getShort();
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		