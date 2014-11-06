package etri.sdn.controller.module.ml2;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;

public class RestNetwork extends Restlet {

	static String neutronNetAll = "/wm/ml2/networks";
	static String neutronNet = "/wm/ml2/networks/{netUUID}";
	static String neutronNetIRIS = "/wm/ml2/networks/{netKey}/{netValue}";

	private NetworkConfiguration parent = null;

	RestNetwork(NetworkConfiguration parent) {
		this.parent = parent;
	}

	NetworkConfiguration getModel() {
		return this.parent;
	}

	public class NetworkDefinition {
		public String netName = null;
		public String netId = null;
		public String admin_state_up = null;
		public String status = null;
		public String shared = null;
		public String tenant_id = null;
		public String router_external = null;
		public String provider_network_type = null;
		public String provider_physical_network = null;
		public String provider_segmentation_id = null;
	}

	protected void jsonToNetworkDefinition(String json, NetworkDefinition network) throws IOException {
		// convert json to map
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> nframe = om.readValue(json, new TypeReference<Map<String, Object>>(){});

		ObjectMapper omm = new ObjectMapper();
		String nInfoJson = omm.writeValueAsString(nframe.get("network"));
		Map<String, Object> nInfo = omm.readValue(nInfoJson, new TypeReference<Map<String, Object>>(){});

		if(nInfo.get("name") != null) {
			network.netName = nInfo.get("name").toString();
		}
		if(nInfo.get("provider:physical_network") != null) {
			network.provider_physical_network = nInfo.get("provider:physical_network").toString();
		}
		if(nInfo.get("admin_state_up") != null) {
			network.admin_state_up = nInfo.get("admin_state_up").toString();
		}
		if(nInfo.get("tenant_id") != null) {
			network.tenant_id = nInfo.get("tenant_id").toString();
		}
		if(nInfo.get("provider:network_type") != null) {
			network.provider_network_type = nInfo.get("provider:network_type").toString();
		}
		if(nInfo.get("router:external") != null) {
			network.router_external = nInfo.get("router:external").toString();
		}
		if(nInfo.get("shared") != null) {
			network.shared = nInfo.get("shared").toString();
		}
		if(nInfo.get("id") != null) {
			network.netId = nInfo.get("id").toString();
		}
		if(nInfo.get("provider:segmentation_id") != null) {
			network.provider_segmentation_id = nInfo.get("provider:segmentation_id").toString();
		}
	}

	@Override
	public void handle(Request request, Response response) {

		Method m = request.getMethod();
		String netUUID = request.getAttributes().get("netUUID") == null ? "" : (String) request.getAttributes().get("netUUID");

		if (m == Method.POST || m == Method.PUT) {
			NetworkDefinition network = new NetworkDefinition();

			try {
				jsonToNetworkDefinition(request.getEntityAsText(), network);
			} catch (IOException e) {
				OFMOpenstackML2Connector.logger.error("RestNetwork Could not parse JSON {}", e.getMessage());
			}

			// We try to get the ID from the URI only if it's not
			// in the POST data
			if (network.netId == null) {
				if(!"".equals(netUUID)) {
					network.netId = netUUID;
				}
			}

			parent.getModule().createNetwork(network);
			response.setStatus(Status.SUCCESS_OK);

		} else if (m == Method.GET) {
			
			String netKey = request.getAttributes().get("netKey") == null ? "" : (String) request.getAttributes().get("netKey");
			String netValue = request.getAttributes().get("netValue") == null ? "" : (String) request.getAttributes().get("netValue");

			response.setEntity(parent.getModule().listNetworks(netUUID, netKey, netValue), MediaType.APPLICATION_JSON);
			response.setStatus(Status.SUCCESS_OK);

		} else if (m == Method.DELETE) {

			parent.getModule().deleteNetwork(netUUID);
			response.setStatus(Status.SUCCESS_OK);

		}
	}
}
