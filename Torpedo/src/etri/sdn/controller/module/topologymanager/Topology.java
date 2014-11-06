package etri.sdn.controller.module.topologymanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.projectfloodlight.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.util.StackTrace;

/**
 * This class provides RESTful API for accessing topology information.
 */
public class Topology extends OFModel {

	private OFMTopologyManager manager;
	private Set<String> topologyUpdateCallbacks;

	public Topology(OFMTopologyManager parent) {
		this.manager = parent;
		this.topologyUpdateCallbacks = new LinkedHashSet<>();
	}

	private RESTApi[] apis = {
			
		new RESTApi(
			/*
			 * op = add | del | list
			 */
			"/wm/topology/callback/{op}/json",
			new Restlet() {
				
				private void reply(Object code, Response response) {
					// create an object mapper.
					ObjectMapper om = new ObjectMapper();
					
					try {
						String r = om.writeValueAsString(code);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						OFMTopologyManager.logger.error("error={}", StackTrace.of(e));
						return;
					}
				}
				
				private String parseJsonAndGet(String name, String text) {
					MappingJsonFactory f = new MappingJsonFactory();		
					ObjectMapper mapper = new ObjectMapper(f);
					try {
						JsonNode rootNode = mapper.readTree(text);
						Iterator<Map.Entry<String, JsonNode>> fields = rootNode.getFields();

						while (fields.hasNext()) {
							Map.Entry<String, JsonNode> field = fields.next();

							if ( field.getKey().equals(name) ) {
								return field.getValue().toString().replaceAll("^\"|\"$", "");
							}
						}
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				public void handle(Request request, Response response) {
					String op = (String) request.getAttributes().get("op");
					if ( op == null ) {
						reply("failed", response);
						return;
					}
					op = op.trim();
					
					if ( op.equals("add") ) {
						
						if ( request.getMethod() != Method.POST ) {
							return;
						}
						
						String url = parseJsonAndGet("url", request.getEntityAsText());
						
						if ( url == null ) {
							return;
						}
						
						System.out.println(url);
						
						/*
						 * Add operation
						 */
						
						topologyUpdateCallbacks.add( url );
						
						reply("ok", response);
					
					} else if ( op.equals("del") ) {
						
						if ( request.getMethod() != Method.POST ) {
							return;
						}
						
						String url = parseJsonAndGet("url", request.getEntityAsText());
						
						if ( url == null ) {
							return;
						}
						
						/*
						 * Del operation
						 */
						
						topologyUpdateCallbacks.remove( url );
						
						reply("ok", response);
						
					} else if ( op.equals("list") ) {
						
						reply(topologyUpdateCallbacks, response);
						
					} else {
						/*
						 * Not supported operation.
						 */
						
						reply("operation " + op + " is not supported", response);
					}
				}
			}
		),
			
		new RESTApi(
			"/wm/topology/switchclusters/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						Iterator<Cluster> it  = manager.getCurrentInstance().clusters.iterator();
						Cluster c = null;
						while(it.hasNext()) {
							c = it.next();
							g.writeFieldName(HexString.toHexString(c.id));
							g.writeStartArray();
							for(Long l : c.getLinks().keySet()) {
								g.writeString(HexString.toHexString(l));
							}
							g.writeEndArray();
						}

						g.writeEndObject();
						g.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}
		)
	};

	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}
	
	/**
	 * This method send HTTP requests for every registered REST callbacks that receives 
	 * Topology Update events. 
	 * 
	 */
	public void topologyUpdated() {
		for ( String callback : this.topologyUpdateCallbacks ) {
			// every callback is a URL.
			ClientResource cr = new ClientResource(callback);
			Representation rep = cr.get();
			OFMTopologyManager.logger.debug("Called topology update callback {}. result = {}", callback, rep);
		}
	}
}
