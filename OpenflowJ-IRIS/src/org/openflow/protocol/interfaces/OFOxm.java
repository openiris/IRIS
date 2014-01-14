package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFOxm  {

	public OFOxmClass getOxmClass();
	public OFOxm setOxmClass(OFOxmClass value);
	public boolean isOxmClassSupported();
	public byte getField();
	public OFOxm setField(byte value);
	public boolean isFieldSupported();
	public byte getBitmask();
	public OFOxm setBitmask(byte value);
	public boolean isBitmaskSupported();
	public byte getPayloadLength();
	public OFOxm setPayloadLength(byte value);
	public boolean isPayloadLengthSupported();
	public byte[] getData();
	public OFOxm setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
