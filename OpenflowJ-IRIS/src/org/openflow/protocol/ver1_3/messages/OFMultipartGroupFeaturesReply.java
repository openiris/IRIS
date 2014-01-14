package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.HashSet;
import org.openflow.protocol.ver1_3.types.*;
import java.util.Set;

public class OFMultipartGroupFeaturesReply extends OFMultipartReply implements org.openflow.protocol.interfaces.OFMultipartGroupFeaturesReply {
    public static int MINIMUM_LENGTH = 56;

    int  types;
	int  capabilities;
	int  max_groups_all;
	int  max_groups_select;
	int  max_groups_indirect;
	int  max_groups_ff;
	int  actions_all;
	int  actions_select;
	int  actions_indirect;
	int  actions_ff;

    public OFMultipartGroupFeaturesReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)19));
		setMultipartType(OFMultipartType.valueOf((short)8, this.type));
    }
    
    public OFMultipartGroupFeaturesReply(OFMultipartGroupFeaturesReply other) {
    	super(other);
		this.types = other.types;
		this.capabilities = other.capabilities;
		this.max_groups_all = other.max_groups_all;
		this.max_groups_select = other.max_groups_select;
		this.max_groups_indirect = other.max_groups_indirect;
		this.max_groups_ff = other.max_groups_ff;
		this.actions_all = other.actions_all;
		this.actions_select = other.actions_select;
		this.actions_indirect = other.actions_indirect;
		this.actions_ff = other.actions_ff;
    }

	public int getTypes() {
		return this.types;
	}
	
	public OFMultipartGroupFeaturesReply setTypes(int types) {
		this.types = types;
		return this;
	}
	
	public boolean isTypesSupported() {
		return true;
	}
			
	public int getCapabilitiesWire() {
		return this.capabilities;
	}
	
	public OFMultipartGroupFeaturesReply setCapabilitiesWire(int capabilities) {
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
		
	public OFMultipartGroupFeaturesReply setCapabilities(Set<org.openflow.protocol.interfaces.OFCapabilities> values) {
		OFCapabilities tmp = OFCapabilities.of(this.capabilities);
		tmp.and( values );
		this.capabilities = tmp.get();
		return this;
	}
	
	public boolean isCapabilitiesSupported() {
		return true;
	}
		
	public int getMaxGroupsAll() {
		return this.max_groups_all;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsAll(int max_groups_all) {
		this.max_groups_all = max_groups_all;
		return this;
	}
	
	public boolean isMaxGroupsAllSupported() {
		return true;
	}
			
	public int getMaxGroupsSelect() {
		return this.max_groups_select;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsSelect(int max_groups_select) {
		this.max_groups_select = max_groups_select;
		return this;
	}
	
	public boolean isMaxGroupsSelectSupported() {
		return true;
	}
			
	public int getMaxGroupsIndirect() {
		return this.max_groups_indirect;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsIndirect(int max_groups_indirect) {
		this.max_groups_indirect = max_groups_indirect;
		return this;
	}
	
	public boolean isMaxGroupsIndirectSupported() {
		return true;
	}
			
	public int getMaxGroupsFf() {
		return this.max_groups_ff;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsFf(int max_groups_ff) {
		this.max_groups_ff = max_groups_ff;
		return this;
	}
	
	public boolean isMaxGroupsFfSupported() {
		return true;
	}
			
	public int getActionsAll() {
		return this.actions_all;
	}
	
	public OFMultipartGroupFeaturesReply setActionsAll(int actions_all) {
		this.actions_all = actions_all;
		return this;
	}
	
	public boolean isActionsAllSupported() {
		return true;
	}
			
	public int getActionsSelect() {
		return this.actions_select;
	}
	
	public OFMultipartGroupFeaturesReply setActionsSelect(int actions_select) {
		this.actions_select = actions_select;
		return this;
	}
	
	public boolean isActionsSelectSupported() {
		return true;
	}
			
	public int getActionsIndirect() {
		return this.actions_indirect;
	}
	
	public OFMultipartGroupFeaturesReply setActionsIndirect(int actions_indirect) {
		this.actions_indirect = actions_indirect;
		return this;
	}
	
	public boolean isActionsIndirectSupported() {
		return true;
	}
			
	public int getActionsFf() {
		return this.actions_ff;
	}
	
	public OFMultipartGroupFeaturesReply setActionsFf(int actions_ff) {
		this.actions_ff = actions_ff;
		return this;
	}
	
	public boolean isActionsFfSupported() {
		return true;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.types = data.getInt();
		this.capabilities = data.getInt();
		this.max_groups_all = data.getInt();
		this.max_groups_select = data.getInt();
		this.max_groups_indirect = data.getInt();
		this.max_groups_ff = data.getInt();
		this.actions_all = data.getInt();
		this.actions_select = data.getInt();
		this.actions_indirect = data.getInt();
		this.actions_ff = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.putInt(this.types);
		data.putInt(this.capabilities);
		data.putInt(this.max_groups_all);
		data.putInt(this.max_groups_select);
		data.putInt(this.max_groups_indirect);
		data.putInt(this.max_groups_ff);
		data.putInt(this.actions_all);
		data.putInt(this.actions_select);
		data.putInt(this.actions_indirect);
		data.putInt(this.actions_ff);
    }

    public String toString() {
        return super.toString() +  ":OFMultipartGroupFeaturesReply-"+":types=" + U32.f(types) + 
		":capabilities=" + U32.f(capabilities) + 
		":max_groups_all=" + U32.f(max_groups_all) + 
		":max_groups_select=" + U32.f(max_groups_select) + 
		":max_groups_indirect=" + U32.f(max_groups_indirect) + 
		":max_groups_ff=" + U32.f(max_groups_ff) + 
		":actions_all=" + U32.f(actions_all) + 
		":actions_select=" + U32.f(actions_select) + 
		":actions_indirect=" + U32.f(actions_indirect) + 
		":actions_ff=" + U32.f(actions_ff);
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
        		
		final int prime = 1597;
		int result = super.hashCode() * prime;
		result = prime * result + (int) types;
		result = prime * result + (int) capabilities;
		result = prime * result + (int) max_groups_all;
		result = prime * result + (int) max_groups_select;
		result = prime * result + (int) max_groups_indirect;
		result = prime * result + (int) max_groups_ff;
		result = prime * result + (int) actions_all;
		result = prime * result + (int) actions_select;
		result = prime * result + (int) actions_indirect;
		result = prime * result + (int) actions_ff;
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
        if (!(obj instanceof OFMultipartGroupFeaturesReply)) {
            return false;
        }
        OFMultipartGroupFeaturesReply other = (OFMultipartGroupFeaturesReply) obj;
		if ( types != other.types ) return false;
		if ( capabilities != other.capabilities ) return false;
		if ( max_groups_all != other.max_groups_all ) return false;
		if ( max_groups_select != other.max_groups_select ) return false;
		if ( max_groups_indirect != other.max_groups_indirect ) return false;
		if ( max_groups_ff != other.max_groups_ff ) return false;
		if ( actions_all != other.actions_all ) return false;
		if ( actions_select != other.actions_select ) return false;
		if ( actions_indirect != other.actions_indirect ) return false;
		if ( actions_ff != other.actions_ff ) return false;
        return true;
    }
}
