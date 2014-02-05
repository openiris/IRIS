package etri.sdn.controller;

import org.openflow.util.HexString;

public class SwitchInformation {
	

	long 		datapathId 						= -1;
	String 		stringId 						= null;
	int			capabilities					= 0;
	int			buffers							= 0;
	int			actions							= 0;
	byte		tables							= 0;
	String  	manufacturer_description		= null;
	String  	hardware_description			= null;
	String  	software_description			= null;
	String  	serial_number					= null;
	String  	datapath_description			= null;
	
	public SwitchInformation() {
		this.datapathId = -1;
	}
	
	public long getId() {
		return datapathId;
	}

	public SwitchInformation setId(long datapathId) {
		this.datapathId = datapathId;
		this.stringId = HexString.toHexString(datapathId);
		return this;
	}

	public String getStringId() {
		return stringId;
	}

	public int getCapabilities() {
		return capabilities;
	}

	public SwitchInformation setCapabilities(int capabilities) {
		this.capabilities = capabilities;
		return this;
	}

	public int getBuffers() {
		return buffers;
	}

	public SwitchInformation setBuffers(int buffers) {
		this.buffers = buffers;
		return this;
	}

	public int getActions() {
		return actions;
	}

	public SwitchInformation setActions(int actions) {
		this.actions = actions;
		return this;
	}

	public byte getTables() {
		return tables;
	}

	public SwitchInformation setTables(byte tables) {
		this.tables = tables;
		return this;
	}

	public String getManufacturerDescription() {
		return manufacturer_description;
	}

	public SwitchInformation setManufacturerDescription(String manufacturer_description) {
		this.manufacturer_description = manufacturer_description;
		return this;
	}

	public String getHardwareDescription() {
		return hardware_description;
	}

	public SwitchInformation setHardwareDescription(String hardware_description) {
		this.hardware_description = hardware_description;
		return this;
	}

	public String getSoftwareDescription() {
		return software_description;
	}

	public SwitchInformation setSoftwareDescription(String software_description) {
		this.software_description = software_description;
		return this;
	}

	public String getSerialNumber() {
		return serial_number;
	}

	public SwitchInformation setSerialNumber(String serial_number) {
		this.serial_number = serial_number;
		return this;
	}

	public String getDatapathDescription() {
		return datapath_description;
	}

	public SwitchInformation setDatapathDescription(String datapath_description) {
		this.datapath_description = datapath_description;
		return this;
	}
}