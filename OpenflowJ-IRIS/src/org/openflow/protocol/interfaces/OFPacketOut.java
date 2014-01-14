package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;

public interface OFPacketOut extends OFMessage {

	public int getBufferId();
	public OFPacketOut setBufferId(int value);
	public boolean isBufferIdSupported();
	public OFPort getInputPort();
	public OFPacketOut setInputPort(OFPort value);
	public boolean isInputPortSupported();
	public short getActionsLength();
	public OFPacketOut setActionsLength(short value);
	public boolean isActionsLengthSupported();
	public List<OFAction> getActions();
	public OFPacketOut setActions(List<OFAction> value);
	public boolean isActionsSupported();
	public byte[] getData();
	public OFPacketOut setData(byte[] value);
	public boolean isDataSupported();
	public OFPort getInPort();
	public OFPacketOut setInPort(OFPort value);
	public boolean isInPortSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
