package etri.sdn.controller.module.staticentrymanager;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.projectfloodlight.openflow.protocol.OFFlowModCommand;
import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.IService;
import etri.sdn.controller.Main;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;

/**
 * This class implements the static flow entry manager module.
 * 
 * @author jshin
 *
 */
public class OFMStaticFlowEntryManager extends OFModule implements IStaticFlowEntryService {

	/**
	 * if this value is false, the static flow entry manager works 
	 * without persistent database. 
	 */
	public boolean dbSupported = true;

	private StaticFlowEntryStorage flowEntryStorage;
	private OFMStorageManager flowEntryDB;
	private String dbName;
	private String collectionName;

	private OFProtocol protocol;

	protected OFProtocol getProtocol() {
		return protocol;
	}

	protected OFMStorageManager getDB(){
		return flowEntryDB;
	}

	protected String getDbName(){
		return dbName;
	}

	protected String getCollectionName(){
		return collectionName;
	}

	/**
	 * Writes the OF message to the dedicated switch.
	 * 
	 * @param sw switch DPID of String format in 00:00:00:00:00:00:00:01 notation
	 * @param message the OFMessage to write
	 */
	private boolean writeOFMessageToSwitch(String dpid, OFMessage message) {
		BigInteger bi = new BigInteger(dpid.replaceAll(":", ""), 16);
		IOFSwitch sw = getController().getSwitch(bi.longValue());
		if (sw == null) {
			return false;
		}
		else {
			return sw.getConnection().write(message);
		}
	}

	/*
	 * OFModule methods
	 */
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		List<Class<? extends IService>> ret = new LinkedList<Class<? extends IService>>();
		ret.add(IStaticFlowEntryService.class);
		return ret;
	}
	
	/**
	 * Initialize this module and check the connection to the persistent database.
	 * If the connection failed, this module starts without the database.
	 * This module does not use registerFilter
	 * because it does not process PACKET_IN messages.
	 */
	@Override
	protected void initialize() {

		protocol = getController().getProtocol();

		flowEntryStorage = new StaticFlowEntryStorage(this, "StaticFlowEntryStorage");

		if (dbSupported) {
			TorpedoProperties conf = TorpedoProperties.loadConfiguration();
			flowEntryDB = (OFMStorageManager) getModule(IStorageService.class);
			dbName = conf.getString("storage-default-db");
			collectionName = flowEntryStorage.getName();
			
			//check the DB connection, and start without DB if connection failed.
			if (!getDB().isConnected()) {
				dbSupported = false;
			}
		}
		
		if (dbSupported) {
			flowEntryStorage.loadFlowModsFromDB();
			
			if (Main.debug) {
				flowEntryStorage.printDB();
			}
		}
	}

	/**
	 * Does nothing in this module.
	 */
	@Override
	protected boolean handleHandshakedEvent(
			Connection conn,
			MessageContext context) {
		return true;
	}

	/**
	 * Does nothing in this module.
	 */
	@Override
	protected boolean handleMessage(
			Connection conn,
			MessageContext context,
			OFMessage msg,
			List<OFMessage> outgoing) {
		return true;
	}

	/**
	 * Does nothing in this module.
	 */
	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	/**
	 * Returns the array of OFModel. 
	 */
	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.flowEntryStorage };
	}

	/* 
	 * IStaticFlowEntryService Methods.
	 */
	@Override
	public void addFlow(String name, Map<String, Object> entry, String dpid) throws StaticFlowEntryException {
		//if the entry that has same name exists, remove it
		if (flowEntryStorage.getFlowModMap().containsKey(name)) {
			deleteFlow(name);
		}

		BigInteger bi = new BigInteger(dpid.replaceAll(":", ""), 16);
		IOFSwitch sw = getController().getSwitch(bi.longValue());
		if (sw == null) {
			throw new StaticFlowEntryException("No switch exists: " + dpid);
		}

		OFMessage message = StaticFlowEntry.makeFlowMod(
				sw, 
				OFFlowModCommand.ADD, 
				entry);

		if (!writeOFMessageToSwitch(dpid, message)) {
			throw new StaticFlowEntryException("Cannot write to switch: " + dpid);
		}

		if (dbSupported) {
			if (!flowEntryStorage.insertDBEntry(flowEntryDB, dbName, collectionName, entry)) {
				throw new StaticFlowEntryException("Cannot write to DB: " + entry);
			}
		}
		flowEntryStorage.addEntryToIndices(dpid, name);
		flowEntryStorage.getFlowModMap().put(name, entry);

		if (dbSupported) {
			if (Main.debug) {
				flowEntryStorage.printDB();
			}
		}
	}

	@Override
	public void deleteFlow(String name) throws StaticFlowEntryException {
		String dpid = flowEntryStorage.getFlowModNameToDpidIndex().get(name);
		if (dpid == null) {
			throw new StaticFlowEntryException("Inconsistent internal state: no switch has rule " + name);
		}

		BigInteger bi = new BigInteger(dpid.replaceAll(":", ""), 16);
		IOFSwitch sw = getController().getSwitch(bi.longValue());
		if (sw == null) {
			throw new StaticFlowEntryException("No switch exists: " + dpid);
		}

		OFMessage message = StaticFlowEntry.makeFlowMod(
				sw,
				OFFlowModCommand.DELETE_STRICT,
				flowEntryStorage.getFlowModMap().get(name));

		if (!writeOFMessageToSwitch(dpid, message)) {
			throw new StaticFlowEntryException("Cannot write to switch: " + dpid);
		}

		if (dbSupported) {
			if (!flowEntryStorage.deleteDBEntry(flowEntryDB, dbName, collectionName, name)) {
				throw new StaticFlowEntryException("Cannot write to db: " + name);
			}
		}
		flowEntryStorage.deleteEntryFromIndices(name);
		flowEntryStorage.getFlowModMap().remove(name);

		if (dbSupported) {
			if (Main.debug) {
				flowEntryStorage.printDB();
			}
		}
	}

	@Override
	public void deleteFlowsForSwitch(String dpid) throws StaticFlowEntryException {
		Set<String> nameset = flowEntryStorage.getDpidToFlowModNameIndex().get(dpid);

		for (String name : nameset) {
			deleteFlow(name);
		}
	}

	@Override
	public void deleteAllFlows() throws StaticFlowEntryException {
		Set<String> nameset = flowEntryStorage.getFlowModNameToDpidIndex().keySet();

		for (String name : nameset) {
			deleteFlow(name);
		}
	}

	@Override
	public Map<String, Map<String, Object>> getAllFlows() {
		return flowEntryStorage.getFlowModMap();
	}

	@Override
	public Map<String, Map<String, Object>> getFlowsForSwitch(String dpid) {
		Map<String, Map<String, Object>> flows = new HashMap<String, Map<String, Object>>();
		Set<String> names = flowEntryStorage.getDpidToFlowModNameIndex().keySet();

		for (String name : names) {
			flows.put(dpid, flowEntryStorage.getFlowModMap().get(name));
		}

		return flows;
	}

	@Override
	public void reloadFlowsToSwitch() throws StaticFlowEntryException {
		flowEntryStorage.reloadFlowModsToSwitch();
	}

}
