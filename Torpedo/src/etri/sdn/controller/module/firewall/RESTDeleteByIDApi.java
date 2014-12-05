package etri.sdn.controller.module.firewall;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTDeleteByIDApi extends Restlet {

	// create logger
	private static final Logger logger = LoggerFactory.getLogger(FirewallStorage.class);

	private OFMFirewall manager;

	public RESTDeleteByIDApi(OFMFirewall manager) {
		this.manager = manager;
	}

	@Override
	public void handle(Request request, Response response) {

		String result = null;
		String status = null;

		String ruleId = (String) request.getAttributes().get("ruleid");

		if ( manager.getFirewallStorage().getFirewallEntryTable().getFirewallEntry(ruleId) != null ) {
			manager.deleteRule( Integer.parseInt(ruleId) );
			status = "Rule deleted";
		} else {
			logger.error("Error! Can't delete, a rule with ID {} doesn't exist.", ruleId);
			status = "Fail to delete rule";
		}

		result = "{\"status\" : \"" + status + "\"}";
		response.setEntity(result, MediaType.APPLICATION_JSON);
	}
}
