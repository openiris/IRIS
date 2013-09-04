package etri.sdn.controller;

import org.restlet.Restlet;


/**
 * This is a class that represents a data structure that holds all the 
 * important data of a module. Therefore, a module normally has a 
 * OFModel object as a member, which holds all the data. 
 * As this class is a major extension point that you can define 
 * the REST API for your down module and normally the REST API 
 * should be very close to such kind of data, this way of modeling 
 * is very natural, we think. 
 * 
 * Further, we are planning to integrate NoSQL function with this class.
 * Currently the NoSQL(MongoDB)-related function are in a separate module
 * {@link etri.sdn.controller.module.storage.OFMStorageManager}. 
 * 
 * @author bjlee
 *
 */
public abstract class OFModel {
	
	/**
	 * This is a superclass of all REST API implementations. 
	 * 
	 * @author bjlee
	 *
	 */
	public class RESTApi {
		
		private String uri;
		private Restlet api;
		
		/**
		 * Constructor to build a RESTApi object. 
		 * You can find the example usage of this constructor in
		 * {@link etri.sdn.controller.module.statemanager.State}, 
		 * which is an {@link OFModel} object.
		 * @param uri
		 * @param api
		 */
		public RESTApi(String uri, Restlet api) {
			this.uri = uri;
			this.api = api;
		}
		
		/**
		 * returns the type of the RESTApi object. 
		 * @return String that represents the RESTApi object type.
		 *         Other than {@link RESTWebUI}, this method always returns "api".
		 */
		public String getType() { return "api"; }
		
		/**
		 * returns a URI associated with this RESTApi object.
		 * @return
		 */
		public String getURI() { return this.uri; }
		
		/**
		 * returns a Restlet object that actually implements the api.
		 * @return
		 */
		public Restlet getAPI() { return this.api; }
	}
	
	/**
	 * This is a class for the RESTWebUI. As this class is only for the UI,
	 * further extension of this class is strictly prohibited. 
	 * 
	 * @author bjlee
	 *
	 */
	public final class RESTWebUI extends RESTApi {

		public RESTWebUI(String uri) {
			super(uri, null);
		}

		/**
		 * this returns the type of this RESTApi.
		 */
		@Override
		public String getType() { return "ui"; }
	}

	/**
	 * Returns the URIs mapped to this Restlet object.
	 * A module class that wants to export its function 
	 * to outer world with REST API should implement to this method
	 * to return the array of RESTApi object.
	 * 
	 * @return String of the form "/users/{user}/orders/{order}". just consult the 
	 *         Restlet API documentation.
	 */
	abstract public RESTApi[] getAllRestApi();
	
}
