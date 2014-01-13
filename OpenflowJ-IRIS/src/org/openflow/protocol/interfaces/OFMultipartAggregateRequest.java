package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFMultipartAggregateRequest extends OFMultipartRequest {

	public byte getTableId();
	public OFMultipartAggregateRequest setTableId(byte value);
	public OFPort getOutPort();
	public OFMultipartAggregateRequest setOutPort(OFPort value);
	public int getOutGroup();
	public OFMultipartAggregateRequest setOutGroup(int value);
	public long getCookie();
	public OFMultipartAggregateRequest setCookie(long value);
	public long getCookieMask();
	public OFMultipartAggregateRequest setCookieMask(long value);
	public OFMatch getMatch();
	public OFMultipartAggregateRequest setMatch(OFMatch value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
