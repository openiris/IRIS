package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFStatisticsFlowRequest extends OFStatisticsRequest {

	public OFMatch getMatch();
	public OFStatisticsFlowRequest setMatch(OFMatch value);
	public boolean isMatchSupported();
	public byte getTableId();
	public OFStatisticsFlowRequest setTableId(byte value);
	public boolean isTableIdSupported();
	public OFPort getOutPort();
	public OFStatisticsFlowRequest setOutPort(OFPort value);
	public boolean isOutPortSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
