package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFMultipartGroupFeaturesReply extends OFMultipartReply  {
    public static int MINIMUM_LENGTH = 56;

    int  types;
	OFCapabilities  capabilities;
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
		setMultipartType(OFMultipartType.valueOf((short)8, getType()));
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
			
	public int getCapabilities() {
		return this.capabilities.getValue();
	}
	
	public OFMultipartGroupFeaturesReply setCapabilities(int capabilities) {
		if (this.capabilities == null) this.capabilities = new OFCapabilities();
		this.capabilities.setValue( capabilities );
		return this;
	}
	public int getMaxGroupsAll() {
		return this.max_groups_all;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsAll(int max_groups_all) {
		this.max_groups_all = max_groups_all;
		return this;
	}
			
	public int getMaxGroupsSelect() {
		return this.max_groups_select;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsSelect(int max_groups_select) {
		this.max_groups_select = max_groups_select;
		return this;
	}
			
	public int getMaxGroupsIndirect() {
		return this.max_groups_indirect;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsIndirect(int max_groups_indirect) {
		this.max_groups_indirect = max_groups_indirect;
		return this;
	}
			
	public int getMaxGroupsFf() {
		return this.max_groups_ff;
	}
	
	public OFMultipartGroupFeaturesReply setMaxGroupsFf(int max_groups_ff) {
		this.max_groups_ff = max_groups_ff;
		return this;
	}
			
	public int getActionsAll() {
		return this.actions_all;
	}
	
	public OFMultipartGroupFeaturesReply setActionsAll(int actions_all) {
		this.actions_all = actions_all;
		return this;
	}
			
	public int getActionsSelect() {
		return this.actions_select;
	}
	
	public OFMultipartGroupFeaturesReply setActionsSelect(int actions_select) {
		this.actions_select = actions_select;
		return this;
	}
			
	public int getActionsIndirect() {
		return this.actions_indirect;
	}
	
	public OFMultipartGroupFeaturesReply setActionsIndirect(int actions_indirect) {
		this.actions_indirect = actions_indirect;
		return this;
	}
			
	public int getActionsFf() {
		return this.actions_ff;
	}
	
	public OFMultipartGroupFeaturesReply setActionsFf(int actions_ff) {
		this.actions_ff = actions_ff;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.types = data.getInt();
		if (this.capabilities == null) this.capabilities = new OFCapabilities();
		this.capabilities.setValue( OFCapabilities.readFrom(data) );
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
		data.putInt(this.capabilities.getValue());
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
		":capabilities=" + capabilities.toString() + 
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
        		
		final int prime = 1597;
		int result = super.hashCode() * prime;
		result = prime * result + (int) types;
		result = prime * result + ((capabilities == null)?0:capabilities.hashCode());
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
		if ( capabilities == null && other.capabilities != null ) { return false; }
		else if ( !capabilities.equals(other.capabilities) ) { return false; }
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
