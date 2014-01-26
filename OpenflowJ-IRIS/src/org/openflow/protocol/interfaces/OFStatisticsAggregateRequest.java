package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFStatisticsAggregateRequest extends OFStatisticsRequest {

	public OFStatisticsAggregateRequest setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	public OFStatisticsAggregateRequest setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFStatisticsAggregateRequest setOutPort(OFPort value);
	public OFPort getOutPort();
	public boolean isOutPortSupported();
	public OFStatisticsAggregateRequest setOutGroup(int value);
	public int getOutGroup();
	public boolean isOutGroupSupported();
	public OFStatisticsAggregateRequest setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFStatisticsAggregateRequest setCookieMask(long value);
	public long getCookieMask();
	public boolean isCookieMaskSupported();
	
	public OFStatisticsAggregateRequest dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
