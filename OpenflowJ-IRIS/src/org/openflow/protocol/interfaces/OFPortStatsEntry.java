package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;

public interface OFPortStatsEntry  {

	public OFPortStatsEntry setPortNumber(OFPort value);
	public OFPort getPortNumber();
	public boolean isPortNumberSupported();
	public OFPortStatsEntry setReceivePackets(long value);
	public long getReceivePackets();
	public boolean isReceivePacketsSupported();
	public OFPortStatsEntry setTransmitPackets(long value);
	public long getTransmitPackets();
	public boolean isTransmitPacketsSupported();
	public OFPortStatsEntry setReceiveBytes(long value);
	public long getReceiveBytes();
	public boolean isReceiveBytesSupported();
	public OFPortStatsEntry setTransmitBytes(long value);
	public long getTransmitBytes();
	public boolean isTransmitBytesSupported();
	public OFPortStatsEntry setReceiveDropped(long value);
	public long getReceiveDropped();
	public boolean isReceiveDroppedSupported();
	public OFPortStatsEntry setTransmitDropped(long value);
	public long getTransmitDropped();
	public boolean isTransmitDroppedSupported();
	public OFPortStatsEntry setReceiveErrors(long value);
	public long getReceiveErrors();
	public boolean isReceiveErrorsSupported();
	public OFPortStatsEntry setTransmitErrors(long value);
	public long getTransmitErrors();
	public boolean isTransmitErrorsSupported();
	public OFPortStatsEntry setReceiveFrameErrors(long value);
	public long getReceiveFrameErrors();
	public boolean isReceiveFrameErrorsSupported();
	public OFPortStatsEntry setReceiveOverrunErrors(long value);
	public long getReceiveOverrunErrors();
	public boolean isReceiveOverrunErrorsSupported();
	public OFPortStatsEntry setReceiveCrcErr(long value);
	public long getReceiveCrcErr();
	public boolean isReceiveCrcErrSupported();
	public OFPortStatsEntry setCollisions(long value);
	public long getCollisions();
	public boolean isCollisionsSupported();
	public OFPortStatsEntry setReceiveCrcErrors(long value);
	public long getReceiveCrcErrors();
	public boolean isReceiveCrcErrorsSupported();
	public OFPortStatsEntry setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFPortStatsEntry setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	
	public OFPortStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
