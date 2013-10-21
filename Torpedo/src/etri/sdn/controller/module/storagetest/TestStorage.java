package etri.sdn.controller.module.storagetest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.module.storagemanager.StorageException;

public class TestStorage extends OFModel {
	private final String name = "TestStorage";
	private OFMStorageManager storageInstance;
	
	public TestStorage(OFMStorageManager storageInstance) {
		this.storageInstance = storageInstance;
	}
	String getName() {
		return name;
	}

	private RESTApi[] apis = {

			/**
			 * This object is to implement a REST handler routine for CRUD test
			 * 
			 */
			new RESTApi(
					"/wm/core/controller/test/crud",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							
							//insert   - create
							//retrieve - retrieve
							//update   - update
							//delete   - delete
							
							String dbName = "mydb";
							String collection = "test";
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("key test", "value test");
							data.put("key name", "value justin");
							data.put("key age", "value 20");
							
							try {
								storageInstance.insert(dbName, collection, (Map<String, Object>)data);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							data.clear();
							
							Map<String, Object> query = new HashMap<String, Object>();
							query.put("key test", "value test");
							
							try {
								List<Map<String, Object>> result = storageInstance.retrieve(dbName, collection, query);
								
								for(Map<String, Object> m : result) {
									System.out.println(m);
								}
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							query.clear();
							// This example#1 is for updating 
							Map<String, Object> key = new HashMap<String, Object>();
							key.put("key test", "value test");
							
							query = new HashMap<String, Object>();
							query.put("key age", "value 30");
							
							Map<String, Object> updateQuery = new HashMap<String, Object>();
							updateQuery.put("$set", query);
							
							try {
								storageInstance.update(dbName, collection, key, updateQuery);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							try {
								List<Map<String, Object>> result = storageInstance.retrieve(dbName, collection, query);
								
								System.out.println("--------- updating with $set ---------");
								for(Map<String, Object> m : result) {
									System.out.println(m);
								}
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							key.clear();
							query.clear();
							updateQuery.clear();
							
							// This example #2 is for updating 
							key = new HashMap<String, Object>();
							key.put("key test", "value test");
							
							query = new HashMap<String, Object>();
							query.put("key age", "value 40");
							
							try {
								storageInstance.update(dbName, collection, key, query);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							try {
								List<Map<String, Object>> result = storageInstance.retrieve(dbName, collection, query);
								
								System.out.println("--------- updating w/o $set ---------");
								for(Map<String, Object> m : result) {
									System.out.println(m);
								}
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							
							
							// insert 2 more data
							data.clear();
							data = new HashMap<String, Object>();
							data.put("key test", "value notest");
							data.put("key name", "value suzy");
							data.put("key age", "value 27");
							
							try {
								storageInstance.insert(dbName, collection, (Map<String, Object>)data);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							data.clear();
							data = new HashMap<String, Object>();
							data.put("key test", "value torpedo");
							data.put("key name", "value Amber");
							data.put("key age", "value 28");
							
							try {
								storageInstance.insert(dbName, collection, (Map<String, Object>)data);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							try {
								List<Map<String, Object>> result = storageInstance.retrieveAll(dbName, collection);
								
								System.out.println("--------- two more data inserted ---------");
								for(Map<String, Object> m : result) {
									System.out.println(m);
								}
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							query.clear();
							query = new HashMap<String, Object>();
							query.put("key name", "value Amber");
							
							
							try {
								boolean result = storageInstance.delete(dbName, collection, query);
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							try {
								List<Map<String, Object>> result = storageInstance.retrieveAll(dbName, collection);
								
								System.out.println("--------- after one data deletion ---------");
								for(Map<String, Object> m : result) {
									System.out.println(m);
								}
							} catch (StorageException e) {
								e.printStackTrace();
							}
							
							
							String r = "{\"hello\" : \"" + "welcome!" +"\"}";
							response.setEntity(r, MediaType.APPLICATION_JSON);
							
						}
					}
					)};

					@Override
					public RESTApi[] getAllRestApi() {
						return this.apis;
					}

	}
