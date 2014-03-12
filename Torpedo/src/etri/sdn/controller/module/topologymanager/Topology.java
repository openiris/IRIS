package etri.sdn.controller.module.topologymanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.projectfloodlight.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.OFModel;

/**
 * This class provides RESTful API for accessing topology information.
 */
public class Topology extends OFModel {

	private OFMTopologyManager manager;

	public Topology(OFMTopologyManager parent) {
		this.manager = parent;
	}

	private RESTApi[] apis = {
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
}
