package etri.sdn.controller.module.connectionmonitor;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFEchoRequest;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.IOFTask;
import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFMFilter;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.module.forwarding.ForwardingBase;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;

/**
 * OFMConnectionChecker class checks aliveness of connection to switches.
 * Without this module, Torpedo verifies switch aliveness only with TCP connection.
 * Some application failure on a normal TCP connection cannot be detected in this case.
 * This module is optional, but it sends periodical ECHO_REQUEST to all switches if activated.
 * If there is no responses, it terminates these connections and removes switches.
 * 
 *  @author jshin
 */
public class OFMConnectionMonitor extends OFModule {

	private static final Logger logger = LoggerFactory.getLogger(ForwardingBase.class);

	/*
	 * If the controller does not receive EXPIRE_THRESHOLD times of sequent ECHO_REPLY,
	 * it disconnects the TCP connection of this switch. 
	 * This value is reasonable for the TCP aggregation.
	 */
	private static short EXPIRE_THRESHOLD = 10;
	
	private static short EXPIRE_CHECK_INTERVAL = 3;

	/*
	 * If this value equals 0, this module does not work.
	 */
	private long echoRequestInterval;		// ms

	/*
	 * a map of IOFSwitch and timestamp 
	 */
	Map<IOFSwitch, Long> switchMap;

	/**
	 * Adds or updates switchMap.
	 * 
	 * @param conn
	 */
	private void updateSwitch (Connection conn) {

		IOFSwitch sw = conn.getSwitch();
		if (sw != null) {
			if (switchMap.containsKey(sw)) {
				logger.debug ("[ConnMon] Switch updated: {}", switchMap.get(sw));
			}
			else {
				logger.debug ("[ConnMon] Switch added: {}", Calendar.getInstance().getTime());
			}

			switchMap.put(sw, Calendar.getInstance().getTimeInMillis());
		}
	}

	/**
	 * Removes disconnected switch from switchMap.
	 * 
	 * @param conn
	 */
	private void removeDisconnectedSwitch (Connection conn) {

		IOFSwitch sw = conn.getSwitch();
		switchMap.remove(sw);

		logger.debug("[ConnMon] Switch disconnected: {}", Calendar.getInstance().getTime());
	}

	/**
	 * Checks and removes expired (no response on ECHO_REQUEST) switches if exist.
	 */
	private void removeExpiredSwitches () {

		logger.debug("[ConnMon] Check expired switches: {}", Calendar.getInstance().getTime());

		for (IOFSwitch sw : switchMap.keySet()) {
			if ( (Calendar.getInstance().getTimeInMillis() - switchMap.get(sw)) 
					> (echoRequestInterval * EXPIRE_THRESHOLD) ) {

				logger.debug("[ConnMon] Switch expired: {}", sw);

				// cuts the connection to expired switch.
				if (sw.getConnection() != null) {
					sw.getConnection().close();
					sw.setConnection(null);
				}

				switchMap.remove(sw);
			}
		}
	}

	/**
	 * Periodically sends ECHO_REQUEST to switches.
	 * 
	 * @param sw
	 */
	private void sendEchoRequest (IOFSwitch sw) {

		logger.debug("[ConnMon] Send echo: {}", Calendar.getInstance().getTime());

		OFFactory fac = OFFactories.getFactory(sw.getVersion());
		OFEchoRequest.Builder echoReq = fac.buildEchoRequest();

		sw.getConnection().write(echoReq.build());
	}

	/*
	 * OFModule Methods 
	 */

	@Override
	protected Collection<Class<? extends IService>> services() {
		// no service to implement
		return Collections.emptyList();
	}

	@Override
	protected void initialize() {

		TorpedoProperties conf = TorpedoProperties.loadConfiguration();
		this.echoRequestInterval = Long.valueOf( conf.getString("echo-request-interval") );

		if (this.echoRequestInterval == 0) {
			return;
		}

		logger.info("starting OFMConnectionMonitor with echo request interval of {}ms.", 
				this.echoRequestInterval);

		/*
		 * a map of IOFSwitch and lastseen
		 */
		this.switchMap = new HashMap<IOFSwitch, Long>();

		// Initializes switchMap with current timestamp.
		for (IOFSwitch sw : getController().getSwitches()) {
			switchMap.put(sw, Calendar.getInstance().getTimeInMillis());
		}

		registerFilter(
			OFType.PACKET_IN, 
			new OFMFilter() {
				@Override
				public boolean filter(OFMessage m) {
					return true;
				}
			}
		);

		registerFilter(
			OFType.ECHO_REPLY, 
			new OFMFilter() {
				@Override
				public boolean filter(OFMessage m) {
					return true;
				}
			}
		);

		this.controller.scheduleTask(new IOFTask() {

			@Override
			public boolean execute() {
				for (IOFSwitch sw : switchMap.keySet()) {
					sendEchoRequest(sw);
				}
				return true;
			}

		}, 0, echoRequestInterval);

		this.controller.scheduleTask(new IOFTask() {

			@Override
			public boolean execute() {
				removeExpiredSwitches();
				return true;
			}

		}, 0, echoRequestInterval * EXPIRE_CHECK_INTERVAL);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn, MessageContext context) {

		if (this.echoRequestInterval != 0) {
			updateSwitch (conn);
		}

		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {

		if (this.echoRequestInterval != 0) {
			switch ( msg.getType() ) {
			case PACKET_IN:
				logger.debug("[ConnMon] PACKET_IN received");
				updateSwitch(conn);
				break;
			case ECHO_REPLY:
				logger.debug("[ConnMon] ECHO_REPLY received");
				updateSwitch(conn);
				break;
			default:
				break;
			}
		}

		return true;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {

		if (this.echoRequestInterval != 0) {
			removeDisconnectedSwitch (conn);
		}

		return true;
	}

	@Override
	public OFModel[] getModels() {
		return null;
	}
}
