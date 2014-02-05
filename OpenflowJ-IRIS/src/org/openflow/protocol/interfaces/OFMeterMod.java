package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;

import java.util.List;

public interface OFMeterMod extends OFMessage {

	public OFMeterMod setCommand(OFMeterModCommand value);
	public OFMeterModCommand getCommand();
	public boolean isCommandSupported();
	public OFMeterMod setFlags(short value);
	public short getFlags();
	public boolean isFlagsSupported();
	public OFMeterMod setMeterId(int value);
	public int getMeterId();
	public boolean isMeterIdSupported();
	public OFMeterMod setMeters(List<OFMeterBand> value);
	public List<OFMeterBand> getMeters();
	public boolean isMetersSupported();
	
	public OFMeterMod dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
