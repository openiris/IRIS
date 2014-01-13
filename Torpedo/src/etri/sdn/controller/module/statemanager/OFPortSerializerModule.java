package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.openflow.util.OFPort;

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
		jgen.writeNumber(reply.get());
	}
}


/**
 * A Custom Serializer Module which consists of 
 * {@link OFFeaturesReplySerializer} and {@link OFPhysicalPortSerializer}
 * 
 * @author bjlee
 *
 */
public final class OFPortSerializerModule extends SimpleModule {

	public OFPortSerializerModule() {
		super("OFPortSerializerModule", new Version(1, 0, 0, "OFPortSerializerModule"));
		
		addSerializer(OFPort.class, new OFPortSerializer());
	}
}