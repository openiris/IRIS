package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFGetAsyncRequest extends OFMessage {

	public int getPacketInMaskEqualMaster();
	public OFGetAsyncRequest setPacketInMaskEqualMaster(int value);
	public int getPacketInMaskSlave();
	public OFGetAsyncRequest setPacketInMaskSlave(int value);
	public int getPortStatusMaskEqualMaster();
	public OFGetAsyncRequest setPortStatusMaskEqualMaster(int value);
	public int getPortStatusMaskSlave();
	public OFGetAsyncRequest setPortStatusMaskSlave(int value);
	public int getFlowRemovedMaskEqualMaster();
	public OFGetAsyncRequest setFlowRemovedMaskEqualMaster(int value);
	public int getFlowRemovedMaskSlave();
	public OFGetAsyncRequest setFlowRemovedMaskSlave(int value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
