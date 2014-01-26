package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import java.util.List;
import java.util.Set;

public class OFFeaturesReply extends OFMessage implements org.openflow.protocol.interfaces.OFFeaturesReply {
    public static int MINIMUM_LENGTH = 32;

    long  datapath_id;
	int  n_buffers;
	byte  n_tables;
	byte  auxiliary_id;
	short pad_1th;
	int  capabilities;
	int  reserved;

    public OFFeaturesReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)6));
    }
    
    public OFFeaturesReply(OFFeaturesReply other) {
    	super(other);
		this.datapath_id = other.datapath_id;
		this.n_buffers = other.n_buffers;
		this.n_tables = other.n_tables;
		this.auxiliary_id = other.auxiliary_id;
		this.capabilities = other.capabilities;
		this.reserved = other.reserved;
    }

	public long getDatapathId() {
		return this.datapath_id;
	}
	
	public OFFeaturesReply setDatapathId(long datapath_id) {
		this.datapath_id = datapath_id;
		return this;
	}
	
	public boolean isDatapathIdSupported() {
		return true;
	}
			
	public int getNBuffers() {
		return this.n_buffers;
	}
	
	public OFFeaturesReply setNBuffers(int n_buffers) {
		this.n_buffers = n_buffers;
		return this;
	}
	
	public boolean isNBuffersSupported() {
		return true;
	}
			
	public byte getNTables() {
		return this.n_tables;
	}
	
	public OFFeaturesReply setNTables(byte n_tables) {
		this.n_tables = n_tables;
		return this;
	}
	
	public boolean isNTablesSupported() {
		return true;
	}
			
	public byte getAuxiliaryId() {
		return this.auxiliary_id;
	}
	
	public OFFeaturesReply setAuxiliaryId(byte auxiliary_id) {
		this.auxiliary_id = auxiliary_id;
		return this;
	}
	
	public boolean isAuxiliaryIdSupported() {
		return true;
	}
			
	public int getCapabilitiesWire() {
		return this.capabilities;
	}
	
	public OFFeaturesReply setCapabilitiesWire(int capabilities) {
		this.capabilities = capabilities;
		return this;
	}
	
	public Set<org.openflow.protocol.interfaces.OFCapabilities> getCapabilities() {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		Set<org.openflow.protocol.interfaces.OFCapabilities> ret = new HashSet<org.openflow.protocol.interfaces.OFCapabilities>();
		for ( org.openflow.protocol.interfaces.OFCapabilities v : org.openflow.protocol.interfaces.OFCapabilities.values() ) {
			if (tmp.has(v)) {
				ret.add(v);
			}
		}
		return ret;
	}
		
	public OFFeaturesReply setCapabilities(Set<org.openflow.protocol.interfaces.OFCapabilities> values) {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		tmp.or( values );
		this.capabilities = tmp.get();
		return this;
	}
	
	public OFFeaturesReply setCapabilities(org.openflow.protocol.interfaces.OFCapabilities ... values) {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		tmp.or( values );
		this.capabilities = tmp.get();
		return this;
	}
	
	public boolean isCapabilitiesSupported() {
		return true;
	}
		
	public int getReserved() {
		return this.reserved;
	}
	
	public OFFeaturesReply setReserved(int reserved) {
		this.reserved = reserved;
		return this;
	}
	
	public boolean isReservedSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFFeaturesReply setActions(int value) {
		throw new UnsupportedOperationException("setActions is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getActions() {
		throw new UnsupportedOperationException("getActions is not supported operation");
	}
	
	public boolean isActionsSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFFeaturesReply setPorts(List<org.openflow.protocol.interfaces.OFPortDesc> value) {
		throw new UnsupportedOperationException("setPorts is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public List<org.openflow.protocol.interfaces.OFPortDesc> getPorts() {
		throw new UnsupportedOperationException("getPorts is not supported operation");
	}
	
	public boolean isPortsSupported() {
		return false;
	}
	
	
	
	
	public OFFeaturesReply dup() {
		return new OFFeaturesReply(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.datapath_id = data.getLong();
		this.n_buffers = data.getInt();
		this.n_tables = data.get();
		this.auxiliary_id = data.get();
		this.pad_1th = data.getShort();
		this.capabilities = data.getInt();
		this.reserved = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putLong(this.datapath_id);
		data.putInt(this.n_buffers);
		data.put(this.n_tables);
		data.put(this.auxiliary_id);
		data.putShort(this.pad_1th);
		data.putInt(this.capabilities);
		data.putInt(this.reserved);
    }

    public String toString() {
        return super.toString() +  ":OFFeaturesReply-"+":datapath_id=" + U64.f(datapath_id) + 
		":n_buffers=" + U32.f(n_buffers) + 
		":n_tables=" + U8.f(n_tables) + 
		":auxiliary_id=" + U8.f(auxiliary_id) + 
		":capabilities=" + U32.f(capabilities) + 
		":reserved=" + U32.f(reserved);
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
        		
		final int prime = 2347;
		int result = super.hashCode() * prime;
		result = prime * result + (int) datapath_id;
		result = prime * result + (int) n_buffers;
		result = prime * result + (int) n_tables;
		result = prime * result + (int) auxiliary_id;
		result = prime * result + (int) capabilities;
		result = prime * result + (int) reserved;
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
        if (!(obj instanceof OFFeaturesReply)) {
            return false;
        }
        OFFeaturesReply other = (OFFeaturesReply) obj;
		if ( datapath_id != other.datapath_id ) return false;
		if ( n_buffers != other.n_buffers ) return false;
		if ( n_tables != other.n_tables ) return false;
		if ( auxiliary_id != other.auxiliary_id ) return false;
		if ( capabilities != other.capabilities ) return false;
		if ( reserved != other.reserved ) return false;
        return true;
    }
}
