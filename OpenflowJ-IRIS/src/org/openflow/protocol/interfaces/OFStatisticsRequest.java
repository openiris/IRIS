package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFStatisticsRequest extends OFStatistics {

	public OFStatisticsRequest setStatisticsType(OFStatisticsType value);
	public OFStatisticsType getStatisticsType();
	public boolean isStatisticsTypeSupported();
	public OFStatisticsRequest setFlags(Set<OFStatisticsRequestFlags> value);
	public Set<OFStatisticsRequestFlags> getFlags();
	public boolean isFlagsSupported();
	public OFStatisticsRequest setFlags(OFStatisticsRequestFlags ... value);
	public OFStatisticsRequest setFlagsWire(short value);
	public short getFlagsWire();
	
	public OFStatisticsRequest dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
