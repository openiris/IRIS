package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFPacketIn extends OFMessage {

	public int getBufferId();
	public OFPacketIn setBufferId(int value);
	public boolean isBufferIdSupported();
	public short getTotalLength();
	public OFPacketIn setTotalLength(short value);
	public boolean isTotalLengthSupported();
	public OFPort getInputPort();
	public OFPacketIn setInputPort(OFPort value);
	public boolean isInputPortSupported();
	public OFPacketInReason getReason();
	public OFPacketIn setReason(OFPacketInReason value);
	public boolean isReasonSupported();
	public byte[] getData();
	public OFPacketIn setData(byte[] value);
	public boolean isDataSupported();
	public byte getTableId();
	public OFPacketIn setTableId(byte value);
	public boolean isTableIdSupported();
	public long getCookie();
	public OFPacketIn setCookie(long value);
	public boolean isCookieSupported();
	public OFMatch getMatch();
	public OFPacketIn setMatch(OFMatch value);
	public boolean isMatchSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
