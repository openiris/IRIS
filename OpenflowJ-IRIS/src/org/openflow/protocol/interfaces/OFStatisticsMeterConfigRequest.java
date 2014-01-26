package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsMeterConfigRequest extends OFStatisticsRequest {

	public int getMeterId();
	public OFStatisticsMeterConfigRequest setMeterId(int value);
	public boolean isMeterIdSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
