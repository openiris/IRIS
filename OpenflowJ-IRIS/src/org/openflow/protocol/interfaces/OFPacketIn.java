package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFPacketIn extends OFMessage {

	public int getBufferId();
	public OFPacketIn setBufferId(int value);
	public short getTotalLength();
	public OFPacketIn setTotalLength(short value);
	public OFPort getInputPort();
	public OFPacketIn setInputPort(OFPort value);
	public OFPacketInReason getReason();
	public OFPacketIn setReason(OFPacketInReason value);
	public byte[] getData();
	public OFPacketIn setData(byte[] value);
	public byte getTableId();
	public OFPacketIn setTableId(byte value);
	public long getCookie();
	public OFPacketIn setCookie(long value);
	public OFMatch getMatch();
	public OFPacketIn setMatch(OFMatch value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
