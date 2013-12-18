package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;



public interface OFFeaturesReply extends OFMessage {

	public long getDatapathId();
	
	public OFFeaturesReply setDatapathId(long value);
	
	public int getNBuffers();
	
	public OFFeaturesReply setNBuffers(int value);
	
	public byte getNTables();
	
	public OFFeaturesReply setNTables(byte value);
	
	public byte getAuxiliaryId();
	
	public OFFeaturesReply setAuxiliaryId(byte value);
	
	public int getCapabilities();
	
	public OFFeaturesReply setCapabilities(int value);
	
	public int getReserved();
	
	public OFFeaturesReply setReserved(int value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
