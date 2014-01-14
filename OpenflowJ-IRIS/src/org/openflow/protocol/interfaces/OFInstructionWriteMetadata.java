package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFInstructionWriteMetadata extends OFInstruction {

	public long getMetadata();
	public OFInstructionWriteMetadata setMetadata(long value);
	public boolean isMetadataSupported();
	public long getMetadataMask();
	public OFInstructionWriteMetadata setMetadataMask(long value);
	public boolean isMetadataMaskSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
