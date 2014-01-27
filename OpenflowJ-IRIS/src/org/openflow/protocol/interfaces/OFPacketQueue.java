package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;
import java.util.List;

public interface OFPacketQueue  {

	public OFPacketQueue setQueueId(int value);
	public int getQueueId();
	public boolean isQueueIdSupported();
	public OFPacketQueue setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFPacketQueue setProperties(List<OFQueueProperty> value);
	public List<OFQueueProperty> getProperties();
	public boolean isPropertiesSupported();
	public OFPacketQueue setPort(OFPort value);
	public OFPort getPort();
	public boolean isPortSupported();
	
	public OFPacketQueue dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
