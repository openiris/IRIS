package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFMeterFeatures  {

	public int getMaxMeter();
	public OFMeterFeatures setMaxMeter(int value);
	public int getBandTypes();
	public OFMeterFeatures setBandTypes(int value);
	public Set<OFCapabilities> getCapabilities();
	public OFMeterFeatures setCapabilities(Set<OFCapabilities> value);
	public byte getMaxBands();
	public OFMeterFeatures setMaxBands(byte value);
	public byte getMaxColor();
	public OFMeterFeatures setMaxColor(byte value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
