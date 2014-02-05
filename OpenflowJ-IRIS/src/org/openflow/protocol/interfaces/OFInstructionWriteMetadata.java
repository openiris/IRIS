package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFInstructionWriteMetadata extends OFInstruction {

	public OFInstructionWriteMetadata setMetadata(long value);
	public long getMetadata();
	public boolean isMetadataSupported();
	public OFInstructionWriteMetadata setMetadataMask(long value);
	public long getMetadataMask();
	public boolean isMetadataMaskSupported();
	
	public OFInstructionWriteMetadata dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
