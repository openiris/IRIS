package $packagename;

import java.nio.ByteBuffer;

public class $typename {
	private $rep value;
	
	$body
	
	public $typename() {
		// does nothing
	}
	
	public void setValue($rep value) {
		this.value = value;
	}
	
	public $rep getValue() {
		return this.value;
	}
	
	public static $rep readFrom(ByteBuffer data) {
		return data.get$methodname();
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
}