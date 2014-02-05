package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFActionId  {

	public OFActionId setType(short value);
	public short getType();
	public boolean isTypeSupported();
	public OFActionId setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	
	public OFActionId dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
