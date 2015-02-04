package etri.sdn.controller.module.ml2;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import etri.sdn.controller.module.ml2.RestSubnet.SubnetDefinition;


@JsonSerialize(using=VirtualSubnetSerializer.class)
public class VirtualSubnet {
	
	protected String subName;								// subnet name
	protected String enable_dhcp;							// subnet enable_dhcp
	protected String network_id;							// subnet network_id
	protected String tenant_id;								// subnet tenant_id
	protected String ip_version;							// subnet ip_version
	protected String gateway_ip;							// subnet gateway_ip
	protected String cidr;									// subnet cidr
	protected String subId;									// subnet id
	protected String ipv6_ra_mode;							// subnet ipv6_ra_mode
	protected String ipv6_address_mode;						// subnet ipv6_address_mode
	protected String shared;								// subnet shared
	protected List<String> dns_nameservers;					// subnet ubnet shared
	protected List<Map<String, String>> allocation_pools;	// subnet allocation_pools
	protected List<String> host_routes;						// subnet host_routes

	/**
	* Constructor requires network info
	* @param subnet : NetworkDefinition - network info
	*/
	public VirtualSubnet(SubnetDefinition subnet) {
		this.subName = subnet.subName;
		this.enable_dhcp = subnet.enable_dhcp;
		this.network_id = subnet.network_id;
		this.tenant_id = subnet.tenant_id;
		this.dns_nameservers = subnet.dns_nameservers;
		this.allocation_pools = subnet.allocation_pools;
		this.host_routes = subnet.host_routes;
		this.ip_version = subnet.ip_version;
		this.gateway_ip = subnet.gateway_ip;
		this.cidr = subnet.cidr;
		this.subId = subnet.subId;
		this.ipv6_ra_mode = subnet.ipv6_ra_mode;
		this.ipv6_address_mode = subnet.ipv6_address_mode;
		this.shared = subnet.shared;
		
		return;
	}

	/**
	* Sets subnet name
	* @param subName : subnet name
	*/
	public void setSubName(String subName){
		this.subName = subName;
		return;
	}
	
	/**
	* Sets subnet enable_dhcp
	* @param enable_dhcp : subnet enable_dhcp
	*/
	public void setEnableDhcp(String enable_dhcp){
		this.enable_dhcp = enable_dhcp;
		return;
	}
	
	/**
	* Sets subnet gateway_ip
	* @param gateway_ip : subnet gateway_ip
	*/
	public void setGatewayIp(String gateway_ip){
		this.gateway_ip = gateway_ip;
		return;
	}
	
	/**
	* Sets subnet shared
	* @param shared : subnet shared
	*/
	public void setShared(String shared){
		this.shared = shared;
		return;
	}
	
	/**
	* Sets subnet dns_nameservers
	* @param dns_nameservers : subnet dns_nameservers
	*/
	public void setDnsNameservers(List<String> dns_nameservers){
		this.dns_nameservers = dns_nameservers;
		return;
	}
	
	/**
	* Sets subnet host_routes
	* @param host_routes : subnet host_routes
	*/
	public void setHostRoutes(List<String> host_routes){
		this.host_routes = host_routes;
		return;
	}
	
	/**
	* Sets subnet ipv6_ra_mode
	* @param ipv6_ra_mode : subnet ipv6_ra_mode
	*/
	public void setIpv6RaMode(String ipv6_ra_mode){
		this.ipv6_ra_mode = ipv6_ra_mode;
		return;
	}
	
	/**
	* Sets subnet ipv6_address_mode
	* @param ipv6_address_mode : subnet ipv6_address_mode
	*/
	public void setIpv6AddressMode(String ipv6_address_mode){
		this.ipv6_address_mode = ipv6_address_mode;
		return;
	}

}
