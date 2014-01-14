package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFGetAsyncRequest extends OFMessage {

	public int getPacketInMaskEqualMaster();
	public OFGetAsyncRequest setPacketInMaskEqualMaster(int value);
	public boolean isPacketInMaskEqualMasterSupported();
	public int getPacketInMaskSlave();
	public OFGetAsyncRequest setPacketInMaskSlave(int value);
	public boolean isPacketInMaskSlaveSupported();
	public int getPortStatusMaskEqualMaster();
	public OFGetAsyncRequest setPortStatusMaskEqualMaster(int value);
	public boolean isPortStatusMaskEqualMasterSupported();
	public int getPortStatusMaskSlave();
	public OFGetAsyncRequest setPortStatusMaskSlave(int value);
	public boolean isPortStatusMaskSlaveSupported();
	public int getFlowRemovedMaskEqualMaster();
	public OFGetAsyncRequest setFlowRemovedMaskEqualMaster(int value);
	public boolean isFlowRemovedMaskEqualMasterSupported();
	public int getFlowRemovedMaskSlave();
	public OFGetAsyncRequest setFlowRemovedMaskSlave(int value);
	public boolean isFlowRemovedMaskSlaveSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
