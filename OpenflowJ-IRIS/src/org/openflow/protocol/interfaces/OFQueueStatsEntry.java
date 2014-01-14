package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFQueueStatsEntry  {

	public OFPort getPortNumber();
	public OFQueueStatsEntry setPortNumber(OFPort value);
	public boolean isPortNumberSupported();
	public int getQueueId();
	public OFQueueStatsEntry setQueueId(int value);
	public boolean isQueueIdSupported();
	public long getTransmitBytes();
	public OFQueueStatsEntry setTransmitBytes(long value);
	public boolean isTransmitBytesSupported();
	public long getTransmitPackets();
	public OFQueueStatsEntry setTransmitPackets(long value);
	public boolean isTransmitPacketsSupported();
	public long getTransmitErrors();
	public OFQueueStatsEntry setTransmitErrors(long value);
	public boolean isTransmitErrorsSupported();
	public int getDurationSec();
	public OFQueueStatsEntry setDurationSec(int value);
	public boolean isDurationSecSupported();
	public int getDurationNsec();
	public OFQueueStatsEntry setDurationNsec(int value);
	public boolean isDurationNsecSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
