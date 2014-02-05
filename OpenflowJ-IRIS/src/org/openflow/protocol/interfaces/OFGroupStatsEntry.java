package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupStatsEntry  {

	public OFGroupStatsEntry setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFGroupStatsEntry setGroupId(int value);
	public int getGroupId();
	public boolean isGroupIdSupported();
	public OFGroupStatsEntry setRefCount(int value);
	public int getRefCount();
	public boolean isRefCountSupported();
	public OFGroupStatsEntry setPacketCount(long value);
	public long getPacketCount();
	public boolean isPacketCountSupported();
	public OFGroupStatsEntry setByteCount(long value);
	public long getByteCount();
	public boolean isByteCountSupported();
	public OFGroupStatsEntry setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFGroupStatsEntry setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	public OFGroupStatsEntry setBucketStats(List<OFBucketCounter> value);
	public List<OFBucketCounter> getBucketStats();
	public boolean isBucketStatsSupported();
	
	public OFGroupStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
