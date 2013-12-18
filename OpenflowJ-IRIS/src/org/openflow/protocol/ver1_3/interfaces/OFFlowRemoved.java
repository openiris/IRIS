package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFFlowRemoved extends OFMessage {

	public long getCookie();
	
	public OFFlowRemoved setCookie(long value);
	
	public short getPriority();
	
	public OFFlowRemoved setPriority(short value);
	
	public OFFlowRemovedReason getReason();
	
	public OFFlowRemoved setReason(OFFlowRemovedReason value);
	
	public byte getTableId();
	
	public OFFlowRemoved setTableId(byte value);
	
	public int getDurationSec();
	
	public OFFlowRemoved setDurationSec(int value);
	
	public int getDurationNsec();
	
	public OFFlowRemoved setDurationNsec(int value);
	
	public short getIdleTimeout();
	
	public OFFlowRemoved setIdleTimeout(short value);
	
	public short getHardTimeout();
	
	public OFFlowRemoved setHardTimeout(short value);
	
	public long getPacketCount();
	
	public OFFlowRemoved setPacketCount(long value);
	
	public long getByteCount();
	
	public OFFlowRemoved setByteCount(long value);
	
	public OFMatchOxm getMatch();
	
	public OFFlowRemoved setMatch(OFMatchOxm value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
