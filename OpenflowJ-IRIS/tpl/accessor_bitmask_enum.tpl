	public $return_type get${method_name}Wire() {
		return this.$variable_name;
	}
	
	public $class_name set${method_name}Wire($return_type $variable_name) {
		this.$variable_name = $variable_name;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.${class_type}> get${method_name}() {
		${class_type} tmp = ${class_type}.of(this.$variable_name);
		Set<org.openflow.protocol.interfaces.${class_type}> ret = new HashSet<org.openflow.protocol.interfaces.${class_type}>();
		for ( org.openflow.protocol.interfaces.${class_type} v : org.openflow.protocol.interfaces.${class_type}.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public $class_name set${method_name}(Set<org.openflow.protocol.interfaces.${class_type}> values) {
		${class_type} tmp = ${class_type}.of(this.$variable_name);
		tmp.or( values );
		this.$variable_name = tmp.get();
		return this;
	}
	
	public $class_name set${method_name}(org.openflow.protocol.interfaces.${class_type} ... values) {
		${class_type} tmp = ${class_type}.of(this.$variable_name);
		tmp.or( values );
		this.$variable_name = tmp.get();
		return this;
	}
	
	public boolean is${method_name}Supported() {
		return true;
	}
		