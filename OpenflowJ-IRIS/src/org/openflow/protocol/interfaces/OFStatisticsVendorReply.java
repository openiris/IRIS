package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsVendorReply extends OFStatisticsReply {

	public OFVendor getVendor();
	public OFStatisticsVendorReply setVendor(OFVendor value);
	public boolean isVendorSupported();
	public byte[] getData();
	public OFStatisticsVendorReply setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
