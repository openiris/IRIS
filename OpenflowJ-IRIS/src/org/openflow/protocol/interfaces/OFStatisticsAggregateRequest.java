package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFStatisticsAggregateRequest extends OFStatisticsRequest {

	public OFMatch getMatch();
	public OFStatisticsAggregateRequest setMatch(OFMatch value);
	public byte getTableId();
	public OFStatisticsAggregateRequest setTableId(byte value);
	public OFPort getOutPort();
	public OFStatisticsAggregateRequest setOutPort(OFPort value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
