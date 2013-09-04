package etri.sdn.controller.module.ui;

import etri.sdn.controller.OFModel;

public class WebUserInterface extends OFModel {
	
	@SuppressWarnings("unused")
	private OFMUserInterface manager;
	
	public WebUserInterface(OFMUserInterface manager) {
		this.manager = manager;
	}
	
	private RESTApi[] apis = {
		new RESTWebUI(null)			// default UI used when there is no match for any URI.
	};

	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}

}
