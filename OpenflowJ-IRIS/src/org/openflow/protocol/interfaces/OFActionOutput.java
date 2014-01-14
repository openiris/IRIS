package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFActionOutput extends OFAction {

	public OFPort getPort();
	public OFActionOutput setPort(OFPort value);
	public boolean isPortSupported();
	public short getMaxLength();
	public OFActionOutput setMaxLength(short value);
	public boolean isMaxLengthSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
