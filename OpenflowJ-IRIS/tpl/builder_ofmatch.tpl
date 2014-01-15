	public static class Builder 
	implements	org.openflow.util.Builder<org.openflow.protocol.interfaces.$builder_returntype>,
				org.openflow.protocol.interfaces.OFMatch.Builder, 
				org.openflow.protocol.interfaces.OFMatchOxm.Builder {
	
		private $classname object;
		
		public Builder() {
			object = new $classname();
		}
		
		$builder_accessors
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object;
		}
	}