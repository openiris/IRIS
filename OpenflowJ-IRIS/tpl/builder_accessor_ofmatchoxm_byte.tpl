		public Builder ${signature} {
			$prerequisite
			return setValue(OFOxmMatchFields.${match_field}, (byte) ${mask}, new byte[] { value });
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		