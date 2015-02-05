package etri.sdn.controller.module.ml2;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class VirtualSubnetSerializer extends JsonSerializer<VirtualSubnet> {

	@Override
	public void serialize(VirtualSubnet vSub, JsonGenerator jGen, SerializerProvider serializer) throws IOException {
		jGen.writeStartObject();
		jGen.writeStringField("name", vSub.subName);
		jGen.writeStringField("enable_dhcp", vSub.enable_dhcp);
		jGen.writeStringField("network_id", vSub.network_id);
		jGen.writeStringField("tenant_id", vSub.tenant_id);
		
		jGen.writeArrayFieldStart("dns_nameservers");
			for (String dns_nameserver : vSub.dns_nameservers) {
				jGen.writeString(dns_nameserver);
			}
		jGen.writeEndArray();
		
		jGen.writeArrayFieldStart("allocation_pools");
			for (Map<String, String> apMap : vSub.allocation_pools) {
				jGen.writeStartObject();
				for (Entry<String, String> entry : apMap.entrySet()) {
					jGen.writeStringField(entry.getKey().toLowerCase(), entry.getValue());
				}
				jGen.writeEndObject();
			}
		jGen.writeEndArray();
		
		jGen.writeArrayFieldStart("host_routes");
			for (String host_route : vSub.host_routes) {
				jGen.writeString(host_route);
			}
		jGen.writeEndArray();
		
		jGen.writeStringField("ip_version", vSub.ip_version);
		jGen.writeStringField("gateway_ip", vSub.gateway_ip);
		jGen.writeStringField("cidr", vSub.cidr);
		jGen.writeStringField("id", vSub.subId);
		jGen.writeStringField("ipv6_ra_mode", vSub.ipv6_ra_mode);
		jGen.writeStringField("ipv6_address_mode", vSub.ipv6_address_mode);
		jGen.writeStringField("shared", vSub.shared);
		jGen.writeEndObject();
	}
}
