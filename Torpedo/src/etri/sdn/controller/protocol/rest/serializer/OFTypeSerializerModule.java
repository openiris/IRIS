package etri.sdn.controller.protocol.rest.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.U64;


/**
 * A Custom Serializer for OFFeaturesReply (FEATURES_REPLY) message.
 * 
 * @author bjlee
 *
 */
final class OFPortSerializer extends JsonSerializer<OFPort> {
	@Override
	public void serialize(OFPort reply, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		jgen.writeNumber(reply.getPortNumber());
	}
}

final class U64Serializer extends JsonSerializer<U64> {
	@Override
	public void serialize(U64 val, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeNumber(val.getValue());
	}
}


/**
 * A Custom Serializer Module which consists of 
 * {@link OFFeaturesReplySerializer} and {@link OFPhysicalPortSerializer}
 * 
 * @author bjlee
 *
 */
public final class OFTypeSerializerModule extends SimpleModule {

	public OFTypeSerializerModule() {
		super("OFTypeSerializerModule", new Version(1, 0, 0, "OFTypeSerializerModule"));
		
		addSerializer(OFPort.class, new OFPortSerializer());
		addSerializer(U64.class, new U64Serializer());
	}
}