	public $return_type get${method_name}() {
		return this.$variable_name;
	}
	
	public $class_name set${method_name}($return_type $variable_name) {
		this.$variable_name = $variable_name;
		for ( org.openflow.protocol.interfaces.OFOxm oxm : this.${variable_name} ) {
			addOxmToIndex(oxm);
		}
		return this;
	}
	
	public boolean is${method_name}Supported() {
		return true;
	}
			