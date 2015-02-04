package etri.sdn.controller.module.storagemanager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import etri.sdn.controller.IService;
import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.protocol.io.Connection;

/**
 * This class enables a persistent storage for the controller.
 * 
 * Since Aug 11, 2013
 * Last Modified Aug 20, 2013
 * @author SaeHyong Park (labry@etri.re.kr)
 * 
 */
public class OFMStorageManager extends OFModule implements IStorageService {
	
	static final Logger logger = LoggerFactory.getLogger(OFMStorageManager.class);

	private MongoClient mongoClient;
	private DB db;
	private ObjectMapper om;
	private Storage storage;
	
	/**
	 * Constructor that creates all member variables
	 */
	public OFMStorageManager()  {
		
		TorpedoProperties conf = TorpedoProperties.loadConfiguration();
		
		String ip = conf.getString("storage-ip");
		int port = conf.getInt("storage-port");
		String db = conf.getString("storage-default-db");
		String passwd = conf.getString("storage-password");

		this.storage = new Storage(this);
		
		try {
			
			this.mongoClient = new MongoClient(ip, port);
			this.db = this.mongoClient.getDB(db);
			boolean auth = this.db.authenticate(db, passwd.toCharArray());
			if(auth) {
				logger.info("DB login successful..");
			} else {
				logger.error("DB login failed.");
			}
			
		} catch ( UnknownHostException e ) {
			logger.info("unknown DB host. We continue without database.");
			this.mongoClient = null;
			
		} catch ( Exception e ) {
			logger.info("cannot log in the database. We continue without database.");
			this.mongoClient = null;
		}
		
		this.om =  new ObjectMapper();
	}
	
	@Override
	protected Collection<Class<? extends IService>> services() {
		List<Class<? extends IService>> ret = new LinkedList<Class<? extends IService>>();
		ret.add(IStorageService.class);
		return ret;
	}

	@Override
	protected void initialize() {
		logger.debug("OFMStorageManager initialize");
	}

	/**
	 * inserts a Map<String, object> object into a persistent storage,
	 * and returns true when insertion is successful and returns false in other cases.
	 *  
	 * @param dbName 
	 * name of the database.
	 * @param collection 
	 * name of collection for the database.
	 * @param query 
	 * Map<String, Object> type data to be inserted.
	 * @return returns true when inserted, false when fails to insert.
	 */
	@Override
	public boolean insert(String dbName, String collection, Map<String, Object> query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = StorageConverter.MapToDBObject(query);

		DBCursor cursor = db.getCollection(collection).find(dbObject);

		if(cursor.count() == 0) {
			WriteResult mResult = null;
			try {
				mResult = db.getCollection(collection).insert(dbObject);
			} catch(Exception e) {
				logger.error("mResult is {}" + e);
				return false;
			}
			Object isInserted = mResult.getField("err");
			if(isInserted == null) {
				logger.debug("inserted successfully.");
			} else {
				logger.info("error occur during inserting procedure.");
				return false;
			}
		}
		return true;
	}

	/**
	 * inserts a JSON style String object into a persistent storage.
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName 		name of the database.
	 * @param collection 	name of collection for the database.
	 * @param r 			JSON style String data to be inserted.
	 * @return returns true when inserted, false when fails to insert.
	 */
	@Override
	public boolean insert(String dbName, String collection, String r) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		boolean inserted = false;

		this.db = this.mongoClient.getDB(dbName);
		String s = StorageConverter.replaceDotToDotUtf(r);
		
		logger.debug(r); //for debugging use;
		
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		Map<String, Object> o;
		try {
			o = om.readValue(s, typeRef);
		} catch (IOException e) {
			logger.info("parsing error: {}", r);
			return false;
		} 

		BasicDBObject query = new BasicDBObject(o);
		Object isInserted = null;

		DBCursor cursor = db.getCollection(collection).find(query);

		if(cursor.count() == 0) {
			logger.debug("inserting..");

			try {
				WriteResult mResult = db.getCollection(collection).insert(query);
				logger.debug("result " + mResult);
				isInserted = mResult.getField("err");
			} catch(Exception e) {
				logger.debug("mResult is {}", e);
			}

			if(isInserted == null) {
				logger.debug("inserted successfully.");
				inserted = true;
			} else {
				logger.info("error occur during inserting procedure.");
			}
			return inserted;
		} else {
			return inserted;
		}
	}
	
	
	/**
	 * deletes data that matches the JSON style query from the persistent storage.
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param query 
	 * Map<String, Object> type query to delete the matching data from the persistent storage.
	 * @return returns true when deleted, false when fails to delete.
	 */
	@Override
	public boolean delete(String dbName, String collection, Map<String, Object> query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = StorageConverter.MapToDBObject(query);

		DBCursor cursor = db.getCollection(collection).find(dbObject);

		if(cursor.count() != 0) {
			db.getCollection(collection).remove(dbObject);
			return true;
		} 

		return false;
	}
	

	/**
	 * deletes data that matches the JSON style query from the persistent storage.
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName		name of the persistent storage.
	 * @param collection 	name of collection for the persistent storage.
	 * @param r 			JSON style String query to delete the matching data from the persistent storage.
	 * @return 				returns true when deleted, false when fails to delete.
	 */
	@Override
	public boolean delete(String dbName, String collection, String r) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		

		try {
			this.db = this.mongoClient.getDB(dbName);
			String s = StorageConverter.replaceDotToDotUtf(r);
			
			logger.debug(r); 
			
			TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
			Map<String,Object> o = om.readValue(s, typeRef); 

			BasicDBObject query = new BasicDBObject(o);

			DBCursor cursor = db.getCollection(collection).find(query);

			if(cursor.count() != 0) {
				db.getCollection(collection).remove(query);
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	/**
	 * updates data that matches the JSON style key from the persistent storage.
	 * The method does not update data with query when there is no matching key in the storage.
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection
	 * name of collection for the persistent storage.
	 * @param key 
	 * Map<String, Object> type key to update the matching data from the persistent storage.
	 * @param query 
	 * Map<String, Object> type query to update the matching data from the persistent storage.
	 * In case of matching data, the matching data is replaced with the query and original key and value pairs are lost, 
	 * and if you want to keep the original key-value pairs while updating the data, 
	 * then you should use "$set" as key and the query as value.
	 * @return returns true when updated, false when fails to update.
	 */
	@Override
	public boolean update(String dbName, String collection, Map<String, Object> key, Map<String, Object> query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbKey = StorageConverter.MapToDBObject(key); 
		BasicDBObject dbQuery = StorageConverter.MapToDBObject(query); 

		WriteResult result = db.getCollection(collection).update(dbKey, dbQuery, false, false);

		logger.debug("M : {}", result);
		Boolean isInserted = (Boolean)result.getField("updatedExisting");
		if(isInserted == Boolean.TRUE) { 
			logger.debug("update successful.");
			return true;
		} 

		logger.debug("noting to be updated or updating error.");
		return false;
	}

	
	/**
	 * updates data that matches the JSON style key from the persistent storage.
	 * The method does not update data with query when there is no matching key in the storage.
	 * returns true when successful and false in other cases
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param key 
	 * JSON style String key to update the matching data from the persistent storage.
	 * @param query 
	 * JSON style String query to update the matching data from the persistent storage.
	 * In case of matching data, the matching data is replaced with the query and original key and value pairs are lost, 
	 * and if you want to keep the original key-value pairs while updating the data, 
	 * then you should use "$set" as key and the query as value.
	 * @return returns true when updated, false when fails to update.
	 */
	@Override
	public boolean update(String dbName, String collection, String key, String query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		String s1 = StorageConverter.replaceDotToDotUtf(key);
		String s2 = StorageConverter.replaceDotToDotUtf(query);

		logger.debug(s1); //for debugging use;
		logger.debug(s2); //for debugging use;

		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		Map<String,Object> o1; 
		Map<String, Object> o2;
		try {
			o1 = om.readValue(s1, typeRef); 
			o2 = om.readValue(s2, typeRef);
		} catch (IOException e) {
			throw new StorageException(e);
		} 

		BasicDBObject dbKey = new BasicDBObject(o1);
		BasicDBObject dbQuery = new BasicDBObject(o2);

		WriteResult mResult = db.getCollection(collection).update(dbKey, dbQuery, false, false);
		Object isInserted = mResult.getField("err");

		if(isInserted == null) { 
			logger.debug("update successful.");
			return true;
		}
		logger.debug("error occur during updating procedure: {}", query);
		return false;
	}
	
	
	/**
	 * upserts data that matches the JSON style key from the persistent storage.
	 * The method inserts the query when there is no matching key in the storage.
	 * In case there is a matching key, it performs update operation. 
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param key 
	 * Map<String, Object> type key to upsert the matching data from the persistent storage.
	 * @param query 
	 * Map<String, Object> type query to upsert the matching data from the persistent storage.
	 * In case of matching data, the matching data is replaced with the query and some original key and value pairs can be lost, 
	 * and if you want to keep all of the original key-value pairs while upserting the data, 
	 * then you should use "$set" as key and the query as value.
	 * @return returns true when updated, false when fails to update.
	 */
	@Override
	public boolean upsert(String dbName, String collection, Map<String, Object> key, Map<String, Object> query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbKey = StorageConverter.MapToDBObject(key); 
		BasicDBObject dbQuery = StorageConverter.MapToDBObject(query); 

		WriteResult result = null;
		try {
			result = db.getCollection(collection).update(dbKey, dbQuery, true, false);
		} catch(Exception e) {
			logger.error("upsertion erorr ={} ", e);
		}
        assert result != null;
        Object isInserted = result.getField("err");
		if(isInserted == null) { 
			logger.debug("upserted successful.");
			return true;
		} 

		logger.debug("error occur during upserting procedure: {}", dbQuery);
		return false;
	}

	
	/**
	 * upserts data that matches the JSON style key from the persistent storage.
	 * The method inserts the query when there is no matching key in the storage.
	 * In case there is a matching key, it performs update operation. 
	 * returns true when successful and false in other cases.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param key 
	 * JSON style String key to upsert the matching data from the persistent storage.
	 * @param query 
	 * JSON style String query to upsert the matching data from the persistent storage.
	 * In case of matching data, the matching data is replaced with the query and some original key and value pairs can be lost, 
	 * and if you want to keep all of the original key-value pairs while upserting the data, 
	 * then you should use "$set" as key and the query as value.
	 * @return returns true when updated, false when fails to update.
	 */
	@Override
	public boolean upsert(String dbName, String collection, String key, String query) throws StorageException {

		if ( this.mongoClient == null ) {
			return false;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		String s1 = StorageConverter.replaceDotToDotUtf(key);
		String s2 = StorageConverter.replaceDotToDotUtf(query);

		logger.debug(s1); //for debugging use;
		logger.debug(s2); //for debugging use;

		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		Map<String,Object> o1; 
		Map<String, Object> o2;
		try {
			o1 = om.readValue(s1, typeRef); 
			o2 = om.readValue(s2, typeRef);
		} catch (IOException e) {
			throw new StorageException(e);
		} 

		BasicDBObject dbKey = new BasicDBObject(o1);
		BasicDBObject dbQuery = new BasicDBObject(o2);

		WriteResult mResult = db.getCollection(collection).update(dbKey, dbQuery, true, false);
		Object isInserted = mResult.getField("err");

		if(isInserted == null) { 
			logger.debug("upserted successful.");
			return true;
		}
		logger.debug("error occur during upserting procedure: {}", query);
		return false;
	}
	
	
	/**
	 * retrieves all data from the persistent storage in the specific collection,
	 * and returns data as List of String. The method removes "_id" key-value pair which MongoDB automatically
	 * attaches to data. 
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @return returns data as List of String.
	 */
	@Override
	public List<String> retrieveAsStringAll(String dbName, String collection) throws StorageException {

		if ( this.mongoClient == null ) {
			return null;
		}
		

		this.db = this.mongoClient.getDB(dbName);
		List<String> resultList = new ArrayList<String>();
		DBCursor cursor = db.getCollection(collection).find();
		DBObject result = null;

		while(cursor.hasNext()) {
			result = cursor.next();
			String r = StorageConverter.replaceDotUtfToDot(result.toString());

			// following regex removes every occurrence of the JSON key-value pair of "_id" : { $oid.... } , 
			r = r.replaceAll("\"_id\"\\s*:\\s*\\{[:$\"\\s0-9a-zA-Z]+\\}\\s*,\\s*", "");
			resultList.add(r);
		}

		return resultList;
	}	

	/**
	 * retrieves data that matches the JSON style query from the persistent storage.
	 * and returns data as List of String. The method removes "_id" key-value pair which MongoDB automatically
	 * attaches to data. 
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param query 
	 * JSON style String query to retrieve the matching data from the persistent storage.
	 * @return returns data as List of String.
	 */
	@Override
	public List<String> retrieveAsString(String dbName, String collection, String query) throws StorageException {

		if ( this.mongoClient == null ) {
			return null;
		}
		
		BasicDBObject dbObject = null;
		this.db = this.mongoClient.getDB(dbName);
		String s = StorageConverter.replaceDotToDotUtf(query);
		
		logger.debug(s); //for debugging use;
		
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		try {
			Map<String,Object> o = om.readValue(s, typeRef); 
			dbObject = new BasicDBObject(o);

		} catch (IOException e) {
			logger.error("DB object creation failed: {}" + e.getMessage());
			throw new StorageException(e);
		}
		List<String> resultList = new ArrayList<String>();
		DBCursor cursor = db.getCollection(collection).find(dbObject);
		DBObject result = null;

		while(cursor.hasNext()) {
			result = cursor.next();
			String r = StorageConverter.replaceDotUtfToDot(result.toString());

			// following regex removes every occurrence of the JSON key-value pair of "_id" : { $oid.... } , 
			r = r.replaceAll("\"_id\"\\s*:\\s*\\{[:$\"\\s0-9a-zA-Z]+\\}\\s*,\\s*", "");
			resultList.add(r);
		}
		return resultList;
	}


	/**
	 * retrieves data that matches the JSON style query from the persistent storage,
	 * and returns data as List of Map<String, Object> type. The method removes "_id" key-value pair which MongoDB automatically
	 * attaches to data. 
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param query 
	 * Map<String, Object> type query to retrieve the matching data from the persistent storage.
	 * @return returns data as List of Map<String, Object> data.
	 */
	@Override
	public List<Map<String, Object>> retrieve(String dbName, String collection, Map<String, Object> query) throws StorageException {

		if ( this.mongoClient == null ) {
			return null;
		}
		
		this.db = this.mongoClient.getDB(dbName);
		BasicDBObject dbObject = StorageConverter.MapToDBObject(query);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DBCursor cursor = db.getCollection(collection).find(dbObject);
		DBObject result = null;

		while(cursor.hasNext()) {
			result = cursor.next();
			Map<String, Object> m = StorageConverter.DBObjectToMap((BasicDBObject) result);
			resultList.add(m);
		}

		return resultList;
	}

	
	/**
	 * retrieves all data from the persistent storage,
	 * and returns data as List of Map<String, Object> type. The method removes "_id" key-value pair which MongoDB automatically
	 * attaches to data. 
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @return returns data as List of Map<String, Object> data.
	 */
	@Override
	public List<Map<String, Object>> retrieveAll(String dbName, String collection) throws StorageException {
		
		if ( this.mongoClient == null ) {
			return null;
		}
		
		this.db = this.mongoClient.getDB(dbName);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DBCursor cursor = db.getCollection(collection).find();
		DBObject result = null;

		while(cursor.hasNext()) {
			result = cursor.next();
			Map<String, Object> m = StorageConverter.DBObjectToMap((BasicDBObject) result);
			resultList.add(m);
		}

		return resultList;
	}	

	
	/**
	 * assigns a key-value pair to ensure the uniqueness of the key-value pair in the persistent storage.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param key 
	 * key-value pair to be unique in the persistent storage. 
	 * This key-value pair should have follow pattern {"key": 1} or {"key": -1}
	 * 1 means ascending order and -1 means descending order.
	 */
	@Override
	public void ensureIndex(String dbName, String collection, Map<String, Object> key) throws StorageException {

		if ( this.mongoClient == null ) {
			return;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		Map<String, Object> unique = new HashMap<String, Object>();
		unique.put("unique", (boolean)true);

		BasicDBObject dbObject1 = new BasicDBObject(key);
		BasicDBObject dbObject2 = new BasicDBObject(unique);
		db.getCollection(collection).ensureIndex(dbObject1, dbObject2);
	}

	/**
	 * drops a key-value pair as an index to ensure the uniqueness in the persistent storage.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 * @param key 
	 * key-value pair to be dropped as an index in the persistent storage.
	 */
	@Override
	public void dropIndex(String dbName, String collection, Map<String, Object> key) throws StorageException {

		if ( this.mongoClient == null ) {
			return;
		}
		
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = new BasicDBObject(key);
		db.getCollection(collection).dropIndex(dbObject);
	}	

	
	/**
	 * retrieves all assigned key-value pairs in the persistent storage.
	 *  
	 * @param dbName
	 * name of the persistent storage.
	 * @param collection 
	 * name of collection for the persistent storage.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIndex(String dbName, String collection) throws StorageException {

		if ( this.mongoClient == null ) {
			return null;
		}
		
		this.db = this.mongoClient.getDB(dbName);
		List<DBObject> listDBobject = db.getCollection(collection).getIndexInfo();
		return (List<Map<String, Object>>) StorageConverter.toJSON(listDBobject);
	}
	
	/**
	 * retrieves names of DBs in the persistent storage.
	 *  
	 * @return List of String type names of DBs in the persistent storage
	 */
	@Override
	public List<String> retrieveDBs() throws StorageException {

		if ( this.mongoClient == null ) {
			return null;
		}
		
		return mongoClient.getDatabaseNames();
	}

	/**
	 * drops DBs in the persistent storage.
	 *  
	 * @param dbName 
	 * name of the persistent storage.
	 */
	@Override
	public void dropDB(String dbName) throws StorageException {
		if ( this.mongoClient == null ) {
			return;
		}
		
		mongoClient.dropDatabase(dbName);
	}
	
	/**
	 * check the connection to the persistent storage.
	 * 
	 * @return false when the connection failed, true otherwise.
	 */
	@Override
	public boolean isConnected () {
		
		if ( this.mongoClient == null ) {
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see IRIS
	 */
	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return false;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return false;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		return false;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] {this.storage};
	}



}
