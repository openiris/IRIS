package etri.sdn.controller.protocol.io;

/**
 * This interface is a generic interface that a protocol server such as TcpServer should implement.
 * @author bjlee
 *
 */
public interface IOFProtocolServer {
    
	/**
	 * register controller to the {@link IOFProtocolServer} instance.
	 * 
	 * @param controller
	 */
	public void registerController(IOFHandler controller);
	
	/**
	 * deregister controller from the {@link IOFProtocolServer} instance.
	 * @param controller
	 */
	public void deregisterConroller(IOFHandler controller);
	
	/**
	 * shutdown the IOFProtocolServer operation.
	 */
	public void shutdown();
	
	/**
	 * wake up the IOFProtocolServer from its suspended status. 
	 * This must be called after calling {@link #start()}.
	 */
	public void wakeup();
	
	/**
	 * start the protocol server.
	 */
	public void start();
}
