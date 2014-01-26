package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFStatisticsFlowRequest extends OFStatisticsRequest {

	public OFStatisticsFlowRequest setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	public OFStatisticsFlowRequest setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFStatisticsFlowRequest setOutPort(OFPort value);
	public OFPort getOutPort();
	public boolean isOutPortSupported();
	public OFStatisticsFlowRequest setOutGroup(int value);
	public int getOutGroup();
	public boolean isOutGroupSupported();
	public OFStatisticsFlowRequest setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFStatisticsFlowRequest setCookieMask(long value);
	public long getCookieMask();
	public boolean isCookieMaskSupported();
	
	public OFStatisticsFlowRequest dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
