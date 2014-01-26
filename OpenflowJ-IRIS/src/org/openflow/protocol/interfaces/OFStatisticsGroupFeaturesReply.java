package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFStatisticsGroupFeaturesReply extends OFStatisticsReply {

	public int getTypes();
	public OFStatisticsGroupFeaturesReply setTypes(int value);
	public boolean isTypesSupported();
	public Set<OFCapabilities> getCapabilities();
	public OFStatisticsGroupFeaturesReply setCapabilities(Set<OFCapabilities> value);
	public boolean isCapabilitiesSupported();
	public int getMaxGroupsAll();
	public OFStatisticsGroupFeaturesReply setMaxGroupsAll(int value);
	public boolean isMaxGroupsAllSupported();
	public int getMaxGroupsSelect();
	public OFStatisticsGroupFeaturesReply setMaxGroupsSelect(int value);
	public boolean isMaxGroupsSelectSupported();
	public int getMaxGroupsIndirect();
	public OFStatisticsGroupFeaturesReply setMaxGroupsIndirect(int value);
	public boolean isMaxGroupsIndirectSupported();
	public int getMaxGroupsFf();
	public OFStatisticsGroupFeaturesReply setMaxGroupsFf(int value);
	public boolean isMaxGroupsFfSupported();
	public int getActionsAll();
	public OFStatisticsGroupFeaturesReply setActionsAll(int value);
	public boolean isActionsAllSupported();
	public int getActionsSelect();
	public OFStatisticsGroupFeaturesReply setActionsSelect(int value);
	public boolean isActionsSelectSupported();
	public int getActionsIndirect();
	public OFStatisticsGroupFeaturesReply setActionsIndirect(int value);
	public boolean isActionsIndirectSupported();
	public int getActionsFf();
	public OFStatisticsGroupFeaturesReply setActionsFf(int value);
	public boolean isActionsFfSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
