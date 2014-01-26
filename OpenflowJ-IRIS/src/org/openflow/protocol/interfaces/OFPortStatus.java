package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFPortStatus extends OFMessage {

	public OFPortStatus setReason(OFPortReason value);
	public OFPortReason getReason();
	public boolean isReasonSupported();
	public OFPortStatus setDesc(OFPortDesc value);
	public OFPortDesc getDesc();
	public boolean isDescSupported();
	
	public OFPortStatus dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
