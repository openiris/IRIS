package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFActionPushVlan extends OFAction {

	public short getEthertype();
	public OFActionPushVlan setEthertype(short value);
	public boolean isEthertypeSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
