package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_0.types.*;

public interface OFStatisticsAggregateRequest extends OFStatisticsRequest {

	public OFMatch getMatch();
	
	public OFStatisticsAggregateRequest setMatch(OFMatch value);
	
	public byte getTableId();
	
	public OFStatisticsAggregateRequest setTableId(byte value);
	
	public short getOutPort();
	
	public OFStatisticsAggregateRequest setOutPort(short value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
