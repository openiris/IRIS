package etri.sdn.controller.module.ml2;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;

class RestCreateSubnet extends Restlet {

	static String URI = "/wm/ml2/subnets/{uuid}";
	
	private NetworkConfiguration parent = null;
	
	RestCreateSubnet(NetworkConfiguration parent) {
		 this.parent = parent;
	}
	
	NetworkConfiguration getModel() {
		return this.parent;
	}
	
	@Override
	public void handle(Request request, Response response) {
		
	}
}
