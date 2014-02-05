package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import org.openflow.protocol.OFPort;

public interface OFBucket  {

	public OFBucket setLength(short value);
	public short getLength();
	public boolean isLengthSupported();
	public OFBucket setWeight(short value);
	public short getWeight();
	public boolean isWeightSupported();
	public OFBucket setWatchPort(OFPort value);
	public OFPort getWatchPort();
	public boolean isWatchPortSupported();
	public OFBucket setWatchGroup(int value);
	public int getWatchGroup();
	public boolean isWatchGroupSupported();
	public OFBucket setActions(List<OFAction> value);
	public List<OFAction> getActions();
	public boolean isActionsSupported();
	
	public OFBucket dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
