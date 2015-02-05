package etri.sdn.controller.module.firewall;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.types.IPv4Address;

/**
 * This class is the serializer of {@link FirewallRule}.
 * 
 * @author jshin
 */
final class OFFirewallRuleSerializer extends JsonSerializer<FirewallRule> {

	@Override
	public void serialize(FirewallRule rule, JsonGenerator jgen, SerializerProvider provider)
	throws IOException {
		
		jgen.writeStartObject();
		jgen.writeNumberField("ruleid", rule.ruleid);
		jgen.writeNumberField("dpid", rule.dpid);
		jgen.writeNumberField("in_port", rule.in_port);
		jgen.writeNumberField("dl_src", rule.dl_src);
		jgen.writeNumberField("dl_dst", rule.dl_dst);
		jgen.writeStringField("dl_type", "0x" + Integer.toHexString(0x0000ffff & rule.dl_type));
		jgen.writeStringField("nw_src_prefix", IPv4Address.of(rule.nw_src_prefix).toString());
		jgen.writeNumberField("nw_src_maskbits", rule.nw_src_maskbits);
		jgen.writeStringField("nw_dst_prefix", IPv4Address.of(rule.nw_dst_prefix).toString());
		jgen.writeNumberField("nw_dst_maskbits", rule.nw_dst_maskbits);
		jgen.writeNumberField("nw_proto", rule.nw_proto);
		jgen.writeNumberField("tp_src", rule.tp_src);
		jgen.writeNumberField("tp_dst", rule.tp_dst);
		jgen.writeBooleanField("wildcard_dpid", rule.wildcard_dpid);
		jgen.writeBooleanField("wildcard_in_port", rule.wildcard_in_port);
		jgen.writeBooleanField("wildcard_dl_src", rule.wildcard_dl_src);
		jgen.writeBooleanField("wildcard_dl_dst", rule.wildcard_dl_dst);
		jgen.writeBooleanField("wildcard_dl_type", rule.wildcard_dl_type);
		jgen.writeBooleanField("wildcard_nw_src", rule.wildcard_nw_src);
		jgen.writeBooleanField("wildcard_nw_dst", rule.wildcard_nw_dst);
		jgen.writeBooleanField("wildcard_nw_proto", rule.wildcard_nw_proto);
		jgen.writeBooleanField("wildcard_tp_src", rule.wildcard_tp_src);
		jgen.writeBooleanField("wildcard_tp_dst", rule.wildcard_tp_dst);
		jgen.writeNumberField("priority", rule.priority);
		if (rule.action == FirewallRule.FirewallAction.ALLOW)
			jgen.writeStringField("action", "ALLOW");
		else if (rule.action == FirewallRule.FirewallAction.DENY)
			jgen.writeStringField("action", "DENY");
		jgen.writeEndObject();
	}
}


/**
 * This class serializes a firewall rule with a different format 
 * from a default JSON format.
 * 
 * @author jshin
 */
public class OFFirewallRuleReplySerializerModule extends SimpleModule {

	public OFFirewallRuleReplySerializerModule() {
		super("OFFirewallRuleReplySerializerModule", 
				new Version(1, 0, 0, "OFFirewallRuleReplySerializerModule"));
		
		addSerializer(FirewallRule.class, new OFFirewallRuleSerializer());
	}
}
