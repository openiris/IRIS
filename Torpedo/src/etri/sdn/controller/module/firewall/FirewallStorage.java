package etri.sdn.controller.module.firewall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import etri.sdn.controller.Main;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.StorageException;
import etri.sdn.controller.util.Logger;

/**
 * This class is a storage to manage firewall rules.
 * FirewallStorage has FirewallEntryTable class which supports basic 
 * storage operations against a memory storage and a persistent database.
 * 
 * @author jshin
 */
public class FirewallStorage extends OFModel{

	public OFMFirewall manager;

	private String name;

	private FirewallEntryTable firewallEntryTable;

	FirewallStorage (OFMFirewall manager, String name){
		this.manager = manager;
		this.name = name;
		firewallEntryTable = new FirewallEntryTable();
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
		List<Map<String, Object>> entryList = (List<Map<String, Object>>) getAllDBEntries
				(manager.getStorageInstance(), manager.getDbName(), manager.getCollectionName() );
		
		System.out.println("Firewall DB : " + entryList.toString());
		System.out.println("Firewall Mem: " + this.firewallEntryTable.getAllFirewallEntries().toString());
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
		
		Map<String, Object> entry = getDBEntry (firewallDB, dbName, collectionName, ruleid);
		if (entry == null) {
			String status = "No such rule exists. ruleid: " + ruleid;
			Logger.error(status);
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
		
		List<Map<String, Object>> entryList = null;
		
		try {
			entryList = firewallDB.retrieveAll(dbName, collectionName);
		} catch (StorageException e) {
			e.printStackTrace();
		}
		
		Collection<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> entry : entryList) {
			HashMap<String, Object> rule = null;
			rule = (HashMap<String, Object>) entry;
			resultList.add(rule);
		}
		
		return resultList;
	}
	
	
	/**
	 * Array of RESTApi objects. 
	 * Each objects represent a REST call handler routine bound to a specific URI.
	 */
	private RESTApi[] apis = {
			
			/**
			 * This object implements a REST handler routine for operations of firewall.
			 */
			new RESTApi(
					"/wm/firewall/module/{op}/json",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							
							IFirewallService firewall = getManager();

							String op = (String) request.getAttributes().get("op");
							//System.out.println("op : " + op);

							String result = null;

							if (op.toLowerCase().equals("storagerules")){
								// create an object mapper.
								ObjectMapper om = new ObjectMapper();
								
								try {
									result = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString( firewallEntryTable.values() );
								} catch (Exception e) {
									e.printStackTrace();
									return;
								}
							}

							else if (op.toLowerCase().equals("status")){
								if (firewall.isEnabled())
									result = "{\"result\" : \"firewall enabled\"}";
								else
									result = "{\"result\" : \"firewall disabled\"}";
							} 

							else if (op.toLowerCase().equals("enable")){
								firewall.enableFirewall(true);
								result = "{\"status\" : \"success\", \"details\" : \"firewall running\"}";
							}

							else if (op.toLowerCase().equals("disable")){
								firewall.enableFirewall(false);
								result = "{\"status\" : \"success\", \"details\" : \"firewall stopped\"}";
							}

							else if (op.toLowerCase().equals("subnet-mask")){
								result = firewall.getSubnetMask();
							}

							else {
								result = "{\"status\" : \"failure\", \"details\" : \"invalid operation\"}";
							}

							response.setEntity(result, MediaType.APPLICATION_JSON);
						}
					}
					),

			/**
			 * This object implements REST handler routines for 
			 * retrieving, inserting and deleting firewall rule(s).
			 */
			new RESTApi(
					"/wm/firewall/rules/json",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							IFirewallService firewall = getManager(); 

							Method m = request.getMethod();
							String result = null;
							String status = null;
							boolean exists = false;
							FirewallRule inRule;

							if (m == Method.GET){		
								// create an object mapper.
								ObjectMapper om = new ObjectMapper();
								om.registerModule( new OFFirewallRuleReplySerializerModule() );
								
								try {
									result = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString( manager.getRules() );
								} catch ( IOException e ) {
									e.printStackTrace();
									return;
								}
							}
							
							else if (m == Method.POST){
								Iterator<FirewallRule> iter = firewall.getRules().iterator();

								String entityText = request.getEntityAsText();
								entityText = entityText.replaceAll("[\']", "");
								//System.out.println(entityText);

								try {
									inRule = FirewallRule.jsonToFirewallRule(entityText);
								} catch (IOException e) {
									Logger.error("Error parsing firewall rule: " + entityText, e);
									e.printStackTrace();
									return;
								}

								while (iter.hasNext()) {
									FirewallRule r = iter.next();
									if ( inRule.isSameAs(r) ){
										exists = true;
										status = "Error! A similar firewall rule already exists.";
										Logger.error(status);
										break;
									}
								}
									
								if ( exists == false ){
									// add rule to firewall
									firewall.addRule(inRule);
									status = "Rule added";
								}
								
								result = "{\"status\" : \"" + status + "\"}";
							}

							else if (m == Method.DELETE){
								Iterator<FirewallRule> iter = firewall.getRules().iterator();

								String entityText = request.getEntityAsText();
								entityText = entityText.replaceAll("[\']", "");
								//System.out.println(entityText);

								try {
									inRule = FirewallRule.jsonToFirewallRule(entityText);
								} catch (IOException e) {
									Logger.error("Error parsing firewall rule: " + entityText, e);
									e.printStackTrace();
									return;
								}

								while (iter.hasNext()) {
									FirewallRule r = iter.next();
									if ( r.ruleid == inRule.ruleid){
										exists = true;
										break;
									}
								}

								if ( !exists ){
									status = "Error! Can't delete, a rule with this ID doesn't exist.";
									Logger.error(status);
									return;
								} else {
									// delete rule from firewall
									firewall.deleteRule( inRule.ruleid );
									status = "Rule deleted";
								}

								result = "{\"status\" : \"" + status + "\"}";
							}
							
							response.setEntity(result, MediaType.APPLICATION_JSON);
						}
					}
					)

	};

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
