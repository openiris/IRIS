package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFExperimenter extends OFMessage {

	public int getExperimenterId();
	public OFExperimenter setExperimenterId(int value);
	public int getSubtype();
	public OFExperimenter setSubtype(int value);
	public byte[] getData();
	public OFExperimenter setData(byte[] value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
