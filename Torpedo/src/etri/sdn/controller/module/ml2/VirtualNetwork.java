package etri.sdn.controller.module.ml2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import etri.sdn.controller.module.ml2.RestNetwork.NetworkDefinition;


@JsonSerialize(using=VirtualNetworkSerializer.class)
public class VirtualNetwork {

	protected String status;					// network status
	protected String netName;					// network name
	protected String provider_physical_network;	// network provider_physical_network
	protected String admin_state_up;			// network admin_state_up
	protected String tenant_id;					// network tenant_id
	protected String provider_network_type;		// network provider_network_type
	protected String router_external;			// network router_external
	protected String shared;					// network shared
	protected String netId;						// network id
	protected String provider_segmentation_id;	// network provider_segmentation_id
	protected Map<String, String> subNameToGuid;

	/**
	* Constructor requires network info
	* @param network : NetworkDefinition - network info
	*/
	public VirtualNetwork(NetworkDefinition network) {
		this.status = network.status;
		this.subNameToGuid = new ConcurrentHashMap<String, String>();
		this.netName = network.netName;
		this.provider_physical_network = network.provider_physical_network;
		this.admin_state_up = network.admin_state_up;
		this.tenant_id = network.tenant_id;
		this.provider_network_type = network.provider_network_type;
		this.router_external = network.router_external;
		this.shared = network.shared;
		this.netId = network.netId;
		this.provider_segmentation_id = network.provider_segmentation_id;
	}

	/**
	* Sets network name
	* @param name : network name
	*/
	public void setNetName(String netName){
		this.netName = netName;
	}
	
	/**
	* Sets network provider_physical_network
	* @param provider_physical_network : network provider_physical_network
	*/
	public void setProviderPhysicalNetwork(String provider_physical_network){
		this.provider_physical_network = provider_physical_network;
	}
	
	/**
	* Sets network admin_state_up
	* @param admin_state_up : network admin_state_up
	*/
	public void setAdminStateUp(String admin_state_up){
		this.admin_state_up = admin_state_up;
	}
	
	/**
	* Sets network provider_network_type
	* @param provider_network_type : network provider_network_type
	*/
	public void setProviderNetworkType(String provider_network_type){
		this.provider_network_type = provider_network_type;
	}
	
	/**
	* Sets network router_external
	* @param router_external : network router_external
	*/
	public void setRouterExternal(String router_external){
		this.router_external = router_external;
	}
	
	/**
	* Sets network shared
	* @param shared : network shared
	*/
	public void setShared(String shared){
		this.shared = shared;
	}
	
	/**
	* Sets network provider_segmentation_id
	* @param provider_segmentation_id : network provider_segmentation_id
	*/
	public void setProviderSegmentationId(String provider_segmentation_id){
		this.provider_segmentation_id = provider_segmentation_id;
	}
	
	public void addSubnets(String subId, String subName) {
		this.subNameToGuid.put(subId, subName);
	}
	
	public void delSubnets(String subId) {
		this.subNameToGuid.remove(subId);
	}

}
