package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;



public interface OFTableStatsEntry  {

	public byte getTableId();
	
	public OFTableStatsEntry setTableId(byte value);
	
	public int getActiveCount();
	
	public OFTableStatsEntry setActiveCount(int value);
	
	public long getLookupCount();
	
	public OFTableStatsEntry setLookupCount(long value);
	
	public long getMatchedCount();
	
	public OFTableStatsEntry setMatchedCount(long value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
