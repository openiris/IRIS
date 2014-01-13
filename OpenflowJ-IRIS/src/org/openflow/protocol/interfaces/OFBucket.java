package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import org.openflow.util.OFPort;
import java.util.List;

public interface OFBucket  {

	public short getLength();
	public OFBucket setLength(short value);
	public short getWeight();
	public OFBucket setWeight(short value);
	public OFPort getWatchPort();
	public OFBucket setWatchPort(OFPort value);
	public int getWatchGroup();
	public OFBucket setWatchGroup(int value);
	public List<OFAction> getActions();
	public OFBucket setActions(List<OFAction> value);

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
