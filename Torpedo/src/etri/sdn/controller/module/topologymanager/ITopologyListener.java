package etri.sdn.controller.module.topologymanager;

/**
 * The user classes of ITopologyService should implement this interface.
 * 
 */
public interface ITopologyListener {
    /**
     * Happens when the switch clusters are recomputed
     */
    void topologyChanged();
}
