package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.openflow.protocol.OFMatch;
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
        jgen.writeNumberField("dataLayerVirtualLan", 
                    match.getDataLayerVirtualLan());
        jgen.writeNumberField("dataLayerVirtualLanPriorityCodePoint", 
                    match.getDataLayerVirtualLanPriorityCodePoint());
        jgen.writeNumberField("inputPort", match.getInputPort());
        jgen.writeStringField("networkDestination", 
                    intToIp(match.getNetworkDestination()));
        jgen.writeNumberField("networkDestinationMaskLen", 
                    match.getNetworkDestinationMaskLen());
        jgen.writeNumberField("networkProtocol", match.getNetworkProtocol());
        jgen.writeStringField("networkSource", 
                    intToIp(match.getNetworkSource()));
        jgen.writeNumberField("networkSourceMaskLen", 
                    match.getNetworkSourceMaskLen());
        jgen.writeNumberField("networkTypeOfService", 
                    match.getNetworkTypeOfService());
        jgen.writeNumberField("transportDestination", 
                    match.getTransportDestination());
        jgen.writeNumberField("transportSource", 
                    match.getTransportSource());
        jgen.writeNumberField("wildcards", match.getWildcards());
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
	}
	
}
