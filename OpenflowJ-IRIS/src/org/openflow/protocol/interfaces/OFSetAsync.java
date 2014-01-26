package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFSetAsync extends OFMessage {

	public OFSetAsync setPacketInMaskEqualMaster(int value);
	public int getPacketInMaskEqualMaster();
	public boolean isPacketInMaskEqualMasterSupported();
	public OFSetAsync setPacketInMaskSlave(int value);
	public int getPacketInMaskSlave();
	public boolean isPacketInMaskSlaveSupported();
	public OFSetAsync setPortStatusMaskEqualMaster(int value);
	public int getPortStatusMaskEqualMaster();
	public boolean isPortStatusMaskEqualMasterSupported();
	public OFSetAsync setPortStatusMaskSlave(int value);
	public int getPortStatusMaskSlave();
	public boolean isPortStatusMaskSlaveSupported();
	public OFSetAsync setFlowRemovedMaskEqualMaster(int value);
	public int getFlowRemovedMaskEqualMaster();
	public boolean isFlowRemovedMaskEqualMasterSupported();
	public OFSetAsync setFlowRemovedMaskSlave(int value);
	public int getFlowRemovedMaskSlave();
	public boolean isFlowRemovedMaskSlaveSupported();
	
	public OFSetAsync dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
