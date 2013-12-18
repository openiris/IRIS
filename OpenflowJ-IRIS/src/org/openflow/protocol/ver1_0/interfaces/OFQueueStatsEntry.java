package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;



public interface OFQueueStatsEntry  {

	public short getPortNumber();
	
	public OFQueueStatsEntry setPortNumber(short value);
	
	public int getQueueId();
	
	public OFQueueStatsEntry setQueueId(int value);
	
	public long getTransmitBytes();
	
	public OFQueueStatsEntry setTransmitBytes(long value);
	
	public long getTransmitPackets();
	
	public OFQueueStatsEntry setTransmitPackets(long value);
	
	public long getTransmitErrors();
	
	public OFQueueStatsEntry setTransmitErrors(long value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
