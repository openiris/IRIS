package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMultipartQueueRequest extends OFMultipartRequest {

	public OFPortNo getPortNo();
	public OFMultipartQueueRequest setPortNo(OFPortNo value);
	public int getQueueId();
	public OFMultipartQueueRequest setQueueId(int value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
