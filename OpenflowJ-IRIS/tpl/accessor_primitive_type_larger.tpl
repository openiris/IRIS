	public $longer_type get${method_name}U() {
		return ${longer_type_util}.f(this.$variable_name);
	}
	
	public $class_name set${method_name}U($longer_type $variable_name) {
		this.$variable_name = ${longer_type_util}.t($variable_name);
		return this;
	}
	