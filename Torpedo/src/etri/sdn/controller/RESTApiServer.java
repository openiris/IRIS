package etri.sdn.controller;

import org.restlet.Application;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

/**
 * This is a REST API server application class 
 * required to create a REST API Server (({@link RESTApiServer}).
 * 
 * @author bjlee
 *
 */
class RestApiServerApplication extends Application {
	private Router router;
	
	/**
	 * Constructor.
	 */
	public RestApiServerApplication() {
		// does nothing.
	}
	
	/**
	 * return the Router object associated with this application.
	 * 
	 * @return 	{@link org.restlet.routing.Router} object.
	 * 			The router object is created when {@link #createInboundRoot()} is called.
	 */
	public Router getRouter() {
		return this.router;
	}
	
	/**
	 * create an inbound root object.
	 * The type of the object is Router, which is a subclass of Restlet.
	 */
	@Override 
	public Restlet createInboundRoot() {
		return (router = new Router(getContext()));
	}
}

/**
 * A REST API server class. 
 * By creating an object of this class and calling {@link #start},
 * You can start an HTTP server that is able to handle REST requests.
 * 
 * @author bjlee
 *
 */
public class RESTApiServer {
	
	/**
	 * REST application.
	 */
	private RestApiServerApplication application;
	
	/**
	 * Constructor to create a REST API server object.
	 */
	public RESTApiServer() {
		application = new RestApiServerApplication();
	}
	
	/**
	 * Start the RESTApiServer.
	 */
	public void start(int port) {
		
		// Start listening for REST requests
        try {
            final Component component = new Component();
            component.getServers().add(Protocol.HTTP, port);
            component.getClients().add(Protocol.FILE);
            component.getDefaultHost().attach(application);
            component.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * Register models to the RESTApiServer object. 
	 * This method calls {@link OFModel#getAllRestApi()} to retrieve 
	 * RESTApi objects from each model. 
	 * Those objects are actually registered to the RESTApiServer object. 
	 * 
	 * @param models
	 */
	public void registerOFModels(OFModel[] models) {
		for ( int i = 0 ; i < models.length; ++i ) {
			OFModel.RESTApi[] apis = models[i].getAllRestApi();
			for ( int j = 0; j < apis.length; ++j ) {
				if ( apis[j] instanceof OFModel.RESTWebUI ) {
					String currentDir = System.getProperty("user.dir");
					Context ctx = application.getRouter().getContext();
					if ( apis[j].getURI() == null ) {
						application.getRouter().attachDefault(
							new Directory(
								ctx, 
								String.format("file:///%s/web/", currentDir)
							)
						);
					}
					else {
						application.getRouter().attach(
								apis[j].getURI(),
								new Directory(
									ctx, 
									String.format("file:///%s/web/", currentDir)
								)
							);
					}
					ctx.setClientDispatcher(new Client(ctx, Protocol.FILE));
				} else {
					application.getRouter().attach(apis[j].getURI(),apis[j].getAPI());
				}
			}
		}
	}
}
