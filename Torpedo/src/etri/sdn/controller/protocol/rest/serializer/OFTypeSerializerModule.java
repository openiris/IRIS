package etri.sdn.controller.protocol.rest.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.protocol.oxm.OFOxm;
import org.projectfloodlight.openflow.types.ArpOpcode;
import org.projectfloodlight.openflow.types.Masked;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.U64;


/**
 * Custom serializer for OFMatch message. 
 * 
 * @author bjlee
 *
 */
final class OFMatchSerializer extends JsonSerializer<Match> {

	/**
	 * Serialize function that converts OFMatch to JSON format.
	 * 
	 * @param match		OFMatch object
	 * @param jgen		JsonGenerator object
	 * @param provider	SerializerProvider object. not used.
	 */
	@Override
	public void serialize(Match match, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		for ( MatchField<?> mf: match.getMatchFields() ) {
			if ( match.isExact(mf) ) {
				jgen.writeStringField(mf.getName(), match.get(mf).toString());
			} else {
				Masked<?> mv = match.getMasked(mf);
				jgen.writeStringField(mf.getName(), mv.getValue().toString());
				jgen.writeStringField(mf.getName()+"_MASK", mv.getMask().toString());
			}
		}
		jgen.writeEndObject();
	}
}

/**
 * Custom serializer for OFOxm object.
 * 
 * @author bjlee
 */
@SuppressWarnings("rawtypes")
final class OFOxmSerializer extends JsonSerializer<OFOxm> {
	@Override
	public void serialize(OFOxm oxm, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField(oxm.getMatchField().getName(), oxm.getValue().toString());
		if ( oxm.isMasked() ) {
			jgen.writeStringField(oxm.getMatchField().getName() + "_MASK", oxm.getMask().toString());
		}
		jgen.writeEndObject();
	}
}

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

final class ArpOpcodeSerializer extends JsonSerializer<ArpOpcode> {

	@Override
	public void serialize(ArpOpcode val, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		jgen.writeString(val.toString());
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
		addSerializer(ArpOpcode.class, new ArpOpcodeSerializer());
		addSerializer(Match.class, new OFMatchSerializer());
		addSerializer(OFOxm.class, new OFOxmSerializer());
	}
}

