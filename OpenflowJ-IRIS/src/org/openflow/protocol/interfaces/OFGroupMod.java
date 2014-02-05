package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFGroupMod extends OFMessage {

	public OFGroupMod setCommand(OFGroupModCommand value);
	public OFGroupModCommand getCommand();
	public boolean isCommandSupported();
	public OFGroupMod setGroupCategory(OFGroupCategory value);
	public OFGroupCategory getGroupCategory();
	public boolean isGroupCategorySupported();
	public OFGroupMod setGroupId(int value);
	public int getGroupId();
	public boolean isGroupIdSupported();
	public OFGroupMod setBuckets(List<OFBucket> value);
	public List<OFBucket> getBuckets();
	public boolean isBucketsSupported();
	
	public OFGroupMod dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
