package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFTableStatsEntry  {

	public OFTableStatsEntry setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFTableStatsEntry setName(byte[] value);
	public byte[] getName();
	public boolean isNameSupported();
	public OFTableStatsEntry setWildcards(Set<OFFlowWildcards> value);
	public Set<OFFlowWildcards> getWildcards();
	public boolean isWildcardsSupported();
	public OFTableStatsEntry setWildcards(OFFlowWildcards ... value);
	public OFTableStatsEntry setWildcardsWire(int value);
	public int getWildcardsWire();
	public OFTableStatsEntry setMaxEntries(int value);
	public int getMaxEntries();
	public boolean isMaxEntriesSupported();
	public OFTableStatsEntry setActiveCount(int value);
	public int getActiveCount();
	public boolean isActiveCountSupported();
	public OFTableStatsEntry setLookupCount(long value);
	public long getLookupCount();
	public boolean isLookupCountSupported();
	public OFTableStatsEntry setMatchedCount(long value);
	public long getMatchedCount();
	public boolean isMatchedCountSupported();
	
	public OFTableStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
