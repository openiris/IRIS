package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFTableFeaturePropertyExperimenter extends OFTableFeatureProperty {

	public int getExperimenterId();
	public OFTableFeaturePropertyExperimenter setExperimenterId(int value);
	public boolean isExperimenterIdSupported();
	public int getSubtype();
	public OFTableFeaturePropertyExperimenter setSubtype(int value);
	public boolean isSubtypeSupported();
	public byte[] getExperimenterData();
	public OFTableFeaturePropertyExperimenter setExperimenterData(byte[] value);
	public boolean isExperimenterDataSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
