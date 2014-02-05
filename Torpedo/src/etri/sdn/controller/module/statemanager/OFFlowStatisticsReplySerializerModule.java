package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.openflow.protocol.ver1_0.messages.OFFlowStatsEntry;
import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.protocol.ver1_0.types.OFFlowWildcards;
import org.openflow.util.HexString;

/**
 * Custom serializer for OFMatch message. 
 * 
 * @author bjlee
 *
 */
final class OFMatchSerializer extends JsonSerializer<OFMatch> {
	
	/**
     * Converts an IP in a 32 bit integer to a dotted-decimal string
     * @param i The IP address in a 32 bit integer
     * @return An IP address string in dotted-decimal
     */
    private String intToIp(int i) {
        return ((i >> 24 ) & 0xFF) + "." +
               ((i >> 16 ) & 0xFF) + "." +
               ((i >>  8 ) & 0xFF) + "." +
               ( i        & 0xFF);
    }
    
    /**
     * Parse this match's wildcard fields and return the number of significant
     * bits in the IP destination field.
     * 
     * NOTE: this returns the number of bits that are fixed, i.e., like CIDR,
     * not the number of bits that are free like OpenFlow encodes.
     * 
     * @return a number between 0 (matches all IPs) and 63 ( 32>= implies exact
     *         match)
     */
    public int getNetworkDestinationMaskLen(OFMatch match) {
        return Math
                .max(32 - ((match.getWildcardsWire() & OFFlowWildcards.NW_DST_MASK) >> OFFlowWildcards.NW_DST_SHIFT),
                        0);
    }

    /**
     * Parse this match's wildcard fields and return the number of significant
     * bits in the IP destination field.
     * 
     * NOTE: this returns the number of bits that are fixed, i.e., like CIDR,
     * not the number of bits that are free like OpenFlow encodes.
     * 
     * @return a number between 0 (matches all IPs) and 32 (exact match)
     */
    public int getNetworkSourceMaskLen(OFMatch match) {
        return Math
                .max(32 - ((match.getWildcardsWire() & OFFlowWildcards.NW_SRC_MASK) >> OFFlowWildcards.NW_SRC_SHIFT),
                        0);
    }
	
    /**
     * Serialize function that converts OFMatch to JSON format.
     * 
     * @param match		OFMatch object
     * @param jgen		JsonGenerator object
     * @param provider	SerializerProvider object. not used.
     */
	@Override
	public void serialize(OFMatch match, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
        jgen.writeStringField("dataLayerDestination", 
                    HexString.toHexString(match.getDataLayerDestination()));
        jgen.writeStringField("dataLayerSource", 
                    HexString.toHexString(match.getDataLayerSource()));
        String dataType = Integer.toHexString(match.getDataLayerType());
        while (dataType.length() < 4) {
            dataType = "0".concat(dataType);
        }
        jgen.writeStringField("dataLayerType", "0x" + dataType);
        jgen.writeNumberField("dataLayerVirtualLan", match.getDataLayerVirtualLan());
        jgen.writeNumberField("dataLayerVirtualLanPriorityCodePoint", match.getDataLayerVirtualLanPriorityCodePoint());
        jgen.writeNumberField("inputPort", match.getInputPort().get());
        jgen.writeStringField("networkDestination", intToIp(match.getNetworkDestination()));
        jgen.writeNumberField("networkDestinationMaskLen", getNetworkDestinationMaskLen(match));
        jgen.writeNumberField("networkProtocol", match.getNetworkProtocol());
        jgen.writeStringField("networkSource", intToIp(match.getNetworkSource()));
        jgen.writeNumberField("networkSourceMaskLen", getNetworkSourceMaskLen(match));
        jgen.writeNumberField("networkTypeOfService", match.getNetworkTypeOfService());
        jgen.writeNumberField("transportDestination", match.getTransportDestination());
        jgen.writeNumberField("transportSource", match.getTransportSource());
        jgen.writeNumberField("wildcards", match.getWildcardsWire());
        jgen.writeEndObject();
	}
}

final class OFFlowStatsEntrySerializer extends JsonSerializer<OFFlowStatsEntry> {

	@Override
	public void serialize(OFFlowStatsEntry entry, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException,	JsonProcessingException {
		jgen.writeStartObject();
        jgen.writeNumberField("tableId", entry.getTableId());
        provider.defaultSerializeField("match", entry.getMatch(), jgen);
        jgen.writeNumberField("durationSeconds", entry.getDurationSec());
        jgen.writeNumberField("durationNanoSeconds", entry.getDurationNsec());
        jgen.writeNumberField("priority", entry.getIdleTimeout());
        jgen.writeNumberField("idleTimeout", entry.getIdleTimeout());
        jgen.writeNumberField("hardTimeout", entry.getHardTimeout());
        jgen.writeNumberField("cookie", entry.getCookie());
        jgen.writeNumberField("packetCount", entry.getPacketCount());
        jgen.writeNumberField("byteCount", entry.getByteCount());
        provider.defaultSerializeField("actions", entry.getActions(), jgen);
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
		
		addSerializer(OFMatch.class, new OFMatchSerializer());
		addSerializer(OFFlowStatsEntry.class, new OFFlowStatsEntrySerializer());
	}
	
}
