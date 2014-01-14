package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMeterBandStats  {

	public long getPacketBandCount();
	public OFMeterBandStats setPacketBandCount(long value);
	public boolean isPacketBandCountSupported();
	public long getByteBandCount();
	public OFMeterBandStats setByteBandCount(long value);
	public boolean isByteBandCountSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
