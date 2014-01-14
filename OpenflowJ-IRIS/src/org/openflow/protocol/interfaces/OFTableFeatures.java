package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFTableFeatures  {

	public short getLength();
	public OFTableFeatures setLength(short value);
	public boolean isLengthSupported();
	public byte getTableId();
	public OFTableFeatures setTableId(byte value);
	public boolean isTableIdSupported();
	public byte[] getName();
	public OFTableFeatures setName(byte[] value);
	public boolean isNameSupported();
	public long getMetadataMatch();
	public OFTableFeatures setMetadataMatch(long value);
	public boolean isMetadataMatchSupported();
	public long getMetadataWrite();
	public OFTableFeatures setMetadataWrite(long value);
	public boolean isMetadataWriteSupported();
	public int getConfig();
	public OFTableFeatures setConfig(int value);
	public boolean isConfigSupported();
	public int getMaxEntries();
	public OFTableFeatures setMaxEntries(int value);
	public boolean isMaxEntriesSupported();
	public List<OFTableFeatureProperty> getProperties();
	public OFTableFeatures setProperties(List<OFTableFeatureProperty> value);
	public boolean isPropertiesSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
