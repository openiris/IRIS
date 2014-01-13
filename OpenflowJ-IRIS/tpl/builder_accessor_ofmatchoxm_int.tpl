		public Builder ${method_name} {
			return setValue(OFOxmMatchFields.${match_field}, ByteBuffer.allocate(4).putInt(value).array());
		}