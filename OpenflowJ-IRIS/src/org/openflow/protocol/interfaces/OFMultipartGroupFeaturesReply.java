package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.Set;

public interface OFMultipartGroupFeaturesReply extends OFMultipartReply {

	public int getTypes();
	public OFMultipartGroupFeaturesReply setTypes(int value);
	public Set<OFCapabilities> getCapabilities();
	public OFMultipartGroupFeaturesReply setCapabilities(Set<OFCapabilities> value);
	public int getMaxGroupsAll();
	public OFMultipartGroupFeaturesReply setMaxGroupsAll(int value);
	public int getMaxGroupsSelect();
	public OFMultipartGroupFeaturesReply setMaxGroupsSelect(int value);
	public int getMaxGroupsIndirect();
	public OFMultipartGroupFeaturesReply setMaxGroupsIndirect(int value);
	public int getMaxGroupsFf();
	public OFMultipartGroupFeaturesReply setMaxGroupsFf(int value);
	public int getActionsAll();
	public OFMultipartGroupFeaturesReply setActionsAll(int value);
	public int getActionsSelect();
	public OFMultipartGroupFeaturesReply setActionsSelect(int value);
	public int getActionsIndirect();
	public OFMultipartGroupFeaturesReply setActionsIndirect(int value);
	public int getActionsFf();
	public OFMultipartGroupFeaturesReply setActionsFf(int value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
