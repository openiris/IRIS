package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;

public interface OFQueueGetConfigRequest extends OFMessage {

	public OFQueueGetConfigRequest setPort(OFPort value);
	public OFPort getPort();
	public boolean isPortSupported();
	
	public OFQueueGetConfigRequest dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
