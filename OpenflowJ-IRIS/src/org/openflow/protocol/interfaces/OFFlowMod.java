package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;

import java.util.List;
import java.util.Set;

public interface OFFlowMod extends OFMessage {

	public OFFlowMod setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	public OFFlowMod setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFFlowMod setCommand(OFFlowModCommand value);
	public OFFlowModCommand getCommand();
	public boolean isCommandSupported();
	public OFFlowMod setIdleTimeout(short value);
	public short getIdleTimeout();
	public boolean isIdleTimeoutSupported();
	public OFFlowMod setHardTimeout(short value);
	public short getHardTimeout();
	public boolean isHardTimeoutSupported();
	public OFFlowMod setPriority(short value);
	public short getPriority();
	public boolean isPrioritySupported();
	public OFFlowMod setBufferId(int value);
	public int getBufferId();
	public boolean isBufferIdSupported();
	public OFFlowMod setOutPort(OFPort value);
	public OFPort getOutPort();
	public boolean isOutPortSupported();
	public OFFlowMod setFlags(Set<OFFlowModFlags> value);
	public Set<OFFlowModFlags> getFlags();
	public boolean isFlagsSupported();
	public OFFlowMod setFlags(OFFlowModFlags ... value);
	public OFFlowMod setFlagsWire(short value);
	public short getFlagsWire();
	public OFFlowMod setActions(List<OFAction> value);
	public List<OFAction> getActions();
	public boolean isActionsSupported();
	public OFFlowMod setCookieMask(long value);
	public long getCookieMask();
	public boolean isCookieMaskSupported();
	public OFFlowMod setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFFlowMod setOutGroup(int value);
	public int getOutGroup();
	public boolean isOutGroupSupported();
	public OFFlowMod setInstructions(List<OFInstruction> value);
	public List<OFInstruction> getInstructions();
	public boolean isInstructionsSupported();
	
	public OFFlowMod dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
