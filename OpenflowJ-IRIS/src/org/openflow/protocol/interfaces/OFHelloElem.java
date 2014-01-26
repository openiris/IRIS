package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFHelloElem  {

	public OFHelloElem setType(OFHelloElemType value);
	public OFHelloElemType getType();
	public boolean isTypeSupported();
	public OFHelloElem setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	
	public OFHelloElem dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
