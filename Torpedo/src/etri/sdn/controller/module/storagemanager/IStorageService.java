package etri.sdn.controller.module.storagemanager;

import java.util.List;
import java.util.Map;

import etri.sdn.controller.IService;

/**
 * The interface to persistent storage used for C.R.U.D. (create, remove, update, delete) operations. 
 * 
 * @author SaeHyong Park (labry@etri.re.kr)
 * 
 */
public interface IStorageService extends IService {
	
	//Data Definition Lanaguage(DDL) APIs
	/**
	 {@link OFMStorageManager#retrieveDBs()}
	 */
	List<String> retrieveDBs() throws StorageException; 
	/**
	 {@link OFMStorageManager#dropDB(String)}
	 */
	void dropDB(String dbName) throws StorageException;
	/**
	 {@link OFMStorageManager#ensureIndex(String, String, Map)}
	 */
	void ensureIndex(String dbName, String collection, Map<String, Object> query) throws StorageException;
	/**
	 {@link OFMStorageManager#dropIndex(String, String, Map)}
	 */
	void dropIndex(String dbName, String collection, Map<String, Object> key) throws StorageException;
	/**
	 {@link OFMStorageManager#getIndex(String, String)}
	 */
	List<Map<String, Object>> getIndex(String dbName, String collection) throws StorageException;
	
	
	//Data Manipulation Language(DML) APIs
	/**
	 {@link OFMStorageManager#insert(String, String, Map)}
	 */
	boolean insert(String dbName, String collection, Map<String, Object> query) throws StorageException;
	/**
	 {@link OFMStorageManager#insert(String, String, String)}
	 */
	boolean insert(String dbName, String collection, String query) throws StorageException;
	
	/**
	 {@link OFMStorageManager#delete(String, String, Map)}
	 */
	boolean delete(String dbName, String collection, Map<String, Object> query)  throws StorageException;
	/**
	 {@link OFMStorageManager#delete(String, String, String)}
	 */
	boolean delete(String dbName, String collection, String query)  throws StorageException;
	
	/**
	 {@link OFMStorageManager#update(String, String, Map, Map)}
	 */
	boolean update(String dbName, String collection,  Map<String, Object> key, Map<String, Object> query)  throws StorageException;
	/**
	 {@link OFMStorageManager#update(String, String, String, String)}
	 */
	boolean update(String dbName, String collection, String key, String query)  throws StorageException;
	
	/**
	 {@link OFMStorageManager#upsert(String, String, Map, Map)}
	 */
	boolean upsert(String dbName, String collection,  Map<String, Object> key, Map<String, Object> query)  throws StorageException;
	/**
	 {@link OFMStorageManager#upsert(String, String, String, String)}
	 */
	boolean upsert(String dbName, String collection, String key, String query)  throws StorageException;
	
	/**
	 {@link OFMStorageManager#retrieve(String, String, Map)}
	 */
	List<Map<String, Object>> retrieve(String dbName, String collection, Map<String, Object> query) throws StorageException;
	/**
	 {@link OFMStorageManager#retrieveAll(String, String)}
	 */
	List<Map<String, Object>> retrieveAll(String dbName, String collection) throws StorageException;
	
	/**
	 {@link OFMStorageManager#retrieveAsString(String, String, String)}
	 */
	List<String> retrieveAsString(String dbName, String collection, String query) throws StorageException;
	/**
	 {@link OFMStorageManager#retrieveAsStringAll(String, String)}
	 */
	List<String> retrieveAsStringAll(String dbName, String collection) throws StorageException;

	/**
	 {@link OFMStorageManager#isConnected()}
	 */
	boolean isConnected () throws StorageException;
	
	//Data Control Langguage(DCL) APIs
	// authentication, grand, revoke ...
	
}
