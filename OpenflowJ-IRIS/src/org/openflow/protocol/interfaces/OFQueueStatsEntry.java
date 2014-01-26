package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFQueueStatsEntry  {

	public OFQueueStatsEntry setPortNumber(OFPort value);
	public OFPort getPortNumber();
	public boolean isPortNumberSupported();
	public OFQueueStatsEntry setQueueId(int value);
	public int getQueueId();
	public boolean isQueueIdSupported();
	public OFQueueStatsEntry setTransmitBytes(long value);
	public long getTransmitBytes();
	public boolean isTransmitBytesSupported();
	public OFQueueStatsEntry setTransmitPackets(long value);
	public long getTransmitPackets();
	public boolean isTransmitPacketsSupported();
	public OFQueueStatsEntry setTransmitErrors(long value);
	public long getTransmitErrors();
	public boolean isTransmitErrorsSupported();
	public OFQueueStatsEntry setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFQueueStatsEntry setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	
	public OFQueueStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
