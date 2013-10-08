package etri.sdn.controller.protocol.io;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.openflow.protocol.OFMessage;

import etri.sdn.controller.IOFTask;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.VersionAdaptor;

/**
 * This is an interface that every controller instance should implement.
 * @author bjlee
 *
 */
public interface IOFHandler {
	
	/**
     * The role of the controller as used by the OF 1.2 and OVS failover and
     * load-balancing mechanism.
     */
    public static enum Role { EQUAL, MASTER, SLAVE };
    
    /**
     * Get the current role of the controller
     */
    public abstract Role getRole();
	
	/**
	 * return the switch identifiers.
	 * @return this function returns a hashset object.
	 */
    public abstract Set<Long> getSwitchIdentifiers();
	public abstract Collection<IOFSwitch> getSwitches();
	public abstract IOFSwitch getSwitch(long id);
	public abstract void addSwitch(long id, IOFSwitch sw);
	public VersionAdaptor getVersionAdaptor(byte version);
	
	public abstract void registerProtocolServer(IOFProtocolServer server);
	
	public abstract void scheduleTask(final IOFTask task, final long after);
	
	public abstract boolean handleReadEvent(Connection conn, List<OFMessage> msgs);
	public abstract boolean handleDisconnectEvent(Connection conn);
	public abstract boolean handleConnectedEvent(final Connection conn);
	
	/**
	 * If the connection is need to be handled by this handler, 
	 * it returns true. Otherwise, returns false. 
	 * @param conn
	 * @return
	 */
	public abstract boolean isMySwitch(Connection conn);
	
	/**
	 * If the messages are need to be handled by this controller,
	 * it returns true. Otherwise, returns false.
	 * @param conn
	 * @param msgs
	 * @return
	 */
	public abstract boolean isMyFlow(Connection conn, List<OFMessage> msgs);

	public abstract void addModule(OFModule module);
	
	public abstract Collection<OFModule> getModules();
	
	public abstract OFModel[] getModels();

	public abstract String getConcatenatedModuleNames();
	
	public abstract String[] getModuleNames();
}
