package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFError extends OFMessage {

	public OFErrorCode getErrorCode();
	public OFError setErrorCode(OFErrorCode value);
	public short getSubcode();
	public OFError setSubcode(short value);
	public byte[] getData();
	public OFError setData(byte[] value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
