package etri.sdn.controller.module.staticentrymanager;

import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class RESTDeleteByNameApi extends Restlet {
	
	private OFMStaticFlowEntryManager manager;
	
	public RESTDeleteByNameApi(OFMStaticFlowEntryManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void handle(Request request, Response response) {
		StringWriter sWriter = new StringWriter();
		JsonFactory f = new JsonFactory();
		JsonGenerator g;
		String status = null;

		String flowName = (String) request.getAttributes().get("name");
		
		if ( !manager.getAllFlows().keySet().contains(flowName) ) {
			status = "There is no entry";
		} else {
			try {
				manager.deleteFlow(flowName);
				status = "Entry deleted: " + flowName;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		try {
			g = f.createJsonGenerator(sWriter);
			g.writeStartObject();
			g.writeFieldName("result");
			g.writeString(status);
			g.writeEndObject();
			g.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String r = sWriter.toString();
		response.setEntity(r, MediaType.APPLICATION_JSON);
	}
}
