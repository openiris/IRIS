	@org.codehaus.jackson.annotate.JsonIgnore
	public $return_type get${method_name}() {
		throw new UnsupportedOperationException("get${method_name} is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean is${method_name}Supported() {
		return false;
	}
	