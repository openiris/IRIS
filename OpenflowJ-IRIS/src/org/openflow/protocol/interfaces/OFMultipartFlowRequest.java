package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFMultipartFlowRequest extends OFMultipartRequest {

	public byte getTableId();
	public OFMultipartFlowRequest setTableId(byte value);
	public boolean isTableIdSupported();
	public OFPort getOutPort();
	public OFMultipartFlowRequest setOutPort(OFPort value);
	public boolean isOutPortSupported();
	public int getOutGroup();
	public OFMultipartFlowRequest setOutGroup(int value);
	public boolean isOutGroupSupported();
	public long getCookie();
	public OFMultipartFlowRequest setCookie(long value);
	public boolean isCookieSupported();
	public long getCookieMask();
	public OFMultipartFlowRequest setCookieMask(long value);
	public boolean isCookieMaskSupported();
	public OFMatch getMatch();
	public OFMultipartFlowRequest setMatch(OFMatch value);
	public boolean isMatchSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
