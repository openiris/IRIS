	public void addOxmToIndex(org.openflow.protocol.interfaces.OFOxm oxm) {
		index.put(OFOxmMatchFields.to(OFOxmMatchFields.valueOf(oxm.getField())), oxm);
	}

	@org.codehaus.jackson.annotate.JsonIgnore	
	public org.openflow.protocol.interfaces.OFOxm getOxmFromIndex(org.openflow.protocol.interfaces.OFOxmMatchFields clazz) {
		return index.get(clazz);
	}
	