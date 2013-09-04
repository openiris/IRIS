package etri.sdn.controller.module.statemanager;

import java.io.IOException;
import java.util.Collection;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

import etri.sdn.controller.OFController;
import etri.sdn.controller.OFModule;

/**
 * A Custom Serializer for OFModule class. 
 * This serializer exports information including:
 * 
 * (1) name of the module
 * (2) services that this module provides 
 * (3) services that this module uses (depends)
 * 
 * @author bjlee
 *
 */
class ModuleSerializer extends JsonSerializer<OFModule> {
	@Override
	public void serialize(OFModule module, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		Collection<OFModule> modules = module.getController().getModules();
		jgen.writeStartObject();
			jgen.writeBooleanField("loaded", true);
			
			jgen.writeFieldName("provides");
			Class<?>[] interfaces = module.getClass().getInterfaces();
			java.util.List<String> deps = new java.util.LinkedList<String>();
			jgen.writeStartObject();
			for ( Class<?> intf : interfaces ) {
				if ( intf.getName().endsWith("Service") ) {
					jgen.writeStringField(intf.getCanonicalName(), module.getClass().getCanonicalName());
				}
				else if (intf.getName().endsWith("Listener")) {
					String depends = intf.getCanonicalName().replace("Listener", "Service");
					deps.add(depends);
				}
			}	
			jgen.writeEndObject();
			
			jgen.writeFieldName("depends");
			jgen.writeStartObject();
			outmost:
			for ( String s : deps ) {
				for ( OFModule m : modules ) {
					Class<?>[] ifs = m.getClass().getInterfaces();
					for ( Class<?> c : ifs ) {
						if ( c.getCanonicalName().equals(s) ) {
							jgen.writeStringField(s, m.getClass().getCanonicalName());
							break outmost;
						}
					}
				}
				
			}
			jgen.writeEndObject();
		jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer that serialize OFController. 
 * Actually, the serialization result is the list of the 
 * serialization results of all OFModule belongs to the controller.
 * 
 * @author bjlee
 *
 */
class ModuleListSerializer extends JsonSerializer<OFController> {
	@Override
	public void serialize(OFController controller, JsonGenerator jgen, SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		for (OFModule m : controller.getModules()) {
			provider.defaultSerializeField(m.getClass().getCanonicalName(), m, jgen);
		}
        jgen.writeEndObject();
	}
}

/**
 * A Custom Serializer module that consists of 
 * {@link ModuleListSerializer} and {@link ModuleSerializer}.
 * 
 * @author bjlee
 *
 */
public class ModuleListSerializerModule extends SimpleModule {
	public ModuleListSerializerModule() {
		super("ModuleListSerializerModule", new Version(1, 0, 0, "ModuleListSerializerModule"));
		
		addSerializer(OFController.class, new ModuleListSerializer());
		addSerializer(OFModule.class, new ModuleSerializer());
	}
}