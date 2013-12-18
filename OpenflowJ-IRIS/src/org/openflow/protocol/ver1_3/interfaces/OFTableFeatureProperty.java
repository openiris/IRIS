package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFTableFeatureProperty  {

	public OFTableFeaturePropertyType getType();
	
	public OFTableFeatureProperty setType(OFTableFeaturePropertyType value);
	
	public short getLength();
	
	public OFTableFeatureProperty setLength(short value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
