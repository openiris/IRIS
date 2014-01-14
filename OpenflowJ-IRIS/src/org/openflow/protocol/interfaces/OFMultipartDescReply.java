package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFMultipartDescReply extends OFMultipartReply {

	public String getManufacturerDescription();
	public OFMultipartDescReply setManufacturerDescription(String value);
	public boolean isManufacturerDescriptionSupported();
	public String getHardwareDescription();
	public OFMultipartDescReply setHardwareDescription(String value);
	public boolean isHardwareDescriptionSupported();
	public String getSoftwareDescription();
	public OFMultipartDescReply setSoftwareDescription(String value);
	public boolean isSoftwareDescriptionSupported();
	public String getSerialNumber();
	public OFMultipartDescReply setSerialNumber(String value);
	public boolean isSerialNumberSupported();
	public String getDatapathDescription();
	public OFMultipartDescReply setDatapathDescription(String value);
	public boolean isDatapathDescriptionSupported();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
