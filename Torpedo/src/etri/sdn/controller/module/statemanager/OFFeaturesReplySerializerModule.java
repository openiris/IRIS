package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFPortDesc;

import etri.sdn.controller.protocol.OFProtocol;

/**
 * A Custom Serializer for OFFeaturesReply (FEATURES_REPLY) message.
 * 
 * @author bjlee
 *
 */
final class OFFeaturesReplySerializer extends JsonSerializer<OFFeaturesReply> {

	OFProtocol protocol;

	public OFFeaturesReplySerializer(OFProtocol protocol) {
		this.protocol = protocol;
	}
	@Override
	public void serialize(OFFeaturesReply reply, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeStringField("datapathId", reply.getDatapathId().toString());
		try {
			jgen.writeStringField("actions", reply.getActions().toString());
		} catch ( UnsupportedOperationException u ) {
			jgen.writeStringField("actions", "[]");
		}

		jgen.writeNumberField("buffers", reply.getNBuffers());
		jgen.writeStringField("capabilities", reply.getCapabilities().toString());
		jgen.writeNumberField("tables", reply.getNTables());
		jgen.writeStringField("type", reply.getType().toString());
		jgen.writeNumberField("version", reply.getVersion().ordinal());
		jgen.writeNumberField("xid", reply.getXid());
		try {
			provider.defaultSerializeField("ports", reply.getPorts(), jgen);
		} catch (UnsupportedOperationException u) {
			provider.defaultSerializeField("ports", this.protocol.getPortInformations(reply.getDatapathId().getLong()), jgen);
		}
		jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer for OFPhysicalPort object.
 * @author bjlee
 *
 */
//final class OFPhysicalPortSerializer extends JsonSerializer<OFPhysicalPort> {
final class OFPhysicalPortSerializer extends JsonSerializer<OFPortDesc> {

	@Override
	//	public void serialize(OFPhysicalPort port, JsonGenerator jgen, SerializerProvider provider) 
	public void serialize(OFPortDesc port, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeNumberField("portNumber", port.getPortNo().getPortNumber());
		jgen.writeStringField("hardwareAddress", port.getHwAddr().toString());
		jgen.writeStringField("name", new String(port.getName()));
		jgen.writeStringField("config", port.getConfig().toString());
		jgen.writeStringField("state", port.getState().toString());
		jgen.writeStringField("currentFeatures", port.getCurr().toString());
		jgen.writeStringField("advertisedFeatures", port.getAdvertised().toString());
		jgen.writeStringField("supportedFeatures", port.getSupported().toString());
		jgen.writeStringField("peerFeatures", port.getPeer().toString());
		jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer Module which consists of 
 * {@link OFFeaturesReplySerializer} and {@link OFPhysicalPortSerializer}
 * 
 * @author bjlee
 *
 */
public final class OFFeaturesReplySerializerModule extends SimpleModule {

	public OFFeaturesReplySerializerModule(OFProtocol protocol) {
		super("OFFeaturesReplyModule", new Version(1, 0, 0, "OFFeaturesReplyModule"));

		addSerializer(OFFeaturesReply.class, new OFFeaturesReplySerializer(protocol));
		//		addSerializer(OFPhysicalPort.class, new OFPhysicalPortSerializer());
		addSerializer(OFPortDesc.class, new OFPhysicalPortSerializer());
	}

}