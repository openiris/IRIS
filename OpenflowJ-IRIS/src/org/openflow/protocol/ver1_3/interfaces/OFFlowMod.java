package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public interface OFFlowMod extends OFMessage {

	public long getCookie();
	
	public OFFlowMod setCookie(long value);
	
	public long getCookieMask();
	
	public OFFlowMod setCookieMask(long value);
	
	public byte getTableId();
	
	public OFFlowMod setTableId(byte value);
	
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
	
	public int getOutPort();
	
	public OFFlowMod setOutPort(int value);
	
	public int getOutGroup();
	
	public OFFlowMod setOutGroup(int value);
	
	public short getFlags();
	
	public OFFlowMod setFlags(short value);
	
	public OFMatchOxm getMatch();
	
	public OFFlowMod setMatch(OFMatchOxm value);
	
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
