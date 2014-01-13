	public $return_type get${method_name}() {
		return this.$variable_name;
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		this.$variable_name = (OFMatchOxm) $variable_name;
		return this;
	}
			