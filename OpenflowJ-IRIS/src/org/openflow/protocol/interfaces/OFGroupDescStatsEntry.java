package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupDescStatsEntry  {

	public OFGroupDescStatsEntry setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFGroupDescStatsEntry setType(byte value);
	public byte getType();
	public boolean isTypeSupported();
	public OFGroupDescStatsEntry setGroupId(int value);
	public int getGroupId();
	public boolean isGroupIdSupported();
	public OFGroupDescStatsEntry setBuckets(List<OFBucket> value);
	public List<OFBucket> getBuckets();
	public boolean isBucketsSupported();
	
	public OFGroupDescStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
