		public Builder set${method_name}($variable_type $variable_name) {
			object.set${method_name}($variable_name);
			return this;
		}
		
		public $variable_type get${method_name}() {
			return object.get${method_name}();
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		