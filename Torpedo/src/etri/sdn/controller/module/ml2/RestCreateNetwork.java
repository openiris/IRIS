package etri.sdn.controller.module.ml2;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;

class RestCreateNetwork extends Restlet {

	static String URI = "/vm/ml2/networks/{uuid}";
	private NetworkConfiguration parent = null;
	
	RestCreateNetwork(NetworkConfiguration parent) {
		this.parent = parent;
	}
	
	NetworkConfiguration getModel() {
		return this.parent;
	}
	
	@Override
	public void handle(Request request, Response response) {
		
	}
}
