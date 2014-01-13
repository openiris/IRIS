	public org.openflow.protocol.interfaces.$return_type get${method_name}() {
		return ${return_type}.to(this.$variable_name.getTypeValue(), this.type);
	}
	
	public $class_name set${method_name}(org.openflow.protocol.interfaces.$return_type $variable_name) {
		this.$variable_name = ${return_type}.from($variable_name, this.type);
		return this;
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		this.$variable_name = $variable_name;
		return this;
	}
	