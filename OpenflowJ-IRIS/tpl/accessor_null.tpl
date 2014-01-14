	@org.codehaus.jackson.annotate.JsonIgnore
	public $return_type get${method_name}() {
		throw new UnsupportedOperationException("$signature is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public $class_name set${method_name}($return_type value) {
		throw new UnsupportedOperationException("$signature is not supported operation");
	}
	
	public boolean is${method_name}Supported() {
		return false;
	}
	