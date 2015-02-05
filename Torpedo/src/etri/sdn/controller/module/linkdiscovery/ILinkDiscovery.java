package etri.sdn.controller.module.linkdiscovery;


/**
 * This interface is actually not an interface, 
 * because there's no class that implements this interface. 
 * The role of this interface is to provide a place holder 
 * that various types related to the link discovery module 
 * can be defined. 
 * 
 * @author bjlee
 */
public interface ILinkDiscovery {

	/**
	 * This enum differentiate the switch type, but never used. 
	 * So soon will be removed from the source tree.
	 * 
	 * @author bjlee
	 * @deprecated
	 */
    public enum SwitchType {
        BASIC_SWITCH, CORE_SWITCH
    }

    /**
     * This enum defines the type of each link. 
     * The string representation is also provided. 
     * 
     * @author bjlee
     *
     */
    public enum LinkType {
        INVALID_LINK {
        	@Override
        	public String toString() {
        		return "invalid";
        	}
        }, 
        DIRECT_LINK{
        	@Override
        	public String toString() {
        		return "internal";
        	}
        }, 
        MULTIHOP_LINK {
        	@Override
        	public String toString() {
        		return "external";
        	}
        }, 
        TUNNEL {
        	@Override
        	public String toString() {
        		return "tunnel";
        	}
        }
    }
}
