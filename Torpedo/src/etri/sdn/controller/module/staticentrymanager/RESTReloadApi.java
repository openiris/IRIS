package etri.sdn.controller.module.staticentrymanager;

import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class RESTReloadApi extends Restlet {
	private OFMStaticFlowEntryManager manager;
	
	public RESTReloadApi(OFMStaticFlowEntryManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void handle(Request request, Response response) {
		StringWriter sWriter = new StringWriter();
		JsonFactory f = new JsonFactory();
		JsonGenerator g = null;
		String status = null;

		String sw = (String) request.getAttributes().get("switch");
		
		try {
			if (sw.toLowerCase().equals("all")) {
				if (!manager.getStaticFlowEntryStorage().getFlowModNameToDpidIndex().isEmpty()) {
					manager.reloadAllFlowsToSwitch();
					status = "All entries are reloaded to switches.";
				}
				else {
					status = "There is no entry";
				}
			}
			else {
				if (!manager.getStaticFlowEntryStorage().getDpidToFlowModNameIndex().isEmpty()) {
					manager.reloadFlowsToSwitch(sw);
					status = "Entries are reloaded to switch: " + sw + ".";
				}
				else {
					status = "There is no entry";
				}
			}
		}
		catch (UnsupportedOperationException e) {
			status = "Fail to reload entry: Wrong version for the switch";
		}
		catch (Exception e) {
			e.printStackTrace();
			status = "Fail to reload entries to switches.";
		}

		try {
			g = f.createJsonGenerator(sWriter);
			g.writeStartObject();
			g.writeFieldName("result");
			g.writeString(status);
			g.writeEndObject();
			g.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String r = sWriter.toString();
		response.setEntity(r, MediaType.APPLICATION_JSON);
	}
}
