package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupStatsEntry  {

	public short getLength();
	public OFGroupStatsEntry setLength(short value);
	public int getGroupId();
	public OFGroupStatsEntry setGroupId(int value);
	public int getRefCount();
	public OFGroupStatsEntry setRefCount(int value);
	public long getPacketCount();
	public OFGroupStatsEntry setPacketCount(long value);
	public long getByteCount();
	public OFGroupStatsEntry setByteCount(long value);
	public int getDurationSec();
	public OFGroupStatsEntry setDurationSec(int value);
	public int getDurationNsec();
	public OFGroupStatsEntry setDurationNsec(int value);
	public List<OFBucketCounter> getBucketStats();
	public OFGroupStatsEntry setBucketStats(List<OFBucketCounter> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
