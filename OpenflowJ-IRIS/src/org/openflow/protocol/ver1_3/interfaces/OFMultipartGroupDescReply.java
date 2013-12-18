package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFMultipartGroupDescReply extends OFMultipartReply {

	public List<OFGroupDescStatsEntry> getEntries();
	
	public OFMultipartGroupDescReply setEntries(List<OFGroupDescStatsEntry> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
