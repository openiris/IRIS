	public $return_type get${method_name}() {
		return this.$variable_name;
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		this.$variable_name = $variable_name;
		return this;
	}
	
	public boolean is${method_name}Supported() {
		return true;
	}
			