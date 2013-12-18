package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFMeterBand  {

	public OFMeterBandType getType();
	
	public OFMeterBand setType(OFMeterBandType value);
	
	public short getLength();
	
	public OFMeterBand setLength(short value);
	
	public int getRate();
	
	public OFMeterBand setRate(int value);
	
	public int getBurstSize();
	
	public OFMeterBand setBurstSize(int value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
