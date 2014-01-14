package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFMultipartRequest extends OFMultipart {

	public OFMultipartType getMultipartType();
	public OFMultipartRequest setMultipartType(OFMultipartType value);
	public boolean isMultipartTypeSupported();
	public Set<OFMultipartRequestFlags> getFlags();
	public OFMultipartRequest setFlags(Set<OFMultipartRequestFlags> value);
	public boolean isFlagsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
