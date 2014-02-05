package etri.sdn.controller.module.statemanager;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.openflow.protocol.interfaces.OFFeaturesReply;
import org.openflow.protocol.interfaces.OFPortDesc;
import org.openflow.util.HexString;

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
		jgen.writeStringField("datapathId", HexString.toHexString(reply.getDatapathId()));
		if (reply.isActionsSupported())
			jgen.writeNumberField("actions", reply.getActions());
		else
			jgen.writeNumberField("actions", 0);
		jgen.writeNumberField("buffers", reply.getNBuffers());
		jgen.writeNumberField("capabilities", reply.getCapabilitiesWire());
		jgen.writeNumberField("length", reply.getLength());
		jgen.writeNumberField("tables", reply.getNTables());
        jgen.writeStringField("type", reply.getType().toString());
        jgen.writeNumberField("version", reply.getVersion());
        jgen.writeNumberField("xid", reply.getXid());
        if ( reply.isPortsSupported() ) {
        	provider.defaultSerializeField("ports", reply.getPorts(), jgen);
        } else {
        	provider.defaultSerializeField("ports", this.protocol.getPorts(reply.getDatapathId()), jgen);
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
		jgen.writeNumberField("portNumber", port.getPort().get());
		jgen.writeStringField("hardwareAddress", HexString.toHexString(port.getHwAddr()));
		jgen.writeStringField("name", new String(port.getName()));
		jgen.writeNumberField("config", port.getConfigWire());
		jgen.writeNumberField("state", port.getStateWire());
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

	public OFFeaturesReplySerializerModule(OFProtocol protocol) {
		super("OFFeaturesReplyModule", new Version(1, 0, 0, "OFFeaturesReplyModule"));
		
		addSerializer(OFFeaturesReply.class, new OFFeaturesReplySerializer(protocol));
//		addSerializer(OFPhysicalPort.class, new OFPhysicalPortSerializer());
		addSerializer(OFPortDesc.class, new OFPhysicalPortSerializer());
	}
	
}