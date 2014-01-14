package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFFlowStatsEntry  {

	public short getLength();
	public OFFlowStatsEntry setLength(short value);
	public boolean isLengthSupported();
	public byte getTableId();
	public OFFlowStatsEntry setTableId(byte value);
	public boolean isTableIdSupported();
	public OFMatch getMatch();
	public OFFlowStatsEntry setMatch(OFMatch value);
	public boolean isMatchSupported();
	public int getDurationSec();
	public OFFlowStatsEntry setDurationSec(int value);
	public boolean isDurationSecSupported();
	public int getDurationNsec();
	public OFFlowStatsEntry setDurationNsec(int value);
	public boolean isDurationNsecSupported();
	public short getPriority();
	public OFFlowStatsEntry setPriority(short value);
	public boolean isPrioritySupported();
	public short getIdleTimeout();
	public OFFlowStatsEntry setIdleTimeout(short value);
	public boolean isIdleTimeoutSupported();
	public short getHardTimeout();
	public OFFlowStatsEntry setHardTimeout(short value);
	public boolean isHardTimeoutSupported();
	public long getCookie();
	public OFFlowStatsEntry setCookie(long value);
	public boolean isCookieSupported();
	public long getPacketCount();
	public OFFlowStatsEntry setPacketCount(long value);
	public boolean isPacketCountSupported();
	public long getByteCount();
	public OFFlowStatsEntry setByteCount(long value);
	public boolean isByteCountSupported();
	public List<OFAction> getActions();
	public OFFlowStatsEntry setActions(List<OFAction> value);
	public boolean isActionsSupported();
	public short getFlags();
	public OFFlowStatsEntry setFlags(short value);
	public boolean isFlagsSupported();
	public List<OFInstruction> getInstructions();
	public OFFlowStatsEntry setInstructions(List<OFInstruction> value);
	public boolean isInstructionsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
