	public static class Builder 
	implements	org.openflow.util.Builder<org.openflow.protocol.interfaces.$builder_returntype>,
				org.openflow.protocol.interfaces.OFMatch.Builder, 
				org.openflow.protocol.interfaces.OFMatchOxm.Builder {
	
		static byte[] mac_mask = { (byte)0xff, (byte)0xff, (byte)0xff, 
								   (byte)0xff, (byte)0xff, (byte)0xff };
		
		private $classname object;
		Byte network_protocol;
		
		public Builder() {
			this.object = new $classname();
			this.network_protocol = null;
		}
		
		public Builder setValue(OFOxmMatchFields match_field, byte mask, byte[] data) {
			List<org.openflow.protocol.interfaces.OFOxm> oxm_fields = object.getOxmFields();
			if ( oxm_fields == null ) {
				oxm_fields = new LinkedList<org.openflow.protocol.interfaces.OFOxm>();
				object.setOxmFields(oxm_fields);
			}
			
			OFOxm oxm = new OFOxm();
			oxm.setOxmClass(OFOxmClass.OPENFLOW_BASIC);
			oxm.setField(match_field.getValue()); //OFOxmMatchFields.
			oxm.setBitmask(mask);
			oxm.setData(data);
			oxm.setPayloadLength((byte) data.length);

			oxm_fields.add( oxm );
			
			return this;
		}
		
		$builder_accessors
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object;
		}
	}