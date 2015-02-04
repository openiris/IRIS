package etri.sdn.controller.module.ml2;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import etri.sdn.controller.module.ml2.RestPort.PortDefinition;


@JsonSerialize(using=VirtualPortSerializer.class)
public class VirtualPort {
	
	protected String binding_host_id;						// port binding_host_id
	protected List<String> allowed_address_pairs;			// port allowed_address_pairs
	protected List<String> extra_dhcp_opts;					// port extra_dhcp_opts
	protected String device_owner;							// port device_owner
	protected Map<String, String> binding_profile;			// port binding_profile
	protected List<Map<String, String>> fixed_ips;			// port fixed_ips
	protected String porId;									// port id
	protected List<Map<String, Object>> security_groups;	// port security_groups
	protected String device_id;								// port device_id
	protected String porName;								// port name
	protected String admin_state_up;						// port admin_state_up
	protected String network_id;							// port tenant_id
	protected String tenant_id;								// port binding_vif_details
	protected Map<String, String> binding_vif_details;		// port binding_vif_details
	protected String binding_vif_detail;
	protected String binding_vnic_type;						// port binding_vnic_type
	protected String binding_vif_type;						// port binding_vif_type
	protected String mac_address;							// port mac_address

	/**
	* Constructor requires network info
	* @param port : NetworkDefinition - network info
	*/
	public VirtualPort(PortDefinition port) {
		this.binding_host_id = port.binding_host_id;
		this.allowed_address_pairs = port.allowed_address_pairs;
		this.extra_dhcp_opts = port.extra_dhcp_opts;
		this.device_owner = port.device_owner;
		this.binding_profile = port.binding_profile;
		this.fixed_ips = port.fixed_ips;
		this.porId = port.porId;
		this.security_groups = port.security_groups;
		this.device_id = port.device_id;
		this.porName = port.porName;
		this.admin_state_up = port.admin_state_up;
		this.network_id=port.network_id;
		this.tenant_id = port.tenant_id;
		this.binding_vif_details = port.binding_vif_details;
		this.binding_vif_detail = port.binding_vif_detail;
		this.binding_vnic_type = port.binding_vnic_type;
		this.binding_vif_type = port.binding_vif_type;
		this.mac_address = port.mac_address;
		
		return;
	}

	/**
	* Sets port binding_host_id
	* @param binding_host_id : port binding_host_id
	*/
	public void setBindingHostId(String binding_host_id) {
		this.binding_host_id = binding_host_id;
		return;
	}

	/**
	* Sets port allowed_address_pairs
	* @param allowed_address_pairs : port allowed_address_pairs
	*/
	public void setAllowedAddressPairs(List<String> allowed_address_pairs) {
		this.allowed_address_pairs = allowed_address_pairs;
		return;
	}

	/**
	* Sets port extra_dhcp_opts
	* @param extra_dhcp_opts : port extra_dhcp_opts
	*/
	public void setExtraDhcpOpts(List<String> extra_dhcp_opts) {
		this.extra_dhcp_opts = extra_dhcp_opts;
		return;
	}

	/**
	* Sets port device_owner
	* @param device_owner : port device_owner
	*/
	public void setDeviceOwner(String device_owner) {
		this.device_owner = device_owner;
		return;
	}

	/**
	* Sets port binding_profile
	* @param binding_profile : port binding_profile
	*/
	public void setBindingProfile(Map<String, String> binding_profile) {
		this.binding_profile = binding_profile;
		return;
	}

	/**
	* Sets port security_groups
	* @param security_groups : port security_groups
	*/
	public void setSecurityGroups(List<Map<String, Object>> security_groups) {
		this.security_groups = security_groups;
		return;
	}

	/**
	* Sets port device_id
	* @param device_id : port device_id
	*/
	public void setDeviceId(String device_id) {
		this.device_id = device_id;
		return;
	}

	/**
	* Sets port porName
	* @param porName : port porName
	*/
	public void setPorName(String porName) {
		this.porName = porName;
		return;
	}

	/**
	* Sets port admin_state_up
	* @param admin_state_up : port admin_state_up
	*/
	public void setAdminStateUp(String admin_state_up) {
		this.admin_state_up = admin_state_up;
		return;
	}

	/**
	* Sets port binding_vif_details
	* @param binding_vif_details : port binding_vif_details
	*/
	public void setBindingVifDetails(Map<String, String> binding_vif_details) {
		this.binding_vif_details = binding_vif_details;
		return;
	}
	
	/**
	* Sets port binding_vif_details
	* @param binding_vif_detail : port binding_vif_details
	*/
	public void setBindingVifDetails(String binding_vif_detail) {
		this.binding_vif_detail = binding_vif_detail;
		return;
	}

	/**
	* Sets port binding_vnic_type
	* @param binding_vnic_type : port binding_vnic_type
	*/
	public void setBindingVnicType(String binding_vnic_type) {
		this.binding_vnic_type = binding_vnic_type;
		return;
	}

	/**
	* Sets port binding_vif_type
	* @param binding_vif_type : port binding_vif_type
	*/
	public void setBindingVifType(String binding_vif_type) {
		this.binding_vif_type = binding_vif_type;
		return;
	}

}
