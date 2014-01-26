package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFStatisticsAggregateRequest extends OFStatisticsRequest {

	public OFMatch getMatch();
	public OFStatisticsAggregateRequest setMatch(OFMatch value);
	public boolean isMatchSupported();
	public byte getTableId();
	public OFStatisticsAggregateRequest setTableId(byte value);
	public boolean isTableIdSupported();
	public OFPort getOutPort();
	public OFStatisticsAggregateRequest setOutPort(OFPort value);
	public boolean isOutPortSupported();
	public int getOutGroup();
	public OFStatisticsAggregateRequest setOutGroup(int value);
	public boolean isOutGroupSupported();
	public long getCookie();
	public OFStatisticsAggregateRequest setCookie(long value);
	public boolean isCookieSupported();
	public long getCookieMask();
	public OFStatisticsAggregateRequest setCookieMask(long value);
	public boolean isCookieMaskSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
