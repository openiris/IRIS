package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupMod extends OFMessage {

	public OFGroupModCommand getCommand();
	public OFGroupMod setCommand(OFGroupModCommand value);
	public boolean isCommandSupported();
	public OFGroupCategory getGroupCategory();
	public OFGroupMod setGroupCategory(OFGroupCategory value);
	public boolean isGroupCategorySupported();
	public int getGroupId();
	public OFGroupMod setGroupId(int value);
	public boolean isGroupIdSupported();
	public List<OFBucket> getBuckets();
	public OFGroupMod setBuckets(List<OFBucket> value);
	public boolean isBucketsSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
