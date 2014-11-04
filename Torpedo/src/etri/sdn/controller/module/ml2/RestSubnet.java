package etri.sdn.controller.module.ml2;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;

import etri.sdn.controller.util.Logger;

public class RestSubnet extends Restlet {

	static String neutronSubnetAll = "/wm/ml2/subnets";
	static String neutronSubnet = "/wm/ml2/subnets/{subUUID}";
	static String neutronSubnetIRIS = "/wm/ml2/subnets/{subKey}/{subValue}";
	
	private NetworkConfiguration parent = null;
	
	RestSubnet(NetworkConfiguration parent) {
		this.parent = parent;
	}
	
	NetworkConfiguration getModel() {
		return this.parent;
	}
	
	public class SubnetDefinition {
		public String subName = null;
		public String enable_dhcp = null;
		public String network_id = null;
		public String tenant_id = null;
		public String ip_version = null;
		public String gateway_ip = null;
		public String cidr = null;
		public String subId = null;
		public String ipv6_ra_mode = null;
		public String ipv6_address_mode = null;
		public String shared = null;
		
		public List<String> dns_nameservers = null;
		public List<Map<String, String>> allocation_pools = null;
		public List<String> host_routes = null;
	}
	
	protected void jsonToSubnetDefinition(String json, SubnetDefinition subnet) throws IOException {
		// convert json to map
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> sframe = om.readValue(json, new TypeReference<Map<String, Object>>(){});
        
        ObjectMapper omm = new ObjectMapper();
        String sInfoJson = omm.writeValueAsString(sframe.get("subnet"));
        Map<String, Object> sInfo = omm.readValue(sInfoJson, new TypeReference<Map<String, Object>>(){});

        if(sInfo.get("name") != null) {
        	subnet.subName = sInfo.get("name").toString();
        }
        if(sInfo.get("enable_dhcp") != null) {
        	subnet.enable_dhcp = sInfo.get("enable_dhcp").toString();
        }
        if(sInfo.get("network_id") != null) {
        	subnet.network_id = sInfo.get("network_id").toString();
        }
        if(sInfo.get("tenant_id") != null) {
        	subnet.tenant_id = sInfo.get("tenant_id").toString();
        }
        if(sInfo.get("ip_version") != null) {
        	subnet.ip_version = sInfo.get("ip_version").toString();
        }
        if(sInfo.get("gateway_ip") != null) {
        	subnet.gateway_ip = sInfo.get("gateway_ip").toString();
        }
        if(sInfo.get("cidr") != null) {
        	subnet.cidr = sInfo.get("cidr").toString();
        }
        if(sInfo.get("id") != null) {
        	subnet.subId = sInfo.get("id").toString();
        }
        if(sInfo.get("ipv6_ra_mode") != null) {
        	subnet.ipv6_ra_mode = sInfo.get("ipv6_ra_mode").toString();
        }
        if(sInfo.get("ipv6_address_mode") != null) {
        	subnet.ipv6_address_mode = sInfo.get("ipv6_address_mode").toString();
        }
        if(sInfo.get("shared") != null) {
        	subnet.shared = sInfo.get("shared").toString();
        }
        if(sInfo.get("dns_nameservers") != null) {
        	ObjectMapper omdn = new ObjectMapper();
        	subnet.dns_nameservers = omdn.readValue(omdn.writeValueAsString(sInfo.get("dns_nameservers")), new TypeReference<List<String>>(){});
        }
        if(sInfo.get("allocation_pools") != null) {
        	ObjectMapper omap = new ObjectMapper();
        	subnet.allocation_pools = omap.readValue(omap.writeValueAsString(sInfo.get("allocation_pools")), new TypeReference<List<Map<String, String>>>(){});
        }
        if(sInfo.get("host_routes") != null) {
        	ObjectMapper omhr = new ObjectMapper();
        	subnet.host_routes = omhr.readValue(omhr.writeValueAsString(sInfo.get("host_routes")), new TypeReference<List<String>>(){});
        }
	}
	
	@Override
	public void handle(Request request, Response response) {
		
		Method m = request.getMethod();
		String subUUID = request.getAttributes().get("subUUID") == null ? "" : (String) request.getAttributes().get("subUUID");
		
		if (m == Method.POST || m == Method.PUT) {
			SubnetDefinition subnet = new SubnetDefinition();
			
			try {
				jsonToSubnetDefinition(request.getEntityAsText(), subnet);
			} catch (IOException e) {
				Logger.error("RestSubnet Could not parse JSON {}", e.getMessage());
			}
			
			// We try to get the ID from the URI only if it's not
	        // in the POST data
	        if (subnet.subId == null) {
	        	if(!"".equals(subUUID)) {
	        		subnet.subId = subUUID;
		        }
	        }

	        parent.getModule().createSubnet(subnet);
	        response.setStatus(Status.SUCCESS_OK);
			
		} else if (m == Method.GET) {
			
			String subKey = request.getAttributes().get("subKey") == null ? "" : (String) request.getAttributes().get("subKey");
			String subValue = request.getAttributes().get("subValue") == null ? "" : (String) request.getAttributes().get("subValue");
			
			response.setEntity(parent.getModule().listSubnets(subUUID, subKey, subValue), MediaType.APPLICATION_JSON);
			response.setStatus(Status.SUCCESS_OK);
			
		} else if (m == Method.DELETE) {
			
			parent.getModule().deleteSubnet(subUUID);
			response.setStatus(Status.SUCCESS_OK);
			
		}
	}

}
