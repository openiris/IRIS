package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import org.openflow.protocol.ver1_0.types.*;

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
	
	public short getOutPort();
	
	public OFFlowMod setOutPort(short value);
	
	public short getFlags();
	
	public OFFlowMod setFlags(short value);
	
	public List<OFAction> getActions();
	
	public OFFlowMod setActions(List<OFAction> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
