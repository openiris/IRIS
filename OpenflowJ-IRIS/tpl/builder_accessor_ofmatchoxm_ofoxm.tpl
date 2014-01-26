		public Builder set${method_name}(List<org.openflow.protocol.interfaces.OFOxm> $variable_name) {
			this.object.setOxmFields($variable_name);
			return this;
		}
		
		public List<org.openflow.protocol.interfaces.OFOxm> get${method_name}() {
			return this.object.getOxmFields();
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		