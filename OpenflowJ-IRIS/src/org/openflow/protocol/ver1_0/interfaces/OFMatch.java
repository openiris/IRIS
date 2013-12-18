package org.openflow.protocol.ver1_0.interfaces;

import java.nio.ByteBuffer;

import org.openflow.protocol.ver1_0.types.*;

public interface OFMatch  {

	public int getWildcards();
	
	public OFMatch setWildcards(int value);
	
	public short getInputPort();
	
	public OFMatch setInputPort(short value);
	
	public byte[] getDataLayerSource();
	
	public OFMatch setDataLayerSource(byte[] value);
	
	public byte[] getDataLayerDestination();
	
	public OFMatch setDataLayerDestination(byte[] value);
	
	public short getDataLayerVirtualLan();
	
	public OFMatch setDataLayerVirtualLan(short value);
	
	public byte getDataLayerVirtualLanPriorityCodePoint();
	
	public OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value);
	
	public short getDataLayerType();
	
	public OFMatch setDataLayerType(short value);
	
	public byte getNetworkTypeOfService();
	
	public OFMatch setNetworkTypeOfService(byte value);
	
	public byte getNetworkProtocol();
	
	public OFMatch setNetworkProtocol(byte value);
	
	public int getNetworkSource();
	
	public OFMatch setNetworkSource(int value);
	
	public int getNetworkDestination();
	
	public OFMatch setNetworkDestination(int value);
	
	public short getTransportSource();
	
	public OFMatch setTransportSource(short value);
	
	public short getTransportDestination();
	
	public OFMatch setTransportDestination(short value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
