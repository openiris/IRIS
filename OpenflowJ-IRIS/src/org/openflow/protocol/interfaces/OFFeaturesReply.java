package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import java.util.Set;

public interface OFFeaturesReply extends OFMessage {

	public long getDatapathId();
	public OFFeaturesReply setDatapathId(long value);
	public boolean isDatapathIdSupported();
	public int getNBuffers();
	public OFFeaturesReply setNBuffers(int value);
	public boolean isNBuffersSupported();
	public byte getNTables();
	public OFFeaturesReply setNTables(byte value);
	public boolean isNTablesSupported();
	public Set<OFCapabilities> getCapabilities();
	public OFFeaturesReply setCapabilities(Set<OFCapabilities> value);
	public boolean isCapabilitiesSupported();
	public int getActions();
	public OFFeaturesReply setActions(int value);
	public boolean isActionsSupported();
	public List<OFPortDesc> getPorts();
	public OFFeaturesReply setPorts(List<OFPortDesc> value);
	public boolean isPortsSupported();
	public byte getAuxiliaryId();
	public OFFeaturesReply setAuxiliaryId(byte value);
	public boolean isAuxiliaryIdSupported();
	public int getReserved();
	public OFFeaturesReply setReserved(int value);
	public boolean isReservedSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
