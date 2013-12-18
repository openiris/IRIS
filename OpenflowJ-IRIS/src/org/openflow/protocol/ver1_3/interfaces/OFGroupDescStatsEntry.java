package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupDescStatsEntry  {

	public short getLength();
	
	public OFGroupDescStatsEntry setLength(short value);
	
	public byte getType();
	
	public OFGroupDescStatsEntry setType(byte value);
	
	public int getGroupId();
	
	public OFGroupDescStatsEntry setGroupId(int value);
	
	public List<OFBucket> getBuckets();
	
	public OFGroupDescStatsEntry setBuckets(List<OFBucket> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
