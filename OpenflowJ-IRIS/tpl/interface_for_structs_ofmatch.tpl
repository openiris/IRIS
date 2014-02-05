package org.openflow.protocol.interfaces;

import java.nio.ByteBuffer;
import org.openflow.protocol.interfaces.OFOxmMatchFields;

$imports

public interface $typename $inherit {

	public interface Builder {
	
		public Builder setValue(OFOxmMatchFields match_field, byte mask, byte[] data);
		public boolean isSetValueSupported();
		
		$builder_accessors
		
		public OFMatch build();
	}
	
	$accessors
	
	public $typename dup();

    public void readFrom(ByteBuffer data);

    public void writeTo(ByteBuffer data);

    public short computeLength();

    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req);
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff();
}
