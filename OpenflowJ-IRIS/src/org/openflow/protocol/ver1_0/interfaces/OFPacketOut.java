package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFPacketOut extends OFMessage {

	public int getBufferId();
	
	public OFPacketOut setBufferId(int value);
	
	public short getInputPort();
	
	public OFPacketOut setInputPort(short value);
	
	public short getActionsLength();
	
	public OFPacketOut setActionsLength(short value);
	
	public List<OFAction> getActions();
	
	public OFPacketOut setActions(List<OFAction> value);
	
	public byte[] getData();
	
	public OFPacketOut setData(byte[] value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
