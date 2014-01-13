package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFMultipartReply extends OFMultipart {

	public OFMultipartType getMultipartType();
	public OFMultipartReply setMultipartType(OFMultipartType value);
	public Set<OFMultipartReplyFlags> getFlags();
	public OFMultipartReply setFlags(Set<OFMultipartReplyFlags> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
