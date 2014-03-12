package etri.sdn.controller.module.topologymanager;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.projectfloodlight.openflow.types.OFPort;

import etri.sdn.controller.IService;
import etri.sdn.controller.module.linkdiscovery.ILinkDiscoveryListener.LDUpdate;
import etri.sdn.controller.module.linkdiscovery.NodePortTuple;

/**
 * This interface is used to define a service that 
 * {@link OFMTopologyManager} should implement. 
 * 
 * @author SaeHyong Park (labry@etri.re.kr)
 *
 */
public interface ITopologyService extends IService {

    public void addListener(ITopologyListener listener);

    public Date getLastUpdateTime();

    /**
     * Query to determine if devices must be learned on a given switch port.
     */
    public boolean isAttachmentPointPort(long switchid, OFPort port);
    public boolean isAttachmentPointPort(long switchid, OFPort port,
                                         boolean tunnelEnabled);

    public long getOpenflowDomainId(long switchId);
    public long getOpenflowDomainId(long switchId, boolean tunnelEnabled);

    /**
     * Returns the identifier of the L2 domain of a given switch.
     * @param switchId The DPID of the switch in long form
     * @return The DPID of the switch that is the key for the cluster
     */
    public long getL2DomainId(long switchId);
    public long getL2DomainId(long switchId, boolean tunnelEnabled);

    /**
     * Queries whether two switches are in the same cluster.
     * @param switch1
     * @param switch2
     * @return true if the switches are in the same cluster
     */
    public boolean inSameOpenflowDomain(long switch1, long switch2);
    public boolean inSameOpenflowDomain(long switch1, long switch2, 
                                        boolean tunnelEnabled);

    /**
     * Queries whether two switches are in the same island.
     * Currently, island and cluster are the same. In future,
     * islands could be different than clusters.
     * @param switch1
     * @param switch2
     * @return True of they are in the same island, false otherwise
     */
    public boolean inSameL2Domain(long switch1, long switch2);
    public boolean inSameL2Domain(long switch1, long switch2, 
                                  boolean tunnelEnabled);

    public boolean isBroadcastDomainPort(long sw, OFPort port);
    public boolean isBroadcastDomainPort(long sw, OFPort port, 
                                         boolean tunnelEnabled);


    public boolean isAllowed(long sw, OFPort portId);
    public boolean isAllowed(long sw, OFPort portId, boolean tunnelEnabled);

    /**
     * Indicates if an attachment point on the new switch port is consistent
     * with the attachment point on the old switch port or not.
     */
    public boolean isConsistent(long oldSw, OFPort oldPort,
                                long newSw, OFPort newPort);
    public boolean isConsistent(long oldSw, OFPort oldPort,
                                long newSw, OFPort newPort,
                                boolean tunnelEnabled);

    /**
     * Indicates if the two switch ports are connected to the same
     * broadcast domain or not.
     * @param s1
     * @param p1
     * @param s2
     * @param p2
     * @return	true if two switch port is in the same broadcast domain, false otherwise
     */
    public boolean isInSameBroadcastDomain(long s1, OFPort p1, 
                                           long s2, OFPort p2);
    public boolean isInSameBroadcastDomain(long s1, OFPort p1,
                                           long s2, OFPort p2,
                                           boolean tunnelEnabled);

    /**
     * Gets a list of ports on a given switch that are known to topology.
     * @param sw The switch DPID in long
     * @return The set of ports on this switch
     */
    public Set<OFPort> getPortsWithLinks(long sw);
    public Set<OFPort> getPortsWithLinks(long sw, boolean tunnelEnabled);

    /** Get broadcast ports on a target switch for a given attachmentpoint
     * point port.
     */
    public Set<OFPort> getBroadcastPorts(long targetSw, long src, OFPort srcPort);

    public Set<OFPort> getBroadcastPorts(long targetSw, long src, OFPort srcPort,
                                        boolean tunnelEnabled);

    /**
     * 
     */
    public boolean isIncomingBroadcastAllowed(long sw, OFPort portId);
    public boolean isIncomingBroadcastAllowed(long sw, OFPort portId,
                                              boolean tunnelEnabled);


    /** Get the proper outgoing switchport for a given pair of src-dst
     * switchports.
     */
    public NodePortTuple getOutgoingSwitchPort(long src, OFPort srcPort,
                                               long dst, OFPort dstPort);


    public NodePortTuple getOutgoingSwitchPort(long src, OFPort srcPort,
                                               long dst, OFPort dstPort,
                                               boolean tunnelEnabled);


    public NodePortTuple getIncomingSwitchPort(long src, OFPort srcPort,
                                               long dst, OFPort dstPort);
    public NodePortTuple getIncomingSwitchPort(long src, OFPort srcPort,
                                               long dst, OFPort dstPort,
                                               boolean tunnelEnabled);

    /**
     * If the dst is not allowed by the higher-level topology,
     * this method provides the topologically equivalent broadcast port.
     * @param src
     * @param dst
     * @return the allowed broadcast port
     */
    public NodePortTuple 
    getAllowedOutgoingBroadcastPort(long src,
    									 OFPort srcPort,
                                    long dst,
                                    OFPort dstPort);

    public NodePortTuple 
    getAllowedOutgoingBroadcastPort(long src,
                                    OFPort srcPort,
                                    long dst,
                                    OFPort dstPort,
                                    boolean tunnelEnabled);

    /**
     * If the src broadcast domain port is not allowed for incoming
     * broadcast, this method provides the topologically equivalent
     * incoming broadcast-allowed src port.  
     * @param src
     * @param srcPort
     * @return the allowed broadcast port
     */
    public NodePortTuple
    getAllowedIncomingBroadcastPort(long src,
                                    OFPort srcPort);

    public NodePortTuple
    getAllowedIncomingBroadcastPort(long src,
                                    OFPort srcPort,
                                    boolean tunnelEnabled);


    /**
     * Gets the set of ports that belong to a broadcast domain.
     * @return The set of ports that belong to a broadcast domain.
     */
    public Set<NodePortTuple> getBroadcastDomainPorts();
    public Set<NodePortTuple> getTunnelPorts();


    /**
     * Returns a set of blocked ports.  The set of blocked
     * ports is the union of all the blocked ports across all
     * instances.
     * @return	Set<NodePortTuple>
     */
    public Set<NodePortTuple> getBlockedPorts();

    /**
     * ITopologyListener provides topologyChanged notification, 
     * but not *what* the changes were.  
     * This method returns the delta in the linkUpdates between the current and the previous topology instance.
     * @return List<LDUpdate>
     */
    public List<LDUpdate> getLastLinkUpdates();

    /**
     * Switch methods
     */
    public Set<OFPort> getPorts(long sw);
}
