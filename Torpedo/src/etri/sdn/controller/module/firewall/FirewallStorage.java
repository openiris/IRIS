package etri.sdn.controller.module.firewall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.Main;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.StorageException;

/**
 * This class is a storage to manage firewall rules.
 * FirewallStorage has FirewallEntryTable class which supports basic 
 * storage operations against a memory storage and a persistent database.
 * 
 * @author jshin
 */
public class FirewallStorage extends OFModel{
	
	// create logger
	private static final Logger logger = LoggerFactory.getLogger(FirewallStorage.class);

	public OFMFirewall manager;

	private String name;

	private FirewallEntryTable firewallEntryTable;

	private RESTApi[] apis;
	
	FirewallStorage (OFMFirewall manager, String name){
		this.manager = manager;
		this.name = name;
		firewallEntryTable = new FirewallEntryTable();

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
			/**
			 * This object implements a REST handler routine for operations of firewall.
			 */
			new RESTApi(
				"/wm/firewall/module/{op}/json",
				new RESTOperationApi(manager)
			),
			
			/**
			 * This object implements REST handler routines for 
			 * retrieving, inserting and deleting firewall rule(s).
			 */
			new RESTApi(
				"/wm/firewall/rules/json",
				new RESTPostDeleteApi(manager)
			),
			
			/**
			 * This object is an additional implements REST handler routines (i.e. RFC2616).
			 * According to RFC2616, DELETE method cannot have data fields.
			 */
			new RESTApi(
				"/wm/firewall/rules/delete/{ruleid}/json",
				new RESTDeleteByIDApi(manager)
			)
		};

		this.apis = tmp;
	}
	
	public  OFMFirewall getManager() {
		return this.manager;
	}

	public void setTableName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public FirewallEntryTable getFirewallEntryTable() {
		return firewallEntryTable;
	}
	
	/**
	 * Copies all entry of the persistent database into the memory storage 
	 * at initial booting stage. When the controller is rebooted, all firewall 
	 * rules need to be loaded into memory storage for consistency.
	 * 
	 * @param entrySet all firewall rules of the persistent database
	 * 
	 */
	public void synchronizeStorage (Collection<Map<String, Object>> entrySet) {
		
		for (Map<String, Object> entry : entrySet){
			firewallEntryTable.insertFirewallEntry(entry.get("ruleid").toString(), entry);
		}

		if ( Main.debug )
			printStorage();
	}
	
	/**
	 * Compares entries of the memory storage and the persistent database.
	 * This method is only used for debug.
	 * 
	 */
	public void printStorage () {
		
		if ( !manager.getStorageInstance().isConnected() ) {
			return;
		}
		
		List<Map<String, Object>> entryList = (List<Map<String, Object>>) getAllDBEntries
				(manager.getStorageInstance(), manager.getDbName(), manager.getCollectionName() );
		
		logger.debug("Firewall DB : {} ", entryList.toString());
		logger.debug("Firewall Mem: {} ", this.firewallEntryTable.getAllFirewallEntries().toString());
	}
	
	/**
	 * Stores a new firewall rule into the persistent database.
	 * 
	 * @param firewallDB a persistent database name
	 * @param dbName the name of the persistent database
	 * @param collectionName the name of the collection for the database
	 * @param rule a firewall rule to insert
	 * 
	 * @return true when inserted
	 */
	public boolean insertDBEntry (IStorageService firewallDB, String dbName, String collectionName, Map<String, Object> rule) {
		
		if ( !firewallDB.isConnected() ) {
			return true;
		}

		try {
			firewallDB.insert(dbName, collectionName, rule);
		} catch (StorageException e) {
			e.printStackTrace();
		}
		
		if ( Main.debug )
			printStorage();
		
		return true;
	}

	/**
	 * Removes the firewall rule from database using key field of ruleid.
	 * This method searches for the target entry from the database first
	 * and deletes the target entry if the rule associated with the given ruleid exists.
	 * 
	 * @param firewallDB a persistent database name
	 * @param dbName the name of the persistent database
	 * @param collectionName the name of the collection for the database
	 * @param ruleid the ruleid of firewall rule to delete
	 * 
	 * @return true when deleted, false when fails to search
	 */
	public boolean deleteDBEntry (IStorageService firewallDB, String dbName, String collectionName, int ruleid) {
		
		if ( !firewallDB.isConnected() ) {
			return true;
		}
		
		Map<String, Object> entry = getDBEntry (firewallDB, dbName, collectionName, ruleid);
		if (entry == null) {
			logger.error("No such rule exists. ruleid: {}", ruleid);
			return false;
		}
		
		try {
			firewallDB.delete(dbName, collectionName, entry);
		} catch (StorageException e) {
			e.printStackTrace();
		}

		if ( Main.debug )
			printStorage();
		
		return true;
	}

	/**
	 * Returns the firewall rule associated with the input ruleid from the database.
	 * 
	 * @param firewallDB a persistent database name
	 * @param dbName the name of the persistent database
	 * @param collectionName the name of the collection for the database
	 * @param ruleid the ruleid of firewall rule to be searched
	 * 
	 * @return the firewall rule associated with the given ruleid, null when fails to search
	 */
	public Map<String, Object> getDBEntry (IStorageService firewallDB, String dbName, String collectionName, int ruleid){
		
		List<Map<String, Object>> entryList = (List<Map<String, Object>>) getAllDBEntries (firewallDB, dbName, collectionName); 
		Iterator<Map<String, Object>> itr = entryList.iterator();

		while (itr.hasNext()){
			Map<String, Object> rule = itr.next();
			
			if (rule.get("ruleid").equals(Integer.toString(ruleid))) {
				return rule;
			}
		}

		return null;
	}
	
	/**
	 * Returns all firewall rules from the database.
	 * 
	 * @param firewallDB a persistent database name
	 * @param dbName the name of the persistent database
	 * @param collectionName the name of the collection for the database
	 * 
	 * @return all firewall rules, null when the database is empty
	 */
	public Collection<Map<String, Object>> getAllDBEntries (IStorageService firewallDB, String dbName, String collectionName) {
		
		if ( !firewallDB.isConnected() ) {
			return Collections.emptyList();
		}
		
		List<Map<String, Object>> entryList = null;
		
		try {
			entryList = firewallDB.retrieveAll(dbName, collectionName);
		} catch (StorageException e) {
			e.printStackTrace();
		}
		
		Collection<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if ( entryList == null ) {
			// this can be caused when we cannot connect to database.
			return resultList;
		}

		for (Map<String, Object> entry : entryList) {
			HashMap<String, Object> rule = null;
			rule = (HashMap<String, Object>) entry;
			resultList.add(rule);
		}
		
		return resultList;
	}

	/**
	 * Returns the list of RESTApi objects
	 * 
	 * @return array of all RESTApi objects 
	 */
	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}

}
