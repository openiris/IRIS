package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;



public interface OFTableFeaturePropertyExperimenter extends OFTableFeatureProperty {

	public int getExperimenterId();
	
	public OFTableFeaturePropertyExperimenter setExperimenterId(int value);
	
	public int getSubtype();
	
	public OFTableFeaturePropertyExperimenter setSubtype(int value);
	
	public byte[] getExperimenterData();
	
	public OFTableFeaturePropertyExperimenter setExperimenterData(byte[] value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
