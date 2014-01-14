		public Builder set${method_name}($variable_type $variable_name) {
			object.set${method_name}($variable_name);
			return this;
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		