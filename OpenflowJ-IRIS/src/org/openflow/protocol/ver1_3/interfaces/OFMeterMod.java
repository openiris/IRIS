package org.openflow.protocol.ver1_3.interfaces;

import java.nio.ByteBuffer;

import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public interface OFMeterMod extends OFMessage {

	public OFMeterModCommand getCommand();
	
	public OFMeterMod setCommand(OFMeterModCommand value);
	
	public short getFlags();
	
	public OFMeterMod setFlags(short value);
	
	public int getMeterId();
	
	public OFMeterMod setMeterId(int value);
	
	public List<OFMeterBand> getMeters();
	
	public OFMeterMod setMeters(List<OFMeterBand> value);
	

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
