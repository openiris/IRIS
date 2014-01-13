	public OF${object_name} create${object_name}() {
		switch(version) {
		$cases
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OF${object_name}.");
		}
	}
	
	public OF${object_name} as${object_name}(OFMessage m) {
		switch(version) {
		$as_cases
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OF${object_name}.");
		}
	}
	