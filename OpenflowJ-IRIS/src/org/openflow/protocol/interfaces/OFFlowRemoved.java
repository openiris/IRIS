package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFFlowRemoved extends OFMessage {

	public OFFlowRemoved setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	public OFFlowRemoved setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFFlowRemoved setPriority(short value);
	public short getPriority();
	public boolean isPrioritySupported();
	public OFFlowRemoved setReason(OFFlowRemovedReason value);
	public OFFlowRemovedReason getReason();
	public boolean isReasonSupported();
	public OFFlowRemoved setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFFlowRemoved setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	public OFFlowRemoved setIdleTimeout(short value);
	public short getIdleTimeout();
	public boolean isIdleTimeoutSupported();
	public OFFlowRemoved setPacketCount(long value);
	public long getPacketCount();
	public boolean isPacketCountSupported();
	public OFFlowRemoved setByteCount(long value);
	public long getByteCount();
	public boolean isByteCountSupported();
	public OFFlowRemoved setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFFlowRemoved setHardTimeout(short value);
	public short getHardTimeout();
	public boolean isHardTimeoutSupported();
	
	public OFFlowRemoved dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
