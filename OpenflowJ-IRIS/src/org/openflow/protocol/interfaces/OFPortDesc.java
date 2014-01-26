package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.Set;

public interface OFPortDesc  {

	public OFPortDesc setPort(OFPort value);
	public OFPort getPort();
	public boolean isPortSupported();
	public OFPortDesc setHwAddr(byte[] value);
	public byte[] getHwAddr();
	public boolean isHwAddrSupported();
	public OFPortDesc setName(byte[] value);
	public byte[] getName();
	public boolean isNameSupported();
	public OFPortDesc setConfig(Set<OFPortConfig> value);
	public Set<OFPortConfig> getConfig();
	public boolean isConfigSupported();
	public OFPortDesc setConfig(OFPortConfig ... value);
	public OFPortDesc setConfigWire(int value);
	public int getConfigWire();
	public OFPortDesc setState(Set<OFPortState> value);
	public Set<OFPortState> getState();
	public boolean isStateSupported();
	public OFPortDesc setState(OFPortState ... value);
	public OFPortDesc setStateWire(int value);
	public int getStateWire();
	public OFPortDesc setCurrentFeatures(int value);
	public int getCurrentFeatures();
	public boolean isCurrentFeaturesSupported();
	public OFPortDesc setAdvertisedFeatures(int value);
	public int getAdvertisedFeatures();
	public boolean isAdvertisedFeaturesSupported();
	public OFPortDesc setSupportedFeatures(int value);
	public int getSupportedFeatures();
	public boolean isSupportedFeaturesSupported();
	public OFPortDesc setPeerFeatures(int value);
	public int getPeerFeatures();
	public boolean isPeerFeaturesSupported();
	public OFPortDesc setCurrSpeed(int value);
	public int getCurrSpeed();
	public boolean isCurrSpeedSupported();
	public OFPortDesc setMaxSpeed(int value);
	public int getMaxSpeed();
	public boolean isMaxSpeedSupported();
	
	public OFPortDesc dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
