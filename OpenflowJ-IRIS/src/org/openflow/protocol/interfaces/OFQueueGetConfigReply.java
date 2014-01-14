package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;

public interface OFQueueGetConfigReply extends OFMessage {

	public OFPort getPort();
	public OFQueueGetConfigReply setPort(OFPort value);
	public boolean isPortSupported();
	public List<OFPacketQueue> getQueues();
	public OFQueueGetConfigReply setQueues(List<OFPacketQueue> value);
	public boolean isQueuesSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
