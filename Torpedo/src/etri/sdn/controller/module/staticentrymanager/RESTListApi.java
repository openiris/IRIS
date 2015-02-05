package etri.sdn.controller.module.staticentrymanager;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class RESTListApi extends Restlet {
	
	private OFMStaticFlowEntryManager manager;
	
	public RESTListApi(OFMStaticFlowEntryManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void handle(Request request, Response response) {
		
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
		
		ObjectMapper om = new ObjectMapper();
		try {
			String r = om.writeValueAsString(manager.getStaticFlowEntryStorage().getFlowModMap(sw));
			response.setEntity(r, MediaType.APPLICATION_JSON);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
