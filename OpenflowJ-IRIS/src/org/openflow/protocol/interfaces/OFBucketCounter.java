package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFBucketCounter  {

	public OFBucketCounter setPacketCount(long value);
	public long getPacketCount();
	public boolean isPacketCountSupported();
	public OFBucketCounter setByteCount(long value);
	public long getByteCount();
	public boolean isByteCountSupported();
	
	public OFBucketCounter dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
