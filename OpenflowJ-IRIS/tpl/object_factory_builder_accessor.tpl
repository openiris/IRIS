	public org.openflow.protocol.interfaces.OFMatch.Builder create${object_name}Builder() {
		switch(version) {
		$cases
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OF${object_name}.");
		}
	}
	