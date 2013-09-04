package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.openflow.protocol.OFFeaturesReply;
import org.openflow.protocol.OFPhysicalPort;
import org.openflow.util.HexString;

/**
 * A Custom Serializer for OFFeaturesReply (FEATURES_REPLY) message.
 * 
 * @author bjlee
 *
 */
final class OFFeaturesReplySerializer extends JsonSerializer<OFFeaturesReply> {
	@Override
	public void serialize(OFFeaturesReply reply, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		jgen.writeStringField("datapathId", HexString.toHexString(reply.getDatapathId()));
		jgen.writeNumberField("actions", reply.getActions());
		jgen.writeNumberField("buffers", reply.getBuffers());
		jgen.writeNumberField("capabilities", reply.getCapabilities());
		jgen.writeNumberField("length", reply.getLength());
		jgen.writeNumberField("tables", reply.getTables());
        jgen.writeStringField("type", reply.getType().toString());
        jgen.writeNumberField("version", reply.getVersion());
        jgen.writeNumberField("xid", reply.getXid());
		provider.defaultSerializeField("ports", reply.getPorts(), jgen);
        jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer for OFPhysicalPort object.
 * @author bjlee
 *
 */
final class OFPhysicalPortSerializer extends JsonSerializer<OFPhysicalPort> {
	
	@Override
	public void serialize(OFPhysicalPort port, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		jgen.writeNumberField("portNumber", port.getPortNumber());
		jgen.writeStringField("hardwareAddress", HexString.toHexString(port.getHardwareAddress()));
		jgen.writeStringField("name", port.getName());
		jgen.writeNumberField("config", port.getConfig());
		jgen.writeNumberField("state", port.getState());
		jgen.writeNumberField("currentFeatures", port.getCurrentFeatures());
		jgen.writeNumberField("advertisedFeatures", port.getAdvertisedFeatures());
		jgen.writeNumberField("supportedFeatures", port.getSupportedFeatures());
		jgen.writeNumberField("peerFeatures", port.getPeerFeatures());
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

	public OFFeaturesReplySerializerModule() {
		super("OFFeaturesReplyModule", new Version(1, 0, 0, "OFFeaturesReplyModule"));
		
		addSerializer(OFFeaturesReply.class, new OFFeaturesReplySerializer());
		addSerializer(OFPhysicalPort.class, new OFPhysicalPortSerializer());
	}
	
}