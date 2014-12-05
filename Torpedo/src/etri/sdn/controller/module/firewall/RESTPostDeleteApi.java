package etri.sdn.controller.module.firewall;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPostDeleteApi extends Restlet {

	// create logger
	private static final Logger logger = LoggerFactory.getLogger(FirewallStorage.class);

	private OFMFirewall manager;

	public RESTPostDeleteApi(OFMFirewall manager) {
		this.manager = manager;
	}

	@Override
	public void handle(Request request, Response response) {

		Method m = request.getMethod();
		String result = null;
		String status = null;
		boolean exists = false;
		FirewallRule inRule;

		if (m == Method.GET){		
			// create an object mapper.
			ObjectMapper om = new ObjectMapper();

			try {
				result = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString( manager.getRules() );
			} catch ( IOException e ) {
				e.printStackTrace();
				return;
			}
		}

		else if (m == Method.POST){
			Iterator<FirewallRule> iter = manager.getRules().iterator();

			String entityText = request.getEntityAsText();
			entityText = entityText.replaceAll("[\']", "");

			try {
				inRule = FirewallRule.jsonToFirewallRule(entityText);
			} catch (IOException e) {
				logger.error("Error parsing firewall rule: {}, {}", entityText, e);
				e.printStackTrace();
				return;
			}

			while (iter.hasNext()) {
				FirewallRule r = iter.next();
				if ( inRule.isSameAs(r) ){
					exists = true;
					logger.error("Error! A similar firewall rule already exists.");
					break;
				}
			}

			if ( exists == false ){
				// add rule to firewall
				manager.addRule(inRule);
				status = "Rule added";
			}

			result = "{\"status\" : \"" + status + "\"}";
		}

		else if (m == Method.DELETE){
			Iterator<FirewallRule> iter = manager.getRules().iterator();

			String entityText = request.getEntityAsText();

			// Clear all rules.
			if (entityText == null) {
				manager.clearRules();
				status = "All rules are deleted.";
			}
			// Delete one rule based on input rule id.
			else {
				entityText = entityText.replaceAll("[\']", "");
				//System.out.println(entityText);

				try {
					inRule = FirewallRule.jsonToFirewallRule(entityText);
				} catch (IOException e) {
					logger.error("Error parsing firewall rule: {}, {}", entityText, e);
					e.printStackTrace();
					return;
				}

				while (iter.hasNext()) {
					FirewallRule r = iter.next();
					if ( r.ruleid == inRule.ruleid){
						exists = true;
						break;
					}
				}

				if ( !exists ){
					logger.error("Error! Can't delete, a rule with ID {} doesn't exist.", inRule.ruleid);
					status = "Fail to delete rule";
				} else {
					// delete rule from firewall
					manager.deleteRule( inRule.ruleid );
					status = "Rule deleted";
				}
			}

			result = "{\"status\" : \"" + status + "\"}";
		}

		response.setEntity(result, MediaType.APPLICATION_JSON);
	}
}
