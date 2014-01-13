package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;

public interface OFPacketQueue  {

	public int getQueueId();
	public OFPacketQueue setQueueId(int value);
	public short getLength();
	public OFPacketQueue setLength(short value);
	public List<OFQueueProperty> getProperties();
	public OFPacketQueue setProperties(List<OFQueueProperty> value);
	public OFPort getPort();
	public OFPacketQueue setPort(OFPort value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
