package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFVendor extends OFMessage {

	public int getVendorId();
	public OFVendor setVendorId(int value);
	public boolean isVendorIdSupported();
	public int getSubtype();
	public OFVendor setSubtype(int value);
	public boolean isSubtypeSupported();
	public byte[] getData();
	public OFVendor setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
