package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFAction  {

	public OFAction setType(OFActionType value);
	public OFActionType getType();
	public boolean isTypeSupported();
	public OFAction setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	
	public OFAction dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
