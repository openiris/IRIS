package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMeterBandStats  {

	public OFMeterBandStats setPacketBandCount(long value);
	public long getPacketBandCount();
	public boolean isPacketBandCountSupported();
	public OFMeterBandStats setByteBandCount(long value);
	public long getByteBandCount();
	public boolean isByteBandCountSupported();
	
	public OFMeterBandStats dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
