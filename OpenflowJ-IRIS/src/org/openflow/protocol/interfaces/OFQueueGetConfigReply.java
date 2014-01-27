package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import org.openflow.protocol.OFPort;

public interface OFQueueGetConfigReply extends OFMessage {

	public OFQueueGetConfigReply setPort(OFPort value);
	public OFPort getPort();
	public boolean isPortSupported();
	public OFQueueGetConfigReply setQueues(List<OFPacketQueue> value);
	public List<OFPacketQueue> getQueues();
	public boolean isQueuesSupported();
	
	public OFQueueGetConfigReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
