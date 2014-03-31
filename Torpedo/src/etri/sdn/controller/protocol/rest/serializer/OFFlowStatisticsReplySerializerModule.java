package etri.sdn.controller.protocol.rest.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.protocol.OFFlowStatsEntry;

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

		addSerializer(OFFlowStatsEntry.class, new OFFlowStatsEntrySerializer());
	}

}
