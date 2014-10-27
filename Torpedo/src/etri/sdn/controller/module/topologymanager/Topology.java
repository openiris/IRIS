package etri.sdn.controller.module.topologymanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.projectfloodlight.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
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
			 * op = add | del
			 * url = <String that starts with http:
			 */
			"/wm/topology/callback/{op}/{url}",
			new Restlet() {
				
				private void reply(String code, Response response) {
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
				
				@Override
				public void handle(Request request, Response response) {
					String op = (String) request.getAttributes().get("op");
					String url = (String) request.getAttributes().get("url");
					if ( op == null || url == null ) {
						reply("failed", response);
						return;
					}
					op = op.trim();
					url = url.trim();
					
					if ( op.equals("add") ) {
						/*
						 * Add operation
						 */
					
						// url should start by http:
						int httpIdx = url.indexOf("http:");
						if ( httpIdx < 0 || httpIdx > 0 ) {
							reply("url format is not right. should start with http:", response);
							return;
						}
						
						topologyUpdateCallbacks.add( url );
						
						reply("ok", response);
					
					} else if ( op.equals("del") ) {
						/*
						 * Del operation
						 */
						
						topologyUpdateCallbacks.remove( url );
						reply("ok", response);
						
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
