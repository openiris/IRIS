package etri.sdn.controller.module.staticentrymanager;

/**
 * This class is the general class of various exceptions produced 
 * by static flow entry.
 *
 * @author bjlee
 */
public class StaticFlowEntryException extends Exception {

	private static final long serialVersionUID = 7364701481591130398L;

	private String reason;

	public StaticFlowEntryException(String cause) {
		this.reason = cause;
	}

	public String getReason() {
		return this.toString();
	}

	public String toString() {
		return "[StaticFlowEntryException] " + this.reason;
	}
}
