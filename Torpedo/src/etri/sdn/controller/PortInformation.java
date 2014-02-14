package etri.sdn.controller;

public class PortInformation {
	int  port					= -1;
	byte[]  hw_addr				= null;
	byte[]  name				= null;
	int  config					= 0;
	int  state					= 0;
	int  current_features		= 0;
	int  advertised_features	= 0;
	int  supported_features		= 0;
	int  peer_features			= 0;
	int  curr_speed				= 0;
	int  max_speed				= 0;
	
	public PortInformation() {
		// does nothing.
	}
	
	public PortInformation(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	public PortInformation setPort(int port) {
		this.port = port;
		return this;
	}
	public byte[] getHwAddr() {
		return hw_addr;
	}
	public String getHwAddrString() {
		if (hw_addr != null) {
			return new String(hw_addr);
		}
		return null;
	}
	public PortInformation setHwAddr(byte[] hw_addr) {
		this.hw_addr = hw_addr;
		return this;
	}
	public byte[] getName() {
		return name;
	}
	public String getNameString() {
		if ( name != null ) {
			return new String(name);
		}
		return null;
	}
	public PortInformation setName(byte[] name) {
		this.name = name;
		return this;
	}
	public int getConfig() {
		return config;
	}
	public PortInformation setConfig(int config) {
		this.config = config;
		return this;
	}
	public int getState() {
		return state;
	}
	public PortInformation setState(int state) {
		this.state = state;
		return this;
	}
	public int getCurrentFeatures() {
		return current_features;
	}
	public PortInformation setCurrentFeatures(int current_features) {
		this.current_features = current_features;
		return this;
	}
	public int getAdvertisedFeatures() {
		return advertised_features;
	}
	public PortInformation setAdvertisedFeatures(int advertised_features) {
		this.advertised_features = advertised_features;
		return this;
	}
	public int getSupportedFeatures() {
		return supported_features;
	}
	public PortInformation setSupportedFeatures(int supported_features) {
		this.supported_features = supported_features;
		return this;
	}
	public int getPeerFeatures() {
		return peer_features;
	}
	public PortInformation setPeerFeatures(int peer_features) {
		this.peer_features = peer_features;
		return this;
	}
	public int getCurrSpeed() {
		return curr_speed;
	}
	public PortInformation setCurrSpeed(int curr_speed) {
		this.curr_speed = curr_speed;
		return this;
	}
	public int getMaxSpeed() {
		return max_speed;
	}
	public PortInformation setMaxSpeed(int max_speed) {
		this.max_speed = max_speed;
		return this;
	}	
}