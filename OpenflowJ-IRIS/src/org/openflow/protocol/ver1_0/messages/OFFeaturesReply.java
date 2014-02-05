package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OFFeaturesReply extends OFMessage implements org.openflow.protocol.interfaces.OFFeaturesReply {
    public static int MINIMUM_LENGTH = 32;
    public static int CORE_LENGTH = 24;

    long  datapath_id;
	int  n_buffers;
	byte  n_tables;
	short pad_1th;
	byte pad_2th;
	int  capabilities;
	int  actions;
	List<org.openflow.protocol.interfaces.OFPortDesc>  ports;

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
		this.capabilities = other.capabilities;
		this.actions = other.actions;
		this.ports = (other.ports == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFPortDesc>();
		for ( org.openflow.protocol.interfaces.OFPortDesc i : other.ports ) { this.ports.add( i.dup() ); }
    }

	public long getDatapathId() {
		return this.datapath_id;
	}
	
	public OFFeaturesReply setDatapathId(long datapath_id) {
		this.datapath_id = datapath_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
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
	
	@org.codehaus.jackson.annotate.JsonIgnore
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
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNTablesSupported() {
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
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isCapabilitiesSupported() {
		return true;
	}
		
	public int getActions() {
		return this.actions;
	}
	
	public OFFeaturesReply setActions(int actions) {
		this.actions = actions;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isActionsSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFPortDesc> getPorts() {
		return this.ports;
	}
	
	public OFFeaturesReply setPorts(List<org.openflow.protocol.interfaces.OFPortDesc> ports) {
		this.ports = ports;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPortsSupported() {
		return true;
	}
			
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFFeaturesReply setAuxiliaryId(byte value) {
		throw new UnsupportedOperationException("setAuxiliaryId is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public byte getAuxiliaryId() {
		throw new UnsupportedOperationException("getAuxiliaryId is not supported operation");
	}
	
	public boolean isAuxiliaryIdSupported() {
		return false;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public OFFeaturesReply setReserved(int value) {
		throw new UnsupportedOperationException("setReserved is not supported operation");
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public int getReserved() {
		throw new UnsupportedOperationException("getReserved is not supported operation");
	}
	
	public boolean isReservedSupported() {
		return false;
	}
	
	
	
	
	public OFFeaturesReply dup() {
		return new OFFeaturesReply(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		super.readFrom(data);
		this.datapath_id = data.getLong();
		this.n_buffers = data.getInt();
		this.n_tables = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
		this.capabilities = data.getInt();
		this.actions = data.getInt();
		if (this.ports == null) this.ports = new LinkedList<org.openflow.protocol.interfaces.OFPortDesc>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) { OFPortDesc t = new OFPortDesc(); t.readFrom(data); this.ports.add(t); __cnt -= OFPortDesc.MINIMUM_LENGTH; }
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putLong(this.datapath_id);
		data.putInt(this.n_buffers);
		data.put(this.n_tables);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
		data.putInt(this.capabilities);
		data.putInt(this.actions);
		if (this.ports != null ) for (org.openflow.protocol.interfaces.OFPortDesc t: this.ports) { t.writeTo(data); }
    }

    public String toString() {
        return super.toString() +  ":OFFeaturesReply-"+":datapath_id=" + U64.f(datapath_id) + 
		":n_buffers=" + U32.f(n_buffers) + 
		":n_tables=" + U8.f(n_tables) + 
		":capabilities=" + U32.f(capabilities) + 
		":actions=" + U32.f(actions) + 
		":ports=" + ports.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
		if ( this.ports != null ) for ( org.openflow.protocol.interfaces.OFPortDesc i : this.ports ) { len += i.computeLength(); }
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
        		
		final int prime = 2887;
		int result = super.hashCode() * prime;
		result = prime * result + (int) datapath_id;
		result = prime * result + (int) n_buffers;
		result = prime * result + (int) n_tables;
		result = prime * result + (int) capabilities;
		result = prime * result + (int) actions;
		result = prime * result + ((ports == null)?0:ports.hashCode());
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
		if ( capabilities != other.capabilities ) return false;
		if ( actions != other.actions ) return false;
		if ( ports == null && other.ports != null ) { return false; }
		else if ( !ports.equals(other.ports) ) { return false; }
        return true;
    }
}
