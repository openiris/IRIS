package etri.sdn.controller.module.storage;

import java.util.List;
import java.util.Map;

import etri.sdn.controller.IService;

/**
 * The interface to persistent storage used for C.R.U.D. (create, remove, update, delete) operations. 
 * All methods are expected to be thread-safe.
 * 
 * @author SaeHyong Park (labry@etri.re.kr)
 * 
 */
public interface IStorageService extends IService {
	
	//Data Definition Lanaguage(DDL) APIs
	List<String> retrieveDBs() throws StorageException; //
	void dropDB(String dbName) throws StorageException; //
	void ensureIndex(String dbName, String collection, Map<String, Object> query) throws StorageException;//
	void dropIndex(String dbName, String collection, Map<String, Object> key) throws StorageException;
	List<Map<String, Object>> getIndex(String dbName, String collection) throws StorageException;//
	
	
	//Data Manipulation Language(DML) APIs
	boolean insert(String dbName, String collection, Map<String, Object> query) throws StorageException;//
	boolean insert(String dbName, String collection, String query) throws StorageException;//
	
	boolean delete(String dbName, String collection, Map<String, Object> query)  throws StorageException;//
	boolean delete(String dbName, String collection, String query)  throws StorageException;//
	
	boolean update(String dbName, String collection,  Map<String, Object> key, Map<String, Object> query)  throws StorageException;
	boolean update(String dbName, String collection, String key, String query)  throws StorageException;
	
	boolean upsert(String dbName, String collection,  Map<String, Object> key, Map<String, Object> query)  throws StorageException;
	boolean upsert(String dbName, String collection, String key, String query)  throws StorageException;
	
	List<Map<String, Object>> retrieve(String dbName, String collection, Map<String, Object> query) throws StorageException;//
	List<Map<String, Object>> retrieveAll(String dbName, String collection) throws StorageException;//
	
	List<String> retrieveAsString(String dbName, String collection, String query) throws StorageException;//
	List<String> retrieveAsStringAll(String dbName, String collection) throws StorageException;//
	
	//Data Control Langguage(DCL) APIs
	// authentication, grand, revoke ...
	
}
