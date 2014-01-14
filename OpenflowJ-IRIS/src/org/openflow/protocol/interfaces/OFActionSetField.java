package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFActionSetField extends OFAction {

	public OFOxm getField();
	public OFActionSetField setField(OFOxm value);
	public boolean isFieldSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
