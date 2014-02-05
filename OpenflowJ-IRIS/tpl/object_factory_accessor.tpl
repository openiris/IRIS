	public static OF${object_name} create${object_name}(byte version) {
		switch(version) {
		$cases
		default: return null;
		}
	}
	
	public static OF${object_name} as${object_name}(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		$as_cases
		default: return null;
		}
	}
	