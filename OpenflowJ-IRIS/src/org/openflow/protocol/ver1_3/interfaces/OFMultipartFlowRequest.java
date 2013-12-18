package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFMultipartFlowRequest extends OFMultipartRequest {

	public byte getTableId();
	
	public OFMultipartFlowRequest setTableId(byte value);
	
	public int getOutPort();
	
	public OFMultipartFlowRequest setOutPort(int value);
	
	public int getOutGroup();
	
	public OFMultipartFlowRequest setOutGroup(int value);
	
	public long getCookie();
	
	public OFMultipartFlowRequest setCookie(long value);
	
	public long getCookieMask();
	
	public OFMultipartFlowRequest setCookieMask(long value);
	
	public OFMatchOxm getMatch();
	
	public OFMultipartFlowRequest setMatch(OFMatchOxm value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
