package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;



public interface OFStatisticsDescReply extends OFStatisticsReply {

	public OFStatisticsDescReply setManufacturerDescription(String value);
	public String getManufacturerDescription();
	public boolean isManufacturerDescriptionSupported();
	public OFStatisticsDescReply setHardwareDescription(String value);
	public String getHardwareDescription();
	public boolean isHardwareDescriptionSupported();
	public OFStatisticsDescReply setSoftwareDescription(String value);
	public String getSoftwareDescription();
	public boolean isSoftwareDescriptionSupported();
	public OFStatisticsDescReply setSerialNumber(String value);
	public String getSerialNumber();
	public boolean isSerialNumberSupported();
	public OFStatisticsDescReply setDatapathDescription(String value);
	public String getDatapathDescription();
	public boolean isDatapathDescriptionSupported();
	
	public OFStatisticsDescReply dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
