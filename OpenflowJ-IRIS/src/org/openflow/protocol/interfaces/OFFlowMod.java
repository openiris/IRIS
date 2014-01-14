package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;
import java.util.Set;

public interface OFFlowMod extends OFMessage {

	public OFMatch getMatch();
	public OFFlowMod setMatch(OFMatch value);
	public boolean isMatchSupported();
	public long getCookie();
	public OFFlowMod setCookie(long value);
	public boolean isCookieSupported();
	public OFFlowModCommand getCommand();
	public OFFlowMod setCommand(OFFlowModCommand value);
	public boolean isCommandSupported();
	public short getIdleTimeout();
	public OFFlowMod setIdleTimeout(short value);
	public boolean isIdleTimeoutSupported();
	public short getHardTimeout();
	public OFFlowMod setHardTimeout(short value);
	public boolean isHardTimeoutSupported();
	public short getPriority();
	public OFFlowMod setPriority(short value);
	public boolean isPrioritySupported();
	public int getBufferId();
	public OFFlowMod setBufferId(int value);
	public boolean isBufferIdSupported();
	public OFPort getOutPort();
	public OFFlowMod setOutPort(OFPort value);
	public boolean isOutPortSupported();
	public Set<OFFlowModFlags> getFlags();
	public OFFlowMod setFlags(Set<OFFlowModFlags> value);
	public boolean isFlagsSupported();
	public List<OFAction> getActions();
	public OFFlowMod setActions(List<OFAction> value);
	public boolean isActionsSupported();
	public long getCookieMask();
	public OFFlowMod setCookieMask(long value);
	public boolean isCookieMaskSupported();
	public byte getTableId();
	public OFFlowMod setTableId(byte value);
	public boolean isTableIdSupported();
	public int getOutGroup();
	public OFFlowMod setOutGroup(int value);
	public boolean isOutGroupSupported();
	public List<OFInstruction> getInstructions();
	public OFFlowMod setInstructions(List<OFInstruction> value);
	public boolean isInstructionsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
