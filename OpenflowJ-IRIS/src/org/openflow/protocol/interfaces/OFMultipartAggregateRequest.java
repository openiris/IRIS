package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFMultipartAggregateRequest extends OFMultipartRequest {

	public byte getTableId();
	public OFMultipartAggregateRequest setTableId(byte value);
	public boolean isTableIdSupported();
	public OFPort getOutPort();
	public OFMultipartAggregateRequest setOutPort(OFPort value);
	public boolean isOutPortSupported();
	public int getOutGroup();
	public OFMultipartAggregateRequest setOutGroup(int value);
	public boolean isOutGroupSupported();
	public long getCookie();
	public OFMultipartAggregateRequest setCookie(long value);
	public boolean isCookieSupported();
	public long getCookieMask();
	public OFMultipartAggregateRequest setCookieMask(long value);
	public boolean isCookieMaskSupported();
	public OFMatch getMatch();
	public OFMultipartAggregateRequest setMatch(OFMatch value);
	public boolean isMatchSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
