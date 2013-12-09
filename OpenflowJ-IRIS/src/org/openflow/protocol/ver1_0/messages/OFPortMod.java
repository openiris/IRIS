package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFPortMod extends OFMessage  {
    public static int MINIMUM_LENGTH = 32;

    OFPortNo  port_no;
	byte[]  hw_addr;
	int  config;
	int  mask;
	int  advertise;
	int pad_1th;

    public OFPortMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)15));
		hw_addr = new byte[6];
    }
    
    public OFPortMod(OFPortMod other) {
    	super(other);
		this.port_no = other.port_no;
		if (other.hw_addr != null) { this.hw_addr = java.util.Arrays.copyOf(other.hw_addr, other.hw_addr.length); }
		this.config = other.config;
		this.mask = other.mask;
		this.advertise = other.advertise;
    }

	public OFPortNo getPortNo() {
		return this.port_no;
	}
	
	public OFPortMod setPortNo(OFPortNo port_no) {
		this.port_no = port_no;
		return this;
	}
			
	public byte[] getHwAddr() {
		return this.hw_addr;
	}
	
	public OFPortMod setHwAddr(byte[] hw_addr) {
		this.hw_addr = hw_addr;
		return this;
	}
			
	public int getConfig() {
		return this.config;
	}
	
	public OFPortMod setConfig(int config) {
		this.config = config;
		return this;
	}
			
	public int getMask() {
		return this.mask;
	}
	
	public OFPortMod setMask(int mask) {
		this.mask = mask;
		return this;
	}
			
	public int getAdvertise() {
		return this.advertise;
	}
	
	public OFPortMod setAdvertise(int advertise) {
		this.advertise = advertise;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.port_no = OFPortNo.valueOf(OFPortNo.readFrom(data));
		if ( this.hw_addr == null ) this.hw_addr = new byte[6];
		data.get(this.hw_addr);
		this.config = data.getInt();
		this.mask = data.getInt();
		this.advertise = data.getInt();
		this.pad_1th = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putShort(this.port_no.getValue());
		if ( this.hw_addr != null ) { data.put(this.hw_addr); }
		data.putInt(this.config);
		data.putInt(this.mask);
		data.putInt(this.advertise);
		data.putInt(this.pad_1th);
    }

    public String toString() {
        return super.toString() +  ":OFPortMod-"+":port_no=" + port_no.toString() + 
		":hw_addr=" + java.util.Arrays.toString(hw_addr) + 
		":config=" + U32.f(config) + 
		":mask=" + U32.f(mask) + 
		":advertise=" + U32.f(advertise);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	if ( this.hw_addr != null ) { len += this.hw_addr.length; } 
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	short l = (short)(computeLength() % req);
    	if ( l == 0 ) { return 0; }
    	return (short)( req - l );
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	return (short)(computeLength() - (short)MINIMUM_LENGTH + alignment((short)0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2861;
		int result = super.hashCode() * prime;
		result = prime * result + ((port_no == null)?0:port_no.hashCode());
		result = prime * result + ((hw_addr == null)?0:java.util.Arrays.hashCode(hw_addr));
		result = prime * result + (int) config;
		result = prime * result + (int) mask;
		result = prime * result + (int) advertise;
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
        if (!(obj instanceof OFPortMod)) {
            return false;
        }
        OFPortMod other = (OFPortMod) obj;
		if ( port_no == null && other.port_no != null ) { return false; }
		else if ( !port_no.equals(other.port_no) ) { return false; }
		if ( hw_addr == null && other.hw_addr != null ) { return false; }
		else if ( !java.util.Arrays.equals(hw_addr, other.hw_addr) ) { return false; }
		if ( config != other.config ) return false;
		if ( mask != other.mask ) return false;
		if ( advertise != other.advertise ) return false;
        return true;
    }
}
