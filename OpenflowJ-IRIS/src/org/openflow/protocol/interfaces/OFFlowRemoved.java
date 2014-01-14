package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFFlowRemoved extends OFMessage {

	public OFMatch getMatch();
	public OFFlowRemoved setMatch(OFMatch value);
	public boolean isMatchSupported();
	public long getCookie();
	public OFFlowRemoved setCookie(long value);
	public boolean isCookieSupported();
	public short getPriority();
	public OFFlowRemoved setPriority(short value);
	public boolean isPrioritySupported();
	public OFFlowRemovedReason getReason();
	public OFFlowRemoved setReason(OFFlowRemovedReason value);
	public boolean isReasonSupported();
	public int getDurationSec();
	public OFFlowRemoved setDurationSec(int value);
	public boolean isDurationSecSupported();
	public int getDurationNsec();
	public OFFlowRemoved setDurationNsec(int value);
	public boolean isDurationNsecSupported();
	public short getIdleTimeout();
	public OFFlowRemoved setIdleTimeout(short value);
	public boolean isIdleTimeoutSupported();
	public long getPacketCount();
	public OFFlowRemoved setPacketCount(long value);
	public boolean isPacketCountSupported();
	public long getByteCount();
	public OFFlowRemoved setByteCount(long value);
	public boolean isByteCountSupported();
	public byte getTableId();
	public OFFlowRemoved setTableId(byte value);
	public boolean isTableIdSupported();
	public short getHardTimeout();
	public OFFlowRemoved setHardTimeout(short value);
	public boolean isHardTimeoutSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
