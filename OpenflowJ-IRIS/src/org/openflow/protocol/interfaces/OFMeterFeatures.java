package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFMeterFeatures  {

	public OFMeterFeatures setMaxMeter(int value);
	public int getMaxMeter();
	public boolean isMaxMeterSupported();
	public OFMeterFeatures setBandTypes(int value);
	public int getBandTypes();
	public boolean isBandTypesSupported();
	public OFMeterFeatures setCapabilities(Set<OFCapabilities> value);
	public Set<OFCapabilities> getCapabilities();
	public boolean isCapabilitiesSupported();
	public OFMeterFeatures setCapabilities(OFCapabilities ... value);
	public OFMeterFeatures setCapabilitiesWire(int value);
	public int getCapabilitiesWire();
	public OFMeterFeatures setMaxBands(byte value);
	public byte getMaxBands();
	public boolean isMaxBandsSupported();
	public OFMeterFeatures setMaxColor(byte value);
	public byte getMaxColor();
	public boolean isMaxColorSupported();
	
	public OFMeterFeatures dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
