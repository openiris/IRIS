package etri.sdn.controller.module.staticentrymanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import etri.sdn.controller.Main;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.util.Logger;
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



	StaticFlowEntryStorage(OFMStaticFlowEntryManager manager, String name) {
		this.manager = manager;
		this.name = name;
		flowModMap = new HashMap<String, Map<String, Object>>();
		dpidToFlowModNameIndex = new HashMap<String, Set<String>>();
		flowModNameToDpidIndex = new HashMap<String, String>();
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
		List<Map<String, Object>> entries = null;

		entries = (List<Map<String, Object>>) getAllDBEntries (
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
	 * OFMStaticFlowEntryManager supports the persistent database. Therefore all 
	 * rules are loaded to the memory when the controller is rebooted. This method
	 * is called by RESTApi.
	 * 
	 * @throws StaticFlowEntryException 
	 */
	public void reloadFlowModsToSwitch() throws StaticFlowEntryException {
		for (String flowName : getFlowModMap().keySet()) {

			// This method will throw exception when flow add failed.
			getManager().addFlow(
					flowName, 
					getFlowModMap().get(flowName), 
					(String) getFlowModMap().get(flowName).get("switch"));

			Logger.stdout("Entry loaded to switch: " + getFlowModMap().get(flowName));
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
		nameset.remove(name);
		if ( nameset.isEmpty() ) {
			getDpidToFlowModNameIndex().remove(dpid);
		}
		else {
			getDpidToFlowModNameIndex().put(dpid, nameset);
		}

		getFlowModNameToDpidIndex().remove(name);

		if (Main.debug) {
			System.out.println("dpid to flowname: " + getDpidToFlowModNameIndex().toString());
			System.out.println("flowname to dpid: " + getFlowModNameToDpidIndex().toString());
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

		if (Main.debug) {
			System.out.println("dpid to flowname: " + getDpidToFlowModNameIndex().toString());
			System.out.println("flowname to dpid: " + getFlowModNameToDpidIndex().toString());
		}
	}

	/**
	 * Compares entries of the memory storage and the persistent database.
	 * This method is only used for debug.
	 * 
	 */
	public void printDB () {
		List<Map<String, Object>> entry = (List<Map<String, Object>>) getAllDBEntries
				(manager.getDB(), manager.getDbName(), manager.getCollectionName() );

		System.out.println("StaticEntry DB : " + entry.toString());
		System.out.println("StaticEntry Mem: " + getFlowModMap());
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
		Map<String, Object> entry = getDBEntry (db, dbName, collectionName, name);
		if (entry == null) {
			Logger.debug("No such entry exists. name: " + name);
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

		try {
			entries = db.retrieveAll(dbName, collectionName);
		} catch (StorageException e) {
			e.printStackTrace();
		}

		Collection<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		if ( entries != null )
			for (Map<String, Object> entry : entries) {
				HashMap<String, Object> result = null;
				result = (HashMap<String, Object>) entry;
				results.add(result);
			}

		return results;
	}

	
	/**
	 * OFModel methods. Array of RESTApi objects.
	 * Each objects represent a REST call handler routine bound to a specific URI.
	 */
	private RESTApi[] apis = {
			/*
			 * LIST example
			 * OF1.0,1.3:	curl http://{controller_ip}:8080/wm/staticflowentry/list/all/json
			 * 				curl http://{controller_ip}:8080/wm/staticflowentry/list/00:00:00:00:00:00:00:01/json
			 */
			new RESTApi("/wm/staticflowentry/list/{switch}/json",
					new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;

					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						
						String sw = (String) request.getAttributes().get("switch");
						Set<String> flows = new HashSet<String>();
						if (sw.toLowerCase().equals("all")) {
							flows = getFlowModMap().keySet();
							if (flows.isEmpty()) {
								flows = null;
							}
						} else {
							flows = getDpidToFlowModNameIndex().get(sw);
						}

						if (flows != null) {
							for (String flow : flows) {
								g.writeFieldName(flow);
								g.writeString( getFlowModMap().get(flow).toString() );
							}
						}
						
						g.writeEndObject();
						g.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}),

			/*
			 * CLEAR example
			 * OF1.0,1.3:	curl http://{controller_ip}:8080/wm/staticflowentry/clear/all/json
			 * 				curl http://{controller_ip}:8080/wm/staticflowentry/clear/00:00:00:00:00:00:00:01/json
			 */
			new RESTApi("/wm/staticflowentry/clear/{switch}/json",
					new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					StringBuilder status = new StringBuilder();

					String sw = (String) request.getAttributes().get("switch");
					Set<String> flows = new HashSet<String>();
					if (sw.toLowerCase().equals("all")) {
						flows = getFlowModMap().keySet();
						if (flows.isEmpty())	flows = null;
					} else {
						flows = getDpidToFlowModNameIndex().get(sw);
					}

					if (flows != null) {
						boolean ret = true;
						StringBuilder exceptionlist = new StringBuilder();
						
						//Avoiding ConcurrentModificationException
						Set<String> flowsToDel = new HashSet<String>();
						flowsToDel.addAll(flows);

						for (String flow : flowsToDel) {
							try {
								getManager().deleteFlow( flow );
							}
							catch (UnsupportedOperationException e) {
								ret = false;
								exceptionlist.append("Wrong version for the switch. ");
							}
							catch (StaticFlowEntryException e) {
								ret = false;
								exceptionlist.append(e.getReason());
								exceptionlist.append(". ");
							}
							catch (Exception e) {
								ret = false;
								e.printStackTrace();
							}
						}
						
						if (ret) {
							status.append("All entries are cleared.");
						}
						else {
							status.append("Failure clearing entries: ");
							status.append(exceptionlist);
						}
					}
					else {
						status.append("There is no entry.");
					}

					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						g.writeFieldName("result");
						g.writeString(status.toString());
						g.writeEndObject();
						g.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}),
			
			/*
			 * RELOAD example
			 * OF1.0,1.3:	curl http://{controller_ip}:8080/wm/staticflowentry/reload/json
			 */
			new RESTApi("/wm/staticflowentry/reload/json",
					new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					String status = null;

					try {
						if (!getFlowModNameToDpidIndex().isEmpty()) {
							getManager().reloadFlowsToSwitch();
							status = "All entries are reloaded to switches.";
						}
						else {
							status = "There is no entry";
						}
					}
					catch (UnsupportedOperationException e) {
						status = "Fail to reload entry: Wrong version for the switch";
					}
					catch (Exception e) {
						e.printStackTrace();
						status = "Fail to reload entries to switches.";
					}

					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						g.writeFieldName("result");
						g.writeString(status);
						g.writeEndObject();
						g.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}),
			
			new RESTApi("/wm/staticflowentry/json",
					new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					String status = null;

					Method m = request.getMethod();
					String entityText = request.getEntityAsText();
					entityText = entityText.replaceAll("[\']", "");

					/*
					 * OFMStaticFlowEntryManager does NOT check every exception for user input 
					 * except for name field (key of StaticFlowEntry). Therefore, the field check 
					 * is user's portion.
					 * For example, let us consider the most simple experiment. When user want to
					 * do drop for all flows and allow ping between two end hosts, switches need
					 * the rules of ARP and ICMP both. But, the ping may be succeed with ICMP rule
					 * only if the ARP entries exist in the ARP table until the entries timed out.
					 * 
					 * OFMStaticFlowEntryManager supports the unified input format. e.g. the user
					 * input have to contain 'instructions' entry although switches support OF1.0
					 * only. In this case, OFMStaticFlowEntryManager sets not OFInstruction but
					 * OFAction directly when it builds OFFlowMod. This can be applied only when
					 * the command is 'apply_actions'.
					 */
					/*
					 * ADD example
					 * OF1.0,1.3:	curl http://{controller_ip}:8080/wm/staticflowentry/list/all/json
					 * 				curl -d '{"switch":"00:00:00:00:00:00:00:01","name":"s1","priority":"101","eth_type":"0x0800","ipv4_src":"10.0.0.1","ipv4_dst":"10.0.0.2","active":"true","instructions":[{"apply_actions":[{"output":"2"}]}]}' http://{controller_ip}:8080/wm/staticflowentry/json
					 * 				curl -d '{"switch":"00:00:00:00:00:00:00:02","name":"s20","priority":"1001","eth_type":"0x0806","ipv4_dst":"10.0.0.4","active":"true","instructions":[{"apply_actions":[{"output":"3"}]}]}' http://{controller_ip}:8080/wm/staticflowentry/json
					 * 				curl -d '{"switch":"00:00:00:00:00:00:00:01","name":"s1","priority":"1001","eth_type":"0x0800","ipv4_dst":"10.0.0.3","active":"true","instructions":[{"apply_actions":[{"set_field":{"ipv4_dst":"10.0.0.2"}},{"output":"2"}]}]}' http://{controller_ip}:8080/wm/staticflowentry/json
					 */
					if (m == Method.POST) {
						Map<String, Object> entry;
						Object flowName;
						
						try {
							entry = StaticFlowEntry.jsonToStaticFlowEntry(entityText);

							flowName = entry.get("name");
							if (flowName != null) {
								StaticFlowEntry.checkInputField(entry.keySet());
								StaticFlowEntry.checkMatchPrerequisite(entry);
								getManager().addFlow((String) flowName, entry, (String) entry.get("switch"));
								status = "Entry pushed: " + flowName;
							}
							else {
								status = "The name field is indispensable";
							}
						}
						catch (UnsupportedOperationException e) {
							status = "Fail to push entry: Wrong version for the switch";
						}
						catch (StaticFlowEntryException e) {
							status = e.getReason();
						}
						catch (IOException e) {
							status = "Fail to parse JSON format";
							e.printStackTrace();
						}
						catch (Exception e) {
							e.printStackTrace();
							status = "Fail to insert entry";
						}
						
						try {
							g = f.createJsonGenerator(sWriter);
							g.writeStartObject();
							g.writeFieldName("result");
							g.writeString(status);
							g.writeEndObject();
							g.close();
						} catch (Exception e){
							e.printStackTrace();
						}
					} 

					/*
					 * DELETE example
					 * OF1.0,1.3:	curl -X DELETE -d '{"name":"s1"}' http://{controller_ip}:8080/wm/staticflowentry/json
					 */
					else if (m == Method.DELETE) {
						Map<String, Object> entry;
						Object flowName;

						try {
							entry = StaticFlowEntry.jsonToStaticFlowEntry(entityText);
							
							flowName = entry.get("name");
							if (flowName != null) {
								getManager().deleteFlow((String)flowName);
								status = "Entry deleted: " + flowName;
							}
							else {
								status = "The name field is indispensable.";
							}
						}
						catch (UnsupportedOperationException e) {
							status = "Fail to delete entry: Wrong version for the switch";
						}
						catch (StaticFlowEntryException e) {
							status = e.getReason();
						}
						catch (Exception e) {
							e.printStackTrace();
							status = "Fail to delete entry";
						}
						
						try {
							g = f.createJsonGenerator(sWriter);
							g.writeStartObject();
							g.writeFieldName("result");
							g.writeString(status);
							g.writeEndObject();
							g.close();
						} catch (Exception e){
							e.printStackTrace();
						}
					}

					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			})
	};

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
