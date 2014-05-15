package etri.sdn.controller.protocol.io;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.IOFTask;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.OFProtocol;

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
	public OFProtocol getProtocol();
	public IOFProtocolServer getServer();

	public abstract void registerProtocolServer(IOFProtocolServer server);

	public abstract boolean handleReadEvent(Connection conn, List<OFMessage> msgs);
	public abstract boolean handleDisconnectEvent(Connection conn);
	public abstract boolean handleConnectedEvent(final Connection conn);

	public abstract void addModule(OFModule module);

	public abstract Collection<OFModule> getModules();

	public abstract OFModel[] getModels();

	public abstract String getConcatenatedModuleNames();

	public abstract String[] getModuleNames();
	
	/**
	 * Run the given task 'after' the given delay. The period of task repetition is also 'after.'
	 * @param task	Task to run
	 * @param after	delay in executing the task. This is also a task repetition period.
	 */
	public abstract void scheduleTask(final IOFTask task, final long after);

	/**
	 * Run the given task periodically after the given delay.
	 * @param task	Task to run
	 * @param delay	delay in executing the task
	 * @param period	The given task is executed repeatedly after this amount of time
	 */
	public abstract void scheduleTask(final IOFTask task, final long delay, final long period);
}
