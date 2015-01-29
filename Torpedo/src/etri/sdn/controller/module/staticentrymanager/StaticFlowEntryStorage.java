package etri.sdn.controller.module.staticentrymanager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.StorageException;

/**
 * This class implements the storage-related functions to manage static flow entries.
 * The main data structure is Map<String, Map<String, Object>> flowModMap that
 * stores static flow entries, and it has two indices of dpidToFlowModNameIndex
 * and flowModNameToDpidIndex.
 * Only the main storage flowModMap is saved in the persistent database.
 * 
 * We provide four types of REST API, that are list, clear, add and delete.
 * the add and delete follows the input format of HTTP.
 * 
 * @author jshin
 *
 */
public class StaticFlowEntryStorage extends OFModel{

	private final static Logger logger = OFMStaticFlowEntryManager.logger;

	public OFMStaticFlowEntryManager manager;

	private String name;

	/**
	 * map <flowmod name, map<field name, field value>>
	 */
	private HashMap<String, Map<String, Object>> flowModMap;

	/**
	 * map <dpid, set<flowmod name>>
	 */
	private Map<String, Set<String>> dpidToFlowModNameIndex;

	/**
	 * map <flowmod name, dpid>, reverse index
	 */
	private Map<String, String> flowModNameToDpidIndex;

	private RESTApi[] apis;

	StaticFlowEntryStorage(OFMStaticFlowEntryManager manager, String name) {
		this.manager = manager;
		this.name = name;
		flowModMap = new HashMap<String, Map<String, Object>>();
		dpidToFlowModNameIndex = new HashMap<String, Set<String>>();
		flowModNameToDpidIndex = new HashMap<String, String>();

		initRestApis();
	}

	/**
	 * Initialize REST API list.
	 */
	private void initRestApis() {

		/**  
		 * Array of RESTApi objects. 
		 * Each objects represent a REST call handler routine bound to a specific URI.
		 */
		RESTApi[] tmp = {
				new RESTApi("/wm/staticflowentry/{dpid}/json",
						new RestSwitchApi(manager)),
			/*
			 * LIST example
			 * OF1.0,1.3:	curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/list/all/json
			 * 				curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/list/00:00:00:00:00:00:00:01/json
			 */

			/*
			 * CLEAR example
			 * OF1.0,1.3:	curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/clear/all/json
			 * 				curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/clear/00:00:00:00:00:00:00:01/json
			 */
			new RESTApi(
				"/wm/staticflowentry/clear/{switch}/json",
				new RESTClearApi(manager) 
			),

			/*
			 * RELOAD example
			 * OF1.0,1.3:	curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/reload/all/json
			 * 				curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/reload/00:00:00:00:00:00:00:01/json
			 */
			new RESTApi(
				"/wm/staticflowentry/reload/{switch}/json",
				new RESTReloadApi(manager)
			),

			/*
			 * ADD example
			 * OF1.0,1.3:	curl http://{controller_ip}:{rest_api_port}/wm/staticflowentry/list/all/json
			 * 				curl -d '{"switch":"00:00:00:00:00:00:00:01","name":"s1","priority":"101","eth_type":"0x0800","ipv4_src":"10.0.0.1","ipv4_dst":"10.0.0.2","active":"true","instructions":[{"apply_actions":[{"output":"2"}]}]}' http://{controller_ip}:{rest_api_port}/wm/staticflowentry/json
			 * 				curl -d '{"switch":"00:00:00:00:00:00:00:02","name":"s20","priority":"1001","eth_type":"0x0806","ipv4_dst":"10.0.0.4","active":"true","instructions":[{"apply_actions":[{"output":"3"}]}]}' http://{controller_ip}:{rest_api_port}/wm/staticflowentry/json
			 * 				curl -d '{"switch":"00:00:00:00:00:00:00:01","name":"s1","priority":"1001","eth_type":"0x0800","ipv4_dst":"10.0.0.3","active":"true","instructions":[{"apply_actions":[{"set_field":{"ipv4_dst":"10.0.0.2"}},{"output":"2"}]}]}' http://{controller_ip}:{rest_api_port}/wm/staticflowentry/json
			 */
			new RESTApi(
				"/wm/staticflowentry/json",
				new RESTPostApi(manager)
			),
			
			/*
			 * DELETE by name example
			 * OF1.0,1.3:	curl -X DELETE -d '{"name":"s1"}' http://{controller_ip}:{rest_api_port}/wm/staticflowentry/json
			 * 
			 * This object is an additional implements REST handler routines (i.e. RFC2616).
			 * According to RFC2616, DELETE method cannot have data fields.
			 */
			new RESTApi(
				"/wm/staticflowentry/delete/{name}/json",
				new RESTDeleteByNameApi(manager)
			)
		};

		this.apis = tmp;
	}

	public OFMStaticFlowEntryManager getManager() {
		return this.manager;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Map<String, Map<String, Object>> getFlowModMap() {
		return flowModMap;
	}

	public Map<String, Map<String, Object>> getFlowModMap(String dpid) {
		Set<String> names = null;
		if ( !dpid.equals("all") ) {
			names = this.getDpidToFlowModNameIndex().get(dpid);
		} else {
			names = this.getFlowModMap().keySet();
		}

		Map<String, Map<String, Object>> flowmods = new HashMap<String, Map<String, Object>>();
		if ( names != null ) { 
			for ( String name : names ) {
				flowmods.put(name, this.getFlowModMap().get(name));
			}
		}

		return flowmods;
	}

	public Map<String, Set<String>> getDpidToFlowModNameIndex() {
		return dpidToFlowModNameIndex;
	}

	public Map<String, String> getFlowModNameToDpidIndex() {
		return flowModNameToDpidIndex;
	}

	/**
	 * Copies all entry of the persistent database into the memory storage 
	 * and switches at initial booting stage. When the controller is rebooted, 
	 * all static entries need to be reloaded for consistency.
	 */
	public void loadFlowModsFromDB() {
		Collection<Map<String, Object>> entries = null;

		entries = getAllDBEntries (
				manager.getDB(), 
				manager.getDbName(), 
				manager.getCollectionName() );

		// Load rules to memory from DB.
		for (Map<String, Object> entry : entries) {
			getFlowModMap().put((String) entry.get("name"), entry);
		}

		buildIndices();
	}

	/**
	 * OFMStaticFlowEntryManager supports the persistent database. 
	 * This reload flows to the particular switch. This method is called by RESTApi.
	 * 
	 * @param dpid the switch dpid in 00:00:00:00:00:00:00:01 notation
	 * @throws StaticFlowEntryException
	 */
	public void reloadFlowModsToSwitch(String dpid) throws StaticFlowEntryException {
		//Avoiding ConcurrentModificationException
		Map<String, Map<String, Object>> entries = new HashMap<String, Map<String, Object>>();
		for ( String flowName : this.dpidToFlowModNameIndex.get(dpid) ) {
			entries.put(flowName, getFlowModMap().get(flowName));
		}

		for (String flowName : entries.keySet()) {
			BigInteger bi = new BigInteger(((String) entries.get(flowName).get("switch")).replaceAll(":", ""), 16);
			if (getManager().getController().getSwitch(bi.longValue()) != null) {
				// This method will throw exception when flow add failed.
				getManager().addFlow(
						flowName, 
						entries.get(flowName), 
						(String) entries.get(flowName).get("switch"));

				logger.debug("Entry loaded to switch: {}", getFlowModMap().get(flowName));
			}
		}
	}

	/**
	 * OFMStaticFlowEntryManager supports the persistent database. 
	 * This reload all flows to proper switches. This method is called by RESTApi.
	 * 
	 * @throws StaticFlowEntryException
	 */
	public void reloadAllFlowModsToSwitch() throws StaticFlowEntryException {
		//Avoiding ConcurrentModificationException
		List<String> dpidList = new LinkedList<>( dpidToFlowModNameIndex.keySet() );
		for (String dpid : dpidList) {
			reloadFlowModsToSwitch(dpid);
		}
	}

	/**
	 * Builds dpidToFlowModNameIndex when the controller is rebooted.
	 * 
	 * @param flowModMap the memory storage which stores all flow entries
	 */
	public void buildDpidToFlowModNameIndex(Map<String, Map<String, Object>> flowModMap) {
		Set<String> nameset = null;

		for (String flowName : getFlowModMap().keySet()) {
			String dpid = getFlowModMap().get(flowName).get("switch").toString();
			if (getDpidToFlowModNameIndex().containsKey(dpid)) {
				nameset = getDpidToFlowModNameIndex().get(dpid);
			}
			else {
				nameset = new HashSet<String>();
			}
			nameset.add(flowName);
			getDpidToFlowModNameIndex().put(dpid, nameset);
		}
	}

	/**
	 * Builds FlowModNameToDpidIndex when the controller is rebooted.
	 * 
	 * @param flowModMap the memory storage which stores all flow entries
	 */
	public void buildFlowModNameToDpidIndex(Map<String, Map<String, Object>> flowModMap) {
		for (String flowName : getFlowModMap().keySet()) {
			getFlowModNameToDpidIndex().put(flowName, getFlowModMap().get(flowName).get("switch").toString());
		}
	}

	/**
	 * Calls buildDpidToFlowModNameIndex() and buildFlowModNameToDpidIndex().
	 */
	public void buildIndices() {
		buildDpidToFlowModNameIndex(flowModMap);
		buildFlowModNameToDpidIndex(flowModMap);
	}

	/**
	 * Deletes flow entry by name from dpidToFlowModNameIndex and flowModNameToDpidIndex.
	 * 
	 * @param name the name of flow entry to delete
	 */
	public void deleteEntryFromIndices(String name) {
		String dpid = getFlowModNameToDpidIndex().get(name);
		Set<String> nameset = getDpidToFlowModNameIndex().get(dpid);
		if ( nameset != null ) {
			nameset.remove(name);
			if ( nameset.isEmpty() ) {
				getDpidToFlowModNameIndex().remove(dpid);
			}
			else {
				getDpidToFlowModNameIndex().put(dpid, nameset);
			}

			getFlowModNameToDpidIndex().remove(name);

			logger.debug("dpid to flowname: {}", getDpidToFlowModNameIndex().toString());
			logger.debug("flowname to dpid: {}", getFlowModNameToDpidIndex().toString());
		}
	}

	/**
	 * Inserts flow entry to dpidToFlowModNameIndex and flowModNameToDpidIndex.
	 * 
	 * @param dpid the switch dpid in 00:00:00:00:00:00:00:01 notation
	 * @param name the name of flow entry to insert
	 */
	public void addEntryToIndices(String dpid, String name) {
		Set<String> nameset = null;
		if ( getDpidToFlowModNameIndex().get(dpid) == null ) {
			nameset = new HashSet<String>();
		}
		else {
			nameset = getDpidToFlowModNameIndex().get(dpid);
		}

		nameset.add(name);
		getDpidToFlowModNameIndex().put(dpid, nameset);

		getFlowModNameToDpidIndex().put(name, dpid);

		logger.debug("dpid to flowname: {}", getDpidToFlowModNameIndex().toString());
		logger.debug("flowname to dpid: {}", getFlowModNameToDpidIndex().toString());
	}

	/**
	 * Compares entries of the memory storage and the persistent database.
	 * This method is only used for debug.
	 * 
	 */
	public void printDB () {
		if ( !manager.getDB().isConnected()) {
			return;
		}

		List<Map<String, Object>> entry = (List<Map<String, Object>>) getAllDBEntries
				(manager.getDB(), manager.getDbName(), manager.getCollectionName() );

		logger.debug("StaticEntry DB : {}", entry.toString());
		logger.debug("StaticEntry Mem: {}", getFlowModMap());
	}

	/**
	 * Inserts flow entry to the persistent database.
	 * 
	 * @param db the OFMStorageManager object
	 * @param dbName the DB name
	 * @param collectionName the collection name
	 * @param entry a flow entry to insert
	 * 
	 * @return true when inserted successfully, false otherwise
	 */
	public boolean insertDBEntry (IStorageService db, String dbName, String collectionName, Map<String, Object> entry) {
		if ( ! db.isConnected() ) {
			return true;
		}

		try {
			db.insert(dbName, collectionName, entry);
		} catch (StorageException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Deletes flow entry from the persistent database.
	 * 
	 * @param db the OFMStorageManager object
	 * @param dbName the DB name
	 * @param collectionName the collection name
	 * @param name a flow entry name of String format
	 * 
	 * @return true when deleted successfully, false otherwise
	 */
	public boolean deleteDBEntry (IStorageService db, String dbName, String collectionName, String name) {
		if ( !db.isConnected() ) {
			return true;
		}

		Map<String, Object> entry = getDBEntry (db, dbName, collectionName, name);
		if (entry == null) {
			logger.debug("No such entry exists: name={} ", name);
			return false;
		}

		try {
			db.delete(dbName, collectionName, entry);
		} catch (StorageException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Gets the flow entry by name from the persistent database.
	 * 
	 * @param db the OFMStorageManager object
	 * @param dbName the DB name
	 * @param collectionName the collection name
	 * @param name a flow entry name to retrieve
	 * 
	 * @return flow entry of Map<String, Object> or null
	 */
	public Map<String, Object> getDBEntry (IStorageService db, String dbName, String collectionName, String name) {
		List<Map<String, Object>> entries = (List<Map<String, Object>>) getAllDBEntries (db, dbName, collectionName); 
		Iterator<Map<String, Object>> itr = entries.iterator();

		while (itr.hasNext()){
			Map<String, Object> entry = itr.next();

			if (entry.get("name").equals(name)) {
				return entry;
			}
		}

		return null;
	}

	/**
	 * Gets all flow entries from the persistent database.
	 * 
	 * @param db the OFMStorageManager object
	 * @param dbName the DB name
	 * @param collectionName the collection name
	 * 
	 * @return all flow entries of Collection<Map<String, Object>> 
	 */
	public Collection<Map<String, Object>> getAllDBEntries (IStorageService db, String dbName, String collectionName) {
		List<Map<String, Object>> entries = null;

		if ( ! db.isConnected() ) {
			return Collections.emptyList();
		}

		try {
			entries = db.retrieveAll(dbName, collectionName);
		} catch (StorageException e) {
			e.printStackTrace();
		}

		Collection<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		if ( entries != null ) {
			for (Map<String, Object> entry : entries) {
				HashMap<String, Object> result = null;
				result = (HashMap<String, Object>) entry;
				results.add(result);
			}
		}

		return results;
	}

	/**
	 * Returns the list of RESTApi objects
	 * 
	 * @return array of all RESTApi objects 
	 */
	@Override
	public RESTApi[] getAllRestApi() {
		return apis;
	}
}
