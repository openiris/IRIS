package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMultipartExperimenterReply extends OFMultipartReply {

	public int getExperimenterId();
	public OFMultipartExperimenterReply setExperimenterId(int value);
	public boolean isExperimenterIdSupported();
	public int getExperimentType();
	public OFMultipartExperimenterReply setExperimentType(int value);
	public boolean isExperimentTypeSupported();
	public byte[] getData();
	public OFMultipartExperimenterReply setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
