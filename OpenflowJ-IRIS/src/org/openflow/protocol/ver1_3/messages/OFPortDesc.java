package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import org.openflow.protocol.OFPort;
import java.util.Set;

public class OFPortDesc   implements org.openflow.protocol.interfaces.OFPortDesc {
    public static int MINIMUM_LENGTH = 64;
    public static int CORE_LENGTH = 64;

    int  port;
	int pad_1th;
	byte[]  hw_addr;
	short pad_2th;
	byte[]  name;
	int  config;
	int  state;
	int  current_features;
	int  advertised_features;
	int  supported_features;
	int  peer_features;
	int  curr_speed;
	int  max_speed;

    public OFPortDesc() {
        hw_addr = new byte[6];
		name = new byte[16];
    }
    
    public OFPortDesc(OFPortDesc other) {
    	this.port = other.port;
		if (other.hw_addr != null) { this.hw_addr = java.util.Arrays.copyOf(other.hw_addr, other.hw_addr.length); }
		if (other.name != null) { this.name = java.util.Arrays.copyOf(other.name, other.name.length); }
		this.config = other.config;
		this.state = other.state;
		this.current_features = other.current_features;
		this.advertised_features = other.advertised_features;
		this.supported_features = other.supported_features;
		this.peer_features = other.peer_features;
		this.curr_speed = other.curr_speed;
		this.max_speed = other.max_speed;
    }

	public OFPort getPort() {
		return new OFPort(this.port);
	}
	
	public OFPortDesc setPort(OFPort port) {
		this.port = (int) port.get();
		return this;
	}
	
	public boolean isPortSupported() {
		return true;
	}
	
	public byte[] getHwAddr() {
		return this.hw_addr;
	}
	
	public OFPortDesc setHwAddr(byte[] hw_addr) {
		this.hw_addr = hw_addr;
		return this;
	}
	
	public boolean isHwAddrSupported() {
		return true;
	}
			
	public byte[] getName() {
		return this.name;
	}
	
	public OFPortDesc setName(byte[] name) {
		this.name = name;
		return this;
	}
	
	public boolean isNameSupported() {
		return true;
	}
			
	public int getConfigWire() {
		return this.config;
	}
	
	public OFPortDesc setConfigWire(int config) {
		this.config = config;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFPortConfig> getConfig() {
		OFPortConfig tmp = OFPortConfig.of(this.config);
		Set<org.openflow.protocol.interfaces.OFPortConfig> ret = new HashSet<org.openflow.protocol.interfaces.OFPortConfig>();
		for ( org.openflow.protocol.interfaces.OFPortConfig v : org.openflow.protocol.interfaces.OFPortConfig.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFPortDesc setConfig(Set<org.openflow.protocol.interfaces.OFPortConfig> values) {
		OFPortConfig tmp = OFPortConfig.of(this.config);
		tmp.or( values );
		this.config = tmp.get();
		return this;
	}
	
	public OFPortDesc setConfig(org.openflow.protocol.interfaces.OFPortConfig ... values) {
		OFPortConfig tmp = OFPortConfig.of(this.config);
		tmp.or( values );
		this.config = tmp.get();
		return this;
	}
	
	public boolean isConfigSupported() {
		return true;
	}
		
	public int getStateWire() {
		return this.state;
	}
	
	public OFPortDesc setStateWire(int state) {
		this.state = state;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFPortState> getState() {
		OFPortState tmp = OFPortState.of(this.state);
		Set<org.openflow.protocol.interfaces.OFPortState> ret = new HashSet<org.openflow.protocol.interfaces.OFPortState>();
		for ( org.openflow.protocol.interfaces.OFPortState v : org.openflow.protocol.interfaces.OFPortState.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFPortDesc setState(Set<org.openflow.protocol.interfaces.OFPortState> values) {
		OFPortState tmp = OFPortState.of(this.state);
		tmp.or( values );
		this.state = tmp.get();
		return this;
	}
	
	public OFPortDesc setState(org.openflow.protocol.interfaces.OFPortState ... values) {
		OFPortState tmp = OFPortState.of(this.state);
		tmp.or( values );
		this.state = tmp.get();
		return this;
	}
	
	public boolean isStateSupported() {
		return true;
	}
		
	public int getCurrentFeatures() {
		return this.current_features;
	}
	
	public OFPortDesc setCurrentFeatures(int current_features) {
		this.current_features = current_features;
		return this;
	}
	
	public boolean isCurrentFeaturesSupported() {
		return true;
	}
			
	public int getAdvertisedFeatures() {
		return this.advertised_features;
	}
	
	public OFPortDesc setAdvertisedFeatures(int advertised_features) {
		this.advertised_features = advertised_features;
		return this;
	}
	
	public boolean isAdvertisedFeaturesSupported() {
		return true;
	}
			
	public int getSupportedFeatures() {
		return this.supported_features;
	}
	
	public OFPortDesc setSupportedFeatures(int supported_features) {
		this.supported_features = supported_features;
		return this;
	}
	
	public boolean isSupportedFeaturesSupported() {
		return true;
	}
			
	public int getPeerFeatures() {
		return this.peer_features;
	}
	
	public OFPortDesc setPeerFeatures(int peer_features) {
		this.peer_features = peer_features;
		return this;
	}
	
	public boolean isPeerFeaturesSupported() {
		return true;
	}
			
	public int getCurrSpeed() {
		return this.curr_speed;
	}
	
	public OFPortDesc setCurrSpeed(int curr_speed) {
		this.curr_speed = curr_speed;
		return this;
	}
	
	public boolean isCurrSpeedSupported() {
		return true;
	}
			
	public int getMaxSpeed() {
		return this.max_speed;
	}
	
	public OFPortDesc setMaxSpeed(int max_speed) {
		this.max_speed = max_speed;
		return this;
	}
	
	public boolean isMaxSpeedSupported() {
		return true;
	}
			
	
	
	
	public OFPortDesc dup() {
		return new OFPortDesc(this);
	}
	
    public void readFrom(ByteBuffer data) {
        this.port = data.getInt();
		this.pad_1th = data.getInt();
		if ( this.hw_addr == null ) this.hw_addr = new byte[6];
		data.get(this.hw_addr);
		this.pad_2th = data.getShort();
		if ( this.name == null ) this.name = new byte[16];
		data.get(this.name);
		this.config = data.getInt();
		this.state = data.getInt();
		this.current_features = data.getInt();
		this.advertised_features = data.getInt();
		this.supported_features = data.getInt();
		this.peer_features = data.getInt();
		this.curr_speed = data.getInt();
		this.max_speed = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putInt(this.port);
		data.putInt(this.pad_1th);
		if ( this.hw_addr != null ) { data.put(this.hw_addr); }
		data.putShort(this.pad_2th);
		if ( this.name != null ) { data.put(this.name); }
		data.putInt(this.config);
		data.putInt(this.state);
		data.putInt(this.current_features);
		data.putInt(this.advertised_features);
		data.putInt(this.supported_features);
		data.putInt(this.peer_features);
		data.putInt(this.curr_speed);
		data.putInt(this.max_speed);
    }

    public String toString() {
        return  ":OFPortDesc-"+":port=" + U32.f(port) + 
		":hw_addr=" + java.util.Arrays.toString(hw_addr) + 
		":name=" + java.util.Arrays.toString(name) + 
		":config=" + U32.f(config) + 
		":state=" + U32.f(state) + 
		":current_features=" + U32.f(current_features) + 
		":advertised_features=" + U32.f(advertised_features) + 
		":supported_features=" + U32.f(supported_features) + 
		":peer_features=" + U32.f(peer_features) + 
		":curr_speed=" + U32.f(curr_speed) + 
		":max_speed=" + U32.f(max_speed);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2357;
		int result = super.hashCode() * prime;
		result = prime * result + (int) port;
		result = prime * result + ((hw_addr == null)?0:java.util.Arrays.hashCode(hw_addr));
		result = prime * result + ((name == null)?0:java.util.Arrays.hashCode(name));
		result = prime * result + (int) config;
		result = prime * result + (int) state;
		result = prime * result + (int) current_features;
		result = prime * result + (int) advertised_features;
		result = prime * result + (int) supported_features;
		result = prime * result + (int) peer_features;
		result = prime * result + (int) curr_speed;
		result = prime * result + (int) max_speed;
		return result;
    }

    @Override
    public boolean equals(Object obj) {
        
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFPortDesc)) {
            return false;
        }
        OFPortDesc other = (OFPortDesc) obj;
		if ( port != other.port ) return false;
		if ( hw_addr == null && other.hw_addr != null ) { return false; }
		else if ( !java.util.Arrays.equals(hw_addr, other.hw_addr) ) { return false; }
		if ( name == null && other.name != null ) { return false; }
		else if ( !java.util.Arrays.equals(name, other.name) ) { return false; }
		if ( config != other.config ) return false;
		if ( state != other.state ) return false;
		if ( current_features != other.current_features ) return false;
		if ( advertised_features != other.advertised_features ) return false;
		if ( supported_features != other.supported_features ) return false;
		if ( peer_features != other.peer_features ) return false;
		if ( curr_speed != other.curr_speed ) return false;
		if ( max_speed != other.max_speed ) return false;
        return true;
    }
}
