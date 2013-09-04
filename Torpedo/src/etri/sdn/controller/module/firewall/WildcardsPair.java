package etri.sdn.controller.module.firewall;

import org.openflow.protocol.OFMatch;

/**
 * A pair of wildcards which are assigned later to the firewall's decision.
 *   
 */
public class WildcardsPair {
    public int allow = OFMatch.OFPFW_ALL;
    public int drop = OFMatch.OFPFW_ALL;
}
