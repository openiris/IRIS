		public Builder ${method_name} {
			return setValue(OFOxmMatchFields.${match_field}, ByteBuffer.allocate(2).putShort(value).array());
		}