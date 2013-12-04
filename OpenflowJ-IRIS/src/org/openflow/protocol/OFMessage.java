package org.openflow.protocol;

import java.nio.ByteBuffer;

public interface OFMessage {
	public byte getVersion();
	public OFMessage setLength(short len);
	public int getLengthU();
	public void writeTo(ByteBuffer buf);
	public byte getTypeByte();
	public int getXid();
	public OFMessage setXid(int xid);
	public short computeLength();
}
