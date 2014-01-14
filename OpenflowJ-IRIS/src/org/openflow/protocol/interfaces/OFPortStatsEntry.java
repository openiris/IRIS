package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFPortStatsEntry  {

	public OFPort getPortNumber();
	public OFPortStatsEntry setPortNumber(OFPort value);
	public boolean isPortNumberSupported();
	public long getReceivePackets();
	public OFPortStatsEntry setReceivePackets(long value);
	public boolean isReceivePacketsSupported();
	public long getTransmitPackets();
	public OFPortStatsEntry setTransmitPackets(long value);
	public boolean isTransmitPacketsSupported();
	public long getReceiveBytes();
	public OFPortStatsEntry setReceiveBytes(long value);
	public boolean isReceiveBytesSupported();
	public long getTransmitBytes();
	public OFPortStatsEntry setTransmitBytes(long value);
	public boolean isTransmitBytesSupported();
	public long getReceiveDropped();
	public OFPortStatsEntry setReceiveDropped(long value);
	public boolean isReceiveDroppedSupported();
	public long getTransmitDropped();
	public OFPortStatsEntry setTransmitDropped(long value);
	public boolean isTransmitDroppedSupported();
	public long getReceiveErrors();
	public OFPortStatsEntry setReceiveErrors(long value);
	public boolean isReceiveErrorsSupported();
	public long getTransmitErrors();
	public OFPortStatsEntry setTransmitErrors(long value);
	public boolean isTransmitErrorsSupported();
	public long getReceiveFrameErrors();
	public OFPortStatsEntry setReceiveFrameErrors(long value);
	public boolean isReceiveFrameErrorsSupported();
	public long getReceiveOverrunErrors();
	public OFPortStatsEntry setReceiveOverrunErrors(long value);
	public boolean isReceiveOverrunErrorsSupported();
	public long getReceiveCrcErr();
	public OFPortStatsEntry setReceiveCrcErr(long value);
	public boolean isReceiveCrcErrSupported();
	public long getCollisions();
	public OFPortStatsEntry setCollisions(long value);
	public boolean isCollisionsSupported();
	public long getReceiveCrcErrors();
	public OFPortStatsEntry setReceiveCrcErrors(long value);
	public boolean isReceiveCrcErrorsSupported();
	public int getDurationSec();
	public OFPortStatsEntry setDurationSec(int value);
	public boolean isDurationSecSupported();
	public int getDurationNsec();
	public OFPortStatsEntry setDurationNsec(int value);
	public boolean isDurationNsecSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
