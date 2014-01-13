package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFTableMod extends OFMessage implements org.openflow.protocol.interfaces.OFTableMod {
    public static int MINIMUM_LENGTH = 16;

    byte  table_id;
	short pad_1th;
	byte pad_2th;
	int  config;

    public OFTableMod() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
    }
    
    public OFTableMod(OFTableMod other) {
    	super(other);
		this.table_id = other.table_id;
		this.config = other.config;
    }

	public byte getTableId() {
		return this.table_id;
	}
	
	public OFTableMod setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			
	public int getConfig() {
		return this.config;
	}
	
	public OFTableMod setConfig(int config) {
		this.config = config;
		return this;
	}
			
	
	
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.table_id = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
		this.config = data.getInt();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.table_id);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
		data.putInt(this.config);
    }

    public String toString() {
        return super.toString() +  ":OFTableMod-"+":table_id=" + U8.f(table_id) + 
		":config=" + U32.f(config);
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
        		
		final int prime = 2371;
		int result = super.hashCode() * prime;
		result = prime * result + (int) table_id;
		result = prime * result + (int) config;
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
        if (!(obj instanceof OFTableMod)) {
            return false;
        }
        OFTableMod other = (OFTableMod) obj;
		if ( table_id != other.table_id ) return false;
		if ( config != other.config ) return false;
        return true;
    }
}
