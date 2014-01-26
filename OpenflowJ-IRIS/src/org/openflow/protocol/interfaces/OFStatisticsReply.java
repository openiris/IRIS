package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFStatisticsReply extends OFStatistics {

	public OFStatisticsReply setStatisticsType(OFStatisticsType value);
	public OFStatisticsType getStatisticsType();
	public boolean isStatisticsTypeSupported();
	public OFStatisticsReply setFlags(Set<OFStatisticsReplyFlags> value);
	public Set<OFStatisticsReplyFlags> getFlags();
	public boolean isFlagsSupported();
	public OFStatisticsReply setFlags(OFStatisticsReplyFlags ... value);
	public OFStatisticsReply setFlagsWire(short value);
	public short getFlagsWire();
	
	public OFStatisticsReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
