package etri.sdn.controller.module.ml2;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class VirtualPortSerializer extends JsonSerializer<VirtualPort> {

	// REST GET
	@Override
	public void serialize(VirtualPort vPor, JsonGenerator jGen, SerializerProvider serializer) throws IOException, JsonProcessingException {
		
		jGen.writeStartObject();
		jGen.writeStringField("binding:host_id", vPor.binding_host_id);
		jGen.writeArrayFieldStart("allowed_address_pairs");
			for (String allowed_address_pair : vPor.allowed_address_pairs) {
				jGen.writeString(allowed_address_pair);
			}
		jGen.writeEndArray();
		jGen.writeArrayFieldStart("extra_dhcp_opts");
			for (String extra_dhcp_opt : vPor.extra_dhcp_opts) {
				jGen.writeString(extra_dhcp_opt);
			}
		jGen.writeEndArray();
		jGen.writeStringField("device_owner", vPor.device_owner);
		jGen.writeObjectFieldStart("binding_profile");
			if(vPor.binding_profile != null) {
				for (Entry<String, String> entry : vPor.binding_profile.entrySet()) {
					jGen.writeStringField(entry.getKey().toLowerCase(), entry.getValue());
				}
			}
		jGen.writeEndObject();
		jGen.writeArrayFieldStart("fixed_ips");
			for (Map<String, String> fiMap : vPor.fixed_ips) {
				jGen.writeStartObject();
				for (Entry<String, String> entry : fiMap.entrySet()) {
					jGen.writeStringField(entry.getKey().toLowerCase(), entry.getValue());
				}
				jGen.writeEndObject();
			}
		jGen.writeEndArray();
		jGen.writeStringField("id", vPor.porId);
		jGen.writeArrayFieldStart("security_groups");
			for (Map<String, Object> sgMap : vPor.security_groups) {
				for (Entry<String, Object> entry : sgMap.entrySet()) {
					if("id".equals(entry.getKey().toLowerCase())) {
						jGen.writeString(entry.getValue().toString());
					}
				}
			}
		jGen.writeEndArray();
		jGen.writeStringField("device_id", vPor.device_id);
		jGen.writeStringField("name", vPor.porName);
		jGen.writeStringField("admin_state_up", vPor.admin_state_up);
		jGen.writeStringField("network_id", vPor.network_id);
		jGen.writeStringField("tenant_id", vPor.tenant_id);
		if(vPor.binding_vif_detail != null) {
			jGen.writeStringField("binding_vif_details", vPor.binding_vif_detail);
		} else {
			jGen.writeObjectFieldStart("binding_vif_details");
			if(vPor.binding_vif_details != null) {
				for (Entry<String, String> entry : vPor.binding_vif_details.entrySet()) {
					jGen.writeStringField(entry.getKey().toLowerCase(), entry.getValue());
				}
			}
		jGen.writeEndObject(); }
//		jGen.writeStringField("binding_vif_detail", vPor.binding_vif_detail); // (String FieldName(REST KEY), String Value(REST Value))
		jGen.writeStringField("binding_vnic_type", vPor.binding_vnic_type);
		jGen.writeStringField("binding_vif_type", vPor.binding_vif_type);
		jGen.writeStringField("mac_address", vPor.mac_address);
		jGen.writeEndObject();
		
	}
}
