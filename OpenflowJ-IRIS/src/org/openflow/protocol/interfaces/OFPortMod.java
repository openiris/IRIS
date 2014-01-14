package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFPortMod extends OFMessage {

	public OFPortNo getPortNo();
	public OFPortMod setPortNo(OFPortNo value);
	public boolean isPortNoSupported();
	public byte[] getHwAddr();
	public OFPortMod setHwAddr(byte[] value);
	public boolean isHwAddrSupported();
	public int getConfig();
	public OFPortMod setConfig(int value);
	public boolean isConfigSupported();
	public int getMask();
	public OFPortMod setMask(int value);
	public boolean isMaskSupported();
	public int getAdvertise();
	public OFPortMod setAdvertise(int value);
	public boolean isAdvertiseSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
