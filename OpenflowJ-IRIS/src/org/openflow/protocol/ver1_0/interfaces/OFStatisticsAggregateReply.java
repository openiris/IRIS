package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsAggregateReply extends OFStatisticsReply {

	public long getPacketCount();
	
	public OFStatisticsAggregateReply setPacketCount(long value);
	
	public long getByteCount();
	
	public OFStatisticsAggregateReply setByteCount(long value);
	
	public int getFlowCount();
	
	public OFStatisticsAggregateReply setFlowCount(int value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
