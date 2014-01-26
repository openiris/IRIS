package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsExperimenterReply extends OFStatisticsReply {

	public OFStatisticsExperimenterReply setExperimenterId(int value);
	public int getExperimenterId();
	public boolean isExperimenterIdSupported();
	public OFStatisticsExperimenterReply setExperimentType(int value);
	public int getExperimentType();
	public boolean isExperimentTypeSupported();
	public OFStatisticsExperimenterReply setData(byte[] value);
	public byte[] getData();
	public boolean isDataSupported();
	
	public OFStatisticsExperimenterReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
