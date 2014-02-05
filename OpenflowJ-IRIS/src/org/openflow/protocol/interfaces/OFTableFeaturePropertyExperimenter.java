package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFTableFeaturePropertyExperimenter extends OFTableFeatureProperty {

	public OFTableFeaturePropertyExperimenter setExperimenterId(int value);
	public int getExperimenterId();
	public boolean isExperimenterIdSupported();
	public OFTableFeaturePropertyExperimenter setSubtype(int value);
	public int getSubtype();
	public boolean isSubtypeSupported();
	public OFTableFeaturePropertyExperimenter setExperimenterData(byte[] value);
	public byte[] getExperimenterData();
	public boolean isExperimenterDataSupported();
	
	public OFTableFeaturePropertyExperimenter dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
