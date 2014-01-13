package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFTableFeatures  {

	public short getLength();
	public OFTableFeatures setLength(short value);
	public byte getTableId();
	public OFTableFeatures setTableId(byte value);
	public byte[] getName();
	public OFTableFeatures setName(byte[] value);
	public long getMetadataMatch();
	public OFTableFeatures setMetadataMatch(long value);
	public long getMetadataWrite();
	public OFTableFeatures setMetadataWrite(long value);
	public int getConfig();
	public OFTableFeatures setConfig(int value);
	public int getMaxEntries();
	public OFTableFeatures setMaxEntries(int value);
	public List<OFTableFeatureProperty> getProperties();
	public OFTableFeatures setProperties(List<OFTableFeatureProperty> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
