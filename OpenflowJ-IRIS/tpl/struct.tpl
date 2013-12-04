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
    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
    	$computelength
    	return len;
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
