		public Builder ${signature} {
			// byte[] mac = java.util.Arrays.copyOf(value, value.length + mac_mask.length);
			// System.arraycopy(mac_mask, 0, mac, value.length, mac_mask.length);
			// return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, mac);
			return setValue(OFOxmMatchFields.${match_field}, (byte) 0, value);
		}
		
		public byte[] get${method_name}() {
			org.openflow.protocol.interfaces.OFOxm oxm = 
					getValue(org.openflow.protocol.interfaces.OFOxmMatchFields.${match_field});
			if ( oxm == null || oxm.getData() == null ) {
				return null;
			}
			byte[] ret = new byte[6];
			System.arraycopy(oxm.getData(), 0, ret, 0, 6);
			return ret;
		}
		
		@org.codehaus.jackson.annotate.JsonIgnore
		public boolean is${method_name}Supported() {
			return true;
		}
		