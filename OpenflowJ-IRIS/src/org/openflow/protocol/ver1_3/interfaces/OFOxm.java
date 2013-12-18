package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFOxm  {

	public OFOxmClass getOxmClass();
	
	public OFOxm setOxmClass(OFOxmClass value);
	
	public byte getField();
	
	public OFOxm setField(byte value);
	
	public byte getBitmask();
	
	public OFOxm setBitmask(byte value);
	
	public byte getPayloadLength();
	
	public OFOxm setPayloadLength(byte value);
	
	public byte[] getData();
	
	public OFOxm setData(byte[] value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
