package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_3.types.*;

public class OFInstructionGotoTable extends OFInstruction  {
    public static int MINIMUM_LENGTH = 8;

    byte  table_id;
	short pad_1th;
	byte pad_2th;

    public OFInstructionGotoTable() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFInstructionType.valueOf((short)1));
    }
    
    public OFInstructionGotoTable(OFInstructionGotoTable other) {
    	super(other);
		this.table_id = other.table_id;
    }

	public byte getTableId() {
		return this.table_id;
	}
	
	public OFInstructionGotoTable setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
			

    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.table_id = data.get();
		this.pad_1th = data.getShort();
		this.pad_2th = data.get();
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        data.put(this.table_id);
		data.putShort(this.pad_1th);
		data.put(this.pad_2th);
    }

    public String toString() {
        return super.toString() +  ":OFInstructionGotoTable-"+":table_id=" + U8.f(table_id);
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
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
        		
		final int prime = 2131;
		int result = super.hashCode() * prime;
		result = prime * result + (int) table_id;
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
        if (!(obj instanceof OFInstructionGotoTable)) {
            return false;
        }
        OFInstructionGotoTable other = (OFInstructionGotoTable) obj;
		if ( table_id != other.table_id ) return false;
        return true;
    }
}
