package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;



public interface OFPortDesc  {

	public short getPort();
	
	public OFPortDesc setPort(short value);
	
	public byte[] getHwAddr();
	
	public OFPortDesc setHwAddr(byte[] value);
	
	public byte[] getName();
	
	public OFPortDesc setName(byte[] value);
	
	public int getConfig();
	
	public OFPortDesc setConfig(int value);
	
	public int getState();
	
	public OFPortDesc setState(int value);
	
	public int getCurrentFeatures();
	
	public OFPortDesc setCurrentFeatures(int value);
	
	public int getAdvertisedFeatures();
	
	public OFPortDesc setAdvertisedFeatures(int value);
	
	public int getSupportedFeatures();
	
	public OFPortDesc setSupportedFeatures(int value);
	
	public int getPeerFeatures();
	
	public OFPortDesc setPeerFeatures(int value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
