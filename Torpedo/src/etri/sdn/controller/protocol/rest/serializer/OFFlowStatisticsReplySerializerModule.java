package etri.sdn.controller.protocol.rest.serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.protocol.OFFlowStatsEntry;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.Masked;

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
		for ( MatchField mf: match.getMatchFields() ) {
			if ( match.isExact(mf) ) {
				jgen.writeStringField(mf.getName(), match.get(mf).toString());
			} else {
				Masked mv = match.getMasked(mf);
				jgen.writeStringField(mf.getName(), mv.getValue().toString());
				jgen.writeStringField(mf.getName()+"_MASK", mv.getMask().toString());
			}
		}
		jgen.writeEndObject();
	}
}

final class OFFlowStatsEntrySerializer extends JsonSerializer<OFFlowStatsEntry> {

	@Override
	public void serialize(OFFlowStatsEntry entry, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException,	JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeNumberField("tableId", entry.getTableId().getValue());
		provider.defaultSerializeField("match", entry.getMatch(), jgen);
		jgen.writeNumberField("durationSeconds", entry.getDurationSec());
		jgen.writeNumberField("durationNanoSeconds", entry.getDurationNsec());
		jgen.writeNumberField("priority", entry.getPriority());
		jgen.writeNumberField("idleTimeout", entry.getIdleTimeout());
		jgen.writeNumberField("hardTimeout", entry.getHardTimeout());
		jgen.writeNumberField("cookie", entry.getCookie().getValue());
		jgen.writeNumberField("packetCount", entry.getPacketCount().getValue());
		jgen.writeNumberField("byteCount", entry.getByteCount().getValue());
		try {
			provider.defaultSerializeField("actions", entry.getActions(), jgen);
		} catch (UnsupportedOperationException u) {
			provider.defaultSerializeField("instructions", entry.getInstructions(), jgen);
		}
		jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer module consists of OFFlowStatisticsReplySerializer
 * 
 * @author bjlee
 *
 */
public class OFFlowStatisticsReplySerializerModule extends SimpleModule {

	public OFFlowStatisticsReplySerializerModule() {
		super("OFFlowStatisticsReplySerializerModule", 
				new Version(1, 0, 0, "OFFlowStatisticsReplySerializerModule"));

		addSerializer(Match.class, new OFMatchSerializer());
		addSerializer(OFFlowStatsEntry.class, new OFFlowStatsEntrySerializer());
	}

}
