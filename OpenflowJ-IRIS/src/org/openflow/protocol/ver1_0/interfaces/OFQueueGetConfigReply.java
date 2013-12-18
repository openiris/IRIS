package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFQueueGetConfigReply extends OFMessage {

	public short getPort();
	
	public OFQueueGetConfigReply setPort(short value);
	
	public List<OFPacketQueue> getQueues();
	
	public OFQueueGetConfigReply setQueues(List<OFPacketQueue> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
