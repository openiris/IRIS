package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import java.util.Set;

public interface OFFeaturesReply extends OFMessage {

	public OFFeaturesReply setDatapathId(long value);
	public long getDatapathId();
	public boolean isDatapathIdSupported();
	public OFFeaturesReply setNBuffers(int value);
	public int getNBuffers();
	public boolean isNBuffersSupported();
	public OFFeaturesReply setNTables(byte value);
	public byte getNTables();
	public boolean isNTablesSupported();
	public OFFeaturesReply setCapabilities(Set<OFCapabilities> value);
	public Set<OFCapabilities> getCapabilities();
	public boolean isCapabilitiesSupported();
	public OFFeaturesReply setCapabilities(OFCapabilities ... value);
	public OFFeaturesReply setCapabilitiesWire(int value);
	public int getCapabilitiesWire();
	public OFFeaturesReply setActions(int value);
	public int getActions();
	public boolean isActionsSupported();
	public OFFeaturesReply setPorts(List<OFPortDesc> value);
	public List<OFPortDesc> getPorts();
	public boolean isPortsSupported();
	public OFFeaturesReply setAuxiliaryId(byte value);
	public byte getAuxiliaryId();
	public boolean isAuxiliaryIdSupported();
	public OFFeaturesReply setReserved(int value);
	public int getReserved();
	public boolean isReservedSupported();
	
	public OFFeaturesReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
