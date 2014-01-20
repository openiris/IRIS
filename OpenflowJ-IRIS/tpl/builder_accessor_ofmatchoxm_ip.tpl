		public Builder ${signature} {
			return setValue(OFOxmMatchFields.${match_field}, (byte) 1, ByteBuffer.allocate(8).putInt(value).putInt(0xffffffff).array());
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		