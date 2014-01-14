package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFActionExperimenter extends OFAction {

	public int getExperimenterId();
	public OFActionExperimenter setExperimenterId(int value);
	public boolean isExperimenterIdSupported();
	public byte[] getData();
	public OFActionExperimenter setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
