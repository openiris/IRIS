package etri.sdn.controller.module.statemanager;

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
import org.openflow.protocol.OFBFlowWildcard;
import org.openflow.protocol.interfaces.OFFlowStatsEntry;
import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFOxm;
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
    static String intToIp(int i) {
        return ((i >> 24 ) & 0xFF) + "." +
               ((i >> 16 ) & 0xFF) + "." +
               ((i >>  8 ) & 0xFF) + "." +
               ( i        & 0xFF);
    }
    
    static String arrToIp(byte[] arr) {
    	return String.format("%d.%d.%d.%d", arr[0], arr[1], arr[2], arr[3]);
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
    static int getNetworkDestinationMaskLen(OFMatch match) {
        return Math
                .max(32 - ((match.getWildcardsWire() & OFBFlowWildcard.NW_DST_MASK) >> OFBFlowWildcard.NW_DST_SHIFT), 0);
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
    static int getNetworkSourceMaskLen(OFMatch match) {
        return Math
                .max(32 - ((match.getWildcardsWire() & OFBFlowWildcard.NW_SRC_MASK) >> OFBFlowWildcard.NW_SRC_SHIFT), 0);
    }
    
    static int getMaskLen(byte[] mask) {
    	// from the mask, we calculate the mask length.
    	int ret = 0;
    	for (byte b: mask) {
    		if ( b == (byte) 0xff ) {
    			ret += 8;
    		}
    	}
    	return ret;
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
		if ( match.isWildcardsSupported() ) {
			int wildcard = match.getWildcardsWire();
			
			jgen.writeNumberField("wildcards", wildcard);
			if ( (wildcard & OFBFlowWildcard.DL_DST) == 0 ) 
				jgen.writeStringField("dataLayerDestination", HexString.toHexString(match.getDataLayerDestination()));
			if ( (wildcard & OFBFlowWildcard.DL_SRC) == 0 ) 
				jgen.writeStringField("dataLayerSource", HexString.toHexString(match.getDataLayerSource()));
			if ( (wildcard & OFBFlowWildcard.DL_TYPE) == 0 ) {
		        String dataType = Integer.toHexString((int)match.getDataLayerType());
		        while (dataType.length() < 4) {
		            dataType = "0".concat(dataType);
		        }
		        jgen.writeStringField("dataLayerType", "0x" + dataType);
			}
			if ( (wildcard & OFBFlowWildcard.DL_VLAN) == 0 )
				jgen.writeNumberField("dataLayerVirtualLan", match.getDataLayerVirtualLan());
			if ( (wildcard & OFBFlowWildcard.DL_VLAN_PCP) == 0 )
				jgen.writeNumberField("dataLayerVirtualLanPriorityCodePoint", match.getDataLayerVirtualLanPriorityCodePoint());
			
			if ( (wildcard & OFBFlowWildcard.IN_PORT) == 0 )
				jgen.writeNumberField("inputPort", match.getInputPort().get());
			if ( (wildcard & OFBFlowWildcard.NW_PROTO) == 0 )
	        	 jgen.writeNumberField("networkProtocol", match.getNetworkProtocol());
	        if ( (wildcard & OFBFlowWildcard.NW_DST_ALL) == 0 )
	        	jgen.writeStringField("networkDestination", intToIp(match.getNetworkDestination()));
	        if ( (wildcard & OFBFlowWildcard.NW_DST_MASK) == 0 )
	        	jgen.writeNumberField("networkDestinationMaskLen", getNetworkDestinationMaskLen(match));
	        if ( (wildcard & OFBFlowWildcard.NW_SRC_ALL) == 0 )
		        jgen.writeStringField("networkSource", intToIp(match.getNetworkSource()));
	        if ( (wildcard & OFBFlowWildcard.NW_SRC_MASK) == 0 )
		        jgen.writeNumberField("networkSourceMaskLen", getNetworkSourceMaskLen(match));
	        if ( (wildcard & OFBFlowWildcard.NW_TOS) == 0 )
	        	jgen.writeNumberField("networkTypeOfService", match.getNetworkTypeOfService());
	        if ( (wildcard & OFBFlowWildcard.TP_DST) == 0 )
	        	jgen.writeNumberField("transportDestination", match.getTransportDestination());
	        if ( (wildcard & OFBFlowWildcard.TP_SRC) == 0 )
	        	jgen.writeNumberField("transportSource", match.getTransportSource());
		}
		else {
			for ( OFOxm oxm : match.getOxmFields() ) {
				provider.defaultSerializeValue(oxm, jgen);
			}
		}
        jgen.writeEndObject();
	}
}

abstract class OxmVisualizer {
	public abstract void visualize(JsonGenerator jgen, SerializerProvider provider, OFOxm oxm) 
			throws JsonGenerationException, IOException;
}

class MacVisualizer extends OxmVisualizer {

	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider, OFOxm oxm) 
			throws JsonGenerationException, IOException {
		byte field = oxm.getField();
		byte hasmask = oxm.getBitmask();
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		byte[] mac = new byte[6];
		byte[] mask = new byte[6];
		buffer.get(mac);
		if ( hasmask != (byte) 0x00 ) 
			buffer.get(mask);
		switch ( field ) {
		case 0x03:			// DST
			jgen.writeStringField("dataLayerDestination", HexString.toHexString(mac));
			break;
		case 0x04:			// SRC
			jgen.writeStringField("dataLayerSource", HexString.toHexString(mac));
			break;
		}
	}
	
}

class DataLayerTypeVisualizer extends OxmVisualizer {

	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider, OFOxm oxm) 
			throws JsonGenerationException, IOException {
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		String dataType = Integer.toHexString(buffer.getShort());
        while (dataType.length() < 4) {
            dataType = "0".concat(dataType);
        }
        jgen.writeStringField("dataLayerType", "0x" + dataType);
	}	
}

class IntVisualizer extends OxmVisualizer {
	private String fieldName;
	
	IntVisualizer(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider, OFOxm oxm) 
			throws JsonGenerationException, IOException {
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		jgen.writeNumberField(fieldName, buffer.getInt());
	}
}

class ShortVisualizer extends OxmVisualizer {
	private String fieldName;
	
	ShortVisualizer(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider,
			OFOxm oxm) throws JsonGenerationException, IOException {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		jgen.writeNumberField(fieldName, buffer.getShort());
	}
	
}

class ByteVisualizer extends OxmVisualizer {
	private String fieldName;
	
	ByteVisualizer(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider, OFOxm oxm) 
			throws JsonGenerationException, IOException {
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		jgen.writeNumberField(fieldName, buffer.get());
	}
}

class IPAddressVisualizer extends OxmVisualizer {

	@Override
	public void visualize(JsonGenerator jgen, SerializerProvider provider,
			OFOxm oxm) throws JsonGenerationException, IOException {
		byte field = oxm.getField();
		byte hasmask = oxm.getBitmask();
		ByteBuffer buffer = ByteBuffer.wrap(oxm.getData());
		byte[] ip = new byte[4];
		byte[] mask = new byte[4];
		buffer.get(ip);
		if ( hasmask != (byte) 0x00 ) 
			buffer.get(mask);
		switch ( field ) {
		case 11:			// SRC
			jgen.writeStringField("networkSource", OFMatchSerializer.arrToIp(ip));
			break;
		case 12:			// DST
			jgen.writeStringField("networkDestination", OFMatchSerializer.arrToIp(ip));
			break;
		}
	}
	
}

final class OFOxmSerializer extends JsonSerializer<OFOxm> {
	
	Map<Byte, OxmVisualizer> vids = new ConcurrentHashMap<Byte, OxmVisualizer>();
	
	public OFOxmSerializer() {
		vids.put((byte)0, new IntVisualizer("inputPort"));				// OFB_IN_PORT
		vids.put((byte)3, new MacVisualizer());							// OFB_ETH_DST
		vids.put((byte)4, new MacVisualizer());							// OFB_ETH_SRC
		vids.put((byte)5, new DataLayerTypeVisualizer());				// OFB_ETH_TYPE
		vids.put((byte)6, new ShortVisualizer("dataLayerVirtualLan"));	// OFB_VLAN_VID
		vids.put((byte)7, new ByteVisualizer("dataLayerVirtualLanPriorityCodePoint"));	// OFB_VLAN_PCP
		vids.put((byte)8, new ByteVisualizer("networkDscp"));
		vids.put((byte)9, new ByteVisualizer("networkEcn"));
		vids.put((byte)10, new ByteVisualizer("networkProtocol"));
		vids.put((byte)11, new IPAddressVisualizer());
		vids.put((byte)12, new IPAddressVisualizer());
		vids.put((byte)13, new ShortVisualizer("tcpSource"));
		vids.put((byte)14, new ShortVisualizer("tcpDestination"));
		vids.put((byte)15, new ShortVisualizer("udpSource"));
		vids.put((byte)16, new ShortVisualizer("udpDestination"));
		vids.put((byte)19, new ByteVisualizer("icmpType"));
		vids.put((byte)20, new ByteVisualizer("icmpCode"));
	}

	@Override
	public void serialize(OFOxm oxm, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {
		OxmVisualizer v = vids.get(oxm.getField());
		if ( v != null ) {
			v.visualize(jgen, provider, oxm);
		}
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
        if ( entry.isActionsSupported() ) {
        	provider.defaultSerializeField("actions", entry.getActions(), jgen);
        } else {
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
		
		addSerializer(OFMatch.class, new OFMatchSerializer());
		addSerializer(OFOxm.class, new OFOxmSerializer());
		addSerializer(OFFlowStatsEntry.class, new OFFlowStatsEntrySerializer());
	}
	
}
