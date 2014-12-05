package etri.sdn.controller.module.staticentrymanager;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class RESTClearApi extends Restlet {
	private OFMStaticFlowEntryManager manager;
	
	public RESTClearApi(OFMStaticFlowEntryManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void handle(Request request, Response response) {
		StringWriter sWriter = new StringWriter();
		JsonFactory f = new JsonFactory();
		JsonGenerator g = null;
		StringBuilder status = new StringBuilder();

		String sw = (String) request.getAttributes().get("switch");
		Set<String> flows = new HashSet<String>();
		if (sw.toLowerCase().equals("all")) {
			flows = manager.getStaticFlowEntryStorage().getFlowModMap().keySet();
			if ( flows.isEmpty() ) {
				flows = null;
			}
		} else {
			flows = manager.getStaticFlowEntryStorage().getDpidToFlowModNameIndex().get(sw);
		}

		if (flows != null) {
			boolean ret = true;
			StringBuilder exceptionlist = new StringBuilder();
			
			//Avoiding ConcurrentModificationException
			Set<String> flowsToDel = new HashSet<String>();
			flowsToDel.addAll(flows);

			for (String flow : flowsToDel) {
				try {
					manager.deleteFlow( flow );
				}
				catch (UnsupportedOperationException e) {
					ret = false;
					exceptionlist.append("Wrong version for the switch. ");
				}
				catch (StaticFlowEntryException e) {
					ret = false;
					exceptionlist.append(e.getReason());
					exceptionlist.append(". ");
				}
				catch (Exception e) {
					ret = false;
					e.printStackTrace();
				}
			}
			
			if (ret) {
				status.append("All entries are cleared.");
			}
			else {
				status.append("Failure clearing entries: ");
				status.append(exceptionlist);
			}
		}
		else {
			status.append("There is no entry.");
		}

		try {
			g = f.createJsonGenerator(sWriter);
			g.writeStartObject();
			g.writeFieldName("result");
			g.writeString(status.toString());
			g.writeEndObject();
			g.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String r = sWriter.toString();
		response.setEntity(r, MediaType.APPLICATION_JSON);
	}
}
