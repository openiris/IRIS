	public static class Builder 
	implements	org.openflow.protocol.interfaces.OFMatch.Builder {
	
		private $classname object;
		
		public Builder() {
			object = new $classname();
		}
		
		public Builder setValue(org.openflow.protocol.interfaces.OFOxmMatchFields match_field, byte mask, byte[] data) {
			throw new UnsupportedOperationException("setValue is not supported");
		}
		
		public boolean isSetValueSupported() { return false; }
		
		$builder_accessors
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object;
		}
	}