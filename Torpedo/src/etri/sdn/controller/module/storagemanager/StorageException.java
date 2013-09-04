package etri.sdn.controller.module.storagemanager;


/**
 * Signals that an storage related exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * interrupted storage operations.
 *
 * @author SaeHyong Park (labry@etri.re.kr)
 */
public class StorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7614394059888335245L;

	public StorageException() {
		
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
