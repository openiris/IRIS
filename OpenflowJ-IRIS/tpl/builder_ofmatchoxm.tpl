	public static class Builder 
	implements	org.openflow.protocol.interfaces.OFMatch.Builder {
	
		static byte[] mac_mask = { (byte)0xff, (byte)0xff, (byte)0xff, 
								   (byte)0xff, (byte)0xff, (byte)0xff };
		
		private $classname object;
		
		public Builder() {
			this.object = new $classname();
		}
		
		public Builder setValue(org.openflow.protocol.interfaces.OFOxmMatchFields match_field, byte mask, byte[] data) {
			return setValue(OFOxmMatchFields.from(match_field), mask, data);
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
			object.addOxmToIndex( oxm );
			
			return this;
		}
		
		public org.openflow.protocol.interfaces.OFOxm getValue(org.openflow.protocol.interfaces.OFOxmMatchFields match_field) {
			return object.getOxmFromIndex(match_field);
		}
		
		public boolean isSetValueSupported() { return true; }
		
		$builder_accessors
		
		public org.openflow.protocol.interfaces.OFMatch build() {
			return object.setLength(object.computeLength());
		}
	}