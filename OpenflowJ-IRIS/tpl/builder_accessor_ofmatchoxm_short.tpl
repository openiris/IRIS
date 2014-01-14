		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, ByteBuffer.allocate(2).putShort(value).array());
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		