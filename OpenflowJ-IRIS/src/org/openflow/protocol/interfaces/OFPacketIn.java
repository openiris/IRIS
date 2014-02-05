package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.OFPort;

public interface OFPacketIn extends OFMessage {

	public OFPacketIn setBufferId(int value);
	public int getBufferId();
	public boolean isBufferIdSupported();
	public OFPacketIn setTotalLength(short value);
	public short getTotalLength();
	public boolean isTotalLengthSupported();
	public OFPacketIn setInputPort(OFPort value);
	public OFPort getInputPort();
	public boolean isInputPortSupported();
	public OFPacketIn setReason(OFPacketInReason value);
	public OFPacketInReason getReason();
	public boolean isReasonSupported();
	public OFPacketIn setData(byte[] value);
	public byte[] getData();
	public boolean isDataSupported();
	public OFPacketIn setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFPacketIn setCookie(long value);
	public long getCookie();
	public boolean isCookieSupported();
	public OFPacketIn setMatch(OFMatch value);
	public OFMatch getMatch();
	public boolean isMatchSupported();
	
	public OFPacketIn dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
