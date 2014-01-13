package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;
import java.util.Set;

public interface OFFlowMod extends OFMessage {

	public OFMatch getMatch();
	public OFFlowMod setMatch(OFMatch value);
	public long getCookie();
	public OFFlowMod setCookie(long value);
	public OFFlowModCommand getCommand();
	public OFFlowMod setCommand(OFFlowModCommand value);
	public short getIdleTimeout();
	public OFFlowMod setIdleTimeout(short value);
	public short getHardTimeout();
	public OFFlowMod setHardTimeout(short value);
	public short getPriority();
	public OFFlowMod setPriority(short value);
	public int getBufferId();
	public OFFlowMod setBufferId(int value);
	public OFPort getOutPort();
	public OFFlowMod setOutPort(OFPort value);
	public Set<OFFlowModFlags> getFlags();
	public OFFlowMod setFlags(Set<OFFlowModFlags> value);
	public List<OFAction> getActions();
	public OFFlowMod setActions(List<OFAction> value);
	public long getCookieMask();
	public OFFlowMod setCookieMask(long value);
	public byte getTableId();
	public OFFlowMod setTableId(byte value);
	public int getOutGroup();
	public OFFlowMod setOutGroup(int value);
	public List<OFInstruction> getInstructions();
	public OFFlowMod setInstructions(List<OFInstruction> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
