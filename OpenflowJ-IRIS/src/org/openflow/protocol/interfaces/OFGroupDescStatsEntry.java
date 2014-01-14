package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupDescStatsEntry  {

	public short getLength();
	public OFGroupDescStatsEntry setLength(short value);
	public boolean isLengthSupported();
	public byte getType();
	public OFGroupDescStatsEntry setType(byte value);
	public boolean isTypeSupported();
	public int getGroupId();
	public OFGroupDescStatsEntry setGroupId(int value);
	public boolean isGroupIdSupported();
	public List<OFBucket> getBuckets();
	public OFGroupDescStatsEntry setBuckets(List<OFBucket> value);
	public boolean isBucketsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
