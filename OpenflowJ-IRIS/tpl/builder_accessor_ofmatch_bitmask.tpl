		public Builder set${method_name}($variable_type1 $variable_name) {
			object.set${method_name}($variable_name);
			return this;
		}
		
		public Builder set${method_name}($variable_type2 $variable_name) {
			object.set${method_name}($variable_name);
			return this;
		}
		
		public Builder set${method_name}Wire($variable_type3 $variable_name) {
			object.set${method_name}Wire($variable_name);
			return this;
		}
		
		public $variable_type1 get${method_name}() {
			return object.get${method_name}();
		}
		
		public $variable_type3 get${method_name}Wire() {
			return object.get${method_name}Wire();
		}
		
		public boolean is${method_name}Supported() {
			return true;
		}
		