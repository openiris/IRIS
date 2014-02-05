package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFOxm  {

	public OFOxm setOxmClass(OFOxmClass value);
	public OFOxmClass getOxmClass();
	public boolean isOxmClassSupported();
	public OFOxm setField(byte value);
	public byte getField();
	public boolean isFieldSupported();
	public OFOxm setBitmask(byte value);
	public byte getBitmask();
	public boolean isBitmaskSupported();
	public OFOxm setPayloadLength(byte value);
	public byte getPayloadLength();
	public boolean isPayloadLengthSupported();
	public OFOxm setData(byte[] value);
	public byte[] getData();
	public boolean isDataSupported();
	
	public OFOxm dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
