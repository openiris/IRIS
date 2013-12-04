	public $return_type get${method_name}() {
		return this.$variable_name.getValue();
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		if (this.$variable_name == null) this.$variable_name = new $variable_type();
		this.$variable_name.setValue( $variable_name );
		return this;
	}