		public Builder ${signature} {
			$prerequisite
			return setValue(OFOxmMatchFields.${match_field}, new byte[] { value });
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		