package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;



public interface OFGetAsyncReply extends OFMessage {

	public int getPacketInMaskEqualMaster();
	
	public OFGetAsyncReply setPacketInMaskEqualMaster(int value);
	
	public int getPacketInMaskSlave();
	
	public OFGetAsyncReply setPacketInMaskSlave(int value);
	
	public int getPortStatusMaskEqualMaster();
	
	public OFGetAsyncReply setPortStatusMaskEqualMaster(int value);
	
	public int getPortStatusMaskSlave();
	
	public OFGetAsyncReply setPortStatusMaskSlave(int value);
	
	public int getFlowRemovedMaskEqualMaster();
	
	public OFGetAsyncReply setFlowRemovedMaskEqualMaster(int value);
	
	public int getFlowRemovedMaskSlave();
	
	public OFGetAsyncReply setFlowRemovedMaskSlave(int value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
