package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;

import java.util.List;

public interface OFPacketOut extends OFMessage {

	public OFPacketOut setBufferId(int value);
	public int getBufferId();
	public boolean isBufferIdSupported();
	public OFPacketOut setInputPort(OFPort value);
	public OFPort getInputPort();
	public boolean isInputPortSupported();
	public OFPacketOut setActionsLength(short value);
	public short getActionsLength();
	public boolean isActionsLengthSupported();
	public OFPacketOut setActions(List<OFAction> value);
	public List<OFAction> getActions();
	public boolean isActionsSupported();
	public OFPacketOut setData(byte[] value);
	public byte[] getData();
	public boolean isDataSupported();
	public OFPacketOut setInPort(OFPort value);
	public OFPort getInPort();
	public boolean isInPortSupported();
	
	public OFPacketOut dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
