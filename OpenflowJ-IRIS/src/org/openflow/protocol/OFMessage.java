package org.openflow.protocol;

import java.nio.ByteBuffer;

public interface OFMessage {
	public static final byte VERSION10 = 0x01;
	public static final byte VERSION13 = 0x04;
	
	public byte getVersion();
	public OFMessage setLength(short len);
	public int getLengthU();
	public void writeTo(ByteBuffer buf);
	public byte getTypeByte();
	public org.openflow.protocol.interfaces.OFMessageType getType();
	public int getXid();
	public OFMessage setXid(int xid);
	public short computeLength();
}
