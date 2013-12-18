package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_0.types.*;

public interface OFPacketIn extends OFMessage {

	public int getBufferId();
	
	public OFPacketIn setBufferId(int value);
	
	public short getTotalLength();
	
	public OFPacketIn setTotalLength(short value);
	
	public short getInputPort();
	
	public OFPacketIn setInputPort(short value);
	
	public OFPacketInReason getReason();
	
	public OFPacketIn setReason(OFPacketInReason value);
	
	public byte[] getData();
	
	public OFPacketIn setData(byte[] value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
