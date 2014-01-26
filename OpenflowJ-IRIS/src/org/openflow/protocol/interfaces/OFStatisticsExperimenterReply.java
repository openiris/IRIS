package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsExperimenterReply extends OFStatisticsReply {

	public int getExperimenterId();
	public OFStatisticsExperimenterReply setExperimenterId(int value);
	public boolean isExperimenterIdSupported();
	public int getExperimentType();
	public OFStatisticsExperimenterReply setExperimentType(int value);
	public boolean isExperimentTypeSupported();
	public byte[] getData();
	public OFStatisticsExperimenterReply setData(byte[] value);
	public boolean isDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
