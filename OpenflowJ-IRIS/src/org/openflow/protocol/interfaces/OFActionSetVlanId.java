package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFActionSetVlanId extends OFAction {

	public OFActionSetVlanId setVlanId(short value);
	public short getVlanId();
	public boolean isVlanIdSupported();
	
	public OFActionSetVlanId dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
