package etri.sdn.controller.module.ml2;

import java.util.Arrays;

import etri.sdn.controller.OFModel;

class NetworkConfiguration extends OFModel {

	private OFMOpenstackML2Connector parent = null;
	private RESTApi[] apis = null;
	
	public NetworkConfiguration(OFMOpenstackML2Connector parent) 
	{
		this.parent = parent;
		this.apis = Arrays.asList(
			new RESTApi(RestCreateNetwork.URI, new RestCreateNetwork(this)),
			new RESTApi(RestCreatePort.URI, new RestCreatePort(this)),
			new RESTApi(RestCreateSubnet.URI, new RestCreateSubnet(this))
		).toArray( new RESTApi[0] );
	}
	
	OFMOpenstackML2Connector getModule() {
		return this.parent;
	}
	
	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}

}
