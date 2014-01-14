package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsQueueRequest extends OFStatisticsRequest {

	public OFPortNo getPortNo();
	public OFStatisticsQueueRequest setPortNo(OFPortNo value);
	public boolean isPortNoSupported();
	public int getQueueId();
	public OFStatisticsQueueRequest setQueueId(int value);
	public boolean isQueueIdSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
