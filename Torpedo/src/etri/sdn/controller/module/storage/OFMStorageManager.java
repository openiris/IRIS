package etri.sdn.controller.module.storage;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.openflow.protocol.OFMessage;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.util.Logger;

/**
 * This class enables persistent storage for the controller.
 * 
 * Concurrency note: attributes and parameters of a context are stored in concurrent 
 * collections that guarantee thread safe access and modification. If several threads 
 * concurrently access objects and modify these collections, they should synchronize 
 * on the lock of the Context instance.
 * 
 * Since Aug 11, 2013
 * Last Modified Aug 12, 2013
 * @author SaeHyong Park (labry@etri.re.kr)
 * 
 */
public class OFMStorageManager extends OFModule implements IStorageService {

	private MongoClient mongoClient;
	private DB db;
	private ObjectMapper om;
	private Storage storage;

	@Override
	protected void initialize() {

		TorpedoProperties conf = TorpedoProperties.loadConfiguration();

		storage = new Storage(this);

		String ip = conf.getString("storage-ip");
		int port = conf.getInt("storage-port");
		String db = conf.getString("storage-default-db");
		String passwd = conf.getString("storage-password");
		try {
			this.mongoClient = new MongoClient(ip, port );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.db = this.mongoClient.getDB(db);
		boolean auth = this.db.authenticate(db, passwd.toCharArray());
		
		if(auth) {
			Logger.stderr("login successful..");
		} else {
			Logger.stderr("login failed.");
		}
		
		om =  new ObjectMapper();

		registerModule(IStorageService.class, this);
		Logger.stderr("OFMStorageManager initialize");

	}

	/**
	 * inserts a Map<String, object> object into a persistent storage
	 * return true when successful and false in other cases
	 *  
	 * @param dbName name of the database
	 * @param collection name of collection for the database
	 * @param query Map<String, Object> type data to be inserted
	 * @return returns true when inserted, false when fails to insert
	 */
	@Override
	public boolean insert(String dbName, String collection, Map<String, Object> query) throws StorageException {

		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = StorageConverter.MapToDBObject(query);

		DBCursor cursor = db.getCollection(collection).find(dbObject);

		if(cursor.count() == 0) {
			WriteResult mResult = null;
			try {
				mResult = db.getCollection(collection).insert(dbObject);
			} catch(Exception e) {
				System.out.println("mResult is " + e);
				return false;
			}
			Object isInserted = mResult.getField("err");
			if(isInserted == null) {
				Logger.stderr("inserted successful.");
			} else {
				Logger.stderr("error occur during inserting procedure.");
				return isInserted == null;
			}
		}
		return true;
	}

	/**
	 * inserts a JSON style String object into a persistent storage
	 * return true when successful and false in other cases
	 *  
	 * @param dbName name of the database
	 * @param collection name of collection for the database
	 * @param query JSON style String data to be inserted
	 * @return returns true when inserted, false when fails to insert
	 */
	@Override
	public boolean insert(String dbName, String collection, String r) throws StorageException {
		boolean inserted = false;

		this.db = this.mongoClient.getDB(dbName);
		String s = StorageConverter.replaceDotToDotUtf(r);
		System.out.println(r); //for debugging use;
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		Map<String, Object> o;
		try {
			o = om.readValue(s, typeRef);
		} catch (IOException e) {
			Logger.stderr("parsing error!");
			return false;
		} 

		BasicDBObject query = new BasicDBObject(o);
		Object isInserted = null;

		DBCursor cursor = db.getCollection(collection).find(query);

		if(cursor.count() == 0) {
			System.out.println("inserting..");

			try {
				WriteResult mResult = db.getCollection(collection).insert(query);
				System.out.println("result " + mResult);
				isInserted = mResult.getField("err");
			} catch(Exception e) {
				System.out.println("mResult is " + e);
			}

			if(isInserted == null) {
				Logger.stderr("inserted successful.");
				inserted = true;
			} else {
				Logger.stderr("error occur during inserting procedure.");
			}
			return inserted;
		} else {
			return inserted;
		}
	}

	@Override
	public boolean delete(String dbName, String collection, Map<String, Object> query) throws StorageException {
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = StorageConverter.MapToDBObject(query);

		DBCursor cursor = db.getCollection(collection).find(dbObject);

		if(cursor.count() != 0) {
			db.getCollection(collection).remove(dbObject);
			return true;
		} 

		return false;
	}

	@Override
	public boolean delete(String dbName, String collection, String r) throws StorageException {

		try {
			this.db = this.mongoClient.getDB(dbName);
			String s = StorageConverter.replaceDotToDotUtf(r);
			System.out.println(r); 
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

	@Override
	public boolean update(String dbName, String collection, Map<String, Object> key, Map<String, Object> query) throws StorageException {

		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbKey = StorageConverter.MapToDBObject(key); 
		BasicDBObject dbQuery = StorageConverter.MapToDBObject(query); 

		WriteResult result = db.getCollection(collection).update(dbKey, dbQuery, false, false);

		System.out.println("M " + result);
		Boolean isInserted = (Boolean)result.getField("updatedExisting");
		if(isInserted == Boolean.TRUE) { 
			Logger.stderr("update successful.");
			return true;
		} 

		Logger.stderr("noting to be updated or updating error.");
		return false;
	}

	@Override
	public boolean update(String dbName, String collection, String key, String query) throws StorageException {
		this.db = this.mongoClient.getDB(dbName);

		String s1 = StorageConverter.replaceDotToDotUtf(key);
		String s2 = StorageConverter.replaceDotToDotUtf(query);

		System.out.println(s1); //for debugging use;
		System.out.println(s2); //for debugging use;

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
			Logger.stderr("update successful.");
			return true;
		}
		Logger.stderr("error occur during updating procedure.");
		return false;
	}

	@Override
	public boolean upsert(String dbName, String collection, Map<String, Object> key, Map<String, Object> query) throws StorageException {
		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbKey = StorageConverter.MapToDBObject(key); 
		BasicDBObject dbQuery = StorageConverter.MapToDBObject(query); 

		WriteResult result = null;
		try {
			result = db.getCollection(collection).update(dbKey, dbQuery, true, false);
		} catch(Exception e) {
			System.out.println("upsertion erorr " + e);
		}
		Object isInserted = result.getField("err");
		if(isInserted == null) { 
			Logger.stderr("upserted successful.");
			return true;
		} 

		Logger.stderr("error occur during upserting procedure.");
		return false;
	}

	@Override
	public boolean upsert(String dbName, String collection, String key, String query) throws StorageException {
		this.db = this.mongoClient.getDB(dbName);

		String s1 = StorageConverter.replaceDotToDotUtf(key);
		String s2 = StorageConverter.replaceDotToDotUtf(query);

		System.out.println(s1); //for debugging use;
		System.out.println(s2); //for debugging use;

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
			Logger.stderr("upserted successful.");
			return true;
		}
		Logger.stderr("error occur during upserting procedure.");
		return false;
	}

	@Override
	public List<String> retrieveAsStringAll(String dbName, String collection) throws StorageException {

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

	@Override
	public List<String> retrieveAsString(String dbName, String collection, String query) throws StorageException {

		BasicDBObject dbObject = null;
		this.db = this.mongoClient.getDB(dbName);
		String s = StorageConverter.replaceDotToDotUtf(query);
		System.out.println(s); //for debugging use;
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){}; 
		try {
			Map<String,Object> o = om.readValue(s, typeRef); 
			dbObject = new BasicDBObject(o);

		} catch (IOException e) {
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


	@Override
	public List<Map<String, Object>> retrieve(String dbName, String collection, Map<String, Object> query) throws StorageException {

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


	@Override
	public List<Map<String, Object>> retrieveAll(String dbName, String collection) throws StorageException {

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

	@Override
	public void ensureIndex(String dbName, String collection, Map<String, Object> key) throws StorageException {

		this.db = this.mongoClient.getDB(dbName);

		Map<String, Object> unique = new HashMap<String, Object>();
		unique.put("unique", (boolean)true);

		BasicDBObject dbObject1 = new BasicDBObject(key);
		BasicDBObject dbObject2 = new BasicDBObject(unique);
		db.getCollection(collection).ensureIndex(dbObject1, dbObject2);
	}

	@Override
	public void dropIndex(String dbName, String collection, Map<String, Object> key) throws StorageException {

		this.db = this.mongoClient.getDB(dbName);

		BasicDBObject dbObject = new BasicDBObject(key);
		db.getCollection(collection).dropIndex(dbObject);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIndex(String dbName, String collection) throws StorageException {

		this.db = this.mongoClient.getDB(dbName);
		List<DBObject> listDBobject = db.getCollection(collection).getIndexInfo();
		return (List<Map<String, Object>>) StorageConverter.toJSON(listDBobject);
	}
	
	@Override
	public List<String> retrieveDBs() throws StorageException {

		return mongoClient.getDatabaseNames();
	}


	@Override
	public void dropDB(String dbName) throws StorageException {
		mongoClient.dropDatabase(dbName);
	}

	/* (non-Javadoc)
	 * @see org.openflow.protocol.OFMessage#computeLength()
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
