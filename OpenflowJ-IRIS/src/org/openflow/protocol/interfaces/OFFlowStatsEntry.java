package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFFlowStatsEntry  {

	public OFFlowStatsEntry setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFFlowStatsEntry setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFFlowStatsEntry setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	public OFFlowStatsEntry setDurationSec(int value);
	public int getDurationSec();
	public boolean isDurationSecSupported();
	public OFFlowStatsEntry setDurationNsec(int value);
	public int getDurationNsec();
	public boolean isDurationNsecSupported();
	public OFFlowStatsEntry setPriority(short value);
	public short getPriority();
	public boolean isPrioritySupported();
	public OFFlowStatsEntry setIdleTimeout(short value);
	public short getIdleTimeout();
	public boolean isIdleTimeoutSupported();
	public OFFlowStatsEntry setHardTimeout(short value);
	public short getHardTimeout();
	public boolean isHardTimeoutSupported();
	public OFFlowStatsEntry setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFFlowStatsEntry setPacketCount(long value);
	public long getPacketCount();
	public boolean isPacketCountSupported();
	public OFFlowStatsEntry setByteCount(long value);
	public long getByteCount();
	public boolean isByteCountSupported();
	public OFFlowStatsEntry setActions(List<OFAction> value);
	public List<OFAction> getActions();
	public boolean isActionsSupported();
	public OFFlowStatsEntry setFlags(short value);
	public short getFlags();
	public boolean isFlagsSupported();
	public OFFlowStatsEntry setInstructions(List<OFInstruction> value);
	public List<OFInstruction> getInstructions();
	public boolean isInstructionsSupported();
	
	public OFFlowStatsEntry dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
