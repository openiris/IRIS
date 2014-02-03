	public $return_type get${method_name}() {
		$return_type t__ = ($return_type)(this.$variable_name & $mask);
		return ($return_type)(t__ >> $shift_amount);
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		this.$variable_name |= ($return_type) $variable_name << $shift_amount;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean is${method_name}Supported() {
		return true;
	}
			