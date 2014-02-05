package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFStatisticsGroupFeaturesReply extends OFStatisticsReply {

	public OFStatisticsGroupFeaturesReply setTypes(int value);
	public int getTypes();
	public boolean isTypesSupported();
	public OFStatisticsGroupFeaturesReply setCapabilities(Set<OFCapabilities> value);
	public Set<OFCapabilities> getCapabilities();
	public boolean isCapabilitiesSupported();
	public OFStatisticsGroupFeaturesReply setCapabilities(OFCapabilities ... value);
	public OFStatisticsGroupFeaturesReply setCapabilitiesWire(int value);
	public int getCapabilitiesWire();
	public OFStatisticsGroupFeaturesReply setMaxGroupsAll(int value);
	public int getMaxGroupsAll();
	public boolean isMaxGroupsAllSupported();
	public OFStatisticsGroupFeaturesReply setMaxGroupsSelect(int value);
	public int getMaxGroupsSelect();
	public boolean isMaxGroupsSelectSupported();
	public OFStatisticsGroupFeaturesReply setMaxGroupsIndirect(int value);
	public int getMaxGroupsIndirect();
	public boolean isMaxGroupsIndirectSupported();
	public OFStatisticsGroupFeaturesReply setMaxGroupsFf(int value);
	public int getMaxGroupsFf();
	public boolean isMaxGroupsFfSupported();
	public OFStatisticsGroupFeaturesReply setActionsAll(int value);
	public int getActionsAll();
	public boolean isActionsAllSupported();
	public OFStatisticsGroupFeaturesReply setActionsSelect(int value);
	public int getActionsSelect();
	public boolean isActionsSelectSupported();
	public OFStatisticsGroupFeaturesReply setActionsIndirect(int value);
	public int getActionsIndirect();
	public boolean isActionsIndirectSupported();
	public OFStatisticsGroupFeaturesReply setActionsFf(int value);
	public int getActionsFf();
	public boolean isActionsFfSupported();
	
	public OFStatisticsGroupFeaturesReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
