package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsAggregateReply extends OFStatisticsReply {

	public OFStatisticsAggregateReply setPacketCount(long value);
	public long getPacketCount();
	public boolean isPacketCountSupported();
	public OFStatisticsAggregateReply setByteCount(long value);
	public long getByteCount();
	public boolean isByteCountSupported();
	public OFStatisticsAggregateReply setFlowCount(int value);
	public int getFlowCount();
	public boolean isFlowCountSupported();
	
	public OFStatisticsAggregateReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
