package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;

public interface OFPortDesc  {

	public OFPort getPort();
	public OFPortDesc setPort(OFPort value);
	public boolean isPortSupported();
	public byte[] getHwAddr();
	public OFPortDesc setHwAddr(byte[] value);
	public boolean isHwAddrSupported();
	public byte[] getName();
	public OFPortDesc setName(byte[] value);
	public boolean isNameSupported();
	public int getConfig();
	public OFPortDesc setConfig(int value);
	public boolean isConfigSupported();
	public int getState();
	public OFPortDesc setState(int value);
	public boolean isStateSupported();
	public int getCurrentFeatures();
	public OFPortDesc setCurrentFeatures(int value);
	public boolean isCurrentFeaturesSupported();
	public int getAdvertisedFeatures();
	public OFPortDesc setAdvertisedFeatures(int value);
	public boolean isAdvertisedFeaturesSupported();
	public int getSupportedFeatures();
	public OFPortDesc setSupportedFeatures(int value);
	public boolean isSupportedFeaturesSupported();
	public int getPeerFeatures();
	public OFPortDesc setPeerFeatures(int value);
	public boolean isPeerFeaturesSupported();
	public int getCurrSpeed();
	public OFPortDesc setCurrSpeed(int value);
	public boolean isCurrSpeedSupported();
	public int getMaxSpeed();
	public OFPortDesc setMaxSpeed(int value);
	public boolean isMaxSpeedSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
