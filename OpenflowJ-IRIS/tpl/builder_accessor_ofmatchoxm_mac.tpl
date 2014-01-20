		public Builder ${signature} {
			byte[] mac = java.util.Arrays.copyOf(value, value.length + mac_mask.length);
			System.arraycopy(mac_mask, 0, mac, value.length, mac_mask.length);
			return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, mac);
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		