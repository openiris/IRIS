package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFFeaturesReply extends OFMessage {

	public long getDatapathId();
	
	public OFFeaturesReply setDatapathId(long value);
	
	public int getNBuffers();
	
	public OFFeaturesReply setNBuffers(int value);
	
	public byte getNTables();
	
	public OFFeaturesReply setNTables(byte value);
	
	public int getCapabilities();
	
	public OFFeaturesReply setCapabilities(int value);
	
	public int getActions();
	
	public OFFeaturesReply setActions(int value);
	
	public List<OFPortDesc> getPorts();
	
	public OFFeaturesReply setPorts(List<OFPortDesc> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
