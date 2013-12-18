package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_3.types.*;

public interface OFMessage extends org.openflow.protocol.OFMessage {

	public byte getVersion();
	
	public OFMessage setVersion(byte value);
	
	public short getVersionU();
	
	public OFMessage setVersionU(short value);
	
	public OFMessageType getType();
	
	public OFMessage setType(OFMessageType value);
	
	public short getLength();
	
	public OFMessage setLength(short value);
	
	public int getLengthU();
	
	public OFMessage setLengthU(int value);
	
	public int getXid();
	
	public OFMessage setXid(int value);
	
	public long getXidU();
	
	public OFMessage setXidU(long value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
