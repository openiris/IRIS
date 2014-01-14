package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;
import java.util.Set;

public interface OFMatch  {

	public interface Builder {
		public Builder setWildcards(Set<OFFlowWildcards> value);
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
	}
	
	public Set<OFFlowWildcards> getWildcards();

	public OFMatch setWildcards(Set<OFFlowWildcards> value);

	public boolean isWildcardsSupported();

	public OFPort getInputPort();

	public OFMatch setInputPort(OFPort value);

	public boolean isInputPortSupported();

	public byte[] getDataLayerSource();

	public OFMatch setDataLayerSource(byte[] value);

	public boolean isDataLayerSourceSupported();

	public byte[] getDataLayerDestination();

	public OFMatch setDataLayerDestination(byte[] value);

	public boolean isDataLayerDestinationSupported();

	public short getDataLayerVirtualLan();

	public OFMatch setDataLayerVirtualLan(short value);

	public boolean isDataLayerVirtualLanSupported();

	public byte getDataLayerVirtualLanPriorityCodePoint();

	public OFMatch setDataLayerVirtualLanPriorityCodePoint(byte value);

	public boolean isDataLayerVirtualLanPriorityCodePointSupported();

	public short getDataLayerType();

	public OFMatch setDataLayerType(short value);

	public boolean isDataLayerTypeSupported();

	public byte getNetworkTypeOfService();

	public OFMatch setNetworkTypeOfService(byte value);

	public boolean isNetworkTypeOfServiceSupported();

	public byte getNetworkProtocol();

	public OFMatch setNetworkProtocol(byte value);

	public boolean isNetworkProtocolSupported();

	public int getNetworkSource();

	public OFMatch setNetworkSource(int value);

	public boolean isNetworkSourceSupported();

	public int getNetworkDestination();

	public OFMatch setNetworkDestination(int value);

	public boolean isNetworkDestinationSupported();

	public short getTransportSource();

	public OFMatch setTransportSource(short value);

	public boolean isTransportSourceSupported();

	public short getTransportDestination();

	public OFMatch setTransportDestination(short value);

	public boolean isTransportDestinationSupported();

	public List<OFOxm> getOxmFields();

	public OFMatch setOxmFields(List<OFOxm> value);

	public boolean isOxmFieldsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
