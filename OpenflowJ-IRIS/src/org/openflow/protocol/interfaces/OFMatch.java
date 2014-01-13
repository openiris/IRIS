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
	}
	
	public Set<OFFlowWildcards> getWildcards();

	public OFMatch setWildcards(Set<OFFlowWildcards> value);

	public OFPort getInputPort();

	public OFMatch setInputPort(OFPort value);

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

	public List<OFOxm> getOxmFields();

	public OFMatchOxm setOxmFields(List<OFOxm> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
