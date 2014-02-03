package org.openflow.protocol.ver1_0.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import org.openflow.protocol.ver1_0.types.*;

public class OFStatisticsDescReply extends OFStatisticsReply implements org.openflow.protocol.interfaces.OFStatisticsDescReply {
    public static int MINIMUM_LENGTH = 1068;
    public static int CORE_LENGTH = 1056;

    String  manufacturer_description;
	String  hardware_description;
	String  software_description;
	String  serial_number;
	String  datapath_description;

    public OFStatisticsDescReply() {
        super();
		setLength(U16.t(MINIMUM_LENGTH));
		setType(OFMessageType.valueOf((byte)17));
		setStatisticsType(OFStatisticsType.valueOf((short)0, this.type));
    }
    
    public OFStatisticsDescReply(OFStatisticsDescReply other) {
    	super(other);
		this.manufacturer_description = other.manufacturer_description;
		this.hardware_description = other.hardware_description;
		this.software_description = other.software_description;
		this.serial_number = other.serial_number;
		this.datapath_description = other.datapath_description;
    }

	public String getManufacturerDescription() {
		return this.manufacturer_description;
	}
	
	public OFStatisticsDescReply setManufacturerDescription(String manufacturer_description) {
		this.manufacturer_description = manufacturer_description;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isManufacturerDescriptionSupported() {
		return true;
	}
			
	public String getHardwareDescription() {
		return this.hardware_description;
	}
	
	public OFStatisticsDescReply setHardwareDescription(String hardware_description) {
		this.hardware_description = hardware_description;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isHardwareDescriptionSupported() {
		return true;
	}
			
	public String getSoftwareDescription() {
		return this.software_description;
	}
	
	public OFStatisticsDescReply setSoftwareDescription(String software_description) {
		this.software_description = software_description;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isSoftwareDescriptionSupported() {
		return true;
	}
			
	public String getSerialNumber() {
		return this.serial_number;
	}
	
	public OFStatisticsDescReply setSerialNumber(String serial_number) {
		this.serial_number = serial_number;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isSerialNumberSupported() {
		return true;
	}
			
	public String getDatapathDescription() {
		return this.datapath_description;
	}
	
	public OFStatisticsDescReply setDatapathDescription(String datapath_description) {
		this.datapath_description = datapath_description;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isDatapathDescriptionSupported() {
		return true;
	}
			
	
	
	
	public OFStatisticsDescReply dup() {
		return new OFStatisticsDescReply(this);
	}
	
    public void readFrom(ByteBuffer data) {
        super.readFrom(data);
		this.manufacturer_description = StringByteSerializer.readFrom(data, 256);
		this.hardware_description = StringByteSerializer.readFrom(data, 256);
		this.software_description = StringByteSerializer.readFrom(data, 256);
		this.serial_number = StringByteSerializer.readFrom(data, 32);
		this.datapath_description = StringByteSerializer.readFrom(data, 256);
    }

    public void writeTo(ByteBuffer data) {
    	super.writeTo(data);
        StringByteSerializer.writeTo(data, 256, manufacturer_description);
		StringByteSerializer.writeTo(data, 256, hardware_description);
		StringByteSerializer.writeTo(data, 256, software_description);
		StringByteSerializer.writeTo(data, 32, serial_number);
		StringByteSerializer.writeTo(data, 256, datapath_description);
    }

    public String toString() {
        return super.toString() +  ":OFStatisticsDescReply-"+":manufacturer_description=" + manufacturer_description.toString() + 
		":hardware_description=" + hardware_description.toString() + 
		":software_description=" + software_description.toString() + 
		":serial_number=" + serial_number.toString() + 
		":datapath_description=" + datapath_description.toString();
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)(CORE_LENGTH + super.computeLength());
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 2633;
		int result = super.hashCode() * prime;
		result = prime * result + ((manufacturer_description == null)?0:manufacturer_description.hashCode());
		result = prime * result + ((hardware_description == null)?0:hardware_description.hashCode());
		result = prime * result + ((software_description == null)?0:software_description.hashCode());
		result = prime * result + ((serial_number == null)?0:serial_number.hashCode());
		result = prime * result + ((datapath_description == null)?0:datapath_description.hashCode());
		return result;
    }

    @Override
    public boolean equals(Object obj) {
        
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFStatisticsDescReply)) {
            return false;
        }
        OFStatisticsDescReply other = (OFStatisticsDescReply) obj;
		if ( manufacturer_description == null && other.manufacturer_description != null ) { return false; }
		else if ( !manufacturer_description.equals(other.manufacturer_description) ) { return false; }
		if ( hardware_description == null && other.hardware_description != null ) { return false; }
		else if ( !hardware_description.equals(other.hardware_description) ) { return false; }
		if ( software_description == null && other.software_description != null ) { return false; }
		else if ( !software_description.equals(other.software_description) ) { return false; }
		if ( serial_number == null && other.serial_number != null ) { return false; }
		else if ( !serial_number.equals(other.serial_number) ) { return false; }
		if ( datapath_description == null && other.datapath_description != null ) { return false; }
		else if ( !datapath_description.equals(other.datapath_description) ) { return false; }
        return true;
    }
}
