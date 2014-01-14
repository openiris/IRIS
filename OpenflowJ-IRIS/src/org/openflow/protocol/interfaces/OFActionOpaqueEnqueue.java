package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFActionOpaqueEnqueue extends OFAction {

	public OFPort getPort();
	public OFActionOpaqueEnqueue setPort(OFPort value);
	public boolean isPortSupported();
	public int getQueueId();
	public OFActionOpaqueEnqueue setQueueId(int value);
	public boolean isQueueIdSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
