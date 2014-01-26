package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;
import org.openflow.protocol.interfaces.OFOxmMatchFields;

import org.openflow.util.OFPort;
import java.util.List;
import java.util.Set;

public interface OFMatch  {

	public interface Builder {
	
		public Builder setValue(OFOxmMatchFields match_field, byte mask, byte[] data);
		public boolean isSetValueSupported();
		
		public Set<OFFlowWildcards> getWildcards();
		public int getWildcardsWire();
		public OFPort getInputPort();
		public byte[] getDataLayerSource();
		public byte[] getDataLayerDestination();
		public short getDataLayerVirtualLan();
		public byte getDataLayerVirtualLanPriorityCodePoint();
		public short getDataLayerType();
		public byte getNetworkTypeOfService();
		public byte getNetworkProtocol();
		public int getNetworkSource();
		public int getNetworkDestination();
		public short getTransportSource();
		public short getTransportDestination();
		public List<OFOxm> getOxmFields();
		public Builder setWildcards(Set<OFFlowWildcards> value);
		public Builder setWildcards(OFFlowWildcards ... value);
		public Builder setWildcardsWire(int value);
		public Builder setInputPort(OFPort value);
		public Builder setDataLayerSource(byte[] value);
		public Builder setDataLayerDestination(byte[] value);
		public Builder setDataLayerVirtualLan(short value);
		public Builder setDataLayerVirtualLanPriorityCodePoint(byte value);
		public Builder setDataLayerType(short value);
		public Builder setNetworkTypeOfService(byte value);
		public Builder setNetworkProtocol(byte value);
		public Builder setNetworkSource(int value);
		public Builder setNetworkDestination(int value);
		public Builder setTransportSource(short value);
		public Builder setTransportDestination(short value);
		public Builder setOxmFields(List<OFOxm> value);
		public boolean isWildcardsSupported();
		public boolean isInputPortSupported();
		public boolean isDataLayerSourceSupported();
		public boolean isDataLayerDestinationSupported();
		public boolean isDataLayerVirtualLanSupported();
		public boolean isDataLayerVirtualLanPriorityCodePointSupported();
		public boolean isDataLayerTypeSupported();
		public boolean isNetworkTypeOfServiceSupported();
		public boolean isNetworkProtocolSupported();
		public boolean isNetworkSourceSupported();
		public boolean isNetworkDestinationSupported();
		public boolean isTransportSourceSupported();
		public boolean isTransportDestinationSupported();
		public boolean isOxmFieldsSupported();
		
		public OFMatch build();
	}
	
	public OFMatch setWildcards(Set<OFFlowWildcards> value);

	public Set<OFFlowWildcards> getWildcards();

	public boolean isWildcardsSupported();

	public OFMatch setWildcards(OFFlowWildcards ... value);

	public OFMatch setWildcardsWire(int value);

	public int getWildcardsWire();

	public OFMatch setInputPort(OFPort value);

	public OFPort getInputPort();

	public boolean isInputPortSupported();

	public OFMatch setDataLayerSource(byte[] value);

	public byte[] getDataLayerSource();

	public boolean isDataLayerSourceSupported();

	public OFMatch setDataLayerDestination(byte[] value);

	public byte[] getDataLayerDestination();

	public boolean isDataLayerDestinationSupported();

	public OFMatch setDataLayerVirtualLan(short value);

	public short getDataLayerVirtualLan();

	public boolean isDataLayerVirtualLanSupported();

	public OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value);

	public byte getDataLayerVirtualLanPriorityCodePoint();

	public boolean isDataLayerVirtualLanPriorityCodePointSupported();

	public OFMatch setDataLayerType(short value);

	public short getDataLayerType();

	public boolean isDataLayerTypeSupported();

	public OFMatch setNetworkTypeOfService(byte value);

	public byte getNetworkTypeOfService();

	public boolean isNetworkTypeOfServiceSupported();

	public OFMatch setNetworkProtocol(byte value);

	public byte getNetworkProtocol();

	public boolean isNetworkProtocolSupported();

	public OFMatch setNetworkSource(int value);

	public int getNetworkSource();

	public boolean isNetworkSourceSupported();

	public OFMatch setNetworkDestination(int value);

	public int getNetworkDestination();

	public boolean isNetworkDestinationSupported();

	public OFMatch setTransportSource(short value);

	public short getTransportSource();

	public boolean isTransportSourceSupported();

	public OFMatch setTransportDestination(short value);

	public short getTransportDestination();

	public boolean isTransportDestinationSupported();

	public void addOxmToIndex(OFOxm oxm);

	public OFOxm getOxmFromIndex(OFOxmMatchFields field);

	public OFMatch setOxmFields(List<OFOxm> value);

	public List<OFOxm> getOxmFields();

	public boolean isOxmFieldsSupported();
	
	public OFMatch dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
