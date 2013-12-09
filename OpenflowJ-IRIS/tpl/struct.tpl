package $packagename;

import java.nio.ByteBuffer;
import org.openflow.util.*;

$imports

public class $typename $inherit_method $supertype $implements {
    public static int MINIMUM_LENGTH = $minimumlength;

    $declarations

    public $typename() {
        $constructor
    }
    
    public $typename($typename other) {
    	$copyconstructor
    }

$accessors

    public void readFrom(ByteBuffer data) {
        $readfrom
    }

    public void writeTo(ByteBuffer data) {
    	$superwriteto
        $writeto
    }

    public String toString() {
        $tostring
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	$computelength
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(short req) {
    	if (req == 0) return 0;
    	short l = (short)(computeLength() % req);
    	if ( l == 0 ) { return 0; }
    	return (short)( req - l );
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	return (short)(computeLength() - (short)MINIMUM_LENGTH + alignment((short)$align));
    }

    @Override
    public int hashCode() {
        $hashcode
    }

    @Override
    public boolean equals(Object obj) {
        $equals
    }
}
