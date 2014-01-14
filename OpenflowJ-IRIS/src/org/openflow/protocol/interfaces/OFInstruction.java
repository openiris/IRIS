package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFInstruction  {

	public OFInstructionType getType();
	public OFInstruction setType(OFInstructionType value);
	public boolean isTypeSupported();
	public short getLength();
	public OFInstruction setLength(short value);
	public boolean isLengthSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
