package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsRequest extends OFStatistics {

	public OFStatisticsType getStatisticsType();
	public OFStatisticsRequest setStatisticsType(OFStatisticsType value);
	public boolean isStatisticsTypeSupported();
	public short getFlags();
	public OFStatisticsRequest setFlags(short value);
	public boolean isFlagsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
