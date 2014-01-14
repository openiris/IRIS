package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMultipartAggregateReply extends OFMultipartReply {

	public long getPacketCount();
	public OFMultipartAggregateReply setPacketCount(long value);
	public boolean isPacketCountSupported();
	public long getByteCount();
	public OFMultipartAggregateReply setByteCount(long value);
	public boolean isByteCountSupported();
	public int getFlowCount();
	public OFMultipartAggregateReply setFlowCount(int value);
	public boolean isFlowCountSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
