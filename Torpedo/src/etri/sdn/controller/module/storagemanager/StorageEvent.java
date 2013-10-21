package etri.sdn.controller.module.storagemanager;

import java.util.HashMap;
import java.util.Map;

public class StorageEvent {
	
	/*
	 *  Constant values for CRUD operations
	 */
	public static final int STORAGE_OPERATION_ALL          = 0;
	public static final int STORAGE_OPERATION_INSERT       = 1;
	public static final int STORAGE_OPERATION_DELETE       = 2;
	public static final int STORAGE_OPERATION_UPDATE       = 3;
	public static final int STORAGE_OPERATION_UPSERT       = 4;
	public static final int STORAGE_OPERATION_RETRIEVE     = 5;
	public static final int STORAGE_OPERATION_RETRIEVE_ALL = 6;
	
	private String dbName;
	private String collectionName;
	private Map<String, Integer> key;
	private Map<String, Integer> value;
	
	
	public StorageEvent(String dbName, String collectionName,
			Map<String, Integer> key) {
		super();
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.key = key;
	}

	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getCollectionName() {
		return collectionName;
	}
	
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public Map<String, Integer> getKey() {
		return key;
	}

	public void setKey(Map<String, Integer> key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object e) {
		
		if( e instanceof StorageEvent) {
			
			StorageEvent storageEvent = (StorageEvent)e;
			if(dbName.equals(storageEvent.dbName) && collectionName.equals(storageEvent.collectionName)) {
				
				Map<String, Integer> key = storageEvent.getKey();
				for(String str1: key.keySet()) {
					if(containsKeyAndValue(key, str1)) {
						return true;
					}
				}
				return false;
			}
		} 
		
		return false;
	}

	private boolean containsKeyAndValue(Map<String, Integer> key, String str1) {
		for(String str2: this.key.keySet()) {
			if(str1.equals(str2)) {
				if(this.key.get(str2) == 0 || key.get(str1) == this.key.get(str2)) {
					Map<String, Integer> value = new HashMap<String, Integer>();
					value.put(str2, key.get(str1));
					setValue(value);
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, Integer> getValue() {
		return value;
	}

	public void setValue(Map<String, Integer> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "StorageEvent [dbName=" + dbName + ", collectionName="
				+ collectionName + ", key=" + key + ", value=" + value + "]";
	}
	
}
