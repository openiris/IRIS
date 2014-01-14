package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFGetConfigReply extends OFMessage {

	public short getFlags();
	public OFGetConfigReply setFlags(short value);
	public boolean isFlagsSupported();
	public short getMissSendLength();
	public OFGetConfigReply setMissSendLength(short value);
	public boolean isMissSendLengthSupported();
	public short getMissSendLen();
	public OFGetConfigReply setMissSendLen(short value);
	public boolean isMissSendLenSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
