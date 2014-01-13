	public OFPort get${method_name}() {
		return new OFPort(this.$variable_name);
	}
	
	public $class_name set${method_name}(OFPort port) {
		this.$variable_name = ($return_type) port.get();
		return this;
	}
	