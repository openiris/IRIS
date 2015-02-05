package etri.sdn.controller.module.ml2;

import java.io.IOException;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class VirtualNetworkSerializer extends JsonSerializer<VirtualNetwork> {

	@Override
	public void serialize(VirtualNetwork vNet, JsonGenerator jGen, SerializerProvider serializer) throws IOException {
		jGen.writeStartObject();
		jGen.writeStringField("status", vNet.status);
		
		jGen.writeArrayFieldStart("subnets");
			for (Entry<String, String> entry : vNet.subNameToGuid.entrySet()) {
				jGen.writeString(entry.getKey());
			}
		jGen.writeEndArray();
		
		jGen.writeStringField("name", vNet.netName);
		jGen.writeStringField("provider:physical_network", vNet.provider_physical_network);
		jGen.writeStringField("admin_state_up", vNet.admin_state_up);
		jGen.writeStringField("tenant_id", vNet.tenant_id);
		jGen.writeStringField("provider:network_type", vNet.provider_network_type);
		jGen.writeStringField("router:external", vNet.router_external);
		jGen.writeStringField("shared", vNet.shared);
		jGen.writeStringField("id", vNet.netId);
		jGen.writeStringField("provider:segmentation_id", vNet.provider_segmentation_id);
		jGen.writeEndObject();
	}
}
