package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMeterBand  {

	public OFMeterBandType getType();
	public OFMeterBand setType(OFMeterBandType value);
	public boolean isTypeSupported();
	public short getLength();
	public OFMeterBand setLength(short value);
	public boolean isLengthSupported();
	public int getRate();
	public OFMeterBand setRate(int value);
	public boolean isRateSupported();
	public int getBurstSize();
	public OFMeterBand setBurstSize(int value);
	public boolean isBurstSizeSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
