package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFTableFeaturePropertyExperimenterMiss extends OFTableFeatureProperty {

	public OFTableFeaturePropertyExperimenterMiss setExperimenterId(int value);
	public int getExperimenterId();
	public boolean isExperimenterIdSupported();
	public OFTableFeaturePropertyExperimenterMiss setSubtype(int value);
	public int getSubtype();
	public boolean isSubtypeSupported();
	public OFTableFeaturePropertyExperimenterMiss setExperimenterData(byte[] value);
	public byte[] getExperimenterData();
	public boolean isExperimenterDataSupported();
	
	public OFTableFeaturePropertyExperimenterMiss dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
