package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFTableFeatures  {

	public OFTableFeatures setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFTableFeatures setTableId(byte value);
	public byte getTableId();
	public boolean isTableIdSupported();
	public OFTableFeatures setName(byte[] value);
	public byte[] getName();
	public boolean isNameSupported();
	public OFTableFeatures setMetadataMatch(long value);
	public long getMetadataMatch();
	public boolean isMetadataMatchSupported();
	public OFTableFeatures setMetadataWrite(long value);
	public long getMetadataWrite();
	public boolean isMetadataWriteSupported();
	public OFTableFeatures setConfig(int value);
	public int getConfig();
	public boolean isConfigSupported();
	public OFTableFeatures setMaxEntries(int value);
	public int getMaxEntries();
	public boolean isMaxEntriesSupported();
	public OFTableFeatures setProperties(List<OFTableFeatureProperty> value);
	public List<OFTableFeatureProperty> getProperties();
	public boolean isPropertiesSupported();
	
	public OFTableFeatures dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
