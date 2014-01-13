package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFMeterStats  {

	public int getMeterId();
	public OFMeterStats setMeterId(int value);
	public short getLength();
	public OFMeterStats setLength(short value);
	public int getFlowCount();
	public OFMeterStats setFlowCount(int value);
	public long getPacketInCount();
	public OFMeterStats setPacketInCount(long value);
	public long getByteInCount();
	public OFMeterStats setByteInCount(long value);
	public int getDurationSec();
	public OFMeterStats setDurationSec(int value);
	public int getDurationNsec();
	public OFMeterStats setDurationNsec(int value);
	public List<OFMeterBandStats> getBandStats();
	public OFMeterStats setBandStats(List<OFMeterBandStats> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
