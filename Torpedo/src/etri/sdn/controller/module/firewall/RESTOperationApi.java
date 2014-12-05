package etri.sdn.controller.module.firewall;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTOperationApi extends Restlet {

	// create logger
	private static final Logger logger = LoggerFactory.getLogger(FirewallStorage.class);

	private OFMFirewall manager;

	public RESTOperationApi(OFMFirewall manager) {
		this.manager = manager;
	}

	@Override
	public void handle(Request request, Response response) {

		String op = (String) request.getAttributes().get("op");

		String result = null;

		if (op.toLowerCase().equals("storagerules")){
			// create an object mapper.
			ObjectMapper om = new ObjectMapper();
			om.registerModule( new OFFirewallRuleReplySerializerModule() );

			try {
				result = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(
						manager.getFirewallStorage().getFirewallEntryTable().values() );
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		else if (op.toLowerCase().equals("status")){
			if (manager.isEnabled())
				result = "{\"result\" : \"firewall enabled\"}";
			else
				result = "{\"result\" : \"firewall disabled\"}";
		} 

		else if (op.toLowerCase().equals("enable")){
			manager.enableFirewall(true);
			result = "{\"status\" : \"success\", \"details\" : \"firewall running\"}";
		}

		else if (op.toLowerCase().equals("disable")){
			manager.enableFirewall(false);
			result = "{\"status\" : \"success\", \"details\" : \"firewall stopped\"}";
		}

		else if (op.toLowerCase().equals("get-subnet-mask")){
			result = "{\"subnet-mask\" : \"" + manager.getSubnetMask() + "\"}"; 
		}

		else if (op.toLowerCase().equals("set-subnet-mask")){
			String entityText = request.getEntityAsText();
			entityText = entityText.replaceAll("[\']", "");

			try {
				manager.setSubnetMask(FirewallRule.jsonToSubnetMask(entityText));
				result = "{\"status\" : \"success\", \"details\" : \"subnet-mask is set\"}";
			}
			catch (IOException e) {
				logger.error("Error parsing subnet-mask: {}, {}", entityText, e);
				return;
			}
		}

		else {
			result = "{\"status\" : \"failure\", \"details\" : \"invalid operation\"}";
		}

		response.setEntity(result, MediaType.APPLICATION_JSON);
	}
}
