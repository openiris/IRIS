		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, new byte[] { value });
		}
		
		public byte get${method_name}() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.${match_field});
			if ( oxm == null || oxm.getData() == null ) {
				return (byte) 0;
			}
			return oxm.getData()[0];
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		