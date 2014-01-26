package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMeterBand  {

	public OFMeterBand setType(OFMeterBandType value);
	public OFMeterBandType getType();
	public boolean isTypeSupported();
	public OFMeterBand setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFMeterBand setRate(int value);
	public int getRate();
	public boolean isRateSupported();
	public OFMeterBand setBurstSize(int value);
	public int getBurstSize();
	public boolean isBurstSizeSupported();
	
	public OFMeterBand dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
