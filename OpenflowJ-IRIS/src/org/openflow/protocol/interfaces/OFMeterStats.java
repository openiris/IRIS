package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFMeterStats  {

	public OFMeterStats setMeterId(int value);
	public int getMeterId();
	public boolean isMeterIdSupported();
	public OFMeterStats setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFMeterStats setFlowCount(int value);
	public int getFlowCount();
	public boolean isFlowCountSupported();
	public OFMeterStats setPacketInCount(long value);
	public long getPacketInCount();
	public boolean isPacketInCountSupported();
	public OFMeterStats setByteInCount(long value);
	public long getByteInCount();
	public boolean isByteInCountSupported();
	public OFMeterStats setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFMeterStats setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	public OFMeterStats setBandStats(List<OFMeterBandStats> value);
	public List<OFMeterBandStats> getBandStats();
	public boolean isBandStatsSupported();
	
	public OFMeterStats dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
