package etri.sdn.controller.module.staticentrymanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

public class RESTPostDeleteApi extends Restlet {
	private OFMStaticFlowEntryManager manager;
	
	public RESTPostDeleteApi(OFMStaticFlowEntryManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void handle(Request request, Response response) {
		StringWriter sWriter = new StringWriter();
		JsonFactory f = new JsonFactory();
		JsonGenerator g = null;
		String status = null;

		Method m = request.getMethod();
		String entityText = request.getEntityAsText();
		entityText = entityText.replaceAll("[\']", "");

		/*
		 * OFMStaticFlowEntryManager does NOT check every exception for user input 
		 * except for name field (key of StaticFlowEntry). Therefore, the field check 
		 * is user's portion.
		 * For example, let us consider the most simple experiment. When user want to
		 * do drop for all flows and allow ping between two end hosts, switches need
		 * the rules of ARP and ICMP both. But, the ping may be succeed with ICMP rule
		 * only if the ARP entries exist in the ARP table until the entries timed out.
		 * 
		 * OFMStaticFlowEntryManager supports the unified input format. e.g. the user
		 * input have to contain 'instructions' entry although switches support OF1.0
		 * only. In this case, OFMStaticFlowEntryManager sets not OFInstruction but
		 * OFAction directly when it builds OFFlowMod.
		 */
		if (m == Method.POST) {
			Map<String, Object> entry;
			Object flowName;
			
			try {
				entry = StaticFlowEntry.jsonToStaticFlowEntry(entityText);
				if (entry == null) {
					throw new Exception ("The input is null");
				}

				flowName = entry.get("name");
				if (flowName != null) {
					StaticFlowEntry.checkInputField(entry.keySet());
					StaticFlowEntry.checkMatchPrerequisite(entry);
					manager.addFlow((String) flowName, entry, (String) entry.get("switch"));
					status = "Entry pushed: " + flowName;
				}
				else {
					status = "The name field is indispensable";
				}
			}
			catch (UnsupportedOperationException e) {
				status = "Fail to push entry: Wrong version for the switch";
			}
			catch (StaticFlowEntryException e) {
				status = e.getReason();
			}
			catch (IOException e) {
				status = "Fail to parse JSON format";
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
				status = "Fail to insert entry";
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
		} 
		
		else if (m == Method.DELETE) {
			Map<String, Object> entry;
			Object flowName;

			try {
				entry = StaticFlowEntry.jsonToStaticFlowEntry(entityText);
				if (entry == null) {
					throw new Exception ("The input is null");
				}
				
				flowName = entry.get("name");
				if (flowName != null) {
					manager.deleteFlow((String)flowName);
					status = "Entry deleted: " + flowName;
				}
				else {
					status = "The name field is indispensable.";
				}
			}
			catch (UnsupportedOperationException e) {
				status = "Fail to delete entry: Wrong version for the switch";
			}
			catch (StaticFlowEntryException e) {
				status = e.getReason();
			}
			catch (Exception e) {
				e.printStackTrace();
				status = "Fail to delete entry";
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
		}

		String r = sWriter.toString();
		response.setEntity(r, MediaType.APPLICATION_JSON);
	}
}
